package nts.uk.screen.at.ws.kmt.kmt013;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class TargetOrgParams {
    private GeneralDate baseDate;

    private  int unit;

    private String orgId;
}
