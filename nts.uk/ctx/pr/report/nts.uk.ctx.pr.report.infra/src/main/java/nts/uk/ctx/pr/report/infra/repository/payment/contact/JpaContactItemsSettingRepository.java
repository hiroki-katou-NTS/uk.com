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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.QismtLaborInsuOffice;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.QismtLaborInsuOfficePK;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.QismtLaborInsuOfficePK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.QismtLaborInsuOffice_;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSetting;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCpPK;

/**
 * The Class this.
 */
@Stateless
public class JpaContactItemsSettingRepository extends JpaRepository
	implements ContactItemsSettingRepository {

	/** The pay bonus atr. */
	public static int PAY_BONUS_ATR = 0;

	/** The spare pay atr. */
	public static int SPARE_PAY_ATR = 0;

	@Inject 
	public Optional<ContactItemsSetting> findByCode(ContactItemsCode code) {
		this.queryProxy().find(new QcmtCommentMonthCpPK(code.getCompanyCode(), code.getProcessingNo().v(), PAY_BONUS_ATR, code.getProcessingYm().v(),
				SPARE_PAY_ATR), QcmtCommentMonthCp.class);
		
		
		this.commandProxy()
		// get entity manager
				EntityManager em = this.getEntityManager();
				CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

				// call QISMT_LABOR_INSU_OFFICE (QismtLaborInsuOffice SQL)
				CriteriaQuery<QcmtCommentMonthCp> cq = criteriaBuilder
					.createQuery(QismtLaborInsuOffice.class);

				// root data
				Root<QismtLaborInsuOffice> root = cq.from(QismtLaborInsuOffice.class);

				// select root
				cq.select(root);

				// add where
				List<Predicate> lstpredicateWhere = new ArrayList<>();

				// eq CompanyCode
				lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(QismtLaborInsuOffice_.qismtLaborInsuOfficePK).get(QismtLaborInsuOfficePK_.ccd),
					companyCode));

				// set where to SQL
				cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

				// creat query
				TypedQuery<QismtLaborInsuOffice> query = em.createQuery(cq);

				// exclude select
				return query.getResultList().stream().map(item -> this.toDomain(item))
					.collect(Collectors.toList());
		return null;
	}

}
