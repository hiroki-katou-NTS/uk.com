package nts.uk.ctx.basic.dom.organization.employment;

import lombok.Getter;

@Getter
public class Employment {
	private final String companyCode;
	
	private EmploymentCode employmentCode;
	
	private EmploymentName employmentName;
	
	private CloseDateNo closeDateNo;
	
	private Memo memo;
	
	private ProcessingNo processingNo;
	
	private ManageOrNot statutoryHolidayAtr;
	
	private EmploymentCode employementOutCd;

	public Employment(String companyCode, EmploymentCode employmentCode, EmploymentName employmentName,
			CloseDateNo closeDateNo, Memo memo, ProcessingNo processingNo, ManageOrNot statutoryHolidayAtr,EmploymentCode employementOutCd) {
		super();
		this.companyCode = companyCode;
		this.employmentCode = employmentCode;
		this.employmentName = employmentName;
		this.closeDateNo = closeDateNo;
		this.memo = memo;
		this.processingNo = processingNo;
		this.statutoryHolidayAtr = statutoryHolidayAtr;
		this.employementOutCd = employementOutCd;
	}
	
}
