package nts.uk.file.pr.app.export.banktransfer.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class BankTransferParamRpDto {
	private String companyCode;
	private String fromBranchId;
	private int payBonusAtr;
	private int processNo;
	private int processingYM;
	private GeneralDate payDate;
	private int sparePayAtr;
	private Integer orderBy;
	
	public BankTransferParamRpDto(String companyCode, String fromBranchId, int payBonusAtr, int processNo,
			int processingYM, GeneralDate payDate, int sparePayAtr) {
		super();
		this.companyCode = companyCode;
		this.fromBranchId = fromBranchId;
		this.payBonusAtr = payBonusAtr;
		this.processNo = processNo;
		this.processingYM = processingYM;
		this.payDate = payDate;
		this.sparePayAtr = sparePayAtr;
	}
	
	
}
