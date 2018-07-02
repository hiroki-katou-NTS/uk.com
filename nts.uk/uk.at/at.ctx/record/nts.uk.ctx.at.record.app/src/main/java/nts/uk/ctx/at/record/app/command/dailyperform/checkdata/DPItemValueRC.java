package nts.uk.ctx.at.record.app.command.dailyperform.checkdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DPItemValueRC {
	
	private String rowId;

	private int itemId;

	private String value;

	private String valueType;

	private String layoutCode;

	private String employeeId;

	private GeneralDate date;

	private Integer typeGroup;
	
	private String nameMessage;

	public DPItemValueRC(String rowId, String employeeId, GeneralDate date, int itemId) {
		this.rowId = rowId;
		this.employeeId = employeeId;
		this.date = date;
		this.itemId = itemId;
	}

	public DPItemValueRC(String rowId, String employeeId, GeneralDate date, int itemId, String value, String message) {
		this.rowId = rowId;
		this.employeeId = employeeId;
		this.date = date;
		this.itemId = itemId;
		this.value = value;
		this.nameMessage = message;
	}
}
