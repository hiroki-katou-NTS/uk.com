package nts.uk.ctx.at.record.app.find.standardtime.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AgreementYearSettingDto {

	private int yearValue;
	
	private Long errorOneYear;
	
	private Long alarmOneYear;
}
