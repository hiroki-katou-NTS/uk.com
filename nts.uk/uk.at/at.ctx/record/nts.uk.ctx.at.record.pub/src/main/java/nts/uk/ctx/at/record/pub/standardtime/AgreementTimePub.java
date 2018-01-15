package nts.uk.ctx.at.record.pub.standardtime;

import nts.arc.time.GeneralDate;

public interface AgreementTimePub {
	AgreementTimeDto getAgreementTime(String sID, GeneralDate startDate, GeneralDate endDate);
}
