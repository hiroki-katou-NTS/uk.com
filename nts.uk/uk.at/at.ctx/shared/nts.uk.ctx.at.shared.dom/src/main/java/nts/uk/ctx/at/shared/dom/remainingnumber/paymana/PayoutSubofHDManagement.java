package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.UsedDays;

/**
 * 振出振休紐付け管理
 * @author HopNT	
 *
 */

@NoArgsConstructor
@Getter
public class PayoutSubofHDManagement extends AggregateRoot{
	// 振出データID
	private String payoutId;
	
	// 振休データID
	private String subOfHDID;
	
	// 使用日数
	private UsedDays usedDays;
	
	// 対象選択区分
	private TargetSelectionAtr targetSelectionAtr;

	public PayoutSubofHDManagement(String payoutId, String subOfHDID, Double usedDays,
			int targetSelectionAtr) {
		super();
		this.payoutId = payoutId;
		this.subOfHDID = subOfHDID;
		this.usedDays = new UsedDays(usedDays);
		this.targetSelectionAtr = EnumAdaptor.valueOf(targetSelectionAtr, TargetSelectionAtr.class);
	}
	
}
