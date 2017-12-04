package nts.uk.screen.com.app.repository.company;

import lombok.Value;

@Value
public class CompanyQueryDto {
	String companyId;
	String companyCode;
	String companyName;
	int isAbolition;
}
