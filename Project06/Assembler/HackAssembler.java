import java.io.*;
import java.util.*;

public class HackAssembler {

    public static void main(String[] args) {
        
        try {

            String fileName = "Pong.asm";
            Parser parser = new Parser(fileName);
            Code code = new Code();
            SymbolTable symbolTable = new SymbolTable();
            symbolTable.initialization();
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName.replace(".asm", ".hack")));

            int romAddress = 0;

            while (parser.hasMoreLines()) {
                parser.advance();
                if (parser.instructionType() == Parser.Type.L_instruction) {
                    symbolTable.addEntry(parser.symbol(), romAddress);
                } else {
                    romAddress++;
                }
            }

            parser.initPointer();
            int ramAddress = 16;

            while (parser.hasMoreLines()) {

                parser.advance();

                switch (parser.instructionType()) {

                    case A_instruction:
                        String AInstruction = parser.symbol();

                        if (symbolTable.contains(AInstruction)) {
                            int temp = symbolTable.getAddress(AInstruction);
                            String textValue = String.valueOf(temp);
                            AInstruction = Code.AInstructionBinaryString(textValue);
                        } else {

                            if (Character.isDigit(AInstruction.charAt(0)) ) {
                                AInstruction = Code.AInstructionBinaryString(AInstruction);
                            } else {
                                symbolTable.addEntry(AInstruction, ramAddress);
                                ramAddress++;
                                int temp = symbolTable.getAddress(AInstruction);
                                String textValue = String.valueOf(temp);
                                AInstruction = Code.AInstructionBinaryString(textValue);
                            }
                        }

                        out.write(AInstruction);
                        break;
                    
                    case C_instruction:
                        String dest = code.dest(parser.dest());
                        String comp = code.comp(parser.comp());
                        String jump = code.jump(parser.jump());
                        out.write("111" + comp + dest + jump);
                        break;

                    case L_instruction:
                        continue;

                    default:
                        throw new RuntimeException();
                }
                out.newLine();
            }
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}