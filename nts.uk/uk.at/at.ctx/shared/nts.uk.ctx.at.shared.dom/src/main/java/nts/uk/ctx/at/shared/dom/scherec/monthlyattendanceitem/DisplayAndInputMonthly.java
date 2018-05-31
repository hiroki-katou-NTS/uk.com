package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 月次の勤怠項目の表示・入力制御
 * @author tutk
 *
 */
@Getter
public class DisplayAndInputMonthly extends DomainObject {

	private int itemMonthlyId;
	
	private boolean toUse;
	
	private InputControlMonthly inputControlMonthly;

	public DisplayAndInputMonthly(int itemMonthlyId, boolean toUse, InputControlMonthly inputControlMonthly) {
		super();
		this.itemMonthlyId = itemMonthlyId;
		this.toUse = toUse;
		this.inputControlMonthly = inputControlMonthly;
	}
	
}
