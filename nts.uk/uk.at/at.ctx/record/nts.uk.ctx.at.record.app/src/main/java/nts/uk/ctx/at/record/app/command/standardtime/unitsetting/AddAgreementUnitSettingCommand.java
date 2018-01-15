package nts.uk.ctx.at.record.app.command.standardtime.unitsetting;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddAgreementUnitSettingCommand {

	private int classificationUseAtr;

	private int employmentUseAtr;

	private int workPlaceUseAtr;
}
