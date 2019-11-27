package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
@Getter
@Setter
@NoArgsConstructor
public class WorkTypeKAF011 {

	private List<WorkType> lstWorkType;
	private boolean masterUnreg;
}
