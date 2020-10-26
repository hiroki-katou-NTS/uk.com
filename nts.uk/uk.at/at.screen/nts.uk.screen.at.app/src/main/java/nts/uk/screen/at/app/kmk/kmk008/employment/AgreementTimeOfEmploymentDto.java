package nts.uk.screen.at.app.kmk.kmk008.employment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementTimeOfEmploymentDto {

    // 	３６協定基本設定
    //C4_5
    private int overMaxTimes;

    //C4_13
    private int limitOneMonth;

    //C4_14
    private int errorOneMonth;

    //C4_15
    private int alarmOneMonth;

    //C4_18
    private int limitTwoMonths;

    //C4_19
    private int errorTwoMonths;

    //C4_20
    private int alarmTwoMonths;

    //C4_23
    private int errorOneYear;

    //C4_24
    private int alarmOneYear;

    //C4_27
    private int limitOneYear;

    //C4_28
    private int errorTwoYear;

    //C4_29
    private int alarmTwoYear;

    //C4_33,C3_34
    private int errorMonthAverage;

    //C4_35
    private int alarmMonthAverage;

    public static AgreementTimeOfEmploymentDto setData(Optional<AgreementTimeOfEmployment> data){
        if (!data.isPresent()){
            return new AgreementTimeOfEmploymentDto();
        }
        return data.map(x -> new AgreementTimeOfEmploymentDto(
                x.getSetting().getOverMaxTimes().value,
                x.getSetting().getOneMonth().getBasic().getUpperLimit().v(),
                x.getSetting().getOneMonth().getBasic().getErAlTime().getError().v(),
                x.getSetting().getOneMonth().getBasic().getErAlTime().getAlarm().v(),
                x.getSetting().getOneMonth().getSpecConditionLimit().getUpperLimit().v(),
                x.getSetting().getOneMonth().getSpecConditionLimit().getErAlTime().getError().v(),
                x.getSetting().getOneMonth().getSpecConditionLimit().getErAlTime().getAlarm().v(),
                x.getSetting().getOneYear().getBasic().getError().v(),
                x.getSetting().getOneYear().getBasic().getAlarm().v(),
                x.getSetting().getOneYear().getSpecConditionLimit().getUpperLimit().v(),
                x.getSetting().getOneYear().getSpecConditionLimit().getErAlTime().getError().v(),
                x.getSetting().getOneYear().getSpecConditionLimit().getErAlTime().getAlarm().v(),
                x.getSetting().getMultiMonth().getMultiMonthAvg().getError().v(),
                x.getSetting().getMultiMonth().getMultiMonthAvg().getAlarm().v()
        )).orElseGet(AgreementTimeOfEmploymentDto::new);
    }
}
