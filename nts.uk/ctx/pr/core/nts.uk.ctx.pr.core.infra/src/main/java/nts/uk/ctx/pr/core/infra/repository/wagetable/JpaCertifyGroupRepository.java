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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import mockit.external.asm.Opcodes;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.Company;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.infra.data.entity.SmpmtCompany;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertificationGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupRepository;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QcemtCertification;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertify;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyG;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyGPK;

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

	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

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
		CertifyGroup domain = new CertifyGroup(new CertifyGroupGetMemento() {

			@Override
			public String getName() {
				return entity.getCertifyGroupName();
			}

			@Override
			public MultipleTargetSetting getMultiApplySet() {
				return MultipleTargetSetting.valueOf(entity.getMultiApplySet());
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(entity.getQwtmtWagetableCertifyGPK().getCcd());
			}

			@Override
			public String getCode() {
				return entity.getQwtmtWagetableCertifyGPK().getCertifyGroupCd();
			}

			@Override
			public Set<Certification> getCertifies() {
				Set<Certification> setCertification = new HashSet<>();
				for (QwtmtWagetableCertify wagetableCertify : entity.getQwtmtWagetableCertifyList()) {
					setCertification.add(new Certification(new CertificationGetMemento() {

						@Override
						public String getName() {
							return wagetableCertify.getQcemtCertification().getName();
						}

						@Override
						public CompanyCode getCompanyCode() {
							return new CompanyCode(
									wagetableCertify.getQcemtCertification().getQcemtCertificationPK().getCcd());
						}

						@Override
						public String getCode() {
							return wagetableCertify.getQcemtCertification().getQcemtCertificationPK().getCertCd();
						}
					}));
				}
				return setCertification;
			}
		});

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
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<QwtmtWagetableCertifyG> q = cb.createQuery(QwtmtWagetableCertifyG.class);
		Root<QwtmtWagetableCertifyG> c = q.from(QwtmtWagetableCertifyG.class);
		q.select(c);
		TypedQuery<QwtmtWagetableCertifyG> query = getEntityManager().createQuery(q);
		List<QwtmtWagetableCertifyG> results = query.getResultList();
		List<CertifyGroup> lstCertifyGroup = new ArrayList<>();
		for (QwtmtWagetableCertifyG qwtmtWagetableCertifyG : results) {
			lstCertifyGroup.add(toDomain(qwtmtWagetableCertifyG));
		}
		return lstCertifyGroup;
	}

}
