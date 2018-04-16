package nts.uk.ctx.at.function.dom.annualworkschedule;

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
	* 36協定の表示設定
	*/
	private int dispSettAgr36;
	/**
	* 名称
	*/
	private String name;
	/**
	* 
	*/
	private int outNumExceedTime36Agr;
	/**
	* 
	*/
	private int displayFormat;

	/**
	 * 
	 * @param cid 会社ID
	 * @param cd コード
	 * @param dispSettAgr36 36協定の表示設定
	 * @param name 名称
	 * @param outNumExceedTime36Agr
	 * @param displayFormat
	 */
	public SetOutItemsWoSc(String cid, int cd, int dispSettAgr36, String name,
			int outNumExceedTime36Agr, int displayFormat) {
		super();
		this.cid = cid;
		this.cd = cd;
		this.dispSettAgr36 = dispSettAgr36;
		this.name = name;
		this.outNumExceedTime36Agr = outNumExceedTime36Agr;
		this.displayFormat = displayFormat;
	}
}
