package nts.uk.ctx.at.record.dom.workrecord.identificationstatus;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author nampt
 * 本人確認 - root
 * 日の本人確認
 */
@Getter
public class Identification extends AggregateRoot {
	/**会社ID*/
	private String companyID;
	/**社員ID*/
	private String employeeId;
	/**年月日*/
	private GeneralDate processingYmd;
	/**本人確認日*/
	private GeneralDate indentificationYmd;

	public Identification(String companyID, String employeeId, GeneralDate processingYmd,
			GeneralDate indentificationYmd) {
		super();
		this.companyID = companyID;
		this.employeeId = employeeId;
		this.processingYmd = processingYmd;
		this.indentificationYmd = indentificationYmd;
	}


}
