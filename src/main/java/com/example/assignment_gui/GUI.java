package com.example.assignment_gui;

import controller.Controller;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.adt.*;
import model.statement.IStatement;
import model.value.StringValue;
import model.value.Value;
import repository.ProgramState;
import repository.Repository;
import java.util.*;

public class GUI extends Application {
    private Controller currentController = null;
    private boolean isDone = false;
    private ListView<IStatement> programStateList;
    private TextField numberOfProgramStates;
    private TableView<Map.Entry<Integer, Value>> heapTable;
    private TableColumn<Map.Entry<Integer, Value>, String>[] heapTableColumns;
    private ListView<Value> out;
    private ListView<Integer> programStateIdentifiers;
    private TableView<Map.Entry<String, Value>> symbolTable;
    private TableColumn<Map.Entry<String, Value>, String>[] symbolTableColumns;
    private ListView<StringValue> fileTable;
    private ListView<IStatement> executionStack;
    private Button runButton;
    private final HashSet<Integer> ranPrograms = new HashSet<>();
    private Controller[] controllers;
    private HBox[] lines;
    private final int boxSpacing = 10;

    // TODO: make button centered I guess? and text field as well?
    // TODO: let me fucking know what is what thanks
    @Override
    public void start(Stage stage){
        setUpExamples();
        setUpProgramStatesPopup();
        setUpNumberOfProgramStates();
        setUpHeap();
        setUpSymbolTable();
        setUpOut();
        setUpFileTable();
        setUpProgramStateIdentifiers();
        setUpRunButton();
        setUpExecutionStack();
        setUpLayoutAndLook(stage);
    }

    private void setUpLayoutAndLook(Stage stage){
        BorderPane root = new BorderPane();

        final double currentWidth = 750;
        final double currentHeight = 800;

        Scene scene = new Scene(root, currentWidth, currentHeight);
        stage.setScene(scene);
        stage.setTitle("Assignment 7");

        VBox layout = new VBox(boxSpacing);

        lines = new HBox[4];

        lines[0] = new HBox(boxSpacing);
        lines[0].getChildren().addAll(numberOfProgramStates, heapTable);

        lines[1] = new HBox(boxSpacing);
        lines[1].getChildren().addAll(out, fileTable);

        lines[2] = new HBox(boxSpacing);
        lines[2].getChildren().addAll(programStateIdentifiers, symbolTable);

        lines[3] = new HBox(boxSpacing);
        lines[3].getChildren().addAll(executionStack, runButton);

        root.setTop(layout);

        layout.getChildren().addAll(lines);
        stage.show();

        scene.widthProperty().addListener((observable, oldValue, newValue) -> resizeWidth((Double) newValue));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> resizeHeight((Double) newValue));
        resizeWidth(currentWidth);
        resizeHeight(currentHeight);
    }

    private void resizeWidth(double stageWidth){
        final int entriesPerLine = 2;
        double normalWidth = (stageWidth - (entriesPerLine - 1) * boxSpacing)/ entriesPerLine;
        for (HBox line : lines){
            line.setPrefWidth(normalWidth);
        }
        numberOfProgramStates.setPrefWidth(normalWidth);
        heapTable.setPrefWidth(normalWidth);
        for (TableColumn<Map.Entry<Integer, Value>, String> tableColumn : heapTableColumns){
            tableColumn.setPrefWidth(normalWidth / heapTableColumns.length);
        }

        out.setPrefWidth(normalWidth);
        fileTable.setPrefWidth(normalWidth);
        programStateIdentifiers.setPrefWidth(normalWidth);
        symbolTable.setPrefWidth(normalWidth);
        for (TableColumn<Map.Entry<String, Value>, String> tableColumn : symbolTableColumns){
            tableColumn.setPrefWidth(normalWidth / symbolTableColumns.length);
        }

        runButton.setPrefWidth(normalWidth);
        executionStack.setPrefWidth(normalWidth);
    }

    private void resizeHeight(double stageHeight){
        final int entriesPerColumn = (int) Arrays.stream(lines).count();
        double normalHeight = (stageHeight - (entriesPerColumn - 1) * boxSpacing) / entriesPerColumn;
        for (HBox line : lines){
            line.setPrefHeight(normalHeight);
        }
    }


    private void setUpExamples(){
        int length = IStatement.examples.length;
        String filename = "";
        IStatement[] examples = IStatement.examples;
        controllers = new Controller[length];
        Repository[] repositories = new Repository[length];
        ProgramState[] programStates = new ProgramState[length];
        MyStack<IStatement>[] executeStacks = new MyStack[length];

        for (int i = 0; i < length; i++){
            ProgramState emptyProgramState = new ProgramState();
            MyStack<IStatement> emptyExecuteStack = new MyStack<>();
            executeStacks[i] = (MyStack<IStatement>) emptyExecuteStack.push(examples[i]);
            programStates[i] = emptyProgramState.setExecutionStack(executeStacks[i]);
            repositories[i] = new Repository(programStates[i], filename);
            controllers[i] = new Controller(repositories[i]);
        }
    }

    private void setUpExecutionStack(){
        executionStack = new ListView<>();
    }

    private void runButton(){
        if (isDone || currentController == null){
            int currentSelection = getCurrentSelectionIndex();
            if (currentSelection == -1){
                Alert alert = new Alert(Alert.AlertType.ERROR, "nothing is selected!");
                alert.showAndWait();
                return;
            }

            if (ranPrograms.contains(currentSelection)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "program has already been ran!");
                alert.showAndWait();
                return;
            }

            currentController = controllers[currentSelection];
            ranPrograms.add(currentSelection);
            isDone = false;
            try {
                currentController.typeCheck();
            }
            catch (Exception exception){
                Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());
                alert.showAndWait();
                currentController = null;
                return;
            }
            currentController.createExecutor();
            updateView();
            return;
        }
        try {
            MyIHeap<Integer, Value> heap = currentController.getHeap();
            currentController.oneStep();
            updateView();
            List<ProgramState> programList = currentController.removeCompletedPrograms();
            currentController.setProgramList(programList);
            heap.setValues(currentController.safeGarbageCollector(programList, heap.getContent()));
            if (programList.size() == 0){
                isDone = true;
                currentController.destroyExecutor();
            }
        }
        catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());
            alert.showAndWait();
            currentController.destroyExecutor();
            currentController = null;
        }
    }

    private void setUpRunButton(){
        runButton = new Button("Run One Step");
        runButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
            runButton()
        );
    }

    private void setUpNumberOfProgramStates() {
        numberOfProgramStates = new TextField();
        numberOfProgramStates.setDisable(true);
    }

    private void setUpProgramStateIdentifiers() {
        programStateIdentifiers = new ListView<>();
        programStateIdentifiers.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (programStateIdentifiers.getItems().size() == 0)
                return;
            ObservableList<Integer> selectedIndices = programStateIdentifiers.getSelectionModel().getSelectedIndices();
            int selectedIndex;
            if (selectedIndices.size() == 0)
                selectedIndex = 0;
            else
                selectedIndex = programStateIdentifiers.getSelectionModel().getSelectedIndices().get(0);
            Integer ID = programStateIdentifiers.getItems().get(selectedIndex);
            updateExecutionStack(ID);
            updateSymbolTable(ID);
        });

    }

    private void setUpFileTable() {
        fileTable = new ListView<>();
    }

    private void setUpOut() {
        out = new ListView<>();
    }

    private void setUpProgramStatesPopup(){
        IStatement[] examples = IStatement.examples;
        final double currentHeight = 500;
        final double currentWidth = 500;

        programStateList = new ListView<>();
        var allPrograms = FXCollections.observableArrayList(examples);
        programStateList.setItems(allPrograms);

        Stage newStage = new Stage();
        newStage.setTitle("All Programs");

        BorderPane root = new BorderPane();
        HBox layout = new HBox();
        layout.getChildren().add(programStateList);
        root.setTop(layout);

        Scene newScene = new Scene(root, currentWidth, currentHeight);
        newScene.widthProperty().addListener((observable, oldValue, newValue) -> resizeSecondaryWindowWidth((Double) newValue));
        newScene.heightProperty().addListener((observable, oldValue, newValue) -> resizeSecondaryWindowHeight((Double) newValue));
        newStage.setScene(newScene);
        resizeSecondaryWindowHeight(currentHeight);
        resizeSecondaryWindowWidth(currentWidth);
        newStage.show();
    }

    private void resizeSecondaryWindowHeight(double newHeight){
        programStateList.setPrefHeight(newHeight);
    }

    private void resizeSecondaryWindowWidth(double newWidth){
        programStateList.setPrefWidth(newWidth);
    }

    private void setUpHeap(){
        heapTable = new TableView<>();
        heapTableColumns=  new TableColumn[2];
        heapTableColumns[0] = new TableColumn<>("address");
        heapTableColumns[0].setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getKey().toString()));

        heapTableColumns[1] = new TableColumn<>("heap");
        heapTableColumns[1].setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().toString()));

        heapTable.getColumns().addAll(heapTableColumns);
    }

    private void setUpSymbolTable(){
        symbolTable = new TableView<>();
        symbolTableColumns = new TableColumn[2];

        symbolTableColumns[0] = new TableColumn<>("symbol");
        symbolTableColumns[0].setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getKey()));

        symbolTableColumns[1] = new TableColumn<>("value");
        symbolTableColumns[1].setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().toString()));

        symbolTable.getColumns().addAll(symbolTableColumns);
    }

    private int getCurrentSelectionIndex(){
        if (programStateList.getSelectionModel().getSelectedIndices().size() == 0)
            return -1;
        return programStateList.getSelectionModel().getSelectedIndices().get(0);
    }

    private ProgramState getProgramStateByID(int ID){
        if (currentController == null)
            return null;
        return currentController.getProgramStateByID(ID);
    }

    private void updateExecutionStack(int ID){
        executionStack.getItems().clear();
        ProgramState programState = getProgramStateByID(ID);
        MyIStack<IStatement> executionStack;
        if (programState == null)
            executionStack = new MyStack<>();
        else
            executionStack = programState.getExecutionStack();
        for (IStatement statement : executionStack.getItems()){
            this.executionStack.getItems().add(statement);
        }
    }

    private void updateSymbolTable(int ID){
        symbolTable.getItems().clear();
        ProgramState programState = getProgramStateByID(ID);
        MyIDictionary<String, Value> symTable;
        if (programState == null)
            symTable = new MyDictionary<>();
        else
            symTable = programState.getSymbolTable();

        ObservableList<Map.Entry<String, Value>> items = FXCollections.observableArrayList(symTable.getContent().entrySet());
        symbolTable.getItems().addAll(items);
    }

    private void updateOut(){
        out.getItems().clear();
        List<Value> outContents;
        if (currentController == null)
            outContents = new ArrayList<>();
        else
            outContents = currentController.getOut().getContents();

        for (Value outValue: outContents){
            out.getItems().add(outValue);
        }
    }

    private void updateHeap(){
        heapTable.getItems().clear();
        Map<Integer, Value> heapContents;
        if (currentController == null)
            heapContents = new HashMap<>();
        else
            heapContents = currentController.getHeap().getContent();

        ObservableList<Map.Entry<Integer, Value>> items = FXCollections.observableArrayList(heapContents.entrySet());
        heapTable.getItems().addAll(items);
    }

    private void updateNumberOfProgramStates(){
        numberOfProgramStates.clear();
        int numberOfPrograms;
        if (currentController == null)
            numberOfPrograms = 0;
        else
            numberOfPrograms = currentController.getNumberOfPrograms();
        numberOfProgramStates.setText(Integer.toString(numberOfPrograms));
    }

    private void updateFileTable(){
        fileTable.getItems().clear();

        Set<StringValue> files;
        if (currentController == null)
            files = new HashSet<>();
        else
            files = currentController.getFileTable();
        fileTable.getItems().addAll(files);
    }

    private void updateProgramStateIdentifiers(){
        programStateIdentifiers.getItems().clear();
        List<Integer> programStates;
        if (currentController == null)
            programStates = new ArrayList<>();
        else
            programStates = currentController.getProgramStateIdentifiers();
        for (Integer identifier : programStates){
            programStateIdentifiers.getItems().add(identifier);
        }
    }

    private void updateView(){
        symbolTable.getItems().clear();
        executionStack.getItems().clear();
        updateOut();
        updateNumberOfProgramStates();
        updateHeap();
        updateFileTable();
        updateProgramStateIdentifiers();
    }
}

