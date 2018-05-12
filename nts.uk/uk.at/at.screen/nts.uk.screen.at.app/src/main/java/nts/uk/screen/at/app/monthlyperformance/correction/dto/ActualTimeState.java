package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActualTimeState {
	/**
	 * 過去
	 */
	Past(0),
	/**
	 * 当月
	 */
	CurrentMonth(1),
	/**
	 * 未来
	 */
	Future(2);
	
	public final int value;
}
