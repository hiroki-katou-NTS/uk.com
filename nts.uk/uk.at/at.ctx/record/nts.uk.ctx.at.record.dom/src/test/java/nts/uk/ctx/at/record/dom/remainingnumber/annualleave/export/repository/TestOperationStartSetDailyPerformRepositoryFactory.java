package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;

/**
 * 日別実績の運用開始設定
 * @author masaaki_jinno
 *
 */
public class TestOperationStartSetDailyPerformRepositoryFactory {

	static public OperationStartSetDailyPerformRepository create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestOperationStartSetDailyPerformRepository_1();
			
			default:
				return null;
		}
	}
}
