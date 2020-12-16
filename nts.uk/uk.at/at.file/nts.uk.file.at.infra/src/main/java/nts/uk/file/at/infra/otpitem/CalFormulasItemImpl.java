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
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountUnit;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRounding;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberUnit;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.optitem.EmpConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.PerformanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.CalculationAtr;
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

	private static String LANG_JA ="ja";
	private static final String GET_EXPORT_EXCEL_ONE;
	  static {
	      StringBuilder exportSQL = new StringBuilder();
	      exportSQL.append("       SELECT");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.OPTIONAL_ITEM_NO, NULL) AS OPTIONAL_ITEM_NO,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.OPTIONAL_ITEM_NAME, NULL) AS OPTIONAL_ITEM_NAME,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.CALC_ATR_OPT, NULL) AS CALC_ATR_OPT,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.OPTIONAL_ITEM_ATR, NULL) AS OPTIONAL_ITEM_ATR,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.UNIT_OF_OPTIONAL_ITEM, NULL) AS UNIT_OF_OPTIONAL_ITEM,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.USAGE_ATR, NULL) AS USAGE_ATR,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.PERFORMANCE_ATR, NULL) AS PERFORMANCE_ATR,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.EMP_CONDITION_ATR, NULL) AS EMP_CONDITION_ATR,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.NAME, NULL) AS NAME,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.UPPER_LIMIT_ATR, NULL) AS UPPER_LIMIT_ATR,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.UPPER_RANGE_DAY, NULL) AS UPPER_RANGE_DAY,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.UPPER_RANGE_MON, NULL) AS UPPER_RANGE_MON,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.LOWER_LIMIT_ATR, NULL) AS LOWER_LIMIT_ATR,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.LOWER_RANGE_DAY, NULL) AS LOWER_RANGE_DAY,");
	      exportSQL.append("          IIF(RESULT_FINAL.ROW_NUMBER = 1, RESULT_FINAL.LOWER_RANGE_MON, NULL) AS LOWER_RANGE_MON");
	      exportSQL.append("          ,RESULT_FINAL.ITEM_DESCRIP");
	      exportSQL.append("          ,RESULT_FINAL.ITEM_NOTE");
	      exportSQL.append("          ,RESULT_FINAL.SYMBOL");
	      exportSQL.append("          ,RESULT_FINAL.FORMULA_ATR");
	      exportSQL.append("          ,RESULT_FINAL.FORMULA_NAME");
	      exportSQL.append("          ,RESULT_FINAL.CALC_ATR ");
	      exportSQL.append("          ,IIF(RESULT_FINAL.PERFORMANCE_ATR = 1, RESULT_FINAL.DAY_ROUNDING_UNIT, NULL) DAY_ROUNDING_UNIT");
	      exportSQL.append("          ,IIF(RESULT_FINAL.PERFORMANCE_ATR = 1, RESULT_FINAL.DAY_ROUNDING, NULL) DAY_ROUNDING");
	      exportSQL.append("          ,RESULT_FINAL.MON_ROUNDING_UNIT");
	      exportSQL.append("          ,RESULT_FINAL.MON_ROUNDING");
	      exportSQL.append("          ,RESULT_FINAL.CALC_ATR_2");
	      exportSQL.append("          ,RESULT_FINAL.ATTENDANCE_ITEM_NAME");
	      exportSQL.append("          ,RESULT_FINAL.ATTENDANCE_ITEM_2");
	      exportSQL.append("          , RESULT_FINAL.FORMULAR_FROM_FORMULAR");
	      exportSQL.append("          , RESULT_FINAL.FORMULAR");
	      exportSQL.append("         FROM");
	      exportSQL.append("          (SELECT RESULT_TOTAL.DISPORDER");
	      exportSQL.append("             ,RESULT_TOTAL.OPTIONAL_ITEM_NO");
	      exportSQL.append("             , RESULT_TOTAL.OPTIONAL_ITEM_NAME");
	      exportSQL.append("             ,RESULT_TOTAL.OPTIONAL_ITEM_ATR");
	      exportSQL.append("             ,RESULT_TOTAL.UNIT_OF_OPTIONAL_ITEM");
	      exportSQL.append("             ,RESULT_TOTAL.USAGE_ATR");
	      exportSQL.append("             ,RESULT_TOTAL.CALC_ATR_OPT");
	      exportSQL.append("             ,RESULT_TOTAL.PERFORMANCE_ATR");
	      exportSQL.append("             ,RESULT_TOTAL.EMP_CONDITION_ATR");
	      exportSQL.append("             ,RESULT_TOTAL.NAME");
	      exportSQL.append("             ,RESULT_TOTAL.UPPER_LIMIT_ATR");
	      exportSQL.append("             ,RESULT_TOTAL.UPPER_RANGE_DAY");
	      exportSQL.append("             ,RESULT_TOTAL.UPPER_RANGE_MON");
	      exportSQL.append("             ,RESULT_TOTAL.LOWER_LIMIT_ATR");
	      exportSQL.append("             ,RESULT_TOTAL.LOWER_RANGE_DAY");
	      exportSQL.append("             ,RESULT_TOTAL.LOWER_RANGE_MON");
	      exportSQL.append("             ,RESULT_TOTAL.ITEM_DESCRIP");
	      exportSQL.append("             ,RESULT_TOTAL.ITEM_NOTE");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.SYMBOL, NULL) AS SYMBOL");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.FORMULA_ATR, NULL) AS FORMULA_ATR");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.FORMULA_NAME, NULL) AS FORMULA_NAME");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.CALC_ATR, NULL) AS CALC_ATR ");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.DAY_ROUNDING_UNIT, NULL) AS DAY_ROUNDING_UNIT");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.DAY_ROUNDING, NULL) AS DAY_ROUNDING");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.MON_ROUNDING_UNIT, NULL) AS MON_ROUNDING_UNIT");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.MON_ROUNDING, NULL) AS MON_ROUNDING");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.CALC_ATR_2, NULL) AS CALC_ATR_2");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.ATTENDANCE_ITEM_NAME, NULL) AS ATTENDANCE_ITEM_NAME");
	      exportSQL.append("             ,IIF(RESULT_TOTAL.USAGE_ATR = 1, RESULT_TOTAL.ATTENDANCE_ITEM_2, NULL) AS ATTENDANCE_ITEM_2");
	      exportSQL.append("             , ROW_NUMBER() OVER (PARTITION BY RESULT_TOTAL.OPTIONAL_ITEM_NO ORDER BY RESULT_TOTAL.DISPORDER ASC, RESULT_TOTAL.OPTIONAL_ITEM_NO ASC) AS ROW_NUMBER");
	      exportSQL.append("             ,RESULT_TOTAL.FORMULAR_FROM_FORMULAR");
	      exportSQL.append("             ,RESULT_TOTAL.FORMULAR");
	      exportSQL.append("          FROM");
	      exportSQL.append("          (SELECT RESULT_LEFT.OPTIONAL_ITEM_NO");
	      exportSQL.append("           ,RESULT_LEFT.OPTIONAL_ITEM_NAME");
	      exportSQL.append("           ,RESULT_LEFT.OPTIONAL_ITEM_ATR");
	      exportSQL.append("           ,RESULT_LEFT.UNIT_OF_OPTIONAL_ITEM");
	      exportSQL.append("           ,RESULT_LEFT.USAGE_ATR");
	      exportSQL.append("           ,RESULT_LEFT.CALC_ATR_OPT");
	      exportSQL.append("           ,RESULT_LEFT.PERFORMANCE_ATR");
	      exportSQL.append("           ,RESULT_LEFT.EMP_CONDITION_ATR");
	      exportSQL.append("           ,RESULT_LEFT.NAME");
	      exportSQL.append("           ,RESULT_RIGHT.UPPER_LIMIT_ATR");
	      exportSQL.append("           ,RESULT_RIGHT.UPPER_RANGE_DAY");
	      exportSQL.append("           ,RESULT_RIGHT.UPPER_RANGE_MON");
	      exportSQL.append("           ,RESULT_RIGHT.LOWER_LIMIT_ATR");
	      exportSQL.append("           ,RESULT_RIGHT.LOWER_RANGE_DAY");
	      exportSQL.append("           ,RESULT_RIGHT.LOWER_RANGE_MON");
	      exportSQL.append("           ,RESULT_RIGHT.ITEM_DESCRIP");
	      exportSQL.append("           ,RESULT_RIGHT.ITEM_NOTE");
	      exportSQL.append("           ,RESULT_RIGHT.SYMBOL");
	      exportSQL.append("           ,RESULT_RIGHT.FORMULA_ATR");
	      exportSQL.append("           ,RESULT_RIGHT.FORMULA_NAME");
	      exportSQL.append("           ,RESULT_RIGHT.CALC_ATR ");
	      exportSQL.append("           ,RESULT_RIGHT.DAY_ROUNDING_UNIT");
	      exportSQL.append("           ,RESULT_RIGHT.DAY_ROUNDING");
	      exportSQL.append("           ,RESULT_RIGHT.MON_ROUNDING_UNIT");
	      exportSQL.append("           ,RESULT_RIGHT.MON_ROUNDING");
	      exportSQL.append("           ,RESULT_RIGHT.CALC_ATR_2");
	      exportSQL.append("           ,RESULT_RIGHT.ATTENDANCE_ITEM_NAME");
	      exportSQL.append("           ,RESULT_RIGHT.ATTENDANCE_ITEM_2");
	      exportSQL.append("           ,RESULT_RIGHT.DISPORDER");
	      exportSQL.append("            , RESULT_RIGHT.FORMULAR_FROM_FORMULAR");
	      exportSQL.append("            , RESULT_RIGHT.FORMULAR");
	      exportSQL.append("          FROM");
	      exportSQL.append("           (SELECT");
	      exportSQL.append("           RESULT_ONE.OPTIONAL_ITEM_NO");
	      exportSQL.append("           ,RESULT_ONE.OPTIONAL_ITEM_NAME");
	      exportSQL.append("           ,RESULT_ONE.OPTIONAL_ITEM_ATR");
	      exportSQL.append("           ,RESULT_ONE.UNIT_OF_OPTIONAL_ITEM");
	      exportSQL.append("           ,RESULT_ONE.USAGE_ATR");
	      exportSQL.append("           ,RESULT_ONE.CALC_ATR AS CALC_ATR_OPT");
	      exportSQL.append("           ,RESULT_ONE.PERFORMANCE_ATR");
	      exportSQL.append("           ,RESULT_ONE.EMP_CONDITION_ATR");
	      exportSQL.append("           ,RESULT_ONE.NAME");
	      exportSQL.append("            FROM (SELECT");
	      exportSQL.append("             oi.OPTIONAL_ITEM_NO");
	      exportSQL.append("            ,oi.OPTIONAL_ITEM_NAME");
	      exportSQL.append("            ,oi.OPTIONAL_ITEM_ATR");
	      exportSQL.append("            ,oi.UNIT_OF_OPTIONAL_ITEM");
	      exportSQL.append("            ,oi.USAGE_ATR");
	      exportSQL.append("            ,oi.CALC_ATR");
	      exportSQL.append("            ,oi.PERFORMANCE_ATR");
	      exportSQL.append("            ,oi.EMP_CONDITION_ATR");
	      exportSQL.append("            ,IIF(oi.EMP_CONDITION_ATR = 1,STUFF((");
	      exportSQL.append("             SELECT");
	      exportSQL.append("            IIF(oi.EMP_CONDITION_ATR = 1,', ' + ec1.EMP_CD + (CASE WHEN emp1.NAME IS NULL THEN 'マスタ未登録' ELSE  emp1.NAME END), NULL)");
	      exportSQL.append("             FROM");
	      exportSQL.append("             KRCST_APPL_EMP_CON ec1");
	      exportSQL.append("             LEFT JOIN BSYMT_EMPLOYMENT emp1 ON ec1.CID = emp1.CID ");
	      exportSQL.append("             AND ec1.EMP_CD = emp1.CODE ");
	      exportSQL.append("          WHERE");
	      exportSQL.append("           oi.CID = ec1.CID AND ec1.EMP_APPL_ATR = 1 AND oi.OPTIONAL_ITEM_NO = ec1.OPTIONAL_ITEM_NO ORDER BY ec1.EMP_CD");
	      exportSQL.append("              FOR XML PATH ('')");
	      exportSQL.append("              ),");
	      exportSQL.append("             1,");
	      exportSQL.append("             1,");
	      exportSQL.append("             ''");
	      exportSQL.append("            ), NULL) AS NAME");
	      exportSQL.append("           FROM");
	      exportSQL.append("           KRCMT_ANYV oi");
	      exportSQL.append("          WHERE");
	      exportSQL.append("           oi.CID = ?companyId ");
	      exportSQL.append("         ) AS RESULT_ONE");
	      exportSQL.append("         GROUP BY");
	      exportSQL.append("           RESULT_ONE.OPTIONAL_ITEM_NO");
	      exportSQL.append("          ,RESULT_ONE.OPTIONAL_ITEM_NAME");
	      exportSQL.append("          ,RESULT_ONE.OPTIONAL_ITEM_ATR");
	      exportSQL.append("          ,RESULT_ONE.UNIT_OF_OPTIONAL_ITEM");
	      exportSQL.append("          ,RESULT_ONE.USAGE_ATR");
	      exportSQL.append("          ,RESULT_ONE.CALC_ATR");
	      exportSQL.append("          ,RESULT_ONE.PERFORMANCE_ATR");
	      exportSQL.append("          ,RESULT_ONE.EMP_CONDITION_ATR");
	      exportSQL.append("          ,RESULT_ONE.NAME");
	      exportSQL.append("         ) RESULT_LEFT");
	      exportSQL.append("         LEFT JOIN");
	      exportSQL.append("         (SELECT * FROM");
	      exportSQL.append("         (");
	      exportSQL.append("          SELECT");
	      exportSQL.append("          oi.OPTIONAL_ITEM_NO");
	      exportSQL.append("          ,crr.UPPER_LIMIT_ATR");
	      exportSQL.append("          ,(CASE oi.OPTIONAL_ITEM_ATR");
	      exportSQL.append("            WHEN 0 THEN crr.UPPER_DAY_TIME_RANGE");
	      exportSQL.append("            WHEN 1 THEN crr.UPPER_DAY_NUMBER_RANGE");
	      exportSQL.append("            WHEN 2 THEN crr.UPPER_DAY_AMOUNT_RANGE");
	      exportSQL.append("           END) AS UPPER_RANGE_DAY");
	      exportSQL.append("           ,(CASE oi.OPTIONAL_ITEM_ATR");
	      exportSQL.append("            WHEN 0 THEN crr.UPPER_MON_TIME_RANGE");
	      exportSQL.append("            WHEN 1 THEN crr.UPPER_MON_NUMBER_RANGE");
	      exportSQL.append("            WHEN 2 THEN crr.UPPER_MON_AMOUNT_RANGE");
	      exportSQL.append("           END) AS UPPER_RANGE_MON");
	      exportSQL.append("           ,crr.LOWER_LIMIT_ATR");
	      exportSQL.append("           ,(CASE oi.OPTIONAL_ITEM_ATR");
	      exportSQL.append("            WHEN 0 THEN crr.LOWER_DAY_TIME_RANGE");
	      exportSQL.append("            WHEN 1 THEN crr.LOWER_DAY_NUMBER_RANGE");
	      exportSQL.append("            WHEN 2 THEN crr.LOWER_DAY_AMOUNT_RANGE");
	      exportSQL.append("            END) AS LOWER_RANGE_DAY");
	      exportSQL.append("            ,(CASE oi.OPTIONAL_ITEM_ATR");
	      exportSQL.append("            WHEN 0 THEN crr.LOWER_MON_TIME_RANGE");
	      exportSQL.append("            WHEN 1 THEN crr.LOWER_MON_NUMBER_RANGE");
	      exportSQL.append("            WHEN 2 THEN crr.LOWER_MON_AMOUNT_RANGE");
	      exportSQL.append("            END) AS LOWER_RANGE_MON");
	      exportSQL.append("            ,oi.ITEM_DESCRIP");
	      exportSQL.append("            ,oi.ITEM_NOTE");
	      exportSQL.append("           ,oif.SYMBOL");
	      exportSQL.append("          ,oif.FORMULA_ATR");
	      exportSQL.append("          ,oif.FORMULA_NAME");
	      exportSQL.append("          ,oif.CALC_ATR ");
	      exportSQL.append("            ,(CASE oi.OPTIONAL_ITEM_ATR");
	      exportSQL.append("            WHEN 0 THEN fr_day.TIME_ROUNDING_UNIT");
	      exportSQL.append("            WHEN 1 THEN fr_day.NUMBER_ROUNDING_UNIT");
	      exportSQL.append("            WHEN 2 THEN fr_day.AMOUNT_ROUNDING_UNIT");
	      exportSQL.append("           END) AS DAY_ROUNDING_UNIT");
	      exportSQL.append("           ,(CASE oi.OPTIONAL_ITEM_ATR");
	      exportSQL.append("            WHEN 0 THEN fr_day.TIME_ROUNDING");
	      exportSQL.append("            WHEN 1 THEN fr_day.NUMBER_ROUNDING");
	      exportSQL.append("            WHEN 2 THEN fr_day.AMOUNT_ROUNDING");
	      exportSQL.append("            END) AS DAY_ROUNDING");
	      exportSQL.append("           ,(CASE oi.OPTIONAL_ITEM_ATR");
	      exportSQL.append("            WHEN 0 THEN fr_month.TIME_ROUNDING_UNIT");
	      exportSQL.append("            WHEN 1 THEN fr_month.NUMBER_ROUNDING_UNIT");
	      exportSQL.append("            WHEN 2 THEN fr_month.AMOUNT_ROUNDING_UNIT");
	      exportSQL.append("           END) AS MON_ROUNDING_UNIT");
	      exportSQL.append("           ,(CASE oi.OPTIONAL_ITEM_ATR");
	      exportSQL.append("            WHEN 0 THEN fr_month.TIME_ROUNDING");
	      exportSQL.append("            WHEN 1 THEN fr_month.NUMBER_ROUNDING");
	      exportSQL.append("            WHEN 2 THEN fr_month.AMOUNT_ROUNDING");
	      exportSQL.append("           END) AS MON_ROUNDING");
	      exportSQL.append("          ,oif.CALC_ATR AS CALC_ATR_2");
	      exportSQL.append("          ,fd.DISPORDER");
	      exportSQL.append("         , IIF(oif.CALC_ATR = 0 , IIF(oi.PERFORMANCE_ATR = 1, (STUFF((");
	      exportSQL.append("             SELECT ");
	      exportSQL.append("               ' '");
	      exportSQL.append("              + (");
	      exportSQL.append("                  SELECT CASE B.FRAME_CATEGORY ");
	      exportSQL.append("             WHEN 0 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name1.OT_FR_NAME FROM KSHST_OVERTIME_FRAME name1 WHERE cis1.CID = name1.CID AND  name1.OT_FR_NO = B.FRAME_NO)))) ");
	      exportSQL.append("             WHEN 1 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name2.OT_FR_NAME FROM KSHST_OVERTIME_FRAME name2 WHERE cis1.CID = name2.CID AND  name2.OT_FR_NO = B.FRAME_NO))))   ");
	      exportSQL.append("             WHEN 2 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name3.WDO_FR_NAME FROM KSHST_WORKDAYOFF_FRAME name3 WHERE cis1.CID = name3.CID AND  name3.WDO_FR_NO = B.FRAME_NO))))");
	      exportSQL.append("             WHEN 3 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name4.TRANS_FR_NAME FROM KSHST_WORKDAYOFF_FRAME name4 WHERE cis1.CID = name4.CID AND  name4.WDO_FR_NO = B.FRAME_NO))))");
	      exportSQL.append("             WHEN 4 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name5.PREMIUM_NAME FROM KMNMT_PREMIUM_ITEM name5 WHERE cis1.CID = name5.CID AND  name5.PREMIUM_NO = B.FRAME_NO))))");
	      exportSQL.append("             WHEN 5 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name6.TIME_ITEM_NAME FROM KBPST_BP_TIME_ITEM name6 WHERE cis1.CID = name6.CID AND  name6.TIME_ITEM_NO = B.FRAME_NO))))");
	      exportSQL.append("             WHEN 6 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name7.TIME_ITEM_NAME FROM KBPST_BP_TIME_ITEM name7 WHERE cis1.CID = name7.CID AND  name7.TIME_ITEM_NO = B.FRAME_NO))))");
	      exportSQL.append("             WHEN 7 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name8.DVGC_TIME_NAME FROM KRCST_DVGC_TIME name8 WHERE cis1.CID = name8.CID AND  name8.[NO] = B.FRAME_NO ))))");
	      exportSQL.append("             WHEN 8 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name9.OPTIONAL_ITEM_NAME FROM KRCMT_ANYV name9 WHERE cis1.CID = name9.CID AND  name9.OPTIONAL_ITEM_NO = B.FRAME_NO)), '{1}', (SELECT ISNULL(name91.UNIT_OF_OPTIONAL_ITEM,'') FROM KRCMT_ANYV name91 WHERE cis1.CID = name91.CID AND  name91.OPTIONAL_ITEM_NO = B.FRAME_NO))))");
	      exportSQL.append("             WHEN 10 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name11.NAME FROM KSMST_SPECIFIC_DATE_ITEM name11 WHERE cis1.CID = name11.CID AND  name11.SPECIFIC_DATE_ITEM_NO = B.FRAME_NO))))");
	      exportSQL.append("             WHEN 11 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name12.NAME FROM KSHST_OVER_TIME name12 WHERE cis1.CID = name12.CID AND  name12.OVER_TIME_NO = B.FRAME_NO)), '{1}', (SELECT ISNULL(ooba.NAME,'') FROM KSHST_OUTSIDE_OT_BRD ooba WHERE ooba.BRD_ITEM_NO = B.PRELIMINARY_FRAME_NO AND dai.CID = ooba.CID))))  ");
	      exportSQL.append("             WHEN 12 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name13.NAME FROM KSHMT_ABSENCE_FRAME name13 WHERE cis1.CID = name13.CID AND  name13.ABSENCE_FRAME_NO = B.FRAME_NO ))))");
	      exportSQL.append("             WHEN 13 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name14.NAME FROM KSHMT_SPHD_FRAME name14 WHERE cis1.CID = name14.CID AND  name14.SPHD_FRAME_NO = B.FRAME_NO ))))");
	      exportSQL.append("             WHEN 14 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name15.TOTAL_TIMES_NAME FROM KSHST_TOTAL_TIMES name15 WHERE cis1.CID = name15.CID AND  name15.TOTAL_TIMES_NO = B.FRAME_NO )), '{1}', (SELECT ISNULL(name15.TOTAL_TIMES_ABNAME,'') FROM KSHST_TOTAL_TIMES name15 WHERE cis.CID = name15.CID AND  name15.TOTAL_TIMES_NO = B.FRAME_NO))))");
	      exportSQL.append("             WHEN 15 THEN CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(REPLACE(dai.ATTENDANCE_ITEM_NAME, '{0}', (SELECT name16.SPHD_NAME FROM KSHST_SPECIAL_HOLIDAY name16 WHERE cis1.CID = name16.CID AND  name16.SPHD_CD = B.FRAME_NO)), '{1}', (SELECT ISNULL(name16.MEMO,'') FROM KSHST_SPECIAL_HOLIDAY name16 WHERE cis.CID = name16.CID AND  name16.SPHD_CD = B.FRAME_NO))))");
	      exportSQL.append("                   ELSE  CONCAT(CASE cis1.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END, dai.ATTENDANCE_ITEM_NAME)");
	      exportSQL.append("                   END ");
	      exportSQL.append("                 )      ");
	      exportSQL.append("             FROM");
	      exportSQL.append("              KRCMT_CALC_ITEM_SELECTION cis1 ");
	      exportSQL.append("              LEFT JOIN KRCMT_DAI_ATTENDANCE_ITEM dai ON dai.CID = cis1.CID AND dai.ATTENDANCE_ITEM_ID = cis1.ATTENDANCE_ITEM_ID AND cis1.CID = ?companyId");
	      exportSQL.append("             LEFT JOIN KFNMT_ATTENDANCE_LINK B ON(");
	      exportSQL.append("             dai.ATTENDANCE_ITEM_ID = B.ATTENDANCE_ITEM_ID AND B.TYPE_OF_ITEM = 1)");
	      exportSQL.append("             LEFT JOIN KRCST_FORMULA_DISPORDER fd1 ON cis1.OPTIONAL_ITEM_NO = fd1.OPTIONAL_ITEM_NO AND cis1.CID = fd1.CID AND        cis1.FORMULA_ID = fd1.FORMULA_ID");
	      exportSQL.append("             WHERE");
	      exportSQL.append("              fr_day.FORMULA_ID = cis1.FORMULA_ID  AND cis1.OPTIONAL_ITEM_NO = cis.OPTIONAL_ITEM_NO AND cis1.CID = cis.CID");
	      exportSQL.append("             ORDER BY");
	      exportSQL.append("              fd1.DISPORDER,dai.DISPLAY_NUMBER FOR XML PATH ('')");
	      exportSQL.append("              ),");
	      exportSQL.append("             1,");
	      exportSQL.append("             1,");
	      exportSQL.append("             ''");
	      exportSQL.append("            )), (STUFF((");
	      exportSQL.append("             SELECT ");
	      exportSQL.append("               ' ' ");
	      exportSQL.append("              + (");
	      exportSQL.append("                  SELECT CASE B1.FRAME_CATEGORY ");
	      exportSQL.append("                   WHEN 0 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name1.OT_FR_NAME FROM KSHST_OVERTIME_FRAME name1 WHERE cis.CID = name1.CID AND  name1.OT_FR_NO = B1.FRAME_NO )))) ");
	      exportSQL.append("             WHEN 1 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name2.OT_FR_NAME FROM KSHST_OVERTIME_FRAME name2 WHERE cis2.CID = name2.CID AND  name2.OT_FR_NO = B1.FRAME_NO ))))   ");
	      exportSQL.append("             WHEN 2 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name3.WDO_FR_NAME FROM KSHST_WORKDAYOFF_FRAME name3 WHERE cis2.CID = name3.CID AND  name3.WDO_FR_NO = B1.FRAME_NO ))))");
	      exportSQL.append("             WHEN 3 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name4.TRANS_FR_NAME FROM KSHST_WORKDAYOFF_FRAME name4 WHERE cis2.CID = name4.CID AND  name4.WDO_FR_NO = B1.FRAME_NO ))))");
	      exportSQL.append("             WHEN 4 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name5.PREMIUM_NAME FROM KMNMT_PREMIUM_ITEM name5 WHERE cis2.CID = name5.CID AND  name5.PREMIUM_NO = B1.FRAME_NO ))))");
	      exportSQL.append("             WHEN 5 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name6.TIME_ITEM_NAME FROM KBPST_BP_TIME_ITEM name6 WHERE cis2.CID = name6.CID AND  name6.TIME_ITEM_NO = B1.FRAME_NO ))))");
	      exportSQL.append("             WHEN 6 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name7.TIME_ITEM_NAME FROM KBPST_BP_TIME_ITEM name7 WHERE cis2.CID = name7.CID AND  name7.TIME_ITEM_NO = B1.FRAME_NO ))))");
	      exportSQL.append("             WHEN 7 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name8.DVGC_TIME_NAME FROM KRCST_DVGC_TIME name8 WHERE cis2.CID = name8.CID AND  name8.[NO] = B1.FRAME_NO ))))");
	      exportSQL.append("             WHEN 8 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(REPLACE(mai.M_ATD_ITEM_NAME, '{0}',(SELECT name9.OPTIONAL_ITEM_NAME FROM KRCMT_ANYV name9 WHERE cis2.CID = name9.CID AND  name9.OPTIONAL_ITEM_NO = B1.FRAME_NO )), '{1}', (SELECT ISNULL(name9.UNIT_OF_OPTIONAL_ITEM,'') FROM KRCMT_ANYV name9 WHERE cis2.CID = name9.CID AND  name9.OPTIONAL_ITEM_NO = B1.FRAME_NO ))))");
	      exportSQL.append("             WHEN 10 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name11.NAME FROM KSMST_SPECIFIC_DATE_ITEM name11 WHERE cis2.CID = name11.CID AND  name11.SPECIFIC_DATE_ITEM_NO = B1.FRAME_NO ))))");
	      exportSQL.append("              WHEN 11 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT  name12.NAME FROM KSHST_OVER_TIME name12 WHERE cis2.CID = name12.CID AND  name12.OVER_TIME_NO = B1.FRAME_NO )), '{1}', (SELECT ISNULL(ooba.NAME,'') FROM KSHST_OUTSIDE_OT_BRD ooba WHERE ooba.BRD_ITEM_NO = B1.PRELIMINARY_FRAME_NO AND mai.CID = ooba.CID ))))");
	      exportSQL.append("             WHEN 12 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name13.NAME FROM KSHMT_ABSENCE_FRAME name13 WHERE cis2.CID = name13.CID AND  name13.ABSENCE_FRAME_NO = B1.FRAME_NO ))))");
	      exportSQL.append("             WHEN 13 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name14.NAME FROM KSHMT_SPHD_FRAME name14 WHERE cis2.CID = name14.CID AND  name14.SPHD_FRAME_NO = B1.FRAME_NO ))))");
	      exportSQL.append("             WHEN 14 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name15.TOTAL_TIMES_NAME FROM KSHST_TOTAL_TIMES name15 WHERE cis2.CID = name15.CID AND  name15.TOTAL_TIMES_NO = B1.FRAME_NO )), '{1}', (SELECT ISNULL(name15.TOTAL_TIMES_ABNAME,'') FROM KSHST_TOTAL_TIMES name15 WHERE cis2.CID = name15.CID AND  name15.TOTAL_TIMES_NO = B1.FRAME_NO ))))           ");
	      exportSQL.append("             WHEN 15 THEN CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END,(REPLACE(REPLACE(mai.M_ATD_ITEM_NAME, '{0}', (SELECT name16.SPHD_NAME FROM KSHST_SPECIAL_HOLIDAY name16 WHERE cis2.CID = name16.CID AND  name16.SPHD_CD = B1.FRAME_NO )), '{1}', (SELECT ISNULL(name16.MEMO,'') FROM KSHST_SPECIAL_HOLIDAY name16 WHERE cis2.CID = name16.CID AND  name16.SPHD_CD = B1.FRAME_NO ))))");
	      exportSQL.append("                   ELSE CONCAT(CASE cis2.OPERATOR  WHEN 0 THEN '+ ' WHEN 1 THEN '- ' WHEN 2 THEN '* ' WHEN 3 THEN '/ ' END, mai.M_ATD_ITEM_NAME)");
	      exportSQL.append("                   END ");
	      exportSQL.append("                 )       ");
	      exportSQL.append("             FROM");
	      exportSQL.append("              KRCMT_CALC_ITEM_SELECTION cis2");
	      exportSQL.append("              LEFT JOIN KRCMT_MON_ATTENDANCE_ITEM mai ON mai.CID = cis2.CID AND mai.M_ATD_ITEM_ID = cis2.ATTENDANCE_ITEM_ID AND cis2.CID = ?companyId   ");
	      exportSQL.append("             LEFT JOIN KFNMT_ATTENDANCE_LINK B1 ON( mai.M_ATD_ITEM_ID = B1.ATTENDANCE_ITEM_ID ");
	      exportSQL.append("             AND B1.TYPE_OF_ITEM = 2");
	      exportSQL.append("             )");
	      exportSQL.append("             LEFT JOIN KRCST_FORMULA_DISPORDER fd2 ON cis2.OPTIONAL_ITEM_NO = fd2.OPTIONAL_ITEM_NO AND cis2.CID = fd2.CID AND        cis2.FORMULA_ID = fd2.FORMULA_ID");
	      exportSQL.append("             WHERE");
	      exportSQL.append("              fr_month.FORMULA_ID = cis2.FORMULA_ID  AND cis2.OPTIONAL_ITEM_NO = cis.OPTIONAL_ITEM_NO");
	      exportSQL.append("             ORDER BY");
	      exportSQL.append("              fd2.DISPORDER, mai.DISP_NO FOR XML PATH ('')");
	      exportSQL.append("              ),");
	      exportSQL.append("             1,");
	      exportSQL.append("             1,");
	      exportSQL.append("             ''");
	      exportSQL.append("            ))) , NULL) AS ATTENDANCE_ITEM_NAME");
	      exportSQL.append("         ,");
	      exportSQL.append("         IIF(oif.CALC_ATR = 1 ,");
	      exportSQL.append("         (CASE fs.LEFT_SET_METHOD ");
	      exportSQL.append("        WHEN 1 THEN");
	      exportSQL.append("        CONVERT ( VARCHAR, fs.LEFT_INPUT_VAL )");
	      exportSQL.append("        WHEN 0 THEN");
	      exportSQL.append("         CONVERT (VARCHAR,");
	      exportSQL.append("         ( SELECT koiff.SYMBOL FROM KRCMT_OPT_ITEM_FORMULA koiff WHERE koiff.OPTIONAL_ITEM_NO = oi.OPTIONAL_ITEM_NO AND fs.LEFT_FORMULA_ITEM_ID = koiff.FORMULA_ID)) ");
	      exportSQL.append("       END)");
	      exportSQL.append("       +");
	      exportSQL.append("       CASE");
	      exportSQL.append("          fs.OPERATOR ");
	      exportSQL.append("         WHEN 0 THEN");
	      exportSQL.append("         ' + ' ");
	      exportSQL.append("         WHEN 1 THEN");
	      exportSQL.append("         ' - ' ");
	      exportSQL.append("         WHEN 2 THEN");
	      exportSQL.append("         ' * ' ");
	      exportSQL.append("         WHEN 3 THEN");
	      exportSQL.append("         ' / ' ");
	      exportSQL.append("        END ");
	      exportSQL.append("       +");
	      exportSQL.append("       (CASE fs.RIGHT_SET_METHOD ");
	      exportSQL.append("        WHEN 1 THEN");
	      exportSQL.append("        CONVERT ( VARCHAR, fs.RIGHT_INPUT_VAL )");
	      exportSQL.append("        WHEN 0 THEN");
	      exportSQL.append("         CONVERT (VARCHAR,");
	      exportSQL.append("         ( SELECT koiff.SYMBOL FROM KRCMT_OPT_ITEM_FORMULA koiff WHERE koiff.OPTIONAL_ITEM_NO = oi.OPTIONAL_ITEM_NO AND fs.RIGHT_FORMULA_ITEM_ID = koiff.FORMULA_ID)) ");
	      exportSQL.append("       END)");
	      exportSQL.append("          , NULL) AS ATTENDANCE_ITEM_2,");
	      exportSQL.append("            fs.MINUS_SEGMENT as  FORMULAR_FROM_FORMULAR,");
	      exportSQL.append("            cis.MINUS_SEGMENT as FORMULAR");
	      exportSQL.append("         FROM");
	      exportSQL.append("          KRCMT_ANYV oi");
	      exportSQL.append("          LEFT JOIN KRCMT_CALC_RESULT_RANGE crr ON oi.CID = crr.CID AND oi.OPTIONAL_ITEM_NO = crr.OPTIONAL_ITEM_NO");
	      exportSQL.append("          LEFT JOIN KRCMT_FORMULA_ROUNDING fr_day ON oi.CID = fr_day.CID AND oi.OPTIONAL_ITEM_NO = fr_day.OPTIONAL_ITEM_NO AND fr_day.ROUNDING_ATR = 1");
	      exportSQL.append("          LEFT JOIN KRCMT_FORMULA_ROUNDING fr_month ON oi.CID = fr_month.CID AND oi.OPTIONAL_ITEM_NO = fr_month.OPTIONAL_ITEM_NO AND fr_month.ROUNDING_ATR = 2 AND fr_day.FORMULA_ID = fr_month.FORMULA_ID");
	      exportSQL.append("          LEFT JOIN KRCMT_OPT_ITEM_FORMULA oif ON oi.CID = oif.CID AND oi.OPTIONAL_ITEM_NO = oif.OPTIONAL_ITEM_NO AND fr_day.FORMULA_ID = oif.FORMULA_ID");
	      exportSQL.append("          LEFT JOIN KRCST_FORMULA_DISPORDER fd ON oif.OPTIONAL_ITEM_NO = fd.OPTIONAL_ITEM_NO AND oif.CID = fd.CID AND oif.FORMULA_ID = fd.FORMULA_ID");
	      exportSQL.append("          LEFT JOIN KRCMT_CALC_ITEM_SELECTION cis ON fd.FORMULA_ID = cis.FORMULA_ID AND fd.OPTIONAL_ITEM_NO = cis.OPTIONAL_ITEM_NO AND cis.CID = fd.CID");
	      exportSQL.append("          LEFT JOIN KRCMT_FORMULA_SETTING fs ON fd.FORMULA_ID = fs.FORMULA_ID  AND fd.OPTIONAL_ITEM_NO = fs.OPTIONAL_ITEM_NO");
	      exportSQL.append("         WHERE");
	      exportSQL.append("          oi.CID = ?companyId ");
	      exportSQL.append("          ) AS RESULT_END");
	      exportSQL.append("         GROUP BY OPTIONAL_ITEM_NO, SYMBOL, FORMULA_ATR, FORMULA_NAME, CALC_ATR, ATTENDANCE_ITEM_NAME, ATTENDANCE_ITEM_2, UPPER_LIMIT_ATR, UPPER_RANGE_DAY, UPPER_RANGE_MON, LOWER_LIMIT_ATR, LOWER_RANGE_DAY, LOWER_RANGE_MON, ITEM_DESCRIP, ITEM_NOTE, DAY_ROUNDING_UNIT, DAY_ROUNDING, MON_ROUNDING_UNIT, MON_ROUNDING, UPPER_LIMIT_ATR, CALC_ATR_2, DISPORDER,FORMULAR_FROM_FORMULAR,FORMULAR) AS RESULT_RIGHT");
	      exportSQL.append("         ON RESULT_LEFT.OPTIONAL_ITEM_NO = RESULT_RIGHT.OPTIONAL_ITEM_NO ) AS RESULT_TOTAL");
	      exportSQL.append("         )AS RESULT_FINAL  ORDER BY RESULT_FINAL.OPTIONAL_ITEM_NO ASC");

	     GET_EXPORT_EXCEL_ONE = exportSQL.toString();
	  }
	@Override
	public List<MasterData> getDataTableExport(String companyId, String langId) {
		List<MasterData> datas = new ArrayList<>();
		Query query = null;
		if(langId.equals(LANG_JA)) {
		   query = entityManager.createNativeQuery(GET_EXPORT_EXCEL_ONE.toString()).setParameter("companyId",
				companyId);
		}else {
			query = entityManager.createNativeQuery(CalFormulasItemLang.GET_EXPORT_EXCEL_LANG.toString()).setParameter("companyId",
						companyId).setParameter("langId", langId);
		}
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
			Integer optionalItemAtr = null;
			Integer optionalItemUse = null;
			for (Object[] objects : data) {
				if (!Objects.isNull(objects[3])) {
					optionalItemAtr = ((BigDecimal) objects[3]).intValue();
				}
				if (!Objects.isNull(objects[5])) {
					optionalItemUse = ((BigDecimal) objects[5]).intValue();
				}
				if(ObjectUtils.anyNotNull(objects) == true && optionalItemUse == 1){
					datas.add(dataContentTable(objects, optionalItemAtr, langId));
				}
			}
			return datas;
	}

	private MasterData dataContentTable(Object[] object, Integer optionalItemAtr, String langId) {
		Map<String,MasterCellData> data = new HashMap<>();
		// A6_1
		data.put(CalFormulasItemColumn.KMK002_76, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_76)
                .value(object[0] != null ? (BigDecimal) object[0] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		// A6_2
            data.put(CalFormulasItemColumn.KMK002_77, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_77)
                .value(object[1] != null ? (String) object[1] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_25
            data.put(CalFormulasItemColumn.KMK002_110, MasterCellData.builder()
                    .columnId(CalFormulasItemColumn.KMK002_110)
                    .value(object[5] != null ? ((BigDecimal) object[5]).intValue() == 1 ? TextResource.localize("KMK002_103") : TextResource.localize("KMK002_104") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            // A6_3
            data.put(CalFormulasItemColumn.KMK002_78, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_78)
                .value(object[3] != null ? EnumAdaptor.valueOf(((BigDecimal) object[3]).intValue(), OptionalItemAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_4
            data.put(CalFormulasItemColumn.KMK002_79, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_79)
                .value(object[4] != null ? (String) object[4] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_5
            data.put(CalFormulasItemColumn.KMK002_80, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_80)
                .value(object[2] != null ? ((BigDecimal) object[2]).intValue() == 1 ? TextResource.localize("KMK002_14") : TextResource.localize("KMK002_15") : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_6
            data.put(CalFormulasItemColumn.KMK002_81, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_81)
                .value(object[6] != null ? EnumAdaptor.valueOf(((BigDecimal) object[6]).intValue(), PerformanceAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_7
            data.put(CalFormulasItemColumn.KMK002_82, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_82)
                .value(object[7] != null ? EnumAdaptor.valueOf(((BigDecimal) object[7]).intValue(), EmpConditionAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_8
            data.put(CalFormulasItemColumn.KMK002_83, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_83)
                .value(object[8] != null ? (String) object[8] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_9
            data.put(CalFormulasItemColumn.KMK002_84, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_84)
                .value(object[9] != null ? ((BigDecimal) object[9]).intValue() == 1 ? "○" : "-" : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_10
            // A6_26
         // Upper value day
    		switch (optionalItemAtr) {
    			case 0:
    				data.put(CalFormulasItemColumn.KMK002_85, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_85)
    		                .value(object[10] != null ? formatTime(((BigDecimal) object[10]).intValue()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				data.put(CalFormulasItemColumn.KMK002_111, MasterCellData.builder()
                            .columnId(CalFormulasItemColumn.KMK002_111)
                            .value(object[11] != null ? formatTime(((BigDecimal) object[11]).intValue()) : "")
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                            .build());
    				break;
    			case 1:
    				data.put(CalFormulasItemColumn.KMK002_85, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_85)
    		                .value(object[10] != null ? formatNumber(((BigDecimal) object[10]).toString()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				data.put(CalFormulasItemColumn.KMK002_111, MasterCellData.builder()
                            .columnId(CalFormulasItemColumn.KMK002_111)
                            .value(object[11] != null ? formatNumber(((BigDecimal) object[11]).toString()) : "")
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                            .build());
    				break;
    			case 2:
    				data.put(CalFormulasItemColumn.KMK002_85, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_85)
    		                .value(object[10] != null ? formatAmount(((BigDecimal) object[10]).toString()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				data.put(CalFormulasItemColumn.KMK002_111, MasterCellData.builder()
                            .columnId(CalFormulasItemColumn.KMK002_111)
                            .value(object[11] != null ? formatAmount(((BigDecimal) object[11]).toString()) : "")
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                            .build());
    				break;
    		}
    		
            // A6_11
            data.put(CalFormulasItemColumn.KMK002_86, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_86)
                .value(object[12] != null ? ((BigDecimal) object[12]).intValue() == 1 ? "○" : "-" : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_12
            // A6_27
         // Lower value
    		switch (optionalItemAtr) {
    			case 0:
    				data.put(CalFormulasItemColumn.KMK002_87, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_87)
    		                .value(object[13] != null ? formatTime(((BigDecimal) object[13]).intValue()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				data.put(CalFormulasItemColumn.KMK002_112, MasterCellData.builder()
                            .columnId(CalFormulasItemColumn.KMK002_112)
                            .value(object[14] != null ? formatTime(((BigDecimal) object[14]).intValue()) : "")
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                            .build());
    				break;
    			case 1:
    				data.put(CalFormulasItemColumn.KMK002_87, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_87)
    		                .value(object[13] != null ? formatNumber(((BigDecimal) object[13]).toString()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				data.put(CalFormulasItemColumn.KMK002_112, MasterCellData.builder()
                            .columnId(CalFormulasItemColumn.KMK002_112)
                            .value(object[14] != null ? formatNumber(((BigDecimal) object[14]).toString()) : "")
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                            .build());
    				break;
    			case 2:
    				data.put(CalFormulasItemColumn.KMK002_87, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_87)
    		                .value(object[13] != null ? formatAmount(((BigDecimal) object[13]).toString()) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    		                .build());
    				data.put(CalFormulasItemColumn.KMK002_112, MasterCellData.builder()
                            .columnId(CalFormulasItemColumn.KMK002_112)
                            .value(object[14] != null ? formatAmount(((BigDecimal) object[14]).toString()) : "")
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                            .build());
    				break;
    		}
    		
    		// A6_28
    		data.put(CalFormulasItemColumn.KMK002_113, MasterCellData.builder()
                    .columnId(CalFormulasItemColumn.KMK002_113)
                    .value(object[15] != null ? (String) object[15] : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
    		
    		// A6_29
    		data.put(CalFormulasItemColumn.KMK002_114, MasterCellData.builder()
                    .columnId(CalFormulasItemColumn.KMK002_114)
                    .value(object[16] != null ? (String) object[16] : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            
    		// A6_13
            data.put(CalFormulasItemColumn.KMK002_88, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_88)
                .value(object[17] != null ? (String) object[17] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_14
            data.put(CalFormulasItemColumn.KMK002_89, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_89)
                .value(object[18] != null ? EnumAdaptor.valueOf(((BigDecimal) object[18]).intValue(), OptionalItemAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_15
            data.put(CalFormulasItemColumn.KMK002_90, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_90)
                .value(object[19] != null ? (String) object[19] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            // A6_16
            data.put(CalFormulasItemColumn.KMK002_91, MasterCellData.builder()
                .columnId(CalFormulasItemColumn.KMK002_91)
                .value(object[20] != null ? EnumAdaptor.valueOf(((BigDecimal) object[20]).intValue(), CalculationAtr.class).description : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

            switch (optionalItemAtr) {
				case 0:
					data.put(CalFormulasItemColumn.KMK002_93, MasterCellData.builder()
			                .columnId(CalFormulasItemColumn.KMK002_93)
			                .value(object[22] != null ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) object[22]).intValue(), Rounding.class).nameId) : "")
			                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
			                .build());
					break;
				case 1:
					data.put(CalFormulasItemColumn.KMK002_93, MasterCellData.builder()
			                .columnId(CalFormulasItemColumn.KMK002_93)
			                .value(object[22] != null ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[22]).intValue(), NumberRounding.class).nameId) : "")
			                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
			                .build());
					break;
				case 2:
					data.put(CalFormulasItemColumn.KMK002_93, MasterCellData.builder()
			                .columnId(CalFormulasItemColumn.KMK002_93)
			                .value(object[22] != null ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[22]).intValue(), AmountRounding.class).nameId) : "")
			                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
			                .build());
					break;
            }
            
         // A7_18
    		switch (optionalItemAtr) {
    			case 0:
    				data.put(CalFormulasItemColumn.KMK002_92, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_92)
    		                .value(object[21] != null ? TextResource .localize(EnumAdaptor.valueOf(((BigDecimal) object[21]).intValue(), Unit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 1:
    				data.put(CalFormulasItemColumn.KMK002_92, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_92)
    		                .value(object[21] != null ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[21]).intValue(), NumberUnit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 2:
    				data.put(CalFormulasItemColumn.KMK002_92, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_92)
    		                .value(object[21] != null ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[21]).intValue(), AmountUnit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    		}
            
    		// A7_19
    		switch (optionalItemAtr) {
    			case 0:
    				data.put(CalFormulasItemColumn.KMK002_95, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_95)
    		                .value(object[24] != null ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[24]).intValue(), Rounding.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 1:
    				data.put(CalFormulasItemColumn.KMK002_95, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_95)
    		                .value(object[24] != null ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[24]).intValue(), NumberRounding.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 2:
    				data.put(CalFormulasItemColumn.KMK002_95, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_95)
    		                .value(object[24] != null ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[24]).intValue(), AmountRounding.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    		}
            
    		// A7_20
    		switch (optionalItemAtr) {
    			case 0:
    				data.put(CalFormulasItemColumn.KMK002_94, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_94)
    		                .value(object[24] != null ? TextResource .localize(EnumAdaptor.valueOf(((BigDecimal) object[23]).intValue(), Unit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 1:
    				data.put(CalFormulasItemColumn.KMK002_94, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_94)
    		                .value(object[24] != null ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[23]).intValue(), NumberUnit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    			case 2:
    				data.put(CalFormulasItemColumn.KMK002_94, MasterCellData.builder()
    		                .columnId(CalFormulasItemColumn.KMK002_94)
    		                .value(object[24] != null ? TextResource.localize( EnumAdaptor.valueOf(((BigDecimal) object[23]).intValue(), AmountUnit.class).nameId) : "")
    		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    		                .build());
    				break;
    		}
            
            
            
            
		if (object[25] != null) {
			switch (((BigDecimal) object[25]).intValue()) {
			case 0:
				String formularItem = Objects.toString(object[26], "");
				String value = 	formularItem.startsWith(CalFormulasItemColumn.add_Operator) && formularItem.length() > 2 ? 
								formularItem.substring(2, formularItem.length()): formularItem;
								
//				String valueTest1 = MessageFormat.format(value,'1');
				data.put(CalFormulasItemColumn.KMK002_97,
						MasterCellData.builder().columnId(CalFormulasItemColumn.KMK002_97)
								.value(formatName(value))
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				
				// Minus 
				data.put(CalFormulasItemColumn.KMK002_96, MasterCellData.builder()
		                .columnId(CalFormulasItemColumn.KMK002_96)
		                .value(object[29] != null ? ((BigDecimal) object[29]).intValue() == 1 ? "○" : "-" : "")
		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
		                .build());
				break;
			case 1:
				data.put(CalFormulasItemColumn.KMK002_97,
						MasterCellData.builder().columnId(CalFormulasItemColumn.KMK002_97)
								.value(object[27] != null ? ((String) object[27]) : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				
				// Minus 
				data.put(CalFormulasItemColumn.KMK002_96, MasterCellData.builder()
		                .columnId(CalFormulasItemColumn.KMK002_96)
		                .value(object[28] != null ? ((BigDecimal) object[28]).intValue() == 1 ? "○" : "-" : "")
		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
		                .build());
				break;
			}
			
			
		} else {
			data.put(CalFormulasItemColumn.KMK002_96, MasterCellData.builder()
	                .columnId(CalFormulasItemColumn.KMK002_96)
	                .value("")
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	                .build());
			data.put(CalFormulasItemColumn.KMK002_97, MasterCellData.builder().columnId(CalFormulasItemColumn.KMK002_97)
					.value("").style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}
		
//		if(!langId.equals(LANG_JA)) {
//		    data.put(CalFormulasItemColumn.KMK002_100, MasterCellData.builder()
//	                .columnId(CalFormulasItemColumn.KMK002_100)
//	                .value(object[30] != null ? object[30].toString(): "")
//	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
//	                .build());
//		}else {
//			data.put(CalFormulasItemColumn.KMK002_100, MasterCellData.builder()
//	                .columnId(CalFormulasItemColumn.KMK002_100)
//	                .value("")
//	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
//	                .build());
//		}
		return MasterData.builder().rowData(data).build();
	}

	private String formatName(String name) {
		while (name.indexOf("{#") >= 0) {
			int startLocation = name.indexOf("{");
			int endLocation = name.indexOf("}");
			name = name.replace(name.substring(startLocation, endLocation + 1),
					TextResource.localize(name.substring(startLocation + 2, endLocation)));
		}
		return name;
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