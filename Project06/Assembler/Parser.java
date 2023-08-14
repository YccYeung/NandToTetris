import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Parser {

    private int pointer;
    private List<String> commands;
    private String thisCommand;
    
    public enum Type {
        A_instruction, C_instruction, L_instruction
    }

    public Parser(String inputFile){

        initPointer();
        commands = new ArrayList<>();
        String currentLine;

        try {

            BufferedReader in = new BufferedReader(new FileReader(inputFile));
            currentLine = in.readLine();
            while (currentLine != null) {
                currentLine = currentLine.replaceAll(" ", "");
                System.out.println(currentLine);

                if (currentLine.equals("") || currentLine.startsWith("//")) {
                    currentLine = in.readLine();
                    continue;
                } 

                String[] split = currentLine.split("//");
                commands.add(split[0]);
                currentLine = in.readLine();
            } 

            in.close();

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void initPointer() {
        pointer = -1;
    }

    public int getPointer() {
        return pointer;
    }

    public boolean hasMoreLines() {
        return pointer < commands.size() - 1;
    }

    public void advance() {
       pointer++;
       this.thisCommand = commands.get(pointer);
    }

    public Type instructionType() {

        if (this.thisCommand.startsWith("@")) {
            return Type.A_instruction;
        } else if (this.thisCommand.startsWith("(")) {
            return Type.L_instruction;
        }

        return Type.C_instruction;
    }

    public String symbol() {
        

        if (instructionType() == Type.L_instruction) {
            return this.thisCommand.replace("(", "").replace(")", "");
        } else if (instructionType() == Type.A_instruction) {
            return this.thisCommand.substring(1);
        }

        return "";

    }

    public String dest() {
        if (instructionType() == Type.C_instruction && this.thisCommand.contains("=")) {
            return this.thisCommand.split("=")[0];
        }

        return "";
    }

    public String comp() {
        if (instructionType() == Type.C_instruction && thisCommand.contains("=")) {
            return this.thisCommand.split("=")[1];
        } else if (this.thisCommand.contains(";")) {
            return this.thisCommand.split(";")[0];
        } else if (this.thisCommand.contains("=") && this.thisCommand.contains(";")) {
            int first = this.thisCommand.indexOf("=");
            int second = this.thisCommand.indexOf(";");
            return this.thisCommand.substring(first + 1, second);
        }

        return this.thisCommand;
    }

    public String jump() {

        if (thisCommand.contains(";")) {
            return thisCommand.split(";")[1];
        }
        return "";
    }
}
