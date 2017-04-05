package nts.uk.file.pr.app.export.residentialtax;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ResidentialTaxQuery {
	private List<String> residentTaxCodeList;
	private boolean companyLogin;
	private String regalDocCompanyCode;
	private int yearMonth;
	private int processingYearMonth;
	private GeneralDate endDate;
}
