package nts.uk.ctx.at.shared.infra.repository.workrecord.monthlyresults.roleofovertimework;

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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkRepository;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleofovertimework.KrcstRoleOTWork;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleofovertimework.KrcstRoleOTWorkPK_;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleofovertimework.KrcstRoleOTWork_;

@Stateless
public class JpaRoleOTWorkRepository extends JpaRepository implements RoleOvertimeWorkRepository{

	@Override
	public List<RoleOvertimeWork> findByCID(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstRoleOTWork> cq = criteriaBuilder.createQuery(KrcstRoleOTWork.class);
		Root<KrcstRoleOTWork> root = cq.from(KrcstRoleOTWork.class);
		
		cq.select(root);
		List<Predicate> predicateList = new ArrayList<>();
		Expression<String> exp = root.get(KrcstRoleOTWork_.krcstRoleOTWorkPK).get(KrcstRoleOTWorkPK_.cid);
		predicateList.add(exp.in(companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.asc(root.get(KrcstRoleOTWork_.krcstRoleOTWorkPK).get(KrcstRoleOTWorkPK_.overtimeFrNo)));

		return em.createQuery(cq).getResultList().stream().map(entity -> this.toDomain(entity)).collect(Collectors.toList());
	}

	@Override
	public void update(List<RoleOvertimeWork> lstRoleOvertimeWork) {
		List<KrcstRoleOTWork> lstEntity = lstRoleOvertimeWork.stream()
				.map(e -> this.toEntity(e))
				.collect(Collectors.toList());
		this.commandProxy().updateAll(lstEntity);
	}

	@Override
	public void add(List<RoleOvertimeWork> lstRoleOvertimeWork) {
		List<KrcstRoleOTWork> lstEntity = lstRoleOvertimeWork.stream()
									.map(e -> this.toEntity(e))
									.collect(Collectors.toList());
		this.commandProxy().insertAll(lstEntity);
	}
	
	private KrcstRoleOTWork toEntity(RoleOvertimeWork domain) {
		KrcstRoleOTWork entity = new KrcstRoleOTWork();
		domain.saveToMemento(new JpaRoleOTWorkSetMemento(entity));
		return entity;
	}
	
	private RoleOvertimeWork toDomain(KrcstRoleOTWork entity) {
		RoleOvertimeWork domain = new RoleOvertimeWork(new JpaRoleOTWorkGetMemento(entity));
		return domain;
	}

}
