/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertificationGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupRepository;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertify;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyG;

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
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(fromDomain(certifyGroup));
		em.getTransaction().commit();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public CertifyGroup findById(CompanyCode companyCode, WageTableCode code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDuplicateCode(CompanyCode companyCode, WageTableCode code) {
		// TODO Auto-generated method stub
		return false;
	}

	private static CertifyGroup toDomain(QwtmtWagetableCertifyG entity) {
		CertifyGroup domain = new CertifyGroup(new CertifyGroupGetMemento() {

			@Override
			public WageTableCode getWageTableCode() {
				// TODO Auto-generated method stub
				return new WageTableCode("Wagetable");
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return entity.getCertifyGroupName();
			}

			@Override
			public MultipleTargetSetting getMultiApplySet() {
				// TODO Auto-generated method stub
				return MultipleTargetSetting.valueOf(entity.getMultiApplySet());
			}

			@Override
			public String getHistoryId() {
				// TODO Auto-generated method stub
				return "historyId";
			}

			@Override
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return new CompanyCode(entity.getQwtmtWagetableCertifyGPK().getCcd());
			}

			@Override
			public String getCode() {
				// TODO Auto-generated method stub
				return entity.getQwtmtWagetableCertifyGPK().getCertifyGroupCd();
			}

			@Override
			public Set<Certification> getCertifies() {
				// TODO Auto-generated method stub
				Set<Certification> setCertification = new HashSet<>();
				for (QwtmtWagetableCertify wagetableCertify : entity.getQwtmtWagetableCertifyList()) {
					setCertification.add(new Certification(new CertificationGetMemento() {

						@Override
						public String getName() {
							// TODO Auto-generated method stub
							return wagetableCertify.getQcemtCertification().getName();
						}

						@Override
						public CompanyCode getCompanyCode() {
							// TODO Auto-generated method stub
							return new CompanyCode(
									wagetableCertify.getQcemtCertification().getQcemtCertificationPK().getCcd());
						}

						@Override
						public String getCode() {
							// TODO Auto-generated method stub
							return wagetableCertify.getQcemtCertification().getQcemtCertificationPK().getCertCd();
						}
					}));
				}
				return setCertification;
			}
		});

		return domain;
	}

	private QwtmtWagetableCertifyG fromDomain(CertifyGroup certifyGroup) {
		QwtmtWagetableCertifyG qwtmtWagetableCertifyG = new QwtmtWagetableCertifyG();
		return qwtmtWagetableCertifyG;
	}

	@Override
	public List<CertifyGroup> findAll(CompanyCode companyCode) {
		// TODO Auto-generated method stub
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
