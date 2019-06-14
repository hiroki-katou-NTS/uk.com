package nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee;

import lombok.Value;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayee;

@Value
public class ResidentTaxPayeeCommand {

	private String code;
	private String name;
	private String kanaName;
	private int prefectures;
	private String reportCd;
	private String accountNumber;
	private String subscriberName;
	private String designationNum;
	private String postCode;
	private String compileStationName;
	private String memo;
	private boolean updateMode;

	public ResidentTaxPayee toDomain(String companyId) {
		return new ResidentTaxPayee(companyId, this.code, this.name, this.kanaName, this.prefectures,
				this.compileStationName, this.postCode, this.subscriberName, this.accountNumber,
				this.reportCd, this.designationNum, null, this.memo);
	}

}
