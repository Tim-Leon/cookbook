module com.example.cookbookversionone {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;


  opens com.neon.cookbook to javafx.fxml;
  exports com.neon.cookbook;
}