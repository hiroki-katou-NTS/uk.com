package nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCommandHealth {
	
	List<CusHealthInsuDto> cusDataDtos;
	String historyId;
	String socialInsuranceCode;
}
