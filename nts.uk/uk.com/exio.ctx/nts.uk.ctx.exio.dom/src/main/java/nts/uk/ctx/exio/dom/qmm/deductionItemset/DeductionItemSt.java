package nts.uk.ctx.exio.dom.qmm.deductionItemset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 控除項目設定
 */
@Getter
public class DeductionItemSt extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * 控除項目区分
	 */
	private DeductionItemAtr deductionItemAtr;

	/**
	 * 内訳項目利用区分
	 */
	private BreakdownItemUseAtr breakdownItemUseAtr;

	/**
	 * 備考
	 */
	private Optional<String> note;

	public DeductionItemSt(String cid, String salaryItemId, int deductionItemAtr, int breakdownItemUseAtr,
			String note) {
		super();
		this.cid = cid;
		this.salaryItemId = salaryItemId;
		this.deductionItemAtr = EnumAdaptor.valueOf(deductionItemAtr, DeductionItemAtr.class);
		this.breakdownItemUseAtr = EnumAdaptor.valueOf(breakdownItemUseAtr, BreakdownItemUseAtr.class);
		this.note = Optional.ofNullable(note);
	}

}
