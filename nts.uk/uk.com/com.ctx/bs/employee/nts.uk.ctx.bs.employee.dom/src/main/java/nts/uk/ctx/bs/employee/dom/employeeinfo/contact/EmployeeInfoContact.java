package nts.uk.ctx.bs.employee.dom.employeeinfo.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.contact.MailAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.AddressSet1;
import nts.uk.ctx.bs.person.dom.person.currentaddress.AddressSet2;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.HomeSituationType;
import nts.uk.ctx.bs.person.dom.person.currentaddress.HouseRent;
import nts.uk.ctx.bs.person.dom.person.currentaddress.HouseType;
import nts.uk.ctx.bs.person.dom.person.currentaddress.NearestStation;
import nts.uk.ctx.bs.person.dom.person.currentaddress.PersonAddress1;
import nts.uk.ctx.bs.person.dom.person.currentaddress.PersonAddress2;
import nts.uk.ctx.bs.person.dom.person.currentaddress.PersonAddressKana1;
import nts.uk.ctx.bs.person.dom.person.currentaddress.PersonAddressKana2;
import nts.uk.ctx.bs.person.dom.person.currentaddress.PostalCode;
import nts.uk.ctx.bs.person.dom.person.currentaddress.Prefectures;
import nts.uk.ctx.bs.person.dom.person.info.PersonMailAddress;
import nts.uk.ctx.bs.person.dom.person.info.PersonMobile;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The class EmployeeInfoContact 社員連絡先
 * 
 * @author lanlt
 *
 */
@AllArgsConstructor
@Getter
public class EmployeeInfoContact {
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
