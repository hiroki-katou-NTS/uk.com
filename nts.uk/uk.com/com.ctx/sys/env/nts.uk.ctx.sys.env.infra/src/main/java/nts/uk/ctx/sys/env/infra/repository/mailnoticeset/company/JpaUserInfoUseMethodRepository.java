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
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtUseContactCom;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtUseContactComPK;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtUseContactComPK_;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtUseContactCom_;

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
		CriteriaQuery<SevmtUseContactCom> cq = criteriaBuilder.createQuery(SevmtUseContactCom.class);
		Root<SevmtUseContactCom> root = cq.from(SevmtUseContactCom.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(SevmtUseContactCom_.sevmtUseContactComPK).get(SevmtUseContactComPK_.cid),
				companyId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		List<SevmtUseContactCom> listSevmtUseContactCom = em.createQuery(cq).getResultList();

		// Check exist
		if (!CollectionUtil.isEmpty(listSevmtUseContactCom)) {
			listSevmtUseContactCom.stream().forEach(
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
		val pk = new SevmtUseContactComPK(companyId, settingItem.value);
		return this.queryProxy().find(pk, SevmtUseContactCom.class)
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
			Optional<SevmtUseContactCom> optional = this.queryProxy().find(
					new SevmtUseContactComPK(dom.getCompanyId(), dom.getSettingItem().value),
					SevmtUseContactCom.class);
			SevmtUseContactCom entity = new SevmtUseContactCom(new SevmtUseContactComPK());
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
		List<SevmtUseContactCom> entities = lstUserInfo.stream().map(dom -> {
			SevmtUseContactCom entity = new SevmtUseContactCom(new SevmtUseContactComPK());
			dom.saveToMemento(new JpaUserInfoUseMethodSetMemento(entity));
			return entity;
		}).collect(Collectors.toList());
		this.commandProxy().insertAll(entities);
	}

}
