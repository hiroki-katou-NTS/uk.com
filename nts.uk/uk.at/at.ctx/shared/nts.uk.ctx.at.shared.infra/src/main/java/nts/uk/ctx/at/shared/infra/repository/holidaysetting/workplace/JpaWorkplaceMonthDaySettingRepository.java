/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.holidaysetting.workplace;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySettingRepository;
//import nts.uk.ctx.at.shared.infra.entity.holidaysetting.company.KshmtHdpubDPerMComPK_;
//import nts.uk.ctx.at.shared.infra.entity.holidaysetting.company.KshmtHdpubDPerMCom_;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.workplace.KshmtHdpubDPerMWkp;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.workplace.KshmtHdpubDPerMWkpPK_;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.workplace.KshmtHdpubDPerMWkp_;

/**
 * The Class JpaWorkplaceMonthDaySettingRepository.
 */
@Stateless
public class JpaWorkplaceMonthDaySettingRepository extends JpaRepository implements WorkplaceMonthDaySettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingRepository#findByYear(nts.uk.ctx.bs.employee.dom.common.CompanyId, java.lang.String, nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year)
	 */
	@Override
	public Optional<WorkplaceMonthDaySetting> findByYear(CompanyId companyId, String workplaceId, Year year) {
		List<KshmtHdpubDPerMWkp> result = this.findBy(companyId, workplaceId, year, null);
		
		// Check exist
		if (result.isEmpty()) {
			return Optional.empty();
		}
		
		WorkplaceMonthDaySetting domain = new WorkplaceMonthDaySetting(new JpaWorkplaceMonthDaySettingGetMemento(result));
			
		return Optional.of(domain);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingRepository#add(nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySetting)
	 */
	@Override
	public void add(WorkplaceMonthDaySetting domain) {
		List<KshmtHdpubDPerMWkp> entities = new ArrayList<>();
		domain.saveToMemento(new JpaWorkplaceMonthDaySettingSetMemento(entities));
		this.commandProxy().insertAll(entities);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingRepository#update(nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySetting)
	 */
	@Override
	public void update(WorkplaceMonthDaySetting domain) {
		List<KshmtHdpubDPerMWkp> entities = this.findBy(domain.getCompanyId(), domain.getWorkplaceId(),
				domain.getManagementYear(), null);
		domain.saveToMemento(new JpaWorkplaceMonthDaySettingSetMemento(entities));
		this.commandProxy().updateAll(entities);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingRepository#remove(nts.uk.ctx.bs.employee.dom.common.CompanyId, java.lang.String, nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year)
	 */
	@Override
	public void remove(CompanyId companyId, String workplaceId, Year year) {
		List<KshmtHdpubDPerMWkp> result = this.findBy(companyId, workplaceId, year, null);
		this.commandProxy().removeAll(result);
	}
	
	/**
	 * Find by.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param year the year
	 * @param month the month
	 * @return the list
	 */
	private List<KshmtHdpubDPerMWkp> findBy(CompanyId companyId, String workplaceId, Year year, Integer month) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtHdpubDPerMWkp> cq = criteriaBuilder.createQuery(KshmtHdpubDPerMWkp.class);

		// root data
		Root<KshmtHdpubDPerMWkp> root = cq.from(KshmtHdpubDPerMWkp.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		if (companyId != null) {
			lstpredicateWhere
					.add(criteriaBuilder.equal(root.get(KshmtHdpubDPerMWkp_.kshmtHdpubDPerMWkpPK).get(KshmtHdpubDPerMWkpPK_.cid), companyId.v()));
		}
		
		if (workplaceId != null) {
			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KshmtHdpubDPerMWkp_.kshmtHdpubDPerMWkpPK).get(KshmtHdpubDPerMWkpPK_.wkpId),
					workplaceId));
		}

		if (year != null) {
			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KshmtHdpubDPerMWkp_.kshmtHdpubDPerMWkpPK).get(KshmtHdpubDPerMWkpPK_.manageYear),
					year.v()));
		}

		if (month != null) {
			lstpredicateWhere
					.add(criteriaBuilder.equal(root.get(KshmtHdpubDPerMWkp_.kshmtHdpubDPerMWkpPK)
							.get(KshmtHdpubDPerMWkpPK_.month), month.intValue()));
		}

		cq.orderBy(criteriaBuilder.asc(root.get(KshmtHdpubDPerMWkp_.kshmtHdpubDPerMWkpPK).get(KshmtHdpubDPerMWkpPK_.month)));
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KshmtHdpubDPerMWkp> query = em.createQuery(cq);

		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingRepository#findWkpRegisterByYear(nts.uk.ctx.bs.employee.dom.common.CompanyId, nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year)
	 */
	@Override
	public List<String> findWkpRegisterByYear(CompanyId companyId, Year year) {
		List<KshmtHdpubDPerMWkp> result = this.findBy(companyId, null, year, null);
		
		// Check exist
		if (result.isEmpty()) {
			return new ArrayList<>();
		}
		
		return result.stream()
						.map(obj -> obj.getKshmtHdpubDPerMWkpPK().getWkpId())
						.distinct()
						.collect(toList());
	}
}
