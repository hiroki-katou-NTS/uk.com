package nts.uk.screen.at.app.kmk.kmk008.classification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;


import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementTimeClassificationDto {

	// E4_5
	private int overMaxTimes;

	// E4_13
	private int limitOneMonth;

	// E4_14
	private int errorOneMonth;

	// E4_15
	private int alarmOneMonth;

	// E4_18
	private int limitTwoMonths;

	// E4_19
	private int errorTwoMonths;

	// E4_20
	private int alarmTwoMonths;

	// E4_23
	private int errorOneYear;

	// E4_24
	private int alarmOneYear;

	// E4_27
	private int limitOneYear;

	// E4_28
	private int errorTwoYear;

	// E4_29
	private int alarmTwoYear;

	// E4_33,D3_34
	private int errorMonthAverage;

	// E4_35
	private int alarmMonthAverage;

    public static AgreementTimeClassificationDto setData(Optional<AgreementTimeOfClassification> data){
        if (!data.isPresent()){
            return new AgreementTimeClassificationDto();
        }
        return data.map(x -> new AgreementTimeClassificationDto(
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
        )).orElseGet(AgreementTimeClassificationDto::new);
    }
}
