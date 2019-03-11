package nts.uk.file.at.app.export.yearholidaymanagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.query.pub.workplace.WorkplaceExport;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkplaceHolidayExport {

	public WorkplaceHolidayExport(WorkplaceExport workplace) {
			this.workplaceId = workplace.getWorkplaceId();
			this.workplaceCode = workplace.getWorkplaceCode();
			this.workplaceGenericName = workplace.getWorkplaceGenericName();
			this.workplaceName = workplace.getWorkplaceName();
	}

	/** The workplace id. */
	private String workplaceId; // 職場ID

	/** The workplace code. */
	private String workplaceCode; // 職場コード

	/** The workplace generic name. */
	private String workplaceGenericName; // 職場総称

	/** The workplace name. */
	private String workplaceName; // 職場表示名
	
	
	private String hierarchyCode;	
}
