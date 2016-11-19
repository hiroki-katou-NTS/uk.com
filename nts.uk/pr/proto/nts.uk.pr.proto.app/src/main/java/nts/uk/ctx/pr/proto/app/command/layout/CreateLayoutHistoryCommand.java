package nts.uk.ctx.pr.proto.app.command.layout;

import lombok.Getter;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.shr.com.context.AppContexts;

@Getter
public class CreateLayoutHistoryCommand {

	/** Nếu mode trên màn hình là tiếp từ layout lịch sử trước thì isContinue=true */
	private boolean isContinue;
	private String stmtCode;
	private int startYm;
	private int endYm;
	private int endYmPrevious;

	/**
	 * Convert to domain object from command values
	 * @return
	 */
	public LayoutMaster toDomain(int layoutAtr, String stmtName){
		return LayoutMaster.createFromJavaType(
				AppContexts.user().companyCode(), this.startYm, this.stmtCode, this.endYm, layoutAtr, stmtName);
	}
	
}
