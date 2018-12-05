/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.wkp;

import java.math.BigDecimal;
import java.sql.Date;
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

import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KshmtAutoWkpCalSet;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KshmtAutoWkpCalSetPK;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KshmtAutoWkpCalSetPK_;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KshmtAutoWkpCalSet_;

/**
 * The Class JpaWkpAutoCalSettingRepository.
 */
@Stateless
public class JpaWkpAutoCalSettingRepository extends JpaRepository implements WkpAutoCalSettingRepository {

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
            "FROM BSYMT_WORKPLACE_INFO w INNER JOIN BSYMT_WKP_CONFIG c ON c.CID = w.CID " +
            "INNER JOIN BSYMT_WORKPLACE_HIST h ON w.HIST_ID = h.HIST_ID AND w.WKPID = h.WKPID "+
            "INNER JOIN KSHMT_AUTO_WKP_CAL_SET k on w.WKPID = k.WKPID " +
            "WHERE w.CID = ?cid AND h.START_DATE <= ?baseDate AND h.END_DATE >= ?baseDate "+
            "AND c.START_DATE <= ?baseDate AND c.END_DATE >= ?baseDate "+
			"ORDER BY w.WKPCD";
	/** The select no where. */
	public static final String SELECT_NO_WHERE = "SELECT c FROM KshmtAutoWkpCalSet c";
	
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

	@Override
	public List<Object[]> getWorkPlaceSettingToExport(String cid, String baseDate) {
		List<Object[]> resultQuery = null;
		try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = null;
			java.sql.Date sqlDate = null;
            try {
                date = format.parse(baseDate);
				sqlDate = new java.sql.Date(date.getTime());
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
	private KshmtAutoWkpCalSet toEntity(WkpAutoCalSetting wkpAutoCalSetting) {
		Optional<KshmtAutoWkpCalSet> optinal = this.queryProxy().find(
				new KshmtAutoWkpCalSetPK(wkpAutoCalSetting.getCompanyId().v(), wkpAutoCalSetting.getWkpId().v()),
				KshmtAutoWkpCalSet.class);
		KshmtAutoWkpCalSet entity = null;
		if (optinal.isPresent()) {
			entity = optinal.get();
		} else {
			entity = new KshmtAutoWkpCalSet();
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
		KshmtAutoWkpCalSetPK kshmtAutoWkpCalSetPK = new KshmtAutoWkpCalSetPK(companyId, wkpId);

		Optional<KshmtAutoWkpCalSet> optKshmtAutoWkpCalSet = this.queryProxy().find(kshmtAutoWkpCalSetPK,
				KshmtAutoWkpCalSet.class);

		if (!optKshmtAutoWkpCalSet.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(new WkpAutoCalSetting(new JpaWkpAutoCalSettingGetMemento(optKshmtAutoWkpCalSet.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpAutoCalSettingRepository#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String cid, String wkpId) {
		this.commandProxy().remove(KshmtAutoWkpCalSet.class, new KshmtAutoWkpCalSetPK(cid, wkpId));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSettingRepository#getAllWkpAutoCalSetting(java.lang.String)
	 */
	@Override
	public List<WkpAutoCalSetting> getAllWkpAutoCalSetting(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtAutoWkpCalSet> cq = builder.createQuery(KshmtAutoWkpCalSet.class);
		Root<KshmtAutoWkpCalSet> root = cq.from(KshmtAutoWkpCalSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KshmtAutoWkpCalSet_.kshmtAutoWkpCalSetPK)
				.get(KshmtAutoWkpCalSetPK_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList().stream()
				.map(entity -> new WkpAutoCalSetting(new JpaWkpAutoCalSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
