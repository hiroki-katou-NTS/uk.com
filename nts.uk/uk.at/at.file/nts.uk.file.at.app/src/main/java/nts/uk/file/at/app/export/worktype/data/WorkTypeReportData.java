package nts.uk.file.at.app.export.worktype.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktype.CalculateMethod;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeReportData {
	
	/* 勤務種類コード */
	private String workTypeCode;
	/* 勤務種類名称 */
	private String name;
	//  
	private String symbolicName;
	// 勤務種類略名
	private String abbreviationName;
	// 勤務種類備考
	private String memo;
	// 廃止区分
	private int deprecate;
	// 出勤率の計算
	private CalculateMethod calculateMethod;
	// 勤務の単位 (対象範囲)
	private WorkTypeUnit workAtr;
	// 1日
	private WorkTypeClassification oneDayCls;
	// 午前
	private WorkTypeClassification morningCls;
	// 午後
	private WorkTypeClassification afternoonCls;
	// 日勤・夜勤時間を求める
	private int dayNightTimeAsk;
	// 出勤時刻を直行とする
	private int attendanceTime;
	// 退勤時刻を直帰とする
	private int timeLeaveWork;
	// 休日日数をカウントする
	private int countHodiday;
	// 公休を消化する
	private int digestPublicHd;
	// 代休を発生させる
	private int genSubHodiday;
	// 欠勤の集計枠
	private int sumAbsenseNo;
	// 特別休暇の集計枠
	private int sumSpHodidayNo;
	// 休業区分
	private CloseAtr closeAtr;
	//他言語名称
	private String otherLangName;
	//他言語略名
	private String otherLangShortName;
	//並び順
	private Integer dispOrder;
	
}
