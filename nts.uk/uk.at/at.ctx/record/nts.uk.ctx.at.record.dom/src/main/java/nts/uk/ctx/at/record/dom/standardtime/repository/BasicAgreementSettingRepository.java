package nts.uk.ctx.at.record.dom.standardtime.repository;

import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;

public interface BasicAgreementSettingRepository {
	
	void add(BasicAgreementSetting basicAgreementSetting);
	
	void update(BasicAgreementSetting basicAgreementSetting);
	
	void remove(String basicSettingId);
}
