package nts.uk.file.at.infra.ot.autocalsetting.wkp;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.ot.autocalsetting.wkp.WkpAutoCalSettingRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * The Class JpaWkpCalSettingRepository.
 */
@Stateless
public class JpaWkpCalSettingRepository extends JpaRepository implements WkpAutoCalSettingRepository {

	private static final String SELECT_ALL_WORKPLACE_BY_CID = " SELECT " +
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
			"k.WKPCD, " +
			"w.WKP_NAME " +
            "FROM (SELECT HIST_ID, WKPID, CID " +
					"FROM BSYMT_WORKPLACE_HIST "+
					"WHERE END_DATE >= ?baseDate AND CID = ?cid " +
					") h " +
			"INNER JOIN (SELECT HIST_ID, CID, WKPID, WKPCD, WKP_NAME " +
						  "FROM BSYMT_WORKPLACE_INFO " +
						  ") w " +
					"ON w.HIST_ID = h.HIST_ID AND w.WKPID = h.WKPID AND h.CID = w.CID " +
			"RIGHT JOIN (SELECT a.LEGAL_OT_TIME_ATR, "+
								  "a.LEGAL_OT_TIME_LIMIT, "+
								  "a.LEGAL_MID_OT_TIME_ATR, "+
								  "a.LEGAL_MID_OT_TIME_LIMIT, "+
								  "a.NORMAL_OT_TIME_ATR, "+
								  "a.NORMAL_OT_TIME_LIMIT, "+
								  "a.NORMAL_MID_OT_TIME_ATR, "+
								  "a.NORMAL_MID_OT_TIME_LIMIT, "+
								  "a.EARLY_OT_TIME_ATR, "+
								  "a.EARLY_OT_TIME_LIMIT, "+
								  "a.EARLY_MID_OT_TIME_ATR, "+
								  "a.EARLY_MID_OT_TIME_LIMIT, "+
								  "a.FLEX_OT_TIME_ATR, "+
								  "a.FLEX_OT_TIME_LIMIT, "+
								  "a.REST_TIME_ATR, "+
								  "a.REST_TIME_LIMIT, "+
								  "a.LATE_NIGHT_TIME_ATR, "+
								  "a.LATE_NIGHT_TIME_LIMIT, "+
								  "a.LEAVE_LATE, "+
								  "a.LEAVE_EARLY, "+
								  "a.RAISING_CALC_ATR, "+
								  "a.SPECIFIC_RAISING_CALC_ATR, "+
								  "a.DIVERGENCE, "+
								  "i.WKPCD, "+
								  "a.WKPID, "+
								  "a.CID "+
						"FROM (SELECT DISTINCT WKPCD , WKPID, CID "+
							   "FROM BSYMT_WORKPLACE_INFO "+
							   "WHERE CID = '000000000000-0001') i "+
						"INNER JOIN KSHMT_AUTO_WKP_CAL_SET a ON a.CID = i.CID AND a.WKPID = i.WKPID) k "+
							"ON w.WKPID = k.WKPID AND w.CID = k.CID "+
			"ORDER BY k.WKPCD ";

	@Override
	public List<Object[]> getWorkPlaceSettingToExport(String cid, String baseDate) {
		List<Object[]> resultQuery = null;
		try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = null;
			Date sqlDate = null;
            try {
                date = format.parse(baseDate);
				sqlDate = new Date(date.getTime());
            } catch (ParseException e) {
                return Collections.emptyList();
            }
			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(SELECT_ALL_WORKPLACE_BY_CID)
					.setParameter("cid", cid)
                    .setParameter("baseDate", sqlDate)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}
}
