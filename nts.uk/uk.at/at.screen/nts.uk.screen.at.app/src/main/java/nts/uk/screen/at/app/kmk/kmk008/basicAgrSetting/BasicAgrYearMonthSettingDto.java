package nts.uk.screen.at.app.kmk.kmk008.basicAgrSetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicAgrYearMonthSettingDto {

    //K6_5
    //３６協定基本設定.３６協定1ヶ月.特例条項による上限.上限時間
    private int oneMonthUpperLimit;

    public static BasicAgrYearMonthSettingDto setData(BasicAgreementSetting data){
        if (data == null){
            return new BasicAgrYearMonthSettingDto();
        }
        return new BasicAgrYearMonthSettingDto(data.getOneMonth().getSpecConditionLimit().getUpperLimit().v());
    }
}
