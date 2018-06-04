package nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SWkpHistImport;

@AllArgsConstructor
@Data
public class SWkpHistDto {
	/** The date range. */
	// 配属期間
	private GeneralDate startDate;
	private GeneralDate endDate;

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The workplace id. */
	// 職場ID
	private String workplaceId;

	/** The workplace code. */
	private String workplaceCode;

	/** The workplace name. */
	private String workplaceName;

	/** The wkp display name. */
	// 職場表示名
	/**
	 * 
	 */
	private String wkpDisplayName;

	public static SWkpHistDto convertToDto(SWkpHistImport sWkpHist) {
		return new SWkpHistDto(sWkpHist.getDateRange().start(), sWkpHist.getDateRange().end(), sWkpHist.getEmployeeId(),
				sWkpHist.getWorkplaceId(), sWkpHist.getWorkplaceCode(), sWkpHist.getWorkplaceName(), sWkpHist.getWkpDisplayName());
	}

}
