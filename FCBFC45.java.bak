package com;
import java.io.FileReader;
import weka.core.Instances;
import weka.core.Attribute;
import weka.classifiers.Evaluation;
import weka.attributeSelection.FCBFSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.SymmetricalUncertAttributeSetEval;
import weka.filters.Filter;
import weka.classifiers.trees.Id3;
import weka.classifiers.Evaluation;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.filters.unsupervised.attribute.NumericToNominal;
public class FCBFC45 {
	static double acc;
public static void fcbfc45(File file) throws Exception {
	DecimalFormat df = new DecimalFormat("#.###");
	FileReader reader = new FileReader(file.getPath()); 
    Instances instances = new Instances(reader);
	instances.setClassIndex(instances.numAttributes() - 1);

	NumericToNominal convert= new NumericToNominal();
    convert.setInputFormat(instances);
    instances = Filter.useFilter(instances, convert);
	instances.setClassIndex(instances.numAttributes() - 1);

	AttributeSelection filter = new AttributeSelection();
	SymmetricalUncertAttributeSetEval evaluator = new SymmetricalUncertAttributeSetEval(); 
	FCBFSearch search = new FCBFSearch ();
	filter.setEvaluator(evaluator); 
	filter.setSearch(search);
	filter.SelectAttributes(instances);
	Instances newData = filter.reduceDimensionality(instances);

	/*BufferedWriter writer = new BufferedWriter(new FileWriter("newdata.arff"));
	writer.write(newData.toString());
	writer.flush();
	writer.close();*/

	Id3 tree = new Id3();
	newData.setClassIndex(newData.numAttributes()-1);
    Evaluation eval = new Evaluation(newData);
	eval.crossValidateModel(tree,newData,10,new Random(0));
	tree.buildClassifier(newData);
	String results = tree.toString().trim();
	acc = eval.pctCorrect();
	FastCluster.area.append("FCFB C4.5 Classifier\n");
	FastCluster.area.append(eval.toMatrixString()+"\n");
	FastCluster.area.append("Correctly Classified Accuracy : "+ df.format(acc)+"\n\n");
}
}