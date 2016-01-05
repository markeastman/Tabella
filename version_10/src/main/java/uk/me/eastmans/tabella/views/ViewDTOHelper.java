package uk.me.eastmans.tabella.views;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.BallotResult;

import java.util.List;

/**
 * Created by meastman on 05/01/16.
 */
public class ViewDTOHelper {

    static public String[] colors = { "#f56954", "#00a65a", "#f39c12", "#00c0ef", "#3c8dbc", "#d2d6de"};

    public static MapDataDTO[] convertToMapDataDTO(List<BallotResult> results) {
        MapDataDTO[] mapData = new MapDataDTO[results.size()];
        for (int i = 0; i < results.size(); i++) {
            BallotResult br = results.get(i);
            mapData[i] = convertToMapDataDTO(br);
        }
        return mapData;
    }

    public static MapDataDTO convertToMapDataDTO(BallotResult result) {
        MapDataDTO mapData = new MapDataDTO();
        mapData.setLatLng( new float[] {result.getLatitude(), result.getLongitude() } );
        mapData.setName(result.getBallot().getAnswers().get(result.getAnswerIndex()));
        MapMarkerStyle style = new MapMarkerStyle();
        style.setFill(colors[result.getAnswerIndex() % colors.length]);
        mapData.setStyle(style);
        return mapData;
    }

    public static PieDataDTO[] convertToPieData(Ballot b, long[] counts) {
        PieDataDTO[] pieData = new PieDataDTO[counts.length];
        for (int i = 0; i < pieData.length; i++) {
            pieData[i] = new PieDataDTO();
            pieData[i].setValue(counts[i]);
            pieData[i].setColor(colors[i % colors.length]);
            pieData[i].setHighlight(colors[i % colors.length]);
            pieData[i].setLabel(b.getAnswers().get(i));
        }
        return pieData;
    }

    public static String convertToJSONString(MapDataDTO dto)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            return mapper.writeValueAsString(dto);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
