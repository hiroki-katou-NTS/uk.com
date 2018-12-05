package nts.uk.screen.at.app.dailyperformance.correction.flex.change;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageFlex {
	
	//・状態：正常
	Normal(0),
	
	//状態：翌月フレックスでない
	Not_Next_Month(1),
	
	//・状態：当月退職
	Retirement(2) ;
	
	public final int value;
}
