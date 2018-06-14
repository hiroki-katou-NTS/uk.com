package nts.uk.ctx.at.shared.app.find.remainingnumber.paymana;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SWkpHistImport;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FurikyuMngDataExtractionDto {
		
	private List<CompositePayOutSubMngData> compositePayOutSubMngData;
	private int expirationDate;
	private Double numberOfDayLeft;
	private Integer closureID;
	private boolean haveEmploymentCode;
	private SWkpHistImport sWkpHistImport;
	private PersonEmpBasicInfoImport personEmpBasicInfoImport;
}
