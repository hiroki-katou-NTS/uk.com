package nts.uk.ctx.command;

import lombok.Data;

@Data
public class DeleteCompanyInforCommand {
	// 会社ID
	private String companyId;
	// 会社コード
	private String companyCode;
}
