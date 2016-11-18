package nts.uk.ctx.pr.proto.app.layout.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteLayoutHistoryCommand {
	
	private String companyCode;
	
	private String stmtCode;
	
	private int startYM;
		
	private int layoutAtr;
	
	private String stmtName;
	
}
