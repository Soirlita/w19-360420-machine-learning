import java.util.List;
import java.io.FileNotFoundException;
import java.util.Arrays;



public class kNNMain{

  public static void main(String... args) throws FileNotFoundException{

    // TASK 1: Use command line arguments to point DataSet.readDataSet method to
    // the desired file. Choose a given DataPoint, and print its features and label
	List<DataPoint> excel = DataSet.readDataSet(args[0]); 
		DataPoint dp = excel.get(0); // 0 = Index of the first DataPoint in our List
		double [] dp_array = dp.x; // x is the array of datapoint we chose
		double [] dp_features = Arrays.copyOfRange(dp_array,1,dp_array.length);
		System.out.println("*** TASK 1 ***");
		System.out.println("	Features of the first DataPoint: " + Arrays.toString(dp_features));
		System.out.println("	Label of the first DataPoint: " + dp.label);

    //TASK 2:Use the DataSet class to split the fullDataSet into Training and Held Out Test Dataset
	double fractionTrainingSet = 0.3;
	double fractionTestSet = 1.0 - fractionTrainingSet;
	
	List<DataPoint> testSet = DataSet.getTestSet(excel, fractionTestSet);
	List<DataPoint> trainingSet = DataSet.getTrainingSet(excel, fractionTrainingSet);
	System.out.println("*** TASK 2 ***");
	System.out.println("	// We split our data into trainingSet and testSet.");

	System.out.println("In our testSet: ");
	DataSet.printLabelFrequencies(testSet);
	System.out.println("In our trainingSet: ");
	DataSet.printLabelFrequencies(trainingSet);

    // TASK 3: Use the DataSet class methods to plot the 2D data (binary and multi-class)


    // TASK 4: write a new method in DataSet.java which takes as arguments to DataPoint objects,
    // and returns the Euclidean distance between those two points (as a double)
    DataPoint dp1 = excel.get(0);
    DataPoint dp2 = excel.get(1);
    System.out.println("*** TASK 4 ***");
	System.out.println("	// We take two points from our now randomly shuffled (from TASK 2) List excel.");
	System.out.println("	Euclid distance between the two points: " + DataSet.distanceEuclid(dp1,dp2));


    // TASK 5: Use the KNNClassifier class to determine the k nearest neighbors to a given DataPoint,
    // and make a print a predicted target label
   	KNNClassifier model = new KNNClassifier(10); // I'm just choosing k = 10,
   	DataPoint target_dp = testSet.get(0); // let's choose the first point in the test set
   	String prediction = model.predict(trainingSet,target_dp);
   	System.out.println("*** TASK 5 ***");
   	System.out.println("	// We take a point from our testSet and try to predict the label using all the points in trainingSet.");
   	System.out.println("	Our prediction for the target DataPoint: " + prediction);
   	System.out.println("	The actual label for this target DataPoint: " + target_dp.label);


    // TASK 6: loop over the datapoints in the held out test set, and make predictions for Each
    // point based on nearest neighbors in training set. Calculate accuracy of model.
   	
   	int count = 0; 
   	for(int i=0; i<testSet.size(); i++){
   		target_dp = testSet.get(i);
   		prediction = model.predict(trainingSet, target_dp);
   		if ( prediction.equals(target_dp.label)) {
   			count++;
   		}
   	}
   	double accuracy = (double)count/(double)testSet.size();
   	System.out.println("*** TASK 6 ***");
   	System.out.println("	// We go through all DataPoints in our List testSet, predict each and see how many we got right.");
   	System.out.println("	Accuracy = " + accuracy);
  
  // TASK 7: ERROR ANALYSIS (Need to disable all other tasks before running this, because of variable names)
  	int turnOn = 1;
  	if (turnOn == 1){
	   	int k = 1;
	   	double[] accuracies = new double[1000];
	   	double[] precisions = new double[1000]; 
	   	double[] recalls = new double[1000]; 
	   	
	   	double[] false_positivesss = new double[1000]; // extra 
	   	double[] false_negativesss = new double[1000]; // extra 
	   	double[] true_positivesss = new double[1000]; // extra 
	   	double[] true_negativesss = new double[1000]; // extra 

		for(int iter=0; iter<1000; iter++){
			// do the entire thing 1000 times
			List<DataPoint> excel_ = DataSet.readDataSet(args[0]);
			
			List<DataPoint> testSet_ = DataSet.getTestSet(excel_, 0.3);
			List<DataPoint> trainingSet_ = DataSet.getTrainingSet(excel_, 0.7);

			KNNClassifier model_ = new KNNClassifier(k);

			int false_positive = 0;
			int false_negative = 0;
			int true_positive = 0;
			int true_negative = 0;

	   		for(int i=0; i<testSet_.size(); i++){
	   			DataPoint target_dp_ = testSet_.get(i);
	   			String prediction_ = model.predict(trainingSet_, target_dp_);
		   		if ( prediction_.equals(target_dp_.label)) {
		   			if ( prediction_.equals("benign")){
		   				true_positive++;
		   			}
					if ( prediction_.equals("malignant")){
		   				true_negative++;
		   			}
		   		}
		   		else if ( prediction_.equals("benign")){
		   			false_positive++;
		   		} else if ( prediction_.equals("malignant")){
		   			false_negative++;
		   		}
			}
			precisions[iter] = (double)true_positive/((double)true_positive+(double)false_positive);
			recalls[iter] = (double)true_positive/((double)true_positive+(double)false_negative);

	   		int count_ = true_positive + true_negative; 
	   		accuracies[iter] = (double)count_/(double)testSet_.size();
		   	false_positivesss[iter] = (double)false_positive; // extra 
		   	false_negativesss[iter] = (double)false_negative; // extra 
		   	true_positivesss[iter] = (double)true_positive; // extra 
		   	true_negativesss[iter] = (double)true_negative; // extra 

		} 
		System.out.println("*** ERROR ANALYSIS ***");
	   	System.out.println("	Mean accuracy = " + mean(accuracies));
	   	System.out.println("	standardDeviation of accuracies = " + standardDeviation(accuracies));
		System.out.println("	Mean precision = " + mean(precisions));
		System.out.println("	Mean recall = " + mean(recalls));
	}
 }

  public static double mean(double[] arr){
    /*
    Method that takes as an argument an array of doubles
    Returns: average of the elements of array, as a double
    */
    double sum = 0.0;

    for (double a : arr){
      sum += a;
    }
    return (double)sum/arr.length;
  }

  public static double standardDeviation(double[] arr){
    /*
    Method that takes as an argument an array of doubles
    Returns: standard deviation of the elements of array, as a double
    Dependencies: requires the *mean* method written above
    */
    double avg = mean(arr);
    double sum = 0.0;
    for (double a : arr){
      sum += Math.pow(a-avg,2);
    }
    return (double)sum/arr.length;
  }

}
