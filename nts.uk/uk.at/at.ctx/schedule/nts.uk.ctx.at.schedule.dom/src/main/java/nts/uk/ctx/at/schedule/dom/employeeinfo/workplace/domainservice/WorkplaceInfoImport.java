package nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.domainservice;

import lombok.Value;

@Value
public class WorkplaceInfoImport {

    private String workplaceId;

    private String hierarchyCode;

    private String workplaceCode;

    private String workplaceName;

    private String workplaceDisplayName;

    private String workplaceGenericName;

    private String workplaceExternalCode;

}
