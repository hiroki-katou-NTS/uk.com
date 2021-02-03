package nts.uk.screen.at.app.kmk.kmk008.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class AgreementMonthSettingDto {

    /** 年月 */
    private int yearMonthValue;

    /** １ヶ月時間 */
    private int errorOneMonth;

    private int alarmOneMonth;

    public static List<AgreementMonthSettingDto> setData(List<AgreementMonthSetting> data){

        List<AgreementMonthSettingDto> agreementMonthSettingDtos = new ArrayList<>();

        data.forEach(x -> {
            agreementMonthSettingDtos.add(new AgreementMonthSettingDto(
                    x.getYearMonthValue().v(),
                    x.getOneMonthTime().getError().v(),
                    x.getOneMonthTime().getAlarm().v()
            ));
        });

        return agreementMonthSettingDtos;
    }
}
