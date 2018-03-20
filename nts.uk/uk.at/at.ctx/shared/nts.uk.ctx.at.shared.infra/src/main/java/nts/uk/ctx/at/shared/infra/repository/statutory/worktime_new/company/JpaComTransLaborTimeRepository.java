/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.company.CompanyWtSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company.KcwstCompanyWtSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company.KcwstCompanyWtSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company.KcwstCompanyWtSet_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComTransLabTime;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime.company.JpaCompanyWtSettingGetMemento;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime.company.JpaCompanyWtSettingSetMemento;

/**
 * The Class JpaCompanyWtSettingRepository.
 */
@Stateless
public class JpaComTransLaborTimeRepository extends JpaRepository implements ComTransLaborTimeRepository {

	@Override
	public void create(ComTransLaborTime setting) {
		commandProxy().insert(this.toEntity(setting));
		
	}

	@Override
	public void update(ComTransLaborTime setting) {
		commandProxy().update(this.toEntity(setting));
	}

	@Override
	public void remove(String companyId, int year) {
//		commandProxy().remove(entityClass, primaryKey);
		
	}

	@Override
	public Optional<ComTransLaborTime> find(String companyId, int year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstComTransLabTime> cq = cb.createQuery(KshstComTransLabTime.class);
		Root<KshstComTransLabTime> root = cq.from(KshstComTransLabTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	private KshstComTransLabTime toEntity(ComTransLaborTime domain) {
		JpaComTransLaborTimeSetMemento memento = new JpaComTransLaborTimeSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	
	private ComTransLaborTime toDomain(List<KshstComTransLabTime> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new ComTransLaborTime(new JpaComTransLaborTimeGetMemento(entities.get(0)));
	}
}
