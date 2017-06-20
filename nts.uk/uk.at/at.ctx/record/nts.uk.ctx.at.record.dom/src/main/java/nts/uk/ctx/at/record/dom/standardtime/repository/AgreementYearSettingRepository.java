package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;

public interface AgreementYearSettingRepository {
	
	List<AgreementYearSetting> find(String agreementYearSetting);
	
}
