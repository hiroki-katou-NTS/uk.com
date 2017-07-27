package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KcbmtCompanyWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KcbmtCompanyWorkSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KcbmtCompanyWorkSet_;

@Stateless
public class JpaCompanyBasicWorkRepository extends JpaRepository implements CompanyBasicWorkRepository {

	
	@Override
	public void insert(CompanyBasicWork companyBasicWork) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CompanyBasicWork companyBasicWork) {
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public List<CompanyBasicWork> findAll(String companyId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KcbmtCompanyWorkSet> cq = bd.createQuery(KcbmtCompanyWorkSet.class);
		// Root
		Root<KcbmtCompanyWorkSet> root = cq.from(KcbmtCompanyWorkSet.class);
		cq.select(root);
		
		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KcbmtCompanyWorkSet_.kcbmtCompanyWorkSetPK).get(KcbmtCompanyWorkSetPK_.cid), companyId));
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KcbmtCompanyWorkSet> query = em.createQuery(cq);
		
		return query.getResultList().stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
		
	}

	@Override
	public Optional<CompanyBasicWork> find(String companyId, Integer workdayDivision) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private CompanyBasicWork toDomain(KcbmtCompanyWorkSet entity) {
		return new CompanyBasicWork(new JpaCompanyBasicWorkGetMemento(entity));
	}

}
