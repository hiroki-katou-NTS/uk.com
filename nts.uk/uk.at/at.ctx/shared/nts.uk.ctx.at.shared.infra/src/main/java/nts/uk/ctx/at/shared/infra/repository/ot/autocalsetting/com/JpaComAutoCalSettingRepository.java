/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.com;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingExport;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.com.KshmtAutoComCalSet;

/**
 * The Class JpaComAutoCalSettingRepository.
 */
@Stateless
public class JpaComAutoCalSettingRepository extends JpaRepository implements ComAutoCalSettingRepository {

	private static final String SELECT_BY_CID = "SELECT " +
			"c.CID, " +
			"c.EARLY_OT_TIME_LIMIT, " +
			"c.EARLY_MID_OT_TIME_LIMIT, " +
			"c.NORMAL_OT_TIME_LIMIT, " +
			"c.NORMAL_MID_OT_TIME_LIMIT, " +
			"c.LEGAL_OT_TIME_LIMIT, " +
			"c.LEGAL_MID_OT_TIME_LIMIT, " +
			"c.FLEX_OT_TIME_LIMIT, " +
			"c.REST_TIME_LIMIT, " +
			"c.LATE_NIGHT_TIME_LIMIT, " +
			"c.EARLY_OT_TIME_ATR, " +
			"c.EARLY_MID_OT_TIME_ATR, " +
			"c.NORMAL_OT_TIME_ATR, " +
			"c.NORMAL_MID_OT_TIME_ATR, " +
			"c.LEGAL_OT_TIME_ATR, " +
			"c.LEGAL_MID_OT_TIME_ATR, " +
			"c.FLEX_OT_TIME_ATR, " +
			"c.REST_TIME_ATR, " +
			"c.LATE_NIGHT_TIME_ATR, " +
			"c.RAISING_CALC_ATR, " +
			"c.SPECIFIC_RAISING_CALC_ATR, " +
			"c.LEAVE_EARLY, " +
			"c.LEAVE_LATE, " +
			"c.DIVERGENCE FROM KSHMT_AUTO_COM_CAL_SET c WHERE c.CID = ?cid";


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
	public Optional<ComAutoCalSettingExport> getCompanySettingToExport(String cid) {
		Object[] resultQuery = null;
		try {
			resultQuery = (Object[]) this.getEntityManager().createNativeQuery(SELECT_BY_CID)
					.setParameter("cid", cid)
					.getSingleResult();
		} catch (NoResultException e) {
			return Optional.empty();
		}
		return convertToExport(resultQuery);
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

	private Optional<ComAutoCalSettingExport> convertToExport(Object[] obj){
		return Optional.of(new ComAutoCalSettingExport(
				obj[0].toString(),
				((BigDecimal) obj[1]).intValue(),
				((BigDecimal) obj[2]).intValue(),
				((BigDecimal) obj[3]).intValue(),
				((BigDecimal) obj[4]).intValue(),
				((BigDecimal) obj[5]).intValue(),
				((BigDecimal) obj[6]).intValue(),
				((BigDecimal) obj[7]).intValue(),
				((BigDecimal) obj[8]).intValue(),
				((BigDecimal) obj[9]).intValue(),
				((BigDecimal) obj[10]).intValue(),
				((BigDecimal) obj[11]).intValue(),
				((BigDecimal) obj[12]).intValue(),
				((BigDecimal) obj[13]).intValue(),
				((BigDecimal) obj[14]).intValue(),
				((BigDecimal) obj[15]).intValue(),
				((BigDecimal) obj[16]).intValue(),
				((BigDecimal) obj[17]).intValue(),
				((BigDecimal) obj[18]).intValue(),
				((BigDecimal) obj[19]).intValue(),
				((BigDecimal) obj[20]).intValue(),
				((BigDecimal) obj[21]).intValue(),
				((BigDecimal) obj[22]).intValue(),
				((BigDecimal) obj[23]).intValue()));
	}

}
