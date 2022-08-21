public class Category {
	private double weight;
	private double possiblePoints;
	private double earnedPoints;
	private double grade;
	
	public Category(double weight, double possiblePoints, double earnedPoints) {
		setWeight(weight);
		setPossiblePoints(possiblePoints);
		setEarnedPoints(earnedPoints);
		setGrade(getEarnedPoints() / getPossiblePoints());
	}
	public Category (double weight) {
		setWeight(weight);
	}
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		if (weight < 100) {
			this.weight = weight / 100.0;
		}
	}
	public double getPossiblePoints() {
		return possiblePoints;
	}
	public void setPossiblePoints(double possiblePoints) {
		this.possiblePoints = possiblePoints;
	}
	public double getEarnedPoints() {
		return earnedPoints;
	}
	public void setEarnedPoints(double earnedPoints) {
		// Error checking, earned points cannot exceed maximum possible points
		if (getPossiblePoints() >= earnedPoints) {
			this.earnedPoints = earnedPoints;
		}
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
	public String toString() {
		return String.format("Weight:\t%.1f%c\tGrade:\t%.1f", getWeight() * 100.0, '%', getGrade() * 100.0);
	}
}
