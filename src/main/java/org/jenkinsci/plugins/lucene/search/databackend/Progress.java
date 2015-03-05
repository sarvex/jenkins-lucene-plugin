package org.jenkinsci.plugins.lucene.search.databackend;

public class Progress {

    public enum ProgressState {
        PROCESSING, COMPLETE, COMPLETE_WITH_ERROR
    }

    private long endTime;
    private long startTime;

    private ProgressState state = ProgressState.PROCESSING;
    private Throwable reason;
    private int max;
    private int current;

    public void assertNoErrors() throws Throwable {
        if (getState() == ProgressState.COMPLETE_WITH_ERROR) {
            throw getReason();
        }
    }

    public Progress() {
        startTime = System.currentTimeMillis();
    }

    public void setError(Throwable reason) {
        state = ProgressState.COMPLETE_WITH_ERROR;
        this.reason = reason;
    }

    /**
     * Work complete. Does NOT imply success, only that no more processing will
     * be done
     */
    public void setFinished() {
        if (state == ProgressState.PROCESSING) {
            state = ProgressState.COMPLETE_WITH_ERROR;
        }
        endTime = System.currentTimeMillis();
    }

    /**
     * Work has been successfully completed. Current will be one less than max.
     */
    public void setSuccessfullyCompleted() {
        state = ProgressState.COMPLETE;
    }

    public long elapsedTime() {
        return endTime - startTime;
    }

    public Throwable getReason() {
        return reason;
    }

    public ProgressState getState() {
        return state;
    }

    public boolean isFinished() {
        return state == ProgressState.COMPLETE || state == ProgressState.COMPLETE_WITH_ERROR;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}