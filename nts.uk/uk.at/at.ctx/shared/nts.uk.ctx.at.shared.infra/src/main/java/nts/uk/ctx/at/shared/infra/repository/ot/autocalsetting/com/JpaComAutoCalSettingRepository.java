/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.com;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.com.KshmtAutoComCalSet;

/**
 * The Class JpaComAutoCalSettingRepository.
 */
@Stateless
public class JpaComAutoCalSettingRepository extends JpaRepository implements ComAutoCalSettingRepository {
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


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.ComAutoCalSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.ComAutoCalSetting)
	 */
	@Override
	public void update(ComAutoCalSetting comAutoCalSetting) {
		Optional<KshmtAutoComCalSet> optional = this.queryProxy().find(comAutoCalSetting.getCompanyId().v(), KshmtAutoComCalSet.class);

		if (!optional.isPresent()) {
			throw new RuntimeException("Total times not existed.");
		}

		KshmtAutoComCalSet entity = optional.get();
		comAutoCalSetting.saveToMemento(new JpaComAutoCalSettingSetMemento(entity));
		this.commandProxy().update(entity);
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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.ComAutoCalSettingRepository#getAllComAutoCalSetting(java.lang.String)
	 */
	@Override
	public Optional<ComAutoCalSetting> getAllComAutoCalSetting(String companyId) {

		Optional<KshmtAutoComCalSet> optKshmtAutoComCalSet = this.queryProxy().find(companyId, KshmtAutoComCalSet.class);

		if (!optKshmtAutoComCalSet.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(new ComAutoCalSetting(new JpaComAutoCalSettingGetMemento(optKshmtAutoComCalSet.get())));
	}

}
