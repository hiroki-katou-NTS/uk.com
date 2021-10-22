package nts.uk.screen.at.app.dailyperformance.correction.datadialog;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class ParamDialog {
	private String workTypeCode;
	private String employmentCode;
	private String workplaceId;
	private GeneralDate date;
	private String selectCode;
	private String employeeId;
	private Integer itemId;
	private String valueOld;
	private Integer taskFrameNo;
	public ParamDialog (GeneralDate date, String selectCode){
		this.date = date;
		this.selectCode = selectCode;
	}
}
