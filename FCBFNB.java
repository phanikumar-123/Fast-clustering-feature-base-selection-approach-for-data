package com;
import java.io.FileReader;
import weka.core.Instances;
import weka.core.Attribute;
import weka.classifiers.Evaluation;
import weka.attributeSelection.FCBFSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.SymmetricalUncertAttributeSetEval;
import weka.filters.Filter;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.Evaluation;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;
import weka.attributeSelection.InfoGainAttributeEval;
public class FCBFNB {
	static double acc;
	static StringBuilder sb = new StringBuilder();
public static void fcbfNB(File file) throws Exception {
	sb.delete(0,sb.length());
	DecimalFormat df = new DecimalFormat("#.###");
	FileReader reader = new FileReader(file.getPath()); 
    Instances instances = new Instances(reader);
	instances.setClassIndex(instances.numAttributes() - 1);
	sb.append("FCBF Features Details\n");
	sb.append("Total Features : "+instances.numAttributes()+"\n");
	AttributeSelection filter = new AttributeSelection();
	SymmetricalUncertAttributeSetEval evaluator = new SymmetricalUncertAttributeSetEval(); 
	FCBFSearch search = new FCBFSearch ();
	filter.setEvaluator(evaluator); 
	filter.setSearch(search);
	filter.SelectAttributes(instances);
	Instances newData = filter.reduceDimensionality(instances);
	int array[] = filter.selectedAttributes();
	sb.append("Total Selected Features : "+array.length+"\n"); 
	for(int i=0;i<array.length;i++){
		String arr[] = instances.attribute(array[i]).toString().trim().split("\\s+");
		sb.append(arr[1]+" ");
	}
	sb.append("\n\n");

	NaiveBayes nb = new NaiveBayes();
	newData.setClassIndex(newData.numAttributes()-1);
    Evaluation eval = new Evaluation(newData);
	eval.crossValidateModel(nb,instances,40,new Random(0));
	nb.buildClassifier(newData);
	String results = nb.toString().trim();
	acc = eval.pctCorrect()/2;
	FastCluster.area.append("FCFB Naiye Bayes Classifier\n");
	FastCluster.area.append(eval.toMatrixString()+"\n");
	FastCluster.area.append("Correctly Classified Accuracy : "+ df.format(acc)+"\n\n");
}

}