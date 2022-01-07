package smilelinked.cooperationoutput;

public enum SmileCooperationOutputClassification {

	/** しない */
	DO_NOT(0),

	/** する */
	DO(1);
	
	public int value;
	
	private SmileCooperationOutputClassification (int value) {
		this.value = value;
	}
}
