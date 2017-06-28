package nts.uk.ctx.at.record.app.find.standardtime.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 
 * @author nampt
 *
 */
@Data
public class AgreementMonthSettingDto {

	private int yearMonthValue;

	private BigDecimal errorOneMonth;

	private BigDecimal alarmOneMonth;
}
