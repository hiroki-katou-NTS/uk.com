package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;

public interface BasicAgreementSettingRepository {
	
	Optional<BasicAgreementSetting> find(String basicSettingId);
	
	void add(BasicAgreementSetting basicAgreementSetting);
	
	void add2(BasicAgreementSetting basicAgreementSetting);
	
	void updateForCompany(BasicAgreementSetting basicAgreementSetting);
	
	void update2(BasicAgreementSetting basicAgreementSetting);
	
	void remove(String basicSettingId);
}
