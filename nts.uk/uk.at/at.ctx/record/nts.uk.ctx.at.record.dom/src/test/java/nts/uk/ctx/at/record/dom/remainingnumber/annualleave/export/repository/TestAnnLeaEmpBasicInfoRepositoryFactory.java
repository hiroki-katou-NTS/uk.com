package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

/**
 * 年休社員基本情報
 * @author masaaki_jinno
 *
 */
public class TestAnnLeaEmpBasicInfoRepositoryFactory {

	/**
	 * 年休社員基本情報
	 * @param caseNo テストケース番号
	 * @return
	 */
	static public AnnLeaEmpBasicInfoRepository create(String caseNo){
	
		switch ( caseNo ){
			case "1": return new TestAnnLeaEmpBasicInfoRepository_1();
			
			default:
				return null;
		}
	}
	
}
