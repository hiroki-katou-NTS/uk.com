package nts.uk.ctx.at.function.dom.annualworkschedule;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 項目の算出式
*/
@Getter
public class CalcFormulaItem extends AggregateRoot {
	/**
	* 会社ID
	*/
	private String cid;

	/**
	* コード
	*/
	private int setOutCd;

	/**
	* コード
	*/
	private int itemOutCd;

	/**
	* 勤怠項目ID
	*/
	private int attendanceItemId;

	/**
	* 加, 減
	*/
	private int operation;

	public CalcFormulaItem(String cid, int setOutCd, int itemOutCd, int attendanceItemId, int operation) {
		super();
		this.cid = cid;
		this.setOutCd = setOutCd;
		this.itemOutCd = itemOutCd;
		this.attendanceItemId = attendanceItemId;
		this.operation = operation;
	}
}
