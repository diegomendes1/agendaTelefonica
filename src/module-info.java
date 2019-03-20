module Agenda {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens com.diego.agenda.principal;
    opens com.diego.agenda.ui;
}