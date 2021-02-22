package nts.uk.file.at.infra.worktype;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.worktype.ApprovalFunctionConfigRepository;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class JpaApprovalFunctionConfigRepository extends JpaRepository implements ApprovalFunctionConfigRepository {

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllApprovalFunctionConfig(String cid, String baseDate) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  ");
		sql.append("     CASE WHEN TEMP.ROW_NUMBER = 1 THEN TEMP.CODE ELSE NULL END, ");
		sql.append("     CASE WHEN TEMP.ROW_NUMBER = 1 THEN TEMP.NAME ELSE NULL END, ");
		sql.append("     CASE WHEN TEMP.APP_TYPE = 0 THEN ?appType0 ");
		sql.append("       WHEN TEMP.APP_TYPE = 1 THEN ?appType1 ");
		sql.append("       WHEN TEMP.APP_TYPE = 2 THEN ?appType2 ");
		sql.append("       WHEN TEMP.APP_TYPE = 3 THEN ?appType3 ");
		sql.append("       WHEN TEMP.APP_TYPE = 4 THEN ?appType4 ");
		sql.append("       WHEN TEMP.APP_TYPE = 6 THEN ?appType6 ");
		sql.append("       WHEN TEMP.APP_TYPE = 7 THEN ?appType7 ");
		sql.append("       WHEN TEMP.APP_TYPE = 8 THEN ?appType8 ");
		sql.append("       WHEN TEMP.APP_TYPE = 9 THEN ?appType9 ");
		sql.append("       WHEN TEMP.APP_TYPE = 10 THEN ?appType10 ");
		sql.append("       WHEN TEMP.APP_TYPE = 14 THEN ?appType14 ");
		sql.append("       ELSE NULL ");
		sql.append("     END, ");
		sql.append("     CASE WHEN TEMP.USE_ATR = 0 THEN ?notUseText ");
		sql.append("       WHEN TEMP.USE_ATR = 1 THEN ?useText ");
		sql.append("       ELSE NULL ");
		sql.append("     END, ");
		sql.append("     CASE WHEN (TEMP.APP_TYPE != 0 AND TEMP.APP_TYPE != 6) OR TEMP.USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN TEMP.REQUIRED_INSTRUCTION_FLG = 0 THEN ?notRequiredInstruction ");
		sql.append("       WHEN TEMP.REQUIRED_INSTRUCTION_FLG = 1 THEN ?requiredInstruction ");
		sql.append("       ELSE NULL ");
		sql.append("     END REQUIRED_INSTRUCTION_FLG, ");
		sql.append("     CASE WHEN TEMP.USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN TEMP.APP_TYPE = 0 AND TEMP.OT_APP_SETTING_FLG = 0 THEN ?notPrerequisiteUseAtrText ");
		sql.append("       WHEN TEMP.APP_TYPE = 0 AND TEMP.OT_APP_SETTING_FLG = 1 THEN ?prerequisiteUseAtrText ");
		sql.append("       WHEN TEMP.APP_TYPE = 6 AND TEMP.PREREQUISITE_FORPAUSE_FLG = 0 THEN ?notPrerequisiteUseAtrText ");
		sql.append("       WHEN TEMP.APP_TYPE = 6 AND TEMP.PREREQUISITE_FORPAUSE_FLG = 1 THEN ?prerequisiteUseAtrText ");
		sql.append("       ELSE NULL ");
		sql.append("     END OT_APP_SETTING_FLG, ");
		sql.append("     CASE WHEN TEMP.USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN TEMP.APP_TYPE != 0 AND TEMP.APP_TYPE != 6 AND TEMP.APP_TYPE != 8 THEN NULL ");
		sql.append("       WHEN TEMP.TIME_CAL_USE_ATR = 0 THEN ?timeCalNotUseText ");
		sql.append("       WHEN TEMP.TIME_CAL_USE_ATR = 1 THEN ?timeCalUseText ");
		sql.append("       ELSE NULL ");
		sql.append("     END TIME_CAL_USE_ATR, ");
		sql.append("     CASE WHEN TEMP.USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN (TEMP.APP_TYPE != 0 AND TEMP.APP_TYPE != 6) OR TEMP.TIME_CAL_USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN TEMP.BREAK_INPUTFIELD_DIS_FLG = 0 THEN ?breakInputfieldNotDisp ");
		sql.append("       WHEN TEMP.BREAK_INPUTFIELD_DIS_FLG = 1 THEN ?breakInputfieldDisp ");
		sql.append("       ELSE NULL ");
		sql.append("     END BREAK_INPUTFIELD_DIS_FLG, ");
		sql.append("     CASE WHEN TEMP.USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN (TEMP.APP_TYPE != 0 AND TEMP.APP_TYPE != 6) OR TEMP.TIME_CAL_USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN TEMP.GOOUT_TIME_BEGIN_DIS_FLG = 0 THEN ?gooutTimeBeginNotDisp ");
		sql.append("       WHEN TEMP.GOOUT_TIME_BEGIN_DIS_FLG = 1 THEN ?gooutTimeBeginDisp ");
		sql.append("       ELSE NULL ");
		sql.append("     END GOOUT_TIME_BEGIN_DIS_FLG, ");
		sql.append("     CASE WHEN TEMP.USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN (TEMP.APP_TYPE != 0 AND TEMP.APP_TYPE != 6) OR TEMP.TIME_CAL_USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN TEMP.ATWORK_TIME_BEGIN_DIS_FLG = 0 THEN ?atWorkAtr0 ");
		sql.append("       WHEN TEMP.ATWORK_TIME_BEGIN_DIS_FLG = 1 THEN ?atWorkAtr1 ");
		sql.append("       WHEN TEMP.ATWORK_TIME_BEGIN_DIS_FLG = 2 THEN ?atWorkAtr2 ");
		sql.append("       WHEN TEMP.ATWORK_TIME_BEGIN_DIS_FLG = 3 THEN ?atWorkAtr3 ");
		sql.append("       ELSE NULL ");
		sql.append("     END ATWORK_TIME_BEGIN_DIS_FLG, ");
		sql.append("     CASE WHEN TEMP.USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN TEMP.APP_TYPE = 0 AND TEMP.TIME_INPUT_USE_ATR = 0 THEN ?notOvertimeHoursText ");
		sql.append("       WHEN TEMP.APP_TYPE = 0 AND TEMP.TIME_INPUT_USE_ATR = 1 THEN ?overtimeHoursText ");
		sql.append("       ELSE NULL ");
		sql.append("     END OVERTIME_HOURS, ");
		sql.append("     CASE WHEN TEMP.USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN TEMP.APP_TYPE = 6 AND TEMP.TIME_INPUT_USE_ATR = 0 THEN ?notbreakTimeText ");
		sql.append("       WHEN TEMP.APP_TYPE = 6 AND TEMP.TIME_INPUT_USE_ATR = 1 THEN ?breakTimeText ");
		sql.append("       ELSE NULL ");
		sql.append("     END REST_TIME, ");
		sql.append("     CASE WHEN TEMP.USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN TEMP.APP_TYPE = 9 AND TEMP.LATE_OR_LEAVE_APP_CANCEL_FLG = 0 THEN ?notLateOrLeaveAppCancelText ");
		sql.append("       WHEN TEMP.APP_TYPE = 9 AND TEMP.LATE_OR_LEAVE_APP_CANCEL_FLG = 1 THEN ?lateOrLeaveAppCancelText ");
		sql.append("       ELSE NULL ");
		sql.append("     END LATE_OR_LEAVE_APP_CANCEL_FLG, ");
		sql.append("     CASE WHEN TEMP.USE_ATR != 1 THEN NULL ");
		sql.append("       WHEN TEMP.APP_TYPE = 9 AND TEMP.LATE_OR_LEAVE_APP_SETTING_FLG = 0 THEN ?notLateOrLeaveAppSettingText ");
		sql.append("       WHEN TEMP.APP_TYPE = 9 AND TEMP.LATE_OR_LEAVE_APP_SETTING_FLG = 1 THEN ?lateOrLeaveAppSettingText ");
		sql.append("       ELSE NULL ");
		sql.append("     END LATE_OR_LEAVE_APP_SETTING_FLG, ");
		sql.append("     TEMP.MEMO ");
		sql.append("    FROM ");
		sql.append("    (SELECT  ");
		sql.append("     NULL AS CODE, ");
		sql.append("     ?companyText AS NAME, ");
		sql.append("     NULL AS HIERARCHY_CD, ");
		sql.append("     APP_TYPE, ");
		sql.append("     USE_ATR, ");
		sql.append("     REQUIRED_INSTRUCTION_FLG, ");
		sql.append("     OT_APP_SETTING_FLG, ");
		sql.append("     PREREQUISITE_FORPAUSE_FLG, ");
		sql.append("     TIME_CAL_USE_ATR, ");
		sql.append("     BREAK_INPUTFIELD_DIS_FLG, ");
		sql.append("     GOOUT_TIME_BEGIN_DIS_FLG, ");
		sql.append("     ATWORK_TIME_BEGIN_DIS_FLG, ");
		sql.append("     TIME_INPUT_USE_ATR, ");
		sql.append("     LATE_OR_LEAVE_APP_CANCEL_FLG, ");
		sql.append("     LATE_OR_LEAVE_APP_SETTING_FLG, ");
		sql.append("     MEMO,  ");
		sql.append("     0 AS NUM_ORDER,  ");
		sql.append("     ROW_NUMBER() OVER (PARTITION BY CID ORDER BY CID, DISPLAY_ORDER) AS ROW_NUMBER  ");
		sql.append("    FROM (SELECT *, CASE WHEN APP_TYPE = 7 THEN 9 WHEN APP_TYPE = 8 THEN 7 WHEN APP_TYPE = 9 THEN 8 ELSE APP_TYPE END AS DISPLAY_ORDER FROM KRQST_COM_APP_CF_DETAIL) COM_APP");
		sql.append("    WHERE CID = ?cid ");
		sql.append("    UNION ALL ");
		sql.append("    SELECT ");
		sql.append("     IIF(WPI.WKP_CD IS NOT NULL, WPI.WKP_CD, ?masterUnregistered) AS CODE, ");
		sql.append("     IIF(WPI.WKP_NAME IS NOT NULL, WPI.WKP_NAME, ?masterUnregistered) AS NAME, ");
		sql.append("     IIF(WP_CONFIG.HIERARCHY_CD IS NULL, '999999999999999999999999999999',WP_CONFIG.HIERARCHY_CD) AS HIERARCHY_CD, ");
		sql.append("     WP.APP_TYPE, ");
		sql.append("     WP.USE_ATR, ");
		sql.append("     WP.REQUIRED_INSTRUCTION_FLG, ");
		sql.append("     WP.OT_APP_SETTING_FLG, ");
		sql.append("     WP.PREREQUISITE_FORPAUSE_FLG, ");
		sql.append("     WP.TIME_CAL_USE_ATR, ");
		sql.append("     WP.BREAK_INPUTFIELD_DIS_FLG, ");
		sql.append("     WP.GOOUT_TIME_BEGIN_DIS_FLG, ");
		sql.append("     WP.ATWORK_TIME_BEGIN_DIS_FLG, ");
		sql.append("     WP.TIME_INPUT_USE_ATR, ");
		sql.append("     WP.LATE_OR_LEAVE_APP_CANCEL_FLG, ");
		sql.append("     WP.LATE_OR_LEAVE_APP_SETTING_FLG, ");
		sql.append("     WP.MEMO, ");
		sql.append("     WP.NUM_ORDER, ");
		sql.append("     WP.ROW_NUMBER ");
		sql.append("    FROM ");
		sql.append("     (SELECT *, ROW_NUMBER() OVER (PARTITION BY CID ORDER BY CID, WKP_ID, DISPLAY_ORDER) AS NUM_ORDER, ");
		sql.append("     ROW_NUMBER() OVER (PARTITION BY CID, WKP_ID ORDER BY CID, WKP_ID, DISPLAY_ORDER) AS ROW_NUMBER ");
		sql.append("     FROM (SELECT *, CASE WHEN APP_TYPE = 7 THEN 9 WHEN APP_TYPE = 8 THEN 7 WHEN APP_TYPE = 9 THEN 8 ELSE APP_TYPE END DISPLAY_ORDER FROM KRQMT_APP_CF_DETAIL_WKP) WP_APP_CF_DETAIL) WP ");
		sql.append("     LEFT JOIN BSYMT_WKP_INFO WPI ON WP.CID = WPI.CID AND WP.WKP_ID = WPI.WKP_ID");
		sql.append("     LEFT JOIN (SELECT WCI.CID, WCI.WKP_ID, WCI.HIERARCHY_CD FROM BSYMT_WKP_INFO WCI JOIN (SELECT CID, WKP_HIST_ID, ROW_NUMBER() OVER(PARTITION BY CID ORDER BY END_DATE DESC) AS RN FROM BSYMT_WKP_CONFIG_2) WC ON WCI.CID = WC.CID AND WCI.WKP_HIST_ID = WC.WKP_HIST_ID AND WC.RN = 1) WP_CONFIG ON WP.CID = WP_CONFIG.CID AND WP.WKP_ID = WP_CONFIG.WKP_ID ");
		sql.append("    WHERE ");
		sql.append("     WP.CID = ?cid) TEMP ");
		sql.append("     ORDER BY TEMP.HIERARCHY_CD, TEMP.CODE, TEMP.NUM_ORDER, TEMP.ROW_NUMBER;");
		
		List<Object[]> resultQuery = null;
		try {
			resultQuery = (List<Object[]>) getEntityManager().createNativeQuery(sql.toString())
				.setParameter("companyText", TextResource.localize("Com_Company"))
				.setParameter("cid", cid)
				.setParameter("masterUnregistered", TextResource.localize("Enum_MasterUnregistered"))
				.setParameter("appType0", TextResource.localize("KAF022_3"))
				.setParameter("appType1", TextResource.localize("KAF022_4"))
				.setParameter("appType2", TextResource.localize("KAF022_5"))
				.setParameter("appType3", TextResource.localize("KAF022_6"))
				.setParameter("appType4", TextResource.localize("KAF022_7"))
				.setParameter("appType6", TextResource.localize("KAF022_8"))
				.setParameter("appType7", TextResource.localize("KAF022_11"))
				.setParameter("appType8", TextResource.localize("KAF022_9"))
				.setParameter("appType9", TextResource.localize("KAF022_286"))
				.setParameter("appType10", TextResource.localize("KAF022_12"))
				.setParameter("appType14", TextResource.localize("KAF022_13"))
				.setParameter("useText", TextResource.localize("KAF022_100"))
				.setParameter("notUseText", TextResource.localize("KAF022_101"))
				.setParameter("requiredInstruction", "○")
				.setParameter("notRequiredInstruction", "-")
				.setParameter("notPrerequisiteUseAtrText", TextResource.localize("KAF022_291"))
				.setParameter("prerequisiteUseAtrText", TextResource.localize("KAF022_292"))
				.setParameter("timeCalUseText", TextResource.localize("KAF022_296"))
				.setParameter("timeCalNotUseText", TextResource.localize("KAF022_295"))
				.setParameter("breakInputfieldDisp", "○")
				.setParameter("breakInputfieldNotDisp", "-")
				.setParameter("gooutTimeBeginDisp", "○")
				.setParameter("gooutTimeBeginNotDisp", "-")
				.setParameter("atWorkAtr0", TextResource.localize("KAF022_37"))
				.setParameter("atWorkAtr1", TextResource.localize("KAF022_301"))
				.setParameter("atWorkAtr2", TextResource.localize("KAF022_302"))
				.setParameter("atWorkAtr3", TextResource.localize("KAF022_303"))
				.setParameter("overtimeHoursText", TextResource.localize("KAF022_305"))
				.setParameter("notOvertimeHoursText", TextResource.localize("KAF022_306"))
				.setParameter("breakTimeText", TextResource.localize("KAF022_308"))
				.setParameter("notbreakTimeText", TextResource.localize("KAF022_309"))
				.setParameter("lateOrLeaveAppCancelText", TextResource.localize("KAF022_311"))
				.setParameter("notLateOrLeaveAppCancelText", TextResource.localize("KAF022_312"))
				.setParameter("lateOrLeaveAppSettingText", TextResource.localize("KAF022_313"))
				.setParameter("notLateOrLeaveAppSettingText", TextResource.localize("KAF022_314"))
				.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllEmploymentApprovalSetting(String cid) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("     CASE WHEN ROW_NUM = 1 THEN TEMP.CODE ELSE NULL END EMPLOYMENT_CODE, ");
		sql.append("     CASE WHEN ROW_NUM = 1 THEN TEMP.NAME ELSE NULL END EMPLOYMENT_NAME, ");
		sql.append("     CASE WHEN TEMP.APP_TYPE = 0 THEN ?appType0 ");
		sql.append("       WHEN TEMP.APP_TYPE = 1 THEN ?appType1 ");
		sql.append("       WHEN TEMP.APP_TYPE = 2 THEN ?appType2 ");
		sql.append("       WHEN TEMP.APP_TYPE = 3 THEN ?appType3 ");
		sql.append("       WHEN TEMP.APP_TYPE = 4 THEN ?appType4 ");
		sql.append("       WHEN TEMP.APP_TYPE = 6 THEN ?appType6 ");
		sql.append("       WHEN TEMP.APP_TYPE = 7 THEN ?appType7 ");
		sql.append("       WHEN TEMP.APP_TYPE = 8 THEN ?appType8 ");
		sql.append("       WHEN TEMP.APP_TYPE = 9 THEN ?appType9 ");
		sql.append("       WHEN TEMP.APP_TYPE = 10 THEN ?appType10 ");
		sql.append("       WHEN TEMP.APP_TYPE = 11 THEN ?appType11 ");
		sql.append("       WHEN TEMP.APP_TYPE = 14 THEN ?appType14 ");
		sql.append("       ELSE NULL ");
		sql.append("     END APP_TYPE, ");
		sql.append("     CASE WHEN TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_OR_PAUSE_TYPE = 0 THEN ?pauseType0 ");
		sql.append("       WHEN TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_OR_PAUSE_TYPE = 1 THEN ?pauseType1 ");
		sql.append("       WHEN TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_OR_PAUSE_TYPE = 2 THEN ?pauseType2 ");
		sql.append("       WHEN TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_OR_PAUSE_TYPE = 3 THEN ?pauseType3 ");
		sql.append("       WHEN TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_OR_PAUSE_TYPE = 4 THEN ?pauseType4 ");
		sql.append("       WHEN TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_OR_PAUSE_TYPE = 5 THEN ?pauseType5 ");
		sql.append("       WHEN TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_OR_PAUSE_TYPE = 6 THEN ?pauseType6 ");
		sql.append("       WHEN TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_OR_PAUSE_TYPE = 7 THEN ?pauseType7 ");
		sql.append("       WHEN TEMP.APP_TYPE = 10 AND TEMP.HOLIDAY_OR_PAUSE_TYPE = 0 THEN ?holidayType0 ");
		sql.append("       WHEN TEMP.APP_TYPE = 10 AND TEMP.HOLIDAY_OR_PAUSE_TYPE = 1 THEN ?holidayType1 ");
		sql.append("       ELSE NULL ");
		sql.append("     END HOLIDAY_OR_PAUSE_TYPE, ");
		sql.append("     CASE WHEN TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_TYPE_USE_FLG = 0 THEN ?holidayTypeNotUseText ");
		sql.append("       WHEN TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_TYPE_USE_FLG = 1 THEN ?holidayTypeUseText ");
		sql.append("       ELSE NULL ");
		sql.append("     END HOLIDAY_TYPE_USE_FLG, ");
		sql.append("     CASE WHEN TEMP.APP_TYPE != 1 OR (TEMP.APP_TYPE = 1 AND TEMP.HOLIDAY_TYPE_USE_FLG = 0) THEN ");
		sql.append("       STUFF((SELECT ',' + EMP_WT.WORK_TYPE_CODE + IIF(WT.CD IS NOT NULL, WT.NAME, ?masterUnregistered) ");
		sql.append("        FROM ");
		sql.append("         KRQMT_APP_WKTP_EMP EMP_WT LEFT JOIN KSHMT_WKTP WT ");
		sql.append("          ON EMP_WT.CID = WT.CID ");
		sql.append("          AND EMP_WT.WORK_TYPE_CODE = WT.CD ");
		sql.append("        WHERE EMP_WT.CID = TEMP.CID ");
		sql.append("         AND EMP_WT.EMPLOYMENT_CODE = TEMP.CODE ");
		sql.append("         AND EMP_WT.APP_TYPE = TEMP.APP_TYPE ");
		sql.append("         AND EMP_WT.HOLIDAY_OR_PAUSE_TYPE = TEMP.HOLIDAY_OR_PAUSE_TYPE ");
		sql.append("        ORDER BY EMP_WT.CID, EMP_WT.WORK_TYPE_CODE ");
		sql.append("       FOR XML PATH('')), 1 , 1, '') ");
		sql.append("       ELSE NULL ");
		sql.append("     END WORK_TYPE_NAME, ");
		sql.append("	 ROW_NUMBER() OVER (PARTITION BY TEMP.CODE, APP_TYPE ORDER BY TEMP.CODE, APP_TYPE) AS ROW_NUM	");
		sql.append("    FROM ");
		sql.append("     (SELECT ");
		sql.append("      EMP_SET.CID, ");
		sql.append("      EMP_SET.EMPLOYMENT_CODE CODE, ");
		sql.append("      IIF(EMP.CODE IS NOT NULL, EMP.NAME, ?masterUnregistered) NAME, ");
		sql.append("      EMP_SET.APP_TYPE, ");
		sql.append("      EMP_SET.HOLIDAY_OR_PAUSE_TYPE, ");
		sql.append("      EMP_SET.HOLIDAY_TYPE_USE_FLG, ");
		sql.append("      ROW_NUMBER() OVER (PARTITION BY EMP_SET.CID, EMP_SET.EMPLOYMENT_CODE ORDER BY EMP_SET.CID, EMP_SET.EMPLOYMENT_CODE, EMP_SET.APP_TYPE, EMP_SET.DISPLAY_ORDER) AS ROW_NUM ");
		sql.append("     FROM ");
		sql.append("      BSYMT_EMPLOYMENT EMP ");
		sql.append("      RIGHT JOIN (SELECT *, CASE WHEN APP_TYPE = 10 THEN 1 - HOLIDAY_OR_PAUSE_TYPE  ELSE HOLIDAY_OR_PAUSE_TYPE END DISPLAY_ORDER FROM KRQST_APP_EMPLOYMENT_SET) EMP_SET ");
		sql.append("       ON EMP.CID = EMP_SET.CID ");
		sql.append("       AND EMP.CODE = EMP_SET.EMPLOYMENT_CODE ");
		sql.append("     WHERE ");
		sql.append("      EMP_SET.CID = ?cid AND EMP_SET.DISPLAY_FLAG = 1) TEMP ");
		sql.append("    GROUP BY TEMP.CID, TEMP.CODE, TEMP.NAME, TEMP.APP_TYPE, TEMP.HOLIDAY_OR_PAUSE_TYPE, TEMP.HOLIDAY_TYPE_USE_FLG, TEMP.ROW_NUM ");
		sql.append("    ORDER BY TEMP.CODE, TEMP.APP_TYPE, TEMP.ROW_NUM;");
		List<Object[]> resultQuery = null;
		try {
			resultQuery = getEntityManager().createNativeQuery(sql.toString())
					.setParameter("cid", cid)
					.setParameter("appType0", TextResource.localize("KAF022_3"))
					.setParameter("appType1", TextResource.localize("KAF022_4"))
					.setParameter("appType2", TextResource.localize("KAF022_5"))
					.setParameter("appType3", TextResource.localize("KAF022_6"))
					.setParameter("appType4", TextResource.localize("KAF022_7"))
					.setParameter("appType6", TextResource.localize("KAF022_8"))
					.setParameter("appType7", TextResource.localize("KAF022_11"))
					.setParameter("appType8", TextResource.localize("KAF022_9"))
					.setParameter("appType9", TextResource.localize("KAF022_286"))
					.setParameter("appType10", TextResource.localize("KAF022_12"))
					.setParameter("appType14", TextResource.localize("KAF022_13"))
					.setParameter("pauseType0", TextResource.localize("KAF022_47"))
					.setParameter("pauseType1", TextResource.localize("KAF022_48"))
					.setParameter("pauseType2", TextResource.localize("KAF022_49"))
					.setParameter("pauseType3", TextResource.localize("KAF022_50"))
					.setParameter("pauseType4", TextResource.localize("KAF022_51"))
					.setParameter("pauseType5", TextResource.localize("KAF022_52"))
					.setParameter("pauseType6", TextResource.localize("KAF022_53"))
					.setParameter("pauseType7", TextResource.localize("KAF022_54"))
					.setParameter("holidayType1", TextResource.localize("KAF022_279"))
					.setParameter("holidayType0", TextResource.localize("KAF022_54"))
					.setParameter("holidayTypeUseText", "○")
					.setParameter("holidayTypeNotUseText", "-")
					.setParameter("masterUnregistered", TextResource.localize("Enum_MasterUnregistered"))
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}

}
