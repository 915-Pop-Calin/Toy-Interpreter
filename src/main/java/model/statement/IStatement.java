package model.statement;

import model.exceptions.MyException;
import model.expression.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.ReferenceType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.ReferenceValue;
import model.value.StringValue;
import repository.ProgramState;

import java.util.List;

public interface IStatement {
    List<ProgramState> execute(ProgramState state) throws MyException;

    //region Examples
    IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", IntType.INTEGER),
            new CompoundStatement(new AssignStatement("v" , new ValueExpression(new IntValue(3))),
                    new PrintStatement(new VariableExpression("v"))));

    IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", IntType.INTEGER), new CompoundStatement
            (new VariableDeclarationStatement("b", IntType.INTEGER), new CompoundStatement(new AssignStatement("a",
                    new ArithmeticExpression(ArithmeticExpression.Operation.PLUS, new ValueExpression(new IntValue(2)),
                            new ArithmeticExpression(ArithmeticExpression.Operation.MULTIPLICATION ,new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                    new CompoundStatement(new AssignStatement("b",new ArithmeticExpression(ArithmeticExpression.Operation.PLUS,new VariableExpression("a"),
                            new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

    IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", BoolType.BOOL),
            new CompoundStatement(new VariableDeclarationStatement("v", IntType.INTEGER),
                    new CompoundStatement(new AssignStatement("a", new ValueExpression(BoolValue.toFrom(true))),
                            new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v",
                                    new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                    new PrintStatement(new VariableExpression("v"))))));

    IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf", StringType.STRING),
            new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                    new CompoundStatement(new OpenReadFile(new VariableExpression("varf")),
                            new CompoundStatement(new VariableDeclarationStatement("varc", IntType.INTEGER),
                                    new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"), new CompoundStatement(
                                            new PrintStatement(new VariableExpression("varc")), new CompoundStatement(
                                            new ReadFile(new VariableExpression("varf"), "varc"),
                                            new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                            new CloseReadFile(new VariableExpression("varf")))
                                    )
                                    ))))));

    IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("file1", StringType.STRING), new CompoundStatement(
            new AssignStatement("file1", new ValueExpression(new StringValue("test.in"))), new CompoundStatement(new VariableDeclarationStatement("file2",
            StringType.STRING), new CompoundStatement(new AssignStatement("file2", new ValueExpression(new StringValue("test.in"))), new CompoundStatement(new OpenReadFile(new VariableExpression("file1")),
            new OpenReadFile(new VariableExpression("file2")))))));

    IStatement ex6 = new CompoundStatement(new VariableDeclarationStatement("a", BoolType.BOOL), new CompoundStatement(
            new VariableDeclarationStatement("b", IntType.INTEGER), new CompoundStatement(new VariableDeclarationStatement("c", IntType.INTEGER),
            new CompoundStatement(new AssignStatement("a", new RelationalExpression(RelationalExpression.Operation.EQUALS, new VariableExpression("b"),
                    new VariableExpression("c"))), new PrintStatement(new VariableExpression("a"))))));

    IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
            new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(IntType.INTEGER))),
                            new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));

    IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
            new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(IntType.INTEGER))),
                            new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                            new PrintStatement(new ArithmeticExpression(ArithmeticExpression.Operation.PLUS,
                                                    new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))),
                                                    new ValueExpression(new IntValue(5)))))))));

    IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
            new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                            new CompoundStatement(new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                    new PrintStatement(new ArithmeticExpression(ArithmeticExpression.Operation.PLUS,
                                            new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

    IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
            new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(IntType.INTEGER))),
                            new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")))))))));

    IStatement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", IntType.INTEGER), new CompoundStatement(
            new AssignStatement("v", new ValueExpression(new IntValue(4))), new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
            new VariableExpression("v"), new ValueExpression(new IntValue(0))), new CompoundStatement(new PrintStatement(new VariableExpression("v")),
            new AssignStatement("v", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS, new VariableExpression("v"), new ValueExpression(
                    new IntValue(1))))))));

    IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
            new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                  new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                          new PrintStatement(new VariableExpression("v")))));

    IStatement ex13 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
            new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new ReferenceType(IntType.INTEGER))),
                            new CompoundStatement(new HeapAllocationStatement("v1", new VariableExpression("v")),
                                    new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new ReferenceType(new
                                            ReferenceType(IntType.INTEGER)))), new CompoundStatement(new HeapAllocationStatement(
                                                    "v2", new VariableExpression("v1")), new CompoundStatement(new HeapAllocationStatement(
                                                            "v", new ValueExpression(new IntValue(30))),
                                            new CompoundStatement(new VariableDeclarationStatement("v3", IntType.INTEGER),
                                                    new PrintStatement(new HeapReadingExpression(
                                                            new VariableExpression("v"))))
                                    ))
                                    )))));

    IStatement ex14 = new CompoundStatement(new VariableDeclarationStatement("x", IntType.INTEGER),
            new ForkStatement(new PrintStatement(new VariableExpression("x"))));
    //endregion



}
