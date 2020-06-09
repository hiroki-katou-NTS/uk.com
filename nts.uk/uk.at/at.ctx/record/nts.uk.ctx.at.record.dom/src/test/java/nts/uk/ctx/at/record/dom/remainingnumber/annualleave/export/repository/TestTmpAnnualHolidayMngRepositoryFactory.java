package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;

/**
 * 暫定年休管理データ
 * @author masaaki_jinno
 *
 */
public class TestTmpAnnualHolidayMngRepositoryFactory {
	static public TmpAnnualHolidayMngRepository create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestTmpAnnualHolidayMngRepository_1();
			
			default:
				return null;
		}
	}

}

