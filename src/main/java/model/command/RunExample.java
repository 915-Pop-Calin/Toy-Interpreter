/*
package model.command;

import controller.Controller;
import model.exceptions.MyException;
import model.exceptions.MyIOException;

public class RunExample extends Command{
    private Controller controller;

    public RunExample(Integer key, String description, Controller controller){
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        }
        catch (MyIOException ioException){
            System.out.println(ioException.getMessage());
        } catch (MyException exception) {
            exception.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
*/
