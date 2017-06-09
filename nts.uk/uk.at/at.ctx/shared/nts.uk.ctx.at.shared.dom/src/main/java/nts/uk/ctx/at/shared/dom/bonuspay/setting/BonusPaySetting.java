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

	private CompanyId companyId;

	private BonusPaySettingCode code;

	private BonusPaySettingName name;

	private List<BonusPayTimesheet> lstBonusPayTimesheet;

	private List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet;

	private BonusPaySetting(CompanyId companyId, BonusPaySettingCode code, BonusPaySettingName name) {
		super();
		this.companyId = companyId;
		this.code = code;
		this.name = name;
	}

	private BonusPaySetting() {
		super();
	}

	public static BonusPaySetting createFromJavaType(String companyId, String code, String name) {
		return new BonusPaySetting(new CompanyId(companyId), new BonusPaySettingCode(code),
				new BonusPaySettingName(name));
	}

	public void setListTimesheet(List<BonusPayTimesheet> lstBonusPayTimesheet) {
		this.lstBonusPayTimesheet = lstBonusPayTimesheet;
	}
	
	public void setListSpecialTimesheet(List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet) {
		this.lstSpecBonusPayTimesheet = lstSpecBonusPayTimesheet;
	}
}
