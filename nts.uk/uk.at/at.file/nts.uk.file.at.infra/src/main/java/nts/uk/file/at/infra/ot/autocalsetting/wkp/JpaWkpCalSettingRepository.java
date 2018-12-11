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
			"w.WKPCD, " +
			"w.WKP_NAME " +
            "FROM (SELECT HIST_ID, CID, WKPID, WKPCD, WKP_NAME "+
					"FROM BSYMT_WORKPLACE_INFO "+
					"WHERE CID = ?cid) w  "+
			"INNER JOIN (SELECT CID "+
					"FROM BSYMT_WKP_CONFIG "+
					"WHERE START_DATE <= ?baseDate AND END_DATE >= ?baseDate ) c "+
				"ON c.CID = w.CID  "+
            "INNER JOIN (SELECT HIST_ID, WKPID, CID "+
			"FROM BSYMT_WORKPLACE_HIST "+
			"WHERE START_DATE <= ?baseDate AND END_DATE >= ?baseDate )  h "+
			"ON w.HIST_ID = h.HIST_ID AND w.WKPID = h.WKPID AND h.CID = w.CID "+
            "INNER JOIN KSHMT_AUTO_WKP_CAL_SET k on w.WKPID = k.WKPID " +
			"ORDER BY w.WKPCD";

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
