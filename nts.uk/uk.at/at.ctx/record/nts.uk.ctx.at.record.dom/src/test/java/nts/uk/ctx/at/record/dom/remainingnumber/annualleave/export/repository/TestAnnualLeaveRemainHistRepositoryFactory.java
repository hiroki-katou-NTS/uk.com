package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainHistRepository;

/**
 * 年休付与残数履歴データ
 * @author masaaki_jinno
 *
 */
public class TestAnnualLeaveRemainHistRepositoryFactory {

	static public AnnualLeaveRemainHistRepository create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestAnnualLeaveRemainHistRepository_1();
			
			default:
				return null;
		}
	}
}
