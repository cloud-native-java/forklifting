package demo;

import java.io.Serializable;

public class Message implements Serializable {

 private String message;

 public Message(String message) {
  this.message = message;
 }

 public Message() {
 }

 public String getMessage() {
  return message;
 }

 @Override
 public String toString() {
  final StringBuilder sb = new StringBuilder("Message{");
  sb.append("message='").append(message).append('\'');
  sb.append('}');
  return sb.toString();
 }
}
