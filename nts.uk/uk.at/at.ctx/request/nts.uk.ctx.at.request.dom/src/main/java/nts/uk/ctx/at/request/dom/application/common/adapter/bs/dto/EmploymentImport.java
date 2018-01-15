package nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmploymentImport {
	private String companyId;
	private Integer workClosureId;
	private Integer salaryClosureId;
	private String employmentCode;
	private String employmentName;
}
