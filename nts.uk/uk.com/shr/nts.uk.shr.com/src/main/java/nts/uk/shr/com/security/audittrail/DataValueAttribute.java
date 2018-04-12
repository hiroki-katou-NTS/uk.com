package nts.uk.shr.com.security.audittrail;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

@RequiredArgsConstructor
public enum DataValueAttribute {

	STRING(0),
	COUNT(1),
	MONEY(2),
	TIME(3),
	
	;
	public final int value;
	
	public static DataValueAttribute of(int value) {
		return EnumAdaptor.valueOf(value, DataValueAttribute.class);
	}
}
