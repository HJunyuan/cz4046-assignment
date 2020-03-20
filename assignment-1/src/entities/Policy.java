package entities;

public enum Policy {
	UP("\u2191"), DOWN("\u2193"), LEFT("\u2190"), RIGHT("\u2192");

	private String arrow;

	private Policy(String arrow) {
		this.arrow = arrow;
	}

	public String getSymbol() {
		return this.arrow;
	}
}
