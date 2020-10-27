package nts.uk.file.at.infra.ot.autocalsetting.job;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.ot.autocalsetting.job.JobAutoCalSettingRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * The Class JpaJobCalSettingRepository.
 */
@Stateless
public class JpaJobCalSettingRepository extends JpaRepository implements JobAutoCalSettingRepository {

	private static final String SELECT_ALL_POSITION_BY_CID ;
	static {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  ");
		sql.append("      k.LEGAL_OT_TIME_ATR, ");
		sql.append("      k.LEGAL_OT_TIME_LIMIT, ");
		sql.append("      k.LEGAL_MID_OT_TIME_ATR, ");
		sql.append("      k.LEGAL_MID_OT_TIME_LIMIT, ");
		sql.append("      k.NORMAL_OT_TIME_ATR, ");
		sql.append("      k.NORMAL_OT_TIME_LIMIT, ");
		sql.append("      k.NORMAL_MID_OT_TIME_ATR, ");
		sql.append("      k.NORMAL_MID_OT_TIME_LIMIT, ");
		sql.append("      k.EARLY_OT_TIME_ATR, ");
		sql.append("      k.EARLY_OT_TIME_LIMIT, ");
		sql.append("      k.EARLY_MID_OT_TIME_ATR, ");
		sql.append("      k.EARLY_MID_OT_TIME_LIMIT, ");
		sql.append("      k.FLEX_OT_TIME_ATR, ");
		sql.append("      k.FLEX_OT_TIME_LIMIT, ");
		sql.append("      k.REST_TIME_ATR, ");
		sql.append("      k.REST_TIME_LIMIT, ");
		sql.append("      k.LATE_NIGHT_TIME_ATR, ");
		sql.append("      k.LATE_NIGHT_TIME_LIMIT, ");
		sql.append("      k.LEAVE_LATE, ");
		sql.append("      k.LEAVE_EARLY, ");
		sql.append("      k.RAISING_CALC_ATR, ");
		sql.append("      k.SPECIFIC_RAISING_CALC_ATR, ");
		sql.append("      k.DIVERGENCE,  ");
		sql.append("      k.JOB_CD,   ");
		sql.append("      w.JOB_NAME   ");
		sql.append("   FROM  (SELECT HIST_ID, JOB_ID, CID ");
		sql.append("     FROM BSYMT_JOB_HIST ");
		sql.append("     WHERE  END_DATE >= ?baseDate AND CID = ?cid) h ");
		sql.append("   INNER JOIN (SELECT JOB_NAME, JOB_ID, HIST_ID, CID ");
		sql.append("      FROM BSYMT_JOB_INFO ");
		sql.append("     ) w ");
		sql.append("    ON w.HIST_ID = h.HIST_ID AND w.JOB_ID = h.JOB_ID AND w.CID = h.CID ");
		sql.append("   RIGHT JOIN (SELECT j.LEGAL_OT_TIME_ATR, ");
		sql.append("             j.LEGAL_OT_TIME_LIMIT, ");
		sql.append("             j.LEGAL_MID_OT_TIME_ATR, ");
		sql.append("             j.LEGAL_MID_OT_TIME_LIMIT, ");
		sql.append("             j.NORMAL_OT_TIME_ATR, ");
		sql.append("             j.NORMAL_OT_TIME_LIMIT, ");
		sql.append("             j.NORMAL_MID_OT_TIME_ATR, ");
		sql.append("             j.NORMAL_MID_OT_TIME_LIMIT, ");
		sql.append("             j.EARLY_OT_TIME_ATR, ");
		sql.append("             j.EARLY_OT_TIME_LIMIT, ");
		sql.append("             j.EARLY_MID_OT_TIME_ATR, ");
		sql.append("             j.EARLY_MID_OT_TIME_LIMIT, ");
		sql.append("             j.FLEX_OT_TIME_ATR, ");
		sql.append("             j.FLEX_OT_TIME_LIMIT, ");
		sql.append("             j.REST_TIME_ATR, ");
		sql.append("             j.REST_TIME_LIMIT, ");
		sql.append("             j.LATE_NIGHT_TIME_ATR, ");
		sql.append("             j.LATE_NIGHT_TIME_LIMIT, ");
		sql.append("             j.LEAVE_LATE, ");
		sql.append("             j.LEAVE_EARLY, ");
		sql.append("             j.RAISING_CALC_ATR, ");
		sql.append("             j.SPECIFIC_RAISING_CALC_ATR, ");
		sql.append("             j.DIVERGENCE, ");
		sql.append("             i.JOB_CD, ");
		sql.append("             i.JOB_ID, ");
		sql.append("             i.CID ");
		sql.append("  FROM  (SELECT DISTINCT JOB_ID, JOB_CD, CID ");
		sql.append("     FROM BSYMT_JOB_INFO  ");
		sql.append("     WHERE CID =  ?cid) i ");
		sql.append("  RIGHT JOIN (SELECT LEGAL_OT_TIME_ATR, ");
		sql.append("           LEGAL_OT_TIME_LIMIT, ");
		sql.append("           LEGAL_MID_OT_TIME_ATR, ");
		sql.append("           LEGAL_MID_OT_TIME_LIMIT, ");
		sql.append("           NORMAL_OT_TIME_ATR, ");
		sql.append("            NORMAL_OT_TIME_LIMIT, ");
		sql.append("           NORMAL_MID_OT_TIME_ATR, ");
		sql.append("           NORMAL_MID_OT_TIME_LIMIT, ");
		sql.append("           EARLY_OT_TIME_ATR, ");
		sql.append("           EARLY_OT_TIME_LIMIT, ");
		sql.append("           EARLY_MID_OT_TIME_ATR, ");
		sql.append("           EARLY_MID_OT_TIME_LIMIT, ");
		sql.append("           FLEX_OT_TIME_ATR, ");
		sql.append("           FLEX_OT_TIME_LIMIT, ");
		sql.append("           REST_TIME_ATR, ");
		sql.append("           REST_TIME_LIMIT, ");
		sql.append("           LATE_NIGHT_TIME_ATR, ");
		sql.append("           LATE_NIGHT_TIME_LIMIT, ");
		sql.append("           LEAVE_LATE, ");
		sql.append("           LEAVE_EARLY, ");
		sql.append("           RAISING_CALC_ATR, ");
		sql.append("           SPECIFIC_RAISING_CALC_ATR, ");
		sql.append("           DIVERGENCE, ");
		sql.append("           JOBID, ");
		sql.append("           CID  ");
		sql.append("         FROM KRCMT_CALC_SET_JOB");
		sql.append("         WHERE CID = ?cid)j ");
		sql.append("         ON j.CID = i.CID AND j.JOBID = i.JOB_ID ) k ");
		sql.append("      ON w.JOB_ID = k.JOB_ID AND k.CID = w.CID ");
		sql.append("   ORDER BY  CASE WHEN k.JOB_CD IS NULL OR  w.JOB_NAME IS NULL THEN 1 ELSE 0 END ASC, k.JOB_CD");
		SELECT_ALL_POSITION_BY_CID = sql.toString();
	}


	@Override
	public List<Object[]> getPositionSettingToExport(String cid, String baseDate) {
		List<Object[]> resultQuery = null;
		try {
			java.util.Date date = null;
			java.sql.Date sqlDate = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = format.parse(baseDate);
				sqlDate = new java.sql.Date(date.getTime());
			} catch (ParseException e) {
				return Collections.emptyList();
			}
			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(SELECT_ALL_POSITION_BY_CID)
					.setParameter("cid", cid)
					.setParameter("baseDate", sqlDate)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}

}
