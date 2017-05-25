/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.conctact;

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
import nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSetting;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthPs;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthPsPK_;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthPs_;
import nts.uk.ctx.pr.report.infra.repository.payment.conctact.memento.JpaContactPersonalSettingGetMemento;
import nts.uk.ctx.pr.report.infra.repository.payment.conctact.memento.JpaContactPersonalSettingSetMemento;

/**
 * The Class JpaContactPersonalSettingRepository.
 */
@Stateless
public class JpaContactPersonalSettingRepository extends JpaRepository
	implements ContactPersonalSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingRepository
	 * #create(nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSetting)
	 */
	@Override
	public void create(ContactPersonalSetting personalSetting) {
		this.commandProxy().update(this.toEntity(personalSetting));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingRepository
	 * #remove(nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSetting)
	 */
	@Override
	public void remove(ContactPersonalSetting setting) {
		this.commandProxy().remove(this.toEntity(setting));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingRepository
	 * #findAll(java.lang.String)
	 */
	@Override
	public List<ContactPersonalSetting> findAll(String ccd) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QctmtCommentMonthPs> cq = cb.createQuery(QctmtCommentMonthPs.class);
		Root<QctmtCommentMonthPs> root = cq.from(QctmtCommentMonthPs.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(
			root.get(QctmtCommentMonthPs_.qctmtCommentMonthPsPK).get(QctmtCommentMonthPsPK_.ccd),
			ccd));

		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
			.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingRepository
	 * #findAll(java.lang.String, int, int)
	 */
	@Override
	public List<ContactPersonalSetting> findAll(String ccd, int processingYm, int processingNo) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QctmtCommentMonthPs> cq = cb.createQuery(QctmtCommentMonthPs.class);
		Root<QctmtCommentMonthPs> root = cq.from(QctmtCommentMonthPs.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(
			root.get(QctmtCommentMonthPs_.qctmtCommentMonthPsPK).get(QctmtCommentMonthPsPK_.ccd),
			ccd));
		predicateList.add(cb.equal(root.get(QctmtCommentMonthPs_.qctmtCommentMonthPsPK)
			.get(QctmtCommentMonthPsPK_.processingNo), processingNo));
		predicateList.add(cb.equal(root.get(QctmtCommentMonthPs_.qctmtCommentMonthPsPK)
			.get(QctmtCommentMonthPsPK_.processingYm), processingYm));

		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
			.collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the contact personal setting
	 */
	private ContactPersonalSetting toDomain(QctmtCommentMonthPs entity) {
		JpaContactPersonalSettingGetMemento memento = new JpaContactPersonalSettingGetMemento(
			entity);
		ContactPersonalSetting domain = new ContactPersonalSetting(memento);
		return domain;
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the qctmt comment month ps
	 */
	private QctmtCommentMonthPs toEntity(ContactPersonalSetting domain) {
		QctmtCommentMonthPs entity = new QctmtCommentMonthPs();
		domain.saveToMemento(new JpaContactPersonalSettingSetMemento(entity));
		return entity;
	}
}
