package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset;

import java.util.Optional;

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
	private Optional<MonthlyList> monthlyList;

	public CycleSetting(int cycleSettingAtr, Integer january, Integer february, Integer march, Integer april, Integer may, Integer june,
			Integer july, Integer august, Integer september, Integer october, Integer november, Integer december) {
		super();
		this.cycleSettingAtr = EnumAdaptor.valueOf(cycleSettingAtr, CycleSettingAtr.class);
		this.monthlyList = cycleSettingAtr == CycleSettingAtr.NOT_USE.value ? Optional.empty()
				: Optional.of(new MonthlyList(january, february, march, april, may, june, july, august, september,
						october, november, december));
	}

}
