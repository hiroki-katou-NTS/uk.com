package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;

public class TestInterimRemainOffMonthProcessFactory {

	static public InterimRemainOffMonthProcess create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestInterimRemainOffMonthProcess_1();
			
			default:
				return null;
		}
	}
	
	
}
