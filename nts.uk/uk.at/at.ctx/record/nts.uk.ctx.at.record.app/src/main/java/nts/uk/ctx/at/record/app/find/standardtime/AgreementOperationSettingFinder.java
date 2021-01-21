package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementOperationSettingDetailDto;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementOperationSettingDto;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.ClosingDateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.ClosingDateType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.StartingMonthType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.TargetSettingAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOverMaxTimes;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * 
 * @author nampt 運用 screen
 */
@Stateless
public class AgreementOperationSettingFinder {
	
	@Inject I18NResourcesForUK ukResource;

	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;

	public AgreementOperationSettingDto find() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AgreementOperationSettingDto agreementOperationSettingDto = new AgreementOperationSettingDto();

		Optional<AgreementOperationSetting> agreementOperationSetting = this.agreementOperationSettingRepository
				.find(companyId);

		List<EnumConstant> startingMonthEnum = EnumAdaptor.convertToValueNameList(StartingMonthType.class, ukResource);
		List<EnumConstant> numberTimesOverLimitTypeEnum = EnumAdaptor
				.convertToValueNameList(AgreementOverMaxTimes.class, ukResource);
		List<EnumConstant> closingDateTypeEnum = EnumAdaptor.convertToValueNameList(ClosingDateType.class, ukResource);
		List<EnumConstant> closingDateAtrEnum = EnumAdaptor.convertToValueNameList(ClosingDateAtr.class, ukResource);
		List<EnumConstant> yearlyWorkTableAtrEnum = EnumAdaptor.convertToValueNameList(TargetSettingAtr.class, ukResource);
		List<EnumConstant> alarmListAtrEnum = EnumAdaptor.convertToValueNameList(TargetSettingAtr.class, ukResource);

		agreementOperationSettingDto.setAlarmListAtrEnum(alarmListAtrEnum);
		agreementOperationSettingDto.setClosingDateAtrEnum(closingDateAtrEnum);
		agreementOperationSettingDto.setNumberTimesOverLimitTypeEnum(numberTimesOverLimitTypeEnum);
		agreementOperationSettingDto.setClosingDateTypeEnum(closingDateTypeEnum);
		agreementOperationSettingDto.setStartingMonthEnum(startingMonthEnum);
		agreementOperationSettingDto.setYearlyWorkTableAtrEnum(yearlyWorkTableAtrEnum);
		if (agreementOperationSetting.isPresent()) {
			AgreementOperationSettingDetailDto agreementOperationSettingDetailDto = new AgreementOperationSettingDetailDto();
			/** TODO: 36協定時間対応により、コメントアウトされた */
//			agreementOperationSettingDetailDto.setAlarmListAtr(agreementOperationSetting.get().getAlarmListAtr().value);
//			agreementOperationSettingDetailDto.setClosingDateAtr(agreementOperationSetting.get().getClosingDateAtr().value);
//			agreementOperationSettingDetailDto.setClosingDateType(agreementOperationSetting.get().getClosingDateType().value);
//			agreementOperationSettingDetailDto
//					.setNumberTimesOverLimitType(agreementOperationSetting.get().getNumberTimesOverLimitType().value);
//			agreementOperationSettingDetailDto.setStartingMonth(agreementOperationSetting.get().getStartingMonth().value);
//			agreementOperationSettingDetailDto
//					.setYearlyWorkTableAtr(agreementOperationSetting.get().getYearlyWorkTableAtr().value);
//			agreementOperationSettingDto.setAgreementOperationSettingDetailDto(agreementOperationSettingDetailDto);
		}

		return agreementOperationSettingDto;
	}
}
