package nts.uk.screen.at.app.kmk.kmk008.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class AgreementYearSettingDto {

    /** 年度 */
    private int yearValue;

    /** １年間時間 */
    private int errorOneYear;

    private int alarmOneYear;

    public static List<AgreementYearSettingDto> setData(List<AgreementYearSetting> data){

        List<AgreementYearSettingDto> agreementYearSettingDtos = new ArrayList<>();

        data.forEach(x -> {
            agreementYearSettingDtos.add(new AgreementYearSettingDto(
                    x.getYearValue().v(),
                    x.getOneYearTime().getError().v(),
                    x.getOneYearTime().getAlarm().v()
            ));
        });

        return agreementYearSettingDtos;
    }
}
