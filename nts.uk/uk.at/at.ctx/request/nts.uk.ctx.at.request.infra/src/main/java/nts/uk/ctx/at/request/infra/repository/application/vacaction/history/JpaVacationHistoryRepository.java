package nts.uk.ctx.at.request.infra.repository.application.vacaction.history;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository;
import nts.uk.ctx.at.request.infra.entity.valication.history.KrqmtVacationHistory;
import nts.uk.ctx.at.request.infra.entity.valication.history.KrqmtVacationHistoryPK_;
import nts.uk.ctx.at.request.infra.entity.valication.history.KrqmtVacationHistory_;

@Stateless
public class JpaVacationHistoryRepository extends JpaRepository implements VacationHistoryRepository {

	@Override
	public List<PlanVacationHistory> findByWorkTypeCode(String companyId, String workTypeCode) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KCLMT_CLOSURE (KclmtClosure SQL)
		CriteriaQuery<KrqmtVacationHistory> cq = criteriaBuilder.createQuery(KrqmtVacationHistory.class);

		// root data
		Root<KrqmtVacationHistory> root = cq.from(KrqmtVacationHistory.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.cid), companyId));

		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.worktypeCd),
				workTypeCode));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by closure id asc
		cq.orderBy(criteriaBuilder.asc(root.get(KrqmtVacationHistory_.startDate)));

		List<PlanVacationHistory> lstHist = new ArrayList<>();
		// exclude select
		lstHist = em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());

		return lstHist;

	}

	@Override
	public void add(PlanVacationHistory vacationHistory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(PlanVacationHistory vacationHistory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeWkpConfigHist(String companyId, String historyId) {
		// TODO Auto-generated method stub

	}

	private PlanVacationHistory toDomain(KrqmtVacationHistory item) {
		return new PlanVacationHistory(new JpaPlanVacationHistoryGetMemento(item));
	}
}
