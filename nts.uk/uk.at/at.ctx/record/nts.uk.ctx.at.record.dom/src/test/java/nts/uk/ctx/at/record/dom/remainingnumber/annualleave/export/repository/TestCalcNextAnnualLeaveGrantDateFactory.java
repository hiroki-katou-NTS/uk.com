package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;

/**
 * 次回年休付与日を計算
 * @author masaaki_jinno
 *
 */
public class TestCalcNextAnnualLeaveGrantDateFactory {

	static public CalcNextAnnualLeaveGrantDate create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestCalcNextAnnualLeaveGrantDate_1();
			
			default:
				return null;
		}
	}

}
