package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

/**
 * 計算済フラグ
 * @author huylq
 *Refactor5
 */
public enum CalculatedFlag {
	
	CALCULATED(0, "計算済"),
	
	UNCALCULATED(1, "未計算");
	
	public final int value;
	
	public final String name;
	
	private CalculatedFlag(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
