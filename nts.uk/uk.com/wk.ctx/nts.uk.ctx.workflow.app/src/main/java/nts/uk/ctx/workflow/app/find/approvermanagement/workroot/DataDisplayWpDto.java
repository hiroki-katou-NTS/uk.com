package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
@Value
public class DataDisplayWpDto {
	private int id;
	private boolean overLap;
	private List<WorkPlaceAppRootDto> lstWorkplaceRoot;
}
