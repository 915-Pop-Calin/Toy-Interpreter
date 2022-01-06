package repository;

import model.exceptions.MyException;

import java.util.List;

public interface IRepository {
    String getLogFilePath();
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> newProgramStates);
    IRepository setProgramState(ProgramState programState);
}
