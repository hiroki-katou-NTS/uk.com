package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TemporaryDispatchInformation {

	// 出向派遣者
	private boolean temporaryDispatcher;
	
	// 社員ID
	private String employeeId;
	
	// カナ住所１
	private String kanaAddress1;
	
	// カナ住所2
	private String kanaAddress2;
	
	// 住所１
	private String address1;
	
	// 住所2
	private String address2;
	
	// 出向兼務区分
	private String temporaryAssignment;
	
	// 出向兼務区分名
	private String temporaryAssignmentCategory;
	
	// 出向派遣区分
	private String temporaryDispatch;
	
	// 出向派遣区分名
	private String temporaryDispatchCategory;
	
	// 国
	private String country;
	
	// 国内海外区分
	private int classify;
	
	// 国内海外区分名
	private String nameClassify;
	
	// Expiration date
	private GeneralDate expirationDate;
	
	// 社員コード
	private String employeeCode;
	
	// 社員名
	private String employeeName;
	
	// 郵便番号
	private String postalCode;
	
	// 都道府県
	private String prefectures;
	
	// 電話番号
	private String phoneNumber;
	
	public TemporaryDispatchInformation(String employeeId) {
        this.employeeId = employeeId;
    }
}