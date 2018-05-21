package nts.uk.ctx.bs.employee.app.find.employee.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContact;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpInfoContactDto extends PeregDomainDto{
	
	/** The Company mobile phone number - 会社携帯電話番号*/
	@PeregItem("IS00271")
	private String cellPhoneNo;

	/** The Company mail address - 会社メールアドレス */
	@PeregItem("IS00272")
	private String mailAddress;

	/** The Company mobile email address  - 会社携帯メールアドレス */
	@PeregItem("IS00273")
	private String phoneMailAddress;

	/** The  Seat Phone Number Dial In - 座席電話番号ダイヤルイ*/
	@PeregItem("IS00274")
	private String seatDialIn;

	/** The Seat phone number extension - 座席電話番号内線*/
	@PeregItem("IS00275")
	private String seatExtensionNo;
	
	public EmpInfoContactDto(String recordId, String cellPhoneNo,String mailAddress,String phoneMailAddress,String seatDialIn,String seatExtensionNo ) {
		super(recordId);
		this.cellPhoneNo = cellPhoneNo;
		this.mailAddress = mailAddress;
		this.phoneMailAddress = phoneMailAddress;
		this.seatDialIn = seatDialIn;
		this.seatExtensionNo = seatExtensionNo;
	}

	public static EmpInfoContactDto fromDomain(EmployeeInfoContact domain) {
		EmpInfoContactDto result = new EmpInfoContactDto();
		result.setRecordId(domain.getSid());
		if (domain.getCellPhoneNo().isPresent()) {
			result.setCellPhoneNo(domain.getCellPhoneNo().get().v());
		}
		if (domain.getMailAddress().isPresent()) {
			result.setMailAddress(domain.getMailAddress().get().v());
		}
		if (domain.getPhoneMailAddress().isPresent()) {
			result.setPhoneMailAddress(domain.getPhoneMailAddress().get().v());
		}
		if (domain.getSeatDialIn().isPresent()) {
			result.setSeatDialIn(domain.getSeatDialIn().get().v());
		}
		if (domain.getSeatExtensionNo().isPresent()) {
			result.setSeatExtensionNo(domain.getSeatExtensionNo().get().v());
		}
		return result;
	}

}
