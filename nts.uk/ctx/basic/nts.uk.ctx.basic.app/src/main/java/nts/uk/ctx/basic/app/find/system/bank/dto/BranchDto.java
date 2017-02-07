package nts.uk.ctx.basic.app.find.system.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BranchDto {
	private String bankBrandCode;
	private String bankBrandName;
	private String bankBrandNameKana;
	private String memo;
}
