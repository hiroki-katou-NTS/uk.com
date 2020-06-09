package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;

/**
 * 勤続年数テーブル
 * @author masaaki_jinno
 *
 */
public class TestLengthServiceRepositoryFactory{
	
	static public LengthServiceRepository create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestLengthServiceRepository_1();
			
			default:
				return null;
		}
	}

}