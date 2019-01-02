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

	private static final String SELECT_ALL_WORKPLACE_BY_CID ;
	static {
		StringBuilder sqlNormal = new StringBuilder();

		sqlNormal.append("SELECT " );
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
		sqlNormal.append(	"k.WKPCD, " );
		sqlNormal.append(	"w.WKP_NAME " );
		sqlNormal.append(    "FROM (SELECT HIST_ID, WKPID, CID " );
		sqlNormal.append(			"FROM BSYMT_WORKPLACE_HIST ");
		sqlNormal.append(			"WHERE END_DATE >= ?baseDate AND CID = ?cid " );
		sqlNormal.append(			") h " );
		sqlNormal.append(	"INNER JOIN (SELECT HIST_ID, CID, WKPID, WKPCD, WKP_NAME " );
		sqlNormal.append(				  "FROM BSYMT_WORKPLACE_INFO " );
		sqlNormal.append(				  ") w " );
		sqlNormal.append(			"ON w.HIST_ID = h.HIST_ID AND w.WKPID = h.WKPID AND h.CID = w.CID " );
		sqlNormal.append(	"RIGHT JOIN (SELECT a.LEGAL_OT_TIME_ATR, ");
		sqlNormal.append(						  "a.LEGAL_OT_TIME_LIMIT, ");
		sqlNormal.append(						  "a.LEGAL_MID_OT_TIME_ATR, ");
		sqlNormal.append(						  "a.LEGAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append(						  "a.NORMAL_OT_TIME_ATR, ");
		sqlNormal.append(						  "a.NORMAL_OT_TIME_LIMIT, ");
		sqlNormal.append(						  "a.NORMAL_MID_OT_TIME_ATR, ");
		sqlNormal.append(						  "a.NORMAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append(						  "a.EARLY_OT_TIME_ATR, ");
		sqlNormal.append(						  "a.EARLY_OT_TIME_LIMIT, ");
		sqlNormal.append(						  "a.EARLY_MID_OT_TIME_ATR, ");
		sqlNormal.append(						  "a.EARLY_MID_OT_TIME_LIMIT, ");
		sqlNormal.append(						  "a.FLEX_OT_TIME_ATR, ");
		sqlNormal.append(						  "a.FLEX_OT_TIME_LIMIT, ");
		sqlNormal.append(						  "a.REST_TIME_ATR, ");
		sqlNormal.append(						  "a.REST_TIME_LIMIT, ");
		sqlNormal.append(						  "a.LATE_NIGHT_TIME_ATR, ");
		sqlNormal.append(						  "a.LATE_NIGHT_TIME_LIMIT, ");
		sqlNormal.append(						  "a.LEAVE_LATE, ");
		sqlNormal.append(						  "a.LEAVE_EARLY, ");
		sqlNormal.append(						  "a.RAISING_CALC_ATR, ");
		sqlNormal.append(						  "a.SPECIFIC_RAISING_CALC_ATR, ");
		sqlNormal.append(						  "a.DIVERGENCE, ");
		sqlNormal.append(						  "i.WKPCD, ");
		sqlNormal.append(						  "a.WKPID, ");
		sqlNormal.append(						  "a.CID ");
		sqlNormal.append(				"FROM (SELECT DISTINCT WKPCD , WKPID, CID ");
		sqlNormal.append(					   "FROM BSYMT_WORKPLACE_INFO ");
		sqlNormal.append(					   "WHERE CID = '000000000000-0001') i ");
		sqlNormal.append(				"INNER JOIN KSHMT_AUTO_WKP_CAL_SET a ON a.CID = i.CID AND a.WKPID = i.WKPID) k ");
		sqlNormal.append(					"ON w.WKPID = k.WKPID AND w.CID = k.CID ");
		sqlNormal.append(	"ORDER BY k.WKPCD ");
		SELECT_ALL_WORKPLACE_BY_CID = sqlNormal.toString();
}

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
