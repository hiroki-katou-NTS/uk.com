package nts.uk.screen.at.app.kmk.kmk008.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementYearSettingDto {

    /** 年度 */
    private int yearValue;

    /** １年間時間 */
    private int errorOneYear;

    private int alarmOneYear;

    public static AgreementYearSettingDto setData(Optional<AgreementYearSetting> data){

        return data.map(setting -> new AgreementYearSettingDto(
                setting.getYearValue().v(),
                setting.getOneYearTime().getError().v(),
                setting.getOneYearTime().getAlarm().v()
        )).orElseGet(AgreementYearSettingDto::new);
    }
}
