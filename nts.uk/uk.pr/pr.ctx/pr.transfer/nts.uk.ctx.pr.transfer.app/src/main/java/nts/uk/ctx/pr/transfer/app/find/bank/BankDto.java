package nts.uk.ctx.pr.transfer.app.find.bank;

import lombok.Value;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class BankDto {
	
	private String code;
	private String name;
	private String kanaName;
	private String memo;

}
