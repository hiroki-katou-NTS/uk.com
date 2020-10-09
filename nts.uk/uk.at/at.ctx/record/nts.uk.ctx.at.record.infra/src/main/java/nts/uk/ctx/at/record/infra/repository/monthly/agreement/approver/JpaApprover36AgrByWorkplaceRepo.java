package nts.uk.ctx.at.record.infra.repository.monthly.agreement.approver;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplaceRepo;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.approver.Krcmt36AgrApvWkp;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.approver.Krcmt36AgrApvWkpPK;
import nts.uk.shr.com.context.AppContexts;

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
public class JpaApprover36AgrByWorkplaceRepo extends JpaRepository implements Approver36AgrByWorkplaceRepo {

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void insert(Approver36AgrByWorkplace domain){
		val entity =  Krcmt36AgrApvWkp.fromDomain(domain);

		this.commandProxy().insert(entity);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void update(Approver36AgrByWorkplace domain){
		val domainData =Krcmt36AgrApvWkp.fromDomain(domain);
		Optional<Krcmt36AgrApvWkp> findResult = this.queryProxy().find(domainData.pk, Krcmt36AgrApvWkp.class);
		if (findResult.isPresent()) {
			this.commandProxy().update(domainData);
		} else {
			this.commandProxy().insert(domainData);
		}
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void delete(Approver36AgrByWorkplace domain){
		val domainData =Krcmt36AgrApvWkp.fromDomain(domain);

		Optional<Krcmt36AgrApvWkp> findResult = this.queryProxy().find(domainData.pk, Krcmt36AgrApvWkp.class);
		if (findResult.isPresent()) {
			Krcmt36AgrApvWkp target = findResult.get();
			this.commandProxy().remove(target);
		}
	}

	@Override
	public List<Approver36AgrByWorkplace> getByWorkplaceId(String wkpId) {
		String cid = AppContexts.user().companyId();
		val em = this.getEntityManager();
		val cb = em.getCriteriaBuilder();
		CriteriaQuery<Krcmt36AgrApvWkp> cq = cb.createQuery(Krcmt36AgrApvWkp.class);
		Root<Krcmt36AgrApvWkp> root = cq.from(Krcmt36AgrApvWkp.class);
		cq.select(root);

		val wherePredicate = new ArrayList<Predicate>(){{
			add(cb.equal(root.get(Krcmt36AgrApvWkp.Meta_.cid), cid));
			add(cb.equal(root.get(Krcmt36AgrApvWkp.Meta_.pk).get(Krcmt36AgrApvWkpPK.Meta_.workplaceID), wkpId));
		}};
		cq.where(wherePredicate.toArray(new Predicate[] {}));

		return em.createQuery(cq)
				.getResultList()
				.stream()
				.map(Krcmt36AgrApvWkp::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Approver36AgrByWorkplace> getByWorkplaceIdFromDate(String wkpId, GeneralDate date) {
		String cid = AppContexts.user().companyId();
		val em = this.getEntityManager();
		val cb = em.getCriteriaBuilder();
		CriteriaQuery<Krcmt36AgrApvWkp> cq = cb.createQuery(Krcmt36AgrApvWkp.class);
		Root<Krcmt36AgrApvWkp> root = cq.from(Krcmt36AgrApvWkp.class);
		cq.select(root);

		val wherePredicate = new ArrayList<Predicate>(){{
			add(cb.equal(root.get(Krcmt36AgrApvWkp.Meta_.cid), cid));
			add(cb.equal(root.get(Krcmt36AgrApvWkp.Meta_.pk).get(Krcmt36AgrApvWkpPK.Meta_.workplaceID), wkpId));
			add(cb.greaterThanOrEqualTo(
					root.get(Krcmt36AgrApvWkp.Meta_.pk).get(Krcmt36AgrApvWkpPK.Meta_.startDate),
					date
			));
		}};
		cq.where(wherePredicate.toArray(new Predicate[] {}));

		return em.createQuery(cq)
				.getResultList()
				.stream()
				.map(Krcmt36AgrApvWkp::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Approver36AgrByWorkplace> getByWorkplaceIdAndEndDate(String wkpId, GeneralDate endDate) {
		String cid = AppContexts.user().companyId();
		val em = this.getEntityManager();
		val cb = em.getCriteriaBuilder();
		CriteriaQuery<Krcmt36AgrApvWkp> cq = cb.createQuery(Krcmt36AgrApvWkp.class);
		Root<Krcmt36AgrApvWkp> root = cq.from(Krcmt36AgrApvWkp.class);
		cq.select(root);

		val wherePredicate = new ArrayList<Predicate>(){{
			add(cb.equal(root.get(Krcmt36AgrApvWkp.Meta_.cid), cid));
			add(cb.equal(root.get(Krcmt36AgrApvWkp.Meta_.pk).get(Krcmt36AgrApvWkpPK.Meta_.workplaceID), wkpId));
			add(cb.equal(root.get(Krcmt36AgrApvWkp.Meta_.endDate), endDate));
		}};
		cq.where(wherePredicate.toArray(new Predicate[] {}));

		try {
			Krcmt36AgrApvWkp result = em.createQuery(cq).getSingleResult();
			return Optional.of(result.toDomain());
		} catch (NoResultException e){
			return Optional.empty();
		}
	}

	@Override
	public Optional<Approver36AgrByWorkplace> getByWorkplaceIdAndDate(String wkpId, GeneralDate refDate) {
		String cid = AppContexts.user().companyId();
		val em = this.getEntityManager();
		val cb = em.getCriteriaBuilder();
		CriteriaQuery<Krcmt36AgrApvWkp> cq = cb.createQuery(Krcmt36AgrApvWkp.class);
		Root<Krcmt36AgrApvWkp> root = cq.from(Krcmt36AgrApvWkp.class);
		cq.select(root);

		val wherePredicate = new ArrayList<Predicate>(){{
			add(cb.equal(root.get(Krcmt36AgrApvWkp.Meta_.cid), cid));
			add(cb.equal(root.get(Krcmt36AgrApvWkp.Meta_.pk).get(Krcmt36AgrApvWkpPK.Meta_.workplaceID), wkpId));
			add(cb.lessThanOrEqualTo(root.get(Krcmt36AgrApvWkp.Meta_.pk).get(Krcmt36AgrApvWkpPK.Meta_.startDate), refDate));
			add(cb.greaterThanOrEqualTo(root.get(Krcmt36AgrApvWkp.Meta_.endDate), refDate));
		}};
		cq.where(wherePredicate.toArray(new Predicate[] {}));

		try {
			Krcmt36AgrApvWkp result = em.createQuery(cq).getSingleResult();
			return Optional.of(result.toDomain());
		} catch (NoResultException e){
			return Optional.empty();
		}
	}

	@Override
	public void updateStartDate(Approver36AgrByWorkplace domain, GeneralDate startDateBeforeChange) {
		val domainData =Krcmt36AgrApvWkp.fromDomain(domain);
		val pk = new Krcmt36AgrApvWkpPK(domain.getWorkplaceId(),startDateBeforeChange);
		Optional<Krcmt36AgrApvWkp> findResult = this.queryProxy().find(pk, Krcmt36AgrApvWkp.class);
		if (findResult.isPresent()) {

			this.commandProxy().remove(Krcmt36AgrApvWkp.class,pk);
			this.getEntityManager().flush();
			this.commandProxy().insert(domainData);
		} else {
			this.commandProxy().insert(domainData);
		}
	}
}
