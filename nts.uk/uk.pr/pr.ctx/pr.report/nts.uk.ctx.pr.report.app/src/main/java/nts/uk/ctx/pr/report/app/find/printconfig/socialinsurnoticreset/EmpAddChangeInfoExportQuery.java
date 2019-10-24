package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
public class EmpAddChangeInfoExportQuery {
    private EmpAddChangeInfoExport socialInsurNotiCreateSet;
    private List<String> empIds;
    private GeneralDate startDate;
    private GeneralDate endDate;
    private  GeneralDate reference;
    private int screenMode;
}
