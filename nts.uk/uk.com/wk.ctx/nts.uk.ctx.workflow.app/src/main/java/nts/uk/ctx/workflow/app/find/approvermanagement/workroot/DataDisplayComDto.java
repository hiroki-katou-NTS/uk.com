package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;


import java.util.List;

import lombok.Value;
@Value
public class DataDisplayComDto {
	private int id;
	private boolean overLap;
	private String companyName;
	private List<CompanyAppRootDto> lstCompanyRoot;
}
