package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PeriodsClose {

	// 締め名称
	private String closureName;

	// 期間

	private GeneralDate startDate;

	private GeneralDate endDate;
	
	private String workplaceId;
}