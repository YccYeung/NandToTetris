import java.util.HashMap;

public class SymbolTable {

    private HashMap<String, Integer> symbolTable;
    
    public SymbolTable() {
        this.symbolTable = new HashMap<>();
    }

    public void initialization() {
        for (int i = 0; i <= 15; i++) {
            this.symbolTable.put("R" + i, i);
        }
        this.symbolTable.put("SP", 0);
        this.symbolTable.put("LCL", 1);
        this.symbolTable.put("ARG", 2);
        this.symbolTable.put("THIS", 3);
        this.symbolTable.put("THAT", 4);
        this.symbolTable.put("SCREEN", 16384);
        this.symbolTable.put("KBD", 24576);
    }

    public void addEntry(String symbol, int address) {
        this.symbolTable.putIfAbsent(symbol, address);
    }

    public boolean contains(String symbol) {
        return this.symbolTable.containsKey(symbol);
    }

    public int getAddress(String symbol) {
        return this.symbolTable.get(symbol);
    }
    
}
