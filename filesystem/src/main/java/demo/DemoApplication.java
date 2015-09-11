package demo;

import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@Controller
@RequestMapping(value = "/files")
class FileController {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    // <1>
    @RequestMapping(method = RequestMethod.POST)
    String createOrUpdate(@RequestParam MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        maybeLoadFile(name).ifPresent(p -> gridFsTemplate.delete(getFilenameQuery(name)));
        gridFsTemplate.store(file.getInputStream(), name, file.getContentType()).save();
        return "redirect:/";
    }

    // <2>
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    List<String> list() {
        return getFiles().stream()
                .map(GridFSDBFile::getFilename)
                .collect(Collectors.toList());
    }

    // <3>
    @RequestMapping(value = "/{name:.+}", method = RequestMethod.GET)
    ResponseEntity<?> get(@PathVariable String name) throws Exception {
        Optional<GridFSDBFile> optionalCreated = maybeLoadFile(name);
        if (optionalCreated.isPresent()) {
            GridFSDBFile created = optionalCreated.get();
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                created.writeTo(os);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, created.getContentType());
                return new ResponseEntity<byte[]>(os.toByteArray(), headers, HttpStatus.OK);
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    private List<GridFSDBFile> getFiles() {
        return gridFsTemplate.find(null);
    }

    private Optional<GridFSDBFile> maybeLoadFile(String name) {
        GridFSDBFile file = gridFsTemplate.findOne(getFilenameQuery(name));
        return Optional.ofNullable(file);
    }

    private static Query getFilenameQuery(String name) {
        return Query.query(GridFsCriteria.whereFilename().is(name));
    }
}