package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MPDataDto {
    private String id;
	private boolean state;
    private String error;
    private String employeeId;
    private String employeeCode;
    private String employeeName;
    private String workplaceId;
    private String employmentCode;
    private String typeGroup;
    private boolean identify;
    private boolean approval;
    private boolean dailyConfirm;
    private String dailyCorrectPerformance;
    private Set<MPCellDataDto> cellDatas;
    
    public MPDataDto(String id, boolean state, String error, String employeeId, String employeeCode, String employeeName,
			String workplaceId, String employmentCode, String typeGroup, boolean identify, boolean approval,
			boolean dailyConfirm, String dailyCorrectPerformance) {
    	
		this.id = id;
		this.state = state;
		this.error = error;
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.workplaceId = workplaceId;
		this.employmentCode = employmentCode;
		this.typeGroup = typeGroup;
		this.identify = identify;
		this.approval = approval;
		this.dailyConfirm = dailyConfirm;
		this.dailyCorrectPerformance = dailyCorrectPerformance;
		this.cellDatas = new HashSet<MPCellDataDto>(); 
	}	
	
	public void setCellDatas(Set<MPCellDataDto> lstCellData) {
		this.cellDatas = lstCellData;
	}
	
	public void addCellData(MPCellDataDto cellData) {
		this.cellDatas.add(cellData);
	}

	
}
