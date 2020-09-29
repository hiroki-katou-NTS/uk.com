package nts.uk.ctx.at.shared.infra.repository.workrecord.monthlyresults.roleopenperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodRepository;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleopenperiod.KrcstRoleOfOpenPeriod;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleopenperiod.KrcstRoleOfOpenPeriodPK_;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleopenperiod.KrcstRoleOfOpenPeriod_;

/**
 * The Class JpaRoleOpenPeriodRepository.
 */
@Stateless
public class JpaRoleOfOpenPeriodRepository extends JpaRepository implements RoleOfOpenPeriodRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodRepository#findByCID(java.lang.String)
	 */
	@Override
	public List<RoleOfOpenPeriod> findByCID(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstRoleOfOpenPeriod> cq = criteriaBuilder.createQuery(KrcstRoleOfOpenPeriod.class);
		Root<KrcstRoleOfOpenPeriod> root = cq.from(KrcstRoleOfOpenPeriod.class);
		
		cq.select(root);
		List<Predicate> predicateList = new ArrayList<>();
		Expression<String> exp = root.get(KrcstRoleOfOpenPeriod_.krcstRoleOfOpenPeriodPK).get(KrcstRoleOfOpenPeriodPK_.cid);
		predicateList.add(exp.in(companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.asc(root.get(KrcstRoleOfOpenPeriod_.krcstRoleOfOpenPeriodPK).get(KrcstRoleOfOpenPeriodPK_.breakoutOffFrNo)));

		return em.createQuery(cq).getResultList().stream().map(entity -> this.toDomain(entity)).collect(Collectors.toList());
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodRepository#update(java.util.List)
	 */
	@Override
	public void update(List<RoleOfOpenPeriod> lstRoleOfOpenPeriod) {
		List<KrcstRoleOfOpenPeriod> lstEntity = lstRoleOfOpenPeriod.stream()
																		.map(e -> this.toEntity(e))
																		.collect(Collectors.toList());
		this.commandProxy().updateAll(lstEntity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodRepository#add(java.util.List)
	 */
	@Override
	public void add(List<RoleOfOpenPeriod> lstRoleOfOpenPeriod) {
		List<KrcstRoleOfOpenPeriod> lstEntity = lstRoleOfOpenPeriod.stream()
				.map(e -> this.toEntity(e))
				.collect(Collectors.toList());
		this.commandProxy().insertAll(lstEntity);
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the krcst role of open period
	 */
	private KrcstRoleOfOpenPeriod toEntity(RoleOfOpenPeriod domain) {
		KrcstRoleOfOpenPeriod entity = new KrcstRoleOfOpenPeriod();
		domain.saveToMemento(new JpaRoleOfOpenPeriodSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the role of open period
	 */
	private RoleOfOpenPeriod toDomain(KrcstRoleOfOpenPeriod entity) {
		RoleOfOpenPeriod domain = new RoleOfOpenPeriod(new JpaRoleOfOpenPeriodGetMemento(entity));
		return domain;
	}
}
