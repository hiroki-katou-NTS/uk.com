package nts.uk.ctx.bs.employee.dom.employee.contact;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.contact.MailAddress;

/**
 * The class EmployeeInfoContact 社員連絡先
 * 
 * @author lanlt
 *
 */
@AllArgsConstructor
@Getter
public class EmployeeInfoContact extends AggregateRoot{
	
	private String cId;
	//社員ID
	private String sid;
	
	//メールアドレス
	private Optional<MailAddress> mailAddress;
	
	//座席ダイヤルイン
	private Optional<SeatNo> seatDialIn;
	
	//座席内線番号
	private Optional<SeatNo> seatExtensionNo;
	
	//携帯メールアドレス
	private Optional<MailAddress> phoneMailAddress;
	
	//携帯電話番号
	private Optional<CellPhoneNumber> cellPhoneNo;
	
	
	public EmployeeInfoContact(String cid, String sid, String mailAddress, String seatDialIn,
			 String seatExtensionNo, String phoneMailAddress, String cellPhoneNo){
		this.cId = cid;
		this.sid = sid;
		
		//メールアドレス
		if (mailAddress == null || mailAddress.isEmpty()) {
			this.mailAddress = Optional.empty();
		} else {
			this.mailAddress = Optional.of(new MailAddress(mailAddress));
		}
		
		//座席ダイヤルイン
		if (seatDialIn == null || seatDialIn.isEmpty()) {
			this.seatDialIn = Optional.empty();
		} else {
			this.seatDialIn = Optional.of(new SeatNo(seatDialIn));
		}
		
		//座席内線番号
		if (seatExtensionNo == null || seatExtensionNo.isEmpty()) {
			this.seatExtensionNo = Optional.empty();
		} else {
			this.seatExtensionNo = Optional.of(new SeatNo(seatExtensionNo));
		}
		
		//携帯メールアドレス
		if (phoneMailAddress == null || phoneMailAddress.isEmpty()) {
			this.phoneMailAddress = Optional.empty();
		} else {
			this.phoneMailAddress = Optional.of(new MailAddress(phoneMailAddress));
		}
		
		//携帯電話番号
		if (cellPhoneNo == null || cellPhoneNo.isEmpty()) {
			this.cellPhoneNo = Optional.empty();
		} else {
			this.cellPhoneNo = Optional.of(new CellPhoneNumber(cellPhoneNo));
		}
	}
	
	public static EmployeeInfoContact createFromJavaType(String cid, String sid, String mailAddress, String seatDialIn,
			String seatExtensionNo, String phoneMailAddress, String cellPhoneNo) {
		return new EmployeeInfoContact(cid, sid, mailAddress, seatDialIn, seatExtensionNo, phoneMailAddress,
				cellPhoneNo);
	}
	
}
