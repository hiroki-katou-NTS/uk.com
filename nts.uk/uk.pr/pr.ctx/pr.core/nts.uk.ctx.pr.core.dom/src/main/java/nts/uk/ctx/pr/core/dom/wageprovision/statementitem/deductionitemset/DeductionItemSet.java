package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemNameCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.BreakdownItemUseAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.DetailAmountErrorAlarmRangeSetting;
import nts.uk.shr.com.primitive.Memo;

/**
 * 控除項目設定
 */
@Getter
public class DeductionItemSet extends AggregateRoot {

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
	private Optional<Memo> note;

	/**
	 * 項目名コード
	 */
	private ItemNameCode itemNameCode;

	/**
	 * カテゴリ区分
	 */
	private CategoryAtr categoryAtr;

	/**
	 * 控除アラーム範囲設定
	 */
	private DetailAmountErrorAlarmRangeSetting alarmRangeSetting;

	/**
	 * 控除エラー範囲設定
	 */
	private DetailAmountErrorAlarmRangeSetting errorRangeSetting;

	public DeductionItemSet(String cid, String salaryItemId, int deductionItemAtr, int breakdownItemUseAtr,
			String note) {
		super();
		this.cid = cid;
		this.salaryItemId = salaryItemId;
		this.deductionItemAtr = EnumAdaptor.valueOf(deductionItemAtr, DeductionItemAtr.class);
		this.breakdownItemUseAtr = EnumAdaptor.valueOf(breakdownItemUseAtr, BreakdownItemUseAtr.class);
		this.note = note == null ? Optional.empty() : Optional.of(new Memo(note));
	}

}
