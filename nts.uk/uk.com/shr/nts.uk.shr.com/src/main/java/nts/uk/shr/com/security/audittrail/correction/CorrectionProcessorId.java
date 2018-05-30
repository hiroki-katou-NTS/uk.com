package nts.uk.shr.com.security.audittrail.correction;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

@RequiredArgsConstructor
public enum CorrectionProcessorId {

	SAMPLE(0),
	
	;
	public final int value;
	
	public static CorrectionProcessorId of(int value) {
		return EnumAdaptor.valueOf(value, CorrectionProcessorId.class);
	}
}
