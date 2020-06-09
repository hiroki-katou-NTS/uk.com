package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;

/**
 * 年休付与テーブル設定
 * @author masaaki_jinno
 *
 */
public class TestYearHolidayRepositoryFactory {
	static public YearHolidayRepository create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestYearHolidayRepository_1();
			
			default:
				return null;
		}
	}
	
	
}
