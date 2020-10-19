package nts.uk.ctx.at.record.infra.repository.monthly.agreement.approver;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompanyRepo;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.approver.Krcmt36AgrApvCmp;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.approver.Krcmt36AgrApvCmpPK;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * リポジトリ実装：会社別の承認者（36協定）
 * @author khai.dh
 */
@Stateless
public class JpaApprover36AgrByCompanyRepo extends JpaRepository implements Approver36AgrByCompanyRepo {

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void insert(Approver36AgrByCompany domain){
		val entity = Krcmt36AgrApvCmp.fromDomain(domain);

		this.commandProxy().insert(entity);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void update(Approver36AgrByCompany domain){

		val domainData =  Krcmt36AgrApvCmp.fromDomain(domain);
		Optional<Krcmt36AgrApvCmp> findResult = this.queryProxy().find(domainData.pk, Krcmt36AgrApvCmp.class);
		if (findResult.isPresent()) {
			this.commandProxy().update(domainData);
		} else {
			this.commandProxy().insert(domainData);
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void delete(Approver36AgrByCompany domain){
		val domainData =  Krcmt36AgrApvCmp.fromDomain(domain);

		Optional<Krcmt36AgrApvCmp> findResult = this.queryProxy().find(domainData.pk, Krcmt36AgrApvCmp.class);
		if (findResult.isPresent()) {
			Krcmt36AgrApvCmp target = findResult.get();
			this.commandProxy().remove(target);
		}
	}

	@Override
	public List<Approver36AgrByCompany> getByCompanyId(String companyId) {
		val em = this.getEntityManager();
		val cb = em.getCriteriaBuilder();
		CriteriaQuery<Krcmt36AgrApvCmp> cq = cb.createQuery(Krcmt36AgrApvCmp.class);
		Root<Krcmt36AgrApvCmp> root = cq.from(Krcmt36AgrApvCmp.class);
		cq.select(root);

		val wherePredicate = new ArrayList<Predicate>(){{
			add(cb.equal(root.get(Krcmt36AgrApvCmp.Meta_.pk).get(Krcmt36AgrApvCmpPK.Meta_.companyId), companyId));
		}};
		cq.where(wherePredicate.toArray(new Predicate[] {}));

		return em.createQuery(cq)
				.getResultList()
				.stream()
				.map(Krcmt36AgrApvCmp::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Approver36AgrByCompany> getByCompanyIdFromDate(String companyId, GeneralDate date) {
		val em = this.getEntityManager();
		val cb = em.getCriteriaBuilder();
		CriteriaQuery<Krcmt36AgrApvCmp> cq = cb.createQuery(Krcmt36AgrApvCmp.class);
		Root<Krcmt36AgrApvCmp> root = cq.from(Krcmt36AgrApvCmp.class);
		cq.select(root);

		val wherePredicate = new ArrayList<Predicate>(){{
			add(cb.equal(root.get(Krcmt36AgrApvCmp.Meta_.pk).get(Krcmt36AgrApvCmpPK.Meta_.companyId), companyId));
			add(cb.greaterThanOrEqualTo(
					root.get(Krcmt36AgrApvCmp.Meta_.pk).get(Krcmt36AgrApvCmpPK.Meta_.startDate),
					date
			));
		}};
		cq.where(wherePredicate.toArray(new Predicate[] {}));

		return em.createQuery(cq)
				.getResultList()
				.stream()
				.map(Krcmt36AgrApvCmp::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Approver36AgrByCompany> getByCompanyIdAndEndDate(String companyId, GeneralDate endDate) {
		val em = this.getEntityManager();
		val cb = em.getCriteriaBuilder();
		CriteriaQuery<Krcmt36AgrApvCmp> cq = cb.createQuery(Krcmt36AgrApvCmp.class);
		Root<Krcmt36AgrApvCmp> root = cq.from(Krcmt36AgrApvCmp.class);
		cq.select(root);

		val wherePredicate = new ArrayList<Predicate>(){{
			add(cb.equal(root.get(Krcmt36AgrApvCmp.Meta_.pk).get(Krcmt36AgrApvCmpPK.Meta_.companyId), companyId));
			add(cb.equal(root.get(Krcmt36AgrApvCmp.Meta_.endDate), endDate));
		}};
		cq.where(wherePredicate.toArray(new Predicate[] {}));

		try {
			Krcmt36AgrApvCmp result = em.createQuery(cq).getSingleResult();
			return Optional.of(result.toDomain());
		} catch (NoResultException e){
			return Optional.empty();
		}
	}

	@Override
	public Optional<Approver36AgrByCompany> getByCompanyIdAndDate(String companyId, GeneralDate refDate) {
		val em = this.getEntityManager();
		val cb = em.getCriteriaBuilder();
		CriteriaQuery<Krcmt36AgrApvCmp> cq = cb.createQuery(Krcmt36AgrApvCmp.class);
		Root<Krcmt36AgrApvCmp> root = cq.from(Krcmt36AgrApvCmp.class);
		cq.select(root);

		val wherePredicate = new ArrayList<Predicate>(){{
			add(cb.equal(root.get(Krcmt36AgrApvCmp.Meta_.pk).get(Krcmt36AgrApvCmpPK.Meta_.companyId), companyId));
			add(cb.lessThanOrEqualTo(root.get(Krcmt36AgrApvCmp.Meta_.pk).get(Krcmt36AgrApvCmpPK.Meta_.startDate), refDate));
			add(cb.greaterThanOrEqualTo(root.get(Krcmt36AgrApvCmp.Meta_.endDate), refDate));
		}};
		cq.where(wherePredicate.toArray(new Predicate[] {}));

		try {
			Krcmt36AgrApvCmp result = em.createQuery(cq).getSingleResult();
			return Optional.of(result.toDomain());
		} catch (NoResultException e){
			return Optional.empty();
		}
	}

	@Override
	public void updateStartDate(Approver36AgrByCompany domain, GeneralDate startDateBeforeChange) {
		val domainData =  Krcmt36AgrApvCmp.fromDomain(domain);
		val pk = new Krcmt36AgrApvCmpPK(domain.getCompanyId(),startDateBeforeChange);
		Optional<Krcmt36AgrApvCmp> findResult = this.queryProxy().find(pk, Krcmt36AgrApvCmp.class);
		if (findResult.isPresent()) {

			this.commandProxy().remove(Krcmt36AgrApvCmp.class,pk);
			this.getEntityManager().flush();
			this.commandProxy().insert(domainData);
		} else {
			this.commandProxy().insert(domainData);
		}
	}
}
