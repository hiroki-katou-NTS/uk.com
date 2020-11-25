package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementYearSettingDto;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;

/**
 * 
 * @author nampt 個人 screen (年度)
 */
@Stateless
public class AgreementYearSettingFinder {

	@Inject
	private AgreementYearSettingRepository agreementYearSettingRepository;

	public List<AgreementYearSettingDto> find(String employeeId) {
		List<AgreementYearSettingDto> agreementYearSettingDtos = new ArrayList<>();

		List<AgreementYearSetting> agreementYearSettings = agreementYearSettingRepository.find(employeeId);
		if (!agreementYearSettings.isEmpty()) {
			agreementYearSettings.stream().forEach(f -> {
				AgreementYearSettingDto agreementYearSettingDto = new AgreementYearSettingDto();

				agreementYearSettingDto.setYearValue(f.getYearValue().v());
				/** TODO: 36協定時間対応により、コメントアウトされた */
//				agreementYearSettingDto.setAlarmOneYear(f.getAlarmOneYear().v());
//				agreementYearSettingDto.setErrorOneYear(f.getErrorOneYear().v());
				agreementYearSettingDtos.add(agreementYearSettingDto);
			});
		} else {
			return null;
		}

		return agreementYearSettingDtos;
	}
}
