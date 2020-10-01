package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

public interface AgreementTimeOfWorkPlaceDomainService {
	
	List<String> add(AgreementTimeOfWorkPlace agreementTimeOfWorkPlace, BasicAgreementSetting basicAgreementSetting);
	
	List<String> update(BasicAgreementSetting basicAgreementSetting, AgreementTimeOfWorkPlace agreementTimeOfWorkPlace);
	
	void remove(String basicSettingId, String workPlaceId, int laborSystemAtr);

}
