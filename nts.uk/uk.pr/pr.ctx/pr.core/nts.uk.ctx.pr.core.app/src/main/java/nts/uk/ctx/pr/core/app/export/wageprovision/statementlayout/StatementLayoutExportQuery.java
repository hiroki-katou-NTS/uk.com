package nts.uk.ctx.pr.core.app.export.wageprovision.statementlayout;

import lombok.Data;

import java.util.List;

@Data
public class StatementLayoutExportQuery {
    private int processingDate;
    private List<String> statementCodes;
}
