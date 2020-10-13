package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentRepostitory;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementTimeEmployment;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementTimeEmploymentPK;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@Stateless
public class JpaAgreementTimeOfEmploymentRepostitory extends JpaRepository
		implements AgreementTimeOfEmploymentRepostitory {

	private static final String DELETE_BY_TWO_KEYS;

	private static final String FIND_EMPLOYMENT_DETAIL;

	private static final String FIND_EMPLOYMENT_SETTING;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KmkmtAgeementTimeEmployment a ");
		builderString.append("WHERE a.kmkmtAgeementTimeEmploymentPK.companyId = :companyId ");
		builderString.append("AND a.kmkmtAgeementTimeEmploymentPK.employmentCategoryCode = :employmentCategoryCode ");
		builderString.append("AND a.laborSystemAtr = :laborSystemAtr ");
		DELETE_BY_TWO_KEYS = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgeementTimeEmployment a ");
		builderString.append("WHERE a.kmkmtAgeementTimeEmploymentPK.companyId = :companyId ");
		builderString.append("AND a.kmkmtAgeementTimeEmploymentPK.employmentCategoryCode = :employmentCategoryCode ");
		builderString.append("AND a.laborSystemAtr = :laborSystemAtr ");
		FIND_EMPLOYMENT_DETAIL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgeementTimeEmployment a ");
		builderString.append("WHERE a.kmkmtAgeementTimeEmploymentPK.companyId = :companyId ");
		builderString.append("AND a.kmkmtAgeementTimeEmploymentPK.basicSettingId != NULL ");
		builderString.append("AND a.laborSystemAtr = :laborSystemAtr ");
		FIND_EMPLOYMENT_SETTING = builderString.toString();
	}

	@Override
	public void add(AgreementTimeOfEmployment agreementTimeOfEmployment) {
		this.commandProxy().insert(toEntity(agreementTimeOfEmployment));
	}
	
	@Override
	public void update(AgreementTimeOfEmployment agreementTimeOfEmployment) {
		this.commandProxy().update(toEntity(agreementTimeOfEmployment));
	}

	@Override
	public void remove(String companyId, String employmentCategoryCode, LaborSystemtAtr laborSystemAtr) {
		this.getEntityManager().createQuery(DELETE_BY_TWO_KEYS).setParameter("companyId", companyId)
				.setParameter("employmentCategoryCode", employmentCategoryCode)
				.setParameter("laborSystemAtr", laborSystemAtr.value).executeUpdate();
	}

	@Override
	public Optional<String> findEmploymentBasicSettingId(String companyId, String employmentCategoryCode,
			LaborSystemtAtr laborSystemAtr) {
		return this.queryProxy().query(FIND_EMPLOYMENT_DETAIL, KmkmtAgeementTimeEmployment.class)
				.setParameter("companyId", companyId).setParameter("laborSystemAtr", laborSystemAtr.value)
				.setParameter("employmentCategoryCode", employmentCategoryCode, EmploymentCode.class)
				.getSingle(f -> f.kmkmtAgeementTimeEmploymentPK.basicSettingId);
	}
	
	@Override
	public Optional<AgreementTimeOfEmployment> find(String companyId, String employmentCategoryCode,
			LaborSystemtAtr laborSystemAtr) {
		return this.queryProxy().query(FIND_EMPLOYMENT_DETAIL, KmkmtAgeementTimeEmployment.class)
				.setParameter("companyId", companyId).setParameter("laborSystemAtr", laborSystemAtr.value)
				.setParameter("employmentCategoryCode", employmentCategoryCode, EmploymentCode.class)
				.getSingle(f -> toDomain(f));
	}

	@Override
	public List<String> findEmploymentSetting(String companyId, LaborSystemtAtr laborSystemAtr) {
		return this.queryProxy().query(FIND_EMPLOYMENT_SETTING, KmkmtAgeementTimeEmployment.class)
				.setParameter("companyId", companyId).setParameter("laborSystemAtr", laborSystemAtr.value)
				.getList(f -> f.kmkmtAgeementTimeEmploymentPK.employmentCategoryCode);
	}
	
	@Override
	public List<AgreementTimeOfEmployment> findEmploymentSetting(String comId, List<String> employments) {
//		if(employments.isEmpty()){
//			return new ArrayList<>();
//		}
//		String query = "SELECT a FROM KmkmtAgeementTimeEmployment a WHERE a.kmkmtAgeementTimeEmploymentPK.companyId = :companyId"
//				+ " AND a.kmkmtAgeementTimeEmploymentPK.employmentCategoryCode IN :employments";
//		
//		return this.queryProxy().query(query, KmkmtAgeementTimeEmployment.class)
//				.setParameter("companyId", comId).setParameter("employments", employments)
//				.getList(f -> toDomain(f));

		return new ArrayList<>();
	}

	private KmkmtAgeementTimeEmployment toEntity(AgreementTimeOfEmployment agreementTimeOfEmployment) {
		val entity = new KmkmtAgeementTimeEmployment();

		entity.kmkmtAgeementTimeEmploymentPK = new KmkmtAgeementTimeEmploymentPK();
		entity.kmkmtAgeementTimeEmploymentPK.companyId = agreementTimeOfEmployment.getCompanyId();
		entity.kmkmtAgeementTimeEmploymentPK.employmentCategoryCode = agreementTimeOfEmployment
				.getEmploymentCategoryCode().v();
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		entity.kmkmtAgeementTimeEmploymentPK.basicSettingId = agreementTimeOfEmployment.getBasicSettingId();
//		entity.laborSystemAtr = agreementTimeOfEmployment.getLaborSystemAtr().value;
//		entity.upperMonth = agreementTimeOfEmployment.getUpperAgreementSetting().getUpperMonth().valueAsMinutes();
//		entity.upperMonthAverage = agreementTimeOfEmployment.getUpperAgreementSetting().getUpperMonthAverage().valueAsMinutes();

		return entity;
	}

	private static AgreementTimeOfEmployment toDomain(KmkmtAgeementTimeEmployment kmkmtAgeementTimeEmployment) {
		/** TODO: 36協定時間対応により、コメントアウトされた */
		return null;
//		AgreementTimeOfEmployment agreementTimeOfEmployment = AgreementTimeOfEmployment.createJavaType(
//				kmkmtAgeementTimeEmployment.kmkmtAgeementTimeEmploymentPK.companyId,
//				kmkmtAgeementTimeEmployment.kmkmtAgeementTimeEmploymentPK.basicSettingId,
//				kmkmtAgeementTimeEmployment.laborSystemAtr,
//				kmkmtAgeementTimeEmployment.kmkmtAgeementTimeEmploymentPK.employmentCategoryCode,
//				kmkmtAgeementTimeEmployment.upperMonth, kmkmtAgeementTimeEmployment.upperMonthAverage);
//
//		return agreementTimeOfEmployment;
	}
}
