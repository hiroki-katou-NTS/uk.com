package nts.uk.ctx.pr.transfer.app.find.bank;

import lombok.Value;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class BankBranchDto {

	private String id;
	private String code;
	private String name;
	private String bankCode;
	private String kanaName;
	private String memo;
	
}
