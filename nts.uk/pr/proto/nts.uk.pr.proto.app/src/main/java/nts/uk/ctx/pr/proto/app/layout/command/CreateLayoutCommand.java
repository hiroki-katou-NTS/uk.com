package nts.uk.ctx.pr.proto.app.layout.command;

import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;

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
		return null; //LayoutMaster.createFromJavaType(startYM, stmtCode, endYM, layoutAtr, stmtName);
	}
}
