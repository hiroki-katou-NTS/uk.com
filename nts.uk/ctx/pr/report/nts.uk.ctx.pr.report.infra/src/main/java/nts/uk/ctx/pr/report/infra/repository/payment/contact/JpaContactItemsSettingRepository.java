/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.contact;

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
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthEm_;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthEm;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthEmPK_;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSetting;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCp;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCpPK;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCpInitialCmt;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCpInitialCmtPK;

/**
 * The Class JpaContactItemsSettingRepository.
 */
@Stateless
public class JpaContactItemsSettingRepository extends JpaRepository
	implements ContactItemsSettingRepository {

	/** The pay bonus atr. */
	public static int PAY_BONUS_ATR = 0;

	/** The spare pay atr. */
	public static int SPARE_PAY_ATR = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingRepository#
	 * findByCode(nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode,
	 * java.util.List)
	 */
	@Override
	public ContactItemsSetting findByCode(ContactItemsCode code, List<String> empCds) {
		Optional<QcmtCommentMonthCp> commentMonthCp = this.findCommentMonthCp(code);
		JpaContactItemsSettingGetMemento jpa = new JpaContactItemsSettingGetMemento();
		if (commentMonthCp.isPresent()) {
			jpa.setCommentMonthCp(commentMonthCp.get());
		}

		Optional<QctmtCpInitialCmt> commentInitCp = this.findCommentInitCp(code);

		if (commentInitCp.isPresent()) {
			jpa.setCommentInitialCp(commentInitCp.get());
		}
		jpa.setCommentMonthEmps(this.findCommentMonthEmp(code, empCds));
		return new ContactItemsSetting(jpa);
	}

	/**
	 * Find comment month cp.
	 *
	 * @param code
	 *            the code
	 * @return the optional
	 */
	private Optional<QcmtCommentMonthCp> findCommentMonthCp(ContactItemsCode code) {
		return this.queryProxy().find(
			new QcmtCommentMonthCpPK(code.getCompanyCode(), code.getProcessingNo().v(),
				PAY_BONUS_ATR, code.getProcessingYm().v(), SPARE_PAY_ATR),
			QcmtCommentMonthCp.class);
	}

	/**
	 * Find comment init cp.
	 *
	 * @param code
	 *            the code
	 * @return the optional
	 */
	private Optional<QctmtCpInitialCmt> findCommentInitCp(ContactItemsCode code) {
		return this.queryProxy().find(
			new QctmtCpInitialCmtPK(code.getCompanyCode(), PAY_BONUS_ATR, SPARE_PAY_ATR),
			QctmtCpInitialCmt.class);
	}

	private List<QctmtCommentMonthEm> findCommentMonthEmp(ContactItemsCode code,
		List<String> empCds) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call QISMT_LABOR_INSU_OFFICE (QismtLaborInsuOffice SQL)
		CriteriaQuery<QctmtCommentMonthEm> cq = criteriaBuilder
			.createQuery(QctmtCommentMonthEm.class);

		// root data
		Root<QctmtCommentMonthEm> root = cq.from(QctmtCommentMonthEm.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq CompanyCode
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(QctmtCommentMonthEm_.qctmtCommentMonthEmPK).get(QctmtCommentMonthEmPK_.ccd),
			code.getCompanyCode()));


		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<QctmtCommentMonthEm> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingRepository#
	 * save(nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSetting)
	 */
	@Override
	public void save(ContactItemsSetting contactItemsSetting) {

		JpaContactItemsSettingSetMemento jpa = new JpaContactItemsSettingSetMemento();
		contactItemsSetting.saveToMemento(jpa);
		this.commandProxy().update(jpa.getCommentInitialCp());
		this.commandProxy().updateAll(jpa.getCommentInitialEmps());
		this.commandProxy().updateAll(jpa.getCommentMonthEmps());
		this.commandProxy().update(jpa.getCommentMonthCp());
	}
}
