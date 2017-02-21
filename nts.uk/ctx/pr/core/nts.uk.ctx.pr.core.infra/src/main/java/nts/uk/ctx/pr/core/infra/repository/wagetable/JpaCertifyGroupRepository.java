/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import nts.uk.ctx.core.infra.data.entity.SmpmtCompany;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertificationGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupRepository;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertify;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyG;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyGPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyGPK_;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyG_;
import nts.uk.ctx.pr.core.infra.repository.insurance.social.JpaSocialInsuranceOfficeGetMemento;

@Stateless
public class JpaCertifyGroupRepository extends JpaRepository implements CertifyGroupRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupRepository#add(nts.uk.ctx.pr
	 * .core.dom.wagetable.CertifyGroup)
	 */
	@Override
	public void add(CertifyGroup certifyGroup) {
		this.commandProxy().insert(toEntity(certifyGroup));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupRepository#update(nts.uk.ctx
	 * .pr.core.dom.wagetable.CertifyGroup)
	 */
	@Override
	public void update(CertifyGroup certifyGroup) {
		this.commandProxy().update(toEntity(certifyGroup));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupRepository#remove(nts.uk.ctx
	 * .core.dom.company.CompanyCode, java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(CompanyCode companyCode, String groupCode, Long version) {
		this.commandProxy().remove(QwtmtWagetableCertifyG.class,
				new QwtmtWagetableCertifyGPK(companyCode.v(), groupCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupRepository#findById(nts.uk.
	 * ctx.core.dom.company.CompanyCode, java.lang.String)
	 */
	@Override
	public Optional<CertifyGroup> findById(CompanyCode companyCode, String code) {
		return this.queryProxy().find(new QwtmtWagetableCertifyGPK(companyCode.v(), code), QwtmtWagetableCertifyG.class)
				.map(c -> toDomain(c));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the certify group
	 */
	private static CertifyGroup toDomain(QwtmtWagetableCertifyG entity) {
		CertifyGroup domain = new CertifyGroup(new JpaCertifyGroupGetMemento(entity));
		return domain;

	}

	private QwtmtWagetableCertifyG toEntity(CertifyGroup certifyGroup) {
		QwtmtWagetableCertifyG entity = new QwtmtWagetableCertifyG();
		certifyGroup.saveToMemento(new JpaCertifyGroupSetMemento(entity));
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupRepository#findAll(nts.uk.
	 * ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public List<CertifyGroup> findAll(CompanyCode companyCode) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QwtmtWagetableCertifyG> cq = criteriaBuilder.createQuery(QwtmtWagetableCertifyG.class);
		Root<QwtmtWagetableCertifyG> root = cq.from(QwtmtWagetableCertifyG.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QwtmtWagetableCertifyG_.qwtmtWagetableCertifyGPK).get(QwtmtWagetableCertifyGPK_.ccd),
				companyCode.v()));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QwtmtWagetableCertifyG> query = em.createQuery(cq);
		List<CertifyGroup> lstCertifyGroup = query.getResultList().stream().map(item -> toDomain(item))
				.collect(Collectors.toList());
		return lstCertifyGroup;
	}

}
