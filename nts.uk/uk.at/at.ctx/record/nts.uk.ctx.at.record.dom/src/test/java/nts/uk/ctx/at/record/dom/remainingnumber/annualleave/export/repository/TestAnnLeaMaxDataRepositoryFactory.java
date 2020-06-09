package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;

/**
 * 年休上限データ
 * @author masaaki_jinno
 *
 */
public class TestAnnLeaMaxDataRepositoryFactory {

	static public AnnLeaMaxDataRepository create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestAnnLeaMaxDataRepository_1();
			
			default:
				return null;
		}
	}

}
