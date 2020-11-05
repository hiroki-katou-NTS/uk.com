package nts.uk.screen.at.app.kmk.kmk008.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementTimeOfCompanyDto {

    //B3_5
    private int overMaxTimes;

    //B3_13
    private int limitOneMonth;

    //B3_14
    private int errorOneMonth;

    //B3_15
    private int alarmOneMonth;

    //B3_18
    private int limitTwoMonths;

    //B3_19
    private int errorTwoMonths;

    //B3_20
    private int alarmTwoMonths;

    //B3_23
    private int errorOneYear;

    //B3_24
    private int alarmOneYear;

    //B3_27
    private int limitOneYear;

    //B3_28
    private int errorTwoYear;

    //B3_29
    private int alarmTwoYear;

    //B3_33,B3_34
    private int errorMonthAverage;

    //B3_35
    private int alarmMonthAverage;

    public static AgreementTimeOfCompanyDto setData(Optional<AgreementTimeOfCompany> data){
        if (!data.isPresent()){
            return new AgreementTimeOfCompanyDto();
        }
        return data.map(x -> new AgreementTimeOfCompanyDto(
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
        )).orElseGet(AgreementTimeOfCompanyDto::new);
    }
}
