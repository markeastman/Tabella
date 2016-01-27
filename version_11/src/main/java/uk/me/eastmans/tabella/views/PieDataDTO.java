package uk.me.eastmans.tabella.views;

/**
 * Created by meastman on 31/12/15.
 */
public class PieDataDTO {
    private long value;
    private String color;
    private String highlight;
    private String label;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
