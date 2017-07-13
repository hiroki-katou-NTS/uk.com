package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.i18n.custom.IInternationalization;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementOperationSettingDetailDto;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementOperationSettingDto;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateType;
import nts.uk.ctx.at.record.dom.standardtime.enums.TimeOverLimitType;
import nts.uk.ctx.at.record.dom.standardtime.enums.StartingMonthType;
import nts.uk.ctx.at.record.dom.standardtime.enums.TargetSettingAtr;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 運用 screen
 */
@Stateless
public class AgreementOperationSettingFinder {
	
	@Inject IInternationalization internationalization;

	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;

	public AgreementOperationSettingDto find() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AgreementOperationSettingDto agreementOperationSettingDto = new AgreementOperationSettingDto();

		Optional<AgreementOperationSetting> agreementOperationSetting = this.agreementOperationSettingRepository
				.find(companyId);

		List<EnumConstant> startingMonthEnum = EnumAdaptor.convertToValueNameList(StartingMonthType.class, internationalization);
		List<EnumConstant> numberTimesOverLimitTypeEnum = EnumAdaptor
				.convertToValueNameList(TimeOverLimitType.class, internationalization);
		List<EnumConstant> closingDateTypeEnum = EnumAdaptor.convertToValueNameList(ClosingDateType.class, internationalization);
		List<EnumConstant> closingDateAtrEnum = EnumAdaptor.convertToValueNameList(ClosingDateAtr.class, internationalization);
		List<EnumConstant> yearlyWorkTableAtrEnum = EnumAdaptor.convertToValueNameList(TargetSettingAtr.class, internationalization);
		List<EnumConstant> alarmListAtrEnum = EnumAdaptor.convertToValueNameList(TargetSettingAtr.class, internationalization);

		agreementOperationSettingDto.setAlarmListAtrEnum(alarmListAtrEnum);
		agreementOperationSettingDto.setClosingDateAtrEnum(closingDateAtrEnum);
		agreementOperationSettingDto.setNumberTimesOverLimitTypeEnum(numberTimesOverLimitTypeEnum);
		agreementOperationSettingDto.setClosingDateTypeEnum(closingDateTypeEnum);
		agreementOperationSettingDto.setStartingMonthEnum(startingMonthEnum);
		agreementOperationSettingDto.setYearlyWorkTableAtrEnum(yearlyWorkTableAtrEnum);
		if (agreementOperationSetting.isPresent()) {
			AgreementOperationSettingDetailDto agreementOperationSettingDetailDto = new AgreementOperationSettingDetailDto();
			agreementOperationSettingDetailDto.setAlarmListAtr(agreementOperationSetting.get().getAlarmListAtr().value);
			agreementOperationSettingDetailDto.setClosingDateAtr(agreementOperationSetting.get().getClosingDateAtr().value);
			agreementOperationSettingDetailDto.setClosingDateType(agreementOperationSetting.get().getClosingDateType().value);
			agreementOperationSettingDetailDto
					.setNumberTimesOverLimitType(agreementOperationSetting.get().getNumberTimesOverLimitType().value);
			agreementOperationSettingDetailDto.setStartingMonth(agreementOperationSetting.get().getStartingMonth().value);
			agreementOperationSettingDetailDto
					.setYearlyWorkTableAtr(agreementOperationSetting.get().getYearlyWorkTableAtr().value);
			agreementOperationSettingDto.setAgreementOperationSettingDetailDto(agreementOperationSettingDetailDto);
		}

		return agreementOperationSettingDto;
	}
}
