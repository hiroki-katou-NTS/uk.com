package nts.uk.ctx.bs.employee.infra.repository.jobtitle.info;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterExportRepository;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobHist_;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobInfo;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobInfoPK_;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobInfo_;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobSeqMaster;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobSeqMaster_;

@Stateless
public class JpaSequenceMasterExportRepository extends JpaRepository implements SequenceMasterExportRepository {


	@Override
	public List<JobTitleInfo> findAll(String companyId, GeneralDate baseDate) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<BsymtJobInfo> cq = criteriaBuilder.createQuery(BsymtJobInfo.class);
		Root<BsymtJobInfo> root = cq.from(BsymtJobInfo.class);
		Join<BsymtJobInfo, BsymtJobSeqMaster> joinRoot = root.join(BsymtJobInfo_.bsymtJobSeqMaster, JoinType.LEFT);

		// Build query
		cq.select(root);

		// add where
		List<Predicate> listPredicate = new ArrayList<>();
		listPredicate
				.add(criteriaBuilder.equal(root.get(BsymtJobInfo_.bsymtJobInfoPK).get(BsymtJobInfoPK_.cid), companyId));
		listPredicate.add(criteriaBuilder
				.lessThanOrEqualTo(root.get(BsymtJobInfo_.bsymtJobHist).get(BsymtJobHist_.startDate), baseDate));
		listPredicate.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(BsymtJobInfo_.bsymtJobHist).get(BsymtJobHist_.endDate), baseDate));

		cq.where(listPredicate.toArray(new Predicate[] {}));

		// Sort by disporder
		Expression<Object> queryCase = criteriaBuilder.selectCase()
				.when(criteriaBuilder.isNull(joinRoot.get(BsymtJobSeqMaster_.disporder)), Integer.MAX_VALUE)
				.otherwise(joinRoot.get(BsymtJobSeqMaster_.disporder));
		cq.orderBy(criteriaBuilder.asc(queryCase), criteriaBuilder.asc(root.get(BsymtJobInfo_.jobCd)));

		return em.createQuery(cq).getResultList().stream()
				.map(item -> new JobTitleInfo(new JpaJobTitleInfoGetMemento(item))).collect(Collectors.toList());
	}

}
