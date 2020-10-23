package nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.sortsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SortSettingDto {
	
	private String companyID;
	
	private List<OrderListDto> lstOrderList;

}
