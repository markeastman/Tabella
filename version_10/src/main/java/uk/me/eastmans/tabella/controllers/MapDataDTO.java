package uk.me.eastmans.tabella.controllers;

/**
 * Created by meastman on 31/12/15.
 */
public class MapDataDTO {
    private float[] latLng;
    private String name;
    private MapMarkerStyle style;

    public float[] getLatLng() {
        return latLng;
    }

    public void setLatLng(float[] latLng) {
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MapMarkerStyle getStyle() {
        return style;
    }

    public void setStyle(MapMarkerStyle style) {
        this.style = style;
    }
}
