package nts.uk.ctx.at.function.dom.annualworkschedule;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.OutputAgreementTime;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScName;

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
	private OutItemsWoScCode cd;
	
	/**
	* 名称
	*/
	private OutItemsWoScName name;

	/**
	* 36協定時間を超過した月数を出力する
	*/
	private boolean outNumExceedTime36Agr;

	/**
	* 表示形式
	*/
	private OutputAgreementTime displayFormat;

	private List<ItemOutTblBook> listItemOutTblBook;
	
	public static SetOutItemsWoSc createFromJavaType(String cid, String cd, String name, boolean outNumExceedTime36Agr,
			int displayFormat, List<ItemOutTblBook> listItemOutTblBook) {
		return new SetOutItemsWoSc(cid, new OutItemsWoScCode(cd),
				new OutItemsWoScName(name), outNumExceedTime36Agr,
				EnumAdaptor.valueOf(displayFormat, OutputAgreementTime.class),
				listItemOutTblBook);
	}

	public SetOutItemsWoSc(String cid, OutItemsWoScCode cd, OutItemsWoScName name, boolean outNumExceedTime36Agr,
			OutputAgreementTime displayFormat, List<ItemOutTblBook> listItemOutTblBook) {
		super();
		this.cid = cid;
		this.cd = cd;
		this.name = name;
		this.outNumExceedTime36Agr = outNumExceedTime36Agr;
		this.displayFormat = displayFormat;
		this.listItemOutTblBook = listItemOutTblBook;
	}
}
