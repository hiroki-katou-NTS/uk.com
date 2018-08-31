package nts.uk.ctx.exio.dom.qmm.setperiodcycle;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author thanh.tq サイクル設定
 *
 */
@Getter
public class CycleSetting extends DomainObject {
	/**
	 * サイクル設定区分
	 */
	private CycleSettingAtr cycleSettingAtr;

	/**
	 * 対象月リスト
	 */
	private MonthlyList monthlyList;

	public CycleSetting(int cycleSettingAtr, MonthlyList monthlyList) {
		super();
		this.cycleSettingAtr = EnumAdaptor.valueOf(cycleSettingAtr, CycleSettingAtr.class);
		this.monthlyList = monthlyList;
	}

}
