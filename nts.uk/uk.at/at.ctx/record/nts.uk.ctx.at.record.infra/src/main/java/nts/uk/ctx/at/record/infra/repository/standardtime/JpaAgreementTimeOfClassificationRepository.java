package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.math.BigDecimal;
import java.util.List;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfClassification;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementTimeClass;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementTimeClassPK;

public class JpaAgreementTimeOfClassificationRepository extends JpaRepository
		implements AgreementTimeOfClassificationRepository {

	private static final String DELETE_BY_TWO_KEYS;

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KmkmtAgeementTimeClass a ");
		builderString.append("WHERE a.kmkmtAgeementTimeClassPK.companyId = :companyId ");
		builderString.append("AND a.kmkmtAgeementTimeClassPK.classificationCode = :classificationCode ");
		builderString.append("AND a.laborSystemAtr = :laborSystemAtr ");
		DELETE_BY_TWO_KEYS = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgeementTimeClass a ");
		builderString.append("WHERE a.kmkmtAgeementTimeClassPK.companyId = :companyId ");
		builderString.append("AND a.laborSystemAtr = :laborSystemAtr ");
		FIND = builderString.toString();
	}

	@Override
	public void remove(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode) {
		this.getEntityManager().createQuery(DELETE_BY_TWO_KEYS).setParameter("companyId", companyId)
				.setParameter("classificationCode", classificationCode)
				.setParameter("laborSystemAtr", laborSystemAtr.value).executeUpdate();
	}

	@Override
	public void add(AgreementTimeOfClassification agreementTimeOfClassification) {
		this.commandProxy().insert(toEntity(agreementTimeOfClassification));
	}

	private KmkmtAgeementTimeClass toEntity(AgreementTimeOfClassification agreementTimeOfClassification) {
		val entity = new KmkmtAgeementTimeClass();

		entity.kmkmtAgeementTimeClassPK = new KmkmtAgeementTimeClassPK();
		entity.kmkmtAgeementTimeClassPK.basicSettingId = agreementTimeOfClassification.getBasicSettingId();
		entity.kmkmtAgeementTimeClassPK.classificationCode = agreementTimeOfClassification.getClassificationCode();
		entity.kmkmtAgeementTimeClassPK.companyId = agreementTimeOfClassification.getCompanyId();
		entity.laborSystemAtr = new BigDecimal(agreementTimeOfClassification.getLaborSystemAtr().value);

		return entity;
	}

	@Override
	public List<AgreementTimeOfClassification> find(String companyId, LaborSystemtAtr laborSystemAtr) {
		return this.queryProxy().query(FIND, KmkmtAgeementTimeClass.class).setParameter("companyId", companyId)
				.setParameter("laborSystemAtr", laborSystemAtr.value).getList(f -> toDomain(f));
	}

	private static AgreementTimeOfClassification toDomain(KmkmtAgeementTimeClass kmkmtAgeementTimeClass) {
		AgreementTimeOfClassification agreementTimeOfClassification = AgreementTimeOfClassification.createJavaType(
				kmkmtAgeementTimeClass.kmkmtAgeementTimeClassPK.companyId,
				kmkmtAgeementTimeClass.kmkmtAgeementTimeClassPK.basicSettingId, kmkmtAgeementTimeClass.laborSystemAtr,
				kmkmtAgeementTimeClass.kmkmtAgeementTimeClassPK.classificationCode);
		return agreementTimeOfClassification;
	}
}
