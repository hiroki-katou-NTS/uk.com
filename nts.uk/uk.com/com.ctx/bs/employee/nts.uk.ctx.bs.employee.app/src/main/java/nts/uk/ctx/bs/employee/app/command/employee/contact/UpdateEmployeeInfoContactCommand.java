package nts.uk.ctx.bs.employee.app.command.employee.contact;

import lombok.Getter;
import nts.uk.ctx.bs.employee.dom.employee.contact.CellPhoneNumber;
import nts.uk.ctx.bs.employee.dom.employee.contact.SeatNo;
import nts.uk.ctx.bs.person.dom.person.contact.MailAddress;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class UpdateEmployeeInfoContactCommand {
	//社員ID
	@PeregRecordId
	private String sid;
	
	//メールアドレス
	@PeregItem("IS00272")
	private MailAddress mailAddress;
	
	//座席ダイヤルイン
	@PeregItem("IS00274")
	private SeatNo seatDialIn;
	
	//座席内線番号
	@PeregItem("IS00275")
	private SeatNo seatExtensionNo;
	
	//携帯メールアドレス
	@PeregItem("IS00273")
	private MailAddress phoneMailAddress;
	
	//携帯電話番号
	@PeregItem("IS00271")
	private CellPhoneNumber cellPhoneNo;
}
