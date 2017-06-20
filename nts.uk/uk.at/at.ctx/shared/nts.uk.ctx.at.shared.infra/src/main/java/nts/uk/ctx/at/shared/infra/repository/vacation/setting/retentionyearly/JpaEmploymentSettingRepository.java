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
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtrPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtrPK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr_;


/**
 * The Class JpaEmploymentSettingRepository.
 */
@Stateless
public class JpaEmploymentSettingRepository extends JpaRepository implements EmploymentSettingRepository {

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingRepository#insert(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.retentionyearly.EmploymentSetting)
	 */
	@Override
	public void insert(EmploymentSetting employmentSetting) {
		KmfmtRetentionEmpCtr entity = new KmfmtRetentionEmpCtr();
		employmentSetting.saveToMemento(new JpaEmploymentSettingSetMemento(entity));
		this.commandProxy().insert(entity);
		
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingRepository#update(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.retentionyearly.EmploymentSetting)
	 */
	@Override
	public void update(EmploymentSetting employmentSetting) {
		KmfmtRetentionEmpCtr entity = new KmfmtRetentionEmpCtr();
		employmentSetting.saveToMemento(new JpaEmploymentSettingSetMemento(entity));
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
		.remove(KmfmtRetentionEmpCtr.class, new KmfmtRetentionEmpCtrPK(companyId, employmentCode));
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<EmploymentSetting> find(String companyId, String employmentCode) {
		return this.queryProxy()
				.find(new KmfmtRetentionEmpCtrPK(companyId, employmentCode), KmfmtRetentionEmpCtr.class)
				.map(c -> this.toDomain(c));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the employment setting
	 */
	private EmploymentSetting toDomain(KmfmtRetentionEmpCtr entity) {
		return new EmploymentSetting(new JpaEmploymentSettingGetMemento(entity));
		
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingRepository#findAll(java.lang.String)
	 */
	@Override
	public List<EmploymentSetting> findAll(String companyId) {
		// Get entity manager
				EntityManager em = this.getEntityManager();
				CriteriaBuilder bd = em.getCriteriaBuilder();
				CriteriaQuery<KmfmtRetentionEmpCtr> cq = bd.createQuery(KmfmtRetentionEmpCtr.class);
				
				// Root
				Root<KmfmtRetentionEmpCtr> root = cq.from(KmfmtRetentionEmpCtr.class);
				cq.select(root);
				
				// Predicate where clause
				List<Predicate> predicateList = new ArrayList<>();
				predicateList.add(bd.equal(root.get(KmfmtRetentionEmpCtr_.kmfmtRetentionEmpCtrPK)
						.get(KmfmtRetentionEmpCtrPK_.cid), companyId));
				
				// Set Where clause to SQL Query
				cq.where(predicateList.toArray(new Predicate[] {}));
				
				// Create Query
				TypedQuery<KmfmtRetentionEmpCtr> query = em.createQuery(cq);
				
				return query.getResultList().stream()
						.map(item -> this.toDomain(item)).collect(Collectors.toList());
	}
}
