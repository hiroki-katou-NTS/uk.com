package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.UsedDays;

/**
 * 休出代休紐付け管理
 * @author HopNT
 *
 */
@NoArgsConstructor
@Getter
public class LeaveComDayOffManagement extends AggregateRoot{
	// 休出ID
	private String leaveID;
	
	// 代休ID	
	private String comDayOffID;
	
	// 使用日数
	private UsedDays usedDays;
	
	// 使用時間数
	private UsedHours usedHours;
	
	// 対象選択区分
	private TargetSelectionAtr targetSelectionAtr;

	public LeaveComDayOffManagement(String leaveID, String comDayOffID, BigDecimal usedDays, int usedHours,
			int targetSelectionAtr) {
		super();
		this.leaveID = leaveID;
		this.comDayOffID = comDayOffID;
		this.usedDays = new UsedDays(usedDays);
		this.usedHours = new UsedHours(usedHours);
		this.targetSelectionAtr = EnumAdaptor.valueOf(targetSelectionAtr, TargetSelectionAtr.class);
	}
	
	
}
