package nts.uk.file.pr.app.export.banktransfer.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonBankAccountDto {
	private String accountHolderName1;
	private String accountHolderName2;
	private String accountHolderName3;
	private String accountHolderName4;
	private String accountHolderName5;
}
