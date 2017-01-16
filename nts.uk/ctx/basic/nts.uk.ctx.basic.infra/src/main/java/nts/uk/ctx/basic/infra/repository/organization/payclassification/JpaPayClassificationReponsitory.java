package nts.uk.ctx.basic.infra.repository.organization.payclassification;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassification;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationRepository;
import nts.uk.ctx.basic.infra.entity.organization.payclassification.QmnmtPayClass;
import nts.uk.ctx.basic.infra.entity.organization.payclassification.QmnmtPayClassPK;

@Stateless
public class JpaPayClassificationReponsitory extends JpaRepository implements PayClassificationRepository {

	private final String SELECT_NO_WHERE = "SELECT c FROM QmnmtPayClass c";
	private final String SELECT_ALL_BY_COMPANY = SELECT_NO_WHERE + " WHERE c.qmnmtPayClassPK.companyCd = :companyCd";

	private final PayClassification toDomain(QmnmtPayClass entity) {
		val domain = PayClassification.createFromJavaType(entity.payClassName, entity.qmnmtPayClassPK.payClassCode,
				entity.qmnmtPayClassPK.companyCd, entity.memo);

		return domain;
	}

	private QmnmtPayClass toEntity(PayClassification domain) {
		val entity = new QmnmtPayClass();

		entity.qmnmtPayClassPK = new QmnmtPayClassPK();
		entity.qmnmtPayClassPK.payClassCode = domain.getPayClassCode().v();
		entity.payClassName = domain.getPayClassName().v();
		entity.qmnmtPayClassPK.companyCd = domain.getCompanyCode();
		entity.memo = domain.getMemo().v();

		return entity;
	}

	@Override
	public void add(PayClassification payClassification) {
		this.commandProxy().insert(toEntity(payClassification));

	}

	@Override
	public void update(PayClassification payClassification) {
		this.commandProxy().update(toEntity(payClassification));

	}

	@Override
	public List<PayClassification> getPayClassifications(String companyCode) {
		return this.queryProxy().query(SELECT_ALL_BY_COMPANY, QmnmtPayClass.class)
				.setParameter("companyCd", companyCode)

				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<PayClassification> getPayClassification(String companyCode, String payClassCode) {
		try {
			return this.queryProxy().find(new QmnmtPayClassPK(companyCode, payClassCode), QmnmtPayClass.class)
					.map(c -> toDomain(c));
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void remove(String companyCode) {
		val objectKey = new QmnmtPayClassPK();
		objectKey.companyCd = companyCode;

		this.commandProxy().remove(QmnmtPayClass.class, objectKey);
	}

}
