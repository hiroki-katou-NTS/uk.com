package nts.uk.ctx.at.record.dom.byperiod;

import java.util.List;
import java.util.Optional;

/*
 * リポジトリ：任意期間別実績の勤怠時間
 * @author shuichi_ishida
 */
public interface AttendanceTimeOfAnyPeriodRepository {

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param frameCode 任意集計枠コード
	 * @return 該当する任意期間別実績の勤怠時間
	 */
	Optional<AttendanceTimeOfAnyPeriod> find(String employeeId, String frameCode);

	/**
	 * 検索　（社員IDリスト）
	 * @param employeeIds 社員IDリスト
	 * @param frameCode 任意集計枠コード
	 * @return 任意期間別実績の勤怠時間
	 */
	List<AttendanceTimeOfAnyPeriod> findBySids(List<String> employeeIds, String frameCode);
	
	/**
	 * 登録および更新
	 * @param attendanceTimeOfAnyPeriod 任意期間別実績の勤怠時間
	 */
	void persistAndUpdate(AttendanceTimeOfAnyPeriod attendanceTimeOfAnyPeriod);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param frameCode 任意集計枠コード
	 */
	void remove(String employeeId, String frameCode);
}
