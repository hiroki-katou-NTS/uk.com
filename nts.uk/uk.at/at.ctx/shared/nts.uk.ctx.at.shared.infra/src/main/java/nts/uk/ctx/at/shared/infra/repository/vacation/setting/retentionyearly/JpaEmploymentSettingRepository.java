/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KshmtHdstkSetEmp;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KshmtHdstkSetEmpPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KshmtHdstkSetEmpPK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KshmtHdstkSetEmp_;


/**
 * The Class JpaEmploymentSettingRepository.
 */
@Stateless
public class JpaEmploymentSettingRepository extends JpaRepository implements EmploymentSettingRepository {

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingRepository#insert(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.retentionyearly.EmptYearlyRetentionSetting)
	 */
	@Override
	public void insert(EmptYearlyRetentionSetting emptYearlyRetentionSetting) {
		KshmtHdstkSetEmp entity = new KshmtHdstkSetEmp();
		emptYearlyRetentionSetting.saveToMemento(new JpaEmploymentSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingRepository#update(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.retentionyearly.EmptYearlyRetentionSetting)
	 */
	@Override
	public void update(EmptYearlyRetentionSetting emptYearlyRetentionSetting) {
		Optional<KshmtHdstkSetEmp> optional = this.queryProxy()
				.find(new KshmtHdstkSetEmpPK(emptYearlyRetentionSetting.getCompanyId(),
						emptYearlyRetentionSetting.getEmploymentCode()), KshmtHdstkSetEmp.class);
		KshmtHdstkSetEmp entity = null;
		if(optional.isPresent()) {
			entity = optional.get();
		}
		else {
			entity = new KshmtHdstkSetEmp();
		}
		emptYearlyRetentionSetting.saveToMemento(new JpaEmploymentSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String employmentCode) {
		this.commandProxy()
		.remove(KshmtHdstkSetEmp.class, new KshmtHdstkSetEmpPK(companyId, employmentCode));
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<EmptYearlyRetentionSetting> find(String companyId, String employmentCode) {
		return this.queryProxy()
				.find(new KshmtHdstkSetEmpPK(companyId, employmentCode), KshmtHdstkSetEmp.class)
				.map(c -> this.toDomain(c));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the employment setting
	 */
	private EmptYearlyRetentionSetting toDomain(KshmtHdstkSetEmp entity) {
		return new EmptYearlyRetentionSetting(new JpaEmploymentSettingGetMemento(entity));
		
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingRepository#findAll(java.lang.String)
	 */
	@Override
	public List<EmptYearlyRetentionSetting> findAll(String companyId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KshmtHdstkSetEmp> cq = bd.createQuery(KshmtHdstkSetEmp.class);

		// Root
		Root<KshmtHdstkSetEmp> root = cq.from(KshmtHdstkSetEmp.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KshmtHdstkSetEmp_.kshmtHdstkSetEmpPK).get(KshmtHdstkSetEmpPK_.cid), companyId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KshmtHdstkSetEmp> query = em.createQuery(cq);

		return query.getResultList().stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}
}
