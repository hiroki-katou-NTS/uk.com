package nts.uk.ctx.pr.proto.app.command.layout;

import lombok.Getter;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.shr.com.context.AppContexts;

@Getter
public class CreateLayoutCommand {

	/** Nếu mode trên màn hình là copy từ layout khác thì isCopy=true */
	private boolean checkCopy;
	/** StmtCode của Layout được copy */
	private String stmtCodeCopied;
	/** startYm của Layout được copy */
	private int startYmCopied;
	private String stmtCode;
	private int startYm;
	private int layoutAtr;
	private String stmtName;
	private int endYm;
	/**
	 * Convert to domain object from command values
	 * @return
	 */
	public LayoutMaster toDomain(){
		return LayoutMaster.createFromJavaType(AppContexts.user().companyCode(), 
					this.startYm, this.stmtCode, this.endYm, this.layoutAtr, this.stmtName);
	}
}
