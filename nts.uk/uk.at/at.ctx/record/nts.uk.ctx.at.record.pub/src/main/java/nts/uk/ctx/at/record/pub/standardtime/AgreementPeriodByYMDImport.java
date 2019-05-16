package nts.uk.ctx.at.record.pub.standardtime;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Getter
public class AgreementPeriodByYMDImport {
	
	//会社ID
	String companyId;
	
	//指定年月日
	GeneralDate ymd;
	
	//締めID
	ClosureId closureId;

	/**
	 * Constructor 
	 */
	public AgreementPeriodByYMDImport(String companyId, GeneralDate ymd, ClosureId closureId) {
		super();
		this.companyId = companyId;
		this.ymd = ymd;
		this.closureId = closureId;
	}
}
