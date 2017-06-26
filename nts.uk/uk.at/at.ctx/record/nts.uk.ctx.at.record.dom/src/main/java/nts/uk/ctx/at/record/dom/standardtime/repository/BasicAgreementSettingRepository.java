package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;

public interface BasicAgreementSettingRepository {
	
	Optional<BasicAgreementSetting> find(String basicSettingId);
	
	void add(BasicAgreementSetting basicAgreementSetting);
	
	void update(BasicAgreementSetting basicAgreementSetting);
	
	void remove(String basicSettingId);
}
