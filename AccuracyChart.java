package com;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.util.ArrayList;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
public class AccuracyChart extends ApplicationFrame {
	String title;
public AccuracyChart(String t) {
	super(t);
	CategoryDataset dataset = createDataset();
    JFreeChart chart = createChart(dataset);
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    setContentPane(chartPanel);
}
public void windowClosing(WindowEvent we){
	this.setVisible(false);
}
private CategoryDataset createDataset() {
	
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	try{
		BufferedReader br = new BufferedReader(new FileReader("C:/acc/acc.txt"));
		String propose[] = br.readLine().split(",");
		dataset.addValue(Double.parseDouble(propose[0]),"Propose Accuracy","FCBFKNN");
		dataset.addValue(Double.parseDouble(propose[1]),"Propose Accuracy","FCBFNB");
		dataset.addValue(Double.parseDouble(propose[2]),"Propose Accuracy","MRMRKNN");
		dataset.addValue(Double.parseDouble(propose[3]),"Propose Accuracy","MRMRNB");
		br.close();

		br = new BufferedReader(new FileReader("C:/acc/acc1.txt"));
		String ext[] = br.readLine().split(",");
		dataset.addValue(Double.parseDouble(ext[0]),"Extension Accuracy","FCBFKNN");
		dataset.addValue(Double.parseDouble(ext[1]),"Extension Accuracy","FCBFNB");
		dataset.addValue(Double.parseDouble(ext[2]),"Extension Accuracy","MRMRKNN");
		dataset.addValue(Double.parseDouble(ext[3]),"Extension Accuracy","MRMRNB");
		br.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	return dataset;
}
private JFreeChart createChart(CategoryDataset dataset){
	JFreeChart chart = ChartFactory.createLineChart(title,"Service Name","Trust Value",dataset,PlotOrientation.VERTICAL,true,true,false);
	chart.setBackgroundPaint(Color.white);
	CategoryPlot plot = (CategoryPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.lightGray);
    plot.setRangeGridlinePaint(Color.white);
	NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    rangeAxis.setAutoRangeIncludesZero(true);
	LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
    renderer.setSeriesStroke(0, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,1.0f, new float[] {10.0f, 6.0f}, 0.0f));
    renderer.setSeriesStroke(1, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,1.0f, new float[] {6.0f, 6.0f}, 0.0f));
    renderer.setSeriesStroke(2, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,1.0f, new float[] {2.0f, 6.0f}, 0.0f));
    return chart;
}
}
