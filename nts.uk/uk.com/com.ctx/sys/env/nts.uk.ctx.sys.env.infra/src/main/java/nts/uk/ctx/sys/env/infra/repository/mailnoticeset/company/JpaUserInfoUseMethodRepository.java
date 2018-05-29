/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstUserInfoUsemethod;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstUserInfoUsemethodPK;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstUserInfoUsemethodPK_;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstUserInfoUsemethod_;

/**
 * The Class JpaUserInfoUseMethodRepository.
 */
@Stateless
public class JpaUserInfoUseMethodRepository extends JpaRepository implements UserInfoUseMethodRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository#
	 * findByCompanyId(java.lang.String)
	 */
	@Override
	public List<UserInfoUseMethod> findByCompanyId(String companyId) {
		List<UserInfoUseMethod> lstReturn = new ArrayList<>();
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SevstUserInfoUsemethod> cq = criteriaBuilder.createQuery(SevstUserInfoUsemethod.class);
		Root<SevstUserInfoUsemethod> root = cq.from(SevstUserInfoUsemethod.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(SevstUserInfoUsemethod_.sevstUserInfoUsemethodPK).get(SevstUserInfoUsemethodPK_.cid),
				companyId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		List<SevstUserInfoUsemethod> listSevstUserInfoUsemethod = em.createQuery(cq).getResultList();

		// Check exist
		if (!CollectionUtil.isEmpty(listSevstUserInfoUsemethod)) {
			listSevstUserInfoUsemethod.stream().forEach(
					entity -> lstReturn.add(new UserInfoUseMethod(new JpaUserInfoUseMethodGetMemento(entity))));
		}

		return lstReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository#
	 * findByCompanyIdAndSettingItem(java.lang.String,
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem)
	 */
	@Override
	public Optional<UserInfoUseMethod> findByCompanyIdAndSettingItem(String companyId, UserInfoItem settingItem) {
		val pk = new SevstUserInfoUsemethodPK(companyId, settingItem.value);
		return this.queryProxy().find(pk, SevstUserInfoUsemethod.class)
				.map(entity -> new UserInfoUseMethod(new JpaUserInfoUseMethodGetMemento(entity)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository#
	 * update(java.util.List)
	 */
	@Override
	public void update(List<UserInfoUseMethod> lstUserInfo) {
		lstUserInfo.stream().forEach(dom -> {
			Optional<SevstUserInfoUsemethod> optional = this.queryProxy().find(
					new SevstUserInfoUsemethodPK(dom.getCompanyId(), dom.getSettingItem().value),
					SevstUserInfoUsemethod.class);
			SevstUserInfoUsemethod entity = new SevstUserInfoUsemethod(new SevstUserInfoUsemethodPK());
			if (optional.isPresent()) {
				entity = optional.get();
			}
			dom.saveToMemento(new JpaUserInfoUseMethodSetMemento(entity));
			this.commandProxy().update(entity);
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository#
	 * create(java.util.List)
	 */
	@Override
	public void create(List<UserInfoUseMethod> lstUserInfo) {
		List<SevstUserInfoUsemethod> entities = lstUserInfo.stream().map(dom -> {
			SevstUserInfoUsemethod entity = new SevstUserInfoUsemethod(new SevstUserInfoUsemethodPK());
			dom.saveToMemento(new JpaUserInfoUseMethodSetMemento(entity));
			return entity;
		}).collect(Collectors.toList());
		this.commandProxy().insertAll(entities);
	}

}
