package nts.uk.file.pr.app.export.banktransfer.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchDto {
	private String branchId;
	private String branchName;
	private String branchNameKana;
	private String branchCode;
	private String bankCode;
	private String bankName;
	private String bankNameKana;
	
	public BranchDto(String branchId, String branchName, String branchNameKana, String branchCode, String bankCode, String bankName, String bankNameKana) {
		super();
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchNameKana = branchNameKana;
		this.branchCode = branchCode;
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.bankNameKana = bankNameKana;
	}

	public BranchDto(String branchName, String branchCode, String bankCode) {
		super();
		this.branchName = branchName;
		this.branchCode = branchCode;
		this.bankCode = bankCode;
	}
	
	
}
