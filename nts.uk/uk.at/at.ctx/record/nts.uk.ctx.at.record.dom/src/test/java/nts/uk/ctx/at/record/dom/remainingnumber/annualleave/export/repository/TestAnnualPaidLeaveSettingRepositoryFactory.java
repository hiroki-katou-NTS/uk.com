package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;

/**
 * 年休設定
 * @author masaaki_jinno
 *
 */
public class TestAnnualPaidLeaveSettingRepositoryFactory {

	static public AnnualPaidLeaveSettingRepository create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestAnnualPaidLeaveSettingRepository_1();
			
			default:
				return null;
		}
	}
	
}
