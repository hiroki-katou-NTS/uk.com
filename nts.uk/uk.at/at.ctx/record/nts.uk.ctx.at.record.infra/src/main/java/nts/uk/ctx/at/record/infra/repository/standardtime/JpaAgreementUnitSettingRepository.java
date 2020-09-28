package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementUnitSetting;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementUnitSettingPK;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;

@Stateless
public class JpaAgreementUnitSettingRepository extends JpaRepository implements AgreementUnitSettingRepository {

	private static final String FIND;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgeementUnitSetting a ");
		builderString.append("WHERE a.kmkmtAgeementUnitSettingPK.companyId = :companyId ");
		FIND = builderString.toString();
		
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
		
		Optional<KmkmtAgeementUnitSetting> entity = this.queryProxy().query(FIND, KmkmtAgeementUnitSetting.class).setParameter("companyId", agreementUnitSetting.getCompanyId()).getSingle();
		
		if (entity.isPresent()) {
			KmkmtAgeementUnitSetting data = entity.get();
			
			data.classificationUseAtr = new BigDecimal(agreementUnitSetting.getClassificationUseAtr().value);
			data.employmentUseAtr = new BigDecimal(agreementUnitSetting.getEmploymentUseAtr().value);
			data.workPlaceUseAtr = new BigDecimal(agreementUnitSetting.getWorkPlaceUseAtr().value);
			
			this.commandProxy().update(data);
		}
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
