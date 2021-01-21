package nts.uk.file.at.infra.worktime;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodExceededPredAddVacation;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.FontRearSection;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.MultiStampTimePiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingGoOutTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.StampPiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkSystemAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FixedChangeAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestClockCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.PrePlanWorkTimeCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.RoundingTimeType;
import nts.uk.ctx.at.shared.infra.repository.worktime.flowset.ResttimeAtr;
import nts.uk.file.at.app.export.worktime.WorkTimeReportRepository;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class JpaWorkTimeReportRepository extends JpaRepository implements WorkTimeReportRepository {
	private static final String SELECT_WORK_TIME_NORMAL_NO_OTHER_LANGUAGE;
	private static final String SELECT_WORK_TIME_FLOW_NO_OTHER_LANGUAGE;
	private static final String SELECT_WORK_TIME_FLEX_NO_OTHER_LANGUAGE;
	
	static {
		StringBuilder sqlNormalNoOtherLang = new StringBuilder();
		sqlNormalNoOtherLang.append(" SELECT");
		// R1_59 コード
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.WORKTIME_CD, NULL),");
		// R1_60 名称
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.NAME, NULL),");
		// R1_61 略名
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.ABNAME, NULL),");
		// R1_62 記号
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.SYMBOL, NULL),");
		// R1_63 廃止
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.ABOLITION_ATR = ?isAbolish THEN ?isAbolishText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.ABOLITION_ATR = ?notAbolish THEN ?notAbolishText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END, ");
		// R1_64 勤務形態
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.DAILY_WORK_ATR = ?regularWork THEN ?regularWorkText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_65 設定方法
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork THEN ?fixedWorkText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork THEN ?difftimeWorkText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_67 モード
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?detailModeText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?simpleMode THEN ?simpleModeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_66 半日勤務
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue, ?isUseHalfDayText, ?isNotUseHalfDayText)");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue, ?isUseHalfDayText, ?isNotUseHalfDayText)");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_68 備考
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.NOTE, NULL),");
		// R1_69 コメント
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.MEMO, NULL),");
		// R1_70 所定設定.1日の始まりの時刻
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.START_DATE_CLOCK IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.START_DATE_CLOCK AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.START_DATE_CLOCK AS INTEGER)%60,'0#')), NULL),");
		// R1_71 所定設定.1日の範囲時間.時間
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND PRED_TIME_SET.RANGE_TIME_DAY IS NOT NULL, ");
		sqlNormalNoOtherLang.append(" 		CAST(PRED_TIME_SET.RANGE_TIME_DAY AS INTEGER)/60, NULL),");
		// R1_72 所定設定.1日の範囲時間.夜勤シフト
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.NIGHT_SHIFT_ATR = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?isNightShiftText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.NIGHT_SHIFT_ATR = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?isNotNightShiftText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_235 所定設定.変動可能範囲.前に変動
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.CHANGE_AHEAD IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(DIFF_TIME_WORK_SET.CHANGE_AHEAD AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DIFF_TIME_WORK_SET.CHANGE_AHEAD AS INTEGER)%60,'0#')), NULL),");
		// R1_236 所定設定.変動可能範囲.後に変動
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.CHANGE_BEHIND IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(DIFF_TIME_WORK_SET.CHANGE_BEHIND AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DIFF_TIME_WORK_SET.CHANGE_BEHIND AS INTEGER)%60,'0#')), NULL),");
		// R1_73 所定設定.就業時刻1回目.始業時刻
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SHEET_SET1.START_TIME IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(WORK_TIME_SHEET_SET1.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(WORK_TIME_SHEET_SET1.START_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_74 所定設定.就業時刻1回目.終業時刻
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SHEET_SET1.END_TIME IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(WORK_TIME_SHEET_SET1.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(WORK_TIME_SHEET_SET1.END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_75 所定設定.就業時刻1回目.残業を含めた所定時間帯を設定する
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.IS_INCLUDE_OT = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?isIncludeOtText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.IS_INCLUDE_OT = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?isNotIncludeOtText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_4 所定設定.就業時刻2回目
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue THEN ?isUseSecondWorkingDayText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND WORK_TIME_SHEET_SET2.USE_ATR = ?isFalse THEN ?isNotUseSecondWorkingDayText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_76 所定設定.就業時刻2回目.始業時刻
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue AND WORK_TIME_SHEET_SET2.START_TIME IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(WORK_TIME_SHEET_SET2.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(WORK_TIME_SHEET_SET2.START_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_77 所定設定.就業時刻2回目.終業時刻
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue AND WORK_TIME_SHEET_SET2.END_TIME IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(WORK_TIME_SHEET_SET2.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(WORK_TIME_SHEET_SET2.END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_78 所定設定.半日勤務.前半終了時刻
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.MORNING_END_TIME IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.MORNING_END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.MORNING_END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_79 所定設定.半日勤務.後半開始時刻
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.AFTERNOON_START_TIME IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.AFTERNOON_START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.AFTERNOON_START_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_80 所定設定.所定時間.1日
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, CONCAT(CAST(PRED_TIME_SET.PRED_ONE_DAY AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 		FORMAT(CAST(PRED_TIME_SET.PRED_ONE_DAY AS INTEGER)%60,'0#')), NULL),");
		// R1_81 所定設定.所定時間.午前
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.PRED_MORNING IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.PRED_MORNING AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.PRED_MORNING AS INTEGER)%60,'0#')), NULL),");
		// R1_82 所定設定.所定時間.午後
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.PRED_AFTERNOON IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.PRED_AFTERNOON AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.PRED_AFTERNOON AS INTEGER)%60,'0#')), NULL),");
		// R1_83 所定設定.休暇取得時加算時間.1日
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND PRED_TIME_SET.WORK_ADD_ONE_DAY IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.WORK_ADD_ONE_DAY AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.WORK_ADD_ONE_DAY AS INTEGER)%60,'0#')), NULL),");
		// R1_84 所定設定.休暇取得時加算時間.午前
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND PRED_TIME_SET.WORK_ADD_MORNING IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.WORK_ADD_MORNING AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.WORK_ADD_MORNING AS INTEGER)%60,'0#')), NULL),");
		// R1_85 所定設定.休暇取得時加算時間.午後
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND PRED_TIME_SET.WORK_ADD_AFTERNOON IS NOT NULL,");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.WORK_ADD_AFTERNOON AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.WORK_ADD_AFTERNOON AS INTEGER)%60,'0#')), NULL),");
		// R1_86 勤務時間帯.1日勤務用.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FIXED_WORK_TIME_SET1.TIME_STR IS NOT NULL, CONCAT(CAST(FIXED_WORK_TIME_SET1.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_WORK_TIME_SET1.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_WORK_TIME_SET1.TIME_STR IS NOT NULL, CONCAT(CAST(DT_WORK_TIME_SET1.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_WORK_TIME_SET1.TIME_STR AS INTEGER)%60,'0#')), NULL)),");
		// R1_87 勤務時間帯.1日勤務用.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FIXED_WORK_TIME_SET1.TIME_END IS NOT NULL, CONCAT(CAST(FIXED_WORK_TIME_SET1.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_WORK_TIME_SET1.TIME_END AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_WORK_TIME_SET1.TIME_END IS NOT NULL, CONCAT(CAST(DT_WORK_TIME_SET1.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_WORK_TIME_SET1.TIME_END AS INTEGER)%60,'0#')), NULL)),");
		// R1_88 勤務時間帯.1日勤務用.丸め
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_WORK_TIME_SET1.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET1.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET1.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET1.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET1.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET1.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET1.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET1.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DT_WORK_TIME_SET1.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET1.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET1.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET1.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET1.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET1.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET1.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET1.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_89 勤務時間帯.1日勤務用.端数
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_WORK_TIME_SET1.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET1.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DT_WORK_TIME_SET1.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET1.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_90 勤務時間帯.午前勤務用.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_TIME_SET2.TIME_STR IS NOT NULL, CONCAT(CAST(FIXED_WORK_TIME_SET2.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_WORK_TIME_SET2.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND DT_WORK_TIME_SET2.TIME_STR IS NOT NULL, CONCAT(CAST(DT_WORK_TIME_SET2.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_WORK_TIME_SET2.TIME_STR AS INTEGER)%60,'0#')), NULL)),");
		// R1_91 勤務時間帯.午前勤務用.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_TIME_SET2.TIME_END IS NOT NULL, CONCAT(CAST(FIXED_WORK_TIME_SET2.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_WORK_TIME_SET2.TIME_END AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND DT_WORK_TIME_SET2.TIME_END IS NOT NULL, CONCAT(CAST(DT_WORK_TIME_SET2.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_WORK_TIME_SET2.TIME_END AS INTEGER)%60,'0#')), NULL)),");
		// R1_92 勤務時間帯.午前勤務用.丸め
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET2.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET2.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET2.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET2.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET2.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET2.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET2.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET2.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET2.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET2.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET2.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET2.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET2.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET2.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET2.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET2.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_93 勤務時間帯.午前勤務用.端数
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET2.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET2.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isFalse THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET2.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET2.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_94 勤務時間帯.午後勤務用.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_TIME_SET3.TIME_STR IS NOT NULL, CONCAT(CAST(FIXED_WORK_TIME_SET3.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_WORK_TIME_SET3.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND DT_WORK_TIME_SET3.TIME_STR IS NOT NULL, CONCAT(CAST(DT_WORK_TIME_SET3.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_WORK_TIME_SET3.TIME_STR AS INTEGER)%60,'0#')), NULL)),");
		// R1_95 勤務時間帯.午後勤務用.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_TIME_SET3.TIME_END IS NOT NULL, CONCAT(CAST(FIXED_WORK_TIME_SET3.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_WORK_TIME_SET3.TIME_END AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND DT_WORK_TIME_SET3.TIME_END IS NOT NULL, CONCAT(CAST(DT_WORK_TIME_SET3.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_WORK_TIME_SET3.TIME_END AS INTEGER)%60,'0#')), NULL)),");
		// R1_96 勤務時間帯.午後勤務用.丸め
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET3.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET3.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET3.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET3.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET3.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET3.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET3.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET3.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET3.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET3.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET3.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET3.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET3.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET3.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET3.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET3.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_97 勤務時間帯.午後勤務用.端数
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET3.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_WORK_TIME_SET3.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET3.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_WORK_TIME_SET3.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_98 残業時間帯.法定内残業自動計算
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEGAL_OT_SET = ?isLegalOtSet ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?isUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEGAL_OT_SET = ?isNotLegalOtSet ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?isNotUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.OT_SET = ?isLegalOtSet ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?isUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.OT_SET = ?isNotLegalOtSet ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?isNotUseText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_99 残業時間帯.1日勤務用.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_OT_TIME_SET1.TIME_STR IS NOT NULL, CONCAT(CAST(FIXED_OT_TIME_SET1.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_OT_TIME_SET1.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DT_OT_TIME_SET1.TIME_STR IS NOT NULL, CONCAT(CAST(DT_OT_TIME_SET1.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_OT_TIME_SET1.TIME_STR AS INTEGER)%60,'0#')), NULL)),");
		// R1_100 残業時間帯.1日勤務用.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_OT_TIME_SET1.TIME_END IS NOT NULL, CONCAT(CAST(FIXED_OT_TIME_SET1.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_OT_TIME_SET1.TIME_END AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DT_OT_TIME_SET1.TIME_END IS NOT NULL, CONCAT(CAST(DT_OT_TIME_SET1.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_OT_TIME_SET1.TIME_END AS INTEGER)%60,'0#')), NULL)),");
		// R1_101 残業時間帯.1日勤務用.丸め
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_OT_TIME_SET1.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET1.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET1.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET1.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET1.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET1.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET1.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET1.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DT_OT_TIME_SET1.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET1.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET1.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET1.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET1.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET1.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET1.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET1.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_102 残業時間帯.1日勤務用.端数
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_OT_TIME_SET1.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET1.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DT_OT_TIME_SET1.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET1.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_103 残業時間帯.1日勤務用.残業枠
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork ");
		sqlNormalNoOtherLang.append(" 			THEN FIXED_OT_FRAME1.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork ");
		sqlNormalNoOtherLang.append(" 			THEN DT_OT_FRAME1.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_104 残業時間帯.1日勤務用.早出
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_OT_TIME_SET1.TREAT_EARLY_OT_WORK = ?isTrue");
		sqlNormalNoOtherLang.append(" 			THEN ?treatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_OT_TIME_SET1.TREAT_EARLY_OT_WORK = ?isFalse");
		sqlNormalNoOtherLang.append(" 			THEN ?notTreatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_OT_TIME_SET1.TREAT_EARLY_OT_WORK = ?isTrue");
		sqlNormalNoOtherLang.append(" 			THEN ?treatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_OT_TIME_SET1.TREAT_EARLY_OT_WORK = ?isFalse");
		sqlNormalNoOtherLang.append(" 			THEN ?notTreatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_237 残業時間帯.1日勤務用.固定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_OT_TIME_SET1.UPD_START_TIME = ?isTrue");
		sqlNormalNoOtherLang.append(" 			THEN ?isUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_OT_TIME_SET1.UPD_START_TIME = ?isFalse");
		sqlNormalNoOtherLang.append(" 			THEN ?isNotUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_105 残業時間帯.1日勤務用.法定内残業枠
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEGAL_OT_SET = ?isLegalOtSet ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN FIXED_LEGAL_OT_FRAME1.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.OT_SET = ?isLegalOtSet ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN DT_LEGAL_OT_FRAME1.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_106 残業時間帯.1日勤務用.積残順序
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEGAL_OT_SET = ?isLegalOtSet ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN FIXED_OT_TIME_SET1.PAYOFF_ORDER");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.OT_SET = ?isLegalOtSet ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN DT_OT_TIME_SET1.PAYOFF_ORDER");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_107 残業時間帯.午前勤務用.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_OT_TIME_SET2.TIME_STR IS NOT NULL, CONCAT(CAST(FIXED_OT_TIME_SET2.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_OT_TIME_SET2.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND DT_OT_TIME_SET2.TIME_STR IS NOT NULL, CONCAT(CAST(DT_OT_TIME_SET2.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_OT_TIME_SET2.TIME_STR AS INTEGER)%60,'0#')), NULL)),");
		// R1_108 残業時間帯.午前勤務用.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_OT_TIME_SET2.TIME_END IS NOT NULL, CONCAT(CAST(FIXED_OT_TIME_SET2.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_OT_TIME_SET2.TIME_END AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND DT_OT_TIME_SET2.TIME_END IS NOT NULL, CONCAT(CAST(DT_OT_TIME_SET2.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_OT_TIME_SET2.TIME_END AS INTEGER)%60,'0#')), NULL)),");
		// R1_109 残業時間帯.午前勤務用.丸め
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET2.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET2.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET2.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET2.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET2.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET2.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET2.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET2.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET2.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET2.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET2.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET2.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET2.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET2.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET2.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET2.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_110 残業時間帯.午前勤務用.端数
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET2.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET2.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET2.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET2.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_111 残業時間帯.午前勤務用.残業枠
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.USE_HALF_DAY = ?isTrue ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN FIXED_OT_FRAME2.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN DT_OT_FRAME2.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_112 残業時間帯.午前勤務用.早出
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_OT_TIME_SET2.TREAT_EARLY_OT_WORK = ?isTrue ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?treatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_OT_TIME_SET2.TREAT_EARLY_OT_WORK = ?isFalse ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?notTreatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_OT_TIME_SET2.TREAT_EARLY_OT_WORK = ?isTrue ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?treatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_OT_TIME_SET2.TREAT_EARLY_OT_WORK = ?isFalse ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?notTreatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_238 残業時間帯.午前勤務用.固定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_OT_TIME_SET2.UPD_START_TIME = ?isTrue");
		sqlNormalNoOtherLang.append(" 			THEN ?isUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_OT_TIME_SET2.UPD_START_TIME = ?isFalse");
		sqlNormalNoOtherLang.append(" 			THEN ?isNotUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_113 残業時間帯.午前勤務用.法定内残業枠
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEGAL_OT_SET = ?isLegalOtSet AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN FIXED_LEGAL_OT_FRAME2.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.OT_SET = ?isLegalOtSet AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN DT_LEGAL_OT_FRAME2.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_114 残業時間帯.午前勤務用.積残順序
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEGAL_OT_SET = ?isLegalOtSet AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN FIXED_OT_TIME_SET2.PAYOFF_ORDER");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.OT_SET = ?isLegalOtSet AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN DT_OT_TIME_SET2.PAYOFF_ORDER");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_115 残業時間帯.午後勤務用.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FIXED_OT_TIME_SET3.TIME_STR IS NOT NULL, CONCAT(CAST(FIXED_OT_TIME_SET3.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_OT_TIME_SET3.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_OT_TIME_SET3.TIME_STR IS NOT NULL, CONCAT(CAST(DT_OT_TIME_SET3.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_OT_TIME_SET3.TIME_STR AS INTEGER)%60,'0#')), NULL)),");
		// R1_116 残業時間帯.午後勤務用.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FIXED_OT_TIME_SET3.TIME_END IS NOT NULL, CONCAT(CAST(FIXED_OT_TIME_SET3.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_OT_TIME_SET3.TIME_END AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_OT_TIME_SET3.TIME_END IS NOT NULL, CONCAT(CAST(DT_OT_TIME_SET3.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_OT_TIME_SET3.TIME_END AS INTEGER)%60,'0#')), NULL)),");
		// R1_117 残業時間帯.午後勤務用.丸め
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET3.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET3.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET3.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET3.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET3.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET3.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET3.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET3.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET3.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET3.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET3.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET3.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET3.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET3.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET3.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET3.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_118 残業時間帯.午後勤務用.端数
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET3.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_OT_TIME_SET3.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isFalse OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET3.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN DT_OT_TIME_SET3.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_119 残業時間帯.午後勤務用.残業枠
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN FIXED_OT_FRAME3.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN DT_OT_FRAME3.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_120 残業時間帯.午後勤務用.早出
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_OT_TIME_SET3.TREAT_EARLY_OT_WORK = ?isTrue AND FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?treatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_OT_TIME_SET3.TREAT_EARLY_OT_WORK = ?isFalse AND FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?notTreatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_OT_TIME_SET3.TREAT_EARLY_OT_WORK = ?isTrue AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?treatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_OT_TIME_SET3.TREAT_EARLY_OT_WORK = ?isFalse AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?notTreatEarlyOtWork");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_239 残業時間帯.午後勤務用.固定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_OT_TIME_SET3.UPD_START_TIME = ?isTrue");
		sqlNormalNoOtherLang.append(" 			THEN ?isUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue ");
		sqlNormalNoOtherLang.append(" 			AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_OT_TIME_SET3.UPD_START_TIME = ?isFalse");
		sqlNormalNoOtherLang.append(" 			THEN ?isNotUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_121 残業時間帯.午後勤務用.法定内残業枠
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEGAL_OT_SET = ?isLegalOtSet AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN FIXED_LEGAL_OT_FRAME3.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.OT_SET = ?isLegalOtSet AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN DT_LEGAL_OT_FRAME3.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_122 残業時間帯.午後勤務用.積残順序
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEGAL_OT_SET = ?isLegalOtSet AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN FIXED_OT_TIME_SET3.PAYOFF_ORDER");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.OT_SET = ?isLegalOtSet AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN DT_OT_TIME_SET3.PAYOFF_ORDER");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_123 打刻時間帯.優先設定.出勤
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND PIORITY_SET1.PIORITY_ATR = ?beforePiority AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET1.PIORITY_ATR = ?afterPiority AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_124 打刻時間帯.優先設定.退勤
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND PIORITY_SET2.PIORITY_ATR = ?beforePiority AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET2.PIORITY_ATR = ?afterPiority AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_125 打刻時間帯.打刻丸め.出勤
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 		CASE WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		NULL),");
		// R1_126 打刻時間帯.打刻丸め.出勤前後設定
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET1.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlNormalNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET1.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlNormalNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_127 打刻時間帯.打刻丸め.退勤
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 		CASE WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		NULL),");
		// R1_128 打刻時間帯.打刻丸め.退勤前後設定
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET2.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlNormalNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET2.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlNormalNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_129 打刻詳細設定.出勤反映時間帯1回目.開始時刻
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_STAMP_REFLECT1.START_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 			THEN CONCAT(CAST(FIXED_STAMP_REFLECT1.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 				FORMAT(CAST(FIXED_STAMP_REFLECT1.START_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_STAMP_REFLECT1.START_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 			THEN CONCAT(CAST(DT_STAMP_REFLECT1.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 				FORMAT(CAST(DT_STAMP_REFLECT1.START_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_130 打刻詳細設定.出勤反映時間帯1回目.終了時刻
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_STAMP_REFLECT1.END_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 			THEN CONCAT(CAST(FIXED_STAMP_REFLECT1.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 				FORMAT(CAST(FIXED_STAMP_REFLECT1.END_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_STAMP_REFLECT1.END_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 			THEN CONCAT(CAST(DT_STAMP_REFLECT1.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 				FORMAT(CAST(DT_STAMP_REFLECT1.END_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_131 打刻詳細設定.出勤反映時間帯2回目.開始時刻 
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue AND FIXED_STAMP_REFLECT3.START_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 				THEN CONCAT(CAST(FIXED_STAMP_REFLECT3.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 					FORMAT(CAST(FIXED_STAMP_REFLECT3.START_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_132 打刻詳細設定.出勤反映時間帯2回目.終了時刻
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue AND FIXED_STAMP_REFLECT3.END_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 				THEN CONCAT(CAST(FIXED_STAMP_REFLECT3.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 					FORMAT(CAST(FIXED_STAMP_REFLECT3.END_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_133 打刻詳細設定.退勤反映時間帯1回目.開始時刻
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_STAMP_REFLECT2.START_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 			THEN CONCAT(CAST(FIXED_STAMP_REFLECT2.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 				FORMAT(CAST(FIXED_STAMP_REFLECT2.START_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_STAMP_REFLECT2.START_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 			THEN CONCAT(CAST(DT_STAMP_REFLECT2.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 				FORMAT(CAST(DT_STAMP_REFLECT2.START_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_134 打刻詳細設定.退勤反映時間帯1回目.終了時刻
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_STAMP_REFLECT2.END_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 			THEN CONCAT(CAST(FIXED_STAMP_REFLECT2.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 				FORMAT(CAST(FIXED_STAMP_REFLECT2.END_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_STAMP_REFLECT2.END_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 			THEN CONCAT(CAST(DT_STAMP_REFLECT2.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 				FORMAT(CAST(DT_STAMP_REFLECT2.END_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_135 打刻詳細設定.退勤反映時間帯2回目.開始時刻
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue AND FIXED_STAMP_REFLECT4.START_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 				THEN CONCAT(CAST(FIXED_STAMP_REFLECT4.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 					FORMAT(CAST(FIXED_STAMP_REFLECT4.START_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_136 打刻詳細設定.退勤反映時間帯2回目.終了時刻
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode ");
		sqlNormalNoOtherLang.append(" 			AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue AND FIXED_STAMP_REFLECT4.END_TIME IS NOT NULL");
		sqlNormalNoOtherLang.append(" 				THEN CONCAT(CAST(FIXED_STAMP_REFLECT4.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 					FORMAT(CAST(FIXED_STAMP_REFLECT4.END_TIME AS INTEGER)%60,'0#'))");
		sqlNormalNoOtherLang.append(" 		ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_137 打刻詳細設定.優先設定.入門
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET3.PIORITY_ATR = ?beforePiority");
		sqlNormalNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET3.PIORITY_ATR = ?afterPiority");
		sqlNormalNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_138 打刻詳細設定.優先設定.退門
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET4.PIORITY_ATR = ?beforePiority");
		sqlNormalNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET4.PIORITY_ATR = ?afterPiority");
		sqlNormalNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_139 打刻詳細設定.優先設定.PCログオン
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET5.PIORITY_ATR = ?beforePiority");
		sqlNormalNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET5.PIORITY_ATR = ?afterPiority");
		sqlNormalNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_140 打刻詳細設定.優先設定.PCログオフ
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET6.PIORITY_ATR = ?beforePiority");
		sqlNormalNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET6.PIORITY_ATR = ?afterPiority");
		sqlNormalNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_141 打刻詳細設定.打刻丸め.外出
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		NULL),");
		// R1_142 打刻詳細設定.打刻丸め.外出前後設定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET3.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlNormalNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET3.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlNormalNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_143 打刻詳細設定.打刻丸め.戻り
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 		CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		NULL),");
		// R1_144 打刻詳細設定.打刻丸め.戻り前後設定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET4.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlNormalNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET4.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlNormalNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_145 休憩時間帯.1日勤務用.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_HALF_REST_SET1.START_TIME IS NOT NULL, CONCAT(CAST(FIXED_HALF_REST_SET1.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_HALF_REST_SET1.START_TIME AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DT_HALF_REST_TIME1.START_TIME IS NOT NULL, CONCAT(CAST(DT_HALF_REST_TIME1.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_HALF_REST_TIME1.START_TIME AS INTEGER)%60,'0#')), NULL)),");
		// R1_146 休憩時間帯.1日勤務用.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_HALF_REST_SET1.END_TIME IS NOT NULL, CONCAT(CAST(FIXED_HALF_REST_SET1.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_HALF_REST_SET1.END_TIME AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DT_HALF_REST_TIME1.END_TIME IS NOT NULL, CONCAT(CAST(DT_HALF_REST_TIME1.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_HALF_REST_TIME1.END_TIME AS INTEGER)%60,'0#')), NULL)),");
		// R1_240 休憩時間帯.1日勤務用.固定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_HALF_REST_TIME1.UPD_START_TIME = ?isTrue");
		sqlNormalNoOtherLang.append(" 			THEN ?isUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_HALF_REST_TIME1.UPD_START_TIME = ?isFalse");
		sqlNormalNoOtherLang.append(" 			THEN ?isNotUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_147 休憩時間帯.午前勤務用.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FIXED_HALF_REST_SET2.START_TIME IS NOT NULL, CONCAT(CAST(FIXED_HALF_REST_SET2.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_HALF_REST_SET2.START_TIME AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_HALF_REST_TIME2.START_TIME IS NOT NULL, CONCAT(CAST(DT_HALF_REST_TIME2.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_HALF_REST_TIME2.START_TIME AS INTEGER)%60,'0#')), NULL)),");
		// R1_148 休憩時間帯.午前勤務用.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FIXED_HALF_REST_SET2.END_TIME IS NOT NULL, CONCAT(CAST(FIXED_HALF_REST_SET2.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_HALF_REST_SET2.END_TIME AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_HALF_REST_TIME2.END_TIME IS NOT NULL, CONCAT(CAST(DT_HALF_REST_TIME2.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_HALF_REST_TIME2.END_TIME AS INTEGER)%60,'0#')), NULL)),");
		// R1_241 休憩時間帯.午前勤務用.固定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_HALF_REST_TIME2.UPD_START_TIME = ?isTrue AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?isUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_HALF_REST_TIME2.UPD_START_TIME = ?isFalse AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?isNotUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_149 休憩時間帯.開始時間.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FIXED_HALF_REST_SET3.START_TIME IS NOT NULL, CONCAT(CAST(FIXED_HALF_REST_SET3.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_HALF_REST_SET3.START_TIME AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_HALF_REST_TIME3.START_TIME IS NOT NULL, CONCAT(CAST(DT_HALF_REST_TIME3.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_HALF_REST_TIME3.START_TIME AS INTEGER)%60,'0#')), NULL)),");
		// R1_150 休憩時間帯.開始時間.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FIXED_HALF_REST_SET3.END_TIME IS NOT NULL, CONCAT(CAST(FIXED_HALF_REST_SET3.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_HALF_REST_SET3.END_TIME AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND DT_HALF_REST_TIME3.END_TIME IS NOT NULL, CONCAT(CAST(DT_HALF_REST_TIME3.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_HALF_REST_TIME3.END_TIME AS INTEGER)%60,'0#')), NULL)),");
		// R1_242 休憩時間帯.開始時間.固定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_HALF_REST_TIME3.UPD_START_TIME = ?isTrue AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?isUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_HALF_REST_TIME3.UPD_START_TIME = ?isFalse AND DIFF_TIME_WORK_SET.USE_HALF_DAY = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlNormalNoOtherLang.append(" 			THEN ?isNotUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_151 休憩時間帯.休憩計算設定.実績での休憩計算方法
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.CALC_METHOD = ?calculateMethodMasterRef");
		sqlNormalNoOtherLang.append(" 			THEN ?calculateMethodMasterRefText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.CALC_METHOD = ?calculateMethodPlanRef");
		sqlNormalNoOtherLang.append(" 			THEN ?calculateMethodPlanRefText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.DT_CALC_METHOD = ?calculateMethodMasterRef");
		sqlNormalNoOtherLang.append(" 			THEN ?calculateMethodMasterRefText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.DT_CALC_METHOD = ?calculateMethodPlanRef");
		sqlNormalNoOtherLang.append(" 			THEN ?calculateMethodPlanRefText");
		sqlNormalNoOtherLang.append(" 		ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_152 休憩時間帯.休憩計算設定.休憩中に退勤した場合の休憩時間の計算方法
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEV_REST_CALC_TYPE = ?calcMethodAppropAll");
		sqlNormalNoOtherLang.append(" 			THEN ?calcMethodAppropAllText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEV_REST_CALC_TYPE = ?calcMethodNotAppropAll");
		sqlNormalNoOtherLang.append(" 			THEN ?calcMethodNotAppropAllText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork AND FIXED_WORK_SET.LEV_REST_CALC_TYPE = ?calcMethodOfficeWorkAppropAll");
		sqlNormalNoOtherLang.append(" 			THEN ?calcMethodOfficeWorkAppropAllText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.DT_COMMON_REST_SET = ?calcMethodAppropAll");
		sqlNormalNoOtherLang.append(" 			THEN ?calcMethodAppropAllText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.DT_COMMON_REST_SET = ?calcMethodNotAppropAll");
		sqlNormalNoOtherLang.append(" 			THEN ?calcMethodNotAppropAllText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DIFF_TIME_WORK_SET.DT_COMMON_REST_SET = ?calcMethodOfficeWorkAppropAll");
		sqlNormalNoOtherLang.append(" 			THEN ?calcMethodOfficeWorkAppropAllText");
		sqlNormalNoOtherLang.append(" 		ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_153 休出時間帯.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_HOL_TIME_SET.TIME_STR IS NOT NULL, CONCAT(CAST(FIXED_HOL_TIME_SET.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_HOL_TIME_SET.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_HOL_SET.TIME_STR IS NOT NULL, CONCAT(CAST(DIFF_TIME_HOL_SET.TIME_STR AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DIFF_TIME_HOL_SET.TIME_STR AS INTEGER)%60,'0#')), NULL)),");
		// R1_154 休出時間帯.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_HOL_TIME_SET.TIME_END IS NOT NULL, CONCAT(CAST(FIXED_HOL_TIME_SET.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_HOL_TIME_SET.TIME_END AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DIFF_TIME_HOL_SET.TIME_END IS NOT NULL, CONCAT(CAST(DIFF_TIME_HOL_SET.TIME_END AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DIFF_TIME_HOL_SET.TIME_END AS INTEGER)%60,'0#')), NULL)),");
		// R1_155 休出時間帯.法定内休出枠
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork, FIXED_HOL_FRAME.WDO_FR_NAME, DT_HOL_FRAME.WDO_FR_NAME),");
		// R1_156 休出時間帯.法定外休出枠
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork, FIXED_OUT_HOL_FRAME.WDO_FR_NAME, DT_OUT_HOL_FRAME.WDO_FR_NAME),");
		// R1_157 休出時間帯.法定外休出枠（祝日）
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork, FIXED_PUB_HOL_FRAME.WDO_FR_NAME, DT_PUB_HOL_FRAME.WDO_FR_NAME),");
		// R1_158 休出時間帯.丸め
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_HOL_TIME_SET.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_HOL_TIME_SET.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_HOL_TIME_SET.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_HOL_TIME_SET.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_HOL_TIME_SET.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_HOL_TIME_SET.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_HOL_TIME_SET.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_HOL_TIME_SET.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DIFF_TIME_HOL_SET.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DIFF_TIME_HOL_SET.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DIFF_TIME_HOL_SET.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DIFF_TIME_HOL_SET.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DIFF_TIME_HOL_SET.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DIFF_TIME_HOL_SET.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DIFF_TIME_HOL_SET.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 			 WHEN DIFF_TIME_HOL_SET.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_159 休出時間帯.端数
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN FIXED_HOL_TIME_SET.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN FIXED_HOL_TIME_SET.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END,");
		sqlNormalNoOtherLang.append(" 		CASE WHEN DIFF_TIME_HOL_SET.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 			 WHEN DIFF_TIME_HOL_SET.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 			 ELSE NULL");
		sqlNormalNoOtherLang.append(" 		END),");
		// R1_160 休出休憩.開始時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_HOL_REST_SET.START_TIME IS NOT NULL, CONCAT(CAST(FIXED_HOL_REST_SET.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_HOL_REST_SET.START_TIME AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DT_HOL_REST_TIME.START_TIME IS NOT NULL, CONCAT(CAST(DT_HOL_REST_TIME.START_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_HOL_REST_TIME.START_TIME AS INTEGER)%60,'0#')), NULL)),");
		// R1_161 休出休憩.終了時間
		sqlNormalNoOtherLang.append(" 	IIF(WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork,");
		sqlNormalNoOtherLang.append(" 		IIF(FIXED_HOL_REST_SET.END_TIME IS NOT NULL, CONCAT(CAST(FIXED_HOL_REST_SET.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(FIXED_HOL_REST_SET.END_TIME AS INTEGER)%60,'0#')), NULL),");
		sqlNormalNoOtherLang.append(" 		IIF(DT_HOL_REST_TIME.END_TIME IS NOT NULL, CONCAT(CAST(DT_HOL_REST_TIME.END_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(DT_HOL_REST_TIME.END_TIME AS INTEGER)%60,'0#')), NULL)),");
		// R1_243 休出休憩.固定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_HOL_REST_TIME.UPD_START_TIME = ?isTrue");
		sqlNormalNoOtherLang.append(" 			THEN ?isUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork AND DT_HOL_REST_TIME.UPD_START_TIME = ?isFalse");
		sqlNormalNoOtherLang.append(" 			THEN ?isNotUpdStartTimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_162 外出.外出丸め設定.同じ枠内での丸め設定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_SAME_FRAME = ?isTrue THEN ?roudingAfterTotalText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_SAME_FRAME = ?isFalse THEN ?roudingAfterEachTimePeriodText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_163 外出.外出丸め設定.枠を跨る場合の丸め設定
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_CROSS_FRAME = ?isTrue THEN ?roudingAfterTotalText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_CROSS_FRAME = ?isFalse THEN ?roudingAfterEachTimePeriodText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_164 外出.私用・組合外出時間.就業時間帯
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_165 外出.私用・組合外出時間.丸め設定
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_166 外出.私用・組合外出時間.丸め設定端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_167 外出.私用・組合外出時間.残業時間帯
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_168 外出.私用・組合外出時間.丸め設定
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_169 外出.私用・組合外出時間.丸め設定端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_170 外出.私用・組合外出時間.休出時間帯
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_171 外出.私用・組合外出時間.丸め設定
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_172 外出.私用・組合外出時間.丸め設定端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_173 外出.私用・組合外出控除時間.就業時間帯
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_174 外出.私用・組合外出控除時間.丸め設定
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_175 外出.私用・組合外出控除時間.丸め設定端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_176 外出.私用・組合外出控除時間.残業時間帯
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_177 外出.私用・組合外出控除時間.丸め設定
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_178 外出.私用・組合外出控除時間.丸め設定端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_179 外出.私用・組合外出控除時間.休出時間帯
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_180 外出.私用・組合外出控除時間.丸め設定
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_181 外出.私用・組合外出控除時間.丸め設定端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_182 外出.公用・有償外出時間.就業時間帯
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_183 外出.公用・有償外出時間.丸め設定
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_184 外出.公用・有償外出時間.丸め設定端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_185 外出.公用・有償外出時間.残業時間帯
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_186 外出.公用・有償外出時間.丸め設定
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_187 外出.公用・有償外出時間.丸め設定端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_188 外出.公用・有償外出時間.休出時間帯
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_189 外出.公用・有償外出時間.丸め設定
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_190 外出.公用・有償外出時間.丸め設定端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_191 遅刻早退.遅刻早退時間丸め.遅刻丸め
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_192 遅刻早退.遅刻早退時間丸め.遅刻端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.RECORD_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.RECORD_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_193 遅刻早退.遅刻早退時間丸め.早退丸め
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_194 遅刻早退.遅刻早退時間丸め.早退端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.RECORD_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.RECORD_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_195 遅刻早退.遅刻早退控除時間丸め.遅刻丸め
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_196 遅刻早退.遅刻早退控除時間丸め.遅刻端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.DEDUCTION_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.DEDUCTION_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_197 遅刻早退.遅刻早退控除時間丸め.早退丸め
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_198 遅刻早退.遅刻早退控除時間丸め.早退端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.DEDUCTION_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.DEDUCTION_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_199 遅刻早退詳細設定.控除時間.遅刻早退時間を就業時間から控除する
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND LATE_EARLY_SET.IS_DEDUCTE_FROM_TIME = ?isTrue THEN ?isDeducteFromTimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND LATE_EARLY_SET.IS_DEDUCTE_FROM_TIME = ?isFalse THEN ?isNotDeducteFromTimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_200 遅刻早退詳細設定.遅刻早退の判定設定.時間丁度の打刻は遅刻とする
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.EXTRACT_LATE_EARLY_TIME = ?isTrue THEN ?isExtractLateEarlyTimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.EXTRACT_LATE_EARLY_TIME = ?isFalse THEN ?isNotExtractLateEarlyTimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_201 遅刻早退詳細設定.遅刻早退の判定設定.時間丁度の打刻は早退とする
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.EXTRACT_LATE_EARLY_TIME = ?isTrue THEN ?isExtractLateEarlyTimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.EXTRACT_LATE_EARLY_TIME = ?isFalse THEN ?isNotExtractLateEarlyTimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_202 遅刻早退詳細設定.猶予時間.遅刻猶予時間
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND OTHER_LATE_EARLY1.GRACE_TIME IS NOT NULL, ");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(OTHER_LATE_EARLY1.GRACE_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(OTHER_LATE_EARLY1.GRACE_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_203 遅刻早退詳細設定.猶予時間.遅刻猶予時間を就業時間に含める
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.INCLUDE_WORKTIME = ?isTrue THEN ?isIncludeWorktimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.INCLUDE_WORKTIME = ?isFalse THEN ?isNotIncludeWorktimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_204 遅刻早退詳細設定.猶予時間.早退猶予時間
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND OTHER_LATE_EARLY2.GRACE_TIME IS NOT NULL, ");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(OTHER_LATE_EARLY2.GRACE_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(OTHER_LATE_EARLY2.GRACE_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_205 遅刻早退詳細設定.猶予時間.早退猶予時間を就業時間に含める
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.INCLUDE_WORKTIME = ?isTrue THEN ?isIncludeWorktimeText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.INCLUDE_WORKTIME = ?isFalse THEN ?isNotIncludeWorktimeText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_206 加給.コード
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORKTIME_COMMON_SET.RAISING_SALARY_SET, NULL),");
		// R1_207 加給.名称
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND BONUS_PAY_SET.BONUS_PAY_SET_CD IS NOT NULL THEN BONUS_PAY_SET.BONUS_PAY_SET_NAME");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND BONUS_PAY_SET.BONUS_PAY_SET_CD IS NULL AND WORKTIME_COMMON_SET.RAISING_SALARY_SET IS NOT NULL AND WORKTIME_COMMON_SET.RAISING_SALARY_SET != '   ' THEN ?masterUnregistered");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_47 代休.休日出勤
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.USE_ATR = ?isTrue THEN ?isUseSubstitutionText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.USE_ATR = ?isFalse THEN ?isNotUseSubstitutionText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_208 代休.休日出勤.時間区分
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SUBSTITUTION_SET2.USE_ATR = ?isFalse THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.TRANFER_ATR = ?specifiedTimeSubHol THEN ?specifiedTimeSubHolText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.TRANFER_ATR = ?certainTimeExcSubHol THEN ?certainTimeExcSubHolText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_209 代休.休日出勤.１日
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.ONE_DAY_TIME IS NOT NULL ");
		sqlNormalNoOtherLang.append(" 		AND (WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR (SUBSTITUTION_SET2.USE_ATR = ?isTrue AND SUBSTITUTION_SET2.TRANFER_ATR = ?specifiedTimeSubHol)), ");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET2.ONE_DAY_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET2.ONE_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_210 代休.休日出勤.半日
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.HALF_DAY_TIME IS NOT NULL ");
		sqlNormalNoOtherLang.append(" 		AND (WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR (SUBSTITUTION_SET2.USE_ATR = ?isTrue AND SUBSTITUTION_SET2.TRANFER_ATR = ?specifiedTimeSubHol)), ");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET2.HALF_DAY_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET2.HALF_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_211 代休.休日出勤.一定時間
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET2.USE_ATR = ?isTrue ");
		sqlNormalNoOtherLang.append(" 		AND SUBSTITUTION_SET2.TRANFER_ATR = ?certainTimeExcSubHol AND SUBSTITUTION_SET2.CERTAIN_TIME IS NOT NULL, ");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET2.CERTAIN_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET2.CERTAIN_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_48 代休.残業
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.USE_ATR = ?isTrue THEN ?isUseSubstitutionText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.USE_ATR = ?isFalse THEN ?isNotUseSubstitutionText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_212 代休.残業.時間区分
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SUBSTITUTION_SET1.USE_ATR = ?isFalse THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.TRANFER_ATR = ?specifiedTimeSubHol THEN ?specifiedTimeSubHolText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.TRANFER_ATR = ?certainTimeExcSubHol THEN ?certainTimeExcSubHolText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_213 代休.残業.１日
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET1.USE_ATR = ?isTrue ");
		sqlNormalNoOtherLang.append(" 		AND SUBSTITUTION_SET1.TRANFER_ATR = ?specifiedTimeSubHol AND SUBSTITUTION_SET1.ONE_DAY_TIME IS NOT NULL, ");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET1.ONE_DAY_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET1.ONE_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_214 代休.残業.半日
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET1.USE_ATR = ?isTrue ");
		sqlNormalNoOtherLang.append(" 		AND SUBSTITUTION_SET1.TRANFER_ATR = ?specifiedTimeSubHol AND SUBSTITUTION_SET1.HALF_DAY_TIME IS NOT NULL, ");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET1.HALF_DAY_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET1.HALF_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_215 代休.残業.一定時間
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET1.USE_ATR = ?isTrue ");
		sqlNormalNoOtherLang.append(" 		AND SUBSTITUTION_SET1.TRANFER_ATR = ?certainTimeExcSubHol AND SUBSTITUTION_SET1.CERTAIN_TIME IS NOT NULL, ");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET1.CERTAIN_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET1.CERTAIN_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_216 深夜残業.深夜時間丸め
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_217 深夜残業.深夜時間端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.LATE_NIGHT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.LATE_NIGHT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_218 臨時.臨時丸め
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_219 臨時.臨時端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND TEMP_WORKTIME_SET.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND TEMP_WORKTIME_SET.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_220 育児.育児時間帯に勤務した場合の扱い
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.CHILD_CARE_WORK_USE = ?isTrue THEN ?childCareWorkUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.CHILD_CARE_WORK_USE = ?isFalse THEN ?childCareWorkNotUseText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_221 育児.介護時間帯に勤務した場合の扱い
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.NUR_TIMEZONE_WORK_USE = ?isTrue THEN ?nurTimezoneWorkUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.NUR_TIMEZONE_WORK_USE = ?isFalse THEN ?nurTimezoneWorkNotUseText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_222 医療.日勤申し送り時間
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET1.APP_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(MEDICAL_TIME_SET1.APP_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(MEDICAL_TIME_SET1.APP_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_223 医療.夜勤申し送り時間
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET2.APP_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlNormalNoOtherLang.append(" 		CONCAT(CAST(MEDICAL_TIME_SET2.APP_TIME AS INTEGER)/60, ':',");
		sqlNormalNoOtherLang.append(" 			FORMAT(CAST(MEDICAL_TIME_SET2.APP_TIME AS INTEGER)%60,'0#')), NULL),");
		// R1_224 医療.日勤勤務時間.丸め
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_225 医療.日勤勤務時間.端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET1.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET1.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_226 医療.夜勤勤務時間.丸め
		sqlNormalNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlNormalNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		sqlNormalNoOtherLang.append(" 	NULL),");
		// R1_227 医療.夜勤勤務時間.端数
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET2.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET2.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_228 ０時跨ぎ.0時跨ぎ計算
		sqlNormalNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.OVER_DAY_CALC_SET = ?isTrue THEN ?isUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.OVER_DAY_CALC_SET = ?isFalse THEN ?isNotUseText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_229 その他.勤務種類が休暇の場合に就業時間を計算するか
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.HD_CAL_IS_CALCULATE = ?isTrue THEN ?isUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.HD_CAL_IS_CALCULATE = ?isFalse THEN ?isNotUseText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_57 その他.休暇加算時間を加算する場合に就業時間として加算するか
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.EXCEEDED_PRED_CALC_METHOD = ?calcMethodExceededPredAddVacationWorking THEN ?isUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.EXCEEDED_PRED_CALC_METHOD = ?calcMethodExceededPredAddVacationOvertime THEN ?isNotUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.EXCEEDED_PRED_CALC_METHOD = ?calcMethodExceededPredAddVacationWorking THEN ?isUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.EXCEEDED_PRED_CALC_METHOD = ?calcMethodExceededPredAddVacationOvertime THEN ?isNotUseText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_231 その他.休暇加算時間を加算する場合に就業時間として加算するか.残業枠
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.EXCEEDED_PRED_CALC_METHOD = ?calcMethodExceededPredAddVacationOvertime THEN FIXED_EXCEEDED_PRED_OT_FRAME.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.EXCEEDED_PRED_CALC_METHOD = ?calcMethodExceededPredAddVacationOvertime THEN DT_EXCEEDED_PRED_OT_FRAME.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_58 その他.休憩未取得時に就業時間として計算するか
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.OT_CALC_METHOD = ?calcMethodNoBreakWorking THEN ?isUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.OT_CALC_METHOD = ?calcMethodNoBreakOvertime THEN ?isNotUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.OT_CALC_METHOD = ?calcMethodNoBreakWorking THEN ?isUseText");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.OT_CALC_METHOD = ?calcMethodNoBreakOvertime THEN ?isNotUseText");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_232 その他.休憩未取得時に就業時間として計算するか.法定内残業枠
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.OT_CALC_METHOD = ?calcMethodNoBreakOvertime THEN FIXED_OT_IN_LAW_FRAME.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.OT_CALC_METHOD = ?calcMethodNoBreakOvertime THEN DT_OT_IN_LAW_FRAME.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END,");
		// R1_233 その他.休憩未取得時に就業時間として計算するか.法定外残業枠
		sqlNormalNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?fixedWork ");
		sqlNormalNoOtherLang.append(" 			AND FIXED_WORK_SET.OT_CALC_METHOD = ?calcMethodNoBreakOvertime THEN FIXED_OT_NOT_IN_LAW_FRAME.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?difftimeWork ");
		sqlNormalNoOtherLang.append(" 			AND DIFF_TIME_WORK_SET.OT_CALC_METHOD = ?calcMethodNoBreakOvertime THEN DT_OT_NOT_IN_LAW_FRAME.OT_FR_NAME");
		sqlNormalNoOtherLang.append(" 		 ELSE NULL");
		sqlNormalNoOtherLang.append(" 	END");
		sqlNormalNoOtherLang.append(" FROM");
		sqlNormalNoOtherLang.append(" 	(SELECT CID, WORKTIME_CD, NAME, ABNAME, SYMBOL, ABOLITION_ATR, DAILY_WORK_ATR, WORKTIME_SET_METHOD, NOTE, MEMO");
		sqlNormalNoOtherLang.append(" 		FROM KSHMT_WT WORK_TIME_SET");
		sqlNormalNoOtherLang.append(" 		WHERE CID = ?companyId AND DAILY_WORK_ATR = ?regularWork AND WORKTIME_SET_METHOD IN (?fixedWork, ?difftimeWork)) WORK_TIME_SET");
		sqlNormalNoOtherLang.append(" 	CROSS JOIN (VALUES(1),(2),(3),(4),(5),(6),(7),(8),(9),(10)) TEMP(ROW_ID)");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_COM_DISP_MODE WORKTIME_DISP_MODE");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORKTIME_DISP_MODE.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORKTIME_DISP_MODE.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM WORKTIME_COMMON_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORKTIME_COMMON_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORKTIME_COMMON_SET.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX FIXED_WORK_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_WORK_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_WORK_SET.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FIXED_EXCEEDED_PRED_OT_FRAME");
		sqlNormalNoOtherLang.append(" 		ON FIXED_WORK_SET.CID = FIXED_EXCEEDED_PRED_OT_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_WORK_SET.EXCEEDED_PRED_OT_FRAME_NO = FIXED_EXCEEDED_PRED_OT_FRAME.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FIXED_OT_IN_LAW_FRAME");
		sqlNormalNoOtherLang.append(" 		ON FIXED_WORK_SET.CID = FIXED_OT_IN_LAW_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_WORK_SET.OT_IN_LAW = FIXED_OT_IN_LAW_FRAME.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FIXED_OT_NOT_IN_LAW_FRAME");
		sqlNormalNoOtherLang.append(" 		ON FIXED_WORK_SET.CID = FIXED_OT_NOT_IN_LAW_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_WORK_SET.OT_NOT_IN_LAW = FIXED_OT_NOT_IN_LAW_FRAME.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF DIFF_TIME_WORK_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DIFF_TIME_WORK_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DIFF_TIME_WORK_SET.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME DT_EXCEEDED_PRED_OT_FRAME");
		sqlNormalNoOtherLang.append(" 		ON DIFF_TIME_WORK_SET.CID = DT_EXCEEDED_PRED_OT_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND DIFF_TIME_WORK_SET.EXCEEDED_PRED_OT_FRAME_NO = DT_EXCEEDED_PRED_OT_FRAME.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME DT_OT_IN_LAW_FRAME");
		sqlNormalNoOtherLang.append(" 		ON DIFF_TIME_WORK_SET.CID = DT_OT_IN_LAW_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND DIFF_TIME_WORK_SET.OT_IN_LAW = DT_OT_IN_LAW_FRAME.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME DT_OT_NOT_IN_LAW_FRAME");
		sqlNormalNoOtherLang.append(" 		ON DIFF_TIME_WORK_SET.CID = DT_OT_NOT_IN_LAW_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND DIFF_TIME_WORK_SET.OT_NOT_IN_LAW = DT_OT_NOT_IN_LAW_FRAME.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_COM_PRED_TIME PRED_TIME_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PRED_TIME_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PRED_TIME_SET.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_PRED_TS WORK_TIME_SHEET_SET1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORK_TIME_SHEET_SET1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORK_TIME_SHEET_SET1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SHEET_SET1.WORK_NO = ?timezoneUseOne");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_PRED_TS WORK_TIME_SHEET_SET2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORK_TIME_SHEET_SET2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORK_TIME_SHEET_SET2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SHEET_SET2.WORK_NO = ?timezoneUseTwo");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_WORK_TS FIXED_WORK_TIME_SET1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_WORK_TIME_SET1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_WORK_TIME_SET1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_WORK_TIME_SET1.TIME_FRAME_NO");
		sqlNormalNoOtherLang.append(" 		AND FIXED_WORK_TIME_SET1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_WORK_TS FIXED_WORK_TIME_SET2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_WORK_TIME_SET2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_WORK_TIME_SET2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_WORK_TIME_SET2.TIME_FRAME_NO");
		sqlNormalNoOtherLang.append(" 		AND FIXED_WORK_TIME_SET2.AM_PM_ATR = ?amPmAtrAm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_WORK_TS FIXED_WORK_TIME_SET3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_WORK_TIME_SET3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_WORK_TIME_SET3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_WORK_TIME_SET3.TIME_FRAME_NO");
		sqlNormalNoOtherLang.append(" 		AND FIXED_WORK_TIME_SET3.AM_PM_ATR = ?amPmAtrPm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_WORK_TS DT_WORK_TIME_SET1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_WORK_TIME_SET1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_WORK_TIME_SET1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DT_WORK_TIME_SET1.TIME_FRAME_NO");
		sqlNormalNoOtherLang.append(" 		AND DT_WORK_TIME_SET1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_WORK_TS DT_WORK_TIME_SET2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_WORK_TIME_SET2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_WORK_TIME_SET2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DT_WORK_TIME_SET2.TIME_FRAME_NO");
		sqlNormalNoOtherLang.append(" 		AND DT_WORK_TIME_SET2.AM_PM_ATR = ?amPmAtrAm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_WORK_TS DT_WORK_TIME_SET3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_WORK_TIME_SET3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_WORK_TIME_SET3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DT_WORK_TIME_SET3.TIME_FRAME_NO");
		sqlNormalNoOtherLang.append(" 		AND DT_WORK_TIME_SET3.AM_PM_ATR = ?amPmAtrPm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_OVER_TS FIXED_OT_TIME_SET1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_OT_TIME_SET1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_OT_TIME_SET1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_OT_TIME_SET1.WORKTIME_NO");
		sqlNormalNoOtherLang.append(" 		AND FIXED_OT_TIME_SET1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FIXED_OT_FRAME1");
		sqlNormalNoOtherLang.append(" 		ON FIXED_OT_TIME_SET1.CID = FIXED_OT_FRAME1.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_OT_TIME_SET1.OT_FRAME_NO = FIXED_OT_FRAME1.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FIXED_LEGAL_OT_FRAME1");
		sqlNormalNoOtherLang.append(" 		ON FIXED_OT_TIME_SET1.CID = FIXED_LEGAL_OT_FRAME1.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_OT_TIME_SET1.LEGAL_OT_FRAME_NO = FIXED_LEGAL_OT_FRAME1.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_OVER_TS FIXED_OT_TIME_SET2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_OT_TIME_SET2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_OT_TIME_SET2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_OT_TIME_SET2.WORKTIME_NO");
		sqlNormalNoOtherLang.append(" 		AND FIXED_OT_TIME_SET2.AM_PM_ATR = ?amPmAtrAm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FIXED_OT_FRAME2");
		sqlNormalNoOtherLang.append(" 		ON FIXED_OT_TIME_SET2.CID = FIXED_OT_FRAME2.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_OT_TIME_SET2.OT_FRAME_NO = FIXED_OT_FRAME2.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FIXED_LEGAL_OT_FRAME2");
		sqlNormalNoOtherLang.append(" 		ON FIXED_OT_TIME_SET2.CID = FIXED_LEGAL_OT_FRAME2.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_OT_TIME_SET2.LEGAL_OT_FRAME_NO = FIXED_LEGAL_OT_FRAME2.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_OVER_TS FIXED_OT_TIME_SET3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_OT_TIME_SET3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_OT_TIME_SET3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_OT_TIME_SET3.WORKTIME_NO");
		sqlNormalNoOtherLang.append(" 		AND FIXED_OT_TIME_SET3.AM_PM_ATR = ?amPmAtrPm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FIXED_OT_FRAME3");
		sqlNormalNoOtherLang.append(" 		ON FIXED_OT_TIME_SET3.CID = FIXED_OT_FRAME3.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_OT_TIME_SET3.OT_FRAME_NO = FIXED_OT_FRAME3.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FIXED_LEGAL_OT_FRAME3");
		sqlNormalNoOtherLang.append(" 		ON FIXED_OT_TIME_SET3.CID = FIXED_LEGAL_OT_FRAME3.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_OT_TIME_SET3.LEGAL_OT_FRAME_NO = FIXED_LEGAL_OT_FRAME3.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_OVER_TS DT_OT_TIME_SET1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_OT_TIME_SET1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_OT_TIME_SET1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DT_OT_TIME_SET1.WORK_TIME_NO");
		sqlNormalNoOtherLang.append(" 		AND DT_OT_TIME_SET1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME DT_OT_FRAME1");
		sqlNormalNoOtherLang.append(" 		ON DT_OT_TIME_SET1.CID = DT_OT_FRAME1.CID");
		sqlNormalNoOtherLang.append(" 		AND DT_OT_TIME_SET1.OT_FRAME_NO = DT_OT_FRAME1.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME DT_LEGAL_OT_FRAME1");
		sqlNormalNoOtherLang.append(" 		ON DT_OT_TIME_SET1.CID = DT_LEGAL_OT_FRAME1.CID");
		sqlNormalNoOtherLang.append(" 		AND DT_OT_TIME_SET1.LEGAL_OT_FRAME_NO = DT_LEGAL_OT_FRAME1.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_OVER_TS DT_OT_TIME_SET2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_OT_TIME_SET2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_OT_TIME_SET2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DT_OT_TIME_SET2.WORK_TIME_NO");
		sqlNormalNoOtherLang.append(" 		AND DT_OT_TIME_SET2.AM_PM_ATR = ?amPmAtrAm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME DT_OT_FRAME2");
		sqlNormalNoOtherLang.append(" 		ON DT_OT_TIME_SET2.CID = DT_OT_FRAME2.CID");
		sqlNormalNoOtherLang.append(" 		AND DT_OT_TIME_SET2.OT_FRAME_NO = DT_OT_FRAME2.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME DT_LEGAL_OT_FRAME2");
		sqlNormalNoOtherLang.append(" 		ON DT_OT_TIME_SET2.CID = DT_LEGAL_OT_FRAME2.CID");
		sqlNormalNoOtherLang.append(" 		AND DT_OT_TIME_SET2.LEGAL_OT_FRAME_NO = DT_LEGAL_OT_FRAME2.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_OVER_TS DT_OT_TIME_SET3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_OT_TIME_SET3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_OT_TIME_SET3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DT_OT_TIME_SET3.WORK_TIME_NO");
		sqlNormalNoOtherLang.append(" 		AND DT_OT_TIME_SET3.AM_PM_ATR = ?amPmAtrPm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME DT_OT_FRAME3");
		sqlNormalNoOtherLang.append(" 		ON DT_OT_TIME_SET3.CID = DT_OT_FRAME3.CID");
		sqlNormalNoOtherLang.append(" 		AND DT_OT_TIME_SET3.OT_FRAME_NO = DT_OT_FRAME3.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME DT_LEGAL_OT_FRAME3");
		sqlNormalNoOtherLang.append(" 		ON DT_OT_TIME_SET3.CID = DT_LEGAL_OT_FRAME3.CID");
		sqlNormalNoOtherLang.append(" 		AND DT_OT_TIME_SET3.LEGAL_OT_FRAME_NO = DT_LEGAL_OT_FRAME3.OT_FR_NO");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_PIORITY_SET PIORITY_SET1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND PIORITY_SET1.STAMP_ATR = ?stampPiorityAtrGoingWork");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_PIORITY_SET PIORITY_SET2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND PIORITY_SET2.STAMP_ATR = ?stampPiorityAtrLeaveWork");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND PIORITY_SET3.STAMP_ATR = ?stampPiorityAtrEntering");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET4");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET4.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET4.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND PIORITY_SET4.STAMP_ATR = ?stampPiorityAtrExit");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET5");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET5.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET5.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND PIORITY_SET5.STAMP_ATR = ?stampPiorityAtrPcLogin");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET6");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET6.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET6.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND PIORITY_SET6.STAMP_ATR = ?stampPiorityAtrPcLogout");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_ROUNDING_SET ROUNDING_SET1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND ROUNDING_SET1.ATR = ?superiorityAttendance");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_ROUNDING_SET ROUNDING_SET2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND ROUNDING_SET2.ATR = ?superiorityOfficeWork");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_ROUNDING_SET ROUNDING_SET3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND ROUNDING_SET3.ATR = ?superiorityGoOut");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_ROUNDING_SET ROUNDING_SET4");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET4.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET4.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND ROUNDING_SET4.ATR = ?superiorityTurnBack");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_STMP_REF_TS FIXED_STAMP_REFLECT1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_STAMP_REFLECT1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_STAMP_REFLECT1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND FIXED_STAMP_REFLECT1.WORK_NO = ?workNoOne");
		sqlNormalNoOtherLang.append(" 		AND FIXED_STAMP_REFLECT1.ATR = ?goLeavingWorkAtrGoWork");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_STMP_REF_TS FIXED_STAMP_REFLECT2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_STAMP_REFLECT2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_STAMP_REFLECT2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND FIXED_STAMP_REFLECT2.WORK_NO = ?workNoOne");
		sqlNormalNoOtherLang.append(" 		AND FIXED_STAMP_REFLECT2.ATR = ?goLeavingWorkAtrLeaveWork");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_STMP_REF_TS FIXED_STAMP_REFLECT3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_STAMP_REFLECT3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_STAMP_REFLECT3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND FIXED_STAMP_REFLECT3.WORK_NO = ?workNoTwo");
		sqlNormalNoOtherLang.append(" 		AND FIXED_STAMP_REFLECT3.ATR = ?goLeavingWorkAtrGoWork");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_STMP_REF_TS FIXED_STAMP_REFLECT4");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_STAMP_REFLECT4.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_STAMP_REFLECT4.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND FIXED_STAMP_REFLECT4.WORK_NO = ?workNoTwo");
		sqlNormalNoOtherLang.append(" 		AND FIXED_STAMP_REFLECT4.ATR = ?goLeavingWorkAtrLeaveWork");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_STMP_REF_TS DT_STAMP_REFLECT1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_STAMP_REFLECT1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_STAMP_REFLECT1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND DT_STAMP_REFLECT1.WORK_NO = ?workNoOne");
		sqlNormalNoOtherLang.append(" 		AND DT_STAMP_REFLECT1.ATR = ?goLeavingWorkAtrGoWork");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_STMP_REF_TS DT_STAMP_REFLECT2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_STAMP_REFLECT2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_STAMP_REFLECT2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND DT_STAMP_REFLECT2.WORK_NO = ?workNoOne");
		sqlNormalNoOtherLang.append(" 		AND DT_STAMP_REFLECT2.ATR = ?goLeavingWorkAtrLeaveWork");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_STMP_REF_TS DT_STAMP_REFLECT3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_STAMP_REFLECT3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_STAMP_REFLECT3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND DT_STAMP_REFLECT3.WORK_NO = ?workNoTwo");
		sqlNormalNoOtherLang.append(" 		AND DT_STAMP_REFLECT3.ATR = ?goLeavingWorkAtrGoWork");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_STMP_REF_TS DT_STAMP_REFLECT4");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_STAMP_REFLECT4.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_STAMP_REFLECT4.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND DT_STAMP_REFLECT4.WORK_NO = ?workNoTwo");
		sqlNormalNoOtherLang.append(" 		AND DT_STAMP_REFLECT4.ATR = ?goLeavingWorkAtrLeaveWork");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_BR_WEK_TS FIXED_HALF_REST_SET1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_HALF_REST_SET1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_HALF_REST_SET1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_HALF_REST_SET1.PERIOD_NO");
		sqlNormalNoOtherLang.append(" 		AND FIXED_HALF_REST_SET1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_BR_WEK_TS FIXED_HALF_REST_SET2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_HALF_REST_SET2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_HALF_REST_SET2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_HALF_REST_SET2.PERIOD_NO");
		sqlNormalNoOtherLang.append(" 		AND FIXED_HALF_REST_SET2.AM_PM_ATR = ?amPmAtrAm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_BR_WEK_TS FIXED_HALF_REST_SET3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_HALF_REST_SET3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_HALF_REST_SET3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_HALF_REST_SET3.PERIOD_NO");
		sqlNormalNoOtherLang.append(" 		AND FIXED_HALF_REST_SET3.AM_PM_ATR = ?amPmAtrPm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_BR_WEK_TS DT_HALF_REST_TIME1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_HALF_REST_TIME1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_HALF_REST_TIME1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DT_HALF_REST_TIME1.PERIOD_NO");
		sqlNormalNoOtherLang.append(" 		AND DT_HALF_REST_TIME1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_BR_WEK_TS DT_HALF_REST_TIME2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_HALF_REST_TIME2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_HALF_REST_TIME2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DT_HALF_REST_TIME2.PERIOD_NO");
		sqlNormalNoOtherLang.append(" 		AND DT_HALF_REST_TIME2.AM_PM_ATR = ?amPmAtrAm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_BR_WEK_TS DT_HALF_REST_TIME3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_HALF_REST_TIME3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_HALF_REST_TIME3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DT_HALF_REST_TIME3.PERIOD_NO");
		sqlNormalNoOtherLang.append(" 		AND DT_HALF_REST_TIME3.AM_PM_ATR = ?amPmAtrPm");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_HOL_TS FIXED_HOL_TIME_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_HOL_TIME_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_HOL_TIME_SET.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_HOL_TIME_SET.WORKTIME_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME FIXED_HOL_FRAME");
		sqlNormalNoOtherLang.append(" 		ON FIXED_HOL_TIME_SET.CID = FIXED_HOL_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_HOL_TIME_SET.HOL_FRAME_NO = FIXED_HOL_FRAME.WDO_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME FIXED_OUT_HOL_FRAME");
		sqlNormalNoOtherLang.append(" 		ON FIXED_HOL_TIME_SET.CID = FIXED_OUT_HOL_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_HOL_TIME_SET.OUT_HOL_FRAME_NO = FIXED_OUT_HOL_FRAME.WDO_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME FIXED_PUB_HOL_FRAME");
		sqlNormalNoOtherLang.append(" 		ON FIXED_HOL_TIME_SET.CID = FIXED_PUB_HOL_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND FIXED_HOL_TIME_SET.PUB_HOL_FRAME_NO = FIXED_PUB_HOL_FRAME.WDO_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_HOL_TS DIFF_TIME_HOL_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DIFF_TIME_HOL_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DIFF_TIME_HOL_SET.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DIFF_TIME_HOL_SET.WORK_TIME_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME DT_HOL_FRAME");
		sqlNormalNoOtherLang.append(" 		ON DIFF_TIME_HOL_SET.CID = DT_HOL_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND DIFF_TIME_HOL_SET.HOL_FRAME_NO = DT_HOL_FRAME.WDO_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME DT_OUT_HOL_FRAME");
		sqlNormalNoOtherLang.append(" 		ON DIFF_TIME_HOL_SET.CID = DT_OUT_HOL_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND DIFF_TIME_HOL_SET.OUT_HOL_FRAME_NO = DT_OUT_HOL_FRAME.WDO_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME DT_PUB_HOL_FRAME");
		sqlNormalNoOtherLang.append(" 		ON DIFF_TIME_HOL_SET.CID = DT_PUB_HOL_FRAME.CID");
		sqlNormalNoOtherLang.append(" 		AND DIFF_TIME_HOL_SET.PUB_HOL_FRAME_NO = DT_PUB_HOL_FRAME.WDO_FR_NO");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FIX_BR_HOL_TS FIXED_HOL_REST_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FIXED_HOL_REST_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FIXED_HOL_REST_SET.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = FIXED_HOL_REST_SET.PERIOD_NO + 1");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_DIF_BR_HOL_TS DT_HOL_REST_TIME");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = DT_HOL_REST_TIME.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = DT_HOL_REST_TIME.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND TEMP.ROW_ID = DT_HOL_REST_TIME.PERIOD_NO");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT WORKTIME_GO_OUT_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORKTIME_GO_OUT_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORKTIME_GO_OUT_SET.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT_ROUND SPECIAL_ROUND_OUT1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SPECIAL_ROUND_OUT1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SPECIAL_ROUND_OUT1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND SPECIAL_ROUND_OUT1.ROUNDING_TIME_TYPE = ?roundingTimeTypeWorkTimezone");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT_ROUND SPECIAL_ROUND_OUT2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SPECIAL_ROUND_OUT2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SPECIAL_ROUND_OUT2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND SPECIAL_ROUND_OUT2.ROUNDING_TIME_TYPE = ?roundingTimeTypePubHolWorkTimezone");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT_ROUND SPECIAL_ROUND_OUT3");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SPECIAL_ROUND_OUT3.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SPECIAL_ROUND_OUT3.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND SPECIAL_ROUND_OUT3.ROUNDING_TIME_TYPE = ?roundingTimeTypeOtTimezone");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_LATETIME OTHER_LATE_EARLY1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = OTHER_LATE_EARLY1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = OTHER_LATE_EARLY1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND OTHER_LATE_EARLY1.LATE_EARLY_ATR = ?lateEarlyAtrLate");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_LATETIME OTHER_LATE_EARLY2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = OTHER_LATE_EARLY2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = OTHER_LATE_EARLY2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND OTHER_LATE_EARLY2.LATE_EARLY_ATR = ?lateEarlyAtrEarly");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_LATETIME_MNG LATE_EARLY_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = LATE_EARLY_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = LATE_EARLY_SET.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 	LEFT JOIN KRCMT_BONUS_PAY_SET BONUS_PAY_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = BONUS_PAY_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORKTIME_COMMON_SET.RAISING_SALARY_SET = BONUS_PAY_SET.BONUS_PAY_SET_CD");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_HDCOM SUBSTITUTION_SET1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SUBSTITUTION_SET1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SUBSTITUTION_SET1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND SUBSTITUTION_SET1.ORIGIN_ATR = ?fromOverTime");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_HDCOM SUBSTITUTION_SET2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SUBSTITUTION_SET2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SUBSTITUTION_SET2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND SUBSTITUTION_SET2.ORIGIN_ATR = ?workDayOffTime");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_TEMPORARY TEMP_WORKTIME_SET");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = TEMP_WORKTIME_SET.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = TEMP_WORKTIME_SET.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_MEDICAL MEDICAL_TIME_SET1");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = MEDICAL_TIME_SET1.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = MEDICAL_TIME_SET1.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND MEDICAL_TIME_SET1.WORK_SYS_ATR = ?workSystemAtrDayShift");
		sqlNormalNoOtherLang.append(" 	JOIN KSHMT_WT_COM_MEDICAL MEDICAL_TIME_SET2");
		sqlNormalNoOtherLang.append(" 		ON WORK_TIME_SET.CID = MEDICAL_TIME_SET2.CID");
		sqlNormalNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = MEDICAL_TIME_SET2.WORKTIME_CD");
		sqlNormalNoOtherLang.append(" 		AND MEDICAL_TIME_SET2.WORK_SYS_ATR = ?workSystemAtrNightShift");
		sqlNormalNoOtherLang.append(" ORDER BY WORK_TIME_SET.WORKTIME_CD, TEMP.ROW_ID;");

		SELECT_WORK_TIME_NORMAL_NO_OTHER_LANGUAGE = sqlNormalNoOtherLang.toString();

		StringBuilder sqlFlowNoOtherLang = new StringBuilder();
		sqlFlowNoOtherLang.append(" SELECT");
		// R2_51 コード
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.WORKTIME_CD, NULL),");
		// R2_52 名称
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.NAME, NULL),");
		// R2_53 略名
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.ABNAME, NULL),");
		// R2_54 記号
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.SYMBOL, NULL),");
		// R2_55 廃止
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.ABOLITION_ATR = ?isAbolish THEN ?isAbolishText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.ABOLITION_ATR = ?notAbolish THEN ?notAbolishText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END, ");
		// R2_56 勤務形態
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.DAILY_WORK_ATR = ?regularWork THEN ?regularWorkText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_57 設定方法
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.WORKTIME_SET_METHOD = ?flowWork THEN ?flowWorkText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_58 モード
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?detailModeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?simpleMode THEN ?simpleModeText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_60 備考
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.NOTE, NULL),");
		// R2_61 コメント
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.MEMO, NULL),");
		// R2_62 所定設定.1日の始まりの時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.START_DATE_CLOCK IS NOT NULL,");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.START_DATE_CLOCK AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.START_DATE_CLOCK AS INTEGER)%60,'0#')), NULL),");
		// R2_63 所定設定.1日の範囲時間.時間
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND PRED_TIME_SET.RANGE_TIME_DAY IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CAST(PRED_TIME_SET.RANGE_TIME_DAY AS INTEGER)/60, NULL),");
		// R2_64 所定設定.1日の範囲時間.夜勤シフト
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.NIGHT_SHIFT_ATR = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlFlowNoOtherLang.append(" 			THEN ?isNightShiftText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.NIGHT_SHIFT_ATR = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlFlowNoOtherLang.append(" 			THEN ?isNotNightShiftText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_65 所定設定.就業時刻1回目.始業時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SHEET_SET1.START_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(WORK_TIME_SHEET_SET1.START_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(WORK_TIME_SHEET_SET1.START_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_66 所定設定.就業時刻1回目.終業時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SHEET_SET1.END_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(WORK_TIME_SHEET_SET1.END_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(WORK_TIME_SHEET_SET1.END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_67 所定設定.就業時刻1回目.残業を含めた所定時間帯を設定する
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.IS_INCLUDE_OT = ?isTrue THEN ?isIncludeOtText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.IS_INCLUDE_OT = ?isFalse THEN ?isNotIncludeOtText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_4 所定設定.就業時刻2回目
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue THEN ?isUseSecondWorkingDayText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SHEET_SET2.USE_ATR = ?isFalse THEN ?isNotUseSecondWorkingDayText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_69 所定設定.就業時刻2回目.始業時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SHEET_SET2.START_TIME IS NOT NULL ");
		sqlFlowNoOtherLang.append(" 		AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(WORK_TIME_SHEET_SET2.START_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(WORK_TIME_SHEET_SET2.START_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_70 所定設定.就業時刻2回目.終業時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SHEET_SET2.END_TIME IS NOT NULL");
		sqlFlowNoOtherLang.append(" 		AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(WORK_TIME_SHEET_SET2.END_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(WORK_TIME_SHEET_SET2.END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_71 所定設定.半日勤務.前半終了時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.MORNING_END_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.MORNING_END_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.MORNING_END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_72 所定設定.半日勤務.後半開始時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.AFTERNOON_START_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.AFTERNOON_START_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.AFTERNOON_START_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_73 所定設定.所定時間.1日
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.PRED_ONE_DAY IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.PRED_ONE_DAY AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.PRED_ONE_DAY AS INTEGER)%60,'0#')), NULL),");
		// R2_74 所定設定.所定時間.午前
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.PRED_MORNING IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.PRED_MORNING AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.PRED_MORNING AS INTEGER)%60,'0#')), NULL),");
		// R2_75 所定設定.所定時間.午後
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.PRED_AFTERNOON IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.PRED_AFTERNOON AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.PRED_AFTERNOON AS INTEGER)%60,'0#')), NULL),");
		// R2_76 所定設定.休暇取得時加算時間.1日
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.WORK_ADD_ONE_DAY IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.WORK_ADD_ONE_DAY AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.WORK_ADD_ONE_DAY AS INTEGER)%60,'0#')), NULL),");
		// R2_77 所定設定.休暇取得時加算時間.午前
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.WORK_ADD_MORNING IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.WORK_ADD_MORNING AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.WORK_ADD_MORNING AS INTEGER)%60,'0#')), NULL),");
		// R2_78 所定設定.休暇取得時加算時間.午後
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.WORK_ADD_AFTERNOON IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.WORK_ADD_AFTERNOON AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.WORK_ADD_AFTERNOON AS INTEGER)%60,'0#')), NULL),");
		// R2_79 勤務時間帯.就業時間帯丸め.丸め
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 		CASE WHEN FLOW_TIME_ZONE.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_TIME_ZONE.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_TIME_ZONE.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_TIME_ZONE.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_TIME_ZONE.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_TIME_ZONE.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_TIME_ZONE.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_TIME_ZONE.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 			 ELSE NULL");
		sqlFlowNoOtherLang.append(" 		END,");
		sqlFlowNoOtherLang.append(" 		NULL),");
		// R2_80 勤務時間帯.就業時間帯丸め.丸め
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND FLOW_TIME_ZONE.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_TIME_ZONE.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_81 勤務時間帯.予定開始時刻より前に出勤した場合の計算方法
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND FLOW_WORK_SET.CALC_STR_TIME_SET = ?calcFromPlanStartTime");
		sqlFlowNoOtherLang.append(" 			THEN ?calcFromPlanStartTimeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_WORK_SET.CALC_STR_TIME_SET = ?calcFromWorkTime");
		sqlFlowNoOtherLang.append(" 			THEN ?calcFromWorkTimeText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_82 勤務時間帯.所定時間に変更（予定開始・終了時刻の変更）があった場合、所定時間が終わり次第、残業を求める
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_WORK_SET.FIXED_CHANGE_ATR = ?fixedChangeAtrNotChange");
		sqlFlowNoOtherLang.append(" 			THEN ?fixedChangeAtrNotChangeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_WORK_SET.FIXED_CHANGE_ATR = ?fixedChangeAtrAfterShift");
		sqlFlowNoOtherLang.append(" 			THEN ?fixedChangeAtrAfterShiftText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_WORK_SET.FIXED_CHANGE_ATR = ?fixedChangeAtrBeforeAfterShift");
		sqlFlowNoOtherLang.append(" 			THEN ?fixedChangeAtrBeforeAfterShiftText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_83 残業時間帯.経過時間
		sqlFlowNoOtherLang.append(" 	IIF(OT_TIME_ZONE.PASSAGE_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(OT_TIME_ZONE.PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(OT_TIME_ZONE.PASSAGE_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_84 残業時間帯.丸め
		sqlFlowNoOtherLang.append(" 	CASE WHEN OT_TIME_ZONE.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OT_TIME_ZONE.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OT_TIME_ZONE.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OT_TIME_ZONE.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OT_TIME_ZONE.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OT_TIME_ZONE.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OT_TIME_ZONE.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OT_TIME_ZONE.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_85 残業時間帯.端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN OT_TIME_ZONE.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN OT_TIME_ZONE.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_86 残業時間帯.残業枠
		sqlFlowNoOtherLang.append(" 	FLOW_OT_FRAME.OT_FR_NAME,");
		// R2_87 残業時間帯.法定内残業枠
		sqlFlowNoOtherLang.append(" 	IIF(WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, FLOW_LEGAL_OT_FRAME.OT_FR_NAME, NULL),");
		// R2_88 残業時間帯.積残順序
		sqlFlowNoOtherLang.append(" 	IIF(WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, OT_TIME_ZONE.SETTLEMENT_ORDER, NULL),");
		// R2_89 打刻時間帯.優先設定.出勤
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET1.PIORITY_ATR = ?beforePiority");
		sqlFlowNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET1.PIORITY_ATR = ?afterPiority");
		sqlFlowNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_90 打刻時間帯.優先設定.退勤
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET2.PIORITY_ATR = ?beforePiority");
		sqlFlowNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET2.PIORITY_ATR = ?afterPiority");
		sqlFlowNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_91 打刻時間帯.打刻丸め.出勤
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 		CASE WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 			 ELSE NULL");
		sqlFlowNoOtherLang.append(" 		END,");
		sqlFlowNoOtherLang.append(" 		NULL),");
		// R2_92 打刻時間帯.打刻丸め.出勤前後設定
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET1.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlFlowNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET1.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlFlowNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_93 打刻時間帯.打刻丸め.退勤
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 		CASE WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 			 ELSE NULL");
		sqlFlowNoOtherLang.append(" 		END,");
		sqlFlowNoOtherLang.append(" 		NULL),");
		// R2_94 打刻時間帯.打刻丸め.退勤前後設定
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET2.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlFlowNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET2.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlFlowNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_95 打刻詳細設定.出勤反映時間帯.開始時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND STAMP_REFLECT1.STR_CLOCK IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(STAMP_REFLECT1.STR_CLOCK AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(STAMP_REFLECT1.STR_CLOCK AS INTEGER)%60,'0#')), NULL),");
		// R2_96 打刻詳細設定.出勤反映時間帯.終了時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND STAMP_REFLECT1.END_CLOCK IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(STAMP_REFLECT1.END_CLOCK AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(STAMP_REFLECT1.END_CLOCK AS INTEGER)%60,'0#')), NULL),");
		// R2_97 打刻詳細設定.出勤反映時間帯.2勤務目の開始（1勤務目の退勤から分けられる時間）
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND FSTAMP_REFLECT_TIME.TWO_REFLECT_BASIC_TIME IS NOT NULL ");
		sqlFlowNoOtherLang.append(" 		AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND WORK_TIME_SHEET_SET2.USE_ATR = ?isTrue, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(FSTAMP_REFLECT_TIME.TWO_REFLECT_BASIC_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(FSTAMP_REFLECT_TIME.TWO_REFLECT_BASIC_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_98 打刻詳細設定.退勤反映時間帯.開始時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND STAMP_REFLECT2.STR_CLOCK IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(STAMP_REFLECT2.STR_CLOCK AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(STAMP_REFLECT2.STR_CLOCK AS INTEGER)%60,'0#')), NULL),");
		// R2_99 打刻詳細設定.退勤反映時間帯.終了時刻
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND STAMP_REFLECT2.END_CLOCK IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(STAMP_REFLECT2.END_CLOCK AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(STAMP_REFLECT2.END_CLOCK AS INTEGER)%60,'0#')), NULL),");
		// R2_100 打刻詳細設定.優先設定.入門
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET3.PIORITY_ATR = ?beforePiority");
		sqlFlowNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET3.PIORITY_ATR = ?afterPiority");
		sqlFlowNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_101 打刻詳細設定.優先設定.退門
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET4.PIORITY_ATR = ?beforePiority");
		sqlFlowNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET4.PIORITY_ATR = ?afterPiority");
		sqlFlowNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_102 打刻詳細設定.優先設定.PCログオン
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET5.PIORITY_ATR = ?beforePiority");
		sqlFlowNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET5.PIORITY_ATR = ?afterPiority");
		sqlFlowNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_103 打刻詳細設定.優先設定.PCログオフ
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET6.PIORITY_ATR = ?beforePiority");
		sqlFlowNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET6.PIORITY_ATR = ?afterPiority");
		sqlFlowNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_104 打刻詳細設定.打刻丸め.外出
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 		CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 			 ELSE NULL");
		sqlFlowNoOtherLang.append(" 		END,");
		sqlFlowNoOtherLang.append(" 		NULL),");
		// R2_105 打刻詳細設定.打刻丸め.外出前後設定
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL ");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET3.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlFlowNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET3.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlFlowNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_106 打刻詳細設定.打刻丸め.戻り
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 		CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 			 ELSE NULL");
		sqlFlowNoOtherLang.append(" 		END,");
		sqlFlowNoOtherLang.append(" 		NULL),");
		// R2_107 打刻詳細設定.打刻丸め.戻り前後設定
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL ");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET4.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlFlowNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET4.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlFlowNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_108 休憩時間帯.休憩時間の固定
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND FLOW_RT_SET2.FIX_REST_TIME = ?isTrue THEN ?isUseText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_RT_SET2.FIX_REST_TIME = ?isFalse THEN ?isNotUseText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_109 休憩時間帯.休憩時間の固定する.開始時間
		sqlFlowNoOtherLang.append(" 	IIF(FLOW_FIXED_RT_SET2.STR_DAY IS NOT NULL AND FLOW_RT_SET2.FIX_REST_TIME = ?isTrue, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(FLOW_FIXED_RT_SET2.STR_DAY AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(FLOW_FIXED_RT_SET2.STR_DAY AS INTEGER)%60,'0#')), NULL),");
		// R2_110 休憩時間帯.休憩時間の固定する.終了時間
		sqlFlowNoOtherLang.append(" 	IIF(FLOW_FIXED_RT_SET2.END_DAY IS NOT NULL AND FLOW_RT_SET2.FIX_REST_TIME = ?isTrue, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(FLOW_FIXED_RT_SET2.END_DAY AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(FLOW_FIXED_RT_SET2.END_DAY AS INTEGER)%60,'0#')), NULL),");
		// R2_111 休憩時間帯.休憩時間の固定しない.経過時間
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlowNoOtherLang.append(" 			THEN IIF(FLOW_FLOW_RT_SET2.PASSAGE_TIME IS NOT NULL AND FLOW_RT_SET2.FIX_REST_TIME = ?isFalse, ");
		sqlFlowNoOtherLang.append(" 					CONCAT(CAST(FLOW_FLOW_RT_SET2.PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 						FORMAT(CAST(FLOW_FLOW_RT_SET2.PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN '以降は下記の時間で繰り返す'");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '経過時間'");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlowNoOtherLang.append(" 			THEN IIF(FLOW_RT_SET2.AFTER_PASSAGE_TIME IS NOT NULL AND FLOW_RT_SET2.FIX_REST_TIME = ?isFalse AND FLOW_RT_SET2.USE_REST_AFTER_SET = ?isTrue, ");
		sqlFlowNoOtherLang.append(" 					CONCAT(CAST(FLOW_RT_SET2.AFTER_PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 						FORMAT(CAST(FLOW_RT_SET2.AFTER_PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_112 休憩時間帯.休憩時間の固定しない.休憩時間
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlowNoOtherLang.append(" 			THEN IIF(FLOW_FLOW_RT_SET2.REST_TIME IS NOT NULL AND FLOW_RT_SET2.FIX_REST_TIME = ?isFalse, ");
		sqlFlowNoOtherLang.append(" 					CONCAT(CAST(FLOW_FLOW_RT_SET2.REST_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 						FORMAT(CAST(FLOW_FLOW_RT_SET2.REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN ");
		sqlFlowNoOtherLang.append(" 			CASE WHEN FLOW_RT_SET2.FIX_REST_TIME = ?isFalse AND FLOW_RT_SET2.USE_REST_AFTER_SET = ?isTrue THEN ?isUseRestAfterSetText");
		sqlFlowNoOtherLang.append(" 				 WHEN FLOW_RT_SET2.FIX_REST_TIME = ?isFalse AND FLOW_RT_SET2.USE_REST_AFTER_SET = ?isFalse THEN ?isNotUseRestAfterSetText");
		sqlFlowNoOtherLang.append(" 				 ELSE NULL");
		sqlFlowNoOtherLang.append(" 			END");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '休憩時間'");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlowNoOtherLang.append(" 			THEN IIF(FLOW_RT_SET2.AFTER_REST_TIME IS NOT NULL AND FLOW_RT_SET2.FIX_REST_TIME = ?isFalse AND FLOW_RT_SET2.USE_REST_AFTER_SET = ?isTrue, ");
		sqlFlowNoOtherLang.append(" 					CONCAT(CAST(FLOW_RT_SET2.AFTER_REST_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 						FORMAT(CAST(FLOW_RT_SET2.AFTER_REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_116 休憩時間帯.休憩計算設定（詳細設定）.休憩中に退勤した場合の休憩時間の計算方法
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL ");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.COMMON_CALCULATE_METHOD = ?calcMethodAppropAll");
		sqlFlowNoOtherLang.append(" 			THEN ?calcMethodAppropAllText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.COMMON_CALCULATE_METHOD = ?calcMethodNotAppropAll");
		sqlFlowNoOtherLang.append(" 			THEN ?calcMethodNotAppropAllText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.COMMON_CALCULATE_METHOD = ?calcMethodOfficeWorkAppropAll");
		sqlFlowNoOtherLang.append(" 			THEN ?calcMethodOfficeWorkAppropAllText");
		sqlFlowNoOtherLang.append(" 		ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_117 休憩時間帯.休憩計算設定（詳細設定）.2勤務目設定
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL ");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.USE_PLURAL_WORK_REST_TIME = ?isTrue THEN ?isUsePluralWorkRestTimeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.USE_PLURAL_WORK_REST_TIME = ?isFalse THEN ?isNotUsePluralWorkRestTimeText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_118 休憩時間帯.休憩計算設定（詳細設定）.丸め
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 		CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL ");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_REST_SET.REST_SET_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_REST_SET.REST_SET_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_REST_SET.REST_SET_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_REST_SET.REST_SET_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_REST_SET.REST_SET_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_REST_SET.REST_SET_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_REST_SET.REST_SET_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 			 WHEN FLOW_REST_SET.REST_SET_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 			 ELSE NULL");
		sqlFlowNoOtherLang.append(" 		END,");
		sqlFlowNoOtherLang.append(" 		NULL),");
		// R2_119 休憩時間帯.休憩計算設定（詳細設定）.端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL ");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.REST_SET_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.REST_SET_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.REST_SET_ROUNDING = ?roundingDownOver THEN ?roundingDownOverText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_120 休憩時間帯.固定休憩設定（休憩時間の固定する）.実績での休憩計算方法
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLOW_RT_SET2.FIX_REST_TIME = ?isFalse THEN NULL ");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodReferMaster THEN ?restCalcMethodReferMasterText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodReferSchedule THEN ?restCalcMethodReferScheduleText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodWithoutRefer THEN ?restCalcMethodWithoutReferText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_121 休憩時間帯.固定休憩設定（休憩時間の固定する）.予定と実績の勤務が一致しな場合はマスタを参照する
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLOW_RT_SET2.FIX_REST_TIME = ?isTrue AND FLOW_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodReferSchedule, ");
		sqlFlowNoOtherLang.append(" 		IIF(FLOW_REST_SET.IS_CALC_FROM_SCHEDULE = ?isTrue, ?isCalcFromSchedule, ?isNotCalcFromSchedule), NULL),");
		// R2_122 休憩時間帯.固定休憩設定（休憩時間の固定する）.休憩時刻が無い場合はマスタから休憩時刻を参照する
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLOW_RT_SET2.FIX_REST_TIME = ?isTrue AND FLOW_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodReferSchedule, ");
		sqlFlowNoOtherLang.append(" 		IIF(FLOW_REST_SET.IS_REFER_REST_TIME = ?isTrue, ?isReferRestTime, ?isNotReferRestTime), NULL),");
		// R2_123 休憩時間帯.固定休憩設定（休憩時間の固定する）.私用外出を休憩として扱う
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLOW_RT_SET2.FIX_REST_TIME = ?isTrue AND FLOW_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodWithoutRefer, ");
		sqlFlowNoOtherLang.append(" 		IIF(FLOW_REST_SET.USER_PRIVATE_GO_OUT_REST = ?isTrue, ?userPrivateGoOutRest, ?notUserPrivateGoOutRest), NULL),");
		// R2_124 休憩時間帯.固定休憩設定（休憩時間の固定する）.組合外出を休憩として扱う
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLOW_RT_SET2.FIX_REST_TIME = ?isTrue AND FLOW_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodWithoutRefer, ");
		sqlFlowNoOtherLang.append(" 		IIF(FLOW_REST_SET.USER_ASSO_GO_OUT_REST = ?isTrue, ?userAssoGoOutRest, ?notUserAssoGoOutRest), NULL),");
		// R2_125 休憩時間帯.流動休憩設定（休憩時間の固定しない）.実績での休憩計算方法
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLOW_RT_SET2.FIX_REST_TIME = ?isFalse, ");
		sqlFlowNoOtherLang.append(" 		IIF(FLOW_REST_SET.FIXED_CALCULATE_METHOD = ?flowRestCalcMethodReferMaster, ?restCalcMethodReferMasterText, ?flowRestCalcMethodMasterAndStampText), NULL),");
		// R2_126 休憩時間帯.流動休憩設定（休憩時間の固定しない）.外出を休憩として扱う
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLOW_RT_SET2.FIX_REST_TIME = ?isFalse, ");
		sqlFlowNoOtherLang.append(" 		IIF(FLOW_REST_SET.USE_STAMP = ?isTrue, ?useStamp, ?notUserStamp), NULL),");
		// R2_127 休憩時間帯.流動休憩設定（休憩時間の固定しない）.外出の計上方法
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLOW_RT_SET2.FIX_REST_TIME = ?isFalse, ");
		sqlFlowNoOtherLang.append(" 		IIF(FLOW_REST_SET.USE_STAMP_CALC_METHOD = ?useRestTimeToCalc, ?useRestTimeToCalcText, ?useGoOutTimeToCalcText), NULL),");
		// R2_128 休憩時間帯.流動休憩設定（休憩時間の固定しない）.優先設定
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLOW_RT_SET2.FIX_REST_TIME = ?isFalse, ");
		sqlFlowNoOtherLang.append(" 		IIF(FLOW_REST_SET.TIME_MANAGER_SET_ATR = ?isClockManage, ?isClockManageText, ?notClockManageText), NULL),");
		// R2_129 休出時間帯.経過時間
		sqlFlowNoOtherLang.append(" 	IIF(FWORK_HOLIDAY_TIME.PASSAGE_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(FWORK_HOLIDAY_TIME.PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(FWORK_HOLIDAY_TIME.PASSAGE_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_130 休出時間帯.法定内休出枠
		sqlFlowNoOtherLang.append(" 	INLEGAL_BREAK_REST_TIME_FRAME.WDO_FR_NAME,");
		// R2_131 休出時間帯.法定外休出枠
		sqlFlowNoOtherLang.append(" 	OUT_LEGALB_REAK_REST_TIME_FRAME.WDO_FR_NAME,");
		// R2_132 休出時間帯.法定外休出枠（祝日）
		sqlFlowNoOtherLang.append(" 	OUT_LEGAL_PUBHOL_REST_TIME_FRAME.WDO_FR_NAME,");
		// R2_133 休出時間帯.丸め
		sqlFlowNoOtherLang.append(" 	CASE WHEN FWORK_HOLIDAY_TIME.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN FWORK_HOLIDAY_TIME.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN FWORK_HOLIDAY_TIME.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN FWORK_HOLIDAY_TIME.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN FWORK_HOLIDAY_TIME.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN FWORK_HOLIDAY_TIME.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN FWORK_HOLIDAY_TIME.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN FWORK_HOLIDAY_TIME.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_134 休出時間帯.端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN FWORK_HOLIDAY_TIME.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN FWORK_HOLIDAY_TIME.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_135 休出休憩.休憩時間の固定
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND FLOW_RT_SET1.FIX_REST_TIME = ?isTrue THEN ?isUseText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLOW_RT_SET1.FIX_REST_TIME = ?isFalse THEN ?isNotUseText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_136 休出休憩.固定する.開始時間
		sqlFlowNoOtherLang.append(" 	IIF(FLOW_FIXED_RT_SET1.STR_DAY IS NOT NULL AND FLOW_RT_SET1.FIX_REST_TIME = ?isTrue, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(FLOW_FIXED_RT_SET1.STR_DAY AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(FLOW_FIXED_RT_SET1.STR_DAY AS INTEGER)%60,'0#')), NULL),");
		// R2_137 休出休憩.固定する.終了時間
		sqlFlowNoOtherLang.append(" 	IIF(FLOW_FIXED_RT_SET1.END_DAY IS NOT NULL AND FLOW_RT_SET1.FIX_REST_TIME = ?isTrue, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(FLOW_FIXED_RT_SET1.END_DAY AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(FLOW_FIXED_RT_SET1.END_DAY AS INTEGER)%60,'0#')), NULL),");
		// R2_138 休出休憩.固定しない.経過時間
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlowNoOtherLang.append(" 			THEN IIF(FLOW_FLOW_RT_SET1.PASSAGE_TIME IS NOT NULL AND FLOW_RT_SET1.FIX_REST_TIME = ?isFalse, ");
		sqlFlowNoOtherLang.append(" 					CONCAT(CAST(FLOW_FLOW_RT_SET1.PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 						FORMAT(CAST(FLOW_FLOW_RT_SET1.PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN '以降は下記の時間で繰り返す'");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '経過時間'");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlowNoOtherLang.append(" 			THEN IIF(FLOW_RT_SET1.FIX_REST_TIME = ?isFalse AND FLOW_RT_SET1.USE_REST_AFTER_SET = ?isTrue AND FLOW_RT_SET1.AFTER_PASSAGE_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 					CONCAT(CAST(FLOW_RT_SET1.AFTER_PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 						FORMAT(CAST(FLOW_RT_SET1.AFTER_PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_139 休出休憩.固定しない.休憩時間
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlowNoOtherLang.append(" 			THEN IIF(FLOW_FLOW_RT_SET1.REST_TIME IS NOT NULL AND FLOW_RT_SET1.FIX_REST_TIME = ?isFalse, ");
		sqlFlowNoOtherLang.append(" 					CONCAT(CAST(FLOW_FLOW_RT_SET1.REST_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 						FORMAT(CAST(FLOW_FLOW_RT_SET1.REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN ");
		sqlFlowNoOtherLang.append(" 			CASE WHEN FLOW_RT_SET1.FIX_REST_TIME = ?isFalse AND FLOW_RT_SET1.USE_REST_AFTER_SET = ?isTrue THEN ?isUseRestAfterSetText");
		sqlFlowNoOtherLang.append(" 				 WHEN FLOW_RT_SET1.FIX_REST_TIME = ?isFalse AND FLOW_RT_SET1.USE_REST_AFTER_SET = ?isFalse THEN ?isNotUseRestAfterSetText");
		sqlFlowNoOtherLang.append(" 				 ELSE NULL");
		sqlFlowNoOtherLang.append(" 			END");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '休憩時間'");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlowNoOtherLang.append(" 			THEN IIF(FLOW_RT_SET1.FIX_REST_TIME = ?isFalse AND FLOW_RT_SET1.USE_REST_AFTER_SET = ?isTrue AND FLOW_RT_SET1.AFTER_REST_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 					CONCAT(CAST(FLOW_RT_SET1.AFTER_REST_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 						FORMAT(CAST(FLOW_RT_SET1.AFTER_REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_143 外出.外出丸め設定.同じ枠内での丸め設定
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_SAME_FRAME = ?isTrue THEN ?roudingAfterTotalText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_SAME_FRAME = ?isFalse THEN ?roudingAfterEachTimePeriodText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_144 外出.外出丸め設定.枠を跨る場合の丸め設定
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_CROSS_FRAME = ?isTrue THEN ?roudingAfterTotalText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_CROSS_FRAME = ?isFalse THEN ?roudingAfterEachTimePeriodText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_145 外出.私用・組合外出時間.就業時間帯
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_146 外出.私用・組合外出時間.丸め設定
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_147 外出.私用・組合外出時間.丸め設定端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_148 外出.私用・組合外出時間.残業時間帯
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_149 外出.私用・組合外出時間.丸め設定
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_150 外出.私用・組合外出時間.丸め設定端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_151 外出.私用・組合外出時間.休出時間帯
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_152 外出.私用・組合外出時間.丸め設定
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_153 外出.私用・組合外出時間.丸め設定端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_154 外出.私用・組合外出控除時間.就業時間帯
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_155 外出.私用・組合外出控除時間.丸め設定
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_156 外出.私用・組合外出控除時間.丸め設定端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_157 外出.私用・組合外出控除時間.残業時間帯
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_158 外出.私用・組合外出控除時間.丸め設定
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_159 外出.私用・組合外出控除時間.丸め設定端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_160 外出.私用・組合外出控除時間.休出時間帯
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_161 外出.私用・組合外出控除時間.丸め設定
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_162 外出.私用・組合外出控除時間.丸め設定端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_163 外出.公用・有償外出時間.就業時間帯
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_164 外出.公用・有償外出時間.丸め設定
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_165 外出.公用・有償外出時間.丸め設定端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_166 外出.公用・有償外出時間.残業時間帯
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_167 外出.公用・有償外出時間.丸め設定
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_168 外出.公用・有償外出時間.丸め設定端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_169 外出.公用・有償外出時間.休出時間帯
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_170 外出.公用・有償外出時間.丸め設定
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_171 外出.公用・有償外出時間.丸め設定端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_172 遅刻早退.遅刻早退時間丸め.遅刻丸め
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_173 遅刻早退.遅刻早退時間丸め.遅刻端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.RECORD_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.RECORD_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_174 遅刻早退.遅刻早退時間丸め.早退丸め
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_175 遅刻早退.遅刻早退時間丸め.早退端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.RECORD_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.RECORD_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_176 遅刻早退.遅刻早退控除時間丸め.遅刻丸め
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_177 遅刻早退.遅刻早退控除時間丸め.遅刻端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.DEDUCTION_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.DEDUCTION_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_178 遅刻早退.遅刻早退控除時間丸め.早退丸め
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_179 遅刻早退.遅刻早退控除時間丸め.早退端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.DEDUCTION_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.DEDUCTION_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_180 遅刻早退詳細設定.遅刻早退の判定設定.時間丁度の打刻は遅刻とする
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.EXTRACT_LATE_EARLY_TIME = ?isTrue THEN ?isExtractLateEarlyTimeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.EXTRACT_LATE_EARLY_TIME = ?isFalse THEN ?isNotExtractLateEarlyTimeText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_181 遅刻早退詳細設定.遅刻早退の判定設定.時間丁度の打刻は早退とする
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.EXTRACT_LATE_EARLY_TIME = ?isTrue THEN ?isExtractLateEarlyTimeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.EXTRACT_LATE_EARLY_TIME = ?isFalse THEN ?isNotExtractLateEarlyTimeText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_182 遅刻早退詳細設定.猶予時間.遅刻猶予時間
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.GRACE_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(OTHER_LATE_EARLY1.GRACE_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(OTHER_LATE_EARLY1.GRACE_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_183 遅刻早退詳細設定.猶予時間.遅刻猶予時間を就業時間に含める
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.INCLUDE_WORKTIME = ?isTrue THEN ?isIncludeWorktimeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.INCLUDE_WORKTIME = ?isFalse THEN ?isNotIncludeWorktimeText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_184 遅刻早退詳細設定.猶予時間.早退猶予時間
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.GRACE_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(OTHER_LATE_EARLY2.GRACE_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(OTHER_LATE_EARLY2.GRACE_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_185 遅刻早退詳細設定.猶予時間.早退猶予時間を就業時間に含める
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.INCLUDE_WORKTIME = ?isTrue THEN ?isIncludeWorktimeText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.INCLUDE_WORKTIME = ?isFalse THEN ?isNotIncludeWorktimeText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_186 加給.コード
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORKTIME_COMMON_SET.RAISING_SALARY_SET, NULL),");
		// R2_187 加給.名称
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND BONUS_PAY_SET.BONUS_PAY_SET_CD IS NOT NULL THEN BONUS_PAY_SET.BONUS_PAY_SET_NAME");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND BONUS_PAY_SET.BONUS_PAY_SET_CD IS NULL AND WORKTIME_COMMON_SET.RAISING_SALARY_SET IS NOT NULL AND WORKTIME_COMMON_SET.RAISING_SALARY_SET != '   ' THEN ?masterUnregistered");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_41 代休.休日出勤
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.USE_ATR = ?isTrue THEN ?isUseSubstitutionText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.USE_ATR = ?isFalse THEN ?isNotUseSubstitutionText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_189 代休.休日出勤.時間区分
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SUBSTITUTION_SET2.USE_ATR = ?isFalse THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.TRANFER_ATR = ?specifiedTimeSubHol THEN ?specifiedTimeSubHolText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.TRANFER_ATR = ?certainTimeExcSubHol THEN ?certainTimeExcSubHolText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_190 代休.休日出勤.１日
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.ONE_DAY_TIME IS NOT NULL ");
		sqlFlowNoOtherLang.append(" 		AND (WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR (SUBSTITUTION_SET2.USE_ATR = ?isTrue AND SUBSTITUTION_SET2.TRANFER_ATR = ?specifiedTimeSubHol)), ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET2.ONE_DAY_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET2.ONE_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_191 代休.休日出勤.半日
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.HALF_DAY_TIME IS NOT NULL ");
		sqlFlowNoOtherLang.append(" 		AND (WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR (SUBSTITUTION_SET2.USE_ATR = ?isTrue AND SUBSTITUTION_SET2.TRANFER_ATR = ?specifiedTimeSubHol)), ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET2.HALF_DAY_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET2.HALF_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_192 代休.休日出勤.一定時間
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET2.USE_ATR = ?isTrue ");
		sqlFlowNoOtherLang.append(" 		AND SUBSTITUTION_SET2.TRANFER_ATR = ?certainTimeExcSubHol AND SUBSTITUTION_SET2.CERTAIN_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET2.CERTAIN_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET2.CERTAIN_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_42 代休.残業
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.USE_ATR = ?isTrue THEN ?isUseSubstitutionText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.USE_ATR = ?isFalse THEN ?isNotUseSubstitutionText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_194 代休.残業.時間区分
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SUBSTITUTION_SET1.USE_ATR = ?isFalse THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.TRANFER_ATR = ?specifiedTimeSubHol THEN ?specifiedTimeSubHolText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.TRANFER_ATR = ?certainTimeExcSubHol THEN ?certainTimeExcSubHolText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_195 代休.残業.１日
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET1.USE_ATR = ?isTrue ");
		sqlFlowNoOtherLang.append(" 		AND SUBSTITUTION_SET1.TRANFER_ATR = ?specifiedTimeSubHol AND SUBSTITUTION_SET1.ONE_DAY_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET1.ONE_DAY_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET1.ONE_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_196 代休.残業.半日
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET1.USE_ATR = ?isTrue ");
		sqlFlowNoOtherLang.append(" 		AND SUBSTITUTION_SET1.TRANFER_ATR = ?specifiedTimeSubHol AND SUBSTITUTION_SET1.HALF_DAY_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET1.HALF_DAY_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET1.HALF_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_197 代休.残業.一定時間
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET1.USE_ATR = ?isTrue ");
		sqlFlowNoOtherLang.append(" 		AND SUBSTITUTION_SET1.TRANFER_ATR = ?certainTimeExcSubHol AND SUBSTITUTION_SET1.CERTAIN_TIME IS NOT NULL, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET1.CERTAIN_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET1.CERTAIN_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_198 深夜残業.深夜時間丸め
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_199 深夜残業.深夜時間端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.LATE_NIGHT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.LATE_NIGHT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_200 臨時.臨時丸め
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_201 臨時.臨時端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND TEMP_WORKTIME_SET.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND TEMP_WORKTIME_SET.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_202 育児.育児時間帯に勤務した場合の扱い
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.CHILD_CARE_WORK_USE = ?isTrue THEN ?childCareWorkUseText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.CHILD_CARE_WORK_USE = ?isFalse THEN ?childCareWorkNotUseText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_203 育児.介護時間帯に勤務した場合の扱い
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.NUR_TIMEZONE_WORK_USE = ?isTrue THEN ?nurTimezoneWorkUseText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.NUR_TIMEZONE_WORK_USE = ?isFalse THEN ?nurTimezoneWorkNotUseText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_204 医療.日勤申し送り時間
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET1.APP_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(MEDICAL_TIME_SET1.APP_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(MEDICAL_TIME_SET1.APP_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_205 医療.夜勤申し送り時間
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET2.APP_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlowNoOtherLang.append(" 		CONCAT(CAST(MEDICAL_TIME_SET2.APP_TIME AS INTEGER)/60, ':',");
		sqlFlowNoOtherLang.append(" 			FORMAT(CAST(MEDICAL_TIME_SET2.APP_TIME AS INTEGER)%60,'0#')), NULL),");
		// R2_206 医療.日勤勤務時間.丸め
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_207 医療.日勤勤務時間.端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET1.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET1.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_208 医療.夜勤勤務時間.丸め
		sqlFlowNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlowNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		sqlFlowNoOtherLang.append(" 	NULL),");
		// R2_209 医療.夜勤勤務時間.端数
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET2.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET2.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_210 ０時跨ぎ.0時跨ぎ計算
		sqlFlowNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.OVER_DAY_CALC_SET = ?isTrue THEN ?isUseText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.OVER_DAY_CALC_SET = ?isFalse THEN ?isNotUseText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END,");
		// R2_211 その他.勤務種類が休暇の場合に就業時間を計算するか
		sqlFlowNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.HD_CAL_IS_CALCULATE = ?isTrue THEN ?isUseText");
		sqlFlowNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.HD_CAL_IS_CALCULATE = ?isFalse THEN ?isNotUseText");
		sqlFlowNoOtherLang.append(" 		 ELSE NULL");
		sqlFlowNoOtherLang.append(" 	END ");
		sqlFlowNoOtherLang.append(" FROM");
		sqlFlowNoOtherLang.append(" 	(SELECT CID, WORKTIME_CD, NAME, ABNAME, SYMBOL, ABOLITION_ATR, DAILY_WORK_ATR, WORKTIME_SET_METHOD, NOTE, MEMO");
		sqlFlowNoOtherLang.append(" 		FROM KSHMT_WT WORK_TIME_SET");
		sqlFlowNoOtherLang.append(" 		WHERE CID = ?companyId AND DAILY_WORK_ATR = ?regularWork AND WORKTIME_SET_METHOD = ?flowWork) WORK_TIME_SET");
		sqlFlowNoOtherLang.append(" 	CROSS JOIN (VALUES(1),(2),(3),(4),(5),(6),(7),(8),(9),(10)) TEMP(ROW_ID)");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_COM_DISP_MODE WORKTIME_DISP_MODE");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORKTIME_DISP_MODE.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORKTIME_DISP_MODE.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM WORKTIME_COMMON_SET");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORKTIME_COMMON_SET.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORKTIME_COMMON_SET.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO FLOW_WORK_SET");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLOW_WORK_SET.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLOW_WORK_SET.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_COM_PRED_TIME PRED_TIME_SET");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PRED_TIME_SET.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PRED_TIME_SET.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_PRED_TS WORK_TIME_SHEET_SET1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORK_TIME_SHEET_SET1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORK_TIME_SHEET_SET1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SHEET_SET1.WORK_NO = ?timezoneUseOne");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_PRED_TS WORK_TIME_SHEET_SET2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORK_TIME_SHEET_SET2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORK_TIME_SHEET_SET2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SHEET_SET2.WORK_NO = ?timezoneUseTwo");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_WORK_TS FLOW_TIME_ZONE");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLOW_TIME_ZONE.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLOW_TIME_ZONE.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_OVER_TS OT_TIME_ZONE");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = OT_TIME_ZONE.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = OT_TIME_ZONE.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND TEMP.ROW_ID = OT_TIME_ZONE.WORKTIME_NO");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FLOW_OT_FRAME");
		sqlFlowNoOtherLang.append(" 		ON OT_TIME_ZONE.CID = FLOW_OT_FRAME.CID");
		sqlFlowNoOtherLang.append(" 		AND OT_TIME_ZONE.OT_FRAME_NO = FLOW_OT_FRAME.OT_FR_NO");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FLOW_LEGAL_OT_FRAME");
		sqlFlowNoOtherLang.append(" 		ON OT_TIME_ZONE.CID = FLOW_LEGAL_OT_FRAME.CID");
		sqlFlowNoOtherLang.append(" 		AND OT_TIME_ZONE.IN_LEGAL_OT_FRAME_NO = FLOW_LEGAL_OT_FRAME.OT_FR_NO");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_PIORITY_SET PIORITY_SET1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND PIORITY_SET1.STAMP_ATR = ?stampPiorityAtrGoingWork");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_PIORITY_SET PIORITY_SET2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND PIORITY_SET2.STAMP_ATR = ?stampPiorityAtrLeaveWork");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET3");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET3.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET3.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND PIORITY_SET3.STAMP_ATR = ?stampPiorityAtrEntering");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET4");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET4.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET4.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND PIORITY_SET4.STAMP_ATR = ?stampPiorityAtrExit");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET5");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET5.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET5.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND PIORITY_SET5.STAMP_ATR = ?stampPiorityAtrPcLogin");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET6");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET6.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET6.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND PIORITY_SET6.STAMP_ATR = ?stampPiorityAtrPcLogout");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_ROUNDING_SET ROUNDING_SET1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND ROUNDING_SET1.ATR = ?superiorityAttendance");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_ROUNDING_SET ROUNDING_SET2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND ROUNDING_SET2.ATR = ?superiorityOfficeWork");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_ROUNDING_SET ROUNDING_SET3");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET3.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET3.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND ROUNDING_SET3.ATR = ?superiorityGoOut");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_ROUNDING_SET ROUNDING_SET4");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET4.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET4.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND ROUNDING_SET4.ATR = ?superiorityTurnBack");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_STMP_REF_TS STAMP_REFLECT1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = STAMP_REFLECT1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = STAMP_REFLECT1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND STAMP_REFLECT1.WORK_NO = ?workNoOne");
		sqlFlowNoOtherLang.append(" 		AND STAMP_REFLECT1.ATTEND_ATR = ?goLeavingWorkAtrGoWork");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_STMP_REF_TS STAMP_REFLECT2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = STAMP_REFLECT2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = STAMP_REFLECT2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND STAMP_REFLECT2.WORK_NO = ?workNoOne");
		sqlFlowNoOtherLang.append(" 		AND STAMP_REFLECT2.ATTEND_ATR = ?goLeavingWorkAtrLeaveWork");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_STMP_REF2_TS FSTAMP_REFLECT_TIME");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FSTAMP_REFLECT_TIME.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FSTAMP_REFLECT_TIME.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_BR_FL FLOW_RT_SET1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLOW_RT_SET1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLOW_RT_SET1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND FLOW_RT_SET1.RESTTIME_ATR = ?resttimeAtrOffDay");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_BR_FL FLOW_RT_SET2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLOW_RT_SET2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLOW_RT_SET2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND FLOW_RT_SET2.RESTTIME_ATR = ?resttimeAtrHalfDay");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_BR_FI_ALL_TS FLOW_FIXED_RT_SET1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLOW_FIXED_RT_SET1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLOW_FIXED_RT_SET1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND TEMP.ROW_ID = FLOW_FIXED_RT_SET1.PERIOD_NO");
		sqlFlowNoOtherLang.append(" 		AND FLOW_FIXED_RT_SET1.RESTTIME_ATR = ?resttimeAtrOffDay");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_BR_FI_ALL_TS FLOW_FIXED_RT_SET2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLOW_FIXED_RT_SET2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLOW_FIXED_RT_SET2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND TEMP.ROW_ID = FLOW_FIXED_RT_SET2.PERIOD_NO");
		sqlFlowNoOtherLang.append(" 		AND FLOW_FIXED_RT_SET2.RESTTIME_ATR = ?resttimeAtrHalfDay");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_BR_FL_ALL_TS FLOW_FLOW_RT_SET1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLOW_FLOW_RT_SET1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLOW_FLOW_RT_SET1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND TEMP.ROW_ID = FLOW_FLOW_RT_SET1.PERIOD_NO");
		sqlFlowNoOtherLang.append(" 		AND FLOW_FLOW_RT_SET1.RESTTIME_ATR = ?resttimeAtrOffDay");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_BR_FL_ALL_TS FLOW_FLOW_RT_SET2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLOW_FLOW_RT_SET2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLOW_FLOW_RT_SET2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND TEMP.ROW_ID = FLOW_FLOW_RT_SET2.PERIOD_NO");
		sqlFlowNoOtherLang.append(" 		AND FLOW_FLOW_RT_SET2.RESTTIME_ATR = ?resttimeAtrHalfDay");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_BR_FL_ALL FLOW_REST_SET");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLOW_REST_SET.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLOW_REST_SET.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLO_HOL_TS FWORK_HOLIDAY_TIME");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FWORK_HOLIDAY_TIME.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FWORK_HOLIDAY_TIME.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND TEMP.ROW_ID = FWORK_HOLIDAY_TIME.WORKTIME_NO");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME INLEGAL_BREAK_REST_TIME_FRAME");
		sqlFlowNoOtherLang.append(" 		ON FWORK_HOLIDAY_TIME.CID = INLEGAL_BREAK_REST_TIME_FRAME.CID");
		sqlFlowNoOtherLang.append(" 		AND FWORK_HOLIDAY_TIME.INLEGAL_BREAK_REST_TIME = INLEGAL_BREAK_REST_TIME_FRAME.WDO_FR_NO");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME OUT_LEGALB_REAK_REST_TIME_FRAME");
		sqlFlowNoOtherLang.append(" 		ON FWORK_HOLIDAY_TIME.CID = OUT_LEGALB_REAK_REST_TIME_FRAME.CID");
		sqlFlowNoOtherLang.append(" 		AND FWORK_HOLIDAY_TIME.OUT_LEGALB_REAK_REST_TIME = OUT_LEGALB_REAK_REST_TIME_FRAME.WDO_FR_NO");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME OUT_LEGAL_PUBHOL_REST_TIME_FRAME");
		sqlFlowNoOtherLang.append(" 		ON FWORK_HOLIDAY_TIME.CID = OUT_LEGAL_PUBHOL_REST_TIME_FRAME.CID");
		sqlFlowNoOtherLang.append(" 		AND FWORK_HOLIDAY_TIME.OUT_LEGAL_PUBHOL_REST_TIME = OUT_LEGAL_PUBHOL_REST_TIME_FRAME.WDO_FR_NO");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT WORKTIME_GO_OUT_SET");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORKTIME_GO_OUT_SET.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORKTIME_GO_OUT_SET.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT_ROUND SPECIAL_ROUND_OUT1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SPECIAL_ROUND_OUT1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SPECIAL_ROUND_OUT1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND SPECIAL_ROUND_OUT1.ROUNDING_TIME_TYPE = ?roundingTimeTypeWorkTimezone");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT_ROUND SPECIAL_ROUND_OUT2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SPECIAL_ROUND_OUT2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SPECIAL_ROUND_OUT2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND SPECIAL_ROUND_OUT2.ROUNDING_TIME_TYPE = ?roundingTimeTypePubHolWorkTimezone");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT_ROUND SPECIAL_ROUND_OUT3");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SPECIAL_ROUND_OUT3.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SPECIAL_ROUND_OUT3.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND SPECIAL_ROUND_OUT3.ROUNDING_TIME_TYPE = ?roundingTimeTypeOtTimezone");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_LATETIME OTHER_LATE_EARLY1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = OTHER_LATE_EARLY1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = OTHER_LATE_EARLY1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND OTHER_LATE_EARLY1.LATE_EARLY_ATR = ?lateEarlyAtrLate");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_LATETIME OTHER_LATE_EARLY2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = OTHER_LATE_EARLY2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = OTHER_LATE_EARLY2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND OTHER_LATE_EARLY2.LATE_EARLY_ATR = ?lateEarlyAtrEarly");
		sqlFlowNoOtherLang.append(" 	LEFT JOIN KRCMT_BONUS_PAY_SET BONUS_PAY_SET");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = BONUS_PAY_SET.CID");
		sqlFlowNoOtherLang.append(" 		AND WORKTIME_COMMON_SET.RAISING_SALARY_SET = BONUS_PAY_SET.BONUS_PAY_SET_CD");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_HDCOM SUBSTITUTION_SET1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SUBSTITUTION_SET1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SUBSTITUTION_SET1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND SUBSTITUTION_SET1.ORIGIN_ATR = ?fromOverTime");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_HDCOM SUBSTITUTION_SET2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SUBSTITUTION_SET2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SUBSTITUTION_SET2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND SUBSTITUTION_SET2.ORIGIN_ATR = ?workDayOffTime");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_TEMPORARY TEMP_WORKTIME_SET");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = TEMP_WORKTIME_SET.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = TEMP_WORKTIME_SET.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_MEDICAL MEDICAL_TIME_SET1");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = MEDICAL_TIME_SET1.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = MEDICAL_TIME_SET1.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND MEDICAL_TIME_SET1.WORK_SYS_ATR = ?workSystemAtrDayShift");
		sqlFlowNoOtherLang.append(" 	JOIN KSHMT_WT_COM_MEDICAL MEDICAL_TIME_SET2");
		sqlFlowNoOtherLang.append(" 		ON WORK_TIME_SET.CID = MEDICAL_TIME_SET2.CID");
		sqlFlowNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = MEDICAL_TIME_SET2.WORKTIME_CD");
		sqlFlowNoOtherLang.append(" 		AND MEDICAL_TIME_SET2.WORK_SYS_ATR = ?workSystemAtrNightShift");
		sqlFlowNoOtherLang.append(" ORDER BY WORK_TIME_SET.WORKTIME_CD, TEMP.ROW_ID;");
		
		SELECT_WORK_TIME_FLOW_NO_OTHER_LANGUAGE = sqlFlowNoOtherLang.toString();

		StringBuilder sqlFlexNoOtherLang = new StringBuilder();
		sqlFlexNoOtherLang.append(" SELECT");
		// R3_61 コード
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.WORKTIME_CD, NULL),");
		// R3_62 名称
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.NAME, NULL),");
		// R3_63 略名
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.ABNAME, NULL),");
		// R3_64 記号
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.SYMBOL, NULL),");
		// R3_65 廃止
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.ABOLITION_ATR = ?isAbolish THEN ?isAbolishText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.ABOLITION_ATR = ?notAbolish THEN ?notAbolishText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END, ");
		// R3_66 勤務形態
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORK_TIME_SET.DAILY_WORK_ATR = ?flexWork THEN ?flexWorkText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_67 モード
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode THEN ?detailModeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?simpleMode THEN ?simpleModeText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_68 半日勤務
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlFlexNoOtherLang.append(" 			THEN ?isUseHalfDayText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlFlexNoOtherLang.append(" 			THEN ?isNotUseHalfDayText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_69 備考
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.NOTE, NULL),");
		// R3_70 コメント
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORK_TIME_SET.MEMO, NULL),");
		// R3_71 所定設定.1日の始まりの時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.START_DATE_CLOCK IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.START_DATE_CLOCK AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.START_DATE_CLOCK AS INTEGER)%60,'0#')), NULL),");
		// R3_72 所定設定.1日の範囲時間.時間
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND PRED_TIME_SET.RANGE_TIME_DAY IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CAST(PRED_TIME_SET.RANGE_TIME_DAY AS INTEGER)/60, NULL),");
		// R3_73 所定設定.1日の範囲時間.夜勤シフト
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.NIGHT_SHIFT_ATR = ?isTrue THEN ?isNightShiftText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.NIGHT_SHIFT_ATR = ?isFalse THEN ?isNotNightShiftText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_74 所定設定.就業時刻1回目.始業時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SHEET_SET.START_TIME IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(WORK_TIME_SHEET_SET.START_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(WORK_TIME_SHEET_SET.START_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_75 所定設定.就業時刻1回目.終業時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORK_TIME_SHEET_SET.END_TIME IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(WORK_TIME_SHEET_SET.END_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(WORK_TIME_SHEET_SET.END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_76 所定設定.就業時刻1回目.残業を含めた所定時間帯を設定する
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.IS_INCLUDE_OT = ?isTrue THEN ?isIncludeOtText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PRED_TIME_SET.IS_INCLUDE_OT = ?isFalse THEN ?isNotIncludeOtText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_4 所定設定.コアタイム時間帯
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND FLEX_WORK_SET.CORETIME_USE_ATR = ?useCoretime THEN ?useCoretimeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLEX_WORK_SET.CORETIME_USE_ATR = ?notUseCoretime THEN ?notUseCoretimeText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_77 所定設定.コアタイム時間帯.始業時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND FLEX_WORK_SET.CORE_TIME_STR IS NOT NULL AND FLEX_WORK_SET.CORETIME_USE_ATR = ?useCoretime, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_WORK_SET.CORE_TIME_STR AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_WORK_SET.CORE_TIME_STR AS INTEGER)%60,'0#')), NULL),");
		// R3_78 所定設定.コアタイム時間帯.終業時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND FLEX_WORK_SET.CORE_TIME_END IS NOT NULL AND FLEX_WORK_SET.CORETIME_USE_ATR = ?useCoretime, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_WORK_SET.CORE_TIME_END AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_WORK_SET.CORE_TIME_END AS INTEGER)%60,'0#')), NULL),");
		// R3_79 所定設定.コアタイム時間帯.最低勤務時間
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND FLEX_WORK_SET.LEAST_WORK_TIME IS NOT NULL AND FLEX_WORK_SET.CORETIME_USE_ATR = ?notUseCoretime, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_WORK_SET.LEAST_WORK_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_WORK_SET.LEAST_WORK_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_80 所定設定.半日勤務.前半終了時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.MORNING_END_TIME IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.MORNING_END_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.MORNING_END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_81 所定設定.半日勤務.後半開始時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.AFTERNOON_START_TIME IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.AFTERNOON_START_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.AFTERNOON_START_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_82 所定設定.所定時間.1日
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.PRED_ONE_DAY IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.PRED_ONE_DAY AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.PRED_ONE_DAY AS INTEGER)%60,'0#')), NULL),");
		// R3_83 所定設定.所定時間.午前
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.PRED_MORNING IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.PRED_MORNING AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.PRED_MORNING AS INTEGER)%60,'0#')), NULL),");
		// R3_84 所定設定.所定時間.午後
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.PRED_AFTERNOON IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.PRED_AFTERNOON AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.PRED_AFTERNOON AS INTEGER)%60,'0#')), NULL),");
		// R3_85 所定設定.休暇取得時加算時間.午後
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.WORK_ADD_ONE_DAY IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.WORK_ADD_ONE_DAY AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.WORK_ADD_ONE_DAY AS INTEGER)%60,'0#')), NULL),");
		// R3_86 所定設定.休暇取得時加算時間.午後
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.WORK_ADD_MORNING IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.WORK_ADD_MORNING AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.WORK_ADD_MORNING AS INTEGER)%60,'0#')), NULL),");
		// R3_87 所定設定.休暇取得時加算時間.午後
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND PRED_TIME_SET.WORK_ADD_AFTERNOON IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(PRED_TIME_SET.WORK_ADD_AFTERNOON AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(PRED_TIME_SET.WORK_ADD_AFTERNOON AS INTEGER)%60,'0#')), NULL),");
		// R3_88 勤務時間帯.1日勤務用.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_WORK_TIME_SET1.TIME_STR IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_WORK_TIME_SET1.TIME_STR AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_WORK_TIME_SET1.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		// R3_89 勤務時間帯.1日勤務用.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_WORK_TIME_SET1.TIME_END IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_WORK_TIME_SET1.TIME_END AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_WORK_TIME_SET1.TIME_END AS INTEGER)%60,'0#')), NULL),");
		// R3_90 勤務時間帯.1日勤務用.丸め
		sqlFlexNoOtherLang.append(" 	CASE WHEN FLEX_WORK_TIME_SET1.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET1.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET1.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET1.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET1.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET1.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET1.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET1.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_91 勤務時間帯.1日勤務用.端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN FLEX_WORK_TIME_SET1.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET1.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_92 勤務時間帯.午前勤務用.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_WORK_TIME_SET2.TIME_STR IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_WORK_TIME_SET2.TIME_STR AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_WORK_TIME_SET2.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		// R3_93 勤務時間帯.午前勤務用.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_WORK_TIME_SET2.TIME_END IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_WORK_TIME_SET2.TIME_END AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_WORK_TIME_SET2.TIME_END AS INTEGER)%60,'0#')), NULL),");
		// R3_94 勤務時間帯.午前勤務用.丸め
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET2.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET2.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET2.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET2.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET2.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET2.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET2.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET2.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_95 勤務時間帯.午前勤務用.端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET2.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET2.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_96 勤務時間帯.午後勤務用.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_WORK_TIME_SET3.TIME_STR IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_WORK_TIME_SET3.TIME_STR AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_WORK_TIME_SET3.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		// R3_97 勤務時間帯.午後勤務用.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_WORK_TIME_SET3.TIME_END IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_WORK_TIME_SET3.TIME_END AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_WORK_TIME_SET3.TIME_END AS INTEGER)%60,'0#')), NULL),");
		// R3_98 勤務時間帯.午後勤務用.丸め
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET3.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET3.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET3.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET3.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET3.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET3.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET3.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET3.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_99 勤務時間帯.午後勤務用.端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET3.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_WORK_TIME_SET3.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_100 残業時間帯.1日勤務用.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_OT_TIME_SET1.TIME_STR IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_OT_TIME_SET1.TIME_STR AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_OT_TIME_SET1.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		// R3_101 残業時間帯.1日勤務用.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_OT_TIME_SET1.TIME_END IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_OT_TIME_SET1.TIME_END AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_OT_TIME_SET1.TIME_END AS INTEGER)%60,'0#')), NULL),");
		// R3_102 残業時間帯.1日勤務用.丸め
		sqlFlexNoOtherLang.append(" 	CASE WHEN FLEX_OT_TIME_SET1.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET1.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET1.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET1.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET1.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET1.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET1.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET1.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_103 残業時間帯.1日勤務用.端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN FLEX_OT_TIME_SET1.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET1.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_104 残業時間帯.1日勤務用.残業枠
		sqlFlexNoOtherLang.append(" 	FLEX_OT_FRAME1.OT_FR_NAME,");
		// R3_105 残業時間帯.1日勤務用.早出
		sqlFlexNoOtherLang.append(" 	CASE WHEN FLEX_OT_TIME_SET1.TREAT_EARLY_OT_WORK = ?isTrue");
		sqlFlexNoOtherLang.append(" 			THEN ?treatEarlyOtWork");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET1.TREAT_EARLY_OT_WORK = ?isFalse");
		sqlFlexNoOtherLang.append(" 			THEN ?notTreatEarlyOtWork");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_106 残業時間帯.午前勤務用.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_OT_TIME_SET2.TIME_STR IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_OT_TIME_SET2.TIME_STR AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_OT_TIME_SET2.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		// R3_107 残業時間帯.午前勤務用.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_OT_TIME_SET2.TIME_END IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_OT_TIME_SET2.TIME_END AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_OT_TIME_SET2.TIME_END AS INTEGER)%60,'0#')), NULL),");
		// R3_108 残業時間帯.午前勤務用.丸め
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET2.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET2.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET2.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET2.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET2.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET2.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET2.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET2.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_109 残業時間帯.午前勤務用.端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET2.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET2.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_110 残業時間帯.午前勤務用.残業枠
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue");
		sqlFlexNoOtherLang.append(" 			THEN FLEX_OT_FRAME2.OT_FR_NAME");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_111 残業時間帯.午前勤務用.早出
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue AND FLEX_OT_TIME_SET2.TREAT_EARLY_OT_WORK = ?isTrue");
		sqlFlexNoOtherLang.append(" 			THEN ?treatEarlyOtWork");
		sqlFlexNoOtherLang.append(" 		 WHEN WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue AND FLEX_OT_TIME_SET2.TREAT_EARLY_OT_WORK = ?isFalse");
		sqlFlexNoOtherLang.append(" 			THEN ?notTreatEarlyOtWork");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_112 残業時間帯.午後勤務用.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_OT_TIME_SET3.TIME_STR IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_OT_TIME_SET3.TIME_STR AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_OT_TIME_SET3.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		// R3_113 残業時間帯.午後勤務用.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_OT_TIME_SET3.TIME_END IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_OT_TIME_SET3.TIME_END AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_OT_TIME_SET3.TIME_END AS INTEGER)%60,'0#')), NULL),");
		// R3_114 残業時間帯.午後勤務用.丸め
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET3.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET3.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET3.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET3.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET3.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET3.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET3.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET3.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_115 残業時間帯.午後勤務用.端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET3.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_OT_TIME_SET3.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_116 残業時間帯.午後勤務用.残業枠
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue");
		sqlFlexNoOtherLang.append(" 			THEN FLEX_OT_FRAME3.OT_FR_NAME");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_117 残業時間帯.午後勤務用.早出
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue AND FLEX_OT_TIME_SET3.TREAT_EARLY_OT_WORK = ?isTrue");
		sqlFlexNoOtherLang.append(" 			THEN ?treatEarlyOtWork");
		sqlFlexNoOtherLang.append(" 		 WHEN WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue AND FLEX_OT_TIME_SET3.TREAT_EARLY_OT_WORK = ?isFalse");
		sqlFlexNoOtherLang.append(" 			THEN ?notTreatEarlyOtWork");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_118 打刻時間帯.優先設定.出勤
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND PIORITY_SET1.PIORITY_ATR = ?beforePiority AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlFlexNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET1.PIORITY_ATR = ?afterPiority AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlFlexNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_119 打刻時間帯.優先設定.退勤
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND PIORITY_SET2.PIORITY_ATR = ?beforePiority AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlFlexNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET2.PIORITY_ATR = ?afterPiority AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode");
		sqlFlexNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_120 打刻時間帯.打刻丸め.出勤
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 		CASE WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET1.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 			 ELSE NULL");
		sqlFlexNoOtherLang.append(" 		END,");
		sqlFlexNoOtherLang.append(" 		NULL),");
		// R3_121 打刻時間帯.打刻丸め.出勤前後設定
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET1.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlFlexNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET1.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlFlexNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_122 打刻時間帯.打刻丸め.退勤
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 		CASE WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET2.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 			 ELSE NULL");
		sqlFlexNoOtherLang.append(" 		END,");
		sqlFlexNoOtherLang.append(" 		NULL),");
		// R3_123 打刻時間帯.打刻丸め.退勤前後設定
		// R2_94 打刻時間帯.打刻丸め.退勤前後設定
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET2.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlFlexNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET2.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlFlexNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_124 打刻詳細設定.出勤反映時間帯.開始時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND FLEX_STAMP_REFLECT1.START_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_STAMP_REFLECT1.START_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_STAMP_REFLECT1.START_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_125 打刻詳細設定.出勤反映時間帯.終了時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND FLEX_STAMP_REFLECT1.END_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_STAMP_REFLECT1.END_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_STAMP_REFLECT1.END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_126 打刻詳細設定.出勤反映時間帯.2勤務目の開始（1勤務目の退勤から分けられる時間）
		sqlFlexNoOtherLang.append(" 	NULL, ");
		//
		sqlFlexNoOtherLang.append(" 	NULL,");
		// R3_127 打刻詳細設定.退勤反映時間帯.開始時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND FLEX_STAMP_REFLECT2.START_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_STAMP_REFLECT2.START_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_STAMP_REFLECT2.START_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_128 打刻詳細設定.退勤反映時間帯.終了時刻
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND FLEX_STAMP_REFLECT2.END_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_STAMP_REFLECT2.END_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_STAMP_REFLECT2.END_TIME AS INTEGER)%60,'0#')), NULL),");
		//
		sqlFlexNoOtherLang.append(" 	NULL,");
		//
		sqlFlexNoOtherLang.append(" 	NULL,");
		// R3_129 打刻詳細設定.優先設定.入門
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET3.PIORITY_ATR = ?beforePiority");
		sqlFlexNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET3.PIORITY_ATR = ?afterPiority");
		sqlFlexNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_130 打刻詳細設定.優先設定.退門
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET4.PIORITY_ATR = ?beforePiority");
		sqlFlexNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET4.PIORITY_ATR = ?afterPiority");
		sqlFlexNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_131 打刻詳細設定.優先設定.PCログオン
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET5.PIORITY_ATR = ?beforePiority");
		sqlFlexNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET5.PIORITY_ATR = ?afterPiority");
		sqlFlexNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_132 打刻詳細設定.優先設定.PCログオフ
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET6.PIORITY_ATR = ?beforePiority");
		sqlFlexNoOtherLang.append(" 			THEN ?beforePiorityText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND PIORITY_SET6.PIORITY_ATR = ?afterPiority");
		sqlFlexNoOtherLang.append(" 			THEN ?afterPiorityText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_133 打刻詳細設定.打刻丸め.外出
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1,");
		sqlFlexNoOtherLang.append(" 		CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET3.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 			 ELSE NULL");
		sqlFlexNoOtherLang.append(" 		END,");
		sqlFlexNoOtherLang.append(" 		NULL),");
		// R3_134 打刻詳細設定.打刻丸め.外出前後設定
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET3.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlFlexNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET3.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlFlexNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_135 打刻詳細設定.打刻丸め.戻り
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 		CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 			 WHEN ROUNDING_SET4.ROUNDING_TIME_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 			 ELSE NULL");
		sqlFlexNoOtherLang.append(" 		END,");
		sqlFlexNoOtherLang.append(" 		NULL),");
		// R3_136 打刻詳細設定.打刻丸め.戻り前後設定
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET4.FRONT_REAR_ATR = ?fontRearSectionBefore");
		sqlFlexNoOtherLang.append(" 			THEN ?fontRearSectionBeforeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND ROUNDING_SET4.FRONT_REAR_ATR = ?fontRearSectionAfter");
		sqlFlexNoOtherLang.append(" 			THEN ?fontRearSectionAfterText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_137 休憩時間帯.休憩時間の固定
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue THEN ?isUseText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse THEN ?isNotUseText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_138 休憩時間帯.1日勤務用（固定する）.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_HA_FIX_REST1.STR_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_HA_FIX_REST1.STR_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_FIX_REST1.STR_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_139 休憩時間帯.1日勤務用（固定する）.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_HA_FIX_REST1.END_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_HA_FIX_REST1.END_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_FIX_REST1.END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_140 休憩時間帯.午前勤務用（固定する）.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_HA_FIX_REST2.STR_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_HA_FIX_REST2.STR_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_FIX_REST2.STR_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_141 休憩時間帯.午前勤務用（固定する）.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_HA_FIX_REST2.END_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_HA_FIX_REST2.END_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_FIX_REST2.END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_142 休憩時間帯.午後勤務用（固定する）.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_HA_FIX_REST3.STR_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_HA_FIX_REST3.STR_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_FIX_REST3.STR_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_143 休憩時間帯.午後勤務用（固定する）.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_HA_FIX_REST3.END_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_HA_FIX_REST3.END_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_FIX_REST3.END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_144 休憩時間帯.1日勤務用（固定しない）.経過時間
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_REST_SET1.FLOW_PASSAGE_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_REST_SET1.FLOW_PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_REST_SET1.FLOW_PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN '以降は下記の時間で繰り返す'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '経過時間'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_RT_SET1.AFTER_PASSAGE_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse AND FLEX_HA_RT_SET1.USE_REST_AFTER_SET = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_RT_SET1.AFTER_PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_RT_SET1.AFTER_PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_145 休憩時間帯.1日勤務用（固定しない）.休憩時間
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_REST_SET1.FLOW_REST_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_REST_SET1.FLOW_REST_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_REST_SET1.FLOW_REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN ");
		sqlFlexNoOtherLang.append(" 			CASE WHEN FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue THEN NULL");
		sqlFlexNoOtherLang.append(" 				 WHEN FLEX_HA_RT_SET1.USE_REST_AFTER_SET = ?isTrue THEN ?isUseRestAfterSetText");
		sqlFlexNoOtherLang.append(" 				 WHEN FLEX_HA_RT_SET1.USE_REST_AFTER_SET = ?isFalse THEN ?isNotUseRestAfterSetText");
		sqlFlexNoOtherLang.append(" 				ELSE NULL");
		sqlFlexNoOtherLang.append(" 			END");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '休憩時間'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_RT_SET1.AFTER_REST_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse AND FLEX_HA_RT_SET1.USE_REST_AFTER_SET = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_RT_SET1.AFTER_REST_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_RT_SET1.AFTER_REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_149 休憩時間帯.午前勤務用（固定しない）.経過時間
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_REST_SET2.FLOW_PASSAGE_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_REST_SET2.FLOW_PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_REST_SET2.FLOW_PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN '以降は下記の時間で繰り返す'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '経過時間'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_RT_SET2.AFTER_PASSAGE_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue AND FLEX_HA_RT_SET2.USE_REST_AFTER_SET = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_RT_SET2.AFTER_PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_RT_SET2.AFTER_PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_150 休憩時間帯.午前勤務用（固定しない）.休憩時間
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_REST_SET2.FLOW_REST_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_REST_SET2.FLOW_REST_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_REST_SET2.FLOW_REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN ");
		sqlFlexNoOtherLang.append(" 			CASE WHEN FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 				WHEN FLEX_HA_RT_SET2.USE_REST_AFTER_SET = ?isTrue THEN ?isUseRestAfterSetText");
		sqlFlexNoOtherLang.append(" 				WHEN FLEX_HA_RT_SET2.USE_REST_AFTER_SET = ?isFalse THEN ?isNotUseRestAfterSetText");
		sqlFlexNoOtherLang.append(" 				ELSE NULL");
		sqlFlexNoOtherLang.append(" 			END");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '休憩時間'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_RT_SET2.AFTER_REST_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue AND FLEX_HA_RT_SET2.USE_REST_AFTER_SET = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_RT_SET2.AFTER_REST_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_RT_SET2.AFTER_REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_154 休憩時間帯.午後勤務用（固定しない）.経過時間
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_REST_SET3.FLOW_PASSAGE_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_REST_SET3.FLOW_PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_REST_SET3.FLOW_PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN '以降は下記の時間で繰り返す'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '経過時間'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_RT_SET3.AFTER_PASSAGE_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue AND FLEX_HA_RT_SET3.USE_REST_AFTER_SET = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_RT_SET3.AFTER_PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_RT_SET3.AFTER_PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_155 休憩時間帯.午後勤務用（固定しない）.休憩時間
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_REST_SET3.FLOW_REST_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_REST_SET3.FLOW_REST_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_REST_SET3.FLOW_REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN ");
		sqlFlexNoOtherLang.append(" 			CASE WHEN FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue OR WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 				 WHEN FLEX_HA_RT_SET3.USE_REST_AFTER_SET = ?isTrue THEN ?isUseRestAfterSetText");
		sqlFlexNoOtherLang.append(" 				 WHEN FLEX_HA_RT_SET3.USE_REST_AFTER_SET = ?isFalse THEN ?isNotUseRestAfterSetText");
		sqlFlexNoOtherLang.append(" 				 ELSE NULL");
		sqlFlexNoOtherLang.append(" 			END");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '休憩時間'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_HA_RT_SET3.AFTER_REST_TIME IS NOT NULL AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_WORK_SET.USE_HALFDAY_SHIFT = ?isTrue AND FLEX_HA_RT_SET3.USE_REST_AFTER_SET = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_HA_RT_SET3.AFTER_REST_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_HA_RT_SET3.AFTER_REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_159 休憩時間帯.休憩計算設定.休憩中に退勤した場合の休憩時間の計算方法
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLEX_REST_SET.COMMON_CALCULATE_METHOD = ?calcMethodAppropAll");
		sqlFlexNoOtherLang.append(" 			THEN ?calcMethodAppropAllText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLEX_REST_SET.COMMON_CALCULATE_METHOD = ?calcMethodNotAppropAll");
		sqlFlexNoOtherLang.append(" 			THEN ?calcMethodNotAppropAllText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLEX_REST_SET.COMMON_CALCULATE_METHOD = ?calcMethodOfficeWorkAppropAll");
		sqlFlexNoOtherLang.append(" 			THEN ?calcMethodOfficeWorkAppropAllText");
		sqlFlexNoOtherLang.append(" 		ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_160 休憩時間帯.固定休憩設定（休憩時間の固定する）
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse THEN NULL ");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLEX_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodReferMaster THEN ?restCalcMethodReferMasterText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLEX_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodReferSchedule THEN ?restCalcMethodReferScheduleText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLEX_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodWithoutRefer THEN ?restCalcMethodWithoutReferText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_161 休憩時間帯.固定休憩設定（休憩時間の固定する）.予定と実績の勤務が一致しな場合はマスタを参照する
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue AND FLEX_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodReferSchedule, ");
		sqlFlexNoOtherLang.append(" 		IIF(FLEX_REST_SET.IS_CALC_FROM_SCHEDULE = ?isTrue, ?isCalcFromSchedule, ?isNotCalcFromSchedule), NULL),");
		// R3_162 休憩時間帯.固定休憩設定（休憩時間の固定する）.休憩時刻が無い場合はマスタから休憩時刻を参照する
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue AND FLEX_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodReferSchedule, ");
		sqlFlexNoOtherLang.append(" 		IIF(FLEX_REST_SET.IS_REFER_REST_TIME = ?isTrue, ?isReferRestTime, ?isNotReferRestTime), NULL),");
		// R3_163 休憩時間帯.固定休憩設定（休憩時間の固定する）.私用外出を休憩として扱う
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue AND FLEX_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodWithoutRefer, ");
		sqlFlexNoOtherLang.append(" 		IIF(FLEX_REST_SET.USER_PRIVATE_GO_OUT_REST = ?isTrue, ?userPrivateGoOutRest, ?notUserPrivateGoOutRest), NULL),");
		// R3_164 休憩時間帯.固定休憩設定（休憩時間の固定する）.組合外出を休憩として扱う
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isTrue AND FLEX_REST_SET.FIXED_REST_CALC_METHOD = ?restCalcMethodWithoutRefer, ");
		sqlFlexNoOtherLang.append(" 		IIF(FLEX_REST_SET.USER_ASSO_GO_OUT_REST = ?isTrue, ?userAssoGoOutRest, ?notUserAssoGoOutRest), NULL),");
		// R3_165 休憩時間帯.流動休憩設定（休憩時間の固定しない）.実績での休憩計算方法
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse, ");
		sqlFlexNoOtherLang.append(" 		IIF(FLEX_REST_SET.CALCULATE_METHOD = ?flowRestCalcMethodReferMaster, ?restCalcMethodReferMasterText, ?flowRestCalcMethodMasterAndStampText), NULL),");
		// R3_166 休憩時間帯.流動休憩設定（休憩時間の固定しない）.外出を休憩として扱う
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse, ");
		sqlFlexNoOtherLang.append(" 		IIF(FLEX_REST_SET.USE_STAMP = ?isTrue, ?useStamp, ?notUserStamp), NULL),");
		// R3_167 休憩時間帯.流動休憩設定（休憩時間の固定しない）.外出の計上方法
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse, ");
		sqlFlexNoOtherLang.append(" 		IIF(FLEX_REST_SET.USE_STAMP_CALC_METHOD = ?useRestTimeToCalc, ?useRestTimeToCalcText, ?useGoOutTimeToCalcText), NULL),");
		// R3_168 休憩時間帯.流動休憩設定（休憩時間の固定しない）.優先設定
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND FLEX_HA_RT_SET1.FIX_REST_TIME = ?isFalse, ");
		sqlFlexNoOtherLang.append(" 		IIF(FLEX_REST_SET.TIME_MANAGER_SET_ATR = ?isClockManage, ?isClockManageText, ?notClockManageText), NULL),");
		// R3_169 休出時間帯.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_HOL_SET.TIME_STR IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_HOL_SET.TIME_STR AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_HOL_SET.TIME_STR AS INTEGER)%60,'0#')), NULL),");
		// R3_170 休出時間帯.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_HOL_SET.TIME_END IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_HOL_SET.TIME_END AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_HOL_SET.TIME_END AS INTEGER)%60,'0#')), NULL),");
		// R3_171 休出時間帯.法定内休出枠
		sqlFlexNoOtherLang.append(" 	FLEX_HOL_FRAME.WDO_FR_NAME,");
		// R3_172 休出時間帯.法定外休出枠
		sqlFlexNoOtherLang.append(" 	FLEX_OUT_HOL_FRAME.WDO_FR_NAME,");
		// R3_173 休出時間帯.法定外休出枠（祝日）
		sqlFlexNoOtherLang.append(" 	FLEX_PUB_HOL_FRAME.WDO_FR_NAME,");
		// R3_174 休出時間帯.丸め
		sqlFlexNoOtherLang.append(" 	CASE WHEN FLEX_HOL_SET.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_HOL_SET.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_HOL_SET.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_HOL_SET.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_HOL_SET.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_HOL_SET.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_HOL_SET.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_HOL_SET.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_175 休出時間帯.端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN FLEX_HOL_SET.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN FLEX_HOL_SET.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_176 休出休憩.休憩時間の固定
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND FLEX_OD_RT_SET.FIX_REST_TIME = ?isTrue THEN ?isUseText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND FLEX_OD_RT_SET.FIX_REST_TIME = ?isFalse THEN ?isNotUseText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_177 休出休憩.固定する.開始時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_OD_FIX_REST.STR_TIME IS NOT NULL AND FLEX_OD_RT_SET.FIX_REST_TIME = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_OD_FIX_REST.STR_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_OD_FIX_REST.STR_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_178 休出休憩.固定する.終了時間
		sqlFlexNoOtherLang.append(" 	IIF(FLEX_OD_FIX_REST.END_TIME IS NOT NULL AND FLEX_OD_RT_SET.FIX_REST_TIME = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(FLEX_OD_FIX_REST.END_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(FLEX_OD_FIX_REST.END_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_179 休出休憩.固定しない.経過時間
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_OD_REST_SET.FLOW_PASSAGE_TIME IS NOT NULL AND FLEX_OD_RT_SET.FIX_REST_TIME = ?isFalse, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_OD_REST_SET.FLOW_PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_OD_REST_SET.FLOW_PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN '以降は下記の時間で繰り返す'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '経過時間'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_OD_RT_SET.AFTER_PASSAGE_TIME IS NOT NULL AND FLEX_OD_RT_SET.FIX_REST_TIME = ?isFalse AND FLEX_OD_RT_SET.USE_REST_AFTER_SET = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_OD_RT_SET.AFTER_PASSAGE_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_OD_RT_SET.AFTER_PASSAGE_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_180 休出休憩.固定しない.休憩時間
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID < 6   ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_OD_REST_SET.FLOW_REST_TIME IS NOT NULL AND FLEX_OD_RT_SET.FIX_REST_TIME = ?isFalse, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_OD_REST_SET.FLOW_REST_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_OD_REST_SET.FLOW_REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 7 THEN ");
		sqlFlexNoOtherLang.append(" 			CASE WHEN FLEX_OD_RT_SET.FIX_REST_TIME = ?isTrue THEN NULL");
		sqlFlexNoOtherLang.append(" 				 WHEN FLEX_OD_RT_SET.USE_REST_AFTER_SET = ?isTrue THEN ?isUseRestAfterSetText");
		sqlFlexNoOtherLang.append(" 				 WHEN FLEX_OD_RT_SET.USE_REST_AFTER_SET = ?isFalse THEN ?isNotUseRestAfterSetText");
		sqlFlexNoOtherLang.append(" 				 ELSE NULL");
		sqlFlexNoOtherLang.append(" 			END");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 8 THEN '休憩時間'");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 9 ");
		sqlFlexNoOtherLang.append(" 			THEN IIF(FLEX_OD_RT_SET.AFTER_REST_TIME IS NOT NULL AND FLEX_OD_RT_SET.FIX_REST_TIME = ?isFalse AND FLEX_OD_RT_SET.USE_REST_AFTER_SET = ?isTrue, ");
		sqlFlexNoOtherLang.append(" 					CONCAT(CAST(FLEX_OD_RT_SET.AFTER_REST_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 						FORMAT(CAST(FLEX_OD_RT_SET.AFTER_REST_TIME AS INTEGER)%60,'0#')), NULL)");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_184 外出.外出丸め設定.同じ枠内での丸め設定
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_SAME_FRAME = ?isTrue THEN ?roudingAfterTotalText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_SAME_FRAME = ?isFalse THEN ?roudingAfterEachTimePeriodText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_185 外出.外出丸め設定.枠を跨る場合の丸め設定
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_CROSS_FRAME = ?isTrue THEN ?roudingAfterTotalText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_GO_OUT_SET.ROUNDING_CROSS_FRAME = ?isFalse THEN ?roudingAfterEachTimePeriodText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_186 外出.私用・組合外出時間.就業時間帯
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_187 外出.私用・組合外出時間.丸め設定
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_188 外出.私用・組合外出時間.丸め設定端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_189 外出.私用・組合外出時間.残業時間帯
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_190 外出.私用・組合外出時間.丸め設定
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_191 外出.私用・組合外出時間.丸め設定端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_192 外出.私用・組合外出時間.休出時間帯
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_193 外出.私用・組合外出時間.丸め設定
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_194 外出.私用・組合外出時間.丸め設定端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_195 外出.私用・組合外出控除時間.就業時間帯
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_196 外出.私用・組合外出控除時間.丸め設定
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_197 外出.私用・組合外出控除時間.丸め設定端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PERSONAL_DEDUCT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_198 外出.私用・組合外出控除時間.残業時間帯
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_199 外出.私用・組合外出控除時間.丸め設定
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_200 外出.私用・組合外出控除時間.丸め設定端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PERSONAL_DEDUCT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_201 外出.私用・組合外出控除時間.休出時間帯
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_202 外出.私用・組合外出控除時間.丸め設定
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_203 外出.私用・組合外出控除時間.丸め設定端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PERSONAL_DEDUCT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_204 外出.公用・有償外出時間.就業時間帯
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_205 外出.公用・有償外出時間.丸め設定
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT1.PUB_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_206 外出.公用・有償外出時間.丸め設定端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT1.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT1.PUB_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_207 外出.公用・有償外出時間.残業時間帯
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_208 外出.公用・有償外出時間.丸め設定
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT3.PUB_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_209 外出.公用・有償外出時間.丸め設定端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT3.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT3.PUB_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_210 外出.公用・有償外出時間.休出時間帯
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD = ?reverseTimezoneRounding THEN ?reverseTimezoneRoundingText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD = ?isSetRounding THEN ?isSetRoundingText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_211 外出.公用・有償外出時間.丸め設定
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN SPECIAL_ROUND_OUT2.PUB_ROUNDING_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_212 外出.公用・有償外出時間.丸め設定端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SPECIAL_ROUND_OUT2.PUB_ROUNDING_METHOD != ?isSetRounding THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SPECIAL_ROUND_OUT2.PUB_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_213 遅刻早退.遅刻早退時間丸め.遅刻丸め
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.RECORD_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_214 遅刻早退.遅刻早退時間丸め.遅刻端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.RECORD_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.RECORD_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_215 遅刻早退.遅刻早退時間丸め.早退丸め
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.RECORD_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_216 遅刻早退.遅刻早退時間丸め.早退端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.RECORD_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.RECORD_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_217 遅刻早退.遅刻早退控除時間丸め.遅刻丸め
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY1.DEDUCTION_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_218 遅刻早退.遅刻早退控除時間丸め.遅刻端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.DEDUCTION_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.DEDUCTION_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_219 遅刻早退.遅刻早退控除時間丸め.早退丸め
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN OTHER_LATE_EARLY2.DEDUCTION_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_220 遅刻早退.遅刻早退控除時間丸め.早退端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.DEDUCTION_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.DEDUCTION_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_221 遅刻早退詳細設定.控除時間.遅刻早退時間を就業時間から控除する
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND LATE_EARLY_SET.IS_DEDUCTE_FROM_TIME = ?isTrue THEN ?isDeducteFromTimeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND LATE_EARLY_SET.IS_DEDUCTE_FROM_TIME = ?isFalse THEN ?isNotDeducteFromTimeText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_222 遅刻早退詳細設定.遅刻早退の判定設定.時間丁度の打刻は遅刻とする
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.EXTRACT_LATE_EARLY_TIME = ?isTrue THEN ?isExtractLateEarlyTimeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.EXTRACT_LATE_EARLY_TIME = ?isFalse THEN ?isNotExtractLateEarlyTimeText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_223 遅刻早退詳細設定.遅刻早退の判定設定.時間丁度の打刻は早退とする
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.EXTRACT_LATE_EARLY_TIME = ?isTrue THEN ?isExtractLateEarlyTimeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.EXTRACT_LATE_EARLY_TIME = ?isFalse THEN ?isNotExtractLateEarlyTimeText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_224 遅刻早退詳細設定.猶予時間.遅刻猶予時間
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.GRACE_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(OTHER_LATE_EARLY1.GRACE_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(OTHER_LATE_EARLY1.GRACE_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_225 遅刻早退詳細設定.猶予時間.遅刻猶予時間を就業時間に含める
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.INCLUDE_WORKTIME = ?isTrue THEN ?isIncludeWorktimeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY1.INCLUDE_WORKTIME = ?isFalse THEN ?isNotIncludeWorktimeText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_226 遅刻早退詳細設定.猶予時間.早退猶予時間
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.GRACE_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(OTHER_LATE_EARLY2.GRACE_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(OTHER_LATE_EARLY2.GRACE_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_227 遅刻早退詳細設定.猶予時間.早退猶予時間を就業時間に含める
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.INCLUDE_WORKTIME = ?isTrue THEN ?isIncludeWorktimeText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND OTHER_LATE_EARLY2.INCLUDE_WORKTIME = ?isFalse THEN ?isNotIncludeWorktimeText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_228 加給.コード
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, WORKTIME_COMMON_SET.RAISING_SALARY_SET, NULL),");
		// R3_229 加給.名称
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND BONUS_PAY_SET.BONUS_PAY_SET_CD IS NOT NULL THEN BONUS_PAY_SET.BONUS_PAY_SET_NAME");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND BONUS_PAY_SET.BONUS_PAY_SET_CD IS NULL AND WORKTIME_COMMON_SET.RAISING_SALARY_SET IS NOT NULL AND WORKTIME_COMMON_SET.RAISING_SALARY_SET != '   ' THEN ?masterUnregistered");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_230 代休.休日出勤
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.USE_ATR = ?isTrue THEN ?isUseSubstitutionText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.USE_ATR = ?isFalse THEN ?isNotUseSubstitutionText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_231 代休.休日出勤.時間区分
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SUBSTITUTION_SET2.USE_ATR = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.TRANFER_ATR = ?specifiedTimeSubHol THEN ?specifiedTimeSubHolText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.TRANFER_ATR = ?certainTimeExcSubHol THEN ?certainTimeExcSubHolText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_232 代休.休日出勤.１日
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.ONE_DAY_TIME IS NOT NULL ");
		sqlFlexNoOtherLang.append(" 		AND (WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR (SUBSTITUTION_SET2.USE_ATR = ?isTrue AND SUBSTITUTION_SET2.TRANFER_ATR = ?specifiedTimeSubHol)), ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET2.ONE_DAY_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET2.ONE_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_233 代休.休日出勤.半日
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND SUBSTITUTION_SET2.HALF_DAY_TIME IS NOT NULL ");
		sqlFlexNoOtherLang.append(" 		AND (WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR (SUBSTITUTION_SET2.USE_ATR = ?isTrue AND SUBSTITUTION_SET2.TRANFER_ATR = ?specifiedTimeSubHol)), ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET2.HALF_DAY_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET2.HALF_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_234 代休.休日出勤.一定時間
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET2.USE_ATR = ?isTrue ");
		sqlFlexNoOtherLang.append(" 		AND SUBSTITUTION_SET2.TRANFER_ATR = ?certainTimeExcSubHol AND SUBSTITUTION_SET2.CERTAIN_TIME IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET2.CERTAIN_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET2.CERTAIN_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_235 代休.残業
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.USE_ATR = ?isTrue THEN ?isUseSubstitutionText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.USE_ATR = ?isFalse THEN ?isNotUseSubstitutionText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_236 代休.残業.一定時間
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode OR SUBSTITUTION_SET1.USE_ATR = ?isFalse THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.TRANFER_ATR = ?specifiedTimeSubHol THEN ?specifiedTimeSubHolText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND SUBSTITUTION_SET1.TRANFER_ATR = ?certainTimeExcSubHol THEN ?certainTimeExcSubHolText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_237 代休.残業.１日
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET1.USE_ATR = ?isTrue ");
		sqlFlexNoOtherLang.append(" 		AND SUBSTITUTION_SET1.TRANFER_ATR = ?specifiedTimeSubHol AND SUBSTITUTION_SET1.ONE_DAY_TIME IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET1.ONE_DAY_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET1.ONE_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_238 代休.残業.半日
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET1.USE_ATR = ?isTrue ");
		sqlFlexNoOtherLang.append(" 		AND SUBSTITUTION_SET1.TRANFER_ATR = ?specifiedTimeSubHol AND SUBSTITUTION_SET1.HALF_DAY_TIME IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET1.HALF_DAY_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET1.HALF_DAY_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_239 代休.残業.一定時間
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode AND SUBSTITUTION_SET1.USE_ATR = ?isTrue ");
		sqlFlexNoOtherLang.append(" 		AND SUBSTITUTION_SET1.TRANFER_ATR = ?certainTimeExcSubHol AND SUBSTITUTION_SET1.CERTAIN_TIME IS NOT NULL, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(SUBSTITUTION_SET1.CERTAIN_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(SUBSTITUTION_SET1.CERTAIN_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_240 深夜残業.深夜時間丸め
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN WORKTIME_COMMON_SET.LATE_NIGHT_UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_241 深夜残業.深夜時間端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.LATE_NIGHT_ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.LATE_NIGHT_ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_242 臨時.臨時丸め
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP_WORKTIME_SET.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_243 臨時.臨時端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND TEMP_WORKTIME_SET.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND TEMP_WORKTIME_SET.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_244 育児.育児時間帯に勤務した場合の扱い
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.CHILD_CARE_WORK_USE = ?isTrue THEN ?childCareWorkUseText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.CHILD_CARE_WORK_USE = ?isFalse THEN ?childCareWorkNotUseText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_245 育児.介護時間帯に勤務した場合の扱い
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.NUR_TIMEZONE_WORK_USE = ?isTrue THEN ?nurTimezoneWorkUseText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.NUR_TIMEZONE_WORK_USE = ?isFalse THEN ?nurTimezoneWorkNotUseText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_246 医療.日勤申し送り時間
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET1.APP_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(MEDICAL_TIME_SET1.APP_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(MEDICAL_TIME_SET1.APP_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_247 医療.夜勤申し送り時間
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET2.APP_TIME IS NOT NULL AND WORKTIME_DISP_MODE.DISP_MODE = ?detailMode, ");
		sqlFlexNoOtherLang.append(" 		CONCAT(CAST(MEDICAL_TIME_SET2.APP_TIME AS INTEGER)/60, ':',");
		sqlFlexNoOtherLang.append(" 			FORMAT(CAST(MEDICAL_TIME_SET2.APP_TIME AS INTEGER)%60,'0#')), NULL),");
		// R3_248 医療.日勤勤務時間.丸め
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET1.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_249 医療.日勤勤務時間.端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET1.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET1.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_250 医療.夜勤勤務時間.丸め
		sqlFlexNoOtherLang.append(" 	IIF(TEMP.ROW_ID = 1, ");
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime1Min THEN ?roundingTime1MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime5Min THEN ?roundingTime5MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime6Min THEN ?roundingTime6MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime10Min THEN ?roundingTime10MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime15Min THEN ?roundingTime15MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime20Min THEN ?roundingTime20MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime30Min THEN ?roundingTime30MinText");
		sqlFlexNoOtherLang.append(" 		 WHEN MEDICAL_TIME_SET2.UNIT = ?roundingTime60Min THEN ?roundingTime60MinText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		sqlFlexNoOtherLang.append(" 	NULL),");
		// R3_251 医療.夜勤勤務時間.端数
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET2.ROUNDING = ?roundingDown THEN ?roundingDownText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND MEDICAL_TIME_SET2.ROUNDING = ?roundingUp THEN ?roundingUpText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_252 ０時跨ぎ.0時跨ぎ計算
		sqlFlexNoOtherLang.append(" 	CASE WHEN WORKTIME_DISP_MODE.DISP_MODE != ?detailMode THEN NULL");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.OVER_DAY_CALC_SET = ?isTrue THEN ?isUseText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.OVER_DAY_CALC_SET = ?isFalse THEN ?isNotUseText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END,");
		// R3_253 その他.勤務種類が休暇の場合に就業時間を計算するか
		sqlFlexNoOtherLang.append(" 	CASE WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.HD_CAL_IS_CALCULATE = ?isTrue THEN ?isUseText");
		sqlFlexNoOtherLang.append(" 		 WHEN TEMP.ROW_ID = 1 AND WORKTIME_COMMON_SET.HD_CAL_IS_CALCULATE = ?isFalse THEN ?isNotUseText");
		sqlFlexNoOtherLang.append(" 		 ELSE NULL");
		sqlFlexNoOtherLang.append(" 	END ");
		sqlFlexNoOtherLang.append(" FROM");
		sqlFlexNoOtherLang.append(" 	(SELECT CID, WORKTIME_CD, NAME, ABNAME, SYMBOL, ABOLITION_ATR, DAILY_WORK_ATR, WORKTIME_SET_METHOD, NOTE, MEMO");
		sqlFlexNoOtherLang.append(" 		FROM KSHMT_WT WORK_TIME_SET");
		sqlFlexNoOtherLang.append(" 		WHERE CID = ?companyId AND DAILY_WORK_ATR = ?flexWork) WORK_TIME_SET");
		sqlFlexNoOtherLang.append(" 	CROSS JOIN (VALUES(1),(2),(3),(4),(5),(6),(7),(8),(9),(10)) AS TEMP(ROW_ID)");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_COM_DISP_MODE WORKTIME_DISP_MODE");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORKTIME_DISP_MODE.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORKTIME_DISP_MODE.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM WORKTIME_COMMON_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORKTIME_COMMON_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORKTIME_COMMON_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE FLEX_WORK_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_WORK_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_WORK_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_COM_PRED_TIME PRED_TIME_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PRED_TIME_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PRED_TIME_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_PRED_TS WORK_TIME_SHEET_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORK_TIME_SHEET_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORK_TIME_SHEET_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SHEET_SET.WORK_NO = ?timezoneUseOne");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FL_WEK FLEX_HA_RT_SET1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_HA_RT_SET1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_HA_RT_SET1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HA_RT_SET1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FL_WEK FLEX_HA_RT_SET2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_HA_RT_SET2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_HA_RT_SET2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HA_RT_SET2.AM_PM_ATR = ?amPmAtrAm");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FL_WEK FLEX_HA_RT_SET3");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_HA_RT_SET3.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_HA_RT_SET3.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HA_RT_SET3.AM_PM_ATR = ?amPmAtrPm");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_WORK_TS FLEX_WORK_TIME_SET1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_WORK_TIME_SET1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_WORK_TIME_SET1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_WORK_TIME_SET1.TIME_FRAME_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_WORK_TIME_SET1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_WORK_TS FLEX_WORK_TIME_SET2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_WORK_TIME_SET2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_WORK_TIME_SET2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_WORK_TIME_SET2.TIME_FRAME_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_WORK_TIME_SET2.AM_PM_ATR = ?amPmAtrAm");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_WORK_TS FLEX_WORK_TIME_SET3");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_WORK_TIME_SET3.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_WORK_TIME_SET3.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_WORK_TIME_SET3.TIME_FRAME_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_WORK_TIME_SET3.AM_PM_ATR = ?amPmAtrPm");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_OVER_TS FLEX_OT_TIME_SET1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_OT_TIME_SET1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_OT_TIME_SET1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_OT_TIME_SET1.WORKTIME_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_OT_TIME_SET1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FLEX_OT_FRAME1");
		sqlFlexNoOtherLang.append(" 		ON FLEX_OT_TIME_SET1.CID = FLEX_OT_FRAME1.CID");
		sqlFlexNoOtherLang.append(" 		AND FLEX_OT_TIME_SET1.OT_FRAME_NO = FLEX_OT_FRAME1.OT_FR_NO");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_OVER_TS FLEX_OT_TIME_SET2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_OT_TIME_SET2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_OT_TIME_SET2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_OT_TIME_SET2.WORKTIME_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_OT_TIME_SET2.AM_PM_ATR = ?amPmAtrAm");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FLEX_OT_FRAME2");
		sqlFlexNoOtherLang.append(" 		ON FLEX_OT_TIME_SET2.CID = FLEX_OT_FRAME2.CID");
		sqlFlexNoOtherLang.append(" 		AND FLEX_OT_TIME_SET2.OT_FRAME_NO = FLEX_OT_FRAME2.OT_FR_NO");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_OVER_TS FLEX_OT_TIME_SET3");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_OT_TIME_SET3.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_OT_TIME_SET3.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_OT_TIME_SET3.WORKTIME_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_OT_TIME_SET3.AM_PM_ATR = ?amPmAtrPm");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_OVER_FRAME FLEX_OT_FRAME3");
		sqlFlexNoOtherLang.append(" 		ON FLEX_OT_TIME_SET3.CID = FLEX_OT_FRAME3.CID");
		sqlFlexNoOtherLang.append(" 		AND FLEX_OT_TIME_SET3.OT_FRAME_NO = FLEX_OT_FRAME3.OT_FR_NO");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_PIORITY_SET PIORITY_SET1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND PIORITY_SET1.STAMP_ATR = ?stampPiorityAtrGoingWork");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_PIORITY_SET PIORITY_SET2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND PIORITY_SET2.STAMP_ATR = ?stampPiorityAtrLeaveWork");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET3");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET3.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET3.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND PIORITY_SET3.STAMP_ATR = ?stampPiorityAtrEntering");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET4");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET4.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET4.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND PIORITY_SET4.STAMP_ATR = ?stampPiorityAtrExit");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET5");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET5.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET5.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND PIORITY_SET5.STAMP_ATR = ?stampPiorityAtrPcLogin");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_PIORITY_SET PIORITY_SET6");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = PIORITY_SET6.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = PIORITY_SET6.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND PIORITY_SET6.STAMP_ATR = ?stampPiorityAtrPcLogout");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_ROUNDING_SET ROUNDING_SET1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND ROUNDING_SET1.ATR = ?superiorityAttendance");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_ROUNDING_SET ROUNDING_SET2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND ROUNDING_SET2.ATR = ?superiorityOfficeWork");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_ROUNDING_SET ROUNDING_SET3");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET3.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET3.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND ROUNDING_SET3.ATR = ?superiorityGoOut");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_ROUNDING_SET ROUNDING_SET4");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = ROUNDING_SET4.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = ROUNDING_SET4.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND ROUNDING_SET4.ATR = ?superiorityTurnBack");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_STMP_REF_TS FLEX_STAMP_REFLECT1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_STAMP_REFLECT1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_STAMP_REFLECT1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND FLEX_STAMP_REFLECT1.WORK_NO = ?workNoOne");
		sqlFlexNoOtherLang.append(" 		AND FLEX_STAMP_REFLECT1.ATR = ?goLeavingWorkAtrGoWork");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_STMP_REF_TS FLEX_STAMP_REFLECT2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_STAMP_REFLECT2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_STAMP_REFLECT2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND FLEX_STAMP_REFLECT2.WORK_NO = ?workNoOne");
		sqlFlexNoOtherLang.append(" 		AND FLEX_STAMP_REFLECT2.ATR = ?goLeavingWorkAtrLeaveWork");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_STMP_REF_TS FLEX_STAMP_REFLECT3");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_STAMP_REFLECT3.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_STAMP_REFLECT3.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND FLEX_STAMP_REFLECT3.WORK_NO = ?workNoTwo");
		sqlFlexNoOtherLang.append(" 		AND FLEX_STAMP_REFLECT3.ATR = ?goLeavingWorkAtrGoWork");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_STMP_REF_TS FLEX_STAMP_REFLECT4");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_STAMP_REFLECT4.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_STAMP_REFLECT4.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND FLEX_STAMP_REFLECT4.WORK_NO = ?workNoTwo");
		sqlFlexNoOtherLang.append(" 		AND FLEX_STAMP_REFLECT4.ATR = ?goLeavingWorkAtrLeaveWork");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FI_WEK_TS FLEX_HA_FIX_REST1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_HA_FIX_REST1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_HA_FIX_REST1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_HA_FIX_REST1.PERIOD_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HA_FIX_REST1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FI_WEK_TS FLEX_HA_FIX_REST2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_HA_FIX_REST2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_HA_FIX_REST2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_HA_FIX_REST2.PERIOD_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HA_FIX_REST2.AM_PM_ATR = ?amPmAtrAm");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FI_WEK_TS FLEX_HA_FIX_REST3");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_HA_FIX_REST3.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_HA_FIX_REST3.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_HA_FIX_REST3.PERIOD_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HA_FIX_REST3.AM_PM_ATR = ?amPmAtrPm");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FL_WEK_TS FLEX_HA_REST_SET1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_HA_REST_SET1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_HA_REST_SET1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_HA_REST_SET1.PERIOD_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HA_REST_SET1.AM_PM_ATR = ?amPmAtrOneDay");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FL_WEK_TS FLEX_HA_REST_SET2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_HA_REST_SET2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_HA_REST_SET2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_HA_REST_SET2.PERIOD_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HA_REST_SET2.AM_PM_ATR = ?amPmAtrAm");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FL_WEK_TS FLEX_HA_REST_SET3");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_HA_REST_SET3.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_HA_REST_SET3.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_HA_REST_SET3.PERIOD_NO");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HA_REST_SET3.AM_PM_ATR = ?amPmAtrPm");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FL FLEX_REST_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_REST_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_REST_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FL_HOL FLEX_OD_RT_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_OD_RT_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_OD_RT_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_HOL_TS FLEX_HOL_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_HOL_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_HOL_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_HOL_SET.WORKTIME_NO");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME FLEX_HOL_FRAME");
		sqlFlexNoOtherLang.append(" 		ON FLEX_HOL_SET.CID = FLEX_HOL_FRAME.CID");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HOL_SET.HOL_FRAME_NO = FLEX_HOL_FRAME.WDO_FR_NO");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME FLEX_OUT_HOL_FRAME");
		sqlFlexNoOtherLang.append(" 		ON FLEX_HOL_SET.CID = FLEX_OUT_HOL_FRAME.CID");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HOL_SET.OUT_HOL_FRAME_NO = FLEX_OUT_HOL_FRAME.WDO_FR_NO");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_HD_WORK_FRAME FLEX_PUB_HOL_FRAME");
		sqlFlexNoOtherLang.append(" 		ON FLEX_HOL_SET.CID = FLEX_PUB_HOL_FRAME.CID");
		sqlFlexNoOtherLang.append(" 		AND FLEX_HOL_SET.PUB_HOL_FRAME_NO = FLEX_PUB_HOL_FRAME.WDO_FR_NO");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FI_HOL_TS FLEX_OD_FIX_REST");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_OD_FIX_REST.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_OD_FIX_REST.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_OD_FIX_REST.PERIOD_NO");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KSHMT_WT_FLE_BR_FL_HOL_TS FLEX_OD_REST_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = FLEX_OD_REST_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = FLEX_OD_REST_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND TEMP.ROW_ID = FLEX_OD_REST_SET.PERIOD_NO");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT WORKTIME_GO_OUT_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = WORKTIME_GO_OUT_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = WORKTIME_GO_OUT_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT_ROUND SPECIAL_ROUND_OUT1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SPECIAL_ROUND_OUT1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SPECIAL_ROUND_OUT1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND SPECIAL_ROUND_OUT1.ROUNDING_TIME_TYPE = ?roundingTimeTypeWorkTimezone");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT_ROUND SPECIAL_ROUND_OUT2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SPECIAL_ROUND_OUT2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SPECIAL_ROUND_OUT2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND SPECIAL_ROUND_OUT2.ROUNDING_TIME_TYPE = ?roundingTimeTypePubHolWorkTimezone");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_GOOUT_ROUND SPECIAL_ROUND_OUT3");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SPECIAL_ROUND_OUT3.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SPECIAL_ROUND_OUT3.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND SPECIAL_ROUND_OUT3.ROUNDING_TIME_TYPE = ?roundingTimeTypeOtTimezone");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_LATETIME OTHER_LATE_EARLY1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = OTHER_LATE_EARLY1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = OTHER_LATE_EARLY1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND OTHER_LATE_EARLY1.LATE_EARLY_ATR = ?lateEarlyAtrLate");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_LATETIME OTHER_LATE_EARLY2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = OTHER_LATE_EARLY2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = OTHER_LATE_EARLY2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND OTHER_LATE_EARLY2.LATE_EARLY_ATR = ?lateEarlyAtrEarly");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_LATETIME_MNG LATE_EARLY_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = LATE_EARLY_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = LATE_EARLY_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 	LEFT JOIN KRCMT_BONUS_PAY_SET BONUS_PAY_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = BONUS_PAY_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORKTIME_COMMON_SET.RAISING_SALARY_SET = BONUS_PAY_SET.BONUS_PAY_SET_CD");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_HDCOM SUBSTITUTION_SET1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SUBSTITUTION_SET1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SUBSTITUTION_SET1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND SUBSTITUTION_SET1.ORIGIN_ATR = ?fromOverTime");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_HDCOM SUBSTITUTION_SET2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = SUBSTITUTION_SET2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = SUBSTITUTION_SET2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND SUBSTITUTION_SET2.ORIGIN_ATR = ?workDayOffTime");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_TEMPORARY TEMP_WORKTIME_SET");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = TEMP_WORKTIME_SET.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = TEMP_WORKTIME_SET.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_MEDICAL MEDICAL_TIME_SET1");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = MEDICAL_TIME_SET1.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = MEDICAL_TIME_SET1.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND MEDICAL_TIME_SET1.WORK_SYS_ATR = ?workSystemAtrDayShift");
		sqlFlexNoOtherLang.append(" 	JOIN KSHMT_WT_COM_MEDICAL MEDICAL_TIME_SET2");
		sqlFlexNoOtherLang.append(" 		ON WORK_TIME_SET.CID = MEDICAL_TIME_SET2.CID");
		sqlFlexNoOtherLang.append(" 		AND WORK_TIME_SET.WORKTIME_CD = MEDICAL_TIME_SET2.WORKTIME_CD");
		sqlFlexNoOtherLang.append(" 		AND MEDICAL_TIME_SET2.WORK_SYS_ATR = ?workSystemAtrNightShift");
		sqlFlexNoOtherLang.append(" ORDER BY WORK_TIME_SET.WORKTIME_CD, TEMP.ROW_ID;");

		SELECT_WORK_TIME_FLEX_NO_OTHER_LANGUAGE = sqlFlexNoOtherLang.toString();
	}
	@Override
	public List<Object[]> findWorkTimeNormal(String companyId, String langId) {
		String sql = WorkTimeReportSqlOtherLanguage.SELECT_WORK_TIME_NORMAL;
		if(langId.equals("ja")){
			sql = SELECT_WORK_TIME_NORMAL_NO_OTHER_LANGUAGE;
		}
		Query queryString = getEntityManager().createNativeQuery(sql)
				.setParameter("companyId", companyId)
				.setParameter("isAbolish", AbolishAtr.ABOLISH.value)
				.setParameter("notAbolish", AbolishAtr.NOT_ABOLISH.value)
				.setParameter("isAbolishText", "○")
				.setParameter("notAbolishText", "-")
				.setParameter("regularWork", WorkTimeDailyAtr.REGULAR_WORK.value)
				.setParameter("fixedWork", WorkTimeMethodSet.FIXED_WORK.value)
				.setParameter("difftimeWork", WorkTimeMethodSet.DIFFTIME_WORK.value)
				.setParameter("regularWorkText", TextResource.localize("Enum_WorkTimeDailyAtr_Regular_Work"))
				.setParameter("fixedWorkText", TextResource.localize("Enum_WorkTimeMethodSet_Fixed_Work"))
				.setParameter("difftimeWorkText", TextResource.localize("Enum_WorkTimeMethodSet_DiffTime_Work"))
				.setParameter("isTrue", BooleanGetAtr.USE_ATR)
				.setParameter("isFalse", BooleanGetAtr.USE_NOT)
				.setParameter("isUseHalfDayText", TextResource.localize("KMK003_49"))
				.setParameter("isNotUseHalfDayText", TextResource.localize("KMK003_50"))
				.setParameter("detailMode", DisplayMode.DETAIL.value)
				.setParameter("simpleMode", DisplayMode.SIMPLE.value)
				.setParameter("detailModeText", TextResource.localize("KMK003_191"))
				.setParameter("simpleModeText", TextResource.localize("KMK003_190"))
				.setParameter("isNightShiftText", "○")
				.setParameter("isNotNightShiftText", "-")
				.setParameter("isIncludeOtText", "○")
				.setParameter("isNotIncludeOtText", "-")
				.setParameter("isUseSecondWorkingDayText", "○")
				.setParameter("isNotUseSecondWorkingDayText", "-")
				.setParameter("roundingTime1Min", Unit.ROUNDING_TIME_1MIN.value)
				.setParameter("roundingTime5Min", Unit.ROUNDING_TIME_5MIN.value)
				.setParameter("roundingTime6Min", Unit.ROUNDING_TIME_6MIN.value)
				.setParameter("roundingTime10Min", Unit.ROUNDING_TIME_10MIN.value)
				.setParameter("roundingTime15Min", Unit.ROUNDING_TIME_15MIN.value)
				.setParameter("roundingTime20Min", Unit.ROUNDING_TIME_20MIN.value)
				.setParameter("roundingTime30Min", Unit.ROUNDING_TIME_30MIN.value)
				.setParameter("roundingTime60Min", Unit.ROUNDING_TIME_60MIN.value)
				.setParameter("roundingTime1MinText", TextResource.localize("Enum_RoundingTime_1Min"))
				.setParameter("roundingTime5MinText", TextResource.localize("Enum_RoundingTime_5Min"))
				.setParameter("roundingTime6MinText", TextResource.localize("Enum_RoundingTime_6Min"))
				.setParameter("roundingTime10MinText", TextResource.localize("Enum_RoundingTime_10Min"))
				.setParameter("roundingTime15MinText", TextResource.localize("Enum_RoundingTime_15Min"))
				.setParameter("roundingTime20MinText", TextResource.localize("Enum_RoundingTime_20Min"))
				.setParameter("roundingTime30MinText", TextResource.localize("Enum_RoundingTime_30Min"))
				.setParameter("roundingTime60MinText", TextResource.localize("Enum_RoundingTime_60Min"))
				.setParameter("roundingDown", Rounding.ROUNDING_DOWN.value)
				.setParameter("roundingUp", Rounding.ROUNDING_UP.value)
				.setParameter("roundingDownText", TextResource.localize("Enum_Rounding_Down"))
				.setParameter("roundingUpText", TextResource.localize("Enum_Rounding_Up"))
				.setParameter("treatEarlyOtWork", "○")
				.setParameter("notTreatEarlyOtWork", "-")
				.setParameter("isLegalOtSet", LegalOTSetting.LEGAL_INTERNAL_TIME.value)
				.setParameter("isNotLegalOtSet", LegalOTSetting.OUTSIDE_LEGAL_TIME.value)
				.setParameter("isUseText", TextResource.localize("KMK003_142"))
				.setParameter("isNotUseText", TextResource.localize("KMK003_143"))
				.setParameter("beforePiority", MultiStampTimePiorityAtr.BEFORE_PIORITY.value)
				.setParameter("afterPiority", MultiStampTimePiorityAtr.AFTER_PIORITY.value)
				.setParameter("beforePiorityText", TextResource.localize("KMK003_69"))
				.setParameter("afterPiorityText", TextResource.localize("KMK003_70"))
				.setParameter("fontRearSectionBefore", FontRearSection.BEFORE.value)
				.setParameter("fontRearSectionAfter", FontRearSection.AFTER.value)
				.setParameter("fontRearSectionBeforeText", TextResource.localize("KMK003_72"))
				.setParameter("fontRearSectionAfterText", TextResource.localize("KMK003_73"))
				.setParameter("calculateMethodMasterRef", FixedRestCalculateMethod.MASTER_REF.value)
				.setParameter("calculateMethodPlanRef", FixedRestCalculateMethod.PLAN_REF.value)
				.setParameter("calculateMethodMasterRefText", TextResource.localize("KMK003_239"))
				.setParameter("calculateMethodPlanRefText", TextResource.localize("KMK003_240"))
				.setParameter("calcMethodAppropAll", RestTimeOfficeWorkCalcMethod.APPROP_ALL.value)
				.setParameter("calcMethodNotAppropAll", RestTimeOfficeWorkCalcMethod.NOT_APPROP_ALL.value)
				.setParameter("calcMethodOfficeWorkAppropAll", RestTimeOfficeWorkCalcMethod.OFFICE_WORK_APPROP_ALL.value)
				.setParameter("calcMethodAppropAllText", TextResource.localize("KMK003_235"))
				.setParameter("calcMethodNotAppropAllText", TextResource.localize("KMK003_236"))
				.setParameter("calcMethodOfficeWorkAppropAllText", TextResource.localize("KMK003_237"))
				.setParameter("roudingAfterTotalText", TextResource.localize("KMK003_198"))
				.setParameter("roudingAfterEachTimePeriodText", TextResource.localize("KMK003_199"))
				.setParameter("reverseTimezoneRounding", RoundingGoOutTimeSheet.REVERSE_ROUNDING_EACH_TIMEZONE.value)
				.setParameter("isSetRounding", RoundingGoOutTimeSheet.INDIVIDUAL_ROUNDING.value)
				.setParameter("reverseTimezoneRoundingText", TextResource.localize("KMK003_87"))
				.setParameter("isSetRoundingText", TextResource.localize("KMK003_88"))
				.setParameter("isDeducteFromTimeText", "○")
				.setParameter("isNotDeducteFromTimeText", "-")
				.setParameter("isExtractLateEarlyTimeText", "○")
				.setParameter("isNotExtractLateEarlyTimeText", "-")
				.setParameter("isIncludeWorktimeText", "○")
				.setParameter("isNotIncludeWorktimeText", "-")
				.setParameter("isUseSubstitutionText", "○")
				.setParameter("isNotUseSubstitutionText", "-")
				.setParameter("isUpdStartTimeText", "○")
				.setParameter("isNotUpdStartTimeText", "-")
				.setParameter("specifiedTimeSubHol", SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL.value)
				.setParameter("certainTimeExcSubHol", SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL.value)
				.setParameter("specifiedTimeSubHolText", TextResource.localize("KMK003_106"))
				.setParameter("certainTimeExcSubHolText", TextResource.localize("KMK003_108"))
				.setParameter("childCareWorkUseText", TextResource.localize("KMK003_116"))
				.setParameter("childCareWorkNotUseText", TextResource.localize("KMK003_117"))
				.setParameter("nurTimezoneWorkUseText", TextResource.localize("KMK003_196"))
				.setParameter("nurTimezoneWorkNotUseText", TextResource.localize("KMK003_197"))
				.setParameter("calcMethodExceededPredAddVacationWorking", CalcMethodExceededPredAddVacation.CALC_AS_WORKING.value)
				.setParameter("calcMethodExceededPredAddVacationOvertime", CalcMethodExceededPredAddVacation.CALC_AS_OVERTIME.value)
				.setParameter("calcMethodNoBreakWorking", CalcMethodNoBreak.CALC_AS_WORKING.value)
				.setParameter("calcMethodNoBreakOvertime", CalcMethodNoBreak.CALC_AS_OVERTIME.value)
				.setParameter("timezoneUseOne", TimezoneUse.SHIFT_ONE)
				.setParameter("timezoneUseTwo", TimezoneUse.SHIFT_TWO)
				.setParameter("amPmAtrOneDay", AmPmAtr.ONE_DAY.value)
				.setParameter("amPmAtrAm", AmPmAtr.AM.value)
				.setParameter("amPmAtrPm", AmPmAtr.PM.value)
				.setParameter("stampPiorityAtrGoingWork", StampPiorityAtr.GOING_WORK.value)
				.setParameter("stampPiorityAtrLeaveWork", StampPiorityAtr.LEAVE_WORK.value)
				.setParameter("stampPiorityAtrEntering", StampPiorityAtr.ENTERING.value)
				.setParameter("stampPiorityAtrExit", StampPiorityAtr.EXIT.value)
				.setParameter("stampPiorityAtrPcLogin", StampPiorityAtr.PCLOGIN.value)
				.setParameter("stampPiorityAtrPcLogout", StampPiorityAtr.PC_LOGOUT.value)
				.setParameter("superiorityAttendance", Superiority.ATTENDANCE.value)
				.setParameter("superiorityOfficeWork", Superiority.OFFICE_WORK.value)
				.setParameter("superiorityGoOut", Superiority.GO_OUT.value)
				.setParameter("superiorityTurnBack", Superiority.TURN_BACK.value)
				.setParameter("goLeavingWorkAtrGoWork", GoLeavingWorkAtr.GO_WORK.value)
				.setParameter("goLeavingWorkAtrLeaveWork", GoLeavingWorkAtr.LEAVING_WORK.value)
				.setParameter("roundingTimeTypeWorkTimezone", RoundingTimeType.WORK_TIMEZONE.value)
				.setParameter("roundingTimeTypePubHolWorkTimezone", RoundingTimeType.PUB_HOL_WORK_TIMEZONE.value)
				.setParameter("roundingTimeTypeOtTimezone", RoundingTimeType.OT_TIMEZONE.value)
				.setParameter("lateEarlyAtrLate", LateEarlyAtr.LATE.value)
				.setParameter("lateEarlyAtrEarly", LateEarlyAtr.EARLY.value)
				.setParameter("fromOverTime", CompensatoryOccurrenceDivision.FromOverTime.value)
				.setParameter("workDayOffTime", CompensatoryOccurrenceDivision.WorkDayOffTime.value)
				.setParameter("workSystemAtrDayShift", WorkSystemAtr.DAY_SHIFT.value)
				.setParameter("workSystemAtrNightShift", WorkSystemAtr.NIGHT_SHIFT.value)
				.setParameter("workNoOne", 1)
				.setParameter("workNoTwo", 2)
				.setParameter("masterUnregistered", TextResource.localize("Enum_MasterUnregistered"));
		return queryString.getResultList();
	}

	@Override
	public List<Object[]> findWorkTimeFlow(String companyId, String langId) {
		String sql = WorkTimeReportSqlOtherLanguage.SELECT_WORK_TIME_FLOW;
		if(langId.equals("ja")){
			sql = SELECT_WORK_TIME_FLOW_NO_OTHER_LANGUAGE;
		}
		Query queryString = getEntityManager().createNativeQuery(sql)
				.setParameter("companyId", companyId)
				.setParameter("isAbolish", AbolishAtr.ABOLISH.value)
				.setParameter("notAbolish", AbolishAtr.NOT_ABOLISH.value)
				.setParameter("isAbolishText", "○")
				.setParameter("notAbolishText", "-")
				.setParameter("regularWork", WorkTimeDailyAtr.REGULAR_WORK.value)
				.setParameter("flowWork", WorkTimeMethodSet.FLOW_WORK.value)
				.setParameter("regularWorkText", TextResource.localize("Enum_WorkTimeDailyAtr_Regular_Work"))
				.setParameter("flowWorkText", TextResource.localize("Enum_WorkTimeMethodSet_Flow_Work"))
				.setParameter("detailMode", DisplayMode.DETAIL.value)
				.setParameter("simpleMode", DisplayMode.SIMPLE.value)
				.setParameter("detailModeText", TextResource.localize("KMK003_191"))
				.setParameter("simpleModeText", TextResource.localize("KMK003_190"))
				.setParameter("isTrue", BooleanGetAtr.USE_ATR)
				.setParameter("isFalse", BooleanGetAtr.USE_NOT)
				.setParameter("isNightShiftText", "○")
				.setParameter("isNotNightShiftText", "-")			
				.setParameter("isIncludeOtText", "○")
				.setParameter("isNotIncludeOtText", "-")		
				.setParameter("isUseSecondWorkingDayText", "○")
				.setParameter("isNotUseSecondWorkingDayText", "-")			
				.setParameter("roundingTime1Min", Unit.ROUNDING_TIME_1MIN.value)
				.setParameter("roundingTime5Min", Unit.ROUNDING_TIME_5MIN.value)
				.setParameter("roundingTime6Min", Unit.ROUNDING_TIME_6MIN.value)
				.setParameter("roundingTime10Min", Unit.ROUNDING_TIME_10MIN.value)
				.setParameter("roundingTime15Min", Unit.ROUNDING_TIME_15MIN.value)
				.setParameter("roundingTime20Min", Unit.ROUNDING_TIME_20MIN.value)
				.setParameter("roundingTime30Min", Unit.ROUNDING_TIME_30MIN.value)
				.setParameter("roundingTime60Min", Unit.ROUNDING_TIME_60MIN.value)
				.setParameter("roundingTime1MinText", TextResource.localize("Enum_RoundingTime_1Min"))
				.setParameter("roundingTime5MinText", TextResource.localize("Enum_RoundingTime_5Min"))
				.setParameter("roundingTime6MinText", TextResource.localize("Enum_RoundingTime_6Min"))
				.setParameter("roundingTime10MinText", TextResource.localize("Enum_RoundingTime_10Min"))
				.setParameter("roundingTime15MinText", TextResource.localize("Enum_RoundingTime_15Min"))
				.setParameter("roundingTime20MinText", TextResource.localize("Enum_RoundingTime_20Min"))
				.setParameter("roundingTime30MinText", TextResource.localize("Enum_RoundingTime_30Min"))
				.setParameter("roundingTime60MinText", TextResource.localize("Enum_RoundingTime_60Min"))	
				.setParameter("roundingDown", Rounding.ROUNDING_DOWN.value)
				.setParameter("roundingUp", Rounding.ROUNDING_UP.value)	
				.setParameter("roundingDownOver", Rounding.ROUNDING_DOWN_OVER.value)	
				.setParameter("roundingDownText", TextResource.localize("Enum_Rounding_Down"))
				.setParameter("roundingUpText", TextResource.localize("Enum_Rounding_Up"))	
				.setParameter("roundingDownOverText", TextResource.localize("Enum_Rounding_Down_Over"))	
				.setParameter("restCalcMethodReferMaster", FlowFixedRestCalcMethod.REFER_MASTER.value)
				.setParameter("restCalcMethodReferSchedule", FlowFixedRestCalcMethod.REFER_SCHEDULE.value)
				.setParameter("restCalcMethodWithoutRefer", FlowFixedRestCalcMethod.STAMP_WHITOUT_REFER.value)
				.setParameter("restCalcMethodReferMasterText", TextResource.localize("KMK003_241"))
				.setParameter("restCalcMethodReferScheduleText", TextResource.localize("KMK003_242"))
				.setParameter("restCalcMethodWithoutReferText", TextResource.localize("KMK003_243"))
				.setParameter("isCalcFromSchedule", "○")
				.setParameter("isNotCalcFromSchedule", "-")
				.setParameter("isReferRestTime", "○")
				.setParameter("isNotReferRestTime", "-")
				.setParameter("userPrivateGoOutRest", "○")
				.setParameter("notUserPrivateGoOutRest", "-")
				.setParameter("userAssoGoOutRest", "○")
				.setParameter("notUserAssoGoOutRest", "-")
				.setParameter("flowRestCalcMethodReferMaster", FlowRestCalcMethod.REFER_MASTER.value)
				.setParameter("flowRestCalcMethodMasterAndStamp", FlowRestCalcMethod.USE_MASTER_AND_STAMP.value)
				.setParameter("flowRestCalcMethodMasterAndStampText", TextResource.localize("KMK003_252"))
				.setParameter("useStamp", "○")
				.setParameter("notUserStamp", "-")
				.setParameter("useRestTimeToCalc", FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE.value)
				.setParameter("useGoOutTimeToCalc", FlowRestClockCalcMethod.USE_GOOUT_TIME_TO_CALCULATE.value)
				.setParameter("useRestTimeToCalcText", TextResource.localize("KMK003_257"))
				.setParameter("useGoOutTimeToCalcText", TextResource.localize("KMK003_258"))
				.setParameter("isClockManage", RestClockManageAtr.IS_CLOCK_MANAGE.value)
				.setParameter("notClockManage", RestClockManageAtr.NOT_CLOCK_MANAGE.value)
				.setParameter("isClockManageText", TextResource.localize("KMK003_260"))
				.setParameter("notClockManageText", TextResource.localize("KMK003_261"))
				.setParameter("calcFromPlanStartTime", PrePlanWorkTimeCalcMethod.CALC_FROM_PLAN_START_TIME.value)
				.setParameter("calcFromWorkTime", PrePlanWorkTimeCalcMethod.CALC_FROM_WORK_TIME.value)
				.setParameter("calcFromPlanStartTimeText", TextResource.localize("KMK003_136"))
				.setParameter("calcFromWorkTimeText", TextResource.localize("KMK003_137"))
				.setParameter("fixedChangeAtrNotChange", FixedChangeAtr.NOT_CHANGE.value)
				.setParameter("fixedChangeAtrAfterShift", FixedChangeAtr.AFTER_SHIFT.value)
				.setParameter("fixedChangeAtrBeforeAfterShift", FixedChangeAtr.BEFORE_AFTER_SHIFT.value)
				.setParameter("fixedChangeAtrNotChangeText", TextResource.localize("Enum_FixedChangeAtr_notChange"))
				.setParameter("fixedChangeAtrAfterShiftText", TextResource.localize("Enum_FixedChangeAtr_afterShift"))
				.setParameter("fixedChangeAtrBeforeAfterShiftText", TextResource.localize("Enum_FixedChangeAtr_beforeAfterShift"))
				.setParameter("isUseText", TextResource.localize("KMK003_142"))
				.setParameter("isNotUseText", TextResource.localize("KMK003_143"))
				.setParameter("isUseRestAfterSetText", "○")
				.setParameter("isNotUseRestAfterSetText", "-")
				.setParameter("beforePiority", MultiStampTimePiorityAtr.BEFORE_PIORITY.value)
				.setParameter("afterPiority", MultiStampTimePiorityAtr.AFTER_PIORITY.value)
				.setParameter("beforePiorityText", TextResource.localize("KMK003_69"))
				.setParameter("afterPiorityText", TextResource.localize("KMK003_70"))
				.setParameter("fontRearSectionBefore", FontRearSection.BEFORE.value)
				.setParameter("fontRearSectionAfter", FontRearSection.AFTER.value)
				.setParameter("fontRearSectionBeforeText", TextResource.localize("KMK003_72"))
				.setParameter("fontRearSectionAfterText", TextResource.localize("KMK003_73"))
				.setParameter("calcMethodAppropAll", RestTimeOfficeWorkCalcMethod.APPROP_ALL.value)
				.setParameter("calcMethodNotAppropAll", RestTimeOfficeWorkCalcMethod.NOT_APPROP_ALL.value)
				.setParameter("calcMethodOfficeWorkAppropAll",RestTimeOfficeWorkCalcMethod.OFFICE_WORK_APPROP_ALL.value)
				.setParameter("calcMethodAppropAllText", TextResource.localize("KMK003_235"))
				.setParameter("calcMethodNotAppropAllText", TextResource.localize("KMK003_236"))
				.setParameter("calcMethodOfficeWorkAppropAllText", TextResource.localize("KMK003_237"))
				.setParameter("isUsePluralWorkRestTimeText", TextResource.localize("KMK003_113"))
				.setParameter("isNotUsePluralWorkRestTimeText", TextResource.localize("KMK003_114"))
				.setParameter("reverseTimezoneRounding", RoundingGoOutTimeSheet.REVERSE_ROUNDING_EACH_TIMEZONE.value)
				.setParameter("isSetRounding", RoundingGoOutTimeSheet.INDIVIDUAL_ROUNDING.value)
				.setParameter("reverseTimezoneRoundingText", TextResource.localize("KMK003_87"))
				.setParameter("isSetRoundingText", TextResource.localize("KMK003_88"))
				.setParameter("roudingAfterTotalText", TextResource.localize("KMK003_198"))
				.setParameter("roudingAfterEachTimePeriodText", TextResource.localize("KMK003_199"))
				.setParameter("isExtractLateEarlyTimeText", "○")
				.setParameter("isNotExtractLateEarlyTimeText", "-")
				.setParameter("isIncludeWorktimeText", "○")
				.setParameter("isNotIncludeWorktimeText", "-")
				.setParameter("isUseSubstitutionText", "○")
				.setParameter("isNotUseSubstitutionText", "-")
				.setParameter("specifiedTimeSubHol", SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL.value)
				.setParameter("certainTimeExcSubHol", SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL.value)
				.setParameter("specifiedTimeSubHolText", TextResource.localize("KMK003_106"))
				.setParameter("certainTimeExcSubHolText", TextResource.localize("KMK003_108"))
				.setParameter("childCareWorkUseText", TextResource.localize("KMK003_116"))
				.setParameter("childCareWorkNotUseText", TextResource.localize("KMK003_117"))
				.setParameter("nurTimezoneWorkUseText", TextResource.localize("KMK003_196"))
				.setParameter("nurTimezoneWorkNotUseText", TextResource.localize("KMK003_197"))
				.setParameter("timezoneUseOne", TimezoneUse.SHIFT_ONE)
				.setParameter("timezoneUseTwo", TimezoneUse.SHIFT_TWO)
				.setParameter("stampPiorityAtrGoingWork", StampPiorityAtr.GOING_WORK.value)
				.setParameter("stampPiorityAtrLeaveWork", StampPiorityAtr.LEAVE_WORK.value)
				.setParameter("stampPiorityAtrEntering", StampPiorityAtr.ENTERING.value)
				.setParameter("stampPiorityAtrExit", StampPiorityAtr.EXIT.value)
				.setParameter("stampPiorityAtrPcLogin", StampPiorityAtr.PCLOGIN.value)
				.setParameter("stampPiorityAtrPcLogout", StampPiorityAtr.PC_LOGOUT.value)
				.setParameter("superiorityAttendance", Superiority.ATTENDANCE.value)
				.setParameter("superiorityOfficeWork", Superiority.OFFICE_WORK.value)
				.setParameter("superiorityGoOut", Superiority.GO_OUT.value)
				.setParameter("superiorityTurnBack", Superiority.TURN_BACK.value)
				.setParameter("goLeavingWorkAtrGoWork", GoLeavingWorkAtr.GO_WORK.value)
				.setParameter("goLeavingWorkAtrLeaveWork", GoLeavingWorkAtr.LEAVING_WORK.value)
				.setParameter("roundingTimeTypeWorkTimezone", RoundingTimeType.WORK_TIMEZONE.value)
				.setParameter("roundingTimeTypePubHolWorkTimezone", RoundingTimeType.PUB_HOL_WORK_TIMEZONE.value)
				.setParameter("roundingTimeTypeOtTimezone", RoundingTimeType.OT_TIMEZONE.value)
				.setParameter("lateEarlyAtrLate", LateEarlyAtr.LATE.value)
				.setParameter("lateEarlyAtrEarly", LateEarlyAtr.EARLY.value)
				.setParameter("fromOverTime", CompensatoryOccurrenceDivision.FromOverTime.value)
				.setParameter("workDayOffTime", CompensatoryOccurrenceDivision.WorkDayOffTime.value)
				.setParameter("workSystemAtrDayShift", WorkSystemAtr.DAY_SHIFT.value)
				.setParameter("workSystemAtrNightShift", WorkSystemAtr.NIGHT_SHIFT.value)
				.setParameter("resttimeAtrOffDay", ResttimeAtr.OFF_DAY.value)
				.setParameter("resttimeAtrHalfDay", ResttimeAtr.HALF_DAY.value)
				.setParameter("workNoOne", 1)
				.setParameter("masterUnregistered", TextResource.localize("Enum_MasterUnregistered"));
		return queryString.getResultList();
	}

	@Override
	public List<Object[]> findWorkTimeFlex(String companyId, String langId) {
		String sql = WorkTimeReportSqlOtherLanguage.SELECT_WORK_TIME_FLEX;
		if(langId.equals("ja")){
			sql = SELECT_WORK_TIME_FLEX_NO_OTHER_LANGUAGE;
		}
		Query queryString = getEntityManager().createNativeQuery(sql)
				.setParameter("companyId", companyId)
				.setParameter("isAbolish", AbolishAtr.ABOLISH.value)
				.setParameter("notAbolish", AbolishAtr.NOT_ABOLISH.value)
				.setParameter("isAbolishText", "○")
				.setParameter("notAbolishText", "-")
				.setParameter("flexWork", WorkTimeDailyAtr.FLEX_WORK.value)
				.setParameter("flexWorkText", TextResource.localize("Enum_WorkTimeDailyAtr_Flex_Work"))
				.setParameter("isTrue", BooleanGetAtr.USE_ATR)
				.setParameter("isFalse", BooleanGetAtr.USE_NOT)
				.setParameter("isUseHalfDayText", TextResource.localize("KMK003_49"))
				.setParameter("isNotUseHalfDayText", TextResource.localize("KMK003_50"))
				.setParameter("detailMode", DisplayMode.DETAIL.value)
				.setParameter("simpleMode", DisplayMode.SIMPLE.value)
				.setParameter("detailModeText", TextResource.localize("KMK003_191"))
				.setParameter("simpleModeText", TextResource.localize("KMK003_190"))
				.setParameter("isNightShiftText", "○")
				.setParameter("isNotNightShiftText", "-")			
				.setParameter("isIncludeOtText", "○")
				.setParameter("isNotIncludeOtText", "-")			
				.setParameter("isDeducteFromTimeText", "○")
				.setParameter("isNotDeducteFromTimeText", "-")	
				.setParameter("useCoretime", ApplyAtr.USE.value)
				.setParameter("notUseCoretime", ApplyAtr.NOT_USE.value)
				.setParameter("useCoretimeText", TextResource.localize("KMK003_158"))
				.setParameter("notUseCoretimeText", TextResource.localize("KMK003_159"))
				.setParameter("restCalcMethodReferMaster", FlowFixedRestCalcMethod.REFER_MASTER.value)
				.setParameter("restCalcMethodReferSchedule", FlowFixedRestCalcMethod.REFER_SCHEDULE.value)
				.setParameter("restCalcMethodWithoutRefer", FlowFixedRestCalcMethod.STAMP_WHITOUT_REFER.value)
				.setParameter("restCalcMethodReferMasterText", TextResource.localize("KMK003_241"))
				.setParameter("restCalcMethodReferScheduleText", TextResource.localize("KMK003_242"))
				.setParameter("restCalcMethodWithoutReferText", TextResource.localize("KMK003_243"))
				.setParameter("isCalcFromSchedule", "○")
				.setParameter("isNotCalcFromSchedule", "-")
				.setParameter("isReferRestTime", "○")
				.setParameter("isNotReferRestTime", "-")
				.setParameter("userPrivateGoOutRest", "○")
				.setParameter("notUserPrivateGoOutRest", "-")
				.setParameter("userAssoGoOutRest", "○")
				.setParameter("notUserAssoGoOutRest", "-")
				.setParameter("flowRestCalcMethodReferMaster", FlowRestCalcMethod.REFER_MASTER.value)
				.setParameter("flowRestCalcMethodMasterAndStamp", FlowRestCalcMethod.USE_MASTER_AND_STAMP.value)
				.setParameter("flowRestCalcMethodMasterAndStampText", TextResource.localize("KMK003_252"))
				.setParameter("useStamp", "○")
				.setParameter("notUserStamp", "-")
				.setParameter("useRestTimeToCalc", FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE.value)
				.setParameter("useGoOutTimeToCalc", FlowRestClockCalcMethod.USE_GOOUT_TIME_TO_CALCULATE.value)
				.setParameter("useRestTimeToCalcText", TextResource.localize("KMK003_257"))
				.setParameter("useGoOutTimeToCalcText", TextResource.localize("KMK003_258"))
				.setParameter("isClockManage", RestClockManageAtr.IS_CLOCK_MANAGE.value)
				.setParameter("notClockManage", RestClockManageAtr.NOT_CLOCK_MANAGE.value)
				.setParameter("isClockManageText", TextResource.localize("KMK003_260"))
				.setParameter("notClockManageText", TextResource.localize("KMK003_261"))
				.setParameter("roundingTime1Min", Unit.ROUNDING_TIME_1MIN.value)
				.setParameter("roundingTime5Min", Unit.ROUNDING_TIME_5MIN.value)
				.setParameter("roundingTime6Min", Unit.ROUNDING_TIME_6MIN.value)
				.setParameter("roundingTime10Min", Unit.ROUNDING_TIME_10MIN.value)
				.setParameter("roundingTime15Min", Unit.ROUNDING_TIME_15MIN.value)
				.setParameter("roundingTime20Min", Unit.ROUNDING_TIME_20MIN.value)
				.setParameter("roundingTime30Min", Unit.ROUNDING_TIME_30MIN.value)
				.setParameter("roundingTime60Min", Unit.ROUNDING_TIME_60MIN.value)
				.setParameter("roundingTime1MinText", TextResource.localize("Enum_RoundingTime_1Min"))
				.setParameter("roundingTime5MinText", TextResource.localize("Enum_RoundingTime_5Min"))
				.setParameter("roundingTime6MinText", TextResource.localize("Enum_RoundingTime_6Min"))
				.setParameter("roundingTime10MinText", TextResource.localize("Enum_RoundingTime_10Min"))
				.setParameter("roundingTime15MinText", TextResource.localize("Enum_RoundingTime_15Min"))
				.setParameter("roundingTime20MinText", TextResource.localize("Enum_RoundingTime_20Min"))
				.setParameter("roundingTime30MinText", TextResource.localize("Enum_RoundingTime_30Min"))
				.setParameter("roundingTime60MinText", TextResource.localize("Enum_RoundingTime_60Min"))	
				.setParameter("roundingDown", Rounding.ROUNDING_DOWN.value)
				.setParameter("roundingUp", Rounding.ROUNDING_UP.value)	
				.setParameter("roundingDownText", TextResource.localize("Enum_Rounding_Down"))
				.setParameter("roundingUpText", TextResource.localize("Enum_Rounding_Up"))
				.setParameter("treatEarlyOtWork", "○")
				.setParameter("notTreatEarlyOtWork", "-")
				.setParameter("isUseText", TextResource.localize("KMK003_142"))
				.setParameter("isNotUseText", TextResource.localize("KMK003_143"))
				.setParameter("isUseRestAfterSetText", "○")
				.setParameter("isNotUseRestAfterSetText", "-")
				.setParameter("beforePiority", MultiStampTimePiorityAtr.BEFORE_PIORITY.value)
				.setParameter("afterPiority", MultiStampTimePiorityAtr.AFTER_PIORITY.value)
				.setParameter("beforePiorityText", TextResource.localize("KMK003_69"))
				.setParameter("afterPiorityText", TextResource.localize("KMK003_70"))
				.setParameter("fontRearSectionBefore", FontRearSection.BEFORE.value)
				.setParameter("fontRearSectionAfter", FontRearSection.AFTER.value)
				.setParameter("fontRearSectionBeforeText", TextResource.localize("KMK003_72"))
				.setParameter("fontRearSectionAfterText", TextResource.localize("KMK003_73"))
				.setParameter("calcMethodAppropAll", RestTimeOfficeWorkCalcMethod.APPROP_ALL.value)
				.setParameter("calcMethodNotAppropAll", RestTimeOfficeWorkCalcMethod.NOT_APPROP_ALL.value)
				.setParameter("calcMethodOfficeWorkAppropAll",RestTimeOfficeWorkCalcMethod.OFFICE_WORK_APPROP_ALL.value)
				.setParameter("calcMethodAppropAllText", TextResource.localize("KMK003_235"))
				.setParameter("calcMethodNotAppropAllText", TextResource.localize("KMK003_236"))
				.setParameter("calcMethodOfficeWorkAppropAllText", TextResource.localize("KMK003_237"))
				.setParameter("reverseTimezoneRounding", RoundingGoOutTimeSheet.REVERSE_ROUNDING_EACH_TIMEZONE.value)
				.setParameter("isSetRounding", RoundingGoOutTimeSheet.INDIVIDUAL_ROUNDING.value)
				.setParameter("reverseTimezoneRoundingText", TextResource.localize("KMK003_87"))
				.setParameter("isSetRoundingText", TextResource.localize("KMK003_88"))
				.setParameter("roudingAfterTotalText", TextResource.localize("KMK003_198"))
				.setParameter("roudingAfterEachTimePeriodText", TextResource.localize("KMK003_199"))
				.setParameter("isExtractLateEarlyTimeText", "○")
				.setParameter("isNotExtractLateEarlyTimeText", "-")
				.setParameter("isIncludeWorktimeText", "○")
				.setParameter("isNotIncludeWorktimeText", "-")
				.setParameter("isUseSubstitutionText", "○")
				.setParameter("isNotUseSubstitutionText", "-")
				.setParameter("specifiedTimeSubHol", SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL.value)
				.setParameter("certainTimeExcSubHol", SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL.value)
				.setParameter("specifiedTimeSubHolText", TextResource.localize("KMK003_106"))
				.setParameter("certainTimeExcSubHolText", TextResource.localize("KMK003_108"))
				.setParameter("childCareWorkUseText", TextResource.localize("KMK003_116"))
				.setParameter("childCareWorkNotUseText", TextResource.localize("KMK003_117"))
				.setParameter("nurTimezoneWorkUseText", TextResource.localize("KMK003_196"))
				.setParameter("nurTimezoneWorkNotUseText", TextResource.localize("KMK003_197"))
				.setParameter("timezoneUseOne", TimezoneUse.SHIFT_ONE)
				.setParameter("amPmAtrOneDay", AmPmAtr.ONE_DAY.value)
				.setParameter("amPmAtrAm", AmPmAtr.AM.value)
				.setParameter("amPmAtrPm", AmPmAtr.PM.value)
				.setParameter("stampPiorityAtrGoingWork", StampPiorityAtr.GOING_WORK.value)
				.setParameter("stampPiorityAtrLeaveWork", StampPiorityAtr.LEAVE_WORK.value)
				.setParameter("stampPiorityAtrEntering", StampPiorityAtr.ENTERING.value)
				.setParameter("stampPiorityAtrExit", StampPiorityAtr.EXIT.value)
				.setParameter("stampPiorityAtrPcLogin", StampPiorityAtr.PCLOGIN.value)
				.setParameter("stampPiorityAtrPcLogout", StampPiorityAtr.PC_LOGOUT.value)
				.setParameter("superiorityAttendance", Superiority.ATTENDANCE.value)
				.setParameter("superiorityOfficeWork", Superiority.OFFICE_WORK.value)
				.setParameter("superiorityGoOut", Superiority.GO_OUT.value)
				.setParameter("superiorityTurnBack", Superiority.TURN_BACK.value)
				.setParameter("goLeavingWorkAtrGoWork", GoLeavingWorkAtr.GO_WORK.value)
				.setParameter("goLeavingWorkAtrLeaveWork", GoLeavingWorkAtr.LEAVING_WORK.value)
				.setParameter("roundingTimeTypeWorkTimezone", RoundingTimeType.WORK_TIMEZONE.value)
				.setParameter("roundingTimeTypePubHolWorkTimezone", RoundingTimeType.PUB_HOL_WORK_TIMEZONE.value)
				.setParameter("roundingTimeTypeOtTimezone", RoundingTimeType.OT_TIMEZONE.value)
				.setParameter("lateEarlyAtrLate", LateEarlyAtr.LATE.value)
				.setParameter("lateEarlyAtrEarly", LateEarlyAtr.EARLY.value)
				.setParameter("fromOverTime", CompensatoryOccurrenceDivision.FromOverTime.value)
				.setParameter("workDayOffTime", CompensatoryOccurrenceDivision.WorkDayOffTime.value)
				.setParameter("workSystemAtrDayShift", WorkSystemAtr.DAY_SHIFT.value)
				.setParameter("workSystemAtrNightShift", WorkSystemAtr.NIGHT_SHIFT.value)
				.setParameter("resttimeAtrHalfDay", ResttimeAtr.HALF_DAY.value)
				.setParameter("workNoOne", 1)
				.setParameter("workNoTwo", 2)
				.setParameter("masterUnregistered", TextResource.localize("Enum_MasterUnregistered"));
		return queryString.getResultList();
	}

}
