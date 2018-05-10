package nts.uk.ctx.at.function.dom.annualworkschedule;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.OutputAgreementTime;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScName;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
@AllArgsConstructor
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
	private int outNumExceedTime36Agr;

	/**
	* 表示形式
	*/
	private OutputAgreementTime displayFormat;

	private List<ItemOutTblBook> listItemOutTblBook;
	
	public static SetOutItemsWoSc createFromJavaType(String cid, String cd, String name, int outNumExceedTime36Agr,
			int displayFormat, List<ItemOutTblBook> listItemOutTblBook) {
		return new SetOutItemsWoSc(cid, new OutItemsWoScCode(cd),
				new OutItemsWoScName(name), outNumExceedTime36Agr,
				EnumAdaptor.valueOf(displayFormat, OutputAgreementTime.class),
				listItemOutTblBook);
	}
}
