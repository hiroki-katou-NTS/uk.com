package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 現在処理年月
 */
@AllArgsConstructor
@Getter
public class CurrProcessDate extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 給与現在処理年月
	 */
	private int giveCurrTreatYear;

	public static CurrProcessDate createFromJavaType(String cid2, int processCateNo2, int giveCurrTreatYear2) {
		// TODO Auto-generated method stub
		return null;
	}

}
