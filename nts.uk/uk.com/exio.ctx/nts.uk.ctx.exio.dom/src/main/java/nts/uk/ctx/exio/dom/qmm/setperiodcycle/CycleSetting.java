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

	public CycleSetting(int cycleSettingAtr, int january, int february, int march, int april, int may, int june,
			int july, int august, int september, int october, int november, int december) {
		super();
		this.cycleSettingAtr = EnumAdaptor.valueOf(cycleSettingAtr, CycleSettingAtr.class);
		this.monthlyList = new MonthlyList(january, february, march, april, may, june, july, august, september, october,
				november, december);
	}

}
