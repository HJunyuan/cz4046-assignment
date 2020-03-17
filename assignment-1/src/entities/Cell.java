package entities;

public class Cell {
	private double utility;
	private Policy policy;
	
	public Cell() {
		this.utility = 0;
		this.policy = Policy.UP;
	}
	
	public Cell(double utility, Policy policy) {
		this.utility = utility;
		this.policy = policy;
	}
	
	public double getUtility() {
		return utility;
	}
	public void setUtility(double utility) {
		this.utility = utility;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	};
	
	
}
