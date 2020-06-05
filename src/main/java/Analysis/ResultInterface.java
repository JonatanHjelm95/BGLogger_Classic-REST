/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import GrafikObjects.*;
import java.util.List;

/**
 *
 * @author Martin
 */
public interface ResultInterface {
    public List<Plot> getPlots();
    public void addPlot(Plot _plot);
    public List<DataLine> getData();
    public void addData(DataLine _data);
    
}
