package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriodRequire;

/**
 * テスト用　期間中の年休残数を取得　Require クラス
 * @author masaaki_jinno
 *
 */
public class TestGetAnnLeaRemNumWithinPeriodRequire_1 extends GetAnnLeaRemNumWithinPeriodRequire{

	public TestGetAnnLeaRemNumWithinPeriodRequire_1(){
		
		// 社員
		this.empEmployee = TestEmpEmployeeAdapterImplFactory.create("1");
		
		// 年休設定
		this.annualPaidLeaveSet = TestAnnualPaidLeaveSettingRepositoryFactory.create("1");
		
		// 年休社員基本情報
		this.annLeaEmpBasicInfoRepo = TestAnnLeaEmpBasicInfoRepositoryFactory.create("1");
		
		
		
		
	}
}

