/**
 * 9:17:22 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.timeitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.HTCalSettingAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.OTCalSettingAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.TimeItemTypeAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.WTCalSettingAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.TimeItemId;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author hungnm
 *
 */
@Getter
public class BPTimeItemSetting extends AggregateRoot {

	private String companyId;

	private TimeItemId tiemItemId;

	private HTCalSettingAtr holidayCalSettingAtr;

	private OTCalSettingAtr overtimeCalSettingAtr;

	private WTCalSettingAtr worktimeCalSettingAtr;

	private BPTimeItemSetting() {

	}

	private BPTimeItemSetting(String companyId, TimeItemId tiemItemId, HTCalSettingAtr holidayCalSettingAtr,
			OTCalSettingAtr overtimeCalSettingAtr, WTCalSettingAtr worktimeCalSettingAtr) {
		super();
		this.companyId = companyId;
		this.tiemItemId = tiemItemId;
		this.holidayCalSettingAtr = holidayCalSettingAtr;
		this.overtimeCalSettingAtr = overtimeCalSettingAtr;
		this.worktimeCalSettingAtr = worktimeCalSettingAtr;

	}

	public static BPTimeItemSetting createFromJavaType(String companyId, String tiemItemId, int holidayCalSettingAtr,
			int overtimeCalSettingAtr, int worktimeCalSettingAtr) {
		return new BPTimeItemSetting(companyId, new TimeItemId(tiemItemId),
				EnumAdaptor.valueOf(holidayCalSettingAtr, HTCalSettingAtr.class),
				EnumAdaptor.valueOf(overtimeCalSettingAtr, OTCalSettingAtr.class),
				EnumAdaptor.valueOf(worktimeCalSettingAtr, WTCalSettingAtr.class));
	}

}
