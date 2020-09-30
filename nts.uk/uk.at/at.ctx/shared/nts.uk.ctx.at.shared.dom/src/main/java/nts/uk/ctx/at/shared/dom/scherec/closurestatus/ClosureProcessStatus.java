package nts.uk.ctx.at.shared.dom.scherec.closurestatus;

/**
 * 
 * @author HungTT - 締め処理実施状態
 *
 */
public enum ClosureProcessStatus {

	NOT_EXECUTED(0, "未実施"),

	DONE_EXECUTED(1, "実施済み");

	public int value;

	public String nameId;

	private ClosureProcessStatus(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
