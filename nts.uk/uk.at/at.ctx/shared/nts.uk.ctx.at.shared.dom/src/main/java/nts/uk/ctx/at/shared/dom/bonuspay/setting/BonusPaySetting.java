/**
 * 9:18:12 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingName;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author hungnm
 *
 */
@Getter
public class BonusPaySetting extends AggregateRoot {

	private String companyId;

	private BonusPaySettingCode code;

	private BonusPaySettingName name;

	private List<BonusPayTimesheet> lstBonusPayTimesheet;

	private List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet;

	private BonusPaySetting(String companyId, BonusPaySettingCode code, BonusPaySettingName name,
			List<BonusPayTimesheet> lstBonusPayTimesheet, List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet) {
		super();
		this.companyId = companyId;
		this.code = code;
		this.name = name;
		this.lstBonusPayTimesheet = lstBonusPayTimesheet;
		this.lstSpecBonusPayTimesheet = lstSpecBonusPayTimesheet;
	}

	private BonusPaySetting() {
		super();
	}

	public static BonusPaySetting createFromJavaType(String companyId, String code, String name,
			List<BonusPayTimesheet> lstBonusPayTimesheet, List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet) {
		return new BonusPaySetting(companyId, new BonusPaySettingCode(code),
				new BonusPaySettingName(name), lstBonusPayTimesheet, lstSpecBonusPayTimesheet);
	}

}
