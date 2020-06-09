package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;

public class TestInterimRemainRepositoryFactory {

	static public InterimRemainRepository create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestInterimRemainRepository_1();
			
			default:
				return null;
		}
	}
	
}
