package nts.uk.ctx.at.record.infra.repository.standardtime;

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
import nts.uk.shr.com.context.AppContexts;
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
			data.contractCD = AppContexts.user().contractCode();
			data.startingMonth = agreementOperationSetting.getStartingMonth().value;
			data.closingDate = agreementOperationSetting.getClosureDate().getClosureDay().v();
			data.appUseAtr = agreementOperationSetting.isSpecicalConditionApplicationUse();
			data.annualUnitAtr = agreementOperationSetting.isYearSpecicalConditionApplicationUse();
			data.isLastDay = agreementOperationSetting.getClosureDate().getLastDayOfMonth();
			this.commandProxy().update(data);
		}
	}

	private static AgreementOperationSetting toDomain(KmkmtAgeementOperationSetting entity) {

		return new AgreementOperationSetting(
				entity.kmkmtAgeementOperationSettingPK.companyId,
				EnumAdaptor.valueOf(entity.startingMonth, StartingMonthType.class),
				new ClosureDate(entity.closingDate,entity.isLastDay),
				entity.appUseAtr,
				entity.annualUnitAtr);
	}

	private KmkmtAgeementOperationSetting toEntity(AgreementOperationSetting agreementOperationSetting) {
		val entity = new KmkmtAgeementOperationSetting();

		entity.kmkmtAgeementOperationSettingPK = new KmkmtAgeementOperationSettingPK();
		entity.kmkmtAgeementOperationSettingPK.companyId = agreementOperationSetting.getCompanyId();
		entity.contractCD =  AppContexts.user().contractCode();
		entity.startingMonth = agreementOperationSetting.getStartingMonth().value;
		entity.closingDate = agreementOperationSetting.getClosureDate().getClosureDay().v();
		entity.appUseAtr = agreementOperationSetting.isSpecicalConditionApplicationUse();
		entity.annualUnitAtr = agreementOperationSetting.isYearSpecicalConditionApplicationUse();
		entity.isLastDay = agreementOperationSetting.getClosureDate().getLastDayOfMonth();

		return entity;
	}

}
