package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

/**
 * 年休設定
 * @author masaaki_jinno
 *
 */
public class TestAnnualPaidLeaveSettingFactory {

	/**
	 * 年休設定
	 * @param caseNo テストケース番号
	 * @return
	 */
	static public AnnualPaidLeaveSetting create(String caseNo){
	
		switch ( caseNo ){
			case "1": return TestAnnualPaidLeaveSetting_1.create();
			
			default:
				return null;
		}
	}
	
}
