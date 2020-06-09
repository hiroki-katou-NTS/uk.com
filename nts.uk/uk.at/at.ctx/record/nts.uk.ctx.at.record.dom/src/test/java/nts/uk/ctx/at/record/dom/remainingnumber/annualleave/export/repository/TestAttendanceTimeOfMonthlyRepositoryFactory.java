package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;

public class TestAttendanceTimeOfMonthlyRepositoryFactory {
	static public AttendanceTimeOfMonthlyRepository create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestAttendanceTimeOfMonthlyRepository_1();
			
			default:
				return null;
		}
	}

}
