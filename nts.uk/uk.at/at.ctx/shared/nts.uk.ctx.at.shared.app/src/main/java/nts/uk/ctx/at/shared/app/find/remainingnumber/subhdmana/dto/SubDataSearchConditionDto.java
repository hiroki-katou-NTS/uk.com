package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
@Setter
public class SubDataSearchConditionDto {
	private int searchMode;
	private String employeeId;
	private GeneralDate startDate;
	private GeneralDate endDate;
}
