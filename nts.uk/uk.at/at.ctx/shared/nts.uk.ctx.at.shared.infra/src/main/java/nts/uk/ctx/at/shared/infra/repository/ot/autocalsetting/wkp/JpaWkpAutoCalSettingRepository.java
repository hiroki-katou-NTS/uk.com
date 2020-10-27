/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.wkp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KrcmtCalcSetWkp;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KrcmtCalcSetWkpPK;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KrcmtCalcSetWkpPK_;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KrcmtCalcSetWkp_;

/**
 * The Class JpaWkpAutoCalSettingRepository.
 */
@Stateless
public class JpaWkpAutoCalSettingRepository extends JpaRepository implements WkpAutoCalSettingRepository {

	/** The select no where. */
	public static final String SELECT_NO_WHERE = "SELECT c FROM KrcmtCalcSetWkp c";
	
	/** The select by company id. */
	public static final String SELECT_BY_COMPANY_ID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId";
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpAutoCalSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * autocalsetting.WkpAutoCalSetting)
	 */
	@Override
	public void update(WkpAutoCalSetting wkpAutoCalSetting) {
		this.commandProxy().update(this.toEntity(wkpAutoCalSetting));
		this.getEntityManager().flush();

	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSettingRepository#add(nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSetting)
	 */
	@Override
	public void add(WkpAutoCalSetting wkpAutoCalSetting) {
		this.commandProxy().insert(this.toEntity(wkpAutoCalSetting));
		this.getEntityManager().flush();

	}

	/**
	 * To entity.
	 *
	 * @param wkpAutoCalSetting the wkp auto cal setting
	 * @return the kshmt auto wkp cal set
	 */
	private KrcmtCalcSetWkp toEntity(WkpAutoCalSetting wkpAutoCalSetting) {
		Optional<KrcmtCalcSetWkp> optinal = this.queryProxy().find(
				new KrcmtCalcSetWkpPK(wkpAutoCalSetting.getCompanyId().v(), wkpAutoCalSetting.getWkpId().v()),
				KrcmtCalcSetWkp.class);
		KrcmtCalcSetWkp entity = null;
		if (optinal.isPresent()) {
			entity = optinal.get();
		} else {
			entity = new KrcmtCalcSetWkp();
		}
		JpaWkpAutoCalSettingSetMemento memento = new JpaWkpAutoCalSettingSetMemento(entity);
		wkpAutoCalSetting.saveToMemento(memento);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpAutoCalSettingRepository#getAllWkpAutoCalSetting(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<WkpAutoCalSetting> getWkpAutoCalSetting(String companyId, String wkpId) {
		KrcmtCalcSetWkpPK krcmtCalcSetWkpPK = new KrcmtCalcSetWkpPK(companyId, wkpId);

		Optional<KrcmtCalcSetWkp> optKrcmtCalcSetWkp = this.queryProxy().find(krcmtCalcSetWkpPK,
				KrcmtCalcSetWkp.class);

		if (!optKrcmtCalcSetWkp.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(new WkpAutoCalSetting(new JpaWkpAutoCalSettingGetMemento(optKrcmtCalcSetWkp.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpAutoCalSettingRepository#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String cid, String wkpId) {
		this.commandProxy().remove(KrcmtCalcSetWkp.class, new KrcmtCalcSetWkpPK(cid, wkpId));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSettingRepository#getAllWkpAutoCalSetting(java.lang.String)
	 */
	@Override
	public List<WkpAutoCalSetting> getAllWkpAutoCalSetting(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtCalcSetWkp> cq = builder.createQuery(KrcmtCalcSetWkp.class);
		Root<KrcmtCalcSetWkp> root = cq.from(KrcmtCalcSetWkp.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KrcmtCalcSetWkp_.krcmtCalcSetWkpPK)
				.get(KrcmtCalcSetWkpPK_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList().stream()
				.map(entity -> new WkpAutoCalSetting(new JpaWkpAutoCalSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
