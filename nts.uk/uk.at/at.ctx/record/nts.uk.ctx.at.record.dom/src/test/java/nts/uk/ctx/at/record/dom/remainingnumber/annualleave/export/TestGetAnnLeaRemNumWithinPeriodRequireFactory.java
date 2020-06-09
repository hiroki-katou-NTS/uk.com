package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriodRequire;

/**
 * テスト用　期間中の年休残数を取得　Requireオブジェクト作成
 * @author masaaki_jinno
 *
 */
public class TestGetAnnLeaRemNumWithinPeriodRequireFactory {

	static public GetAnnLeaRemNumWithinPeriodRequire create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestGetAnnLeaRemNumWithinPeriodRequire_1();
			
			default:
				return null;
		}
	}
	
}
