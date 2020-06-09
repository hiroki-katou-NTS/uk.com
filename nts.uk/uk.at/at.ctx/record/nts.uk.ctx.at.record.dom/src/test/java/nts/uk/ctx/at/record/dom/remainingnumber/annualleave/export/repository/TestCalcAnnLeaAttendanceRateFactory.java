package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CalcAnnLeaAttendanceRate;

public class TestCalcAnnLeaAttendanceRateFactory {
	static public CalcAnnLeaAttendanceRate create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestCalcAnnLeaAttendanceRate_1();
			
			default:
				return null;
		}
	}
}
