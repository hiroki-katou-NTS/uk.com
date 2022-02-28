package nts.uk.screen.com.app.smm.smm001.screenquery;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkedPaymentConversionDto {
	private Integer paymentCode;
	
	private List<EmploymentAndLinkedMonthSettingDto> selectiveEmploymentCodes;
}
