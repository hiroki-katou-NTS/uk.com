package nts.uk.shr.com.security.audittrail.correction.processor;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * Define CorrectionLogProcessors here.
 */
@RequiredArgsConstructor
public enum CorrectionProcessorId {

	SAMPLE(0),
	
	DAILY(1),
	
	;
	public final int value;
	
	public static CorrectionProcessorId of(int value) {
		return EnumAdaptor.valueOf(value, CorrectionProcessorId.class);
	}
}
