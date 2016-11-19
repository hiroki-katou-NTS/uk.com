package nts.uk.ctx.pr.proto.app.command.layout.register;

import lombok.Getter;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.shr.com.context.AppContexts;

@Getter
public class LayoutCommand {

	private String stmtCode;
	private int startYm;
	private String stmtName;
	private int endYm;
	
	/**
	 * Convert to domain object from command values
	 * @return
	 */
	public LayoutMaster toDomain(int layoutAtr){
		return LayoutMaster.createFromJavaType(AppContexts.user().companyCode(), 
					this.startYm, this.stmtCode, this.endYm, layoutAtr, this.stmtName);
	}
}
