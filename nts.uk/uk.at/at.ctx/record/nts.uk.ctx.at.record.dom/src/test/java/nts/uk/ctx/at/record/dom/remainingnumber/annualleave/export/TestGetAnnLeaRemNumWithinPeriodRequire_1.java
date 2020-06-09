package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriodRequire;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository.*;

/**
 * テスト用　期間中の年休残数を取得　Require クラス
 * @author masaaki_jinno
 *
 */
public class TestGetAnnLeaRemNumWithinPeriodRequire_1 extends GetAnnLeaRemNumWithinPeriodRequire{

	public TestGetAnnLeaRemNumWithinPeriodRequire_1(){ 
		
		/**
		 * 社員
		 */
		this.empEmployee = TestEmpEmployeeAdapterImplFactory.create("1");
		
		/**
		 * 年休設定
		 */
		this.annualPaidLeaveSet = TestAnnualPaidLeaveSettingRepositoryFactory.create("1");
		
		/**
		 * 年休付与テーブル設定
		 */
		this.yearHolidayRepo = TestYearHolidayRepositoryFactory.create("1");
		
		/**
		 * 年休社員基本情報
		 */
		this.annLeaEmpBasicInfoRepo = TestAnnLeaEmpBasicInfoRepositoryFactory.create("1");
		
		/**
		 * 勤続年数テーブル
		 */
		this.lengthServiceRepo = TestLengthServiceRepositoryFactory.create("1");
		
		/**
		 * 年休付与残数データ
		 */
		this.annLeaGrantRemDataRepo = TestAnnLeaGrantRemDataRepositoryFactory.create("1");
		
		/**
		 * 年休上限データ
		 */
		this.annLeaMaxDataRepo = TestAnnLeaMaxDataRepositoryFactory.create("1");
		
		/**
		 * 社員に対応する締め開始日を取得する
		 */
		this.getClosureStartForEmployee = TestGetClosureStartForEmployeeFactory.create("1");

		/**
		 * 次回年休付与日を計算
		 */
		this.calcNextAnnualLeaveGrantDate = TestCalcNextAnnualLeaveGrantDateFactory.create("1");
		
		/**
		 * 月次処理用の暫定残数管理データを作成する
		 */
		this.interimRemOffMonth = TestInterimRemainOffMonthProcessFactory.create("1");
		
		/**
		 * 暫定残数管理データ
		 */
		this.interimRemainRepo = TestInterimRemainRepositoryFactory.create("1");
		
		/**
		 * 暫定年休管理データ
		 */
		this.tmpAnnualLeaveMng = TestTmpAnnualHolidayMngRepositoryFactory.create("1");
		
		/**
		 * 締め状態管理
		 */
		this.closureSttMngRepo = TestClosureStatusManagementRepositoryFactory.create("1");
		
		/**
		 * 年休出勤率を計算する
		 */
		this.calcAnnLeaAttendanceRate = TestCalcAnnLeaAttendanceRateFactory.create("1");
		
		/**
		 * 年休付与テーブル
		 */
		this.grantYearHolidayRepo = TestGrantYearHolidayRepositoryFactory.create("1");
		
		/**
		 * 日別実績の運用開始設定
		 */
		this.operationStartSetRepo = TestOperationStartSetDailyPerformRepositoryFactory.create("1");
		
		/**
		 * 年休付与残数履歴データ
		 */
		this.annualLeaveRemainHistRepo = TestAnnualLeaveRemainHistRepositoryFactory.create("1");
	}
}

