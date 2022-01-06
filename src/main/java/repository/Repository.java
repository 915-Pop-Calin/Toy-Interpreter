package repository;

import model.exceptions.MyException;
import model.exceptions.MyIOException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public final class Repository implements IRepository{
    private List<ProgramState> programStates;

    public String getLogFilePath() {
        return logFilePath;
    }

    private final String logFilePath;

    public Repository(ProgramState programState, String logFilePath){
        this.programStates = new LinkedList<>();
        programStates.add(programState);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getProgramList() {
        return programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> newProgramStates) {
        this.programStates = newProgramStates;
    }

    public IRepository setProgramState(ProgramState programState){
        return new Repository(programState, logFilePath);
    }

}
