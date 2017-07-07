package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementOperationSettingDto;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateType;
import nts.uk.ctx.at.record.dom.standardtime.enums.NumberOfTimeOverLimitType;
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

	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;

	public AgreementOperationSettingDto find() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AgreementOperationSettingDto agreementOperationSettingDto = new AgreementOperationSettingDto();

		Optional<AgreementOperationSetting> agreementOperationSetting = this.agreementOperationSettingRepository
				.find(companyId);

		List<EnumConstant> startingMonthEnum = EnumAdaptor.convertToValueNameList(StartingMonthType.class);
		List<EnumConstant> numberTimesOverLimitTypeEnum = EnumAdaptor
				.convertToValueNameList(NumberOfTimeOverLimitType.class);
		List<EnumConstant> closingDateTypeEnum = EnumAdaptor.convertToValueNameList(ClosingDateType.class);
		List<EnumConstant> closingDateAtrEnum = EnumAdaptor.convertToValueNameList(ClosingDateAtr.class);
		List<EnumConstant> yearlyWorkTableAtrEnum = EnumAdaptor.convertToValueNameList(TargetSettingAtr.class);
		List<EnumConstant> alarmListAtrEnum = EnumAdaptor.convertToValueNameList(TargetSettingAtr.class);

		agreementOperationSettingDto.setAlarmListAtrEnum(alarmListAtrEnum);
		agreementOperationSettingDto.setClosingDateAtrEnum(closingDateAtrEnum);
		agreementOperationSettingDto.setNumberTimesOverLimitTypeEnum(numberTimesOverLimitTypeEnum);
		agreementOperationSettingDto.setClosingDateTypeEnum(closingDateTypeEnum);
		agreementOperationSettingDto.setStartingMonthEnum(startingMonthEnum);
		agreementOperationSettingDto.setYearlyWorkTableAtrEnum(yearlyWorkTableAtrEnum);
		if (agreementOperationSetting.isPresent()) {
			agreementOperationSettingDto.setAlarmListAtr(agreementOperationSetting.get().getAlarmListAtr().value);
			agreementOperationSettingDto.setClosingDateAtr(agreementOperationSetting.get().getClosingDateAtr().value);
			agreementOperationSettingDto.setClosingDateType(agreementOperationSetting.get().getClosingDateType().value);
			agreementOperationSettingDto
					.setNumberTimesOverLimitType(agreementOperationSetting.get().getNumberTimesOverLimitType().value);
			agreementOperationSettingDto.setStartingMonth(agreementOperationSetting.get().getStartingMonth().value);
			agreementOperationSettingDto
					.setYearlyWorkTableAtr(agreementOperationSetting.get().getYearlyWorkTableAtr().value);
		}

		return agreementOperationSettingDto;
	}
}
