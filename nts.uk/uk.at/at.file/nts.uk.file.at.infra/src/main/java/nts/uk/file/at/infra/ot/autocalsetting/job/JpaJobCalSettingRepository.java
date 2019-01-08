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
		StringBuilder sqlNormal = new StringBuilder();
		sqlNormal.append(" SELECT " );
		sqlNormal.append(	"k.LEGAL_OT_TIME_ATR, ");
		sqlNormal.append(	"k.LEGAL_OT_TIME_LIMIT, ");
		sqlNormal.append(	"k.LEGAL_MID_OT_TIME_ATR, ");
		sqlNormal.append(	"k.LEGAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append(	"k.NORMAL_OT_TIME_ATR, ");
		sqlNormal.append(	"k.NORMAL_OT_TIME_LIMIT, ");
		sqlNormal.append(	"k.NORMAL_MID_OT_TIME_ATR, ");
		sqlNormal.append(	"k.NORMAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append(	"k.EARLY_OT_TIME_ATR, ");
		sqlNormal.append(	"k.EARLY_OT_TIME_LIMIT, ");
		sqlNormal.append(	"k.EARLY_MID_OT_TIME_ATR, ");
		sqlNormal.append(	"k.EARLY_MID_OT_TIME_LIMIT, ");
		sqlNormal.append(	"k.FLEX_OT_TIME_ATR, ");
		sqlNormal.append(	"k.FLEX_OT_TIME_LIMIT, ");
		sqlNormal.append(	"k.REST_TIME_ATR, ");
		sqlNormal.append(	"k.REST_TIME_LIMIT, ");
		sqlNormal.append(	"k.LATE_NIGHT_TIME_ATR, ");
		sqlNormal.append(	"k.LATE_NIGHT_TIME_LIMIT, ");
		sqlNormal.append(	"k.LEAVE_LATE, ");
		sqlNormal.append(	"k.LEAVE_EARLY, ");
		sqlNormal.append(	"k.RAISING_CALC_ATR, ");
		sqlNormal.append(	"k.SPECIFIC_RAISING_CALC_ATR, ");
		sqlNormal.append(	"k.DIVERGENCE,  ");
		sqlNormal.append(	"k.JOB_CD,  " );
		sqlNormal.append(	"w.JOB_NAME  " );
		sqlNormal.append(	"FROM  (SELECT HIST_ID, JOB_ID, CID ");
		sqlNormal.append(			"FROM BSYMT_JOB_HIST ");
		sqlNormal.append(			"WHERE  END_DATE >= ?baseDate AND CID = ?cid) h ");
		sqlNormal.append(	"INNER JOIN (SELECT JOB_NAME, JOB_ID, HIST_ID, CID ");
		sqlNormal.append(				"FROM BSYMT_JOB_INFO ");
		sqlNormal.append(			") w ");
		sqlNormal.append(		"ON w.HIST_ID = h.HIST_ID AND w.JOB_ID = h.JOB_ID AND w.CID = h.CID ");
		sqlNormal.append(	"RIGHT JOIN (SELECT j.LEGAL_OT_TIME_ATR, ");
		sqlNormal.append(						"j.LEGAL_OT_TIME_LIMIT, ");
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
		sqlNormal.append("i.JOB_CD, ");
		sqlNormal.append("i.JOB_ID, ");
		sqlNormal.append("i.CID ");
		sqlNormal.append("FROM  (SELECT DISTINCT JOB_ID, JOB_CD, CID ");
		sqlNormal.append(		"FROM BSYMT_JOB_INFO " );
		sqlNormal.append(		"WHERE CID = ?cid) i ");
		sqlNormal.append("INNER JOIN KSHMT_AUTO_JOB_CAL_SET j ON j.CID = i.CID AND j.JOBID = i.JOB_ID ) k ");
		sqlNormal.append("ON w.JOB_ID = k.JOB_ID AND k.CID = w.CID ");
		sqlNormal.append("ORDER BY k.JOB_CD ");
		SELECT_ALL_POSITION_BY_CID = sqlNormal.toString();
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
