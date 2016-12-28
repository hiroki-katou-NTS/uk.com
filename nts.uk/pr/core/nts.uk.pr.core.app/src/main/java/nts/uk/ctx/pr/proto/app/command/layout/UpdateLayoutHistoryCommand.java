package nts.uk.ctx.pr.core.app.command.layout;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.layout.LayoutMaster;
import nts.uk.shr.com.context.AppContexts;

@Getter
public class UpdateLayoutHistoryCommand {
	
	private int startYm;
	//Giá trị startYm trước khi nó được sửa
	private int startYmOriginal;
	private String stmtCode;
	private String historyId;
	public LayoutMaster toDomain(int endYm, int layoutAtr, String stmtName){
		return LayoutMaster.createFromJavaType(
				AppContexts.user().companyCode(), this.startYm, this.stmtCode, 
				endYm, layoutAtr, stmtName, historyId);
	}
}
