package nts.uk.file.at.infra.otpitem;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountUnit;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRounding;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberUnit;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.file.at.app.export.otpitem.CalFormulasItemColumn;
import nts.uk.file.at.app.export.otpitem.CalFormulasItemRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class CalFormulasItemImpl implements CalFormulasItemRepository {
	@PersistenceContext
	private EntityManager entityManager;

	private static final String GET_EXPORT_EXCEL_ONE = "SELECT"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.OPTIONAL_ITEM_NO, NULL) AS OPTIONAL_ITEM_NO,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.OPTIONAL_ITEM_NAME, NULL) AS OPTIONAL_ITEM_NAME,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.OPTIONAL_ITEM_ATR, NULL) AS OPTIONAL_ITEM_ATR,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.UNIT_OF_OPTIONAL_ITEM, NULL) AS UNIT_OF_OPTIONAL_ITEM,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.USAGE_ATR, NULL) AS USAGE_ATR,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.PERFORMANCE_ATR, NULL) AS PERFORMANCE_ATR,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.EMP_CONDITION_ATR, NULL) AS EMP_CONDITION_ATR,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.NAME, NULL) AS NAME,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.UPPER_LIMIT_ATR, NULL) AS UPPER_LIMIT_ATR,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.UPPER_RANGE, NULL) AS UPPER_RANGE,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.LOWER_LIMIT_ATR, NULL) AS LOWER_LIMIT_ATR,"
+"			IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.LOWER_RANGE, NULL) AS LOWER_RANGE			"
+"			,RESULT_FINAL.SYMBOL"
+"			,RESULT_FINAL.FORMULA_ATR"
+"			,RESULT_FINAL.FORMULA_NAME"
+"			,RESULT_FINAL.CALC_ATR"
+"			,IIF(RESULT_FINAL.PERFORMANCE_ATR = 1, RESULT_FINAL.DAY_ROUNDING_UNIT, NULL) DAY_ROUNDING_UNIT"
+"			,IIF(RESULT_FINAL.PERFORMANCE_ATR = 1, RESULT_FINAL.DAY_ROUNDING, NULL) DAY_ROUNDING"
+"			,RESULT_FINAL.MON_ROUNDING_UNIT"
+"			,RESULT_FINAL.MON_ROUNDING"
+"			,RESULT_FINAL.CALC_ATR_2"
+"			,RESULT_FINAL.ATTENDANCE_ITEM_NAME"
+"			,RESULT_FINAL.ATTENDANCE_ITEM_2"
+"		FROM"
+"			(SELECT RESULT_TOTAL.DISPORDER"
+"						,RESULT_TOTAL.OPTIONAL_ITEM_NO"
+"						, RESULT_TOTAL.OPTIONAL_ITEM_NAME"
+"						,RESULT_TOTAL.OPTIONAL_ITEM_ATR"
+"						,RESULT_TOTAL.UNIT_OF_OPTIONAL_ITEM"
+"						,RESULT_TOTAL.USAGE_ATR"
+"						,RESULT_TOTAL.PERFORMANCE_ATR"
+"						,RESULT_TOTAL.EMP_CONDITION_ATR"
+"						,RESULT_TOTAL.NAME"
+"						,RESULT_TOTAL.UPPER_LIMIT_ATR"
+"						,RESULT_TOTAL.UPPER_RANGE"
+"						,RESULT_TOTAL.LOWER_LIMIT_ATR"
+"						,RESULT_TOTAL.LOWER_RANGE"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.SYMBOL, NULL) AS SYMBOL"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.FORMULA_ATR, NULL) AS FORMULA_ATR"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.FORMULA_NAME, NULL) AS FORMULA_NAME"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.CALC_ATR, NULL) AS CALC_ATR"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.DAY_ROUNDING_UNIT, NULL) AS DAY_ROUNDING_UNIT"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.DAY_ROUNDING, NULL) AS DAY_ROUNDING"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.MON_ROUNDING_UNIT, NULL) AS MON_ROUNDING_UNIT"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.MON_ROUNDING, NULL) AS MON_ROUNDING"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.CALC_ATR_2, NULL) AS CALC_ATR_2"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.ATTENDANCE_ITEM_NAME, NULL) AS ATTENDANCE_ITEM_NAME"
+"						,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.ATTENDANCE_ITEM_2, NULL) AS ATTENDANCE_ITEM_2"
+"						, ROW_NUMBER() OVER (PARTITION BY RESULT_TOTAL.OPTIONAL_ITEM_NO ORDER BY RESULT_TOTAL.DISPORDER ASC, RESULT_TOTAL.OPTIONAL_ITEM_NO ASC) AS ROW_NUMBER"
+"			FROM"
+"			(SELECT RESULT_LEFT.OPTIONAL_ITEM_NO"
+"				,RESULT_LEFT.OPTIONAL_ITEM_NAME"
+"				,RESULT_LEFT.OPTIONAL_ITEM_ATR"
+"				,RESULT_LEFT.UNIT_OF_OPTIONAL_ITEM"
+"				,RESULT_LEFT.USAGE_ATR"
+"				,RESULT_LEFT.PERFORMANCE_ATR"
+"				,RESULT_LEFT.EMP_CONDITION_ATR"
+"				,RESULT_LEFT.NAME"
+"				,RESULT_RIGHT.UPPER_LIMIT_ATR"
+"				,RESULT_RIGHT.UPPER_RANGE"
+"				,RESULT_RIGHT.LOWER_LIMIT_ATR"
+"				,RESULT_RIGHT.LOWER_RANGE"
+"				,RESULT_RIGHT.SYMBOL"
+"				,RESULT_RIGHT.FORMULA_ATR"
+"				,RESULT_RIGHT.FORMULA_NAME"
+"				,RESULT_RIGHT.CALC_ATR"
+"				,RESULT_RIGHT.DAY_ROUNDING_UNIT"
+"				,RESULT_RIGHT.DAY_ROUNDING"
+"				,RESULT_RIGHT.MON_ROUNDING_UNIT"
+"				,RESULT_RIGHT.MON_ROUNDING"
+"				,RESULT_RIGHT.CALC_ATR_2"
+"				,RESULT_RIGHT.ATTENDANCE_ITEM_NAME"
+"				,RESULT_RIGHT.ATTENDANCE_ITEM_2"
+"				,RESULT_RIGHT.DISPORDER"
+"			FROM"
+"				(SELECT"
+"				RESULT_ONE.OPTIONAL_ITEM_NO"
+"				,RESULT_ONE.OPTIONAL_ITEM_NAME"
+"				,RESULT_ONE.OPTIONAL_ITEM_ATR"
+"				,RESULT_ONE.UNIT_OF_OPTIONAL_ITEM"
+"				,RESULT_ONE.USAGE_ATR"
+"				,RESULT_ONE.PERFORMANCE_ATR"
+"				,RESULT_ONE.EMP_CONDITION_ATR"
+"				,RESULT_ONE.NAME"
+"					FROM (SELECT"
+"					 oi.OPTIONAL_ITEM_NO"
+"					,oi.OPTIONAL_ITEM_NAME"
+"					,oi.OPTIONAL_ITEM_ATR"
+"					,oi.UNIT_OF_OPTIONAL_ITEM"
+"					,oi.USAGE_ATR"
+"					,oi.PERFORMANCE_ATR"
+"					,oi.EMP_CONDITION_ATR"
+"					,IIF(oi.EMP_CONDITION_ATR = 1,STUFF(("
+"		 			SELECT"
+"					IIF(oi.EMP_CONDITION_ATR = 1,',' + ec1.EMP_CD + (CASE WHEN emp1.NAME IS NULL THEN 'マスタ未登録' ELSE  emp1.NAME END), NULL)"
+"		 			FROM"
+"				KRCST_APPL_EMP_CON ec1"
+"				LEFT JOIN BSYMT_EMPLOYMENT emp1 ON ec1.CID = emp1.CID"
+"				AND ec1.EMP_CD = emp1.CODE"
+"			WHERE"
+"				oi.CID = ec1.CID AND ec1.EMP_APPL_ATR = 1 AND oi.OPTIONAL_ITEM_NO = ec1.OPTIONAL_ITEM_NO ORDER BY ec1.EMP_CD"
+"		 			 FOR XML PATH ('')"
+"		 				),"
+"		 			1,"
+"		 			1,"
+"		 			''"
+"		 		), NULL) AS NAME"
+"				FROM"
+"				KRCST_OPTIONAL_ITEM oi"
+"			WHERE"
+"				oi.CID = ?companyId"
+"		) AS RESULT_ONE"
+"		GROUP BY"
+"			 RESULT_ONE.OPTIONAL_ITEM_NO"
+"			,RESULT_ONE.OPTIONAL_ITEM_NAME"
+"			,RESULT_ONE.OPTIONAL_ITEM_ATR"
+"			,RESULT_ONE.UNIT_OF_OPTIONAL_ITEM"
+"			,RESULT_ONE.USAGE_ATR"
+"			,RESULT_ONE.PERFORMANCE_ATR"
+"			,RESULT_ONE.EMP_CONDITION_ATR"
+"			,RESULT_ONE.NAME"
+"		) RESULT_LEFT"
+"		LEFT JOIN"
+"		(SELECT * FROM"
+"		("
+"			SELECT"
+"			oi.OPTIONAL_ITEM_NO"
+"			,crr.UPPER_LIMIT_ATR"
+"			,(CASE oi.OPTIONAL_ITEM_ATR"
+"		 		WHEN 0 THEN crr.UPPER_TIME_RANGE"
+"		 		WHEN 1 THEN crr.UPPER_NUMBER_RANGE"
+"		 		WHEN 2 THEN crr.UPPER_AMOUNT_RANGE"
+"		 	END) AS UPPER_RANGE"
+"		 	,crr.LOWER_LIMIT_ATR"
+"		 	,(CASE oi.OPTIONAL_ITEM_ATR"
+"		 		WHEN 0 THEN crr.LOWER_TIME_RANGE"
+"		 		WHEN 1 THEN crr.LOWER_NUMBER_RANGE"
+"		 		WHEN 2 THEN crr.LOWER_AMOUNT_RANGE"
+"		 	 END) AS LOWER_RANGE"
+"		  ,oif.SYMBOL"
+"			,oif.FORMULA_ATR"
+"			,oif.FORMULA_NAME"
+"			,oif.CALC_ATR"
+"		   ,(CASE oi.OPTIONAL_ITEM_ATR"
+"		 		WHEN 0 THEN fr_day.TIME_ROUNDING_UNIT"
+"		 		WHEN 1 THEN fr_day.NUMBER_ROUNDING_UNIT"
+"		 		WHEN 2 THEN fr_day.AMOUNT_ROUNDING_UNIT"
+"		 	END) AS DAY_ROUNDING_UNIT"
+"		 	,(CASE oi.OPTIONAL_ITEM_ATR"
+"		 		WHEN 0 THEN fr_day.TIME_ROUNDING"
+"		 		WHEN 1 THEN fr_day.NUMBER_ROUNDING"
+"		 		WHEN 2 THEN fr_day.AMOUNT_ROUNDING"
+"		 	 END) AS DAY_ROUNDING"
+"		 	"
+"		 	,(CASE oi.OPTIONAL_ITEM_ATR"
+"		 		WHEN 0 THEN fr_month.TIME_ROUNDING_UNIT"
+"		 		WHEN 1 THEN fr_month.NUMBER_ROUNDING_UNIT"
+"		 		WHEN 2 THEN fr_month.AMOUNT_ROUNDING_UNIT"
+"		 	END) AS MON_ROUNDING_UNIT"
+"		 	,(CASE oi.OPTIONAL_ITEM_ATR"
+"		 		WHEN 0 THEN fr_month.TIME_ROUNDING"
+"		 		WHEN 1 THEN fr_month.NUMBER_ROUNDING"
+"		 		WHEN 2 THEN fr_month.AMOUNT_ROUNDING"
+"		 	END) AS MON_ROUNDING"
+"			,oif.CALC_ATR AS CALC_ATR_2"
+"			,fd.DISPORDER"
+"		, IIF(oif.CALC_ATR = 0 , IIF(oi.PERFORMANCE_ATR = 1, (STUFF(("
+"		 			SELECT "
+"		 				 CASE cis.OPERATOR  WHEN 0 THEN '+'	WHEN 1 THEN '-'	WHEN 2 THEN '*'	WHEN 3 THEN '/' END "
+"						 + ("
+"											SELECT CASE B.FRAME_CATEGORY "
+" WHEN 0 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name1.OT_FR_NAME FROM KSHST_OVERTIME_FRAME name1 WHERE cis.CID = name1.CID AND  name1.OT_FR_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2 )))	"
+"						WHEN 1 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name2.OT_FR_NAME FROM KSHST_OVERTIME_FRAME name2 WHERE cis.CID = name2.CID AND  name2.OT_FR_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2 )))			"
+"						WHEN 2 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name3.WDO_FR_NAME FROM KSHST_WORKDAYOFF_FRAME name3 WHERE cis.CID = name3.CID AND  name3.WDO_FR_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2 )))"
+"						WHEN 3 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name4.WDO_FR_NAME FROM KSHST_WORKDAYOFF_FRAME name4 WHERE cis.CID = name4.CID AND  name4.WDO_FR_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2 )))"
+"						WHEN 4 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name5.PREMIUM_NAME FROM KMNMT_PREMIUM_ITEM name5 WHERE cis.CID = name5.CID AND  name5.PREMIUM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)))"
+"						WHEN 5 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name6.TIME_ITEM_NAME FROM KBPST_BP_TIME_ITEM name6 WHERE cis.CID = name6.CID AND  name6.TIME_ITEM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)))"
+"						WHEN 6 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name7.TIME_ITEM_NAME FROM KBPST_BP_TIME_ITEM name7 WHERE cis.CID = name7.CID AND  name7.TIME_ITEM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)))"
+"						WHEN 7 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name8.DIVERGENCETIME_NAME FROM KMKMT_DIVERGENCE_TIME name8 WHERE cis.CID = name8.CID AND  name8.DIVERGENCETIME_ID = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)))"
+"						WHEN 8 THEN (REPLACE(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name9.OPTIONAL_ITEM_NAME FROM KRCST_OPTIONAL_ITEM name9 WHERE cis.CID = name9.CID AND  name9.OPTIONAL_ITEM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)), '{1}', (SELECT name9.UNIT_OF_OPTIONAL_ITEM FROM KRCST_OPTIONAL_ITEM name9 WHERE cis.CID = name9.CID AND  name9.OPTIONAL_ITEM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+"						WHEN 10 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name11.NAME FROM KSMST_SPECIFIC_DATE_ITEM name11 WHERE cis.CID = name11.CID AND  name11.SPECIFIC_DATE_ITEM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)))"
+"						WHEN 11 THEN (REPLACE(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name12.NAME FROM KSHST_OVER_TIME name12 WHERE cis.CID = name12.CID AND  name12.OVER_TIME_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)), '{1}', (SELECT ooba.NAME FROM KSHST_OUTSIDE_OT_BRD ooba WHERE ooba.BRD_ITEM_NO = B.PRELIMINARY_FRAME_NO AND dai.CID = ooba.CID AND B.TYPE_OF_ITEM = 1 )))		"
+"						WHEN 12 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name13.NAME FROM KSHMT_ABSENCE_FRAME name13 WHERE cis.CID = name13.CID AND  name13.ABSENCE_FRAME_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)))"
+"						WHEN 13 THEN (REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name14.NAME FROM KSHMT_SPHD_FRAME name14 WHERE cis.CID = name14.CID AND  name14.SPHD_FRAME_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)))"
+"						WHEN 14 THEN (REPLACE(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name15.TOTAL_TIMES_NAME FROM KSHST_TOTAL_TIMES name15 WHERE cis.CID = name15.CID AND  name15.TOTAL_TIMES_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)), '{1}', (SELECT name15.TOTAL_TIMES_ABNAME FROM KSHST_TOTAL_TIMES name15 WHERE cis.CID = name15.CID AND  name15.TOTAL_TIMES_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+"						WHEN 15 THEN (REPLACE(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name16.SPHD_NAME FROM KSHST_SPECIAL_HOLIDAY name16 WHERE cis.CID = name16.CID AND  name16.SPHD_CD = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)), '{1}', (SELECT name16.MEMO FROM KSHST_SPECIAL_HOLIDAY name16 WHERE cis.CID = name16.CID AND  name16.SPHD_CD = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+"												ELSE dai.ATTENDANCE_ITEM_NAME"
+"												END	"
+"										)					 "
+"		 			FROM"
+"		 				KRCMT_CALC_ITEM_SELECTION cis"
+"		 				LEFT JOIN KRCMT_MON_ATTENDANCE_ITEM mai ON mai.CID = cis.CID AND mai.M_ATD_ITEM_ID = cis.ATTENDANCE_ITEM_ID	AND cis.CID = ?companyId			"
+"		 				LEFT JOIN KRCMT_DAI_ATTENDANCE_ITEM dai ON dai.CID = cis.CID AND dai.ATTENDANCE_ITEM_ID = cis.ATTENDANCE_ITEM_ID AND cis.CID = ?companyId"
+"						LEFT JOIN KFNMT_ATTENDANCE_LINK B ON( mai.M_ATD_ITEM_ID = B.ATTENDANCE_ITEM_ID OR dai.ATTENDANCE_ITEM_ID = B.ATTENDANCE_ITEM_ID)"
+"		 			WHERE"
+"		 				fd.FORMULA_ID = cis.FORMULA_ID 	AND fd.OPTIONAL_ITEM_NO = cis.OPTIONAL_ITEM_NO"
+"		 			ORDER BY"
+"		 				dai.DISPLAY_NUMBER FOR XML PATH ('')"
+"		 				),"
+"		 			1,"
+"		 			1,"
+"		 			''"
+"		 		)), (STUFF(("
+"		 			SELECT "
+"		 				 CASE cis.OPERATOR  WHEN 0 THEN '+'	WHEN 1 THEN ' - '	WHEN 2 THEN '*'	WHEN 3 THEN '/' END "
+"						 + ("
+"											SELECT CASE B.FRAME_CATEGORY "
+" WHEN 0 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name1.OT_FR_NAME FROM KSHST_OVERTIME_FRAME name1 WHERE cis.CID = name1.CID AND  name1.OT_FR_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1 )))	"
+" 						WHEN 1 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name2.OT_FR_NAME FROM KSHST_OVERTIME_FRAME name2 WHERE cis.CID = name2.CID AND  name2.OT_FR_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1 )))			"
+" 						WHEN 2 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name3.WDO_FR_NAME FROM KSHST_WORKDAYOFF_FRAME name3 WHERE cis.CID = name3.CID AND  name3.WDO_FR_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+" 						WHEN 3 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name4.WDO_FR_NAME FROM KSHST_WORKDAYOFF_FRAME name4 WHERE cis.CID = name4.CID AND  name4.WDO_FR_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1 )))"
+" 						WHEN 4 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name5.PREMIUM_NAME FROM KMNMT_PREMIUM_ITEM name5 WHERE cis.CID = name5.CID AND  name5.PREMIUM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+" 						WHEN 5 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name6.TIME_ITEM_NAME FROM KBPST_BP_TIME_ITEM name6 WHERE cis.CID = name6.CID AND  name6.TIME_ITEM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+" 						WHEN 6 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name7.TIME_ITEM_NAME FROM KBPST_BP_TIME_ITEM name7 WHERE cis.CID = name7.CID AND  name7.TIME_ITEM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+" 						WHEN 7 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name8.DIVERGENCETIME_NAME FROM KMKMT_DIVERGENCE_TIME name8 WHERE cis.CID = name8.CID AND  name8.DIVERGENCETIME_ID = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+" 						WHEN 8 THEN (REPLACE(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name9.OPTIONAL_ITEM_NAME FROM KRCST_OPTIONAL_ITEM name9 WHERE cis.CID = name9.CID AND  name9.OPTIONAL_ITEM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)), '{1}', (SELECT name9.UNIT_OF_OPTIONAL_ITEM FROM KRCST_OPTIONAL_ITEM name9 WHERE cis.CID = name9.CID AND  name9.OPTIONAL_ITEM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)))"
+" 						WHEN 10 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name11.NAME FROM KSMST_SPECIFIC_DATE_ITEM name11 WHERE cis.CID = name11.CID AND  name11.SPECIFIC_DATE_ITEM_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+" 						WHEN 11 THEN (REPLACE(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name12.NAME FROM KSHST_OVER_TIME name12 WHERE cis.CID = name12.CID AND name12.OVER_TIME_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)), '{1}', (SELECT ooba.NAME FROM KSHST_OUTSIDE_OT_BRD ooba WHERE ooba.BRD_ITEM_NO = B.PRELIMINARY_FRAME_NO AND mai.CID = ooba.CID AND B.TYPE_OF_ITEM = 2)))						"
+" 						WHEN 12 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name13.NAME FROM KSHMT_ABSENCE_FRAME name13 WHERE cis.CID = name13.CID AND  name13.ABSENCE_FRAME_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+" 						WHEN 13 THEN (REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name14.NAME FROM KSHMT_SPHD_FRAME name14 WHERE cis.CID = name14.CID AND  name14.SPHD_FRAME_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+" 						WHEN 14 THEN (REPLACE(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name15.TOTAL_TIMES_NAME FROM KSHST_TOTAL_TIMES name15 WHERE cis.CID = name15.CID AND  name15.TOTAL_TIMES_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)), '{1}', (SELECT name15.TOTAL_TIMES_ABNAME FROM KSHST_TOTAL_TIMES name15 WHERE cis.CID = name15.CID AND  name15.TOTAL_TIMES_NO = B.FRAME_NO AND B.TYPE_OF_ITEM = 2)))											"
+" 						WHEN 15 THEN (REPLACE(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name16.SPHD_NAME FROM KSHST_SPECIAL_HOLIDAY name16 WHERE cis.CID = name16.CID AND  name16.SPHD_CD = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)), '{1}', (SELECT name16.MEMO FROM KSHST_SPECIAL_HOLIDAY name16 WHERE cis.CID = name16.CID AND  name16.SPHD_CD = B.FRAME_NO AND B.TYPE_OF_ITEM = 1)))"
+"												ELSE mai.M_ATD_ITEM_NAME"
+"												END	"
+"										)						 "
+"		 			FROM"
+"		 				KRCMT_CALC_ITEM_SELECTION cis"
+"		 				LEFT JOIN KRCMT_MON_ATTENDANCE_ITEM mai ON mai.CID = cis.CID AND mai.M_ATD_ITEM_ID = cis.ATTENDANCE_ITEM_ID	AND cis.CID = ?companyId			"
+"		 				LEFT JOIN KRCMT_DAI_ATTENDANCE_ITEM dai ON dai.CID = cis.CID AND dai.ATTENDANCE_ITEM_ID = cis.ATTENDANCE_ITEM_ID AND cis.CID = ?companyId"
+"						LEFT JOIN KFNMT_ATTENDANCE_LINK B ON( mai.M_ATD_ITEM_ID = B.ATTENDANCE_ITEM_ID OR dai.ATTENDANCE_ITEM_ID = B.ATTENDANCE_ITEM_ID)"
+"		 			WHERE"
+"		 				fd.FORMULA_ID = cis.FORMULA_ID 	AND fd.OPTIONAL_ITEM_NO = cis.OPTIONAL_ITEM_NO"
+"		 			ORDER BY"
+"		 				mai.DISP_NO ASC FOR XML PATH ('')"
+"		 				),"
+"		 			1,"
+"		 			1,"
+"		 			''"
+"		 		))) , NULL) AS ATTENDANCE_ITEM_NAME"
+"		,"
+"		IIF(oif.CALC_ATR = 1 ,"
+"		(CASE fs.LEFT_SET_METHOD "
+"	WHEN 1 THEN"
+"	CONVERT ( VARCHAR, CAST(fs.LEFT_INPUT_VAL AS INT) )"
+"	WHEN 0 THEN"
+"		CONVERT (VARCHAR,"
+"		( SELECT koiff.SYMBOL FROM KRCMT_OPT_ITEM_FORMULA koiff WHERE koiff.OPTIONAL_ITEM_NO = oi.OPTIONAL_ITEM_NO AND fs.LEFT_FORMULA_ITEM_ID = koiff.FORMULA_ID)) "
+"END)"
+"+"
+"CASE"
+"			fs.OPERATOR "
+"		WHEN 0 THEN"
+"		' + ' "
+"		WHEN 1 THEN"
+"		' - ' "
+"		WHEN 2 THEN"
+"		' * ' "
+"		WHEN 3 THEN"
+"		' / ' "
+"	END "
+"+"
+"(CASE fs.RIGHT_SET_METHOD "
+"	WHEN 1 THEN"
+"	CONVERT ( VARCHAR, CAST(fs.RIGHT_INPUT_VAL AS INT) )"
+"	WHEN 0 THEN"
+"		CONVERT (VARCHAR,"
+"		( SELECT koiff.SYMBOL FROM KRCMT_OPT_ITEM_FORMULA koiff WHERE koiff.OPTIONAL_ITEM_NO = oi.OPTIONAL_ITEM_NO AND fs.RIGHT_FORMULA_ITEM_ID = koiff.FORMULA_ID)) "
+"END)"
+"			, NULL) AS ATTENDANCE_ITEM_2"
+"		FROM"
+"			KRCST_OPTIONAL_ITEM oi"
+"			LEFT JOIN KRCST_CALC_RESULT_RANGE crr ON oi.CID = crr.CID AND oi.OPTIONAL_ITEM_NO = crr.OPTIONAL_ITEM_NO"
+"			LEFT JOIN KRCMT_FORMULA_ROUNDING fr_day ON oi.CID = fr_day.CID AND oi.OPTIONAL_ITEM_NO = fr_day.OPTIONAL_ITEM_NO AND fr_day.ROUNDING_ATR = 1"
+"			LEFT JOIN KRCMT_FORMULA_ROUNDING fr_month ON oi.CID = fr_month.CID AND oi.OPTIONAL_ITEM_NO = fr_month.OPTIONAL_ITEM_NO AND fr_month.ROUNDING_ATR = 2 AND fr_day.FORMULA_ID = fr_month.FORMULA_ID"
+"			LEFT JOIN KRCMT_OPT_ITEM_FORMULA oif ON oi.CID = oif.CID AND oi.OPTIONAL_ITEM_NO = oif.OPTIONAL_ITEM_NO AND fr_day.FORMULA_ID = oif.FORMULA_ID"
+"			LEFT JOIN KRCST_FORMULA_DISPORDER fd ON oif.OPTIONAL_ITEM_NO = fd.OPTIONAL_ITEM_NO AND oif.CID = fd.CID AND oif.FORMULA_ID = fd.FORMULA_ID"
+"			LEFT JOIN KRCMT_CALC_ITEM_SELECTION cis ON fd.FORMULA_ID = cis.FORMULA_ID AND fd.OPTIONAL_ITEM_NO = cis.OPTIONAL_ITEM_NO AND cis.CID = fd.CID"
+"			LEFT JOIN KRCMT_FORMULA_SETTING fs ON fd.FORMULA_ID = fs.FORMULA_ID  AND fd.OPTIONAL_ITEM_NO = fs.OPTIONAL_ITEM_NO"
+"		WHERE"
+"			oi.CID = ?companyId"
+"			) AS RESULT_END"
+"		GROUP BY OPTIONAL_ITEM_NO, SYMBOL, FORMULA_ATR, FORMULA_NAME, CALC_ATR, ATTENDANCE_ITEM_NAME, ATTENDANCE_ITEM_2, UPPER_LIMIT_ATR, UPPER_RANGE, LOWER_LIMIT_ATR, LOWER_RANGE, DAY_ROUNDING_UNIT, DAY_ROUNDING, MON_ROUNDING_UNIT, MON_ROUNDING, UPPER_LIMIT_ATR, CALC_ATR_2, DISPORDER) AS RESULT_RIGHT"
+"		ON RESULT_LEFT.OPTIONAL_ITEM_NO = RESULT_RIGHT.OPTIONAL_ITEM_NO ) AS RESULT_TOTAL"
+"		)AS RESULT_FINAL ORDER BY RESULT_FINAL.OPTIONAL_ITEM_NO ASC";
	@Override
	public List<MasterData> getDataTableExport(String companyId) {
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL_ONE.toString()).setParameter("companyId",
				companyId);
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
			Integer optionalItemAtr = null;
			Integer optionalItemUse = null;
			for (Object[] objects : data) {
				if (!Objects.isNull(objects[2])) {
					optionalItemAtr = ((BigDecimal) objects[2]).intValue();
				}
				if (!Objects.isNull(objects[4])) {
					optionalItemUse = ((BigDecimal) objects[4]).intValue();
				}
				if(ObjectUtils.anyNotNull(objects) == true){
					datas.add(dataContentTable(objects, optionalItemAtr, optionalItemUse));
				}
			}
			return datas;
	}

	private MasterData dataContentTable(Object[] object, Integer optionalItemAtr, Integer optionalItemUse) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(CalFormulasItemColumn.KMK002_76, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_76)
                .value(object[0] != null ? (BigDecimal) object[0] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_77, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_77)
                .value(object[1] != null ? (String) object[1] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_78, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_78)
                .value(object[2] != null ? EnumAdaptor.valueOf(((BigDecimal) object[2]).intValue(), OptionalItemAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_79, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_79)
                .value(object[3] != null ? (String) object[3] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_80, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_80)
                .value(object[4] != null ? EnumAdaptor.valueOf(((BigDecimal) object[4]).intValue(), OptionalItemUsageAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_81, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_81)
                .value(object[5] != null && optionalItemUse == 1 ? EnumAdaptor.valueOf(((BigDecimal) object[5]).intValue(), PerformanceAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_82, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_82)
                .value(object[6] != null && optionalItemUse == 1 ? EnumAdaptor.valueOf(((BigDecimal) object[6]).intValue(), EmpConditionAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_83, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_83)
                .value(object[7] != null && optionalItemUse == 1 ? (String) object[7] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_84, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_84)
                .value(object[8] != null && optionalItemUse == 1 ? ((BigDecimal) object[8]).intValue() == 1 ? "○" : "-" : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
         // Upper value
    		switch (optionalItemAtr) {
    			case 0:
    				data.put(CalFormulasItemColumn.KMK002_85, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_85)
    		                .value(object[9] != null && optionalItemUse == 1 && ((BigDecimal) object[8]).intValue() == 1 ? formatTime(((BigDecimal) object[9]).intValue()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				break;
    			case 1:
    				data.put(CalFormulasItemColumn.KMK002_85, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_85)
    		                .value(object[9] != null && optionalItemUse == 1 && ((BigDecimal) object[8]).intValue() == 1 ? formatNumber(((BigDecimal) object[9]).toString()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				break;
    			case 2:
    				data.put(CalFormulasItemColumn.KMK002_85, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_85)
    		                .value(object[9] != null && optionalItemUse == 1 && ((BigDecimal) object[8]).intValue() == 1 ? formatAmount(((BigDecimal) object[9]).toString()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				break;
    		}
            
            data.put(CalFormulasItemColumn.KMK002_86, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_86)
                .value(object[10] != null && optionalItemUse == 1 ? ((BigDecimal) object[10]).intValue() == 1 ? "○" : "-" : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
         // Lower value
    		switch (optionalItemAtr) {
    			case 0:
    				data.put(CalFormulasItemColumn.KMK002_87, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_87)
    		                .value(object[11] != null && optionalItemUse == 1 && ((BigDecimal) object[10]).intValue() == 1 ? formatTime(((BigDecimal) object[11]).intValue()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				break;
    			case 1:
    				data.put(CalFormulasItemColumn.KMK002_87, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_87)
    		                .value(object[11] != null && optionalItemUse == 1 && ((BigDecimal) object[10]).intValue() == 1 ? formatNumber(((BigDecimal) object[11]).toString()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				break;
    			case 2:
    				data.put(CalFormulasItemColumn.KMK002_87, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_87)
    		                .value(object[11] != null && optionalItemUse == 1 && ((BigDecimal) object[10]).intValue() == 1 ? formatAmount(((BigDecimal) object[11]).toString()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				break;
    		}
            
            data.put(CalFormulasItemColumn.KMK002_88, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_88)
                .value(object[12] != null && optionalItemUse == 1 ? (String) object[12] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_89, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_89)
                .value(object[13] != null && optionalItemUse == 1 ? EnumAdaptor.valueOf(((BigDecimal) object[13]).intValue(), OptionalItemAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_90, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_90)
                .value(object[14] != null && optionalItemUse == 1 ? (String) object[14] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CalFormulasItemColumn.KMK002_91, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_91)
                .value(object[15] != null && optionalItemUse == 1 ? EnumAdaptor.valueOf(((BigDecimal) object[15]).intValue(), CalculationAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            
            switch (optionalItemAtr) {
				case 0:
					data.put(CalFormulasItemColumn.KMK002_93, MasterCellData.builder()
			                .columnId(CalFormulasItemColumn.KMK002_93)
			                .value(object[17] != null && optionalItemUse == 1 ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) object[17]).intValue(), Rounding.class).nameId) : "")
			                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
			                .build());
					break;
				case 1:
					data.put(CalFormulasItemColumn.KMK002_93, MasterCellData.builder()
			                .columnId(CalFormulasItemColumn.KMK002_93)
			                .value(object[17] != null && optionalItemUse == 1 ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[17]).intValue(), NumberRounding.class).nameId) : "")
			                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
			                .build());
					break;
				case 2:
					data.put(CalFormulasItemColumn.KMK002_93, MasterCellData.builder()
			                .columnId(CalFormulasItemColumn.KMK002_93)
			                .value(object[17] != null && optionalItemUse == 1 ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[17]).intValue(), AmountRounding.class).nameId) : "")
			                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
			                .build());
					break;
            }
            
         // A7_18
    		switch (optionalItemAtr) {
    			case 0:
    				data.put(CalFormulasItemColumn.KMK002_92, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_92)
    		                .value(object[16] != null && optionalItemUse == 1 ? TextResource .localize(EnumAdaptor.valueOf(((BigDecimal) object[16]).intValue(), Unit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 1:
    				data.put(CalFormulasItemColumn.KMK002_92, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_92)
    		                .value(object[16] != null && optionalItemUse == 1 ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[16]).intValue(), NumberUnit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 2:
    				data.put(CalFormulasItemColumn.KMK002_92, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_92)
    		                .value(object[16] != null && optionalItemUse == 1 ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[16]).intValue(), AmountUnit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    		}
            
    		// A7_19
    		switch (optionalItemAtr) {
    			case 0:
    				data.put(CalFormulasItemColumn.KMK002_95, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_95)
    		                .value(object[19] != null && optionalItemUse == 1 ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[19]).intValue(), Rounding.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 1:
    				data.put(CalFormulasItemColumn.KMK002_95, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_95)
    		                .value(object[19] != null && optionalItemUse == 1 ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[19]).intValue(), NumberRounding.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 2:
    				data.put(CalFormulasItemColumn.KMK002_95, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_95)
    		                .value(object[19] != null && optionalItemUse == 1 ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[19]).intValue(), AmountRounding.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    		}
            
    		// A7_20
    		switch (optionalItemAtr) {
    			case 0:
    				data.put(CalFormulasItemColumn.KMK002_94, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_94)
    		                .value(object[19] != null && optionalItemUse == 1 ? TextResource .localize(EnumAdaptor.valueOf(((BigDecimal) object[18]).intValue(), Unit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 1:
    				data.put(CalFormulasItemColumn.KMK002_94, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_94)
    		                .value(object[19] != null && optionalItemUse == 1 ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[18]).intValue(), NumberUnit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 2:
    				data.put(CalFormulasItemColumn.KMK002_94, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_94)
    		                .value(object[19] != null && optionalItemUse == 1 ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[18]).intValue(), AmountUnit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    		}
            
            
            data.put(CalFormulasItemColumn.KMK002_96, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_96)
                .value(object[20] != null && optionalItemUse == 1 ? ((BigDecimal) object[20]).intValue() == 1 ? "○" : "-" : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            
		if (object[20] != null) {
			switch (((BigDecimal) object[20]).intValue()) {
			case 0:
				data.put(CalFormulasItemColumn.KMK002_97,
						MasterCellData.builder().columnId(CalFormulasItemColumn.KMK002_97)
								.value(object[21] != null && optionalItemUse == 1 ? ((String) object[21]) : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				break;
			case 1:
				data.put(CalFormulasItemColumn.KMK002_97,
						MasterCellData.builder().columnId(CalFormulasItemColumn.KMK002_97)
								.value(object[22] != null && optionalItemUse == 1 ? ((String) object[22]) : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				break;
			}
		} else {
			data.put(CalFormulasItemColumn.KMK002_97, MasterCellData.builder().columnId(CalFormulasItemColumn.KMK002_97)
					.value("").style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}
		
		return MasterData.builder().rowData(data).build();
	}

	private String formatTime(int source) {
		int regularized = Math.abs(source);
		int hourPart = (regularized / 60);
		int minutePart = regularized % 60;
		String resultString = StringUtils.join(StringUtil.padLeft(String.valueOf(hourPart), 1, '0'), ":",
				StringUtil.padLeft(String.valueOf(minutePart), 2, '0'));
		return resultString;
	}

	private String formatNumber(String number) {
		double numberParse = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,##0.0");
        String resultString = formatter.format(numberParse);
		return resultString;
	}

	private String formatAmount(String amount) {
		double amountParse = Double.parseDouble(amount);
        DecimalFormat formatter = new DecimalFormat("#,###");
		return formatter.format(amountParse);
	}
}