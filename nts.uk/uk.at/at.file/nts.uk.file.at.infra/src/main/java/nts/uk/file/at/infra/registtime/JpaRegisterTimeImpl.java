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
	
	private static final String SQL_EXPORT_SHEET_4 = " SELECT ss.WKPCD,"
			+" 			 MAX(ss.WKP_NAME) as WKP_NAME,"
			+" 			 MAX(ss.ERROR_WEEK) as ERROR_WEEK,"
			+" 			 MAX(ss.ALARM_WEEK) as ALARM_WEEK,"
			+" 			 MAX(ss.LIMIT_WEEK) as LIMIT_WEEK,"
			+" 			 MAX(ss.ERROR_TWO_WEEKS) as ERROR_TWO_WEEKS,"
			+" 			 MAX(ss.ALARM_TWO_WEEKS) as ALARM_TWO_WEEKS,"
			+" 			 MAX(ss.LIMIT_TWO_WEEKS) as LIMIT_TWO_WEEKS,"
			+" 			 MAX(ss.ERROR_FOUR_WEEKS) as ERROR_FOUR_WEEKS,"
			+" 			 MAX(ss.ALARM_FOUR_WEEKS) as ALARM_FOUR_WEEKS,"
			+" 			 MAX(ss.LIMIT_FOUR_WEEKS) as LIMIT_FOUR_WEEKS,"
			+" 			 MAX(ss.ERROR_ONE_MONTH) as ERROR_ONE_MONTH,"
			+" 			 MAX(ss.ALARM_ONE_MONTH) as ALARM_ONE_MONTH,"
			+" 			 MAX(ss.LIMIT_ONE_MONTH) as LIMIT_ONE_MONTH,"
			+" 			 MAX(ss.ERROR_TWO_MONTH) as ERROR_TWO_MONTH,"
			+" 			 MAX(ss.ALARM_TWO_MONTH) as ALARM_TWO_MONTH,"
			+" 			 MAX(ss.LIMIT_TWO_MONTH) as LIMIT_TWO_MONTH,"
			+" 	"
			+" 			 MAX(ss.ERROR_THREE_MONTH) as ERROR_THREE_MONTH,"
			+" 			 MAX(ss.ALARM_THREE_MONTH) as ALARM_THREE_MONTH,"
			+" 			 MAX(ss.LIMIT_THREE_MONTH) as LIMIT_THREE_MONTH,"
			+" "
			+" 			 MAX(ss.ERROR_YEARLY) as ERROR_YEARLY,"
			+" 			 MAX(ss.ALARM_YEARLY) as ALARM_YEARLY,"
			+" 			 MAX(ss.LIMIT_YEARLY) as LIMIT_YEARLY"
			+" "
			+"  FROM"
			+" ("
			+" SELECT"
			+" 	kk.WKPCD,"
			+" 	IIF("
			+" 	ee.END_DATE = '9999-12-31 00:00:00',"
			+" 	kk.WKP_NAME,"
			+" 	''"
			+" 	) as WKP_NAME,"
			+" 	ee.END_DATE,"
			+" 	aa.ERROR_WEEK,"
			+" 	aa.ALARM_WEEK,"
			+" 	LIMIT_WEEK = ("
			+" 		SELECT"
			+" 			aa.LIMIT_WEEK"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+" 	),"
			+" 	aa.ERROR_TWO_WEEKS,"
			+" 	aa.ALARM_TWO_WEEKS,"
			+" 	LIMIT_TWO_WEEKS = ("
			+" 		SELECT"
			+" 			aa.LIMIT_TWO_WEEKS"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+" 	),"
			+" 	aa.ERROR_FOUR_WEEKS,"
			+" 	aa.ALARM_FOUR_WEEKS,"
			+" 	LIMIT_FOUR_WEEKS = ("
			+" 		SELECT"
			+" 			aa.LIMIT_FOUR_WEEKS"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+" 	),"
			+" 	aa.ERROR_ONE_MONTH,"
			+" 	aa.ALARM_ONE_MONTH,"
			+" 	LIMIT_ONE_MONTH = ("
			+" 		SELECT"
			+" 			aa.LIMIT_ONE_MONTH"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+" 	),"
			+" 	aa.ERROR_TWO_MONTH,"
			+" 	aa.ALARM_TWO_MONTH,"
			+" 	LIMIT_TWO_MONTH = ("
			+" 		SELECT"
			+" 			aa.LIMIT_TWO_MONTH"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+" 	),"
			+" 	aa.ERROR_THREE_MONTH,"
			+" 	aa.ALARM_THREE_MONTH,"
			+" 	LIMIT_THREE_MONTH = ("
			+" 		SELECT"
			+" 			aa.LIMIT_THREE_MONTH"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+" 	),"
			+" 	aa.ERROR_YEARLY,"
			+" 	aa.ALARM_YEARLY,"
			+" 	LIMIT_YEARLY = ("
			+" 		SELECT"
			+" 			aa.LIMIT_YEARLY"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 0"
			+" 	)"
			+" FROM"
			+" 	KMKMT_BASIC_AGREEMENT_SET aa"
			+" JOIN KMKMT_AGREEMENTTIME_WPL bb "
			+" ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" JOIN BSYMT_WORKPLACE_INFO kk "
			+" ON bb.WKPCD = kk.WKPID"
			+" JOIN BSYMT_WORKPLACE_HIST ee "
			+" ON kk.HIST_ID = ee.HIST_ID"
			+" WHERE"
			+" 	 bb.LABOR_SYSTEM_ATR = 0 AND kk.CID = '000000000000-0104'"
			+" ) ss GROUP BY ss.WKPCD ";

	
	
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
	
	
	private static final String SQL_EXPORT_SHEET_8 = " SELECT ss.WKPCD,"
			+" 			 MAX(ss.WKP_NAME) as WKP_NAME,"
			+" 			 MAX(ss.ERROR_WEEK) as ERROR_WEEK,"
			+" 			 MAX(ss.ALARM_WEEK) as ALARM_WEEK,"
			+" 			 MAX(ss.LIMIT_WEEK) as LIMIT_WEEK,"
			+" 			 MAX(ss.ERROR_TWO_WEEKS) as ERROR_TWO_WEEKS,"
			+" 			 MAX(ss.ALARM_TWO_WEEKS) as ALARM_TWO_WEEKS,"
			+" 			 MAX(ss.LIMIT_TWO_WEEKS) as LIMIT_TWO_WEEKS,"
			+" 			 MAX(ss.ERROR_FOUR_WEEKS) as ERROR_FOUR_WEEKS,"
			+" 			 MAX(ss.ALARM_FOUR_WEEKS) as ALARM_FOUR_WEEKS,"
			+" 			 MAX(ss.LIMIT_FOUR_WEEKS) as LIMIT_FOUR_WEEKS,"
			+" 			 MAX(ss.ERROR_ONE_MONTH) as ERROR_ONE_MONTH,"
			+" 			 MAX(ss.ALARM_ONE_MONTH) as ALARM_ONE_MONTH,"
			+" 			 MAX(ss.LIMIT_ONE_MONTH) as LIMIT_ONE_MONTH,"
			+" 			 MAX(ss.ERROR_TWO_MONTH) as ERROR_TWO_MONTH,"
			+" 			 MAX(ss.ALARM_TWO_MONTH) as ALARM_TWO_MONTH,"
			+" 			 MAX(ss.LIMIT_TWO_MONTH) as LIMIT_TWO_MONTH,"
			+" 	"
			+" 			 MAX(ss.ERROR_THREE_MONTH) as ERROR_THREE_MONTH,"
			+" 			 MAX(ss.ALARM_THREE_MONTH) as ALARM_THREE_MONTH,"
			+" 			 MAX(ss.LIMIT_THREE_MONTH) as LIMIT_THREE_MONTH,"
			+" "
			+" 			 MAX(ss.ERROR_YEARLY) as ERROR_YEARLY,"
			+" 			 MAX(ss.ALARM_YEARLY) as ALARM_YEARLY,"
			+" 			 MAX(ss.LIMIT_YEARLY) as LIMIT_YEARLY"
			+" "
			+"  FROM"
			+" ("
			+" SELECT"
			+" 	kk.WKPCD,"
			+" 	IIF("
			+" 	ee.END_DATE = '9999-12-31 00:00:00',"
			+" 	kk.WKP_NAME,"
			+" 	''"
			+" 	) as WKP_NAME,"
			+" 	ee.END_DATE,"
			+" 	aa.ERROR_WEEK,"
			+" 	aa.ALARM_WEEK,"
			+" 	LIMIT_WEEK = ("
			+" 		SELECT"
			+" 			aa.LIMIT_WEEK"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+" 	),"
			+" 	aa.ERROR_TWO_WEEKS,"
			+" 	aa.ALARM_TWO_WEEKS,"
			+" 	LIMIT_TWO_WEEKS = ("
			+" 		SELECT"
			+" 			aa.LIMIT_TWO_WEEKS"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+" 	),"
			+" 	aa.ERROR_FOUR_WEEKS,"
			+" 	aa.ALARM_FOUR_WEEKS,"
			+" 	LIMIT_FOUR_WEEKS = ("
			+" 		SELECT"
			+" 			aa.LIMIT_FOUR_WEEKS"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+" 	),"
			+" 	aa.ERROR_ONE_MONTH,"
			+" 	aa.ALARM_ONE_MONTH,"
			+" 	LIMIT_ONE_MONTH = ("
			+" 		SELECT"
			+" 			aa.LIMIT_ONE_MONTH"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+" 	),"
			+" 	aa.ERROR_TWO_MONTH,"
			+" 	aa.ALARM_TWO_MONTH,"
			+" 	LIMIT_TWO_MONTH = ("
			+" 		SELECT"
			+" 			aa.LIMIT_TWO_MONTH"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+" 	),"
			+" 	aa.ERROR_THREE_MONTH,"
			+" 	aa.ALARM_THREE_MONTH,"
			+" 	LIMIT_THREE_MONTH = ("
			+" 		SELECT"
			+" 			aa.LIMIT_THREE_MONTH"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+" 	),"
			+" 	aa.ERROR_YEARLY,"
			+" 	aa.ALARM_YEARLY,"
			+" 	LIMIT_YEARLY = ("
			+" 		SELECT"
			+" 			aa.LIMIT_YEARLY"
			+" 		FROM"
			+" 			KMKMT_BASIC_AGREEMENT_SET aa"
			+" 		JOIN KMKMT_AGREEMENTTIME_COM bb ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" 		WHERE"
			+" 			bb.CID = ?cid AND bb.LABOR_SYSTEM_ATR = 1"
			+" 	)"
			+" FROM"
			+" 	KMKMT_BASIC_AGREEMENT_SET aa"
			+" JOIN KMKMT_AGREEMENTTIME_WPL bb "
			+" ON aa.BASIC_SETTING_ID = bb.BASIC_SETTING_ID"
			+" JOIN BSYMT_WORKPLACE_INFO kk "
			+" ON bb.WKPCD = kk.WKPID"
			+" JOIN BSYMT_WORKPLACE_HIST ee "
			+" ON kk.HIST_ID = ee.HIST_ID"
			+" WHERE"
			+" 	 bb.LABOR_SYSTEM_ATR = 1 AND kk.CID = '000000000000-0104'"
			+" ) ss GROUP BY ss.WKPCD ";

	
	
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
	
	@Override
	public List<MasterData> getDataExportSheet1() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_1.toString()).setParameter(1, cid);
		Object[] data = (Object[]) query.getSingleResult();
		int closeDateAtr = ((BigDecimal)data[1]).intValue();
		for (int i = 0; i < data.length; i++) {
			if(closeDateAtr == 0 && i == 2)
				continue;
			datas.add(toDataSheet1(data[i],i,closeDateAtr));
		}
		return datas;
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



}
