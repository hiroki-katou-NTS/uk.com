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

	private static final String SELECT_ALL_POSITION_BY_CID = " SELECT " +
			"k.LEGAL_OT_TIME_ATR, "+
			"k.LEGAL_OT_TIME_LIMIT, "+
			"k.LEGAL_MID_OT_TIME_ATR, "+
			"k.LEGAL_MID_OT_TIME_LIMIT, "+
			"k.NORMAL_OT_TIME_ATR, "+
			"k.NORMAL_OT_TIME_LIMIT, "+
			"k.NORMAL_MID_OT_TIME_ATR, "+
			"k.NORMAL_MID_OT_TIME_LIMIT, "+
			"k.EARLY_OT_TIME_ATR, "+
			"k.EARLY_OT_TIME_LIMIT, "+
			"k.EARLY_MID_OT_TIME_ATR, "+
			"k.EARLY_MID_OT_TIME_LIMIT, "+
			"k.FLEX_OT_TIME_ATR, "+
			"k.FLEX_OT_TIME_LIMIT, "+
			"k.REST_TIME_ATR, "+
			"k.REST_TIME_LIMIT, "+
			"k.LATE_NIGHT_TIME_ATR, "+
			"k.LATE_NIGHT_TIME_LIMIT, "+
			"k.LEAVE_LATE, "+
			"k.LEAVE_EARLY, "+
			"k.RAISING_CALC_ATR, "+
			"k.SPECIFIC_RAISING_CALC_ATR, "+
			"k.DIVERGENCE,  "+
			"w.JOB_CD,  " +
			"w.JOB_NAME  " +
			"FROM  (SELECT JOB_CD, JOB_NAME, JOB_ID, HIST_ID, CID " +
					"FROM BSYMT_JOB_INFO "+
					"WHERE CID = ?cid ) w "+
			"INNER JOIN (SELECT HIST_ID, JOB_ID, CID "+
					"FROM BSYMT_JOB_HIST " +
					"WHERE START_DATE <= ?baseDate AND END_DATE >= ?baseDate) h " +
					"ON w.HIST_ID = h.HIST_ID AND w.JOB_ID = h.JOB_ID AND w.CID = h.CID "+
			"INNER JOIN  KSHMT_AUTO_JOB_CAL_SET k on w.JOB_ID = k.JOBID AND k.CID = w.CID "+
			"ORDER BY w.JOB_CD ";

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
