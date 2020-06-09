package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

/**
 * 年休付与残数データ
 * @author masaaki_jinno
 *
 */
public class TestAnnLeaGrantRemDataRepositoryFactory {
	
	/**
	 * 年休付与残数データ
	 * @param caseNo テストケース番号
	 * @return
	 */
	static public AnnLeaGrantRemDataRepository create(String caseNo){
	
		switch ( caseNo ){
			case "1": return new TestAnnLeaGrantRemDataRepository_1();
			
			default:
				return null;
		}
	}
}
