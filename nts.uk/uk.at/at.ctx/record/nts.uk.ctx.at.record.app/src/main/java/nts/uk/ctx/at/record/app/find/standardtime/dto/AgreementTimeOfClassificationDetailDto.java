package nts.uk.ctx.at.record.app.find.standardtime.dto;

import java.math.BigDecimal;

import lombok.Data;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;

@Data
public class AgreementTimeOfClassificationDetailDto {
	private BigDecimal alarmWeek;

	private BigDecimal errorWeek;

	private BigDecimal limitWeek;

	private BigDecimal alarmTwoWeeks;

	private BigDecimal errorTwoWeeks;

	private BigDecimal limitTwoWeeks;

	private BigDecimal alarmFourWeeks;

	private BigDecimal errorFourWeeks;

	private BigDecimal limitFourWeeks;

	private BigDecimal alarmOneMonth;

	private BigDecimal errorOneMonth;

	private BigDecimal limitOneMonth;

	private BigDecimal alarmTwoMonths;

	private BigDecimal errorTwoMonths;

	private BigDecimal limitTwoMonths;

	private BigDecimal alarmThreeMonths;

	private BigDecimal errorThreeMonths;

	private BigDecimal limitThreeMonths;

	private BigDecimal alarmOneYear;

	private BigDecimal errorOneYear;

	private BigDecimal limitOneYear;

	public static AgreementTimeOfCompanyDto toDomain(BasicAgreementSetting basicAgreementSetting) {
		return new AgreementTimeOfCompanyDto(basicAgreementSetting.getAlarmWeek().v(),
				basicAgreementSetting.getErrorWeek().v(), basicAgreementSetting.getLimitWeek().v(),
				basicAgreementSetting.getAlarmTwoWeeks().v(), basicAgreementSetting.getErrorTwoWeeks().v(),
				basicAgreementSetting.getLimitTwoWeeks().v(), basicAgreementSetting.getAlarmFourWeeks().v(),
				basicAgreementSetting.getErrorFourWeeks().v(), basicAgreementSetting.getLimitFourWeeks().v(),
				basicAgreementSetting.getAlarmOneMonth().v(), basicAgreementSetting.getErrorOneMonth().v(),
				basicAgreementSetting.getLimitOneMonth().v(), basicAgreementSetting.getAlarmTwoMonths().v(),
				basicAgreementSetting.getErrorTwoMonths().v(), basicAgreementSetting.getLimitTwoMonths().v(),
				basicAgreementSetting.getAlarmThreeMonths().v(), basicAgreementSetting.getErrorThreeMonths().v(),
				basicAgreementSetting.getLimitThreeMonths().v(), basicAgreementSetting.getAlarmOneYear().v(),
				basicAgreementSetting.getErrorOneYear().v(), basicAgreementSetting.getLimitOneYear().v());
	}
}
