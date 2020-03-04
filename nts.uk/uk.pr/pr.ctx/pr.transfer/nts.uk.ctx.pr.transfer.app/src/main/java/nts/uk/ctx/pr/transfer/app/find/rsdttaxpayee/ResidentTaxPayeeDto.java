package nts.uk.ctx.pr.transfer.app.find.rsdttaxpayee;

import lombok.Value;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayee;
/**
 * 
 * @author HungTT
 *
 */
@Value
public class ResidentTaxPayeeDto {

	private String code;
	private String name;
	private String kanaName;
	private int prefectures;
	private String reportCd;
	private String reportName;
	private String accountNumber;
	private String subscriberName;
	private String designationNum;
	private String postCode;
	private String compileStationName;
	private String memo;

	public ResidentTaxPayeeDto(ResidentTaxPayee domain, String reportName) {
		this.code = domain.getCode().v();
		this.name = domain.getName().v();
		this.kanaName = domain.getKanaName().isPresent() ? domain.getKanaName().get().v() : null;
		this.prefectures = domain.getPrefectures();
		this.reportCd = domain.getReportCd().isPresent() ? domain.getReportCd().get().v() : null;
		this.accountNumber = domain.getAccountNumber().isPresent() ? domain.getAccountNumber().get().v() : null;
		this.subscriberName = domain.getSubscriberName().isPresent() ? domain.getSubscriberName().get().v() : null;
		this.designationNum = domain.getDesignationNum().isPresent() ? domain.getDesignationNum().get().v() : null;
		this.postCode = domain.getCompileStation().getZipCode().isPresent()
				? domain.getCompileStation().getZipCode().get().v() : null;
		this.compileStationName = domain.getCompileStation().getName().isPresent()
				? domain.getCompileStation().getName().get().v() : null;
		this.memo = domain.getNote().isPresent() ? domain.getNote().get().v() : null;
		this.reportName = reportName;
	}

}
