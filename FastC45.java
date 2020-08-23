package com;
import java.io.FileReader;
import weka.core.Instances;
import weka.core.Attribute;
import weka.classifiers.Evaluation;
import weka.attributeSelection.RerankingSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.ClassifierSubsetEval;
import weka.filters.Filter;
import weka.classifiers.trees.Id3;
import weka.classifiers.Evaluation;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.SelectedTag;
import weka.core.Tag;
import weka.filters.unsupervised.attribute.NumericToNominal;
public class FastC45 {
	static double acc;
	
public static void fastc45(File file) throws Exception {
	DecimalFormat df = new DecimalFormat("#.###");
	FileReader reader = new FileReader(file.getPath()); 
    Instances instances = new Instances(reader);
	instances.setClassIndex(instances.numAttributes() - 1);

	NumericToNominal convert= new NumericToNominal();
    convert.setInputFormat(instances);
    instances = Filter.useFilter(instances, convert);
	instances.setClassIndex(instances.numAttributes() - 1);

	AttributeSelection filter = new AttributeSelection();
	ClassifierSubsetEval evaluator = new ClassifierSubsetEval(); 
	RerankingSearch cluster = new RerankingSearch();
	Tag arr[] = cluster.TAGS_RERANK;
	cluster.setRerankMethod(new SelectedTag(2,arr));
	System.out.println(cluster.getRerankMethod().getSelectedTag().getID()+"===");
	filter.setEvaluator(evaluator); 
	filter.setSearch(cluster);
	filter.SelectAttributes(instances);
	Instances newData = filter.reduceDimensionality(instances);
	int array[] = filter.selectedAttributes();
	for(int i=0;i<array.length;i++){
		System.out.print(instances.attribute(array[i]).toString()+" ");
	}

	Id3 tree = new Id3();
	newData.setClassIndex(newData.numAttributes()-1);
    Evaluation eval = new Evaluation(newData);
	eval.crossValidateModel(tree,newData,10,new Random(0));
	tree.buildClassifier(newData);
	String results = tree.toString().trim();
	acc = eval.pctCorrect();
	FastCluster.area.append("Fast C4.5 Classifier\n");
	FastCluster.area.append(eval.toMatrixString()+"\n");
	FastCluster.area.append("Correctly Classified Accuracy : "+ df.format(acc)+"\n\n");
}
}