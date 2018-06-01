package nts.uk.ctx.at.record.dom.workrecord.managectualsituation;

import lombok.Data;
import nts.arc.time.GeneralDate;
/**
 * 日の実績の状況
 */
@Data
public class DailyActualSituation {
	/**
	 * 年月日: 年月日
	 */
	GeneralDate date;
	/**
	 * 日の実績が存在する
	 */
	boolean dailyAchievementsExist;
	/**
	 * 本人確認が完了している
	 */
	boolean identificationCompleted;
	/**
	 * エラーが0件である
	 */
	boolean dailyRecordError;
}
