package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;

@Getter
@Setter
@AllArgsConstructor
public class AbsenceReflectParameter {
	/**
	 * 社員ID
	 */
	private String employeeId;
	/**
	 * 年月日
	 */
	private GeneralDate baseDate;
	/**
	 * 予定と実績を同じに変更する区分
	 */
	private ScheAndRecordSameChangeFlg scheAndRecordSameChangeFlg;
	/**
	 * 予定反映区分
	 */
	private boolean scheTimeReflectAtr;
	/**
	 * 休暇申請．勤務種類コード
	 */
	private String workTypeCode;
	/**
	 * 反映状態
	 */
	private ReflectedStateRecord reflectState;
	/**
	 * 予定反映不可理由
	 */
	private ReasonNotReflectRecord reasoNotReflect;

}
