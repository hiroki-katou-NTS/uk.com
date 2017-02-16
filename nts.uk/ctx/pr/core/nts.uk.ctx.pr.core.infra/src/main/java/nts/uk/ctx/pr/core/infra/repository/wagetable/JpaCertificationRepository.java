package nts.uk.ctx.pr.core.infra.repository.wagetable;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertificationGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.CertificationReponsitory;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QcemtCertification;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyG;

@Stateless
public class JpaCertificationRepository extends JpaRepository implements CertificationReponsitory {

	@Override
	public void add(Certification certification) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Certification certification) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Certification> findAll(CompanyCode companyCode) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<QcemtCertification> q = cb.createQuery(QcemtCertification.class);
		Root<QcemtCertification> c = q.from(QcemtCertification.class);
		q.select(c);
		TypedQuery<QcemtCertification> query = getEntityManager().createQuery(q);
		List<QcemtCertification> results = query.getResultList();
		List<Certification> lstCertification = new ArrayList<>();
		for (QcemtCertification qcemtCertification : results) {
			lstCertification.add(toDomain(qcemtCertification));
		}
		return lstCertification;
	}

	@Override
	public Certification findById(CompanyCode companyCode, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDuplicateCode(CompanyCode companyCode, String code) {
		// TODO Auto-generated method stub
		return false;
	}

	private Certification toDomain(QcemtCertification qcemtCertification) {
		Certification certification = new Certification(new CertificationGetMemento() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return qcemtCertification.getName();
			}

			@Override
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return new CompanyCode(qcemtCertification.getQcemtCertificationPK().getCcd());
			}

			@Override
			public String getCode() {
				// TODO Auto-generated method stub
				return qcemtCertification.getQcemtCertificationPK().getCertCd();
			}
		});
		return certification;
	}
}
