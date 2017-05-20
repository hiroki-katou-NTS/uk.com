package nts.uk.file.pr.app.export.banktransfer.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDto {
	private String bankName;
	private String bankCode;
	private String bankNameKana;
	
	public BankDto(String bankName, String bankCode) {
		this.bankName = bankName;
		this.bankCode = bankCode;
	}
}
