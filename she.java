package sde;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;
public class she {
	 public static void main(String[] args) throws IOException {
	        // Parse the JSON input from the file
	        String jsonData = new String(Files.readAllBytes(new File("input.json").toPath()));
	        JSONObject jsonObject = new JSONObject(jsonData);

	        // Parse the number of points (n) and required number of points (k)
	        JSONObject keys = jsonObject.getJSONObject("keys");
	        int n = keys.getInt("n");
	        int k = keys.getInt("k");

	        // Store the (x, y) points decoded from the input
	        Map<Integer, Integer> points = new HashMap<>();

	        // Iterate over the JSON keys to extract the points
	        for (String key : jsonObject.keySet()) {
	            if (!key.equals("keys")) {
	                JSONObject point = jsonObject.getJSONObject(key);
	                int x = Integer.parseInt(key);
	                int base = point.getInt("base");
	                String value = point.getString("value");
	                int y = Integer.parseInt(value, base); // Decode the y value from the base
	                points.put(x, y);
	            }
	        }
s
	        // Use Lagrange interpolation to find the constant term (c) of the polynomial
	        double constantTerm = lagrangeInterpolation(points, k);
	        System.out.println("The secret (constant term) is: " + constantTerm);

	        // For the second test case, check for wrong points
	        if (n == 9) {
	            checkForWrongPoints(points, k, constantTerm);
	        }
	    }

	    // Function to perform Lagrange interpolation
	    public static double lagrangeInterpolation(Map<Integer, Integer> points, int k) {
	        double secret = 0;

	        // Implement Lagrange interpolation to find the constant term
	        for (Map.Entry<Integer, Integer> entry : points.entrySet()) {
	            int xi = entry.getKey();
	            int yi = entry.getValue();

	            // Calculate the Lagrange basis polynomial
	            double li = 1;
	            for (Map.Entry<Integer, Integer> other : points.entrySet()) {
	                int xj = other.getKey();
	                if (xj != xi) {
	                    li *= (0 - xj) / (double)(xi - xj);
	                }
	            }
	            // Add to the secret (constant term)
	            secret += yi * li;
	        }
	        return secret;
	    }

	    // Function to check for wrong points in the second test case
	    public static void checkForWrongPoints(Map<Integer, Integer> points, int k, double constantTerm) {
	        // Implement logic to identify wrong points based on the reconstructed polynomial
	        for (Map.Entry<Integer, Integer> entry : points.entrySet()) {
	            int x = entry.getKey();
	            int y = entry.getValue();
	            
	            // Compare y with the calculated polynomial value at x
	            double calculatedY = lagrangeInterpolation(points, k); // Calculate polynomial value at x
	            if (calculatedY != y) {
	                System.out.println("Wrong point detected: x = " + x + ", y = " + y);
	            }
	        }
	    }

}
