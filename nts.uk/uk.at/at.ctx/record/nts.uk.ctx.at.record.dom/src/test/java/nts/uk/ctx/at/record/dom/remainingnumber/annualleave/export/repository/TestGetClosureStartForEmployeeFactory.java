package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

public class TestGetClosureStartForEmployeeFactory {

	static public GetClosureStartForEmployee create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestGetClosureStartForEmployee_1();
			
			default:
				return null;
		}
	}
	
}
