/**
 * 
 */
package nts.uk.screen.hr.app.databeforereflecting.find;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class ProcessWhenRetirementDateChanges {
	
	public RetirementRelatedInfo ProcessWhenRetirementDateChanges(String sid, GeneralDate retirementDate, Integer retirementType ){
		
		String cid = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
		
		
		// 退職日が空白(ResignmentDate = empty)
		if (retirementDate == null) {
			return null;
		}
		
		// アルゴリズム[退職関連情報の取得]を実行する(Thực hiện thuật toán [lấy thông tin liên quan đến nghỉ hưu])
		
		
		
		return null;
		
		
	}
	
	// アルゴリズム[退職関連情報の取得]を実行する(Thực hiện thuật toán [lấy thông tin liên quan đến nghỉ hưu])
	public RetirementRelatedInfo getRetirementRelatedInfo(String cid, GeneralDate baseDate, String sid, GeneralDate retirementDate, Integer retirementType ){
		
		// アルゴリズム [基準日で退職・解雇の就業規則の取得] を実行する
		// (Thực hiện thuật toán [lấy quy tắc công việc của Resignment/Dimissal bằng BaseDate])
		
		
		
		
		
		return null;
	}

}
