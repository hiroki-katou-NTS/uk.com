package nts.uk.file.at.infra.specialholiday;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.uk.file.at.app.export.specialholiday.SpecialHolidayExRepository;
import nts.uk.file.at.app.export.specialholiday.SpecialHolidayUtils;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaSpecialHolidayExRepository extends JpaRepository implements SpecialHolidayExRepository {
	
	private static final String GET_SPECIAL_HOLIDAY_DATA;
	
	private static final String GET_SPECIAL_HOLIDAY_EVENT;
	static {
		
	StringBuilder specialHDData = new StringBuilder()
			.append("SELECT * ")
			.append(" FROM (SELECT 		")																										
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.SPHD_CD")
			.append("	ELSE NULL")
			.append("	END SPHD_CD, ")
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.SPHD_NAME")
			.append("	ELSE NULL")
			.append("	END SPHD_NAME, ")
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
			.append("		CASE WHEN (TABLE_RESULT.abName IS NULL OR TABLE_RESULT.leaveName IS NULL) THEN CONCAT(TABLE_RESULT.abName, TABLE_RESULT.leaveName) ELSE CONCAT(TABLE_RESULT.abName,',', TABLE_RESULT.leaveName) END")
			.append("	ELSE NULL")
			.append("	END TargetItem, ")
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.MEMO")
			.append("	ELSE NULL")
			.append("	END MEMO,")
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
			.append("		CASE TABLE_RESULT.GRANT_DATE WHEN 0 THEN ? WHEN 1 THEN  ? WHEN 2 THEN  ? END")
			.append("	ELSE NULL")
			.append("	END GRANT_DATE,")
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
			.append("		CASE TABLE_RESULT.TYPE_TIME WHEN 0 THEN ? WHEN 1 THEN ? END")
			.append("	ELSE NULL")
			.append("	END TYPE_TIME,")
			// TYPE_TIME là 0 「周期、日数を指定して付与する」 付与周期, 付与日数 is blank")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.TYPE_TIME = 0) THEN ")
			.append("			CASE WHEN TABLE_RESULT.[INTERVAL] IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.[INTERVAL],'年') END")
			.append("	ELSE NULL")
			.append("	END INTERVAL_VALUE, ")
			// TYPE_TIME là 0 「周期、日数を指定して付与する」 付与周期, 付与日数 is not blank")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.TYPE_TIME = 0) THEN ")
			.append("			CASE WHEN TABLE_RESULT.GRANTED_DAYS IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.GRANTED_DAYS,'日') END")
			.append("	ELSE NULL")
			.append("	END GRANTED_DAYS, ")
			// TYPE_TIME là 1「付与テーブルを参照して付与する」 table code is not blank	")
			.append("	CASE WHEN  (TABLE_RESULT.ROW_NUMBER2 = 1 AND TABLE_RESULT.TYPE_TIME = 1)  THEN TABLE_RESULT.GD_TBL_CD")
			.append("	ELSE NULL")
			.append("	END GD_TBL_CD, ")
			.append("	CASE WHEN  (TABLE_RESULT.ROW_NUMBER2 = 1 AND TABLE_RESULT.TYPE_TIME = 1) THEN TABLE_RESULT.GRANT_NAME")
			.append("	ELSE NULL")
			.append("	END GRANT_NAME,")
			.append("	CASE WHEN  (TABLE_RESULT.ROW_NUMBER2 = 1 AND TABLE_RESULT.TYPE_TIME = 1)  THEN ")
			.append("		CASE TABLE_RESULT.IS_SPECIFIED WHEN 0 THEN '-' WHEN 1 THEN '○' END")
			.append("	ELSE NULL")
			.append("	END IS_SPECIFIED,")
			.append("	CASE WHEN  (TABLE_RESULT.TYPE_TIME = 1AND TABLE_RESULT.[NO] IS NOT NULL) THEN TABLE_RESULT.ROW_NUMBER2")
			.append("	ELSE NULL")
			.append("	END TABLE_NO,")
			.append("	CASE WHEN  (TABLE_RESULT.TYPE_TIME = 1)  THEN ")
			.append("		CASE WHEN TABLE_RESULT.GRANTED_SMAL IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.GRANTED_SMAL,'日') END")
			.append("	ELSE NULL")
			.append("	END GRANTED_SMAL,")
			.append("	CASE WHEN  (TABLE_RESULT.TYPE_TIME = 1)  THEN ")
			.append("		CASE WHEN TABLE_RESULT.MONTHS IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.MONTHS,'ヶ月') END")
			.append("	ELSE NULL")
			.append("	END MONTHS,")
			.append("	CASE WHEN  (TABLE_RESULT.TYPE_TIME = 1)  THEN ")
			.append("		CASE WHEN TABLE_RESULT.YEARS IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.YEARS,'年') END")
			.append("	ELSE NULL")
			.append("	END YEARS,")
			.append("	CASE WHEN  (TABLE_RESULT.ROW_NUMBER2 = 1 AND TABLE_RESULT.TYPE_TIME = 1)  THEN ")
			.append("		CASE TABLE_RESULT.FIXED_ASSIGN WHEN 0 THEN '-' WHEN 1 THEN '○' END")
			.append("	ELSE NULL")
			.append("	END FIXED_ASSIGN,")
			.append("	CASE WHEN  (TABLE_RESULT.ROW_NUMBER2 = 1 AND TABLE_RESULT.TYPE_TIME = 1 AND TABLE_RESULT.FIXED_ASSIGN = 1)  THEN ")
			.append("		CASE WHEN TABLE_RESULT.NUMBER_OF_DAYS IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.NUMBER_OF_DAYS,'日') END")
			.append("	ELSE NULL")
			.append("	END NUMBER_OF_DAYS,")
			// Table code end")
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
			.append("		CASE TABLE_RESULT.TIME_CSL_METHOD WHEN 0 THEN ? WHEN 1 THEN ? WHEN 2 THEN ? WHEN 3 THEN ? END")
			.append("	ELSE NULL")
			.append("	END TIME_CSL_METHOD, ")
			// Time_cls_method is 0 「無期限」")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.TIME_CSL_METHOD = 0) THEN ")
			.append("		CASE WHEN TABLE_RESULT.LIMIT_CARRYOVER_DAYS IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.LIMIT_CARRYOVER_DAYS,'日') END")
			.append("	ELSE NULL")
			.append("	END LIMIT_CARRYOVER_DAYS, ")
			// Time_cls_method is 1 「有効期限を指定する」")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.TIME_CSL_METHOD = 1)  THEN ")
			.append("		CASE WHEN TABLE_RESULT.DEADLINE_YEARS IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.DEADLINE_YEARS,'年') END")
			.append("	ELSE NULL")
			.append("	END DEADLINE_YEARS, ")
			// Time_cls_method is 1 「有効期限を指定する」")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.TIME_CSL_METHOD = 1) THEN ")
			.append("		CASE WHEN TABLE_RESULT.DEADLINE_MONTHS IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.DEADLINE_MONTHS,'ヶ月') END")
			.append("	ELSE NULL")
			.append("	END DEADLINE_MONTHS, ")
			// Time_cls_method is 3 「使用可能期間を指定する」")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.TIME_CSL_METHOD = 3) THEN TABLE_RESULT.START_DATE")
			.append("	ELSE NULL")
			.append("	END START_DATE, ")
			// Time_cls_method is 3 「使用可能期間を指定する」")
			.append("	CASE WHEN  (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.TIME_CSL_METHOD = 3) THEN TABLE_RESULT.END_DATE")
			.append("	ELSE NULL")
			.append("	END END_DATE, ")
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
			.append("		CASE TABLE_RESULT.GENDER_REST WHEN 1 THEN '-' WHEN 0 THEN '○' END")
			.append("	ELSE NULL")
			.append("	END GENDER_REST, ")
			// 性別条件 is 0 Gender is blank")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.GENDER_REST = 0) THEN ")
			.append("		CASE TABLE_RESULT.GENDER WHEN 1 THEN ? WHEN 2 THEN ? END")
			.append("	ELSE NULL")
			.append("	END GENDER, ")
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
			.append("		CASE TABLE_RESULT.REST_EMP WHEN 1 THEN '-' WHEN 0 THEN '○' END")
			.append("	ELSE NULL")
			.append("	END REST_EMP, ")
			// -- 雇用条件 is 0 Employment list is blank")
			.append("	CASE WHEN  (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.REST_EMP = 0)  THEN TABLE_RESULT.empName")
			.append("	ELSE NULL")
			.append("	END empName,")
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
			.append("		CASE TABLE_RESULT.RESTRICTION_CLS WHEN 1 THEN '-' WHEN 0 THEN '○' END")
			.append("	ELSE NULL")
			.append("	END RESTRICTION_CLS, ")
			// 分類条件 is 0 Class list is blank")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.RESTRICTION_CLS = 0) THEN TABLE_RESULT.clsName")
			.append("	ELSE NULL")
			.append("	END clsName, ")
			.append("	CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
			.append("		CASE TABLE_RESULT.AGE_LIMIT WHEN 1 THEN '-' WHEN 0 THEN '○' END")
			.append("	ELSE NULL")
			.append("	END AGE_LIMIT, ")
			// Age_limit is 1 (利用しない) then blank")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.AGE_LIMIT = 0) THEN ")
			.append("		CASE WHEN TABLE_RESULT.AGE_LOWER_LIMIT IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.AGE_LOWER_LIMIT,'歳') END")
			.append("	ELSE NULL")
			.append("	END AGE_LOWER_LIMIT, ")
			// Age_limit is 1 (利用しない) then blank")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.AGE_LIMIT = 0) THEN ")
			.append("		CASE WHEN TABLE_RESULT.AGE_HIGHER_LIMIT IS NULL THEN NULL ELSE CONCAT(TABLE_RESULT.AGE_HIGHER_LIMIT,'歳') END")
			.append("	ELSE NULL")
			.append("	END AGE_HIGHER_LIMIT, ")
			// Age_limit is 1 (利用しない) then blank")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.AGE_LIMIT = 0) THEN ")
			.append("		CASE TABLE_RESULT.AGE_CRITERIA_CLS WHEN 0 THEN ? WHEN 1 THEN ? END")
			.append("	ELSE NULL")
			.append("	END AGE_CRITERIA_CLS, ")
			// Age_limit is 1 (利用しない) then blank")
			.append("	CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.AGE_LIMIT = 0) THEN TABLE_RESULT.AGE_BASE_DATE")
			.append("	ELSE NULL")
			.append("	END AGE_BASE_DATE,")
			.append("	TABLE_RESULT.ROW_INDEX")
			.append("	FROM")
			.append("		(Select sphd.SPHD_CD, sphd.SPHD_NAME, ")
			.append("			STUFF((SELECT ',' + mab1.NAME ")
			.append("					FROM KSHMT_HDSP_FRAME_ABSENCE sab1")
			.append("						LEFT JOIN KSHMT_ABSENCE_FRAME mab1 ON sab1.ABS_FRAME_NO = mab1.ABSENCE_FRAME_NO ")
			.append("						AND sab1.CID = mab1.CID AND mab1.ABOLISH_ATR = 0")
			.append("					WHERE sab1.SPHD_CD = sab.SPHD_CD  AND sab1.CID = sab.CID")
			.append("					ORDER BY mab1.ABSENCE_FRAME_NO")
			.append("				FOR XML PATH ('')), 1, 1, '') as abName,")
			.append("			STUFF((SELECT ',' + mspl1.NAME")
			.append("					FROM KSHMT_HDSP_FRAME_HDSP spl1")
			.append("						LEFT JOIN KSHMT_HDSP_FRAME mspl1 ON spl1.SPHD_NO = mspl1.SPHD_FRAME_NO ")
			.append("						AND mspl1.CID = spl1.CID AND mspl1.ABOLISH_ATR = 0")
			.append("					WHERE spl1.SPHD_CD = spl.SPHD_CD  AND spl1.CID = spl.CID")
			.append("					ORDER BY mspl1.SPHD_FRAME_NO")
			.append("				FOR XML PATH ('')), 1, 1, '') as leaveName,")
			.append("			sphd.MEMO,")
			.append("			gra.GRANT_DATE, gra.TYPE_TIME, gra.[INTERVAL], gra.GRANTED_DAYS, ")
			.append("			graDa.GD_TBL_CD, graDa.GRANT_NAME, graDa.IS_SPECIFIED, graDa.FIXED_ASSIGN, graDa.NUMBER_OF_DAYS, ely.[NO], ely.GRANTED_DAYS as GRANTED_SMAL, ely.MONTHS,")
			.append("			ely.YEARS, graPe.TIME_CSL_METHOD,graPe.LIMIT_CARRYOVER_DAYS, graPe.DEADLINE_YEARS, graPe.DEADLINE_MONTHS,")
			.append("			graPe.START_DATE, graPe.END_DATE,")
			.append("			leaRe.GENDER_REST, leaRe.GENDER, leaRe.RESTRICTION_CLS, ")
			.append("			STUFF((SELECT ',' + CONCAT(cls1.CLS_CD,IIF(mcls1.CLSNAME IS NULL, ? ,mcls1.CLSNAME))")
			.append("					FROM KSHMT_HDSP_COND_CLS cls1")
			.append("						LEFT JOIN BSYMT_CLASSIFICATION mcls1 ON mcls1.CID = cls1.CID AND mcls1.CLSCD = cls1.CLS_CD")
			.append("					WHERE cls1.SPHD_CD = cls.SPHD_CD  AND cls1.CID = cls.CID")
			.append("					ORDER BY cls1.CLS_CD")
			.append("				FOR XML PATH ('')), 1, 1, '') as clsName, ")
			.append("			leaRe.REST_EMP, ")
			.append("			STUFF((SELECT ',' + CONCAT(emp1.EMP_CD,IIF(memp1.NAME IS NULL, ?, memp1.NAME))")
			.append("					FROM KSHMT_HDSP_COND_EMP emp1 ")
			.append("						LEFT JOIN BSYMT_EMPLOYMENT memp1 ON memp1.CID = emp1.CID AND memp1.CODE = emp1.EMP_CD")
			.append("					WHERE emp1.SPHD_CD = emp.SPHD_CD  AND emp1.CID = emp.CID")
			.append("					ORDER BY emp1.EMP_CD")
			.append("				FOR XML PATH ('')), 1, 1, '') as empName,")
			.append("			leaRe.AGE_LIMIT, leaRe.AGE_LOWER_LIMIT, leaRe.AGE_HIGHER_LIMIT, leaRe.AGE_CRITERIA_CLS, leaRe.AGE_BASE_DATE,")
			.append("			ROW_NUMBER() OVER (PARTITION BY sphd.SPHD_CD ORDER BY sphd.SPHD_CD,graDa.GD_TBL_CD, ely.[NO]) AS ROW_NUMBER,")
			.append("			ROW_NUMBER() OVER (PARTITION BY sphd.SPHD_CD, graDa.GD_TBL_CD ORDER BY sphd.SPHD_CD,graDa.GD_TBL_CD, ely.[NO]) AS ROW_NUMBER2,")
			.append("			ROW_NUMBER() OVER (ORDER BY  sphd.SPHD_CD,graDa.GD_TBL_CD, ely.[NO]) AS ROW_INDEX")
			.append("			FROM KSHMT_HDSP sphd ")
			.append("				LEFT JOIN KSHMT_HDSP_FRAME_ABSENCE sab ON sphd.SPHD_CD = sab.SPHD_CD AND sphd.CID = sab.CID")
			.append("				LEFT JOIN KSHMT_ABSENCE_FRAME mab ON sab.ABS_FRAME_NO = mab.ABSENCE_FRAME_NO AND sab.CID = mab.CID AND mab.ABOLISH_ATR = 0")
			.append("				LEFT JOIN KSHMT_HDSP_FRAME_HDSP spl ON sphd.SPHD_CD = spl.SPHD_CD AND sphd.CID = spl.CID ")
			.append("				LEFT JOIN KSHMT_HDSP_FRAME mspl ON spl.SPHD_NO = mspl.SPHD_FRAME_NO AND mspl.CID = spl.CID AND mspl.ABOLISH_ATR = 0")
			.append("				INNER JOIN KSHMT_HDSP_GRANT gra ON gra.CID = sphd.CID AND gra.SPHD_CD = sphd.SPHD_CD")
			.append("				INNER JOIN KSHMT_HDSP_PERIOD graPe ON graPe.CID = sphd.CID AND graPe.SPHD_CD = sphd.SPHD_CD")
			.append("				INNER JOIN KSHMT_HDSP_CONDITION leaRe ON leaRe.CID = sphd.CID AND leaRe.SPHD_CD = sphd.SPHD_CD")
			.append("				LEFT JOIN KSHMT_HDSP_COND_CLS cls ON cls.CID = sphd.CID AND cls.SPHD_CD = sphd.SPHD_CD")
			.append("				LEFT JOIN BSYMT_CLASSIFICATION mcls ON mcls.CID = cls.CID AND mcls.CLSCD = cls.CLS_CD")
			.append("				LEFT JOIN KSHMT_HDSP_COND_EMP emp ON emp.CID = sphd.CID AND emp.SPHD_CD = sphd.SPHD_CD")
			.append("				LEFT JOIN BSYMT_EMPLOYMENT memp ON memp.CID = emp.CID AND memp.CODE = emp.EMP_CD")
			.append("				LEFT JOIN KSHMT_HDSP_GRANT_TBL graDa ON graDa.CID = sphd.CID AND graDa.SPHD_CD = sphd.SPHD_CD")
			.append("				LEFT JOIN KSHMT_HDSP_ELAPSE_YEARS ely ON ely.CID = graDa.CID AND ely.SPHD_CD = graDa.SPHD_CD AND ely.GD_CD = graDa.GD_TBL_CD")
			.append("			WHERE sphd.CID = ?")
			.append("			GROUP BY sphd.SPHD_CD, sphd.SPHD_NAME, sphd.MEMO, gra.GRANTED_DAYS,  gra.GRANT_DATE, gra.TYPE_TIME,gra.[INTERVAL], ")
			.append("				graPe.TIME_CSL_METHOD, leaRe.AGE_LOWER_LIMIT,graDa.GD_TBL_CD, graDa.GRANT_NAME, graDa.IS_SPECIFIED,graDa.FIXED_ASSIGN, graDa.NUMBER_OF_DAYS,")
			.append("	 			ely.[NO], ely.GRANTED_DAYS, ely.MONTHS, ely.YEARS,graPe.LIMIT_CARRYOVER_DAYS, graPe.DEADLINE_YEARS, graPe.DEADLINE_MONTHS, ")
			.append("				graPe.START_DATE, graPe.END_DATE,leaRe.GENDER_REST, leaRe.GENDER, leaRe.RESTRICTION_CLS, leaRe.REST_EMP,leaRe.AGE_LIMIT, leaRe.AGE_LOWER_LIMIT, ")
			.append("				leaRe.AGE_HIGHER_LIMIT, leaRe.AGE_CRITERIA_CLS, leaRe.AGE_BASE_DATE,sab.CID, sab.SPHD_CD,spl.CID, ")
			.append("				spl.SPHD_CD,cls.CID, cls.SPHD_CD,emp.CID, emp.SPHD_CD) TABLE_RESULT) R")
			.append(" WHERE SPHD_CD IS NOT NULL OR")
			.append("		GD_TBL_CD IS NOT NULL OR")
			.append("		TABLE_NO IS NOT NULL ")
			.append("ORDER BY ROW_INDEX");
	
		GET_SPECIAL_HOLIDAY_DATA = specialHDData.toString();
		
		specialHDData.setLength(0);
		specialHDData.append("SELECT * FROM ")
		.append(" (SELECT ")
		.append("		CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.S_HOLIDAY_EVENT_NO")
		.append("		ELSE NULL")
		.append("		END S_HOLIDAY_EVENT_NO,")
		.append("		CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.NAME")
		.append("		ELSE NULL")
		.append("		END HOLIDAY_NAME, ")
		.append("		CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.MEMO")
		.append("		ELSE NULL")
		.append("		END MEMO, ")
		.append("		CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
		// Param 1, param 2
		.append("			CASE TABLE_RESULT.MAX_NUMBER_DAYS_TYPE WHEN 1 THEN  ? WHEN 2 THEN  ? END")
		.append("		ELSE NULL")
		.append("		END MAX_NUMBER_DAYS_TYPE,")
		// 上限日数の設定方法 = 1  固定日数を上限とする
		.append("		CASE WHEN (TABLE_RESULT.ROW_NUMBER = 1 AND TABLE_RESULT.MAX_NUMBER_DAYS_TYPE = 1) THEN ")
		.append("			CASE WHEN (TABLE_RESULT.FIXED_DAY_GRANT IS NULL) THEN NULL ELSE CONCAT(TABLE_RESULT.FIXED_DAY_GRANT,'日') END")
		.append("		ELSE NULL")
		.append("		END FIXED_DAY_GRANT,")
		.append("		CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
		.append("			CASE TABLE_RESULT.MAKE_INVITATION_ATR WHEN 0 THEN '-' WHEN 1 THEN '○' END")
		.append("		ELSE NULL")
		.append("		END MAKE_INVITATION_ATR,")
		.append("		CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN ")
		.append("			CASE TABLE_RESULT.INCLUDE_HOLIDAYS_ATR WHEN 0 THEN '-' WHEN 1 THEN '○' END")
		.append("		ELSE NULL")
		.append("		END INCLUDE_HOLIDAYS_ATR,")
		// 上限日数の設定方法 = 2 続柄ごとに上限を設定する
		.append("		CASE WHEN (TABLE_RESULT.MAX_NUMBER_DAYS_TYPE = 2) THEN TABLE_RESULT.RELATIONSHIP_CD")
		.append("		ELSE NULL")
		.append("		END RELATIONSHIP_CD,")
		.append("		CASE WHEN (TABLE_RESULT.MAX_NUMBER_DAYS_TYPE = 2) THEN TABLE_RESULT.RELATIONSHIP_NAME")
		.append("		ELSE NULL")
		.append("		END RELATIONSHIP_NAME,")
		.append("		CASE WHEN (TABLE_RESULT.MAX_NUMBER_DAYS_TYPE = 2) THEN ")
		.append("			CASE TABLE_RESULT.THREE_PARENT_OR_LESS WHEN 0 THEN '-' WHEN 1 THEN '○' END")
		.append("		ELSE NULL")
		.append("		END THREE_PARENT_OR_LESS,")
		.append("		CASE WHEN (TABLE_RESULT.MAX_NUMBER_DAYS_TYPE = 2) THEN ")
		.append("			CASE WHEN (TABLE_RESULT.GRANTED_DAY IS NULL) THEN NULL ELSE CONCAT(TABLE_RESULT.GRANTED_DAY,'日') END")
		.append("		ELSE NULL")
		.append("		END GRANTED_DAY,")
		.append("		CASE WHEN (TABLE_RESULT.MAX_NUMBER_DAYS_TYPE = 2) THEN ")
		.append("			CASE WHEN (TABLE_RESULT.MORNING_HOUR IS NULL OR TABLE_RESULT.MAKE_INVITATION_ATR = 0) THEN NULL ELSE CONCAT(TABLE_RESULT.MORNING_HOUR,'日') END")
		.append("		ELSE NULL")
		.append("		END MORNING_HOUR, ")
		.append("		TABLE_RESULT.ROW_INDEX ")
		.append("	FROM	")
		.append(" 		(SELECT hdframe.NAME, holidayevent.S_HOLIDAY_EVENT_NO, holidayevent.MEMO,holidayevent.MAX_NUMBER_DAYS_TYPE, holidayevent.FIXED_DAY_GRANT,")
		.append("				holidayevent.MAKE_INVITATION_ATR, holidayevent.INCLUDE_HOLIDAYS_ATR,  grantdayrelp.RELATIONSHIP_CD, ")
		.append("				rel.RELATIONSHIP_NAME, rel.THREE_PARENT_OR_LESS, grantdayrelp.GRANTED_DAY, grantdayrelp.MORNING_HOUR, ")
		.append("				ROW_NUMBER() OVER (PARTITION BY hdframe.SPHD_FRAME_NO ORDER BY hdframe.SPHD_FRAME_NO, grantdayrelp.RELATIONSHIP_CD) AS ROW_NUMBER,")
		.append("				ROW_NUMBER() OVER (ORDER BY hdframe.SPHD_FRAME_NO, grantdayrelp.RELATIONSHIP_CD) AS ROW_INDEX")
		.append(" 		FROM KSHMT_HDSPEV holidayevent")
		.append("			LEFT JOIN KSHMT_HDSP_FRAME hdframe ON holidayevent.S_HOLIDAY_EVENT_NO = hdframe.SPHD_FRAME_NO AND holidayevent.CID = hdframe.CID")
		.append("			LEFT JOIN KSHMT_HDSPEV_GRANT_LIMIT grantdayperrelp ON holidayevent.S_HOLIDAY_EVENT_NO = grantdayperrelp.S_HOLIDAY_EVENT_NO AND holidayevent.CID = grantdayperrelp.CID")
		.append("			LEFT JOIN KSHMT_HD_SPEV_LIMIT grantdayrelp ON holidayevent.S_HOLIDAY_EVENT_NO = grantdayrelp.S_HOLIDAY_EVENT_NO AND holidayevent.CID = grantdayrelp.CID")
		.append("			LEFT JOIN KSHMT_RELATIONSHIP rel ON grantdayrelp.RELATIONSHIP_CD = rel.RELATIONSHIP_CD AND holidayevent.CID = rel.CID")
		// Param 3
		.append(" 		WHERE holidayevent.CID = ?) TABLE_RESULT) R")
		.append(" WHERE HOLIDAY_NAME IS NOT NULL OR RELATIONSHIP_CD IS NOT NULL")
		.append(" ORDER BY ROW_INDEX ");
		GET_SPECIAL_HOLIDAY_EVENT = specialHDData.toString();
	}
	
	@Override
	public List<MasterData> getSPHDExportData(String cid) {
		List<MasterData> result = new ArrayList<>();
		try (PreparedStatement stmt = this.connection()
				.prepareStatement(GET_SPECIAL_HOLIDAY_DATA)){
			stmt.setString(1,TextResource.localize("KMF004_15"));
			stmt.setString(2, TextResource.localize("KMF004_16"));
			stmt.setString(3, TextResource.localize("KMF004_17"));
			stmt.setString(4, TextResource.localize("KMF004_19"));
			stmt.setString(5, TextResource.localize("KMF004_20"));
			stmt.setString(6, TextResource.localize("KMF004_28"));
			stmt.setString(7, TextResource.localize("KMF004_29"));
			stmt.setString(8, TextResource.localize("KMF004_30"));
			stmt.setString(9, TextResource.localize("KMF004_31"));
			stmt.setString(10, TextResource.localize("KMF004_55"));
			stmt.setString(11, TextResource.localize("KMF004_56"));
			stmt.setString(12, TextResource.localize("Enum_ReferenceYear_THIS_YEAR"));
			stmt.setString(13, TextResource.localize("Enum_AgeBaseYearAtr_THIS_MONTH"));
			stmt.setString(14, TextResource.localize("Enum_MasterUnregistered"));
			stmt.setString(15, TextResource.localize("Enum_MasterUnregistered"));
			// new Items('0', nts.uk.resource.getText('Enum_ReferenceYear_THIS_YEAR')),
            // new Items('1', nts.uk.resource.getText('Enum_AgeBaseYearAtr_THIS_MONTH'))
			stmt.setString(16, cid);
			result.addAll(new NtsResultSet(stmt.executeQuery()).getList(i -> toMasterList(i)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private MasterData toMasterList(NtsResultRecord r){
		
		Map<String, MasterCellData> data = new HashMap<>();
		Map<String,String> deadLineStartDate = SpecialHolidayUtils.convertMonthDay(r.getString("START_DATE"));
		Map<String,String> deadLineEndDate = SpecialHolidayUtils.convertMonthDay(r.getString("END_DATE"));
		Map<String,String> ageBaseDate = SpecialHolidayUtils.convertMonthDay(r.getString("AGE_BASE_DATE"));
		
		data.put(SpecialHolidayUtils.KMF004_106, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_106)
					.value(r.getString("SPHD_CD"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_107,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_107)
					.value(r.getString("SPHD_NAME"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_108,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_108)
					.value(r.getString("TargetItem"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_109,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_109)
					.value(r.getString("MEMO"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_110,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_110)
					.value(r.getString("GRANT_DATE"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_111,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_111)
					.value(r.getString("TYPE_TIME"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_112,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_112)
					.value(r.getString("INTERVAL_VALUE"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_113,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_113)
					.value(r.getString("GRANTED_DAYS"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_114,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_114)
					.value(r.getString("GD_TBL_CD"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_115,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_115)
					.value(r.getString("GRANT_NAME"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_116,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_116)
					.value(r.getString("IS_SPECIFIED"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_117,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_117)
					.value(r.getString("TABLE_NO"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_118,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_118)
					.value(r.getString("YEARS"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_119,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_119)
					.value(r.getString("MONTHS"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_120,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_120)
					.value(r.getString("GRANTED_SMAL"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_121,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_121)
					.value(r.getString("FIXED_ASSIGN"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_122,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_122)
					.value(r.getString("NUMBER_OF_DAYS"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_123,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_123)
					.value(r.getString("TIME_CSL_METHOD"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_124,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_124)
					.value(r.getString("LIMIT_CARRYOVER_DAYS"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_125,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_125)
					.value(r.getString("DEADLINE_YEARS"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_126,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_126)
					.value(r.getString("DEADLINE_MONTHS"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_127,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_127)
					.value(deadLineStartDate.get(SpecialHolidayUtils.MONTH))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_128,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_128)
					.value(deadLineStartDate.get(SpecialHolidayUtils.DAY))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_129,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_129)
					.value(deadLineEndDate.get(SpecialHolidayUtils.MONTH))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_130,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_130)
					.value(deadLineEndDate.get(SpecialHolidayUtils.DAY))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_131,  MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_131)
					.value(r.getString("GENDER_REST"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_132, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_132)
					.value(r.getString("GENDER"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_133, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_133)
					.value(r.getString("REST_EMP"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_134, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_134)
					.value(r.getString("empName"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_135, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_135)
					.value(r.getString("RESTRICTION_CLS"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_136, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_136)
					.value(r.getString("clsName"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_137, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_137)
					.value(r.getString("AGE_LIMIT"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_138, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_138)
					.value(r.getString("AGE_LOWER_LIMIT"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_139, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_139)
					.value(r.getString("AGE_HIGHER_LIMIT"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_140, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_140)
					.value(r.getString("AGE_CRITERIA_CLS"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_141, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_141)
					.value(ageBaseDate.get(SpecialHolidayUtils.MONTH))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		data.put(SpecialHolidayUtils.KMF004_142, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_142)
					.value(ageBaseDate.get(SpecialHolidayUtils.DAY))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		return MasterData.builder().rowData(data).build();
	}

	@Override
	public List<MasterData> getSPHDEventExportData(String cid) {
		
		List<MasterData> result = new ArrayList<>();
		
		try (PreparedStatement stmt = this.connection().prepareStatement(GET_SPECIAL_HOLIDAY_EVENT)){
			stmt.setString(1, TextResource.localize("KMF004_76"));
			stmt.setString(2, TextResource.localize("KMF004_77"));
			stmt.setString(3, cid);
			result.addAll(new NtsResultSet(stmt.executeQuery()).getList(i->toSPHDEventData(i)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private MasterData toSPHDEventData(NtsResultRecord r){
		Map<String,MasterCellData> data = new HashMap<>();
		 data.put(SpecialHolidayUtils.KMF004_143, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_143)
					.value(r.getString("S_HOLIDAY_EVENT_NO"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		 data.put(SpecialHolidayUtils.KMF004_144, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_144)
					.value(r.getString("HOLIDAY_NAME"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
		 data.put(SpecialHolidayUtils.KMF004_161, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_161)
					.value(r.getString("MEMO"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        data.put(SpecialHolidayUtils.KMF004_145, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_145)
					.value(r.getString("MAX_NUMBER_DAYS_TYPE"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        data.put(SpecialHolidayUtils.KMF004_146, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_146)
					.value(r.getString("FIXED_DAY_GRANT"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        data.put(SpecialHolidayUtils.KMF004_147, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_147)
					.value(r.getString("MAKE_INVITATION_ATR"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        data.put(SpecialHolidayUtils.KMF004_148, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_148)
					.value(r.getString("INCLUDE_HOLIDAYS_ATR"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        data.put(SpecialHolidayUtils.KMF004_149, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_149)
					.value(r.getString("RELATIONSHIP_CD"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        data.put(SpecialHolidayUtils.KMF004_150, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_150)
					.value(r.getString("RELATIONSHIP_NAME"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        data.put(SpecialHolidayUtils.KMF004_162, MasterCellData.builder()
				.columnId(SpecialHolidayUtils.KMF004_162)
				.value(r.getString("THREE_PARENT_OR_LESS"))
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
				.build());
        data.put(SpecialHolidayUtils.KMF004_151, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_151)
					.value(r.getString("GRANTED_DAY"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        data.put(SpecialHolidayUtils.KMF004_152, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_152)
					.value(r.getString("MORNING_HOUR"))
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
		return MasterData.builder().rowData(data).build();
	}

}
