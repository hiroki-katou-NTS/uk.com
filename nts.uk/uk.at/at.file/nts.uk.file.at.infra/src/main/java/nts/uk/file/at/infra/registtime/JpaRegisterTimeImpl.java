package nts.uk.file.at.infra.registtime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateType;
import nts.uk.ctx.at.record.dom.standardtime.enums.StartingMonthType;
import nts.uk.ctx.at.record.dom.standardtime.enums.TargetSettingAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.TimeOverLimitType;
import nts.uk.file.at.app.export.regisagreetime.RegistTimeColumn;
import nts.uk.file.at.app.export.regisagreetime.RegistTimeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaRegisterTimeImpl implements RegistTimeRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final String SQL_EXPORT_SHEET_1 = "SELECT "
			+ "STARTING_MONTH_TYPE,"
			+ "CLOSING_DATE_ATR,"
			+ "CLOSING_DATE_TYPE,"
			+ "NUMBER_TIMES_OVER_LIMIT_TYPE,"
			+ "ALARM_LIST_ATR,"
			+ "YEARLY_WORK_TABLE_ATR "
			+ "FROM "
			+ "KMKMT_AGREEMENT_OPE_SET "
			+ "WHERE CID = ?1";
	
	private static final String SQL_EXPORT_SHEET_2 = "SELECT aa.ERROR_WEEK,aa.ALARM_WEEK,aa.LIMIT_WEEK, "
			+ "aa.ERROR_TWO_WEEKS,aa.ALARM_TWO_WEEKS,aa.LIMIT_TWO_WEEKS, "
			+ "aa.ERROR_FOUR_WEEKS,aa.ALARM_FOUR_WEEKS,aa.LIMIT_FOUR_WEEKS, "
			+ "aa.ERROR_ONE_MONTH,aa.ALARM_ONE_MONTH,aa.LIMIT_ONE_MONTH, "
			+ "aa.ERROR_TWO_MONTH,aa.ALARM_TWO_MONTH,aa.LIMIT_TWO_MONTH, "
			+ "aa.ERROR_THREE_MONTH,aa.ALARM_THREE_MONTH,aa.LIMIT_THREE_MONTH, "
			+ "aa.ERROR_YEARLY,aa.ALARM_YEARLY,aa.LIMIT_YEARLY "
			+ "FROM  "
			+ "KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN  "
			+ "KMKMT_AGREEMENTTIME_COM bb "
			+ "ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?1 and bb.LABOR_SYSTEM_ATR = 0";
	
	private static final String SQL_EXPORT_SHEET_3 = "SELECT "
			+ "kk.CODE,"
			+ "kk.NAME,"
			+ "aa.ERROR_WEEK,"
			+ "aa.ALARM_WEEK,"
			+ "LIMIT_WEEK = ("
			+ "SELECT aa.LIMIT_WEEK "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "),"
			+ "aa.ERROR_TWO_WEEKS, "
			+ "aa.ALARM_TWO_WEEKS, "
			+ "LIMIT_TWO_WEEKS = ( "
			+ "SELECT aa.LIMIT_TWO_WEEKS "
			+ "FROM "
			+ "KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+ "),"
			+ "aa.ERROR_FOUR_WEEKS, "
			+ "aa.ALARM_FOUR_WEEKS, "
			+ "LIMIT_FOUR_WEEKS = ( "
			+ "SELECT aa.LIMIT_FOUR_WEEKS "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "),"
			+ "aa.ERROR_ONE_MONTH, "
			+ "aa.ALARM_ONE_MONTH, "
			+ "LIMIT_ONE_MONTH = ( "
			+ "SELECT aa.LIMIT_ONE_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "),"
			+ "aa.ERROR_TWO_MONTH, "
			+ "aa.ALARM_TWO_MONTH, "
			+ "LIMIT_TWO_MONTH = ( "
			+ "SELECT aa.LIMIT_TWO_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "),"
			+ "aa.ERROR_THREE_MONTH, "
			+ "aa.ALARM_THREE_MONTH, "
			+ "LIMIT_THREE_MONTH = ("
			+ "SELECT aa.LIMIT_THREE_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE "
			+ "bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "),"
			+ "aa.ERROR_YEARLY, "
			+ "aa.ALARM_YEARLY,"
			+ "LIMIT_YEARLY = ("
			+ "SELECT aa.LIMIT_YEARLY "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ ")"
			+ "FROM "
			+ "KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_EMP bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "JOIN BSYMT_EMPLOYMENT kk ON "
			+ "kk.CODE = bb.EMP_CTG_CODE "
			+ "WHERE "
			+ "	bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "AND kk.CID = ?cid";
	
	private static final String SQL_EXPORT_SHEET_4 = " WITH summary AS ("
			+ " SELECT "
			+ " 	kk.WKPCD,"
			+ " 	kk.WKP_NAME,"
			+ " 	ROW_NUMBER() OVER("
			+ " 	PARTITION BY kk.WKPCD"
			+ " 	ORDER BY ee.END_DATE DESC) AS rk,"
			+ " 	aa.ERROR_WEEK,"
			+ " 	aa.ALARM_WEEK,"
			+ " 	LIMIT_WEEK = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_WEEK"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+ " 	),"
			+ " 	aa.ERROR_TWO_WEEKS,"
			+ " 	aa.ALARM_TWO_WEEKS,"
			+ " 	LIMIT_TWO_WEEKS = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_TWO_WEEKS"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+ " 	),"
			+ " 	aa.ERROR_FOUR_WEEKS,"
			+ " 	aa.ALARM_FOUR_WEEKS,"
			+ " 	LIMIT_FOUR_WEEKS = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_FOUR_WEEKS"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+ " 	),"
			+ " 	aa.ERROR_ONE_MONTH,"
			+ " 	aa.ALARM_ONE_MONTH,"
			+ " 	LIMIT_ONE_MONTH = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_ONE_MONTH"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+ " 	),"
			+ " 	aa.ERROR_TWO_MONTH,"
			+ " 	aa.ALARM_TWO_MONTH,"
			+ " 	LIMIT_TWO_MONTH = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_TWO_MONTH"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+ " 	),"
			+ " 	aa.ERROR_THREE_MONTH,"
			+ " 	aa.ALARM_THREE_MONTH,"
			+ " 	LIMIT_THREE_MONTH = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_THREE_MONTH"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+ " 	),"
			+ " 	aa.ERROR_YEARLY,"
			+ " 	aa.ALARM_YEARLY,"
			+ " 	LIMIT_YEARLY = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_YEARLY"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+ " 	),"
			+ " qq.HIERARCHY_CD"
			+ " FROM"
			+ " 	KMKMT_BASIC_AGREEMENT_SET aa"
			+ " JOIN KMKMT_AGREEMENTTIME_WPL bb "
			+ " ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " JOIN BSYMT_WORKPLACE_INFO kk "
			+ " ON bb.WKPCD = kk.WKPID"
			+ " JOIN BSYMT_WORKPLACE_HIST ee "
			+ " ON kk.HIST_ID = ee.HIST_ID "
			+ " JOIN BSYMT_WKP_CONFIG hh "
			+ "	ON hh.CID = ee.CID AND hh.END_DATE = '9999-12-31 00:00:00' "
			+ " JOIN BSYMT_WKP_CONFIG_INFO qq "
			+ " ON qq.WKPID = kk.WKPID AND hh.HIST_ID = qq.HIST_ID "
			+ " WHERE"
			+ " 	 bb.LABOR_SYSTEM_ATR = 0 AND kk.CID = ?cid"
			+ " )"
			+ " SELECT s.WKPCD,s.WKP_NAME,s.ERROR_WEEK,s.ALARM_WEEK,s.LIMIT_WEEK,"
			+ " 			 s.ERROR_TWO_WEEKS,s.ALARM_TWO_WEEKS,s.LIMIT_TWO_WEEKS,"
			+ " 			 s.ERROR_FOUR_WEEKS,s.ALARM_FOUR_WEEKS,s.LIMIT_FOUR_WEEKS,"
			+ " 			 s.ERROR_ONE_MONTH,s.ALARM_ONE_MONTH,s.LIMIT_ONE_MONTH,"
			+ " 			 s.ERROR_TWO_MONTH,s.ALARM_TWO_MONTH,s.LIMIT_TWO_MONTH,"
			+ " 			 s.ERROR_THREE_MONTH,s.ALARM_THREE_MONTH,s.LIMIT_THREE_MONTH,"
			+ " 			 s.ERROR_YEARLY,s.ALARM_YEARLY,s.LIMIT_YEARLY"
			+ "   FROM summary s"
			+ "  WHERE s.rk = 1 "
			+ "	 ORDER BY s.HIERARCHY_CD";

	
	
	private static final String SQL_EXPORT_SHEET_5 = "SELECT "
			+ "kk.CLSCD,"
			+ "kk.CLSNAME,"
			+ "aa.ERROR_WEEK,"
			+ "aa.ALARM_WEEK,"
			+ "LIMIT_WEEK = ("
			+ "SELECT aa.LIMIT_WEEK "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "),"
			+ "aa.ERROR_TWO_WEEKS, "
			+ "aa.ALARM_TWO_WEEKS, "
			+ "LIMIT_TWO_WEEKS = ( "
			+ "SELECT aa.LIMIT_TWO_WEEKS "
			+ "FROM "
			+ "KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+ "),"
			+ "aa.ERROR_FOUR_WEEKS, "
			+ "aa.ALARM_FOUR_WEEKS, "
			+ "LIMIT_FOUR_WEEKS = ( "
			+ "SELECT aa.LIMIT_FOUR_WEEKS "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "),"
			+ "aa.ERROR_ONE_MONTH, "
			+ "aa.ALARM_ONE_MONTH, "
			+ "LIMIT_ONE_MONTH = ( "
			+ "SELECT aa.LIMIT_ONE_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "),"
			+ "aa.ERROR_TWO_MONTH, "
			+ "aa.ALARM_TWO_MONTH, "
			+ "LIMIT_TWO_MONTH = ( "
			+ "SELECT aa.LIMIT_TWO_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "),"
			+ "aa.ERROR_THREE_MONTH, "
			+ "aa.ALARM_THREE_MONTH, "
			+ "LIMIT_THREE_MONTH = ("
			+ "SELECT aa.LIMIT_THREE_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE "
			+ "bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "),"
			+ "aa.ERROR_YEARLY, "
			+ "aa.ALARM_YEARLY,"
			+ "LIMIT_YEARLY = ("
			+ "SELECT aa.LIMIT_YEARLY "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0 "
			+ ")"
			+ "FROM "
			+ "KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_CLASS bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "JOIN BSYMT_CLASSIFICATION kk ON "
			+ " bb.CLSCD = kk.CLSCD AND bb.LABOR_SYSTEM_ATR = 0 "
			+ "WHERE "
			+ "	bb.CID = ?cid "
			+ "AND kk.CID = ?cid";
	
	private static final String SQL_EXPORT_SHEET_6 = "SELECT aa.ERROR_WEEK,aa.ALARM_WEEK,aa.LIMIT_WEEK, "
			+ "aa.ERROR_TWO_WEEKS,aa.ALARM_TWO_WEEKS,aa.LIMIT_TWO_WEEKS, "
			+ "aa.ERROR_FOUR_WEEKS,aa.ALARM_FOUR_WEEKS,aa.LIMIT_FOUR_WEEKS, "
			+ "aa.ERROR_ONE_MONTH,aa.ALARM_ONE_MONTH,aa.LIMIT_ONE_MONTH, "
			+ "aa.ERROR_TWO_MONTH,aa.ALARM_TWO_MONTH,aa.LIMIT_TWO_MONTH, "
			+ "aa.ERROR_THREE_MONTH,aa.ALARM_THREE_MONTH,aa.LIMIT_THREE_MONTH, "
			+ "aa.ERROR_YEARLY,aa.ALARM_YEARLY,aa.LIMIT_YEARLY "
			+ "FROM  "
			+ "KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN  "
			+ "KMKMT_AGREEMENTTIME_COM bb "
			+ "ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?1 and bb.LABOR_SYSTEM_ATR = 1";
	
	
	private static final String SQL_EXPORT_SHEET_7 = "SELECT "
			+ "kk.CODE,"
			+ "kk.NAME,"
			+ "aa.ERROR_WEEK,"
			+ "aa.ALARM_WEEK,"
			+ "LIMIT_WEEK = ("
			+ "SELECT aa.LIMIT_WEEK "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_TWO_WEEKS, "
			+ "aa.ALARM_TWO_WEEKS, "
			+ "LIMIT_TWO_WEEKS = ( "
			+ "SELECT aa.LIMIT_TWO_WEEKS "
			+ "FROM "
			+ "KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid  AND bb.LABOR_SYSTEM_ATR = 1"
			+ "),"
			+ "aa.ERROR_FOUR_WEEKS, "
			+ "aa.ALARM_FOUR_WEEKS, "
			+ "LIMIT_FOUR_WEEKS = ( "
			+ "SELECT aa.LIMIT_FOUR_WEEKS "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_ONE_MONTH, "
			+ "aa.ALARM_ONE_MONTH, "
			+ "LIMIT_ONE_MONTH = ( "
			+ "SELECT aa.LIMIT_ONE_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_TWO_MONTH, "
			+ "aa.ALARM_TWO_MONTH, "
			+ "LIMIT_TWO_MONTH = ( "
			+ "SELECT aa.LIMIT_TWO_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_THREE_MONTH, "
			+ "aa.ALARM_THREE_MONTH, "
			+ "LIMIT_THREE_MONTH = ("
			+ "SELECT aa.LIMIT_THREE_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE "
			+ "bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_YEARLY, "
			+ "aa.ALARM_YEARLY,"
			+ "LIMIT_YEARLY = ("
			+ "SELECT aa.LIMIT_YEARLY "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ ")"
			+ "FROM "
			+ "KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_EMP bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "JOIN BSYMT_EMPLOYMENT kk ON "
			+ "kk.CODE = bb.EMP_CTG_CODE "
			+ "WHERE "
			+ "	bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "AND kk.CID = ?cid";
	
	
	private static final String SQL_EXPORT_SHEET_8 =  " WITH summary AS ("
			+ " SELECT "
			+ " 	kk.WKPCD,"
			+ " 	kk.WKP_NAME,"
			+ " 	ROW_NUMBER() OVER("
			+ " 	PARTITION BY kk.WKPCD"
			+ " 	ORDER BY ee.END_DATE DESC) AS rk,"
			+ " 	aa.ERROR_WEEK,"
			+ " 	aa.ALARM_WEEK,"
			+ " 	LIMIT_WEEK = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_WEEK"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+ " 	),"
			+ " 	aa.ERROR_TWO_WEEKS,"
			+ " 	aa.ALARM_TWO_WEEKS,"
			+ " 	LIMIT_TWO_WEEKS = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_TWO_WEEKS"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+ " 	),"
			+ " 	aa.ERROR_FOUR_WEEKS,"
			+ " 	aa.ALARM_FOUR_WEEKS,"
			+ " 	LIMIT_FOUR_WEEKS = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_FOUR_WEEKS"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+ " 	),"
			+ " 	aa.ERROR_ONE_MONTH,"
			+ " 	aa.ALARM_ONE_MONTH,"
			+ " 	LIMIT_ONE_MONTH = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_ONE_MONTH"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+ " 	),"
			+ " 	aa.ERROR_TWO_MONTH,"
			+ " 	aa.ALARM_TWO_MONTH,"
			+ " 	LIMIT_TWO_MONTH = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_TWO_MONTH"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+ " 	),"
			+ " 	aa.ERROR_THREE_MONTH,"
			+ " 	aa.ALARM_THREE_MONTH,"
			+ " 	LIMIT_THREE_MONTH = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_THREE_MONTH"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+ " 	),"
			+ " 	aa.ERROR_YEARLY,"
			+ " 	aa.ALARM_YEARLY,"
			+ " 	LIMIT_YEARLY = ("
			+ " 		SELECT"
			+ " 			aa.LIMIT_YEARLY"
			+ " 		FROM"
			+ " 			KMKMT_BASIC_AGREEMENT_SET aa"
			+ " 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " 		WHERE"
			+ " 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+ " 	), "
			+ "		qq.HIERARCHY_CD"
			+ " FROM"
			+ " 	KMKMT_BASIC_AGREEMENT_SET aa"
			+ " JOIN KMKMT_AGREEMENTTIME_WPL bb "
			+ " ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+ " JOIN BSYMT_WORKPLACE_INFO kk "
			+ " ON bb.WKPCD = kk.WKPID"
			+ " JOIN BSYMT_WORKPLACE_HIST ee "
			+ " ON kk.HIST_ID = ee.HIST_ID "
			+ "	JOIN BSYMT_WKP_CONFIG hh "
			+ "	ON hh.CID = ee.CID AND hh.END_DATE = '9999-12-31 00:00:00' "
			+ "	JOIN BSYMT_WKP_CONFIG_INFO qq "
			+ " ON qq.WKPID = kk.WKPID AND hh.HIST_ID = qq.HIST_ID "
			+ " WHERE"
			+ " 	 bb.LABOR_SYSTEM_ATR = 1 AND kk.CID = ?cid"
			+ " )"
			+ " SELECT s.WKPCD,s.WKP_NAME,s.ERROR_WEEK,s.ALARM_WEEK,s.LIMIT_WEEK,"
			+ " 			 s.ERROR_TWO_WEEKS,s.ALARM_TWO_WEEKS,s.LIMIT_TWO_WEEKS,"
			+ " 			 s.ERROR_FOUR_WEEKS,s.ALARM_FOUR_WEEKS,s.LIMIT_FOUR_WEEKS,"
			+ " 			 s.ERROR_ONE_MONTH,s.ALARM_ONE_MONTH,s.LIMIT_ONE_MONTH,"
			+ " 			 s.ERROR_TWO_MONTH,s.ALARM_TWO_MONTH,s.LIMIT_TWO_MONTH,"
			+ " 			 s.ERROR_THREE_MONTH,s.ALARM_THREE_MONTH,s.LIMIT_THREE_MONTH,"
			+ " 			 s.ERROR_YEARLY,s.ALARM_YEARLY,s.LIMIT_YEARLY"
			+ "   FROM summary s"
			+ "  WHERE s.rk = 1 "
			+ "	 ORDER BY s.HIERARCHY_CD";

	
	
	private static final String SQL_EXPORT_SHEET_9 = "SELECT "
			+ "kk.CLSCD,"
			+ "kk.CLSNAME,"
			+ "aa.ERROR_WEEK,"
			+ "aa.ALARM_WEEK,"
			+ "LIMIT_WEEK = ("
			+ "SELECT aa.LIMIT_WEEK "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_TWO_WEEKS, "
			+ "aa.ALARM_TWO_WEEKS, "
			+ "LIMIT_TWO_WEEKS = ( "
			+ "SELECT aa.LIMIT_TWO_WEEKS "
			+ "FROM "
			+ "KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_FOUR_WEEKS, "
			+ "aa.ALARM_FOUR_WEEKS, "
			+ "LIMIT_FOUR_WEEKS = ( "
			+ "SELECT aa.LIMIT_FOUR_WEEKS "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_ONE_MONTH, "
			+ "aa.ALARM_ONE_MONTH, "
			+ "LIMIT_ONE_MONTH = ( "
			+ "SELECT aa.LIMIT_ONE_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_TWO_MONTH, "
			+ "aa.ALARM_TWO_MONTH, "
			+ "LIMIT_TWO_MONTH = ( "
			+ "SELECT aa.LIMIT_TWO_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_THREE_MONTH, "
			+ "aa.ALARM_THREE_MONTH, "
			+ "LIMIT_THREE_MONTH = ("
			+ "SELECT aa.LIMIT_THREE_MONTH "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE "
			+ "bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "),"
			+ "aa.ERROR_YEARLY, "
			+ "aa.ALARM_YEARLY,"
			+ "LIMIT_YEARLY = ("
			+ "SELECT aa.LIMIT_YEARLY "
			+ "FROM KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "WHERE bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1 "
			+ ")"
			+ "FROM "
			+ "KMKMT_BASIC_AGREEMENT_SET aa "
			+ "JOIN KMKMT_AGREEMENTTIME_CLASS bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID "
			+ "JOIN BSYMT_CLASSIFICATION kk ON "
			+ " bb.CLSCD = kk.CLSCD AND bb.LABOR_SYSTEM_ATR = 1 "
			+ "WHERE "
			+ "	bb.CID = ?cid "
			+ "AND kk.CID = ?cid";
	
	private static final String SQL_EXPORT_SHEET_10 = " SELECT"
			+" 	CASE"
			+" WHEN kk.ROW_NUMBER1 = 1 THEN"
			+" 	kk.SCD"
			+" ELSE"
			+" 	NULL"
			+" END SCD,"
			+"  CASE"
			+" WHEN kk.ROW_NUMBER2 = 1 THEN"
			+" 	kk.BUSINESS_NAME"
			+" ELSE"
			+" 	NULL"
			+" END BUSINESS_NAME,"
			+"  CASE"
			+" WHEN kk.ROW_NUMBER3 = 1 THEN"
			+" 	kk.YM_K"
			+" ELSE"
			+" 	NULL"
			+" END YM_K,"
			+"  CASE"
			+" WHEN kk.ROW_NUMBER4 = 1 THEN"
			+" 	kk.ERROR_ONE_MONTH"
			+" ELSE"
			+" 	NULL"
			+" END ERROR_ONE_MONTH,"
			+"  CASE"
			+" WHEN kk.ROW_NUMBER5 = 1 THEN"
			+" 	kk.ALARM_ONE_MONTH"
			+" ELSE"
			+" 	NULL"
			+" END ALARM_ONE_MONTH,"
			+"  CASE"
			+" WHEN kk.ROW_NUMBER6 = 1 THEN"
			+" 	kk.Y_K"
			+" ELSE"
			+" 	NULL"
			+" END Y_K,"
			+"  CASE"
			+" WHEN kk.ROW_NUMBER7 = 1 THEN"
			+" 	kk.ERROR_YEARLY"
			+" ELSE"
			+" 	NULL"
			+" END ERROR_YEARLY,"
			+"  CASE"
			+" WHEN kk.ROW_NUMBER8 = 1 THEN"
			+" 	kk.ALARM_YEARLY"
			+" ELSE"
			+" 	NULL"
			+" END ALARM_YEARLY"
			+" FROM"
			+" 	("
			+" 		SELECT"
			+" 			cc.SCD,"
			+" 			dd.BUSINESS_NAME,"
			+" 			aa.YM_K,"
			+" 			aa.ERROR_ONE_MONTH,"
			+" 			aa.ALARM_ONE_MONTH,"
			+" 			bb.Y_K,"
			+" 			bb.ERROR_YEARLY,"
			+" 			bb.ALARM_YEARLY,"
			+" 			ROW_NUMBER () OVER ("
			+" 				PARTITION BY cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K ,"
			+" 					aa.ERROR_ONE_MONTH,"
			+" 					aa.ALARM_ONE_MONTH,"
			+" 					bb.Y_K ,"
			+" 					bb.ERROR_YEARLY,"
			+" 					bb.ALARM_YEARLY"
			+" 				ORDER BY"
			+" 					cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K DESC,"
			+" 					aa.ERROR_ONE_MONTH,"
			+" 					aa.ALARM_ONE_MONTH,"
			+" 					bb.Y_K DESC,"
			+" 					bb.ERROR_YEARLY,"
			+" 					bb.ALARM_YEARLY"
			+" 			) AS ROW_NUMBER8,"
			+" 			ROW_NUMBER () OVER ("
			+" 				PARTITION BY cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K ,"
			+" 					aa.ERROR_ONE_MONTH,"
			+" 					aa.ALARM_ONE_MONTH,"
			+" 					bb.Y_K ,"
			+" 					bb.ERROR_YEARLY"
			+" 				ORDER BY"
			+" 					cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K DESC,"
			+" 					aa.ERROR_ONE_MONTH,"
			+" 					aa.ALARM_ONE_MONTH,"
			+" 					bb.Y_K DESC,"
			+" 					bb.ERROR_YEARLY"
			+" 			) AS ROW_NUMBER7,"
			+" 			ROW_NUMBER () OVER ("
			+" 				PARTITION BY cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K ,"
			+" 					aa.ERROR_ONE_MONTH,"
			+" 					aa.ALARM_ONE_MONTH,"
			+" 					bb.Y_K"
			+" 				ORDER BY"
			+" 					cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K DESC,"
			+" 					aa.ERROR_ONE_MONTH,"
			+" 					aa.ALARM_ONE_MONTH,"
			+" 					bb.Y_K DESC"
			+" 			) AS ROW_NUMBER6,"
			+" 			ROW_NUMBER () OVER ("
			+" 				PARTITION BY cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K ,"
			+" 					aa.ERROR_ONE_MONTH,"
			+" 					aa.ALARM_ONE_MONTH"
			+" 				ORDER BY"
			+" 					cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K DESC,"
			+" 					aa.ERROR_ONE_MONTH,"
			+" 					aa.ALARM_ONE_MONTH,"
			+" 					bb.Y_K DESC"
			+" 			) AS ROW_NUMBER5,"
			+" 			ROW_NUMBER () OVER ("
			+" 				PARTITION BY cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K,"
			+" 					aa.ERROR_ONE_MONTH"
			+" 				ORDER BY"
			+" 					cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K DESC,"
			+" 					aa.ERROR_ONE_MONTH,"
			+" 					bb.Y_K DESC"
			+" 			) AS ROW_NUMBER4,"
			+" 			ROW_NUMBER () OVER ("
			+" 				PARTITION BY cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K"
			+" 				ORDER BY"
			+" 					cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K DESC,"
			+" 					bb.Y_K DESC"
			+" 			) AS ROW_NUMBER3,"
			+" 			ROW_NUMBER () OVER ("
			+" 				PARTITION BY cc.SCD,dd.BUSINESS_NAME"
			+" 				ORDER BY"
			+" 					cc.SCD,"
			+" 					dd.BUSINESS_NAME,"
			+" 					aa.YM_K DESC,bb.Y_K DESC"
			+" 			) AS ROW_NUMBER2,"
			+" 			ROW_NUMBER () OVER ("
			+" 				PARTITION BY cc.SCD"
			+" 				ORDER BY"
			+" 					cc.SCD,"
			+" 					aa.YM_K DESC,bb.Y_K DESC"
			+" 			) AS ROW_NUMBER1"
			+" 		FROM"
			+" 			KMKMT_AGREEMENT_MONTH_SET aa"
			+" 		JOIN KMKMT_AGREEMENT_YEAR_SET bb ON aa.SID = bb.SID"
			+" 		JOIN BSYMT_EMP_DTA_MNG_INFO cc ON aa.SID = cc.SID"
			+" 		JOIN BPSMT_PERSON dd ON cc.PID = dd.PID"
			+" 		WHERE"
			+" 			bb.Y_K >= ?startY"
			+" 		AND bb.Y_K <= ?endY"
			+" 		AND aa.YM_K >= ?startYM"
			+" 		AND aa.YM_K <= ?endYM"
			+" 		AND cc.CID = ?cid"
			+" 	) kk"
			+" ORDER BY"
			+" 	kk.SCD, kk.YM_K DESC,kk.Y_K DESC";
	
	
	@Override
	public List<MasterData> getDataExportSheet1() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_1.toString()).setParameter(1, cid);
		try {
			Object[] data = (Object[]) query.getSingleResult();
			int closeDateAtr = ((BigDecimal)data[1]).intValue();
			for (int i = 0; i < data.length; i++) {
				if(closeDateAtr == 0 && i == 2)
					continue;
				datas.add(toDataSheet1(data[i],i,closeDateAtr));
			}
		} catch (Exception e) {
			for (int i = 0; i < 5; i++) {
				datas.add(toDataEmptySheet1(i));
			}
			return datas;
		}
		return datas;
	}
	
	private MasterData toDataEmptySheet1(int checkRow) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_80, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_80)
                .value(checkRow == 0 ? RegistTimeColumn.KMK008_82 : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.HEADER_NONE1, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_80)
                .value(checkRow == 0 ? RegistTimeColumn.KMK008_83 : checkRow == 1 ? RegistTimeColumn.KMK008_84 : checkRow == 2 ? RegistTimeColumn.KMK008_85 : checkRow == 3 ? RegistTimeColumn.KMK008_86 : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.HEADER_NONE2, MasterCellData.builder()
                .columnId(RegistTimeColumn.HEADER_NONE2)
                .value(checkRow == 3 ? RegistTimeColumn.KMK008_87 : checkRow == 4 ? RegistTimeColumn.KMK008_88 : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_81, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_81)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		return MasterData.builder().rowData(data).build();
	}
	
	
	private MasterData toDataSheet1(Object object,int check,int closeDateAtr) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_80, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_80)
                .value(check == 0 ? RegistTimeColumn.KMK008_82 : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.HEADER_NONE1, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_80)
                .value(check == 0 ? RegistTimeColumn.KMK008_83 : check == 1 ? RegistTimeColumn.KMK008_84 : check == 3 ? RegistTimeColumn.KMK008_85 : check == 4 ? RegistTimeColumn.KMK008_86 : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.HEADER_NONE2, MasterCellData.builder()
                .columnId(RegistTimeColumn.HEADER_NONE2)
                .value(check == 4 ? RegistTimeColumn.KMK008_87 : check == 5 ? RegistTimeColumn.KMK008_88 : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_81, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_81)
                .value(getValue(((BigDecimal)object).intValue(),check,closeDateAtr))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		return MasterData.builder().rowData(data).build();
	}
	
	
	private String getValue(int type,int param,int closeDateAtr){
		String value = null;
		switch (param) {
		case 0:
			StartingMonthType startingMonthType = EnumAdaptor.valueOf(type, StartingMonthType.class);
			value = EnumAdaptor.convertToValueName(startingMonthType).getLocalizedName();
			break;
		case 1:
			ClosingDateAtr closingDateAtr = EnumAdaptor.valueOf(type, ClosingDateAtr.class);
			value = EnumAdaptor.convertToValueName(closingDateAtr).getLocalizedName();
			break;
		case 2:
			ClosingDateType closingDateType = EnumAdaptor.valueOf(type, ClosingDateType.class);
			value = EnumAdaptor.convertToValueName(closingDateType).getLocalizedName();
			break;
		case 3:
			TimeOverLimitType timeOverLimitType = EnumAdaptor.valueOf(type, TimeOverLimitType.class);
			value = EnumAdaptor.convertToValueName(timeOverLimitType).getLocalizedName();
			break;
		case 4:
			if(closeDateAtr != 0) {
				TargetSettingAtr targetSettingAtr = EnumAdaptor.valueOf(type, TargetSettingAtr.class);
				value = EnumAdaptor.convertToValueName(targetSettingAtr).getLocalizedName();
			} else {
				value = "";
			}
			break;
		case 5:
			if(closeDateAtr != 0) {
				TargetSettingAtr yearlyWorkTableAtr = EnumAdaptor.valueOf(type, TargetSettingAtr.class);
				value = EnumAdaptor.convertToValueName(yearlyWorkTableAtr).getLocalizedName();
			} else {
				value = "";
			}
			break;

		default:
			break;
		}
		return value;
	}
	
	private String formatTime(int source) {
		int regularized = Math.abs(source);
		int hourPart = (regularized / 60);
		int minutePart = regularized % 60;
		String resultString = StringUtils.join(StringUtil.padLeft(String.valueOf(hourPart), 2, '0'),":", StringUtil.padLeft(String.valueOf(minutePart), 2, '0'));
		return resultString;
	}

	@Override
	public List<MasterData> getDataExportSheet2() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_2.toString()).setParameter(1, cid);
		Object[] data = null;
		try {
			data = (Object[]) query.getSingleResult();
			int j = 0 ;
			for (int i = 0; i < 7; i++) {
				datas.add(toDataSheet2(data,i,j));
				j = j+3;
			}
		} catch (Exception e) {
			for (int i = 0; i < 7; i++) {
				datas.add(toDataSheet2(data,i,0));
			}
		}
		
		return datas;
	}
	
	
	
	private MasterData toDataSheet2(Object[] objects,int rownum,int param) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_89, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_89)
                .value(getColumnOneSheet2(rownum))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_90, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_90)
                .value(objects != null ? formatTime(((BigDecimal)objects[param]).intValue()) : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_91, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_91)
                .value(objects != null ? formatTime(((BigDecimal)objects[++param]).intValue()) : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_92, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_92)
                .value(objects != null ? formatTime(((BigDecimal)objects[++param]).intValue()) : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		return MasterData.builder().rowData(data).build();
	}
	
	
	private String getColumnOneSheet2(int rownum) {
		String value = "";
		switch (rownum) {
		case 0:
			value = RegistTimeColumn.KMK008_93;
			break;
		case 1 :
			value = RegistTimeColumn.KMK008_94;
			break;
		case 2 :
			value = RegistTimeColumn.KMK008_95;
			break;
		case 3 :
			value = RegistTimeColumn.KMK008_96;
			break;
		case 4 :
			value = RegistTimeColumn.KMK008_97;
			break;
		case 5 :
			value = RegistTimeColumn.KMK008_98;
			break;
		case 6 :
			value = RegistTimeColumn.KMK008_99;
			break;
		default:
			break;
		}
		return value;
	}

	@Override
	public List<MasterData> getDataExportSheet3() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_3.toString()).
				setParameter("cid", cid);
		@SuppressWarnings("unchecked")
		List<Object[]> data =  query.getResultList();
		for (Object[] objects : data) {
			int j = 2 ;
			for (int i = 0; i < 7; i++) {
				datas.add(toDataSheet3(objects,i,j));
				j = j+3;
			}
		}
		return datas;
	}
	
	
	private MasterData toDataSheet3(Object[] objects,int rownum,int param) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_100, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_100)
                .value(rownum == 0 ? objects[0] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_101, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_101)
                .value(rownum == 0 ? objects[1] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_89, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_89)
                .value(getColumnOneSheet2(rownum))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		 data.put(RegistTimeColumn.KMK008_90, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_90)
	                .value(formatTime(((BigDecimal)objects[param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		 data.put(RegistTimeColumn.KMK008_91, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_91)
	                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		 data.put(RegistTimeColumn.KMK008_92, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_92)
	                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		return MasterData.builder().rowData(data).build();
	}
	
	
	@Override
	public List<MasterData> getDataExportSheet4() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_4.toString()).
				setParameter("cid", cid);
		@SuppressWarnings("unchecked")
		List<Object[]> data =  query.getResultList();
		for (Object[] objects : data) {
			int j = 2 ;
			for (int i = 0; i < 7; i++) {
				datas.add(toDataSheet4(objects,i,j));
				j = j+3;
			}
		}
		return datas;
	}
	
	private MasterData toDataSheet4(Object[] objects,int rownum,int param) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_102, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_102)
                .value(rownum == 0 ? objects[0] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_103, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_103)
                .value(rownum == 0 ? objects[1] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_89, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_89)
                .value(getColumnOneSheet2(rownum))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		 data.put(RegistTimeColumn.KMK008_90, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_90)
	                .value(formatTime(((BigDecimal)objects[param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		 data.put(RegistTimeColumn.KMK008_91, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_91)
	                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		 data.put(RegistTimeColumn.KMK008_92, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_92)
	                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		return MasterData.builder().rowData(data).build();
	}
	
	@Override
	public List<MasterData> getDataExportSheet5() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_5.toString()).
				setParameter("cid", cid);
		@SuppressWarnings("unchecked")
		List<Object[]> data =  query.getResultList();
		for (Object[] objects : data) {
			int j = 2 ;
			for (int i = 0; i < 7; i++) {
				datas.add(toDataSheet5(objects,i,j));
				j = j+3;
			}
		}
		return datas;
	}
	
	
	
	private MasterData toDataSheet5(Object[] objects,int rownum,int param) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_104, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_104)
                .value(rownum == 0 ? objects[0] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_105, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_105)
                .value(rownum == 0 ? objects[1] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_89, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_89)
                .value(getColumnOneSheet2(rownum))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_90, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_90)
                .value(formatTime(((BigDecimal)objects[param]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_91, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_91)
                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_92, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_92)
                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		return MasterData.builder().rowData(data).build();
	}

	@Override
	public List<MasterData> getDataExportSheet6() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_6.toString()).setParameter(1, cid);
		Object[] data = null;
		try {
			data = (Object[]) query.getSingleResult();
			int j = 0 ;
			for (int i = 0; i < 7; i++) {
				datas.add(toDataSheet6(data,i,j));
				j = j+3;
			}
		} catch (Exception e) {
			for (int i = 0; i < 7; i++) {
				datas.add(toDataSheet6(data, i, 0));
			}
		}
		
		return datas;
	}
	
	
	
	private MasterData toDataSheet6(Object[] objects,int rownum,int param) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_89, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_89)
                .value(getColumnOneSheet2(rownum))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_90, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_90)
                .value(objects == null ? "" : formatTime(((BigDecimal)objects[param]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_91, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_91)
                .value(objects == null ? "" : formatTime(((BigDecimal)objects[++param]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_92, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_92)
                .value(objects == null  ? "" : formatTime(((BigDecimal)objects[++param]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		return MasterData.builder().rowData(data).build();
	}

	@Override
	public List<MasterData> getDataExportSheet7() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_7.toString()).
				setParameter("cid", cid);
		
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		for (Object[] objects : data) {
			int j = 2;
			for (int i = 0; i < 7; i++) {
				datas.add(toDataSheet7(objects, i, j));
				j = j + 3;
			}
		}
		return datas;
	}
	
	
	
	private MasterData toDataSheet7(Object[] objects,int rownum,int param) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_100, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_100)
                .value(rownum == 0 ? objects[0] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_101, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_101)
                .value(rownum == 0 ? objects[1] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_89, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_89)
                .value(getColumnOneSheet2(rownum))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		 data.put(RegistTimeColumn.KMK008_90, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_90)
	                .value(formatTime(((BigDecimal)objects[param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		 data.put(RegistTimeColumn.KMK008_91, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_91)
	                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		 data.put(RegistTimeColumn.KMK008_92, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_92)
	                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		return MasterData.builder().rowData(data).build();
	}
	
	
	@Override
	public List<MasterData> getDataExportSheet8() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_8.toString()).
				setParameter("cid", cid);
		@SuppressWarnings("unchecked")
		List<Object[]> data =  query.getResultList();
		for (Object[] objects : data) {
			int j = 2 ;
			for (int i = 0; i < 7; i++) {
				datas.add(toDataSheet8(objects,i,j));
				j = j+3;
			}
		}
		return datas;
	}
	
	private MasterData toDataSheet8(Object[] objects,int rownum,int param) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_102, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_102)
                .value(rownum == 0 ? objects[0] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_103, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_103)
                .value(rownum == 0 ? objects[1] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_89, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_89)
                .value(getColumnOneSheet2(rownum))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		 data.put(RegistTimeColumn.KMK008_90, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_90)
	                .value(formatTime(((BigDecimal)objects[param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		 data.put(RegistTimeColumn.KMK008_91, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_91)
	                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		 data.put(RegistTimeColumn.KMK008_92, MasterCellData.builder()
	                .columnId(RegistTimeColumn.KMK008_92)
	                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	                .build());
		return MasterData.builder().rowData(data).build();
	}
	
	@Override
	public List<MasterData> getDataExportSheet9() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_9.toString()).
				setParameter("cid", cid);
		@SuppressWarnings("unchecked")
		List<Object[]> data =  query.getResultList();
		for (Object[] objects : data) {
			int j = 2 ;
			for (int i = 0; i < 7; i++) {
				datas.add(toDataSheet9(objects,i,j));
				j = j+3;
			}
		}
		return datas;
	}
	
	private MasterData toDataSheet9(Object[] objects,int rownum,int param) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_104, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_104)
                .value(rownum == 0 ? objects[0] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_105, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_105)
                .value(rownum == 0 ? objects[1] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_89, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_89)
                .value(getColumnOneSheet2(rownum))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_90, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_90)
                .value(formatTime(((BigDecimal)objects[param]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_91, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_91)
                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_92, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_92)
                .value(formatTime(((BigDecimal)objects[++param]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		return MasterData.builder().rowData(data).build();
	}




	@Override
	public List<MasterData> getDataExportSheet10(GeneralDate startDate, GeneralDate endDate) {
		List<MasterData> datas = new ArrayList<>();
		String startYM = startDate.yearMonth().toString();
		String endYM = endDate.yearMonth().toString();
		int startY = startDate.year();
		int endY = endDate.year();
		String checkYM = "";
		String checkY = "";
		Boolean checkValue = false;
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_10.toString()).
				setParameter("cid", cid).
				setParameter("startY", startY).
				setParameter("endY", endY).
				setParameter("startYM", startYM).
				setParameter("endYM", endYM);
		@SuppressWarnings("unchecked")
		List<Object[]> data =  query.getResultList();
		for (Object[] objects : data) {
			if(objects[1] != null) {
				checkYM = ((BigDecimal)objects[2]).toString();
				checkY = ((BigDecimal)objects[5]).toString();
				checkValue = true;
			} else {
				checkValue = false;
			}
			datas.add(toDataSheet10(objects,checkYM,checkY,checkValue));
		}
		return datas;
	}
	
	private MasterData toDataSheet10(Object[] objects,String checkYM,String checkY,Boolean checkValue) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_106, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_106)
                .value(objects[0])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(RegistTimeColumn.KMK008_107, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_107)
                .value(objects[1])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		
		data.put(RegistTimeColumn.KMK008_109, MasterCellData.builder()
			        .columnId(RegistTimeColumn.KMK008_109)
			        .value(objects[2] != null  ? checkValue || !checkYM.equals(((BigDecimal)objects[2]).toString()) ? ((BigDecimal)objects[2]).toString().substring(0, 4) + "/" + ((BigDecimal)objects[2]).toString().substring(4, ((BigDecimal)objects[2]).toString().length()) : "" :"")
			        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
			        .build());
		
		data.put(RegistTimeColumn.KMK008_110, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_110)
                .value(objects[3] != null  ? checkValue || !checkYM.equals(((BigDecimal)objects[2]).toString()) ?  formatTime(((BigDecimal)objects[3]).intValue()) : "" : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_111, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_111)
                .value(objects[4] != null ?  checkValue || !checkYM.equals(((BigDecimal)objects[2]).toString()) ? formatTime(((BigDecimal)objects[4]).intValue()) : "":"")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		
		
		data.put(RegistTimeColumn.KMK008_112, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_112)
                .value(checkValue || !checkY.equals(((BigDecimal)objects[5]).toString()) ? objects[5] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_113, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_113)
                .value(objects[6] != null  ? checkValue || !checkY.equals(((BigDecimal)objects[5]).toString()) ? formatTime(((BigDecimal)objects[6]).intValue()) : "" : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(RegistTimeColumn.KMK008_114, MasterCellData.builder()
                .columnId(RegistTimeColumn.KMK008_114)
                .value(objects[7] != null  ? checkValue || !checkY.equals(((BigDecimal)objects[5]).toString()) ? formatTime(((BigDecimal)objects[7]).intValue()) : "" :"")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		
		return MasterData.builder().rowData(data).build();
	}

}
