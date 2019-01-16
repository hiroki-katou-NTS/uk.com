package nts.uk.file.at.infra.ot.autocalsetting.wkpjob;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * The Class JpaWkpJobCalSettingRepository.
 */
@Stateless
public class JpaWkpJobCalSettingRepository extends JpaRepository implements WkpJobAutoCalSettingRepository {

	private static final String GET_ALL_WORKPLACE_JOB;


	static {
		StringBuilder sqlNormal = new StringBuilder();

		sqlNormal.append("SELECT ");
		sqlNormal.append("temp.LEGAL_OT_TIME_ATR, ");
		sqlNormal.append("temp.LEGAL_OT_TIME_LIMIT, ");
		sqlNormal.append("temp.LEGAL_MID_OT_TIME_ATR, ");
		sqlNormal.append("temp.LEGAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append("temp.NORMAL_OT_TIME_ATR, ");
		sqlNormal.append("temp.NORMAL_OT_TIME_LIMIT, ");
		sqlNormal.append("temp.NORMAL_MID_OT_TIME_ATR, ");
		sqlNormal.append("temp.NORMAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append("temp.EARLY_OT_TIME_ATR, ");
		sqlNormal.append("temp.EARLY_OT_TIME_LIMIT, ");
		sqlNormal.append("temp.EARLY_MID_OT_TIME_ATR, ");
		sqlNormal.append("temp.EARLY_MID_OT_TIME_LIMIT, ");
		sqlNormal.append("temp.FLEX_OT_TIME_ATR, ");
		sqlNormal.append("temp.FLEX_OT_TIME_LIMIT, ");
		sqlNormal.append("temp.REST_TIME_ATR, ");
		sqlNormal.append("temp.REST_TIME_LIMIT, ");
		sqlNormal.append("temp.LATE_NIGHT_TIME_ATR, ");
		sqlNormal.append("temp.LATE_NIGHT_TIME_LIMIT, ");
		sqlNormal.append("temp.LEAVE_LATE, ");
		sqlNormal.append("temp.LEAVE_EARLY, ");
		sqlNormal.append("temp.RAISING_CALC_ATR, ");
		sqlNormal.append("temp.SPECIFIC_RAISING_CALC_ATR, ");
		sqlNormal.append("temp.DIVERGENCE, ");
		sqlNormal.append("CASE WHEN temp.ROW_NUMBER = 1 THEN temp.WKPCD ELSE NULL END, ");
		sqlNormal.append("CASE WHEN temp.ROW_NUMBER = 1 THEN temp.WKP_NAME ELSE NULL END, ");
		sqlNormal.append("temp.JOB_CD, ");
		sqlNormal.append("temp.JOB_NAME ");
		sqlNormal.append("FROM ");
		sqlNormal.append("( ");
		sqlNormal.append("SELECT  ");
		sqlNormal.append("ROW_NUMBER() OVER (PARTITION BY wj.WKPCD ORDER BY  wj.WKPCD, wj.JOB_CD) AS ROW_NUMBER, ");
		sqlNormal.append("wj.LEGAL_OT_TIME_ATR, ");
		sqlNormal.append("wj.LEGAL_OT_TIME_LIMIT, ");
		sqlNormal.append("wj.LEGAL_MID_OT_TIME_ATR, ");
		sqlNormal.append("wj.LEGAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append("wj.NORMAL_OT_TIME_ATR, ");
		sqlNormal.append("wj.NORMAL_OT_TIME_LIMIT, ");
		sqlNormal.append("wj.NORMAL_MID_OT_TIME_ATR, ");
		sqlNormal.append("wj.NORMAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append("wj.EARLY_OT_TIME_ATR, ");
		sqlNormal.append("wj.EARLY_OT_TIME_LIMIT, ");
		sqlNormal.append("wj.EARLY_MID_OT_TIME_ATR, ");
		sqlNormal.append("wj.EARLY_MID_OT_TIME_LIMIT, ");
		sqlNormal.append("wj.FLEX_OT_TIME_ATR, ");
		sqlNormal.append("wj.FLEX_OT_TIME_LIMIT, ");
		sqlNormal.append("wj.REST_TIME_ATR, ");
		sqlNormal.append("wj.REST_TIME_LIMIT, ");
		sqlNormal.append("wj.LATE_NIGHT_TIME_ATR, ");
		sqlNormal.append("wj.LATE_NIGHT_TIME_LIMIT, ");
		sqlNormal.append("wj.LEAVE_LATE, ");
		sqlNormal.append("wj.LEAVE_EARLY, ");
		sqlNormal.append("wj.RAISING_CALC_ATR, ");
		sqlNormal.append("wj.SPECIFIC_RAISING_CALC_ATR, ");
		sqlNormal.append("wj.DIVERGENCE, ");
		sqlNormal.append("wj.WKPCD, ");
		sqlNormal.append("w.WKP_NAME, ");
		sqlNormal.append("wj.JOB_CD, ");
		sqlNormal.append("jn.JOB_NAME ");
		sqlNormal.append("FROM 	(SELECT HIST_ID, WKPID, CID ");
		sqlNormal.append("FROM BSYMT_WORKPLACE_HIST ");
		sqlNormal.append("WHERE END_DATE >= ?baseDate  AND CID = ?cid)  h ");
		sqlNormal.append("INNER JOIN (SELECT HIST_ID, CID, WKPID, WKP_NAME ");
		sqlNormal.append("FROM BSYMT_WORKPLACE_INFO ");
		sqlNormal.append(") w ");
		sqlNormal.append("ON w.HIST_ID = h.HIST_ID AND w.WKPID = h.WKPID AND h.CID = w.CID ");
		sqlNormal.append("RIGHT JOIN (SELECT j.LEGAL_OT_TIME_ATR, ");
		sqlNormal.append("j.LEGAL_OT_TIME_LIMIT, ");
		sqlNormal.append("j.LEGAL_MID_OT_TIME_ATR, ");
		sqlNormal.append("j.LEGAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append("j.NORMAL_OT_TIME_ATR, ");
		sqlNormal.append("j.NORMAL_OT_TIME_LIMIT, ");
		sqlNormal.append("j.NORMAL_MID_OT_TIME_ATR, ");
		sqlNormal.append("j.NORMAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append("j.EARLY_OT_TIME_ATR, ");
		sqlNormal.append("j.EARLY_OT_TIME_LIMIT, ");
		sqlNormal.append("j.EARLY_MID_OT_TIME_ATR, ");
		sqlNormal.append("j.EARLY_MID_OT_TIME_LIMIT, ");
		sqlNormal.append("j.FLEX_OT_TIME_ATR, ");
		sqlNormal.append("j.FLEX_OT_TIME_LIMIT, ");
		sqlNormal.append("j.REST_TIME_ATR, ");
		sqlNormal.append("j.REST_TIME_LIMIT, ");
		sqlNormal.append("j.LATE_NIGHT_TIME_ATR, ");
		sqlNormal.append("j.LATE_NIGHT_TIME_LIMIT, ");
		sqlNormal.append("j.LEAVE_LATE, ");
		sqlNormal.append("j.LEAVE_EARLY, ");
		sqlNormal.append("j.RAISING_CALC_ATR, ");
		sqlNormal.append("j.SPECIFIC_RAISING_CALC_ATR, ");
		sqlNormal.append("j.DIVERGENCE, ");
		sqlNormal.append("j.WPKID, ");
		sqlNormal.append("j.CID, ");
		sqlNormal.append("jf.JOB_ID, ");
		sqlNormal.append("wf.WKPCD, ");
		sqlNormal.append("jf.JOB_CD ");
		sqlNormal.append("FROM (SELECT DISTINCT JOB_ID, JOB_CD, CID ");
		sqlNormal.append("FROM BSYMT_JOB_INFO ");
		sqlNormal.append(" WHERE CID = ?cid) jf ");
		sqlNormal.append("INNER JOIN KSHMT_AUTO_WKP_JOB_CAL j ON j.CID = jf.CID AND j.JOBID = jf.JOB_ID ");
		sqlNormal.append("INNER JOIN (SELECT DISTINCT WKPCD , WKPID, CID ");
		sqlNormal.append("FROM BSYMT_WORKPLACE_INFO ");
		sqlNormal.append("WHERE CID = ?cid)  wf ");
		sqlNormal.append("ON j.WPKID = wf.WKPID AND j.CID = wf.CID  ");
		sqlNormal.append(") wj ");
		sqlNormal.append("ON  wj.WPKID = w.WKPID AND wj.CID = w.CID ");
		sqlNormal.append("LEFT JOIN (SELECT jh.JOB_ID , jh.HIST_ID, ji.JOB_NAME, jh.CID ");
		sqlNormal.append("FROM (SELECT JOB_ID, HIST_ID, CID ");
		sqlNormal.append("FROM BSYMT_JOB_HIST ");
		sqlNormal.append("WHERE END_DATE >= ?baseDate ) jh ");
		sqlNormal.append("INNER JOIN BSYMT_JOB_INFO ji ");
		sqlNormal.append("ON ji.HIST_ID = jh.HIST_ID AND ji.JOB_ID = jh.JOB_ID AND ji.CID = jh.CID ");
		sqlNormal.append(") jn ");
		sqlNormal.append("ON wj.JOB_ID = jn.JOB_ID  AND wj.CID = jn.CID ");
		sqlNormal.append(")temp ");

		GET_ALL_WORKPLACE_JOB = sqlNormal.toString();


	}

	@Override
	public List<Object[]> getWkpJobSettingToExport(String cid, String baseDate) {
		List<Object[]> resultQuery = null;
		try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlDate = null;
            java.util.Date date = null;
            try {
                date = format.parse(baseDate);
                sqlDate = new java.sql.Date(date.getTime());
            } catch (ParseException e) {
                return Collections.emptyList();
            }
			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(GET_ALL_WORKPLACE_JOB)
					.setParameter("cid", cid)
                    .setParameter("baseDate", sqlDate)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}

}
