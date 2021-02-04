package nts.uk.ctx.bs.employee.pub.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeContactObject {

	//社員ID
	private String sid;
	
	//メールアドレス
	private String mailAddress;

    // メールアドレスが在席照会に表示するか
    private boolean isMailAddressDisplay; 
	
	//座席ダイヤルイン
	private String seatDialIn;
	
	//座席ダイヤルインが在席照会に表示するか
    private boolean isSeatDialInDisplay;
	
	//座席内線番号
	private String seatExtensionNo;
	
	//座席内線番号が在席照会に表示するか
    private boolean isSeatExtensionNumberDisplay;
	
	//携帯メールアドレス
	private String phoneMailAddress;
	
	//携帯メールアドレスが在席照会に表示するか
    private boolean isMobileMailAddressDisplay;
	
	//携帯電話番号
	private String cellPhoneNo;
	
	//携帯電話番号が在席照会に表示するか
    private boolean isCellPhoneNumberDisplay;
}
