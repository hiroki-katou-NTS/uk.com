package nts.uk.ctx.at.record.dom.anyperiod;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.correction.content.*;

@RequiredArgsConstructor
public class AnyPeriodCorrection implements DataCorrection {
	
	@Getter
	private final UserInfo targetUser;
	
	private final GeneralDate targetDate;

	private final String anyPeriodFrameCode;
	
	@Getter
	private final CorrectionAttr correctionAttr;
	
	@Getter
	private final ItemInfo correctedItem;
	
	@Getter
	private final int showOrder;

	@Override
	public TargetDataType getTargetDataType() {
		return TargetDataType.ANY_PERIOD_SUMMARY;
	}

	@Override
	public TargetDataKey getTargetDataKey() {
		return TargetDataKey.of(this.targetDate, anyPeriodFrameCode);
	}

}
