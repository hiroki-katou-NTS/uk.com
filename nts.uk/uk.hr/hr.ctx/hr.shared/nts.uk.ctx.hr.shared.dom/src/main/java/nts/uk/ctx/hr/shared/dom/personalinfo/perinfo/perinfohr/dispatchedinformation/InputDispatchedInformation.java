package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputDispatchedInformation {

	// 契約コード
	String contractCode;
	
	// 会社ID
	String companyId;
	
	// 基準日
	GeneralDate baseDate;
	
	// 社員コードを取得する
	boolean employeeCode;
	
	//社員名を取得する
	boolean employeeName;
	
	// 満了予定日を取得する
	boolean expirationDate;
	
	// 選択マスタの名称を取得する
	boolean nameSelectedMaster;
	
	// 国内海外区分を取得する
	boolean classification1;
	
	// 出向派遣区分を取得する
	boolean classification2;
	
	// 出向兼務区分を取得する
	boolean classification3;
	
	// 出向先会社名を取得する
	boolean nameCompany;
	
	// 出向先住所を取得する
	boolean address;
	
	// カナ住所を取得する
	boolean addressKana;
	
	// 出向兼務者を含める
	boolean include = true;
	
	// List<社員ID>
	List<String> employeeIds = new ArrayList<>();
}