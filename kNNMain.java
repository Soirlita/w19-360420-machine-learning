import java.util.List;
import java.io.FileNotFoundException;
import java.util.Arrays;

//Its on GitHub

public class kNNMain{

  public static void main(String... args) throws FileNotFoundException
  {

    // TASK 1: Use command line arguments to point DataSet.readDataSet method to
    // the desired file. Choose a given DataPoint, and print its features and label
	
	List<DataPoint> excel = DataSet.readDataSet(args[0]); 
	DataPoint dp = excel.get(0); // 0 = First data point list
	double [] features = dp.x; // x is the array of datapoint we chose
	double [] array = Arrays.copyOfRange(features,1,features.length);
	System.out.println(Arrays.toString(array));
	

    //TASK 2:Use the DataSet class to split the fullDataSet into Training and Held Out Test Dataset
	
	double fractionTrainingSet = 0.2;
	double fractionTestSet = 1.0 - fractionTrainingSet;
	
	List<DataPoint> testSet = DataSet.getTestSet(excel, fractionTestSet);
	List<DataPoint> trainingSet = DataSet.getTrainingSet(excel, fractionTrainingSet);


    // TASK 4: write a new method in DataSet.java which takes as arguments to DataPoint objects,
    // and returns the Euclidean distance between those two points (as a double)



    // TASK 5: Use the KNNClassifier class to determine the k nearest neighbors to a given DataPoint,
    // and make a print a predicted target label



    // TASK 6: loop over the datapoints in the held out test set, and make predictions for Each
    // point based on nearest neighbors in training set. Calculate accuracy of model.

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
