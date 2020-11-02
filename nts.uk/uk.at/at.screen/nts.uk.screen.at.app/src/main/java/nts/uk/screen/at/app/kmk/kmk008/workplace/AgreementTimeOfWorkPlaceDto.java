package nts.uk.screen.at.app.kmk.kmk008.workplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementTimeOfWorkPlaceDto {

    // 	３６協定基本設定
    //D4_5
    private int overMaxTimes;

    //D4_13
    private int limitOneMonth;

    //D4_14
    private int errorOneMonth;

    //D4_15
    private int alarmOneMonth;

    //D4_18
    private int limitTwoMonths;

    //D4_19
    private int errorTwoMonths;

    //D4_20
    private int alarmTwoMonths;

    //D4_23
    private int errorOneYear;

    //D4_24
    private int alarmOneYear;

    //D4_27
    private int limitOneYear;

    //D4_28
    private int errorTwoYear;

    //D4_29
    private int alarmTwoYear;

    //D4_33,D3_34
    private int errorMonthAverage;

    //D4_35
    private int alarmMonthAverage;

    public static AgreementTimeOfWorkPlaceDto setData(Optional<AgreementTimeOfWorkPlace> data){
        if (!data.isPresent()){
            return new AgreementTimeOfWorkPlaceDto();
        }
        return data.map(x -> new AgreementTimeOfWorkPlaceDto(
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
        )).orElseGet(AgreementTimeOfWorkPlaceDto::new);
    }
}
