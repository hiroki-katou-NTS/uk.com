package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppOvertimeRefer {	
	
	private String appDate;
	
	private String workTypeCD;
	
	private String workTypeName;
	
	private String workTimeCD;
	
	private String workTimeName;
	
	private String timeRange;
	
	private List<OvertimeRefer> listOvertimeRefer; 

}
