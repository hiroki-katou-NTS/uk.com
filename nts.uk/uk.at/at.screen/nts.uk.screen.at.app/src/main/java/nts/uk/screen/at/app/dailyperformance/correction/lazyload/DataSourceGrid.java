package nts.uk.screen.at.app.dailyperformance.correction.lazyload;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPCellDataDto;

@Data

public class DataSourceGrid {
	private String id;
	private String state;
	private String error;
	private GeneralDate date;
	private boolean sign;
	private String employeeId;
	private String employeeCode;
	private String employeeName;
	private String workplaceId;
	private List<DPCellDataDto> datas;
	public DataSourceGrid(String id, String state, String error, GeneralDate date, boolean sign, String employeeId,
			String employeeCode, String employeeName, String workplaceId) {
		this.id = id;
		this.state = state;
		this.error = error;
		this.date = date;
		this.sign = sign;
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.workplaceId = workplaceId;
		this.datas = new ArrayList<>();
	}
	
	
}
