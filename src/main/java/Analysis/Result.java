/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import GrafikObjects.DataLine;
import GrafikObjects.Plot;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martin
 */
public class Result implements ResultInterface{
    private List<Plot> plots = new ArrayList<>();
    private List<DataLine> data = new ArrayList<>();
    
    @Override
    public List<Plot> getPlots() {
        return plots;
    }

    @Override
    public void addPlot(Plot _plot) {
        plots.add(_plot);
    }

    @Override
    public List<DataLine> getData() {
        return data;
    }

    @Override
    public void addData(DataLine _data) {
        data.add(_data);
    }
    
}
