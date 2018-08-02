package nts.uk.ctx.sys.log.app.find.reference.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;

/**
 * 
 * @author Thuongtv
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDataCorrectRecordRefeDto {


	private String operationId;
	private GeneralDate targetDate;
	private int targetDataType;
	private String itemName;
	private String valueBefore;
	private String valueAfter;
	private String remarks;
	private String correctionAttr;
	private String userNameTaget;
	private String employeeIdtaget;

	public static LogDataCorrectRecordRefeDto fromDomain(DataCorrectionLog domain) {

		return new LogDataCorrectRecordRefeDto(
				domain.getOperationId(),
				domain.getTargetDataKey().getDateKey().get(),
				domain.getTargetDataType().value,
				domain.getCorrectedItem().getName(),
				domain.getCorrectedItem().getValueBefore().getViewValue(),
				domain.getCorrectedItem().getValueAfter().getViewValue(),
				domain.getRemark(),getCorrectionAttr(domain.getCorrectionAttr().value),
				domain.getTargetUser().getUserName(),domain.getTargetUser().getEmployeeId()
				);
		
	}
	
	private static String getCorrectionAttr(int attr) {
		CorrectionAttr correctionAttr = CorrectionAttr.of(attr);
		switch (correctionAttr) {
		case EDIT:
			return TextResource.localize("Enum_CorrectionAttr_EDIT");
		case CALCULATE:
			return TextResource.localize("Enum_CorrectionAttr_CALCULATE");
		case REFLECT:
			return TextResource.localize("Enum_CorrectionAttr_REFLECT");
		default:
			return "";
		}
	}
	
}
