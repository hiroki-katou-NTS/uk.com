package nts.uk.screen.at.ws.dailyperformance.correction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TypeError {
	DUPLICATE(0), 
	COUPLE(1), 
	CONTINUOUS(2), 
	ITEM28(3);
	public final int value;
}
