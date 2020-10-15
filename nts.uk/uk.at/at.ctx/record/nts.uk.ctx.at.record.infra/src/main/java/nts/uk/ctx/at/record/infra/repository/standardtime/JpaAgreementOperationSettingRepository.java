package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementOperationSetting;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementOperationSettingPK;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.StartingMonthType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class JpaAgreementOperationSettingRepository extends JpaRepository
		implements AgreementOperationSettingRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgeementOperationSetting a ");
		builderString.append("WHERE a.kmkmtAgeementOperationSettingPK.companyId = :companyId ");
		FIND = builderString.toString();

	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<AgreementOperationSetting> find(String companyId) {
		return this.queryProxy().query(FIND, KmkmtAgeementOperationSetting.class).setParameter("companyId", companyId)
				.getSingle(f -> toDomain(f));
	}

	@Override
	public void add(AgreementOperationSetting agreementOperationSetting) {
		this.commandProxy().insert(toEntity(agreementOperationSetting));
	}

	@Override
	public void update(AgreementOperationSetting agreementOperationSetting) {

		Optional<KmkmtAgeementOperationSetting> entity = this.queryProxy()
				.query(FIND, KmkmtAgeementOperationSetting.class)
				.setParameter("companyId", agreementOperationSetting.getCompanyId()).getSingle();
		
		if (entity.isPresent()) {
			KmkmtAgeementOperationSetting data = entity.get();
			/** TODO: 36協定時間対応により、コメントアウトされた */
//			data.alarmListAtr = new BigDecimal(agreementOperationSetting.getAlarmListAtr().value);
//			data.closingDateAtr = new BigDecimal(agreementOperationSetting.getClosingDateAtr().value);
//			data.closingDateType = new BigDecimal(agreementOperationSetting.getClosingDateType().value);
//			data.numberTimesOverLimitType = new BigDecimal(agreementOperationSetting.getNumberTimesOverLimitType().value);
			data.startingMonthType = new BigDecimal(agreementOperationSetting.getStartingMonth().value);
//			data.yearlyWorkTableAtr = new BigDecimal(agreementOperationSetting.getYearlyWorkTableAtr().value);
			
			this.commandProxy().update(data);
		}
	}

	private static AgreementOperationSetting toDomain(KmkmtAgeementOperationSetting entity) {
		
		AgreementOperationSetting agreementOperationSetting = new AgreementOperationSetting(
				entity.kmkmtAgeementOperationSettingPK.companyId, 
				EnumAdaptor.valueOf(entity.startingMonthType.intValue(), StartingMonthType.class),
				new ClosureDate(entity.closingDateType.intValue(), 
								entity.closingDateType.intValue() == 30), 
				false, false);

		return agreementOperationSetting;
	}

	private KmkmtAgeementOperationSetting toEntity(AgreementOperationSetting agreementOperationSetting) {
		val entity = new KmkmtAgeementOperationSetting();

		entity.kmkmtAgeementOperationSettingPK = new KmkmtAgeementOperationSettingPK();
		entity.kmkmtAgeementOperationSettingPK.companyId = agreementOperationSetting.getCompanyId();
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		entity.alarmListAtr = new BigDecimal(agreementOperationSetting.getAlarmListAtr().value);
//		entity.closingDateAtr = new BigDecimal(agreementOperationSetting.getClosingDateAtr().value);
//		entity.closingDateType = new BigDecimal(agreementOperationSetting.getClosingDateType().value);
//		entity.numberTimesOverLimitType = new BigDecimal(agreementOperationSetting.getNumberTimesOverLimitType().value);
		entity.startingMonthType = new BigDecimal(agreementOperationSetting.getStartingMonth().value);
//		entity.yearlyWorkTableAtr = new BigDecimal(agreementOperationSetting.getYearlyWorkTableAtr().value);

		return entity;
	}

}
