package nts.uk.ctx.at.function.dom.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
@Getter
public class SetOutItemsWoSc extends AggregateRoot {
	/**
	* 会社ID
	*/
	private String cid;

	/**
	* コード
	*/
	private int cd;

	/**
	* 名称
	*/
	private String name;

	/**
	* 36協定時間を超過した月数を出力する
	*/
	private int outNumExceedTime36Agr;

	/**
	* 表示形式
	*/
	private int displayFormat;

	public SetOutItemsWoSc(String cid, int cd, String name, int outNumExceedTime36Agr, int displayFormat) {
		super();
		this.cid = cid;
		this.cd = cd;
		this.name = name;
		this.outNumExceedTime36Agr = outNumExceedTime36Agr;
		this.displayFormat = displayFormat;
	}
}
