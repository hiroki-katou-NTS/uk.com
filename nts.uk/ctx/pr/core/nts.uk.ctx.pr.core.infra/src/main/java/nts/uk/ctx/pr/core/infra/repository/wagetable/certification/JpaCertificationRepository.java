/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.certification;

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
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationReponsitory;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QcemtCertification;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QcemtCertificationPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QcemtCertificationPK_;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QcemtCertification_;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertify;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyPK_;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertify_;

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
	// Function get all Certification by companyCode
	@Override
	public List<Certification> findAll(String companyCode) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call QCEMT_CERTIFICATION (QcemtCertification SQL)
		CriteriaQuery<QcemtCertification> cq = criteriaBuilder.createQuery(QcemtCertification.class);

		// root data
		Root<QcemtCertification> root = cq.from(QcemtCertification.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq CompanyCode
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(QcemtCertification_.qcemtCertificationPK).get(QcemtCertificationPK_.ccd), companyCode));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<QcemtCertification> query = em.createQuery(cq);

		// exclude select
		List<Certification> lstCertification = query.getResultList().stream().map(item -> this.toDomain(item))
			.collect(Collectors.toList());

		return lstCertification;
	}

	/**
	 * To domain.
	 *
	 * @param qcemtCertification
	 *            the qcemt certification
	 * @return the certification
	 */
	private Certification toDomain(QcemtCertification qcemtCertification) {
		return new Certification(new JpaCertificationGetMemento(qcemtCertification));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationReponsitory#
	 * findAllNoneOfGroup(nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	// Function get all Certification none of Group
	@Override
	public List<Certification> findAllNoneOfGroup(String companyCode) {
		// getAll
		List<Certification> lstCertificationFull = this.findAll(companyCode);
		List<Certification> resCertification = new ArrayList<>();
		for (Certification certification : lstCertificationFull) {
			if (!this.checkExistOfGroup(companyCode, certification.getCode(), null)) {
				resCertification.add(certification);
			}
		}
		return resCertification;
	}

	/**
	 * Check exist of group.
	 *
	 * @param certification
	 *            the certification
	 * @param certifyGroupCodeNone
	 *            the certify group code none
	 * @return true, if successful
	 */
	// check Certification ExistOfGroup and none of CertifyGroupCode
	private boolean checkExistOfGroup(String companyCode, String certificationCode, String certifyGroupCode) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// QWTMT_WAGETABLE_CERTIFY (QwtmtWagetableCertify) SQL
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);

		// root data QwtmtWagetableCertify
		Root<QwtmtWagetableCertify> root = cq.from(QwtmtWagetableCertify.class);

		// select count(*) root (SQL SELECT)
		cq.select(criteriaBuilder.count(root));

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq CompanyCode (where)
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(QwtmtWagetableCertify_.qwtmtWagetableCertifyPK).get(QwtmtWagetableCertifyPK_.ccd),
			companyCode));

		// eq CerticationCode (where)
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(QwtmtWagetableCertify_.qwtmtWagetableCertifyPK).get(QwtmtWagetableCertifyPK_.certifyCd),
			certificationCode));

		// noteq CertifyGroupCodeNone (certifyGroupCodeNone not null)
		if (certifyGroupCode != null) {
			lstpredicateWhere
			.add(criteriaBuilder.notEqual(root.get(QwtmtWagetableCertify_.qwtmtWagetableCertifyPK)
				.get(QwtmtWagetableCertifyPK_.certifyGroupCd), certifyGroupCode));
		}

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		return (em.createQuery(cq).getSingleResult() > 0);
	}

	@Override
	public Optional<Certification> findById(String companyCode, String certificationCode,
		String certifyGroupCode) {
		if (this.checkExistOfGroup(companyCode, certificationCode, certifyGroupCode)) {
			return this.queryProxy()
				.find(new QcemtCertificationPK(companyCode, certificationCode), QcemtCertification.class)
				.map(c -> this.toDomain(c));
		}
		return Optional.empty();
	}
}
