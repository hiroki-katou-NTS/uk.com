package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDisplayControlDto {
	/** 資格コード */
	public String companyId;

	/** 個人情報区分 */
	public List<Integer> personInforAtr;

	/** 表示区分 */
	public int personDisplayAtr;

	/** 資格表示記号 */
	public String personSyQualify;

	/** 取得不足表示区分 */
	public boolean pubHolidayShortageAtr;

	/** 取得超過表示区分 */
	public boolean pubHolidayExcessAtr;

	/** 勤務就業記号表示区分 */
	public boolean symbolAtr;

	/** 半日表示区分 */
	public boolean symbolHalfDayAtr;

	/** 半日記号 */
	public boolean symbolHalfDayName;

	/** 資格コード */
	public List<String> qualifyCodes;
}
