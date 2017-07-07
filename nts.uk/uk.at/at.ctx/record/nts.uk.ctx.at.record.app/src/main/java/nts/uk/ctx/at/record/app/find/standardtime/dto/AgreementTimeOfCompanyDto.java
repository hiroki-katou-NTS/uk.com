package nts.uk.ctx.at.record.app.find.standardtime.dto;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;

@Value
public class AgreementTimeOfCompanyDto {

	private Long alarmWeek;

	private Long errorWeek;

	private Long limitWeek;

	private Long alarmTwoWeeks;

	private Long errorTwoWeeks;

	private Long limitTwoWeeks;

	private Long alarmFourWeeks;

	private Long errorFourWeeks;

	private Long limitFourWeeks;

	private Long alarmOneMonth;

	private Long errorOneMonth;

	private Long limitOneMonth;

	private Long alarmTwoMonths;

	private Long errorTwoMonths;

	private Long limitTwoMonths;

	private Long alarmThreeMonths;

	private Long errorThreeMonths;

	private Long limitThreeMonths;

	private Long alarmOneYear;

	private Long errorOneYear;

	private Long limitOneYear;

	public static AgreementTimeOfCompanyDto toDomain(BasicAgreementSetting basicAgreementSetting) {
		return new AgreementTimeOfCompanyDto(basicAgreementSetting.getAlarmWeek().v(), basicAgreementSetting.getErrorWeek().v(),
				basicAgreementSetting.getLimitWeek().v(), basicAgreementSetting.getAlarmTwoWeeks().v(), basicAgreementSetting.getErrorTwoWeeks().v(),
				basicAgreementSetting.getLimitTwoWeeks().v(), basicAgreementSetting.getAlarmFourWeeks().v(), basicAgreementSetting.getErrorFourWeeks().v(),
				basicAgreementSetting.getLimitFourWeeks().v(), basicAgreementSetting.getAlarmOneMonth().v(), basicAgreementSetting.getErrorOneMonth().v(),
				basicAgreementSetting.getLimitOneMonth().v(), basicAgreementSetting.getAlarmTwoMonths().v(), basicAgreementSetting.getErrorTwoMonths().v(),
				basicAgreementSetting.getLimitTwoMonths().v(), basicAgreementSetting.getAlarmThreeMonths().v(), basicAgreementSetting.getErrorThreeMonths().v(),
				basicAgreementSetting.getLimitThreeMonths().v(), basicAgreementSetting.getAlarmOneYear().v(), basicAgreementSetting.getErrorOneYear().v(),
				basicAgreementSetting.getLimitOneYear().v());
	}
}
