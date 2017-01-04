package nts.uk.ctx.pr.core.app.command.layout;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.layout.LayoutMaster;
import nts.uk.shr.com.context.AppContexts;

@Getter
public class CreateLayoutHistoryCommand {

	/** Nếu mode trên màn hình là tiếp từ layout lịch sử trước thì isContinue=true */
	private boolean checkContinue;
	private String stmtCode;
	private int startYm;
	private int endYm;
	private int startPrevious;
	private int layoutAtr;
	/**
	 * Convert to domain object from command values
	 * @return
	 */
	public LayoutMaster toDomain(int startYm,
			int endYm,
			int layoutAtr,
			String stmtName, String historyId){
		return LayoutMaster.createFromJavaType(
				AppContexts.user().companyCode(), startYm, this.stmtCode, endYm, layoutAtr, stmtName, historyId);
	}
	
}
