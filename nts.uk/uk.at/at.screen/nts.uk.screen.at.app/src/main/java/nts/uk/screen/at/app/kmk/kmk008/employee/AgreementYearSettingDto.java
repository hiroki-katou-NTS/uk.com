package nts.uk.screen.at.app.kmk.kmk008.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementYearSettingDto {

    /** 年度 */
    private Year yearValue;

    /** １年間時間 */
    private OneYearErrorAlarmTime oneYearTime;

    public static AgreementYearSettingDto setData(Optional<AgreementYearSetting> data){

        return data.map(setting -> new AgreementYearSettingDto(
                setting.getYearValue(),
                setting.getOneYearTime()
        )).orElseGet(AgreementYearSettingDto::new);
    }
}
