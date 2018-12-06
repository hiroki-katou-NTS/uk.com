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

	private static final String GET_ALL_WORKPLACE_JOB = "SELECT  "+
			"wj.LEGAL_OT_TIME_ATR, "+
			"wj.LEGAL_OT_TIME_LIMIT, "+
			"wj.LEGAL_MID_OT_TIME_ATR, "+
			"wj.LEGAL_MID_OT_TIME_LIMIT, "+
			"wj.NORMAL_OT_TIME_ATR, "+
			"wj.NORMAL_OT_TIME_LIMIT, "+
			"wj.NORMAL_MID_OT_TIME_ATR, "+
			"wj.NORMAL_MID_OT_TIME_LIMIT, "+
			"wj.EARLY_OT_TIME_ATR, "+
			"wj.EARLY_OT_TIME_LIMIT, "+
			"wj.EARLY_MID_OT_TIME_ATR, "+
			"wj.EARLY_MID_OT_TIME_LIMIT, "+
			"wj.FLEX_OT_TIME_ATR, "+
			"wj.FLEX_OT_TIME_LIMIT, "+
			"wj.REST_TIME_ATR, "+
			"wj.REST_TIME_LIMIT, "+
			"wj.LATE_NIGHT_TIME_ATR, "+
			"wj.LATE_NIGHT_TIME_LIMIT, "+
			"wj.LEAVE_LATE, "+
			"wj.LEAVE_EARLY, "+
			"wj.RAISING_CALC_ATR, "+
			"wj.SPECIFIC_RAISING_CALC_ATR, "+
			"wj.DIVERGENCE,  "+
			"w.WKPCD, " +
			"w.WKP_NAME, "+
			"j.JOB_CD, "+
			"j.JOB_NAME "+

	"FROM BSYMT_WORKPLACE_INFO w "+
		"INNER JOIN BSYMT_WKP_CONFIG c ON c.CID = w.CID "+
		"INNER JOIN BSYMT_WORKPLACE_HIST h ON w.HIST_ID = h.HIST_ID AND w.WKPID = h.WKPID AND h.CID = w.CID "+
		"INNER JOIN KSHMT_AUTO_WKP_JOB_CAL wj on  wj.WPKID = w.WKPID AND wj.CID = w.CID "+
		"INNER JOIN BSYMT_JOB_INFO j ON wj.JOBID = j.JOB_ID AND wj.CID = j.CID "+
		"INNER JOIN BSYMT_JOB_HIST jh on j.HIST_ID = jh.HIST_ID AND j.JOB_ID = jh.JOB_ID AND j.CID = jh.CID "+
	"WHERE wj.CID = ?cid " +
		"AND h.START_DATE <= ?baseDate AND h.END_DATE >= ?baseDate "+
		"AND c.START_DATE <= ?baseDate AND c.END_DATE >= ?baseDate "+
		"AND jh.START_DATE <= ?baseDate AND jh.END_DATE >= ?baseDate "+
	"ORDER BY w.WKPCD, j.JOB_CD ";

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
