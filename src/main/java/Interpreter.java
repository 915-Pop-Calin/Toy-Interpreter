import controller.Controller;
import model.adt.MyStack;
import model.command.ExitCommand;
import model.command.RunExample;
import model.statement.IStatement;
import repository.ProgramState;
import repository.Repository;
import view.TextMenu;

public class Interpreter {
    public static void main(String[] args){

        int length = IStatement.examples.length;
        String startFilename = "LogFiles/logs", endFilename = ".txt";
        IStatement[] examples = IStatement.examples;
        Controller[] controllers = new Controller[length];
        Repository[] repositories = new Repository[length];
        ProgramState[] programStates = new ProgramState[length];
        MyStack<IStatement>[] executeStacks = new MyStack[length];

        for (int i = 0; i < length; i++){
            ProgramState emptyProgramState = new ProgramState();
            MyStack<IStatement> emptyExecuteStack = new MyStack<>();
            executeStacks[i] = (MyStack<IStatement>) emptyExecuteStack.push(examples[i]);
            programStates[i] = emptyProgramState.setExecutionStack(executeStacks[i]);
            String currentFilename = startFilename + (i+1) + endFilename;
            repositories[i] = new Repository(programStates[i], currentFilename);
            controllers[i] = new Controller(repositories[i]);
        }

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand(0, "exit"));
        for (int i = 0; i < length; i++){
            menu.addCommand(new RunExample(i + 1, examples[i].toString(), controllers[i]));
        }

        menu.show();
    }
}
