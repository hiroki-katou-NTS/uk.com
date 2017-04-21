package nts.uk.file.pr.app.export.residentialtax;

import java.util.List;
import lombok.Data;

@Data
public class InhabitantTaxChecklistQuery {
	private List<String> residentTaxCodeList;
	private boolean companyLogin;
	private int yearMonth;
	private int processingYearMonth;
}
