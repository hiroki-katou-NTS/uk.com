package nts.uk.screen.at.app.kmk.kmk008.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class EmployeeMonthSettingDto {

    private String employeeId;

    private List<AgreementMonthSettingDto> settingDtos;

    public static List<EmployeeMonthSettingDto> setData(Map<String, List<AgreementMonthSetting>> data,List<String> employeeIds){

        List<EmployeeMonthSettingDto> employeeMonthSettingDtos = new ArrayList<>();

        data.forEach((key, value) -> {
            List<AgreementMonthSettingDto> settingDtos = new ArrayList<>();
            value.forEach(a -> {
                settingDtos.add(new AgreementMonthSettingDto(
                        a.getYearMonthValue().v(),
                        a.getOneMonthTime().getError().v(),
                        a.getOneMonthTime().getAlarm().v()
                ));
            });

            employeeMonthSettingDtos.add(new EmployeeMonthSettingDto(
                    key,
                    settingDtos
            ));
        });

        List<String> sidsInDb = employeeMonthSettingDtos.stream().map(x -> x.employeeId).collect(Collectors.toList());

        List<String> sidsNew = employeeIds.stream().filter(x -> !sidsInDb.contains(x)).collect(Collectors.toList());

        sidsNew.forEach(x -> {
            employeeMonthSettingDtos.add(new EmployeeMonthSettingDto(x, Arrays.asList()));
        });

        return employeeMonthSettingDtos;
    }

}
