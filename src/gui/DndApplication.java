package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import java.io.File;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.scene.Scene;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.util.ArrayList;




public class DndApplication<toReturn> extends Application {
    private Controller mainController;
    private BorderPane root;
    private Stage primaryStage;
    private ListView<String> listView;
    private ListView<String> removeList;
    private ComboBox<String> comboBox;
    private TextArea description;
    private GridPane mainGrid;
    private TextField inputField;
    private TextField inputFieldTwo;
    private TextField saveFilename;
    private TextField loadFilename;

    @Override
    public void start(Stage stage) throws Exception {
        mainController = new Controller(this);
        initialize();
        primaryStage = stage;
        root = setRoot();
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add("res/stylesheet.css");
        primaryStage.setTitle("Dungeon & Dragon Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initialize() {
        listView = new ListView<String>();
        comboBox = new ComboBox<String>();
        description = new TextArea();
        mainGrid = new GridPane();
        inputField = new TextField();
        inputFieldTwo = new TextField();
        removeList = new ListView<String>();
        saveFilename = new TextField();
        loadFilename = new TextField();
    }

    private void getDoorInfo() {
        comboBox.setOnAction((event) -> {
            String selectedDoor = comboBox.getSelectionModel().getSelectedItem();
            String doorDescription = new String();
            doorDescription = mainController.getDoorDescription(findIndex(selectedDoor));
            doorDescriptionWindow(doorDescription);
        });
    }
    public Label setLabel(String s) {
        Label returnLabel = new Label(s);
        returnLabel.setWrapText(true);
        returnLabel.setAlignment(Pos.CENTER);
        return returnLabel;
    }

    private void doorDescriptionWindow(String s) {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Door Description");
        Label desLabel = setLabel(s);
        Button newButton = addAndSetCloseButtton(popupWindow);
        VBox layout= new VBox(10);
        layout.getChildren().addAll(desLabel, newButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene= new Scene(layout, 300, 250);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    private Button addAndSetCloseButtton(Stage s) {
        Button returnButton = createButton();
        returnButton.setText("Close");
        returnButton.setOnAction(e -> s.close());
        return returnButton;
    }

    private ToolBar createToolBar() {
        MenuItem menuItemOne = new MenuItem("Save");
        menuItemOne.setOnAction(e -> saveWindow());
        MenuItem menuItemTwo = new MenuItem("Load"); 
        menuItemTwo.setOnAction(e -> loadWindow());
        MenuButton newMenuButton = new MenuButton("File", null, menuItemOne, menuItemTwo);
        ToolBar newToolBar = new ToolBar(newMenuButton);
        return newToolBar;
    }

    private TextArea createTextArea() {
        TextArea newTextArea = new TextArea();
        newTextArea.setPrefWidth(100);
        newTextArea.setPrefHeight(200);
        description = newTextArea;
        description.setStyle("-fx-opacity: 1;"+"-fx-base: #800;" + "-fx-text-fill: #FFF;");
        description.setDisable(true);
        return description;
    }
    private Button createButton() {
        Button newButton = new Button();
        return newButton;
    }
    private Button createTextButton(String s) {
        Button newButton = new Button();
        newButton.setText(s);
        return newButton;
    }

    private ListView<String> createListView() {
        ListView<String> newListView = new ListView<String>();
        newListView.setOrientation(Orientation.VERTICAL);
        ArrayList<String> nameList = mainController.getNameList();
        for(String s: nameList) {
            newListView.getItems().add(s);
        }
        listView = newListView;
        listView.setStyle("-fx-base: #800;" + "-fx-color: #FFF;");
        return listView;
    }

    private void setLeft(BorderPane pane) {
        VBox newVBox = new VBox();
        Button selectButton = createButton();
        selectButton.setStyle("-fx-pref-width: 100px;" + "-fx-background-color: #800;" + "-fx-text-fill: #FFF;");
        setSelectButton(selectButton);
        newVBox.getChildren().addAll(createListView(), selectButton);
        pane.setLeft(newVBox);
    }

    private void setSelectButton(Button select) {
        select.setText("Select");
        select.setOnAction(e -> selectButtonClicked());
    }

    private void setEditButtion(Button edit) {
        edit.setText("Edit");
        edit.setStyle("-fx-background-color: #800;" + "-fx-text-fill: #FFF;");
        edit.setOnAction(e -> editButtonClicked());
    }
    private int findIndex(String string) {
        int returnIndex = 0;
        for(int i=0; i < string.length(); i++) {
            if(Character.isDigit(string.charAt(i))) {
                returnIndex = Integer.parseInt(String.valueOf(string.charAt(i)));
                break;
            }
        }
        return returnIndex;
    }


    private void setCenter(BorderPane pane) {
        VBox newVBox = new VBox();
        VBox newV = new VBox();
        Canvas can = new Canvas();
        can.setHeight(600);
        can.setWidth(150);
        newV.prefWidth(150);
        newV.prefHeight(500);
        mainGrid.prefWidth(150);
        mainGrid.prefHeight(300);
        mainGrid.setStyle("-fx-border-width: 5px;" + "-fx-border-radius: 10px;");
        newV.getChildren().add(mainGrid);
        newVBox.getChildren().addAll(can, createTextArea());
        pane.setCenter(newVBox);
    }
    private void updateComboBox(ArrayList<String> list) {
        comboBox.getItems().clear();
        for(String s: list) {
            comboBox.getItems().add(s);
        }
    }


    private void updateDescription(String string) {
        description.clear();
        description.setText(string);
    }
    private void editDescription() {
        ObservableList<String> itemSelected;
        itemSelected = listView.getSelectionModel().getSelectedItems();
        String des = new String();
        for(String s: itemSelected) {
            if(s.contains("Chamber")) {
                des = mainController.getChamberDescription(findIndex(s));
            }else if(s.contains("Passage")) {
                des = mainController.getPassageDescription(findIndex(s));
            }
        }
        updateDescription(des);

    }

    private HBox addRemoveMonsterButtons() {
        HBox newHBox = new HBox();
        Button addButton = createTextButton("Add Monster");
        addButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        addButton.setOnAction(e -> addMonsterButton());
        Button removeButton = createTextButton("Remove Monster");
        removeButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        removeButton.setOnAction(e -> removeMonsterButton());
        newHBox.getChildren().addAll(addButton, removeButton);
        return newHBox;
    }

    private HBox addRemoveTreasureButtons() {
        HBox newHBox = new HBox();
        Button addButton = createTextButton("Add Treasure");
        addButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        addButton.setOnAction(e -> addTreasureButton());
        Button removeButton = createTextButton("Remove Treasure");
        removeButton.setOnAction(e -> removeTreasureButton());
        removeButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        newHBox.getChildren().addAll(addButton, removeButton);
        return newHBox;
    }

    private void makeEditWindow() {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Edit Space Contents");
        Button newButton = addAndSetCloseButtton(popupWindow);
        newButton.setStyle("-fx-pref-width: 100px;" + "-fx-alignment: center;");
        VBox layout= new VBox(10);
        layout.getChildren().addAll(setLabel("Monster: "), addRemoveMonsterButtons(), setLabel("Treasure: "), addRemoveTreasureButtons(), newButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-padding: 10px;");
        Scene scene= new Scene(layout, 300, 250);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }
    private void editButtonClicked() {
        makeEditWindow();
    }
    private void addMonsterButton() {
        addMonsterWindow();
    }

    private void removeMonsterButton() {
        removeMonsterWindow();
    }

    private void addTreasureButton() {
        addTreasureWindow();
    }
    private void removeTreasureButton() {
        removeTreasureWindow();
    }

    private void addMonster() {
        ObservableList<String> itemSelected;
        itemSelected = listView.getSelectionModel().getSelectedItems();
        for(String s: itemSelected) {
            if(s.contains("Chamber")) {
                mainController.addMonsterToChamber(Integer.parseInt(inputField.getText()), findIndex(s));
                Alert a = new Alert(AlertType.INFORMATION);
                Label label = new Label("The monster has been added to the selected chamber");
                label.setWrapText(true);
                a.getDialogPane().setContent(label);
                a.show();
            } else if(s.contains("Passage")) {
                mainController.addMonsterToPassage(Integer.parseInt(inputField.getText()), findIndex(s));
                Alert a = new Alert(AlertType.INFORMATION);
                Label label = new Label("The monster has been added to the selected passage");
                label.setWrapText(true);
                a.getDialogPane().setContent(label);
                a.show();
            }
        }
        editDescription();
    }

    private void addTreasure() {
        ObservableList<String> itemSelected;
        itemSelected = listView.getSelectionModel().getSelectedItems();
        for(String s: itemSelected) {
            if(s.contains("Chamber")) {
                mainController.addTreasureToChamber(Integer.parseInt(inputField.getText()), Integer.parseInt(inputFieldTwo.getText()), findIndex(s));
                Alert a = new Alert(AlertType.INFORMATION);
                Label label = new Label("The treasure has been added to the selected chamber");
                label.setWrapText(true);
                a.getDialogPane().setContent(label);
                a.show();
            } else if(s.contains("Passage")) {
                Alert a = new Alert(AlertType.ERROR);
                Label label = new Label("A treasure cannot be added to a passage");
                label.setWrapText(true);
                a.getDialogPane().setContent(label);
                a.show();
            }
        }
        editDescription();
    }

    private boolean confirmation() {
        boolean returnBool = false;
        Alert a = new Alert(AlertType.CONFIRMATION);
        Label label = new Label("Are You Sure You Want To Delete The Selected Items?");
        label.setWrapText(true);
        a.getDialogPane().setContent(label);
        Optional<ButtonType> result = a.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            returnBool = true;
        }
        return returnBool;
    }

    private void saveWindow() {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Save");
        Button newButton = addAndSetCloseButtton(popupWindow);
        Button saveButton = createTextButton("Save");
        saveButton.setOnAction(e -> saveFile());
        Label newLabel = new Label("Save As..: ");
        TextField input = new TextField();
        input.setPromptText("EX: filename.sav");
        HBox save = new HBox();
        save.getChildren().addAll(newLabel, input);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(newButton, saveButton);
        VBox layout= new VBox(10);
        layout.getChildren().addAll(save, buttons);
        saveFilename = input;
        layout.setAlignment(Pos.CENTER);
        Scene scene= new Scene(layout, 300, 250);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    private void loadWindow() {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Load");
        Button newButton = addAndSetCloseButtton(popupWindow);
        Button loadButton = createTextButton("Load");
        loadButton.setOnAction(e -> loadFile());
        Label newLabel = new Label("Load File: ");
        TextField input = new TextField();
        input.setPromptText("EX: filename.sav");
        HBox load = new HBox();
        load.getChildren().addAll(newLabel, input);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(newButton, loadButton);
        VBox layout= new VBox(10);
        layout.getChildren().addAll(load, buttons);
        loadFilename = input;
        layout.setAlignment(Pos.CENTER);
        Scene scene= new Scene(layout, 300, 250);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }
    private void saveFile() {
        boolean bool = false;
        bool = mainController.save(saveFilename.getText());
        if(bool) {
            Alert a = new Alert(AlertType.INFORMATION);
            Label label = new Label("The file was successfully saved");
            label.setWrapText(true);
            a.getDialogPane().setContent(label);
            a.show();
        } else {
            Alert a = new Alert(AlertType.WARNING);
            Label label = new Label("The attempt to save the file was unsuccessful");
            label.setWrapText(true);
            a.getDialogPane().setContent(label);
            a.show();
        }
    }

    private void loadFile() {
        boolean bool = false;
        bool = mainController.load(loadFilename.getText());
        if(bool) {
            Alert a = new Alert(AlertType.INFORMATION);
            Label label = new Label("The file was successfully loaded");
            label.setWrapText(true);
            a.getDialogPane().setContent(label);
            a.show();
        } else {
            Alert a = new Alert(AlertType.WARNING);
            Label label = new Label("The attempt to load the savedfile was unsuccessful");
            label.setWrapText(true);
            a.getDialogPane().setContent(label);
            a.show();
        } 
        
    }
    private void removeMonster() {
        if(!confirmation()) {
            return;
        }
        ObservableList<String> itemSelected;
        String str = new String();
        itemSelected = listView.getSelectionModel().getSelectedItems();
        for(String st: itemSelected) {
            str = st;
        }
        itemSelected = removeList.getSelectionModel().getSelectedItems();
        for(String s: itemSelected) {
            if(str.contains("Chamber")) {
                mainController.removeMonsterFromChamber(findIndex(str), findIndex(s));
                Alert a = new Alert(AlertType.INFORMATION);
                Label label = new Label("The selected monster(s) have been removed form the chamber");
                label.setWrapText(true);
                a.getDialogPane().setContent(label);
                a.show();
            } else if(str.contains("Passage")) {
                mainController.removeMonsterFromPassage(findIndex(str), s);
                Alert a = new Alert(AlertType.INFORMATION);
                Label label = new Label("The selected monster(s) have been removed form the passage");
                label.setWrapText(true);
                a.getDialogPane().setContent(label);
                a.show();
            }
        }
        editDescription();
    }

    private void removeTreasure() {
        if(!confirmation()) {
            return;
        }
        ObservableList<String> itemSelected;
        String str = new String();
        itemSelected = listView.getSelectionModel().getSelectedItems();
        for(String st: itemSelected) {
            str = st;
        }
        itemSelected = removeList.getSelectionModel().getSelectedItems();
        for(String s: itemSelected) {
            if(str.contains("Chamber")) {
                mainController.removeTreasureFromChamber(findIndex(str), findIndex(s));
                Alert a = new Alert(AlertType.INFORMATION);
                Label label = new Label("The selected treasure(s) have been removed form the chamber");
                label.setWrapText(true);
                a.getDialogPane().setContent(label);
                a.show();
            }
        }
        editDescription();
    }
    
    private void addMonsterWindow() {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Add Monster");
        Button newButton = addAndSetCloseButtton(popupWindow);
        newButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        Button addButton = createTextButton("Add");
        addButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        addButton.setOnAction(e -> addMonster());
        Label newLabel = new Label("Input Roll: ");
        TextField input = new TextField();
        input.setPromptText("Input number between 1 and 100");
        HBox buttons = new HBox();
        buttons.getChildren().addAll(newButton,addButton);
        buttons.setStyle("-fx-alignment: center;");
        VBox layout= new VBox(10);
        layout.setStyle("-fx-padding: 10px;");
        layout.getChildren().addAll(newLabel, input, buttons);
        inputField = input;
        layout.setAlignment(Pos.CENTER_LEFT);
        Scene scene= new Scene(layout, 300, 250);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }
    
    private void addTreasureWindow() {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Add Treasure");
        Button newButton = addAndSetCloseButtton(popupWindow);
        newButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        Button addButton = createTextButton("Add");
        addButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        addButton.setOnAction(e -> addTreasure());
        Label newLabel = new Label("Choose Treasure: ");
        TextField input = new TextField();
        input.setPromptText("Input roll between 1 and 100");
        Label label = new Label("Set Container: ");
        TextField in = new TextField();
        in.setPromptText("Input roll between 1 and 20");
        HBox buttons = new HBox();
        buttons.setStyle("-fx-alignment: center;");
        buttons.getChildren().addAll(newButton, addButton);
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10px;");
        layout.getChildren().addAll(newLabel, input, label, in, buttons);
        inputField = input;
        inputFieldTwo = in;
        layout.setAlignment(Pos.CENTER_LEFT);
        Scene scene= new Scene(layout, 300, 250);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    private void removeMonsterWindow() {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Remove Monster");
        Button newButton = addAndSetCloseButtton(popupWindow);
        newButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        Button removeButton = createTextButton("Remove");
        removeButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        removeButton.setOnAction(e -> removeMonster());
        Label newLabel = new Label("Select all the monster(s) you would like to delete: ");
        newLabel.setWrapText(true);
        ListView<String> deleteList = new ListView<String>();
        deleteList = createRemoveMonsterList();
        HBox buttons = new HBox();
        buttons.setStyle("-fx-alignment: center;");
        buttons.getChildren().addAll(newButton, removeButton);
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10px;");
        layout.getChildren().addAll(newLabel, deleteList, buttons);
        removeList = deleteList;
        layout.setAlignment(Pos.CENTER_LEFT);
        Scene scene= new Scene(layout, 300, 250);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    private void removeTreasureWindow() {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Remove Treasure");
        Button newButton = addAndSetCloseButtton(popupWindow);
        newButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        Button removeButton = createTextButton("Remove");
        removeButton.setStyle("-fx-spacing: 0px 5px 0px;" + "-fx-spacing: 0px 5px 0px;");
        removeButton.setOnAction(e -> removeTreasure());
        Label newLabel = new Label("Select all the treasure(s) you would like to delete: ");
        newLabel.setWrapText(true);
        ListView<String> deleteList = new ListView<String>();
        deleteList = createRemoveTreasureList();
        HBox buttons = new HBox();
        buttons.setStyle("-fx-alignment: center;");
        buttons.getChildren().addAll(newButton, removeButton);
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10px;");
        layout.getChildren().addAll(newLabel, deleteList, buttons);
        removeList = deleteList;
        layout.setAlignment(Pos.CENTER_LEFT);
        Scene scene= new Scene(layout, 300, 250);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    private ListView<String> createRemoveMonsterList() {
        ListView<String> newListView = new ListView<String>();
        newListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ArrayList<String> list = new ArrayList<String>();
        ObservableList<String> itemSelected;
        newListView.setOrientation(Orientation.VERTICAL);
        itemSelected = listView.getSelectionModel().getSelectedItems();
        for(String s: itemSelected) {
            if(s.contains("Chamber")) {
                list = mainController.chamberRemoveMonsterList(findIndex(s));
            } else if(s.contains("Passage")) {
                list = mainController.passageRemoveMonsterList(findIndex(s));
            }
        }
        for(String st: list) {
            newListView.getItems().add(st);
        }
        return newListView;
    }

    private ListView<String> createRemoveTreasureList() {
        ListView<String> newListView = new ListView<String>();
        newListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> itemSelected;
        ArrayList<String> list = new ArrayList<String>();
        newListView.setOrientation(Orientation.VERTICAL);
        itemSelected = listView.getSelectionModel().getSelectedItems();
        for(String s: itemSelected) {
            if(s.contains("Chamber")) {
                list = mainController.chamberRemoveTreasureList(findIndex(s));
            }
        }
        for(String st: list) {
            newListView.getItems().add(st);
        }
        return newListView;
    }

    private void selectButtonClicked() {
        ObservableList<String> itemSelected;
        String string = new String("");
        ArrayList<String> doorList = new ArrayList<String>();
        itemSelected = listView.getSelectionModel().getSelectedItems();
        for(String item: itemSelected) {
            if(item.contains("Chamber")) {
                doorList = mainController.getDoorString(mainController.getChamberDoors(findIndex(item)));
                string = mainController.getChamberDescription(findIndex(item));
            } else if (item.contains("Passage")) {
                doorList = mainController.getDoorString(mainController.getPassageDoors(findIndex(item)));
                string = mainController.getPassageDescription(findIndex(item));
            }
        }
        updateDescription(string);
        updateComboBox(doorList);
        getDoorInfo();
    }

    private BorderPane setRoot() {
        BorderPane newBorderPane = new BorderPane();
        newBorderPane.setTop(createToolBar());
        newBorderPane.setStyle("-fx-background-color: #000;");
        setLeft(newBorderPane);
        comboBox.setPromptText("List of Doors");
        comboBox.setStyle("-fx-border-radius: 10px;" + "-fx-pref-width: 150px;" + "-fx-base: #800;" + "-fx-text-fill: #FFF;");
        newBorderPane.setRight(comboBox);
        Button editButton = new Button();
        setEditButtion(editButton);
        HBox newHBox = new HBox();
        newHBox.getChildren().add(editButton);
        newBorderPane.setBottom(newHBox);
        setCenter(newBorderPane);
        return newBorderPane;
    }


    public static void main(String[] args) {
        launch(args);
    }
}