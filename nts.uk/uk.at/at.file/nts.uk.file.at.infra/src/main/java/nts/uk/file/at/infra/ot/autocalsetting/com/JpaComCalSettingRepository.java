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
	private static final String SELECT_BY_CID ;
	static {
		StringBuilder sqlNormal = new StringBuilder();
		sqlNormal.append("SELECT " );
		sqlNormal.append(		"k.LEGAL_OT_TIME_ATR, ");
		sqlNormal.append(		"k.LEGAL_OT_TIME_LIMIT, ");
		sqlNormal.append(		"k.LEGAL_MID_OT_TIME_ATR, ");
		sqlNormal.append(		"k.LEGAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append(		"k.NORMAL_OT_TIME_ATR, ");
		sqlNormal.append(		"k.NORMAL_OT_TIME_LIMIT, ");
		sqlNormal.append(		"k.NORMAL_MID_OT_TIME_ATR, ");
		sqlNormal.append(		"k.NORMAL_MID_OT_TIME_LIMIT, ");
		sqlNormal.append(		"k.EARLY_OT_TIME_ATR, ");
		sqlNormal.append(		"k.EARLY_OT_TIME_LIMIT, ");
		sqlNormal.append(		"k.EARLY_MID_OT_TIME_ATR, ");
		sqlNormal.append(		"k.EARLY_MID_OT_TIME_LIMIT, ");
		sqlNormal.append(		"k.FLEX_OT_TIME_ATR, ");
		sqlNormal.append("k.FLEX_OT_TIME_LIMIT, ");
		sqlNormal.append("k.REST_TIME_ATR, ");
		sqlNormal.append("k.REST_TIME_LIMIT, ");
		sqlNormal.append("k.LATE_NIGHT_TIME_ATR, ");
		sqlNormal.append("k.LATE_NIGHT_TIME_LIMIT, ");
		sqlNormal.append("k.LEAVE_LATE, ");
		sqlNormal.append("k.LEAVE_EARLY, ");
		sqlNormal.append("k.RAISING_CALC_ATR, ");
		sqlNormal.append("k.SPECIFIC_RAISING_CALC_ATR, ");
		sqlNormal.append("k.DIVERGENCE  ");
		sqlNormal.append("FROM KRCMT_CALC_SET_COM k WHERE k.CID = ?cid");
		SELECT_BY_CID = sqlNormal.toString();

	}


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
