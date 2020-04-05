package nts.uk.screen.hr.app.databeforereflecting.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.service.RetirementInformationService;
import nts.uk.screen.hr.app.databeforereflecting.command.DataBeforeReflectCommand;
import nts.uk.shr.com.context.AppContexts;

/**
 * thuat toan : 登録状況チェック
 * Path : UKDesign.UniversalK.人事.JCM_異動・発令.JCM007_退職者の登録.A：退職者の登録.アルゴリズム.登録状況チェック
 * @author laitv
 *
 */

@Stateless
public class CheckStatusRegistration extends CommandHandlerWithResult<String, Boolean> {
	
	@Inject
	private RetirementInformationService retirementInfoService;
	
	
	
	@Override
	protected Boolean handle(CommandHandlerContext<String> context) {
		String sid = context.getCommand();
		return this.checkStatusRegistration(sid);
	}
	
	// 
	// アルゴリズム[登録状況チェック]を実行する(Thực hiện thuật toán "CHeck tình trạng đăng ký ")
	public Boolean checkStatusRegistration(String sid){
		
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
