package nts.uk.file.at.infra.worktype;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.worktype.PreparationBeforeApplyRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaPreparationBeforeApplyRepository extends JpaRepository implements PreparationBeforeApplyRepository {

	private static final String SELECT_ALL_BY_CID =
				"SELECT temp.ROW_NUMBER, "+
						"temp.CLOSURE_ID, "+
						"temp.CLOSURE_NAME, "+
						"temp.USE_ATR, "+
						"temp.DEADLINE_CRITERIA, "+
						"temp.DEADLINE, "+
						"temp.BASE_DATE_FLG, "+
						"temp.APP_CONTENT_CHANGE_FLG, "+
						"temp.MHI_SUBJECT, "+
						"temp.MHI_CONTENT, "+
						"temp.MOI_SUBJECT, "+
						"temp.MOI_CONTENT, "+
						"temp.IS_CONCURRENTLY, "+
						"temp.PRINCIPAL_APPROVAL_FLG, "+
						"temp.CONTENT, "+
						"temp.MAIL_TITLE, "+
						"temp.MAIL_BODY, "+
						"temp.APP_REASON_DISP_ATR, "+
						"temp.DISPLAY_PRE_POST_FLG, "+
						"pas.ROW_NUMBER, "+
						"pas.APP_TYPE, "+
						"atd.ROW_NUMBER, "+
						"atd.OT_RESTRICT_PRE_DAY, "+
						"atd.RETRICT_PRE_METHOD_CHECK_FLG, "+
						"atd.PRE_OTWORK_TIME, "+
						"atd.NORMAL_OT_TIME, "+
						"atd.RETRICT_PRE_TIMEDAY, "+
						"atd.APP_TYPE, "+
						"atd.RETRICT_PRE_USE_FLG, "+
						"atd.RETRICT_PRE_DAY, "+
						"atd.RETRICT_POST_ALLOW_FUTURE_FLG, "+
						"atd.SEND_MAIL_WHEN_REGISTER_FLG, "+
						"atd.SEND_MAIL_WHEN_APPROVAL_FLG, "+
						"atd.DISPLAY_REASON_FLG, "+
						"atd.PRE_POST_CAN_CHANGE_FLG, "+
						"adn.ROW_NUMBER, "+
                        "adn.APP_TYPE," +
						"adn.DISP_NAME, "+
						"dr.ROW_NUMBER, "+
						"dr.TYPE_OF_LEAVE_APP, "+
						"dr.DISPLAY_FIXED_REASON, "+
						"dr.DISPLAY_APP_REASON, "+
						"temp.REQUIRE_APP_REASON_FLG," +
						"temp.APP_OVERTIME_NIGHT_FLG, " +
						"temp.APP_ACT_CONFIRM_FLG, " +
						"temp.APP_ACT_MONTH_CONFIRM_FLG, " +
						"temp.APP_END_WORK_FLG, " +
						"temp.APP_ACT_LOCK_FLG, " +
						"temp.MANUAL_SEND_MAIL_ATR "+
				"FROM "+
						"(SELECT "+
					 			"ROW_NUMBER() OVER (ORDER BY clo.CLOSURE_ID ASC) AS ROW_NUMBER, "+
								"clo.CLOSURE_ID, "+
								"cloh.CLOSURE_NAME, "+
								"clo.CID, "+
								"ad.USE_ATR, "+
								"ad.DEADLINE_CRITERIA, "+
								"ad.DEADLINE, "+
								"asg.BASE_DATE_FLG, "+
								"asg.APP_CONTENT_CHANGE_FLG, "+
								"asg.APP_REASON_DISP_ATR, "+
								"asg.DISPLAY_PRE_POST_FLG, "+
								"ue.URL_EMBEDDED, "+
								"mhi.SUBJECT AS MHI_SUBJECT, "+
								"mhi.CONTENT AS MHI_CONTENT, "+
								"moi.SUBJECT AS MOI_SUBJECT, "+
								"moi.CONTENT AS MOI_CONTENT, "+
								"jas.IS_CONCURRENTLY, "+
								"was.PRINCIPAL_APPROVAL_FLG, "+
								"ate.CONTENT, "+
								"krm.MAIL_TITLE, "+
						 		"krm.MAIL_BODY, "+
								"asg.REQUIRE_APP_REASON_FLG, " +
								"asg.APP_OVERTIME_NIGHT_FLG, " +
								"asg.APP_ACT_CONFIRM_FLG, " +
								"asg.APP_ACT_MONTH_CONFIRM_FLG, " +
								"asg.APP_END_WORK_FLG, " +
								"asg.APP_ACT_LOCK_FLG, " +
								"asg.MANUAL_SEND_MAIL_ATR "+
						"FROM "+
								"(SELECT "+
					 					"CLOSURE_ID, "+
			 							"CID, "+
			 							"CLOSURE_MONTH "+
								"FROM KCLMT_CLOSURE "+
					 			"WHERE CID = ?cid) clo "+
						"INNER JOIN  KCLMT_CLOSURE_HIST cloh "+
						"ON clo.CID = cloh.CID AND clo.CLOSURE_ID = cloh.CLOSURE_ID AND clo.CLOSURE_MONTH <= cloh.END_YM "+
						"AND clo.CLOSURE_MONTH >= cloh.STR_YM "+
			"FULL JOIN "+
					"(SELECT CID, USE_ATR, DEADLINE_CRITERIA, DEADLINE, CLOSURE_ID " +
					 "FROM KRQST_APP_DEADLINE "+
					" WHERE CID = ?cid) ad "+
			 "ON clo.CID = ad.CID AND clo.CLOSURE_ID = ad.CLOSURE_ID "+
			"FULL JOIN "+
						"(SELECT CID, " +
								"BASE_DATE_FLG, " +
								"APP_CONTENT_CHANGE_FLG, " +
								"APP_REASON_DISP_ATR, " +
								"DISPLAY_PRE_POST_FLG, " +
								"REQUIRE_APP_REASON_FLG, " +
								"APP_OVERTIME_NIGHT_FLG, " +
								"APP_ACT_CONFIRM_FLG, " +
								"APP_ACT_MONTH_CONFIRM_FLG, " +
								"APP_END_WORK_FLG, " +
								"APP_ACT_LOCK_FLG, " +
								"MANUAL_SEND_MAIL_ATR "+
						"FROM KRQST_APPLICATION_SETTING "+
						" WHERE CID = ?cid) asg "+
				"ON clo.CID = asg.CID "+
			"FULL JOIN "+
						"(SELECT CID, URL_EMBEDDED "+
						"FROM KRQST_URL_EMBEDDED "+
					 	"WHERE CID = ?cid) ue "+
				"ON ue.CID = clo.CID "+
			"FULL JOIN "+
						"(SELECT CONTENT, CID "+
						"FROM KRQMT_APPROVAL_TEMPLATE "+
						"WHERE CID = ?cid) ate "+
				"ON ate.CID = clo.CID "+
			"FULL JOIN "+
						"(SELECT  SUBJECT, CONTENT, CID "+
						"FROM KRQMT_MAIL_HD_INSTRUCTION "+
						"WHERE CID = ?cid) mhi "+
				"ON mhi.CID = clo.CID "+
			"FULL JOIN (SELECT SUBJECT, CONTENT, CID "+
						"FROM KRQMT_MAIL_OT_INSTRUCTION "+
					    "WHERE CID = ?cid) moi "+
				"ON moi.CID = clo.CID "+

			"FULL JOIN (SELECT MAIL_TITLE, MAIL_BODY, CID "+
						"FROM KRQST_REMAND_MAIL "+
					   	"WHERE CID = ?cid) krm "+
				"ON krm.CID = clo.CID "+
			"FULL JOIN "+
						"(SELECT IS_CONCURRENTLY, CID "+
						"FROM WWFST_JOB_ASSIGN_SET  "+
					 	"WHERE CID = ?cid) jas "+
				"ON jas.CID = clo.CID "+
			"FULL JOIN "+
						"(SELECT PRINCIPAL_APPROVAL_FLG, CID "+
						"FROM WWFST_APPROVAL_SETTING "+
					 	"WHERE CID = ?cid)was "+
				"ON was.CID = clo.CID "+
		") temp "+

	"FULL JOIN "+
			"(SELECT APP_TYPE, "+
						"ROW_NUMBER() OVER (ORDER BY APP_TYPE ) AS ROW_NUMBER "+
			"FROM KRQST_PROXY_APP_SET "+
			"WHERE  CID = ?cid) pas "+
		"ON pas.ROW_NUMBER = temp.ROW_NUMBER "+
	"FULL JOIN "+
			"(SELECT ROW_NUMBER() OVER (ORDER BY APP_TYPE ) AS ROW_NUMBER, "+
					"OT_RESTRICT_PRE_DAY, "+
					"RETRICT_PRE_METHOD_CHECK_FLG, "+
					"PRE_OTWORK_TIME, "+
					"NORMAL_OT_TIME, "+
					"RETRICT_PRE_TIMEDAY, "+
					"APP_TYPE, "+
					"RETRICT_PRE_USE_FLG, "+
					"RETRICT_PRE_DAY, "+
					"RETRICT_POST_ALLOW_FUTURE_FLG, " +
					"SEND_MAIL_WHEN_REGISTER_FLG, " +
					"SEND_MAIL_WHEN_APPROVAL_FLG, " +
					"DISPLAY_REASON_FLG, " +
					"PRE_POST_CAN_CHANGE_FLG "+
			"FROM KRQST_APP_TYPE_DISCRETE "+
			"WHERE APP_TYPE NOT IN (3, 5, 8, 11, 12, 13, 14) AND  CID = ?cid "+
			") atd " +
		"ON temp.ROW_NUMBER = atd.ROW_NUMBER "+
	"FULL JOIN "+
			"( SELECT CID, "+
			 		"APP_TYPE , "+
			 		"DISP_NAME, "+
				 	"ROW_NUMBER() OVER (ORDER BY APP_TYPE ) AS ROW_NUMBER "+
			"FROM KRQMT_APP_DISP_NAME "+
			"WHERE APP_TYPE != 5  AND APP_TYPE != 12 AND CID = ?cid "+
			") adn "+
		"ON adn.ROW_NUMBER = temp.ROW_NUMBER "+
	"FULL JOIN (SELECT TYPE_OF_LEAVE_APP, "+
			   			"DISPLAY_FIXED_REASON, "+
			    		"ROW_NUMBER() OVER (ORDER BY CID ) AS ROW_NUMBER, "+
						"DISPLAY_APP_REASON "+
				"FROM KRQST_DISPLAY_REASON "+
				"WHERE CID = ?cid) dr " +
		"ON dr.ROW_NUMBER = temp.ROW_NUMBER";

	@Override
	public List<Object[]> getChangePerInforDefinitionToExport(String cid) {
		List<Object[]> resultQuery = null;
        try {
			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(SELECT_ALL_BY_CID)
					.setParameter("cid", cid)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}
	@Override
	public List<Object[]> getExtraData(String cid) {
		List<Object[]> resultQuery = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT aos.WORKTYPE_PERMISSION_FLAG, ");
		sql.append("aos.FLEX_EXCESS_USE_SET_ATR, ");
		sql.append("rac.BONUS_TIME_DISPLAY_ATR, ");
		sql.append("rac.DIVERGENCE_REASON_FORM_ATR, ");
		sql.append("rac.DIVERGENCE_REASON_INPUT_ATR, ");
		sql.append("rac.PERFORMANCE_DISPLAY_ATR, ");
		sql.append("rac.PRE_DISPLAY_ATR, ");
		sql.append("rac.CAL_OVERTIME_DISPLAY_ATR, ");
		sql.append("rac.EXTRATIME_DISPLAY_ATR, ");
		sql.append("rac.PRE_EXCESS_DISPLAY_SETTING, ");
		sql.append("rac.PERFORMANCE_EXCESS_ATR, ");
		sql.append("was.OVERRIDE_SET, ");
		sql.append("rac.EXTRATIME_EXCESS_ATR, ");
		sql.append("rac.APP_DATE_CONTRADICTION_ATR, ");
		sql.append("ar.APP_TYPE, ");
		sql.append("ar.REASON_TEMP, ");
		sql.append("ar.DEFAULT_FLG ");
		sql.append("FROM (SELECT ROW_NUMBER() OVER (ORDER BY DISPORDER ASC) AS ROW_NUMBER, ");
		sql.append("APP_TYPE, ");
		sql.append("REASON_TEMP, ");
		sql.append("DEFAULT_FLG ");
		sql.append("FROM KRQST_APP_REASON ");
		sql.append("WHERE CID = ?cid) ar ");
		sql.append("FULL JOIN (SELECT ROW_NUMBER() OVER (ORDER BY CID ASC) AS ROW_NUMBER, ");
		sql.append("CID, WORKTYPE_PERMISSION_FLAG, FLEX_EXCESS_USE_SET_ATR ");
		sql.append("FROM KRQST_APP_OVERTIME_SET WHERE CID = ?cid) aos ");
		sql.append("ON ar.ROW_NUMBER = aos.ROW_NUMBER ");
		sql.append("FULL JOIN (SELECT ROW_NUMBER() OVER (ORDER BY APP_TYPE ASC) AS ROW_NUMBER, ");
		sql.append("APP_TYPE, BONUS_TIME_DISPLAY_ATR, DIVERGENCE_REASON_FORM_ATR, DIVERGENCE_REASON_INPUT_ATR, PERFORMANCE_DISPLAY_ATR,PRE_DISPLAY_ATR, ");
		sql.append("CAL_OVERTIME_DISPLAY_ATR, EXTRATIME_DISPLAY_ATR,PRE_EXCESS_DISPLAY_SETTING, PERFORMANCE_EXCESS_ATR, EXTRATIME_EXCESS_ATR, APP_DATE_CONTRADICTION_ATR ");
		sql.append("FROM KRQST_OT_REST_APP_COM_SET WHERE CID = ?cid) rac ");
		sql.append("ON rac.ROW_NUMBER = ar.ROW_NUMBER ");
		sql.append("FULL JOIN (SELECT ROW_NUMBER() OVER (ORDER BY CID ASC) AS ROW_NUMBER, ");
		sql.append("OVERRIDE_SET ");
		sql.append("FROM KRQST_WITHDRAWAL_APP_SET ");
		sql.append("WHERE CID = ?cid )was ");
		sql.append("ON was.ROW_NUMBER = ar.ROW_NUMBER ");
		sql.append("FULL JOIN (SELECT ROW_NUMBER() OVER (ORDER BY CID ASC) AS ROW_NUMBER, INIT_DISPLAY_WORKTIME ");
		sql.append("FROM KRQST_APP_WORK_CHANGE_SET WHERE CID = ?cid) awc ");
		sql.append("ON awc.ROW_NUMBER = ar.ROW_NUMBER ");

		try {
			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(sql.toString())
					.setParameter("cid", cid)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}

	private java.sql.Date conVertToDateSQL(String baseDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.sql.Date sqlDate = null;
		try {
			date = format.parse(baseDate);
			sqlDate = new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			return null;
		}
		return sqlDate;
	}

	@Override
	public List<Object[]> getJob(String cid, String baseDate){
		List<Object[]> resultQuery = null;
		StringBuilder selectJob = new StringBuilder();
		selectJob.append("SELECT k.JOB_CD, w.JOB_NAME, k.SEARCH_SET_FLG ");
		selectJob.append("FROM (");
		selectJob.append("SELECT HIST_ID, JOB_ID, CID ");
		selectJob.append("FROM BSYMT_JOB_HIST ");
		selectJob.append("WHERE END_DATE >= ?baseDate AND CID = ?cid) h ");
		selectJob.append("INNER JOIN (SELECT JOB_NAME, JOB_ID, HIST_ID, CID  ");
		selectJob.append("FROM BSYMT_JOB_INFO ) w  ");
		selectJob.append("ON w.HIST_ID = h.HIST_ID AND w.JOB_ID = h.JOB_ID AND w.CID = h.CID ");
		selectJob.append("RIGHT JOIN (SELECT i.JOB_CD, i.JOB_ID, i.CID, j.SEARCH_SET_FLG ");
		selectJob.append("FROM (SELECT JOB_ID, JOB_CD, CID ");
		selectJob.append("FROM BSYMT_JOB_INFO WHERE CID = ?cid) i ");
		selectJob.append("INNER JOIN WWFST_JOBTITLE_SEARCH_SET j ON j.CID = i.CID AND j.JOB_ID = i.JOB_ID ) k ");
		selectJob.append("ON w.JOB_ID = k.JOB_ID AND k.CID = w.CID ");
		selectJob.append("ORDER BY k.JOB_CD ");
		try {
			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(selectJob.toString())
					.setParameter("cid", cid)
					.setParameter("baseDate", this.conVertToDateSQL(baseDate))
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}
}
