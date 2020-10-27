/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtMailDestinFunc;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtMailDestinFuncPK;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtMailDestinFuncPK_;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtMailDestinFunc_;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class JpaMailDestinationFunctionRepository.
 */
@Stateless
public class JpaMailDestinationFunctionRepository extends JpaRepository implements MailDestinationFunctionRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * MailDestinationFunctionRepository#findByCidAndSettingItem(java.lang.
	 * String, nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem)
	 */
	@Override
	public MailDestinationFunction findByCidAndSettingItem(String companyId, UserInfoItem userInfoItem) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SevmtMailDestinFunc> cq = criteriaBuilder.createQuery(SevmtMailDestinFunc.class);
		Root<SevmtMailDestinFunc> root = cq.from(SevmtMailDestinFunc.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(SevmtMailDestinFunc_.sevmtMailDestinFuncPK).get(SevmtMailDestinFuncPK_.cid), companyId));
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(SevmtMailDestinFunc_.sevmtMailDestinFuncPK).get(SevmtMailDestinFuncPK_.settingItem),
				userInfoItem.value));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		List<SevmtMailDestinFunc> listSevmtMailDestinFunc = em.createQuery(cq).getResultList();

		// Check exist
		if (CollectionUtil.isEmpty(listSevmtMailDestinFunc)) {
			return null;
		}

		// Return
		return new MailDestinationFunction(new JpaMailDestinationFunctionGetMemento(listSevmtMailDestinFunc));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * MailDestinationFunctionRepository#add(nts.uk.ctx.sys.env.dom.
	 * mailnoticeset.company.MailDestinationFunction)
	 */
	@Override
	public void add(MailDestinationFunction domain) {
		List<SevmtMailDestinFunc> entities = new ArrayList<>();
		domain.saveToMemento(new JpaMailDestinationFunctionSetMemento(entities, domain.getCompanyId()));
		this.commandProxy().insertAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * MailDestinationFunctionRepository#remove(java.lang.String,
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem)
	 */
	@Override
	public void remove(String companyId, UserInfoItem userInfoItem) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaDelete<SevmtMailDestinFunc> cq = criteriaBuilder.createCriteriaDelete(SevmtMailDestinFunc.class);
		Root<SevmtMailDestinFunc> root = cq.from(SevmtMailDestinFunc.class);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(SevmtMailDestinFunc_.sevmtMailDestinFuncPK).get(SevmtMailDestinFuncPK_.cid), companyId));
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(SevmtMailDestinFunc_.sevmtMailDestinFuncPK).get(SevmtMailDestinFuncPK_.settingItem),
				userInfoItem.value));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		em.createQuery(cq).executeUpdate();
	}

	@Override
	@SneakyThrows
	public List<MailDestinationFunction> findByCidSettingItemAndUse(String cID, Integer functionID, NotUseAtr use) {

		List<SevmtMailDestinFunc> listSevmtMailDestinFunc;

		String sql = "select * from SEVMT_MAIL_DESTIN_FUNC"
				+ " where CID = ?"
				+ " and FUNCTION_ID = ?"
				+ " and SEND_SET = ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, cID);
			stmt.setInt(2, functionID);
			stmt.setInt(3, use.value);
			
			listSevmtMailDestinFunc = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
				SevmtMailDestinFunc ent = new SevmtMailDestinFunc();
				SevmtMailDestinFuncPK pk = new SevmtMailDestinFuncPK();
				pk.setCid(rec.getString("CID"));
				pk.setSettingItem(rec.getInt("SETTING_ITEM"));
				pk.setFunctionId(rec.getInt("FUNCTION_ID"));
				ent.setSevmtMailDestinFuncPK(pk);
				ent.setSendSet(rec.getInt("SEND_SET"));
				return ent;
			});
		}
		
		// Check exist
		if (CollectionUtil.isEmpty(listSevmtMailDestinFunc)) {
			return null;
		}
		List<Integer> keys = new ArrayList<Integer>();
		listSevmtMailDestinFunc.forEach(x -> {
			if (!keys.contains(x.getSevmtMailDestinFuncPK().getSettingItem())) {
				keys.add(x.getSevmtMailDestinFuncPK().getSettingItem());
			}
		});
		List<MailDestinationFunction> result = new ArrayList<MailDestinationFunction>();
		keys.forEach(x -> {
			List<SevmtMailDestinFunc> entity = listSevmtMailDestinFunc.stream()
					.filter(item -> item.getSevmtMailDestinFuncPK().getSettingItem().equals(x))
					.collect(Collectors.toList());
			result.add(new MailDestinationFunction(new JpaMailDestinationFunctionGetMemento(entity), use));
		});
		return result;

	}

}
