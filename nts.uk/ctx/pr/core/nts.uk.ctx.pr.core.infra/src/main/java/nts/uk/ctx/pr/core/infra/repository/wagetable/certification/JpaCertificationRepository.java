/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.certification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationReponsitory;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QcemtCertification;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QcemtCertificationPK_;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QcemtCertification_;

/**
 * The Class JpaCertificationRepository.
 */
@Stateless
public class JpaCertificationRepository extends JpaRepository implements CertificationReponsitory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationReponsitory#
	 * findAll(nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public List<Certification> findAll(CompanyCode companyCode) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QcemtCertification> cq = criteriaBuilder
				.createQuery(QcemtCertification.class);
		
		Root<QcemtCertification> root = cq.from(QcemtCertification.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QcemtCertification_.qcemtCertificationPK).get(QcemtCertificationPK_.ccd),
				companyCode.v()));
		
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QcemtCertification> query = em.createQuery(cq);
		
		List<Certification> lstCertifyGroup = query.getResultList().stream()
				.map(item -> toDomain(item)).collect(Collectors.toList());
		
		return lstCertifyGroup;
	}

	/**
	 * To domain.
	 *
	 * @param qcemtCertification
	 *            the qcemt certification
	 * @return the certification
	 */
	private Certification toDomain(QcemtCertification qcemtCertification) {
		Certification certification = new Certification(new CertificationGetMemento() {

			@Override
			public String getName() {
				return qcemtCertification.getName();
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(qcemtCertification.getQcemtCertificationPK().getCcd());
			}

			@Override
			public String getCode() {
				return qcemtCertification.getQcemtCertificationPK().getCertCd();
			}
		});
		return certification;
	}
}
