package nts.uk.ctx.at.record.app.find.standardtime.dto;

import lombok.Value;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;

@Value
public class AgreementTimeOfCompanyDto {

	private int alarmWeek;

	private int errorWeek;

	private int limitWeek;

	private int alarmTwoWeeks;

	private int errorTwoWeeks;

	private int limitTwoWeeks;

	private int alarmFourWeeks;

	private int errorFourWeeks;

	private int limitFourWeeks;

	private int alarmOneMonth;

	private int errorOneMonth;

	private int limitOneMonth;

	private int alarmTwoMonths;

	private int errorTwoMonths;

	private int limitTwoMonths;

	private int alarmThreeMonths;

	private int errorThreeMonths;

	private int limitThreeMonths;

	private int alarmOneYear;

	private int errorOneYear;

	private int limitOneYear;

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
