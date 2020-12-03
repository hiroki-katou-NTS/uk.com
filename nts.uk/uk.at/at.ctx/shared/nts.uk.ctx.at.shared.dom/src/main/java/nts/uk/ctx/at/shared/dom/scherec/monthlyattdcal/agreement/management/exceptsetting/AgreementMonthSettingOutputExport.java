package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgreementMonthSettingOutputExport {
	
	// データあるなし
	private Boolean isExist;
	
	// optional<３６協定年月設定>
	private Optional<AgreementMonthSetting> opAgreementMonthSetting = Optional.empty();

}
