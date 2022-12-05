package presentationLayer.controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import presentationLayer.App;

public abstract class AbstractController {
    protected App app;
    protected Pane pane;
    protected Scene scene;

    public void init(App app, Pane pane) {
        this.app = app;
        this.pane = pane;
        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());

        initComponents();
    }

    public abstract void initComponents();

    public abstract void loadValues(String... args);

    public Scene getScene() {
        return scene;
    }
}
