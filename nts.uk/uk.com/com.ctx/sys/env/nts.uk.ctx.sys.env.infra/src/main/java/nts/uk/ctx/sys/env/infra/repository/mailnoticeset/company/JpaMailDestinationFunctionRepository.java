package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstMailDestinFunc;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstMailDestinFuncPK_;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstMailDestinFunc_;

@Stateless
public class JpaMailDestinationFunctionRepository extends JpaRepository implements MailDestinationFunctionRepository {

	@Override
	public MailDestinationFunction findByCidAndSettingItem(String companyId, UserInfoItem userInfoItem) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SevstMailDestinFunc> cq = criteriaBuilder.createQuery(SevstMailDestinFunc.class);
		Root<SevstMailDestinFunc> root = cq.from(SevstMailDestinFunc.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(SevstMailDestinFunc_.sevstMailDestinFuncPK).get(SevstMailDestinFuncPK_.cid), companyId));
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(SevstMailDestinFunc_.sevstMailDestinFuncPK).get(SevstMailDestinFuncPK_.settingItem),
				userInfoItem));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		List<SevstMailDestinFunc> listSevstMailDestinFunc = em.createQuery(cq).getResultList();

		// Check exist
		if (CollectionUtil.isEmpty(listSevstMailDestinFunc)) {
			return null;
		}

		// Return
		return new MailDestinationFunction(new JpaMailDestinationFunctionGetMemento(listSevstMailDestinFunc));
	}

	@Override
	public void add(MailDestinationFunction domain) {
		List<SevstMailDestinFunc> entities = new ArrayList<>();
		domain.saveToMemento(new JpaMailDestinationFunctionSetMemento(entities,domain.getCompanyId()));
		this.commandProxy().insertAll(entities);
	}

	@Override
	public void update(MailDestinationFunction domain) {
		
	}

	@Override
	public void remove(String companyId, UserInfoItem userInfoItem) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaDelete<SevstMailDestinFunc> cq = criteriaBuilder.createCriteriaDelete(SevstMailDestinFunc.class);
		Root<SevstMailDestinFunc> root = cq.from(SevstMailDestinFunc.class);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(SevstMailDestinFunc_.sevstMailDestinFuncPK).get(SevstMailDestinFuncPK_.cid), companyId));
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(SevstMailDestinFunc_.sevstMailDestinFuncPK).get(SevstMailDestinFuncPK_.settingItem),
				userInfoItem));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		em.createQuery(cq).executeUpdate();
	}

}
