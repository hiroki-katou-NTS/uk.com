package nts.uk.file.at.app.export.worktype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktype.CalculateMethod;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

/**
 * 
 * @author sonnh
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeReportData {

	/**
	 * 勤務種類コード
	 */
	private String workTypeCode;
	/**
	 *  勤務種類名称
	 */
	private String name;
	/**
	 *  記号
	 */
	private String symbolicName;
	/**
	 *  勤務種類略名
	 */
	private String abbreviationName;
	/**
	 *  勤務種類備考
	 */
	private String memo;
	/**
	 *  廃止区分
	 */
	private int deprecate;
	/**
	 *  出勤率の計算
	 */
	private CalculateMethod calculateMethod;
	/**
	 *  勤務の単位 (対象範囲)
	 */
	private WorkTypeUnit workAtr;
	/**
	 *  1日
	 */
	private WorkTypeClassification oneDayCls;
	/**
	 *  午前
	 */
	private WorkTypeClassification morningCls;
	/**
	 *  午後
	 */
	private WorkTypeClassification afternoonCls;

	/**
	 * １日の休日区分
	 */
	private HolidayAtr oneDayHolidayAtr;

	/**
	 * 1日の日勤・夜勤時間を求める
	 */
	private int oneDayDayNightTimeAsk;
	/**
	 *  出勤時刻を直行とする
	 */
	private int oneDayAttendanceTime;
	/**
	 *  退勤時刻を直帰とする
	 */
	private int oneDayTimeLeaveWork;
	/**
	 *  公休を消化する
	 */
	private int oneDayDigestPublicHd;
	/**
	 *  代休を発生させる
	 */
	private int oneDayGenSubHodiday;
	/**
	 *  欠勤の集計枠
	 */
	private int oneDaySumAbsenseNo;
	/**
	 *  特別休暇の集計枠
	 */
	private int oneDaySumSpHodidayNo;
	/**
	 *  休業区分
	 */
	private CloseAtr oneDaycloseAtr;

	/**
	 * 午前の日勤・夜勤時間を求める
	 */
	private Integer morningDayNightTimeAsk;
	/**
	 *  午前の出勤時刻を直行とする
	 */
	private Integer morningAttendanceTime;
	/**
	 *  午前の退勤時刻を直帰とする
	 */
	private Integer morningTimeLeaveWork;
	/**
	 *  午前の休日日数をカウントする
	 */
	private Integer morningCountHodiday;
	/**
	 *  午前の公休を消化する
	 */
	private Integer morningDigestPublicHd;
	/**
	 *  午前の代休を発生させる
	 */
	private Integer morningGenSubHodiday;
	/**
	 *  午前の欠勤の集計枠
	 */
	private Integer morningSumAbsenseNo;
	/**
	 *  午前の特別休暇の集計枠
	 */
	private Integer morningSumSpHodidayNo;
	/**
	 * 午後の日勤・夜勤時間を求める
	 */
	private Integer afternoonDayNightTimeAsk;
	/**
	 *  午後の出勤時刻を直行とする
	 */
	private Integer afternoonAttendanceTime;
	/**
	 *  午後の退勤時刻を直帰とする
	 */
	private Integer afternoonTimeLeaveWork;
	/**
	 *  午後の休日日数をカウントする
	 */
	private Integer afternoonCountHodiday;
	/**
	 *  午後の公休を消化する
	 */
	private Integer afternoonDigestPublicHd;
	/**
	 *  午後の代休を発生させる
	 */
	private Integer afternoonGenSubHodiday;
	/**
	 *  午後の欠勤の集計枠
	 */
	private Integer afternoonSumAbsenseNo;
	/**
	 * 午後の 特別休暇の集計枠
	 */
	private Integer afternoonSumSpHodidayNo;
	/**
	 *  他言語名称
	 */
	private String otherLangName;
	/**
	 *  他言語略名
	 */
	private String otherLangShortName;
	/**
	 *  並び順
	 */
	private Integer dispOrder;

}
