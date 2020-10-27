/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingCondition;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWt;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWtPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWtPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWt_;
import nts.uk.shr.com.i18n.TextResource;

/**
 * The Class JpaWorkTimeSettingRepository.
 */
@Stateless
public class JpaWorkTimeSettingRepository extends JpaRepository implements WorkTimeSettingRepository {
	
	private static final String SELECT_CODE_AND_NAME_BY_WORKTIME_CODE = "SELECT c.kshmtWtPK.worktimeCd, c.name FROM KshmtWt c"
			+ " WHERE c.kshmtWtPK.cid = :companyId AND c.kshmtWtPK.worktimeCd IN :listWorkTimeCode";
	private static final String FIND_BY_CID = "SELECT c FROM KshmtWt c  WHERE c.kshmtWtPK.cid = :companyId";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository#
	 * findByCompanyId(java.lang.String)
	 */
	@Override
	public List<WorkTimeSetting> findByCompanyId(String companyId) {
		return this.findWithCondition(companyId, new WorkTimeSettingCondition(null, null, false));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository#
	 * findByCodes(java.lang.String, java.util.List)
	 */
	@Override
	public List<WorkTimeSetting> findByCodes(String companyID, List<String> codes) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWt> cq = criteriaBuilder.createQuery(KshmtWt.class);
		Root<KshmtWt> root = cq.from(KshmtWt.class);

		// select root
		cq.select(root);
		
		List<KshmtWt> lstKwtstWorkTimeSet = new ArrayList<>();
		
		CollectionUtil.split(codes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subListCodes -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			
			lstpredicateWhere
					.add(root.get(KshmtWt_.kshmtWtPK).get(KshmtWtPK_.worktimeCd).in(subListCodes));
			lstpredicateWhere.add(criteriaBuilder
					.equal(root.get(KshmtWt_.kshmtWtPK).get(KshmtWtPK_.cid), companyID));
			lstpredicateWhere.add(
					criteriaBuilder.equal(root.get(KshmtWt_.abolitionAtr), AbolishAtr.NOT_ABOLISH.value));
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			lstKwtstWorkTimeSet.addAll(em.createQuery(cq).getResultList());
		});
		
		return lstKwtstWorkTimeSet.stream()
				.map(item -> new WorkTimeSetting(new JpaWorkTimeSettingGetMemento(item)))
				.collect(Collectors.toList());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository#
	 * findByCodes(java.lang.String, java.util.List)
	 */
	@Override
	public List<WorkTimeSetting> findByCodesWithNoMaster(String companyID, List<String> codes) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWt> cq = criteriaBuilder.createQuery(KshmtWt.class);
		Root<KshmtWt> root = cq.from(KshmtWt.class);

		// select root
		cq.select(root);
		
		List<KshmtWt> lstKwtstWorkTimeSet = new ArrayList<>();
		
		codes.forEach(code -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();

			lstpredicateWhere.add(criteriaBuilder
					.equal(root.get(KshmtWt_.kshmtWtPK).get(KshmtWtPK_.worktimeCd), code));
			lstpredicateWhere.add(criteriaBuilder
					.equal(root.get(KshmtWt_.kshmtWtPK).get(KshmtWtPK_.cid), companyID));
			lstpredicateWhere
					.add(criteriaBuilder.equal(root.get(KshmtWt_.abolitionAtr), AbolishAtr.NOT_ABOLISH.value));
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
			List<KshmtWt> results = em.createQuery(cq).getResultList();
			KshmtWt wkTime;
			if (results.isEmpty()) {
				wkTime = new KshmtWt(new KshmtWtPK(companyID, code), 0,
						TextResource.localize("KAL003_120"), TextResource.localize("KAL003_120"), "", 0, 0, 0, "", "",
						"");
			} else {
				wkTime = results.get(0);
			}
			lstKwtstWorkTimeSet.add(wkTime);
		});
		
		return lstKwtstWorkTimeSet.stream()
				.map(item -> new WorkTimeSetting(new JpaWorkTimeSettingGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository#
	 * findByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WorkTimeSetting> findByCode(String companyId, String worktimeCode) {
		Optional<KshmtWt> entity = this.findByPk(companyId, worktimeCode);

		return entity.isPresent() ? Optional.of(new WorkTimeSetting(new JpaWorkTimeSettingGetMemento(entity.get())))
				: Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository#
	 * save(nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting)
	 */
	@Override
	public void add(WorkTimeSetting domain) {
		KshmtWt entity = new KshmtWt();
		domain.saveToMemento(new JpaWorkTimeSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository#
	 * update(nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting)
	 */
	@Override
	public void update(WorkTimeSetting domain) {
		KshmtWtPK pk = new KshmtWtPK(domain.getCompanyId(), domain.getWorktimeCode().v());

		Optional<KshmtWt> op = this.queryProxy().find(pk, KshmtWt.class);
		KshmtWt entity = op.get();
		domain.saveToMemento(new JpaWorkTimeSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository#
	 * remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String workTimeCode) {
		this.commandProxy().remove(KshmtWt.class, new KshmtWtPK(companyId, workTimeCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository#
	 * findWithCondition(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet,
	 * nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr)
	 */
	@Override
	public List<WorkTimeSetting> findWithCondition(String companyId, WorkTimeSettingCondition condition) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWt> cq = criteriaBuilder.createQuery(KshmtWt.class);
		Root<KshmtWt> root = cq.from(KshmtWt.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KshmtWt_.kshmtWtPK).get(KshmtWtPK_.cid), companyId));
		if (condition.getWorkTimeDailyAtr() != null) {
			lstpredicateWhere.add(
					criteriaBuilder.equal(root.get(KshmtWt_.dailyWorkAtr), condition.getWorkTimeDailyAtr()));
		}
		if (condition.getWorkTimeMethodSet() != null) {
			lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshmtWt_.worktimeSetMethod),
					condition.getWorkTimeMethodSet()));
		}
		if (condition.getIsAbolish() != null && !condition.getIsAbolish()) {
			lstpredicateWhere.add(
					criteriaBuilder.equal(root.get(KshmtWt_.abolitionAtr), AbolishAtr.NOT_ABOLISH.value));
		}
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		List<KshmtWt> lstKwtstWorkTimeSet = em.createQuery(cq).getResultList();

		return lstKwtstWorkTimeSet.stream().map(item -> {
			WorkTimeSetting worktimeSetting = new WorkTimeSetting(new JpaWorkTimeSettingGetMemento(item));
			return worktimeSetting;
		}).collect(Collectors.toList());
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository#getListWorkTimeSetByListCode(java.lang.String, java.util.List)
	 */
	@Override
	public List<WorkTimeSetting> getListWorkTimeSetByListCode(String companyId, List<String> workTimeCodes) {
		if(workTimeCodes.isEmpty()){
			return this.findWithCondition(companyId, new WorkTimeSettingCondition(null, null, null));
		}
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWt> cq = criteriaBuilder.createQuery(KshmtWt.class);
		Root<KshmtWt> root = cq.from(KshmtWt.class);

		// select root
		cq.select(root);
		
		List<KshmtWt> resultList = new ArrayList<>();

		CollectionUtil.split(workTimeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();

			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KshmtWt_.kshmtWtPK).get(KshmtWtPK_.cid),
					companyId));
			lstpredicateWhere.add(root.get(KshmtWt_.kshmtWtPK)
					.get(KshmtWtPK_.worktimeCd).in(splitData));
			lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshmtWt_.abolitionAtr),
					AbolishAtr.NOT_ABOLISH.value));
			
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			resultList.addAll(em.createQuery(cq).getResultList());
		});
		
		return resultList.stream()
				.map(item -> new WorkTimeSetting(new JpaWorkTimeSettingGetMemento(item)))
				.collect(Collectors.toList());
	}

	/**
	 * Find by pk.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @return the optional
	 */
	@SneakyThrows
	private Optional<KshmtWt> findByPk(String companyId, String worktimeCode) {
		try (PreparedStatement statement = this.connection().prepareStatement(
				"select * from KSHMT_WT where CID = ? and WORKTIME_CD = ?")) {
			statement.setString(1, companyId);
			statement.setString(2, worktimeCode);
			
			return new NtsResultSet(statement.executeQuery()).getSingle(rec -> {
				KshmtWtPK pk = new KshmtWtPK();
				pk.setCid(companyId);
				pk.setWorktimeCd(worktimeCode);
				
				KshmtWt entity = new KshmtWt();
				entity.setKshmtWtPK(pk);
				entity.setName(rec.getString("NAME"));
				entity.setAbname(rec.getString("ABNAME"));
				entity.setSymbol(rec.getString("SYMBOL"));
				entity.setDailyWorkAtr(rec.getInt("DAILY_WORK_ATR"));
				entity.setWorktimeSetMethod(rec.getInt("WORKTIME_SET_METHOD"));
				entity.setAbolitionAtr(rec.getInt("ABOLITION_ATR"));
				entity.setColor(rec.getString("COLOR"));
				entity.setMemo(rec.getString("MEMO"));
				entity.setNote(rec.getString("NOTE"));
				
				return entity;
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository#
	 * findActiveItems(java.lang.String)
	 */
	@Override
	public List<WorkTimeSetting> findActiveItems(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWt> cq = criteriaBuilder.createQuery(KshmtWt.class);
		Root<KshmtWt> root = cq.from(KshmtWt.class);

		// select root
		cq.select(root);
		List<KshmtWt> lstKwtstWorkTimeSet = new ArrayList<>();
		
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KshmtWt_.kshmtWtPK).get(KshmtWtPK_.cid),
					companyId));
			lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshmtWt_.abolitionAtr),
					AbolishAtr.NOT_ABOLISH.value));
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			lstKwtstWorkTimeSet.addAll(em.createQuery(cq).getResultList());

			return lstKwtstWorkTimeSet.stream().map(item -> {
			WorkTimeSetting worktimeSetting = new WorkTimeSetting(
					new JpaWorkTimeSettingGetMemento(item));
			return worktimeSetting;
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<WorkTimeSetting> findByCodeAndAbolishCondition(String companyId, String workTimeCode,
			AbolishAtr abolishAtr) {
		if (StringUtil.isNullOrEmpty(workTimeCode, true)) {
			return Optional.empty();
		}
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWt> cq = criteriaBuilder.createQuery(KshmtWt.class);
		Root<KshmtWt> root = cq.from(KshmtWt.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KshmtWt_.kshmtWtPK).get(KshmtWtPK_.cid), companyId));
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KshmtWt_.kshmtWtPK).get(KshmtWtPK_.worktimeCd), workTimeCode));
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshmtWt_.abolitionAtr), abolishAtr.value));
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		List<KshmtWt> lstKwtstWorkTimeSet = em.createQuery(cq).getResultList();
		if (lstKwtstWorkTimeSet.isEmpty()) {
			return Optional.empty();
		} else {
			// get first item of list have 1 element
			return Optional.of(new WorkTimeSetting(new JpaWorkTimeSettingGetMemento(lstKwtstWorkTimeSet.get(0))));
		}
	}

	@Override
	public Map<String, String> getCodeNameByListWorkTimeCd(String companyId, List<String> listWorkTimeCode) {
		
		List<Object[]> listObject = new ArrayList<>();
		
		// split list listWorkTimeCode
		CollectionUtil.split(listWorkTimeCode, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			listObject.addAll(this.queryProxy()
					.query(SELECT_CODE_AND_NAME_BY_WORKTIME_CODE, Object[].class)
					.setParameter("companyId", companyId)
					.setParameter("listWorkTimeCode", subList).getList());
		});
		
		return listObject.stream().collect(Collectors.toMap(x -> String.valueOf(x[0]), x -> String.valueOf(x[1])));
	}

	@Override
	public List<WorkTimeSetting> findByCId(String companyId) {
		return this.queryProxy()
				.query(FIND_BY_CID, KshmtWt.class)
				.setParameter("companyId", companyId)
				.getList(c -> new WorkTimeSetting(new JpaWorkTimeSettingGetMemento(c)));
	}
	
}
