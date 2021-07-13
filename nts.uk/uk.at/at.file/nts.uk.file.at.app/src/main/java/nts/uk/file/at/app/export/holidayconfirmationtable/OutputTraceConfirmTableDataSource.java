package nts.uk.file.at.app.export.holidayconfirmationtable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.app.query.holidayconfirmationtable.DisplayContentsOfSubLeaveConfirmationTable;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;

import java.util.List;

@AllArgsConstructor
@Getter
public class OutputTraceConfirmTableDataSource {
    List<DisplayContentsOfSubLeaveConfirmationTable> leaveConfirmationTables;
    CompanyBsImport companyInfo;
    CreateTraceConfirmationTableFileQuery query;
}
