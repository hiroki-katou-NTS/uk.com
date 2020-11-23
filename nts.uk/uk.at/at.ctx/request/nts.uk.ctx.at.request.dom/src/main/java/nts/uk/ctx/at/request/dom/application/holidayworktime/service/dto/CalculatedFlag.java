package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

/**
 * 計算済フラグ
 * @author huylq
 *Refactor5
 */
public enum CalculatedFlag {
	
	UNCALCULATED(0, "未計算"),
	
	CALCULATED(1, "計算済");
	
	public final int value;
	
	public final String name;
	
	private CalculatedFlag(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
