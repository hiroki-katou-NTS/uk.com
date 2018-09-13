package nts.uk.ctx.at.record.pub.dailyprocess.scheduletime;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManagePerCompanySet;

/**
 * 実績以外から会社共通の設定を取得するためのpublic
 * @author keisuke_hoshina
 *
 */
public interface CompanyCommonSettingPub {
	public ManagePerCompanySet getCompanySet();
}
