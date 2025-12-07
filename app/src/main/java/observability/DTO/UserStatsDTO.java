package observability.DTO;

public class UserStatsDTO {
    public int readCount =0;
    public int writeCount =0;
    public int expensiveCount =0;

    public void increment(String action) {
        switch (action) {
            case "READ" -> readCount++;
            case "WRITE"-> writeCount++;
            case "EXPENSIVE" -> expensiveCount++;
        }
    }

    public int getReadCount() { return readCount; }
    public int getWriteCount() { return writeCount; }
    public int getExpensiveSearchCount() { return expensiveCount; }
}
