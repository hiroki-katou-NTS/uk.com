package nts.uk.shr.pereg.app;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemValueType {

	STRING(1),
	NUMERIC(2),
	DATE(3),
	;
	public final int value; 
}
