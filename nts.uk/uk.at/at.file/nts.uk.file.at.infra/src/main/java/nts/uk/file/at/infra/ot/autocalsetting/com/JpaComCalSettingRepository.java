package nts.uk.file.at.infra.ot.autocalsetting.com;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.ot.autocalsetting.com.ComAutoCalSettingRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 * The Class JpaComCalSettingRepository.
 */
@Stateless
public class JpaComCalSettingRepository extends JpaRepository implements ComAutoCalSettingRepository {
	private static final String SELECT_BY_CID = "SELECT " +
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
			"k.DIVERGENCE  "+
			"FROM KSHMT_AUTO_COM_CAL_SET k WHERE k.CID = ?cid";

	@Override
	public Object[] getCompanySettingToExport(String cid) {
		Object[] resultQuery = null;
		try {
			resultQuery = (Object[]) this.getEntityManager().createNativeQuery(SELECT_BY_CID)
					.setParameter("cid", cid)
					.getSingleResult();
		} catch (NoResultException e) {
			return resultQuery;
		}
		return resultQuery;
	}

}
