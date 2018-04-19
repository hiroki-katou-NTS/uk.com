package uts.uk.file.at.app.export.dailyschedule;

public enum OutputConditionSetting {
	USE_CONDITION(0),
	
	NOT_USE_CONDITION(1);
	
	private final int outputSetting;

	private OutputConditionSetting(int outputSetting) {
		this.outputSetting = outputSetting;
	}
	
}
