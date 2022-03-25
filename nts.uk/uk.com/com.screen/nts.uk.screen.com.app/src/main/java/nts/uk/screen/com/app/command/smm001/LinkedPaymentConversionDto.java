package nts.uk.screen.com.app.command.smm001;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkedPaymentConversionDto {
	private Integer paymentCode;
	
	private List<EmploymentAndLinkedMonthSettingDto> employmentAndLinkedMonthSettingDtos;
}
