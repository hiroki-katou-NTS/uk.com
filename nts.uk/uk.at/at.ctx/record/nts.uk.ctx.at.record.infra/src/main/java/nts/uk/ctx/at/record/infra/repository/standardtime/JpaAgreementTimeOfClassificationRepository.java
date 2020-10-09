package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementTimeClass;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementTimeClassPK;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

@Stateless
public class JpaAgreementTimeOfClassificationRepository extends JpaRepository
		implements AgreementTimeOfClassificationRepository {

	private static final String DELETE_BY_TWO_KEYS;

	private static final String FIND;

	private static final String FIND_CLASSIFICATION_SETTING;

	private static final String FIND_CLASSIFICATION_DETAIL;

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

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgeementTimeClass a ");
		builderString.append("WHERE a.kmkmtAgeementTimeClassPK.companyId = :companyId ");
		builderString.append("AND a.kmkmtAgeementTimeClassPK.basicSettingId != NULL ");
		builderString.append("AND a.laborSystemAtr = :laborSystemAtr ");
		FIND_CLASSIFICATION_SETTING = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgeementTimeClass a ");
		builderString.append("WHERE a.kmkmtAgeementTimeClassPK.companyId = :companyId ");
		builderString.append("AND a.kmkmtAgeementTimeClassPK.classificationCode = :classificationCode ");
		builderString.append("AND a.laborSystemAtr = :laborSystemAtr ");
		FIND_CLASSIFICATION_DETAIL = builderString.toString();
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
	
	@Override
	public void update(AgreementTimeOfClassification agreementTimeOfClassification) { 
		this.commandProxy().update(toEntity(agreementTimeOfClassification));
	}

	@Override
	public List<String> findClassificationSetting(String companyId, LaborSystemtAtr laborSystemAtr) {
		return this.queryProxy().query(FIND_CLASSIFICATION_SETTING, KmkmtAgeementTimeClass.class)
				.setParameter("companyId", companyId).setParameter("laborSystemAtr", laborSystemAtr.value)
				.getList(f -> f.kmkmtAgeementTimeClassPK.classificationCode);
	}

	@Override
	public Optional<String> findEmploymentBasicSettingID(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode) {
		return this.queryProxy().query(FIND_CLASSIFICATION_DETAIL, KmkmtAgeementTimeClass.class)
				.setParameter("companyId", companyId).setParameter("laborSystemAtr", laborSystemAtr.value)
				.setParameter("classificationCode", classificationCode)
				.getSingle(f -> f.kmkmtAgeementTimeClassPK.basicSettingId);
	}
	
	@Override
	public Optional<AgreementTimeOfClassification> find(String companyId, LaborSystemtAtr laborSystemAtr,
			String classificationCode) {
		return this.queryProxy().query(FIND_CLASSIFICATION_DETAIL, KmkmtAgeementTimeClass.class)
				.setParameter("companyId", companyId).setParameter("laborSystemAtr", laborSystemAtr.value)
				.setParameter("classificationCode", classificationCode).getSingle(x -> toDomain(x));
	}

	@Override
	public List<AgreementTimeOfClassification> find(String companyId, List<String> classificationCode) {
//		if(classificationCode.isEmpty()){
//			return new ArrayList<>();
//		}
//		String query = "SELECT a FROM KmkmtAgeementTimeClass a WHERE a.kmkmtAgeementTimeClassPK.companyId = :companyId "
//				+ "AND a.kmkmtAgeementTimeClassPK.classificationCode in :classificationCode ";
//		
//		return this.queryProxy().query(query, KmkmtAgeementTimeClass.class)
//				.setParameter("companyId", companyId)
//				.setParameter("classificationCode", classificationCode)
//				.getList(f -> toDomain(f));

		return new ArrayList<>();
	}

	private KmkmtAgeementTimeClass toEntity(AgreementTimeOfClassification agreementTimeOfClassification) {
		val entity = new KmkmtAgeementTimeClass();

		entity.kmkmtAgeementTimeClassPK = new KmkmtAgeementTimeClassPK();
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		entity.kmkmtAgeementTimeClassPK.basicSettingId = agreementTimeOfClassification.getBasicSettingId();
		entity.kmkmtAgeementTimeClassPK.classificationCode = agreementTimeOfClassification.getClassificationCode().v();
		entity.kmkmtAgeementTimeClassPK.companyId = agreementTimeOfClassification.getCompanyId();
//		entity.laborSystemAtr = agreementTimeOfClassification.getLaborSystemAtr().value;
//		entity.upperMonth = agreementTimeOfClassification.getUpperAgreementSetting().getUpperMonth().v().intValue();
//		entity.upperMonthAverage = agreementTimeOfClassification.getUpperAgreementSetting().getUpperMonthAverage().v().intValue();

		return entity;
	}

	@Override
	public List<AgreementTimeOfClassification> find(String companyId, LaborSystemtAtr laborSystemAtr) {
		return this.queryProxy().query(FIND, KmkmtAgeementTimeClass.class).setParameter("companyId", companyId)
				.setParameter("laborSystemAtr", laborSystemAtr.value).getList(f -> toDomain(f));
	}

	private static AgreementTimeOfClassification toDomain(KmkmtAgeementTimeClass kmkmtAgeementTimeClass) {
		/** TODO: 36協定時間対応により、コメントアウトされた */
		return null;
//		AgreementTimeOfClassification agreementTimeOfClassification = AgreementTimeOfClassification.createJavaType(
//				kmkmtAgeementTimeClass.kmkmtAgeementTimeClassPK.companyId,
//				kmkmtAgeementTimeClass.kmkmtAgeementTimeClassPK.basicSettingId, kmkmtAgeementTimeClass.laborSystemAtr,
//				kmkmtAgeementTimeClass.kmkmtAgeementTimeClassPK.classificationCode,
//				kmkmtAgeementTimeClass.upperMonth, kmkmtAgeementTimeClass.upperMonthAverage);
//		return agreementTimeOfClassification;
	}
}
