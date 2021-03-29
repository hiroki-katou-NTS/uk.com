package nts.uk.screen.at.app.kmk.kmk008.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class EmployeeYearSettingDto {

    private String employeeId;

    private List<AgreementYearSettingDto> settingDtos;

    public static List<EmployeeYearSettingDto> setData(Map<String, List<AgreementYearSetting>> data,List<String> employeeIds){

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

        List<String> sidsInDb = employeeYearSettingDtos.stream().map(x -> x.employeeId).collect(Collectors.toList());

        List<String> sidsNew = employeeIds.stream().filter(x -> !sidsInDb.contains(x)).collect(Collectors.toList());

        sidsNew.forEach(x -> {
            employeeYearSettingDtos.add(new EmployeeYearSettingDto(x, Arrays.asList()));
        });

        return employeeYearSettingDtos;
    }

}
