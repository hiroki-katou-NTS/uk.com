/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.wkpjob;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KshmtAutoWkpJobCal;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KshmtAutoWkpJobCalPK;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KshmtAutoWkpJobCalPK_;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KshmtAutoWkpJobCal_;

/**
 * The Class JpaWkpJobAutoCalSettingRepository.
 */
@Stateless
public class JpaWkpJobAutoCalSettingRepository extends JpaRepository implements WkpJobAutoCalSettingRepository {

	private static final String GET_ALL_WORKPLACE_JOB = "SELECT  "+
			"wj.LEGAL_OT_TIME_ATR, "+
			"wj.LEGAL_OT_TIME_LIMIT, "+
			"wj.LEGAL_MID_OT_TIME_ATR, "+
			"wj.LEGAL_MID_OT_TIME_LIMIT, "+
			"wj.NORMAL_OT_TIME_ATR, "+
			"wj.NORMAL_OT_TIME_LIMIT, "+
			"wj.NORMAL_MID_OT_TIME_ATR, "+
			"wj.NORMAL_MID_OT_TIME_LIMIT, "+
			"wj.EARLY_OT_TIME_ATR, "+
			"wj.EARLY_OT_TIME_LIMIT, "+
			"wj.EARLY_MID_OT_TIME_ATR, "+
			"wj.EARLY_MID_OT_TIME_LIMIT, "+
			"wj.FLEX_OT_TIME_ATR, "+
			"wj.FLEX_OT_TIME_LIMIT, "+
			"wj.REST_TIME_ATR, "+
			"wj.REST_TIME_LIMIT, "+
			"wj.LATE_NIGHT_TIME_ATR, "+
			"wj.LATE_NIGHT_TIME_LIMIT, "+
			"wj.LEAVE_LATE, "+
			"wj.LEAVE_EARLY, "+
			"wj.RAISING_CALC_ATR, "+
			"wj.SPECIFIC_RAISING_CALC_ATR, "+
			"wj.DIVERGENCE,  "+
			"w.WKPCD, " +
			"w.WKP_NAME, "+
			"j.JOB_CD, "+
			"j.JOB_NAME "+

	"FROM BSYMT_WORKPLACE_INFO w "+
		"INNER JOIN BSYMT_WKP_CONFIG c ON c.CID = w.CID "+
		"INNER JOIN BSYMT_WORKPLACE_HIST h ON w.HIST_ID = h.HIST_ID AND w.WKPID = h.WKPID AND h.CID = w.CID "+
		"INNER JOIN KSHMT_AUTO_WKP_JOB_CAL wj on  wj.WPKID = w.WKPID AND wj.CID = w.CID "+
		"INNER JOIN BSYMT_JOB_INFO j ON wj.JOBID = j.JOB_ID AND wj.CID = j.CID "+
		"INNER JOIN BSYMT_JOB_HIST jh on j.HIST_ID = jh.HIST_ID AND j.JOB_ID = jh.JOB_ID AND j.CID = jh.CID "+
	"WHERE wj.CID = ?cid " +
		"AND h.START_DATE <= ?baseDate AND h.END_DATE >= ?baseDate "+
		"AND c.START_DATE <= ?baseDate AND c.END_DATE >= ?baseDate "+
		"AND jh.START_DATE <= ?baseDate AND jh.END_DATE >= ?baseDate "+
	"ORDER BY w.WKPCD, j.JOB_CD ";

	@Override
	public List<Object[]> getWkpJobSettingToExport(String cid, String baseDate) {
		List<Object[]> resultQuery = null;
		try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlDate = null;
            java.util.Date date = null;
            try {
                date = format.parse(baseDate);
                sqlDate = new java.sql.Date(date.getTime());
            } catch (ParseException e) {
                return Collections.emptyList();
            }
			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(GET_ALL_WORKPLACE_JOB)
					.setParameter("cid", cid)
                    .setParameter("baseDate", sqlDate)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpJobAutoCalSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * autocalsetting.WkpJobAutoCalSetting)
	 */
	@Override
	public void update(WkpJobAutoCalSetting wkpJobAutoCalSetting) {
		this.commandProxy().update(this.toEntity(wkpJobAutoCalSetting));
		this.getEntityManager().flush();
	}
	
	/**
	 * To entity.
	 *
	 * @param wkpJobAutoCalSetting the wkp job auto cal setting
	 * @return the kshmt auto wkp job cal
	 */
	private KshmtAutoWkpJobCal toEntity(WkpJobAutoCalSetting wkpJobAutoCalSetting) {
		Optional<KshmtAutoWkpJobCal> optinal = this.queryProxy()
				.find(new KshmtAutoWkpJobCalPK(wkpJobAutoCalSetting.getCompanyId().v(),
						wkpJobAutoCalSetting.getJobId().v(), wkpJobAutoCalSetting.getJobId().v()),
						KshmtAutoWkpJobCal.class);
		KshmtAutoWkpJobCal entity = null;
		if (optinal.isPresent()) {
			entity = optinal.get();
		} else {
			entity = new KshmtAutoWkpJobCal();
		}
		JpaWkpJobAutoCalSettingSetMemento memento = new JpaWkpJobAutoCalSettingSetMemento(entity);
		wkpJobAutoCalSetting.saveToMemento(memento);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpJobAutoCalSettingRepository#getAllWkpJobAutoCalSetting(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpJobAutoCalSetting> getWkpJobAutoCalSetting(String companyId, String wkpId, String jobId) {
		KshmtAutoWkpJobCalPK kshmtAutoJobCalSetPK = new KshmtAutoWkpJobCalPK(companyId, wkpId, jobId);

		Optional<KshmtAutoWkpJobCal> optKshmtAutoWkpJobCal = this.queryProxy().find(kshmtAutoJobCalSetPK,
				KshmtAutoWkpJobCal.class);

		if (!optKshmtAutoWkpJobCal.isPresent()) {
			return Optional.empty();
		}

		return Optional
				.of(new WkpJobAutoCalSetting(new JpaWkpJobAutoCalSettingGetMemento(optKshmtAutoWkpJobCal.get())));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingRepository#delete(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String cid, String wkpId, String jobId) {
		this.commandProxy().remove(KshmtAutoWkpJobCal.class, new KshmtAutoWkpJobCalPK(cid, wkpId, jobId));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingRepository#add(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSetting)
	 */
	@Override
	public void add(WkpJobAutoCalSetting wkpJobAutoCalSetting) {
		this.commandProxy().insert(this.toEntity(wkpJobAutoCalSetting));
		this.getEntityManager().flush();
		
	}
	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository#getAllWkpJobAutoCalSetting(java.lang.String)
	 */
	@Override
	public List<WkpJobAutoCalSetting> getAllWkpJobAutoCalSetting(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtAutoWkpJobCal> cq = builder.createQuery(KshmtAutoWkpJobCal.class);
		Root<KshmtAutoWkpJobCal> root = cq.from(KshmtAutoWkpJobCal.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KshmtAutoWkpJobCal_.kshmtAutoWkpJobCalPK)
				.get(KshmtAutoWkpJobCalPK_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList().stream()
				.map(entity -> new WkpJobAutoCalSetting(new JpaWkpJobAutoCalSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
