package nts.uk.file.pr.app.export.banktransfer.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchDto {
	private String branchId;
	private String branchName;
	private String branchCode;
	private String bankCode;
	private String bankName;
	
	public BranchDto(String branchId, String branchName, String branchCode, String bankCode, String bankName) {
		super();
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchCode = branchCode;
		this.bankCode = bankCode;
		this.bankName = bankName;
	}

	public BranchDto(String branchName, String branchCode, String bankCode) {
		super();
		this.branchName = branchName;
		this.branchCode = branchCode;
		this.bankCode = bankCode;
	}
	
	
}
