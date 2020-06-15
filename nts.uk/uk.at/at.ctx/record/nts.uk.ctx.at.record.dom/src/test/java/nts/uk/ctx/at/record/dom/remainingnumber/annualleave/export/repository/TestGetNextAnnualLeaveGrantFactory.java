package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrant;

public class TestGetNextAnnualLeaveGrantFactory {
	
	static public GetNextAnnualLeaveGrant create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestGetNextAnnualLeaveGrant_1();
			
			default:
				return null;
		}
	}
}
