package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.Data;

import java.util.List;

@Data
public class StatementLayoutExportQuery {
    private int processingDate;
    private List<String> statementCodes;
}
