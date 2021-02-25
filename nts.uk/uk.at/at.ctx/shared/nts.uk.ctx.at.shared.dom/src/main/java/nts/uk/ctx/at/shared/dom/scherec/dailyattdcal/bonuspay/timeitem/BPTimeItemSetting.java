/**
 * 9:17:22 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.HTCalSettingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.OTCalSettingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.TimeItemTypeAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.WTCalSettingAtr;

/**
 * @author hungnm
 *
 */
@Getter
public class BPTimeItemSetting extends AggregateRoot {

	private String companyId;

	private int timeItemNo;

	private HTCalSettingAtr holidayCalSettingAtr;

	private OTCalSettingAtr overtimeCalSettingAtr;

	private WTCalSettingAtr worktimeCalSettingAtr;
	
	private TimeItemTypeAtr timeItemTypeAtr;

	private BPTimeItemSetting() {

	}

	private BPTimeItemSetting(String companyId, int tiemItemNo, HTCalSettingAtr holidayCalSettingAtr,
			OTCalSettingAtr overtimeCalSettingAtr, WTCalSettingAtr worktimeCalSettingAtr, TimeItemTypeAtr timeItemTypeAtr) {
		super();
		this.companyId = companyId;
		this.timeItemNo = tiemItemNo;
		this.holidayCalSettingAtr = holidayCalSettingAtr;
		this.overtimeCalSettingAtr = overtimeCalSettingAtr;
		this.worktimeCalSettingAtr = worktimeCalSettingAtr;
		this.timeItemTypeAtr = timeItemTypeAtr;

	}

	public static BPTimeItemSetting createFromJavaType(String companyId, int timeItemNo, int holidayCalSettingAtr,
			int overtimeCalSettingAtr, int worktimeCalSettingAtr, int timeItemTypeAtr) {
		return new BPTimeItemSetting(companyId, 
				timeItemNo,
				EnumAdaptor.valueOf(holidayCalSettingAtr, HTCalSettingAtr.class),
				EnumAdaptor.valueOf(overtimeCalSettingAtr, OTCalSettingAtr.class),
				EnumAdaptor.valueOf(worktimeCalSettingAtr, WTCalSettingAtr.class),
				EnumAdaptor.valueOf(timeItemTypeAtr, TimeItemTypeAtr.class));
	}

}
