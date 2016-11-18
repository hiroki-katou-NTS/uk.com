package nts.uk.ctx.pr.proto.app.layout.command;

import lombok.Getter;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.shr.com.context.AppContexts;

@Getter
public class UpdateLayoutHistoryCommand {
	
	private int startYM;
	private String stmtCode;

	public LayoutMaster toDomain(int endYm, int layoutAtr, String stmtName){
		return LayoutMaster.createFromJavaType(
				AppContexts.user().companyCode(), this.startYM, this.stmtCode, endYm, layoutAtr, stmtName);
	}
}
