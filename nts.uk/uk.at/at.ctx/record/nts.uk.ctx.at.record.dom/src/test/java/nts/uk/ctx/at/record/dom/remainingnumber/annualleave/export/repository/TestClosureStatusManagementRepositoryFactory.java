package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;

/**
 * 締め状態管理
 * @author masaaki_jinno
 *
 */
public class TestClosureStatusManagementRepositoryFactory {

	static public ClosureStatusManagementRepository create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestClosureStatusManagementRepository_1();
			
			default:
				return null;
		}
	}
}
