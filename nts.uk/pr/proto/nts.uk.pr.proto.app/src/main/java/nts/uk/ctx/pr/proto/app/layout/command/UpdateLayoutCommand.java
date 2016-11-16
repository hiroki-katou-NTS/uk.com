package nts.uk.ctx.pr.proto.app.layout.command;

import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.shr.com.context.AppContexts;

public class UpdateLayoutCommand {
	
	private String stmtCode;
	private int startYM;
	private String stmtName;
	private int endYM;
	
	/**
	 * Convert to domain object from command values
	 * @return
	 */
	public LayoutMaster toDomain(int layoutAtr){
		return LayoutMaster.createFromJavaType(AppContexts.user().companyCode(), 
					this.startYM, this.stmtCode, this.endYM, layoutAtr, this.stmtName);
	}
}
