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

	private static final String GET_ALL_WORKPLACE_JOB =
	"SELECT " +
		"temp.LEGAL_OT_TIME_ATR, "+
		"temp.LEGAL_OT_TIME_LIMIT, "+
		"temp.LEGAL_MID_OT_TIME_ATR, "+
		"temp.LEGAL_MID_OT_TIME_LIMIT, "+
		"temp.NORMAL_OT_TIME_ATR, "+
		"temp.NORMAL_OT_TIME_LIMIT, "+
		"temp.NORMAL_MID_OT_TIME_ATR, "+
		"temp.NORMAL_MID_OT_TIME_LIMIT, "+
		"temp.EARLY_OT_TIME_ATR, "+
		"temp.EARLY_OT_TIME_LIMIT, "+
		"temp.EARLY_MID_OT_TIME_ATR, "+
		"temp.EARLY_MID_OT_TIME_LIMIT, "+
		"temp.FLEX_OT_TIME_ATR, "+
		"temp.FLEX_OT_TIME_LIMIT, "+
		"temp.REST_TIME_ATR, "+
		"temp.REST_TIME_LIMIT, "+
		"temp.LATE_NIGHT_TIME_ATR, "+
		"temp.LATE_NIGHT_TIME_LIMIT, "+
		"temp.LEAVE_LATE, "+
		"temp.LEAVE_EARLY, "+
		"temp.RAISING_CALC_ATR, "+
		"temp.SPECIFIC_RAISING_CALC_ATR, "+
		"temp.DIVERGENCE, "+
		"CASE WHEN temp.ROW_NUMBER = 1 THEN temp.WKPCD ELSE NULL END, "+
		"CASE WHEN temp.ROW_NUMBER = 1 THEN temp.WKP_NAME ELSE NULL END, "+
		"temp.JOB_CD, "+
		"temp.JOB_NAME "+
	"FROM "+
		"( " +
			"SELECT  "+
					"ROW_NUMBER() OVER (PARTITION BY wj.WKPCD ORDER BY  wj.WKPCD, wj.JOB_CD) AS ROW_NUMBER, "+
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
					"wj.DIVERGENCE, "+
					"wj.WKPCD, "+
					"w.WKP_NAME, "+
					"wj.JOB_CD, "+
					"jn.JOB_NAME "+
			"FROM 	(SELECT HIST_ID, WKPID, CID "+
					"FROM BSYMT_WORKPLACE_HIST "+
				  	 "WHERE END_DATE >= ?baseDate  AND CID = ?cid)  h "+
					"INNER JOIN (SELECT HIST_ID, CID, WKPID, WKP_NAME "+
								 "FROM BSYMT_WORKPLACE_INFO "+
		") w "+
	"ON w.HIST_ID = h.HIST_ID AND w.WKPID = h.WKPID AND h.CID = w.CID "+
	"RIGHT JOIN (SELECT j.LEGAL_OT_TIME_ATR, "+
				 		"j.LEGAL_OT_TIME_LIMIT, "+
						"j.LEGAL_MID_OT_TIME_ATR, "+
						"j.LEGAL_MID_OT_TIME_LIMIT, "+
						"j.NORMAL_OT_TIME_ATR, "+
						"j.NORMAL_OT_TIME_LIMIT, "+
						"j.NORMAL_MID_OT_TIME_ATR, "+
						"j.NORMAL_MID_OT_TIME_LIMIT, "+
						"j.EARLY_OT_TIME_ATR, "+
						"j.EARLY_OT_TIME_LIMIT, "+
						"j.EARLY_MID_OT_TIME_ATR, "+
						"j.EARLY_MID_OT_TIME_LIMIT, "+
						"j.FLEX_OT_TIME_ATR, "+
						"j.FLEX_OT_TIME_LIMIT, "+
						"j.REST_TIME_ATR, "+
						"j.REST_TIME_LIMIT, "+
						"j.LATE_NIGHT_TIME_ATR, "+
						"j.LATE_NIGHT_TIME_LIMIT, "+
						"j.LEAVE_LATE, "+
						"j.LEAVE_EARLY, "+
						"j.RAISING_CALC_ATR, "+
						"j.SPECIFIC_RAISING_CALC_ATR, "+
						"j.DIVERGENCE, "+
						"j.WPKID, "+
						"j.CID, "+
						"jf.JOB_ID, "+
						"wf.WKPCD, "+
						"jf.JOB_CD "+
				"FROM (SELECT DISTINCT JOB_ID, JOB_CD, CID "+
						"FROM BSYMT_JOB_INFO "+
						" WHERE CID = ?cid) jf "+
				"INNER JOIN KSHMT_AUTO_WKP_JOB_CAL j ON j.CID = jf.CID AND j.JOBID = jf.JOB_ID "+
				"INNER JOIN (SELECT DISTINCT WKPCD , WKPID, CID "+
							"FROM BSYMT_WORKPLACE_INFO "+
							"WHERE CID = ?cid)  wf "+
				"ON j.WPKID = wf.WKPID AND j.CID = wf.CID  "+
			") wj "+
	"ON  wj.WPKID = w.WKPID AND wj.CID = w.CID "+
	"LEFT JOIN (SELECT jh.JOB_ID , jh.HIST_ID, ji.JOB_NAME, jh.CID "+
				"FROM (SELECT JOB_ID, HIST_ID, CID "+
					   "FROM BSYMT_JOB_HIST "+
					   "WHERE END_DATE >= ?baseDate ) jh "+
				"INNER JOIN BSYMT_JOB_INFO ji "+
				"ON ji.HIST_ID = jh.HIST_ID AND ji.JOB_ID = jh.JOB_ID AND ji.CID = jh.CID "+
				") jn "+
	"ON wj.JOB_ID = jn.JOB_ID  AND wj.CID = jn.CID "+
		")temp ";

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
