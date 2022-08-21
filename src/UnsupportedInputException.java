public class UnsupportedInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double negativeWeight;
	private String reason;
	
	public UnsupportedInputException(double negativeWeight, String reason) {
		setNegativeWeight(negativeWeight);
		setReason(reason);
	}

	public double getNegativeWeight() {
		return negativeWeight;
	}

	public void setNegativeWeight(double negativeWeight) {
		this.negativeWeight = negativeWeight;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return String.format("ERROR: %.1f -- %s Please restart the program and enter proper input.", getNegativeWeight(), getReason());
	}
}