package nts.uk.ctx.at.record.pubimp.dailyprocess.scheduletime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.CommonCompanySettingForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManagePerCompanySet;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.CompanyCommonSettingPub;

/**
 * 
 * @author keisuke_hoshina
 *
 */
@Stateless
public class CompanyCommonSettingPubImpl implements CompanyCommonSettingPub{

	@Inject
	private CommonCompanySettingForCalc commonCompanySettingForCalc;
	
	@Override
	public ManagePerCompanySet getCompanySet() {
		return commonCompanySettingForCalc.getCompanySetting();
	}
	
}
