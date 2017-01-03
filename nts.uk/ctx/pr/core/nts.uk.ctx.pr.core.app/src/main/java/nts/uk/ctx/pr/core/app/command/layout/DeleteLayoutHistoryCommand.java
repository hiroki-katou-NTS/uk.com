package nts.uk.ctx.pr.core.app.command.layout;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.layout.LayoutMaster;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
public class DeleteLayoutHistoryCommand {
	
	private String companyCode;
	
	private String stmtCode;
	
	private int startYm;
		
	private int layoutAtr;
	
	private String stmtName;
	
	private int endYm;
	
	private String historyId;
	public LayoutMaster toDomain(int endYm, int layoutAtr, String stmtName){
		return LayoutMaster.createFromJavaType(
				AppContexts.user().companyCode(), 
				this.startYm, 
				this.stmtCode, 
				endYm, 
				layoutAtr, 
				stmtName,
				this.historyId);
	}
}
