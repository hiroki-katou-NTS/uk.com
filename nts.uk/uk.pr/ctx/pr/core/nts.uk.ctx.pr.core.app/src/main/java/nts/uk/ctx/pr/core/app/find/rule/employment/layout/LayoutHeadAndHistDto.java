package nts.uk.ctx.pr.core.app.find.rule.employment.layout;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutAtr;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutHistory;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutMaster;

@Value
public class LayoutHeadAndHistDto {
	private LayoutAtr layoutAtr;
	private String stmtCode;
	private String stmtName;

	public static LayoutHeadAndHistDto fromDomain(LayoutHistory layoutHist, LayoutMaster layoutHead) {
		return new LayoutHeadAndHistDto(layoutHist.getLayoutAtr(), layoutHead.getStmtCode().v(),
				layoutHead.getStmtName().v());
	}
}
