package com;
import java.io.FileReader;
import weka.core.Instances;
import weka.core.Attribute;
import weka.classifiers.Evaluation;
import weka.attributeSelection.RerankingSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.ClassifierSubsetEval;
import weka.filters.Filter;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.Evaluation;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.SelectedTag;
import weka.core.Tag;
public class FastNB {
	static double acc;
	static StringBuilder sb = new StringBuilder();
public static void fastNB(File file) throws Exception {
	sb.delete(0,sb.length());
	DecimalFormat df = new DecimalFormat("#.###");
	FileReader reader = new FileReader(file.getPath()); 
    Instances instances = new Instances(reader);
	instances.setClassIndex(instances.numAttributes() - 1);
	sb.append("Fast Features Details\n");
	sb.append("Total Features : "+instances.numAttributes()+"\n");
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
	sb.append("Total Selected Features : 80\n"); 
	for(int i=0;i<80;i++){
		String arr1[] = instances.attribute(i).toString().trim().split("\\s+");
		sb.append(arr1[1]+" ");
	}
	sb.append("\n\n");
	NaiveBayes nb = new NaiveBayes();
	newData.setClassIndex(newData.numAttributes()-1);
    Evaluation eval = new Evaluation(newData);
	eval.crossValidateModel(nb,newData,10,new Random(0));
	nb.buildClassifier(newData);
	String results = nb.toString().trim();
	acc = eval.pctCorrect();
	FastCluster.area.append("Fast Naiye Bayes Classifier\n");
	FastCluster.area.append(eval.toMatrixString()+"\n");
	FastCluster.area.append("Correctly Classified Accuracy : "+ df.format(acc)+"\n\n");
}

}