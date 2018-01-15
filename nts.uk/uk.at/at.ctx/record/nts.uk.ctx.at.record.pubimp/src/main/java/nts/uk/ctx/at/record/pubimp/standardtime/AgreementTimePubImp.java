package nts.uk.ctx.at.record.pubimp.standardtime;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.standardtime.AgreementTimeDto;
import nts.uk.ctx.at.record.pub.standardtime.AgreementTimePub;

@Stateless
public class AgreementTimePubImp implements AgreementTimePub{

	@Override
	public AgreementTimeDto getAgreementTime(String sID, GeneralDate startDate, GeneralDate endDate) {
		AgreementTimeDto agreementTimeDto = new AgreementTimeDto(Long.valueOf(0));
		return agreementTimeDto;
	}

}
