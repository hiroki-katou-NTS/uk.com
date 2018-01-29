package nts.uk.ctx.bs.employee.dom.employeeinfo.contact;

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
	//社員ID
	private String sid;
	
	//メールアドレス
	private MailAddress mailAddress;
	
	//座席ダイヤルイン
	private SeatNo seatDialIn;
	
	//座席内線番号
	private SeatNo seatExtensionNo;
	
	//携帯メールアドレス
	private MailAddress phoneMailAddress;
	
	//携帯電話番号
	private CellPhoneNumber cellPhoneNo;
	
	
	public EmployeeInfoContact(String sid, String mailAddress, String seatDialIn,
			 String seatExtensionNo, String phoneMailAddress, String cellPhoneNo){
		this.sid = sid;
		this.mailAddress = new MailAddress(mailAddress);
		this.seatDialIn = new SeatNo(seatDialIn);
		this.seatExtensionNo = new SeatNo(seatExtensionNo);
		this.phoneMailAddress = new MailAddress(phoneMailAddress);
		this.cellPhoneNo = new CellPhoneNumber(cellPhoneNo);
	}
	
	public static EmployeeInfoContact createFromJavaType(String sid, String mailAddress, String seatDialIn,
			 String seatExtensionNo, String phoneMailAddress, String cellPhoneNo){
	 return new EmployeeInfoContact(sid, mailAddress, seatDialIn, seatExtensionNo, phoneMailAddress, cellPhoneNo);
	}
	
}
