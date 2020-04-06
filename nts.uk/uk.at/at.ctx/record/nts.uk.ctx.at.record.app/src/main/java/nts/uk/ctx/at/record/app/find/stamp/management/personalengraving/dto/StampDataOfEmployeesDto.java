package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataOfEmployees;

/**
 * @author anhdt
 *
 */
@Data
public class StampDataOfEmployeesDto {
	private String employeeId;
	private String date;
	private List<StampRecordDto> stampRecords = new ArrayList<>();
	private List<EmpStampDto> stamps = new ArrayList<>();
	
	public StampDataOfEmployeesDto (StampDataOfEmployees domain) {
		this.employeeId = domain.getEmployeeId();
		this.date = domain.getDate().toString();
		
		for(StampRecord stampRecord : domain.getListStampRecord()) {
			this.stampRecords.add(new StampRecordDto(stampRecord));
		}
		
		for(Stamp stamp : domain.getListStamp()) {
			this.stamps.add(new EmpStampDto(stamp));
		}
		
	}
}
