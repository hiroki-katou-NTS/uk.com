package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;

@Data
@Builder
class ManagementClassificationSetting {
	SubstVacationSetting substVacationSetting;
	ManageDistinct manageDistinct;
}