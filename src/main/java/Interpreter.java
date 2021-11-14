import controller.Controller;
import model.adt.MyStack;
import model.command.ExitCommand;
import model.command.RunExample;
import model.statement.IStatement;
import model.value.IntValue;
import repository.ProgramState;
import repository.Repository;
import view.TextMenu;

public class Interpreter {
    public static void main(String[] args){

        ProgramState programState1 = new ProgramState();
        MyStack<IStatement> executeStack1 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack1 = (MyStack<IStatement>) executeStack1.push(IStatement.ex1);
        ProgramState actualState1 = programState1.setExecutionStack(actualExecuteStack1);
        Repository repository1 = new Repository(actualState1, "logs1.txt");
        Controller controller1 = new Controller(repository1);

        ProgramState programState2 = new ProgramState();
        MyStack<IStatement> executeStack2 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack2 = (MyStack<IStatement>) executeStack2.push(IStatement.ex2);
        ProgramState actualState2 = programState2.setExecutionStack(actualExecuteStack2);
        Repository repository2 = new Repository(actualState2, "logs2.txt");
        Controller controller2 = new Controller(repository2);

        ProgramState programState3 = new ProgramState();
        MyStack<IStatement> executeStack3 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack3 = (MyStack<IStatement>) executeStack3.push(IStatement.ex3);
        ProgramState actualState3 = programState3.setExecutionStack(actualExecuteStack3);
        Repository repository3 = new Repository(actualState3, "logs3.txt");
        Controller controller3 = new Controller(repository3);

        ProgramState programState4 = new ProgramState();
        MyStack<IStatement> executeStack4 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack4 = (MyStack<IStatement>) executeStack4.push(IStatement.ex4);
        ProgramState actualState4 = programState4.setExecutionStack(actualExecuteStack4);
        Repository repository4 = new Repository(actualState4, "logs4.txt");
        Controller controller4 = new Controller(repository4);

        ProgramState programState5 = new ProgramState();
        MyStack<IStatement> executeStack5 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack5 = (MyStack<IStatement>) executeStack5.push(IStatement.ex5);
        ProgramState actualState5 = programState5.setExecutionStack(actualExecuteStack5);
        Repository repository5 = new Repository(actualState5, "logs5.txt");
        Controller controller5 = new Controller(repository5);

        ProgramState programState6 = new ProgramState();
        MyStack<IStatement> executeStack6 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack6 = (MyStack<IStatement>) executeStack6.push(IStatement.ex6);
        ProgramState actualState6 = programState6.setExecutionStack(actualExecuteStack6);
        Repository repository6 = new Repository(actualState6, "logs6.txt");
        Controller controller6 = new Controller(repository6);

        ProgramState programState7 = new ProgramState();
        MyStack<IStatement> executeStack7 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack7 = (MyStack<IStatement>) executeStack7.push(IStatement.ex7);
        ProgramState actualState7 = programState7.setExecutionStack(actualExecuteStack7);
        Repository repository7 = new Repository(actualState7, "logs7.txt");
        Controller controller7 = new Controller(repository7);

        ProgramState programState8 = new ProgramState();
        MyStack<IStatement> executeStack8 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack8 = (MyStack<IStatement>) executeStack8.push(IStatement.ex8);
        ProgramState actualState8 = programState8.setExecutionStack(actualExecuteStack8);
        Repository repository8 = new Repository(actualState8, "logs8.txt");
        Controller controller8 = new Controller(repository8);

        ProgramState programState9 = new ProgramState();
        MyStack<IStatement> executeStack9 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack9 = (MyStack<IStatement>) executeStack9.push(IStatement.ex9);
        ProgramState actualState9 = programState9.setExecutionStack(actualExecuteStack9);
        Repository repository9 = new Repository(actualState9, "logs9.txt");
        Controller controller9 = new Controller(repository9);

        ProgramState programState10 = new ProgramState();
        MyStack<IStatement> executeStack10 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack10 = (MyStack<IStatement>) executeStack10.push(IStatement.ex10);
        ProgramState actualState10 = programState10.setExecutionStack(actualExecuteStack10);
        Repository repository10 = new Repository(actualState10, "logs10.txt");
        Controller controller10 = new Controller(repository10);

        ProgramState programState11 = new ProgramState();
        MyStack<IStatement> executeStack11 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack11 = (MyStack<IStatement>) executeStack11.push(IStatement.ex11);
        ProgramState actualState11 = programState11.setExecutionStack(actualExecuteStack11);
        Repository repository11 = new Repository(actualState11, "logs11.txt");
        Controller controller11 = new Controller(repository11);

        ProgramState programState12 = new ProgramState();
        MyStack<IStatement> executeStack12 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack12 = (MyStack<IStatement>) executeStack12.push(IStatement.ex12);
        ProgramState actualState12 = programState12.setExecutionStack(actualExecuteStack12);
        Repository repository12 = new Repository(actualState12, "logs12.txt");
        Controller controller12 = new Controller(repository12);

        ProgramState programState13 = new ProgramState();
        MyStack<IStatement> executeStack13 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack13 = (MyStack<IStatement>) executeStack13.push(IStatement.ex13);
        ProgramState actualState13 = programState13.setExecutionStack(actualExecuteStack13);
        Repository repository13 = new Repository(actualState13, "logs13.txt");
        Controller controller13 = new Controller(repository13);

        ProgramState programState14 = new ProgramState();
        MyStack<IStatement> executeStack14 = new MyStack<>();
        MyStack<IStatement> actualExecuteStack14 = (MyStack<IStatement>) executeStack14.push(IStatement.ex14);
        ProgramState actualState14 = programState14.setExecutionStack(actualExecuteStack14);
        Repository repository14 = new Repository(actualState14, "logs14.txt");
        Controller controller14 = new Controller(repository14);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand(0, "exit"));
        menu.addCommand(new RunExample(1, IStatement.ex1.toString(), controller1));
        menu.addCommand(new RunExample(2, IStatement.ex2.toString(), controller2));
        menu.addCommand(new RunExample(3, IStatement.ex3.toString(), controller3));
        menu.addCommand(new RunExample(4, IStatement.ex4.toString(), controller4));
        menu.addCommand(new RunExample(5, IStatement.ex5.toString(), controller5));
        menu.addCommand(new RunExample(6, IStatement.ex6.toString(), controller6));
        menu.addCommand(new RunExample(7, IStatement.ex7.toString(), controller7));
        menu.addCommand(new RunExample(8, IStatement.ex8.toString(), controller8));
        menu.addCommand(new RunExample(9, IStatement.ex9.toString(), controller9));
        menu.addCommand(new RunExample(10, IStatement.ex10.toString(), controller10));
        menu.addCommand(new RunExample(11, IStatement.ex11.toString(), controller11));
        menu.addCommand(new RunExample(12, IStatement.ex12.toString(), controller12));
        menu.addCommand(new RunExample(13, IStatement.ex13.toString(), controller13));
        menu.addCommand(new RunExample(14, IStatement.ex14.toString(), controller14));
        menu.show();
    }
}
