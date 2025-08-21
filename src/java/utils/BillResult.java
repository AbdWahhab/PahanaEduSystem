package utils;

public class BillResult {
    private BillResultStatus status;
    private String message;

    public BillResult(BillResultStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BillResultStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // ✅ Add this method
    public boolean isSuccess() {
        return this.status == BillResultStatus.SUCCESS;
    }
}
