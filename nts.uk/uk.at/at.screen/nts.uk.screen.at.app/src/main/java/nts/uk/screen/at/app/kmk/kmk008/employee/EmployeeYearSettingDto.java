package nts.uk.screen.at.app.kmk.kmk008.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class EmployeeYearSettingDto {

    private String employeeId;

    private List<AgreementYearSettingDto> settingDtos;

    public static List<EmployeeYearSettingDto> setData(Map<String, List<AgreementYearSetting>> data){

        List<EmployeeYearSettingDto> employeeYearSettingDtos = new ArrayList<>();

        data.forEach((key, value) -> {
            List<AgreementYearSettingDto> settingDtos = new ArrayList<>();
            value.forEach(a -> {
                settingDtos.add(new AgreementYearSettingDto(
                        a.getYearValue().v(),
                        a.getOneYearTime().getError().v(),
                        a.getOneYearTime().getAlarm().v()
                ));
            });

            employeeYearSettingDtos.add(new EmployeeYearSettingDto(
                    key,
                    settingDtos
            ));
        });

        return employeeYearSettingDtos;
    }

}
