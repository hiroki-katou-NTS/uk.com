package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementOperationSetting;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementOperationSettingPK;

@Stateless
public class JpaAgreementOperationSettingRepository extends JpaRepository
		implements AgreementOperationSettingRepository {

	private static final String FIND;

	private static final String UPDATE_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgeementOperationSetting a ");
		builderString.append("WHERE a.kmkmtAgeementOperationSettingPK.companyId = :companyId ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KmkmtAgeementOperationSetting a ");
		builderString
				.append("SET a.startingMonthType = :startingMonthType , a.numberTimesOverLimitType = :numberTimesOverLimitType, "
						+ " a.closingDateType = :closingDateType, a.closingDateAtr = :closingDateAtr, "
						+ " a.yearlyWorkTableAtr = :yearlyWorkTableAtr, a.alarmListAtr = :alarmListAtr ");
		builderString.append("WHERE a.kmkmtAgeementOperationSettingPK.companyId = :companyId ");
		UPDATE_BY_KEY = builderString.toString();

	}

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
		this.getEntityManager().createQuery(UPDATE_BY_KEY)
				.setParameter("companyId", agreementOperationSetting.getCompanyId())
				.setParameter("startingMonthType", agreementOperationSetting.getStartingMonth().value)
				.setParameter("numberTimesOverLimitType", agreementOperationSetting.getNumberTimesOverLimitType().value)
				.setParameter("closingDateType", agreementOperationSetting.getClosingDateType().value)
				.setParameter("closingDateAtr", agreementOperationSetting.getClosingDateAtr().value)
				.setParameter("yearlyWorkTableAtr", agreementOperationSetting.getYearlyWorkTableAtr().value)
				.setParameter("alarmListAtr", agreementOperationSetting.getAlarmListAtr().value).executeUpdate();
	}

	private static AgreementOperationSetting toDomain(KmkmtAgeementOperationSetting kmkmtAgeementOperationSetting) {
		AgreementOperationSetting agreementOperationSetting = AgreementOperationSetting.createFromJavaType(
				kmkmtAgeementOperationSetting.kmkmtAgeementOperationSettingPK.companyId,
				kmkmtAgeementOperationSetting.startingMonthType.intValue(),
				kmkmtAgeementOperationSetting.numberTimesOverLimitType.intValue(),
				kmkmtAgeementOperationSetting.closingDateType.intValue(),
				kmkmtAgeementOperationSetting.closingDateAtr.intValue(),
				kmkmtAgeementOperationSetting.yearlyWorkTableAtr.intValue(),
				kmkmtAgeementOperationSetting.alarmListAtr.intValue());

		return agreementOperationSetting;
	}

	private KmkmtAgeementOperationSetting toEntity(AgreementOperationSetting agreementOperationSetting) {
		val entity = new KmkmtAgeementOperationSetting();

		entity.kmkmtAgeementOperationSettingPK = new KmkmtAgeementOperationSettingPK();
		entity.kmkmtAgeementOperationSettingPK.companyId = agreementOperationSetting.getCompanyId();
		entity.alarmListAtr = new BigDecimal(agreementOperationSetting.getAlarmListAtr().value);
		entity.closingDateAtr = new BigDecimal(agreementOperationSetting.getClosingDateAtr().value);
		entity.closingDateType = new BigDecimal(agreementOperationSetting.getClosingDateType().value);
		entity.numberTimesOverLimitType = new BigDecimal(agreementOperationSetting.getNumberTimesOverLimitType().value);
		entity.startingMonthType = new BigDecimal(agreementOperationSetting.getStartingMonth().value);
		entity.yearlyWorkTableAtr = new BigDecimal(agreementOperationSetting.getYearlyWorkTableAtr().value);

		return entity;
	}

}
