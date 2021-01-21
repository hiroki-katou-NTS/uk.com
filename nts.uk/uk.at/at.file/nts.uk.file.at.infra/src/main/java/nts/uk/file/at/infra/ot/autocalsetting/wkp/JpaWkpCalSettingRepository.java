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
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  ");
		sql.append("   k.LEGAL_OT_TIME_ATR, ");
		sql.append("   k.LEGAL_OT_TIME_LIMIT, ");
		sql.append("   k.LEGAL_MID_OT_TIME_ATR, ");
		sql.append("   k.LEGAL_MID_OT_TIME_LIMIT, ");
		sql.append("   k.NORMAL_OT_TIME_ATR, ");
		sql.append("   k.NORMAL_OT_TIME_LIMIT, ");
		sql.append("   k.NORMAL_MID_OT_TIME_ATR, ");
		sql.append("   k.NORMAL_MID_OT_TIME_LIMIT, ");
		sql.append("   k.EARLY_OT_TIME_ATR, ");
		sql.append("   k.EARLY_OT_TIME_LIMIT, ");
		sql.append("   k.EARLY_MID_OT_TIME_ATR, ");
		sql.append("   k.EARLY_MID_OT_TIME_LIMIT, ");
		sql.append("   k.FLEX_OT_TIME_ATR, ");
		sql.append("   k.FLEX_OT_TIME_LIMIT, ");
		sql.append("   k.REST_TIME_ATR, ");
		sql.append("   k.REST_TIME_LIMIT, ");
		sql.append("   k.LATE_NIGHT_TIME_ATR, ");
		sql.append("   k.LATE_NIGHT_TIME_LIMIT, ");
		sql.append("   k.LEAVE_LATE, ");
		sql.append("   k.LEAVE_EARLY, ");
		sql.append("   k.RAISING_CALC_ATR, ");
		sql.append("   k.SPECIFIC_RAISING_CALC_ATR, ");
		sql.append("   k.DIVERGENCE,  ");
		sql.append("   k.WKP_CD,  ");
		sql.append("   w.WKP_NAME,  ");
		sql.append("   k.WKPID ,");
		sql.append("   HIERARCHY_CD");
		sql.append("FROM ");
		sql.append("  (SELECT WKP_HIST_ID, CID, WKP_ID, WKP_CD, WKP_NAME, HIERARCHY_CD  ");
		sql.append("   FROM BSYMT_WKP_INFO) w  ");
		sql.append("  INNER JOIN BSYMT_WKP_CONFIG_2 wc ");
		sql.append("     ON wc.CID = w.CID AND wc.END_DATE = ?baseDate");
		sql.append("  RIGHT JOIN (SELECT a.LEGAL_OT_TIME_ATR, ");
		sql.append("          a.LEGAL_OT_TIME_LIMIT, ");
		sql.append("          a.LEGAL_MID_OT_TIME_ATR, ");
		sql.append("          a.LEGAL_MID_OT_TIME_LIMIT, ");
		sql.append("          a.NORMAL_OT_TIME_ATR, ");
		sql.append("          a.NORMAL_OT_TIME_LIMIT, ");
		sql.append("          a.NORMAL_MID_OT_TIME_ATR, ");
		sql.append("          a.NORMAL_MID_OT_TIME_LIMIT, ");
		sql.append("          a.EARLY_OT_TIME_ATR, ");
		sql.append("          a.EARLY_OT_TIME_LIMIT, ");
		sql.append("          a.EARLY_MID_OT_TIME_ATR, ");
		sql.append("          a.EARLY_MID_OT_TIME_LIMIT, ");
		sql.append("          a.FLEX_OT_TIME_ATR, ");
		sql.append("          a.FLEX_OT_TIME_LIMIT, ");
		sql.append("          a.REST_TIME_ATR, ");
		sql.append("          a.REST_TIME_LIMIT, ");
		sql.append("          a.LATE_NIGHT_TIME_ATR, ");
		sql.append("          a.LATE_NIGHT_TIME_LIMIT, ");
		sql.append("          a.LEAVE_LATE, ");
		sql.append("          a.LEAVE_EARLY, ");
		sql.append("          a.RAISING_CALC_ATR, ");
		sql.append("          a.SPECIFIC_RAISING_CALC_ATR, ");
		sql.append("          a.DIVERGENCE, ");
		sql.append("          i.WKP_CD, ");
		sql.append("          a.WKPID, ");
		sql.append("          a.CID ");
		sql.append("      FROM (SELECT DISTINCT WKP_CD , WKP_ID, CID ");
		sql.append("          FROM BSYMT_WKP_INFO ");
		sql.append("          WHERE CID = ?cid) i ");
		sql.append("      RIGHT JOIN (SELECT  LEGAL_OT_TIME_ATR, ");
		sql.append("                LEGAL_OT_TIME_LIMIT, ");
		sql.append("                LEGAL_MID_OT_TIME_ATR, ");
		sql.append("                LEGAL_MID_OT_TIME_LIMIT, ");
		sql.append("                NORMAL_OT_TIME_ATR, ");
		sql.append("                NORMAL_OT_TIME_LIMIT, ");
		sql.append("                NORMAL_MID_OT_TIME_ATR, ");
		sql.append("                NORMAL_MID_OT_TIME_LIMIT, ");
		sql.append("                EARLY_OT_TIME_ATR, ");
		sql.append("                EARLY_OT_TIME_LIMIT, ");
		sql.append("                EARLY_MID_OT_TIME_ATR, ");
		sql.append("                EARLY_MID_OT_TIME_LIMIT, ");
		sql.append("                FLEX_OT_TIME_ATR, ");
		sql.append("                FLEX_OT_TIME_LIMIT, ");
		sql.append("                REST_TIME_ATR, ");
		sql.append("                REST_TIME_LIMIT, ");
		sql.append("                LATE_NIGHT_TIME_ATR, ");
		sql.append("                LATE_NIGHT_TIME_LIMIT, ");
		sql.append("                LEAVE_LATE, ");
		sql.append("                LEAVE_EARLY, ");
		sql.append("                RAISING_CALC_ATR, ");
		sql.append("                SPECIFIC_RAISING_CALC_ATR, ");
		sql.append("                DIVERGENCE, ");
		sql.append("                WKPID, ");
		sql.append("                CID ");
		sql.append("            FROM KRCMT_CALC_SET_WKP ");
		sql.append("            WHERE CID = ?cid)");
		sql.append("            a ON a.CID = i.CID AND a.WKPID = i.WKP_ID) k ");
		sql.append("      ON w.WKP_ID = k.WKPID AND w.CID = k.CID");
		sql.append("ORDER BY CASE WHEN w.HIERARCHY_CD IS NULL THEN 1 ELSE 0 END ASC, HIERARCHY_CD");
		SELECT_ALL_WORKPLACE_BY_CID = sql.toString();
}


	@SuppressWarnings("unchecked")
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
