package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementUnitSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementOperationSetting;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementOperationSettingPK;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementUnitSetting;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementUnitSettingPK;

@Stateless
public class JpaAgreementUnitSettingRepository extends JpaRepository implements AgreementUnitSettingRepository {

	private static final String FIND;

	private static final String UPDATE_BY_KEY;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgeementUnitSetting a ");
		builderString.append("WHERE a.kmkmtAgeementUnitSettingPK.companyId = :companyId ");
		FIND = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("UPDATE KmkmtAgeementUnitSetting a ");
		builderString.append("SET a.employmentUseAtr = :employmentUseAtr , a.workPlaceUseAtr = :workPlaceUseAtr, "
				+ " a.classificationUseAtr = :classificationUseAtr ");
		builderString.append("WHERE a.kmkmtAgeementUnitSettingPK.companyId = :companyId ");
		UPDATE_BY_KEY = builderString.toString();
	}

	@Override
	public Optional<AgreementUnitSetting> find(String companyId) {
		return this.queryProxy().query(FIND, KmkmtAgeementUnitSetting.class).setParameter("companyId", companyId)
				.getSingle(f -> toDomain(f));
	}

	@Override
	public void add(AgreementUnitSetting agreementUnitSetting) {
		this.commandProxy().insert(toEntity(agreementUnitSetting));
	}

	@Override
	public void update(AgreementUnitSetting agreementUnitSetting) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY)
				.setParameter("companyId", agreementUnitSetting.getCompanyId())
				.setParameter("employmentUseAtr", agreementUnitSetting.getEmploymentUseAtr().value)
				.setParameter("workPlaceUseAtr", agreementUnitSetting.getWorkPlaceUseAtr().value)
				.setParameter("classificationUseAtr", agreementUnitSetting.getClassificationUseAtr().value).executeUpdate();
	}

	private static AgreementUnitSetting toDomain(KmkmtAgeementUnitSetting kmkmtAgeementUnitSetting) {
		AgreementUnitSetting agreementUnitSetting = AgreementUnitSetting.createFromJavaType(
				kmkmtAgeementUnitSetting.kmkmtAgeementUnitSettingPK.companyId,
				kmkmtAgeementUnitSetting.classificationUseAtr.intValue(),
				kmkmtAgeementUnitSetting.employmentUseAtr.intValue(),
				kmkmtAgeementUnitSetting.workPlaceUseAtr.intValue());

		return agreementUnitSetting;
	}
	
	private KmkmtAgeementUnitSetting toEntity(AgreementUnitSetting agreementUnitSetting){
		val entity = new KmkmtAgeementUnitSetting();
		
		entity.kmkmtAgeementUnitSettingPK = new KmkmtAgeementUnitSettingPK();
		entity.kmkmtAgeementUnitSettingPK.companyId = agreementUnitSetting.getCompanyId();
		entity.classificationUseAtr = new BigDecimal(agreementUnitSetting.getClassificationUseAtr().value);
		entity.employmentUseAtr = new BigDecimal(agreementUnitSetting.getEmploymentUseAtr().value);
		entity.workPlaceUseAtr = new BigDecimal(agreementUnitSetting.getWorkPlaceUseAtr().value);
		
		return entity;
	}
}
