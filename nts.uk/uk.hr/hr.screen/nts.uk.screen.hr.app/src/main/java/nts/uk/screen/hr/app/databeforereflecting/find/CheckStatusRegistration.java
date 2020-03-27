package nts.uk.screen.hr.app.databeforereflecting.find;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.RetirementInformation;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.service.RetirementInformationService;
import nts.uk.shr.com.context.AppContexts;

/**
 * thuat toan : 登録状況チェック
 * Path : UKDesign.UniversalK.人事.JCM_異動・発令.JCM007_退職者の登録.A：退職者の登録.アルゴリズム.登録状況チェック
 * @author laitv
 *
 */

@Stateless
public class CheckStatusRegistration {
	
	@Inject
	private RetirementInformationService retirementInfoService;
	
	// 
	// アルゴリズム[登録状況チェック]を実行する(Thực hiện thuật toán "CHeck tình trạng đăng ký ")
	public Boolean CheckStatusRegistration(String sid){
		
		// アルゴリズム[退職登録済みチェック]を実行する(thực hiện thuật toán[check đã đăng ký nghỉ việc])
		//[input]
		//・会社ID = ログイン会社ID
	    //・社員ID = input. 社員ID
		//・基準日 = システム日付
		
		int checkResult = retirementInfoService.retirementRegisteredCheck(AppContexts.user().companyId(), sid, GeneralDate.today());
		
		if (checkResult == 9) {
			return true;
		}else if(checkResult == 2) {
			
			throw new BusinessException("MsgJ_JCM007_5"); 
			
		}else if(checkResult == 1) {
			
			throw new BusinessException("MsgJ_JCM007_1"); 
			
		}
		
		return false;
	}
}
