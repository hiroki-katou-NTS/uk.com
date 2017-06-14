package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClassificationUseAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.EmploymentUseAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.WorkPlaceUseAtr;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementUnitSetting {

	private String companyId;

	private ClassificationUseAtr classificationUseAtr;

	private EmploymentUseAtr employmentUseAtr;

	private WorkPlaceUseAtr workPlaceUseAtr;
}
