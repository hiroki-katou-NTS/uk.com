package nts.uk.ctx.at.record.dom.adapter.personnelcostsetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonnelCostSettingImport {
	
	//No
	private Integer NO;
	
	//勤怠項目ID
	private Integer attendanceItemId;
	
	

}
