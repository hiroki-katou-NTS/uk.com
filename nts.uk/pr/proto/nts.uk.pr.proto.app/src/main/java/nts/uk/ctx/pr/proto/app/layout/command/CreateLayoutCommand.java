package nts.uk.ctx.pr.proto.app.layout.command;

import lombok.Getter;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.shr.com.context.AppContexts;

@Getter
public class CreateLayoutCommand {

	private String stmtCode;
	private int startYM;
	private int layoutAtr;
	private String stmtName;
	private int endYM;
	/**
	 * Convert to domain object from command values
	 * @return
	 */
	public LayoutMaster toDomain(){
		return LayoutMaster.createFromJavaType(AppContexts.user().companyCode(), 
					this.startYM, this.stmtCode, this.endYM, this.layoutAtr, this.stmtName);
	}
}
