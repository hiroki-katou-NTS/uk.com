package nts.uk.ctx.at.request.pub.application.reflect;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface ApplicationReflectPub {
	/**
	 * pub 申請反映Mgrクラス
	 * @param workId: 実行ID
	 * @param workAtr 実行区分
	 * @param workDate ・対象期間開始日 ・対象期間終了日
	 * @return True: 完了処理、False：　中断
	 */
	boolean applicationRellect(String workId, WorkReflectAtr workAtr, DatePeriod workDate);
}
