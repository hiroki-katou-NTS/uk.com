package nts.uk.ctx.at.shared.app.find.remainingnumber.nursingcareleave.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CareLeaveDataDto {
	
	//社員ID
	private String sId;
	
	//使用日数
	private double numOfUsedDay;
}
