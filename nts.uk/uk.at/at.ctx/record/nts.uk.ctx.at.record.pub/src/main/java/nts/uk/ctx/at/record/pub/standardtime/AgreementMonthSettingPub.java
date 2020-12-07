package nts.uk.ctx.at.record.pub.standardtime;

import nts.arc.time.YearMonth;

public interface AgreementMonthSettingPub {
	/**
	 * Refactor5 [NO.708]社員と年月を指定して３６協定年月設定を取得する
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).月の勤怠計算.36協定実績.３６協定管理.特例設定.Export."[NO.708]社員と年月を指定して３６協定年月設定を取得する"
	 * @param employeeId
	 * @param yearMonth
	 * @return
	 */
	AgreementMonthSettingOutputExport getExport(String employeeId, YearMonth yearMonth);
}
