package nts.uk.ctx.at.request.dom.application.common.adapter.record.standardtime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgreementMonthSettingOutput {
	// データあるなし
	private Boolean isExist;
	
	// optional<３６協定年月設定>
	private Optional<AgreementMonthSetting> opAgreementMonthSetting = Optional.empty();
}
