package nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice;

import lombok.Data;

import java.util.List;

@Data
public class LaborInsuranceExportQuery {
    private int processingDate;
    private List<String> statementCodes;
}
