package model.statement;

import model.adt.MyIDictionary;
import model.exceptions.MyException;
import model.expression.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.ReferenceValue;
import model.value.StringValue;
import repository.ProgramState;

import java.util.List;
import java.util.Vector;

public interface IStatement {
    List<ProgramState> execute(ProgramState state) throws MyException;
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;

    //region Examples
    IStatement[] examples = {new CompoundStatement(new VariableDeclarationStatement("v", IntType.INTEGER),
            new CompoundStatement(new AssignStatement("v" , new ValueExpression(new IntValue(3))),
                    new PrintStatement(new VariableExpression("v")))),

            new CompoundStatement(new VariableDeclarationStatement("a", IntType.INTEGER), new CompoundStatement
                    (new VariableDeclarationStatement("b", IntType.INTEGER), new CompoundStatement(new AssignStatement("a",
                            new ArithmeticExpression(ArithmeticExpression.Operation.PLUS, new ValueExpression(new IntValue(2)),
                                    new ArithmeticExpression(ArithmeticExpression.Operation.MULTIPLICATION ,new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                            new CompoundStatement(new AssignStatement("b",new ArithmeticExpression(ArithmeticExpression.Operation.PLUS,new VariableExpression("a"),
                                    new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b")))))),

            new CompoundStatement(new VariableDeclarationStatement("a", BoolType.BOOL),
                    new CompoundStatement(new VariableDeclarationStatement("v", IntType.INTEGER),
                            new CompoundStatement(new AssignStatement("a", new ValueExpression(BoolValue.toFrom(true))),
                                    new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v",
                                            new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                            new PrintStatement(new VariableExpression("v")))))),

            new CompoundStatement(new VariableDeclarationStatement("varf", StringType.STRING),
                    new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("varf")),
                                    new CompoundStatement(new VariableDeclarationStatement("varc", IntType.INTEGER),
                                            new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"), new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("varc")), new CompoundStatement(
                                                    new ReadFile(new VariableExpression("varf"), "varc"),
                                                    new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                            new CloseReadFile(new VariableExpression("varf")))
                                            )
                                            )))))),

            new CompoundStatement(new VariableDeclarationStatement("file1", StringType.STRING), new CompoundStatement(
                    new AssignStatement("file1", new ValueExpression(new StringValue("test.in"))), new CompoundStatement(new VariableDeclarationStatement("file2",
                    StringType.STRING), new CompoundStatement(new AssignStatement("file2", new ValueExpression(new StringValue("test.in"))), new CompoundStatement(new OpenReadFile(new VariableExpression("file1")),
                    new OpenReadFile(new VariableExpression("file2"))))))),

            new CompoundStatement(new VariableDeclarationStatement("a", BoolType.BOOL), new CompoundStatement(
                    new VariableDeclarationStatement("b", IntType.INTEGER), new CompoundStatement(new VariableDeclarationStatement("c", IntType.INTEGER),
                    new CompoundStatement(new AssignStatement("a", new RelationalExpression(RelationalExpression.Operation.EQUALS, new VariableExpression("b"),
                            new VariableExpression("c"))), new PrintStatement(new VariableExpression("a")))))),

            new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(IntType.INTEGER))),
                                    new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                            new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a"))))))),

            new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(IntType.INTEGER))),
                                    new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                            new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                                    new PrintStatement(new ArithmeticExpression(ArithmeticExpression.Operation.PLUS,
                                                            new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))),
                                                            new ValueExpression(new IntValue(5))))))))),

            new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                    new CompoundStatement(new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(new ArithmeticExpression(ArithmeticExpression.Operation.PLUS,
                                                    new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5)))))))),

            new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(IntType.INTEGER))),
                                    new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                            new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                    new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))))))))),

            new CompoundStatement(new VariableDeclarationStatement("v", IntType.INTEGER), new CompoundStatement(
                    new AssignStatement("v", new ValueExpression(new IntValue(4))), new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
                    new VariableExpression("v"), new ValueExpression(new IntValue(0))), new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                    new AssignStatement("v", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS, new VariableExpression("v"), new ValueExpression(
                            new IntValue(1)))))))),

            new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                    new PrintStatement(new VariableExpression("v"))))),

            new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(IntType.INTEGER)),
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
                                            ))))),

            new CompoundStatement(new VariableDeclarationStatement("v", IntType.INTEGER),
                    new CompoundStatement(new VariableDeclarationStatement("q", IntType.INTEGER),
                            new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(IntType.INTEGER)),
                                    new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                            new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(
                                                    new IntValue(22))), new CompoundStatement(new ForkStatement(
                                                    new CompoundStatement(new HeapWritingStatement("a",
                                                            new ValueExpression(new IntValue(30))),
                                                            new CompoundStatement(new AssignStatement("v",
                                                                    new ValueExpression(new IntValue(32))),
                                                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                            new CompoundStatement(new AssignStatement("q", new HeapReadingExpression(
                                                                                    new VariableExpression("a"))), new PrintStatement(new VariableExpression("q"))
                                                                            ))))),
                                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                            new CompoundStatement(new AssignStatement("q", new HeapReadingExpression(new VariableExpression("a"))),
                                                                    new PrintStatement(new VariableExpression("q")))))))
                            ))),

            new CompoundStatement(new VariableDeclarationStatement("v", StringType.STRING),
                    new CompoundStatement(new AssignStatement("v", new ValueExpression(new StringValue("test.in"))),
                            new CompoundStatement(new ForkStatement(new CompoundStatement(new VariableDeclarationStatement("u",
                                    StringType.STRING), new CompoundStatement(new AssignStatement("u", new ValueExpression(new StringValue("test.in"))),
                                    new OpenReadFile(new VariableExpression("u"))))),
                                    new OpenReadFile(new VariableExpression("v"))))),

            new CompoundStatement(new VariableDeclarationStatement("filename", StringType.STRING),
                    new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("file.in"))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                    new CompoundStatement(new VariableDeclarationStatement("number", IntType.INTEGER),
                                            new CompoundStatement(new ReadFile(new VariableExpression("filename"), "number"),
                                                    new CompoundStatement(new VariableDeclarationStatement("closeFilename", new ReferenceType(StringType.STRING)),
                                                            new CompoundStatement(new HeapAllocationStatement("closeFilename", new ValueExpression(new StringValue("file.in"))),
                                                                    new CompoundStatement(new CloseReadFile(new HeapReadingExpression(new VariableExpression("closeFilename"))),
                                                                            new CompoundStatement(new VariableDeclarationStatement("num", new ReferenceType(IntType.INTEGER)),
                                                                                    new CompoundStatement(new HeapAllocationStatement("num", new VariableExpression("number")),
                                                                                            new CompoundStatement(new ForkStatement(new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(0))),
                                                                                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("num"))),
                                                                                                            new HeapWritingStatement("num", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS,
                                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(1))))))),
                                                                                                    new CompoundStatement(new VariableDeclarationStatement("boolean", BoolType.BOOL),
                                                                                                            new IfStatement(new VariableExpression("boolean"), new PrintStatement(new VariableExpression("boolean")),
                                                                                                                    new NopStatement()))))))))))))),

            new CompoundStatement(new VariableDeclarationStatement("filename", IntType.INTEGER),
                    new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("file.in"))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                    new CompoundStatement(new VariableDeclarationStatement("number", IntType.INTEGER),
                                            new CompoundStatement(new ReadFile(new VariableExpression("filename"), "number"),
                                                    new CompoundStatement(new VariableDeclarationStatement("closeFilename", new ReferenceType(StringType.STRING)),
                                                            new CompoundStatement(new HeapAllocationStatement("closeFilename", new ValueExpression(new StringValue("file.in"))),
                                                                    new CompoundStatement(new CloseReadFile(new HeapReadingExpression(new VariableExpression("closeFilename"))),
                                                                            new CompoundStatement(new VariableDeclarationStatement("num", new ReferenceType(IntType.INTEGER)),
                                                                                    new CompoundStatement(new HeapAllocationStatement("num", new VariableExpression("number")),
                                                                                            new CompoundStatement(new ForkStatement(new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(0))),
                                                                                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("num"))),
                                                                                                            new HeapWritingStatement("num", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS,
                                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(1))))))),
                                                                                                    new CompoundStatement(new VariableDeclarationStatement("boolean", BoolType.BOOL),
                                                                                                            new IfStatement(new VariableExpression("boolean"), new PrintStatement(new VariableExpression("boolean")),
                                                                                                                    new NopStatement()))))))))))))),

            new CompoundStatement(new VariableDeclarationStatement("filename", IntType.INTEGER),
                    new CompoundStatement(new AssignStatement("filename", new ValueExpression(new IntValue(3))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                    new CompoundStatement(new VariableDeclarationStatement("number", IntType.INTEGER),
                                            new CompoundStatement(new ReadFile(new VariableExpression("filename"), "number"),
                                                    new CompoundStatement(new VariableDeclarationStatement("closeFilename", new ReferenceType(StringType.STRING)),
                                                            new CompoundStatement(new HeapAllocationStatement("closeFilename", new ValueExpression(new StringValue("file.in"))),
                                                                    new CompoundStatement(new CloseReadFile(new HeapReadingExpression(new VariableExpression("closeFilename"))),
                                                                            new CompoundStatement(new VariableDeclarationStatement("num", new ReferenceType(IntType.INTEGER)),
                                                                                    new CompoundStatement(new HeapAllocationStatement("num", new VariableExpression("number")),
                                                                                            new CompoundStatement(new ForkStatement(new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(0))),
                                                                                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("num"))),
                                                                                                            new HeapWritingStatement("num", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS,
                                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(1))))))),
                                                                                                    new CompoundStatement(new VariableDeclarationStatement("boolean", BoolType.BOOL),
                                                                                                            new IfStatement(new VariableExpression("boolean"), new PrintStatement(new VariableExpression("boolean")),
                                                                                                                    new NopStatement()))))))))))))),

            new CompoundStatement(new VariableDeclarationStatement("filename", StringType.STRING),
                    new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("file.in"))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                    new CompoundStatement(new VariableDeclarationStatement("number", StringType.STRING),
                                            new CompoundStatement(new ReadFile(new VariableExpression("filename"), "number"),
                                                    new CompoundStatement(new VariableDeclarationStatement("closeFilename", new ReferenceType(StringType.STRING)),
                                                            new CompoundStatement(new HeapAllocationStatement("closeFilename", new ValueExpression(new StringValue("file.in"))),
                                                                    new CompoundStatement(new CloseReadFile(new HeapReadingExpression(new VariableExpression("closeFilename"))),
                                                                            new CompoundStatement(new VariableDeclarationStatement("num", new ReferenceType(IntType.INTEGER)),
                                                                                    new CompoundStatement(new HeapAllocationStatement("num", new VariableExpression("number")),
                                                                                            new CompoundStatement(new ForkStatement(new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(0))),
                                                                                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("num"))),
                                                                                                            new HeapWritingStatement("num", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS,
                                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(1))))))),
                                                                                                    new CompoundStatement(new VariableDeclarationStatement("boolean", BoolType.BOOL),
                                                                                                            new IfStatement(new VariableExpression("boolean"), new PrintStatement(new VariableExpression("boolean")),
                                                                                                                    new NopStatement()))))))))))))),

            new CompoundStatement(new VariableDeclarationStatement("filename", StringType.STRING),
                    new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("file.in"))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                    new CompoundStatement(new VariableDeclarationStatement("number", IntType.INTEGER),
                                            new CompoundStatement(new ReadFile(new VariableExpression("filename"), "number"),
                                                    new CompoundStatement(new VariableDeclarationStatement("closeFilename", new ReferenceType(IntType.INTEGER)),
                                                            new CompoundStatement(new HeapAllocationStatement("closeFilename", new ValueExpression(new StringValue("file.in"))),
                                                                    new CompoundStatement(new CloseReadFile(new HeapReadingExpression(new VariableExpression("closeFilename"))),
                                                                            new CompoundStatement(new VariableDeclarationStatement("num", new ReferenceType(IntType.INTEGER)),
                                                                                    new CompoundStatement(new HeapAllocationStatement("num", new VariableExpression("number")),
                                                                                            new CompoundStatement(new ForkStatement(new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(0))),
                                                                                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("num"))),
                                                                                                            new HeapWritingStatement("num", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS,
                                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(1))))))),
                                                                                                    new CompoundStatement(new VariableDeclarationStatement("boolean", BoolType.BOOL),
                                                                                                            new IfStatement(new VariableExpression("boolean"), new PrintStatement(new VariableExpression("boolean")),
                                                                                                                    new NopStatement()))))))))))))),

            new CompoundStatement(new VariableDeclarationStatement("filename", StringType.STRING),
                    new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("file.in"))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                    new CompoundStatement(new VariableDeclarationStatement("number", IntType.INTEGER),
                                            new CompoundStatement(new ReadFile(new VariableExpression("filename"), "number"),
                                                    new CompoundStatement(new VariableDeclarationStatement("closeFilename", new ReferenceType(IntType.INTEGER)),
                                                            new CompoundStatement(new HeapAllocationStatement("closeFilename", new ValueExpression(new IntValue(7))),
                                                                    new CompoundStatement(new CloseReadFile(new HeapReadingExpression(new VariableExpression("closeFilename"))),
                                                                            new CompoundStatement(new VariableDeclarationStatement("num", new ReferenceType(IntType.INTEGER)),
                                                                                    new CompoundStatement(new HeapAllocationStatement("num", new VariableExpression("number")),
                                                                                            new CompoundStatement(new ForkStatement(new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(0))),
                                                                                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("num"))),
                                                                                                            new HeapWritingStatement("num", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS,
                                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(1))))))),
                                                                                                    new CompoundStatement(new VariableDeclarationStatement("boolean", BoolType.BOOL),
                                                                                                            new IfStatement(new VariableExpression("boolean"), new PrintStatement(new VariableExpression("boolean")),
                                                                                                                    new NopStatement()))))))))))))),

            new CompoundStatement(new VariableDeclarationStatement("filename", StringType.STRING),
                    new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("file.in"))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                    new CompoundStatement(new VariableDeclarationStatement("number", IntType.INTEGER),
                                            new CompoundStatement(new ReadFile(new VariableExpression("filename"), "number"),
                                                    new CompoundStatement(new VariableDeclarationStatement("closeFilename", new ReferenceType(StringType.STRING)),
                                                            new CompoundStatement(new HeapAllocationStatement("closeFilename", new ValueExpression(new StringValue("file.in"))),
                                                                    new CompoundStatement(new CloseReadFile(new HeapReadingExpression(new VariableExpression("closeFilename"))),
                                                                            new CompoundStatement(new VariableDeclarationStatement("num", new ReferenceType(StringType.STRING)),
                                                                                    new CompoundStatement(new HeapAllocationStatement("num", new ValueExpression(new StringValue("hmm today I will"))),
                                                                                            new CompoundStatement(new ForkStatement(new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(0))),
                                                                                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("num"))),
                                                                                                            new HeapWritingStatement("num", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS,
                                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(1))))))),
                                                                                                    new CompoundStatement(new VariableDeclarationStatement("boolean", BoolType.BOOL),
                                                                                                            new IfStatement(new VariableExpression("boolean"), new PrintStatement(new VariableExpression("boolean")),
                                                                                                                    new NopStatement()))))))))))))),

            new CompoundStatement(new VariableDeclarationStatement("filename", StringType.STRING),
                    new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("file.in"))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                    new CompoundStatement(new VariableDeclarationStatement("number", IntType.INTEGER),
                                            new CompoundStatement(new ReadFile(new VariableExpression("filename"), "number"),
                                                    new CompoundStatement(new VariableDeclarationStatement("closeFilename", new ReferenceType(StringType.STRING)),
                                                            new CompoundStatement(new HeapAllocationStatement("closeFilename", new ValueExpression(new StringValue("file.in"))),
                                                                    new CompoundStatement(new CloseReadFile(new HeapReadingExpression(new VariableExpression("closeFilename"))),
                                                                            new CompoundStatement(new VariableDeclarationStatement("num", new ReferenceType(IntType.INTEGER)),
                                                                                    new CompoundStatement(new HeapAllocationStatement("num", new VariableExpression("number")),
                                                                                            new CompoundStatement(new ForkStatement(new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(0))),
                                                                                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("num"))),
                                                                                                            new HeapWritingStatement("num", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS,
                                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(1))))))),
                                                                                                    new CompoundStatement(new VariableDeclarationStatement("boolean", IntType.INTEGER),
                                                                                                            new IfStatement(new VariableExpression("boolean"), new PrintStatement(new VariableExpression("boolean")),
                                                                                                                    new NopStatement()))))))))))))),

            new CompoundStatement(new VariableDeclarationStatement("filename", StringType.STRING),
                    new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("file.in"))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                    new CompoundStatement(new VariableDeclarationStatement("number", IntType.INTEGER),
                                            new CompoundStatement(new ReadFile(new VariableExpression("filename"), "number"),
                                                    new CompoundStatement(new VariableDeclarationStatement("closeFilename", new ReferenceType(StringType.STRING)),
                                                            new CompoundStatement(new HeapAllocationStatement("closeFilename", new ValueExpression(new StringValue("file.in"))),
                                                                    new CompoundStatement(new CloseReadFile(new HeapReadingExpression(new VariableExpression("closeFilename"))),
                                                                            new CompoundStatement(new VariableDeclarationStatement("num", new ReferenceType(IntType.INTEGER)),
                                                                                    new CompoundStatement(new HeapAllocationStatement("num", new VariableExpression("number")),
                                                                                            new CompoundStatement(new ForkStatement(new WhileStatement(new RelationalExpression(RelationalExpression.Operation.GREATER,
                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(0))),
                                                                                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("num"))),
                                                                                                            new HeapWritingStatement("num", new ValueExpression(new StringValue("calin")))))),
                                                                                                    new CompoundStatement(new VariableDeclarationStatement("boolean", BoolType.BOOL),
                                                                                                            new IfStatement(new VariableExpression("boolean"), new PrintStatement(new VariableExpression("boolean")),
                                                                                                                    new NopStatement()))))))))))))),

            new CompoundStatement(new VariableDeclarationStatement("filename", StringType.STRING),
                    new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("file.in"))),
                            new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                    new CompoundStatement(new VariableDeclarationStatement("number", IntType.INTEGER),
                                            new CompoundStatement(new ReadFile(new VariableExpression("filename"), "number"),
                                                    new CompoundStatement(new VariableDeclarationStatement("closeFilename", new ReferenceType(StringType.STRING)),
                                                            new CompoundStatement(new HeapAllocationStatement("closeFilename", new ValueExpression(new StringValue("file.in"))),
                                                                    new CompoundStatement(new CloseReadFile(new HeapReadingExpression(new VariableExpression("closeFilename"))),
                                                                            new CompoundStatement(new VariableDeclarationStatement("num", new ReferenceType(IntType.INTEGER)),
                                                                                    new CompoundStatement(new HeapAllocationStatement("num", new VariableExpression("number")),
                                                                                            new CompoundStatement(new ForkStatement(new WhileStatement(new HeapReadingExpression(new VariableExpression("num")),
                                                                                                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("num"))),
                                                                                                            new HeapWritingStatement("num", new ArithmeticExpression(ArithmeticExpression.Operation.MINUS,
                                                                                                                    new HeapReadingExpression(new VariableExpression("num")), new ValueExpression(new IntValue(1))))))),
                                                                                                    new CompoundStatement(new VariableDeclarationStatement("boolean", BoolType.BOOL),
                                                                                                            new IfStatement(new VariableExpression("boolean"), new PrintStatement(new VariableExpression("boolean")),
                                                                                                                    new NopStatement())))))))))))))
    };
    //endregion



}
