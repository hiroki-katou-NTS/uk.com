package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementMonthSettingDto;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;

/**
 * 
 * @author nampt 個人 screen (年月)
 */
@Stateless
public class AgreementMonthSettingFinder {

	@Inject
	private AgreementMonthSettingRepository agreementMonthSettingRepository;

	public List<AgreementMonthSettingDto> find(String employeeId) {
		List<AgreementMonthSettingDto> agreementMonthSettingDtos = new ArrayList<>();

		List<AgreementMonthSetting> agreementMonthSettings = agreementMonthSettingRepository.find(employeeId);

		if (!agreementMonthSettings.isEmpty()) {
			agreementMonthSettings.stream().forEach(f -> {
				AgreementMonthSettingDto agreementMonthSettingDto = new AgreementMonthSettingDto();

				agreementMonthSettingDto.setYearMonthValue(f.getYearMonthValue().v());
				agreementMonthSettingDto.setAlarmOneMonth(f.getAlarmOneMonth().v());
				agreementMonthSettingDto.setErrorOneMonth(f.getErrorOneMonth().v());
				agreementMonthSettingDtos.add(agreementMonthSettingDto);
			});
		} else {
			return null;
		}

		return agreementMonthSettingDtos;
	}

}
