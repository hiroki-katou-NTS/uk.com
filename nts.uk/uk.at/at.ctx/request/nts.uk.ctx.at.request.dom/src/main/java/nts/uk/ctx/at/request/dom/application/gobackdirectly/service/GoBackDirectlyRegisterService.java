package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;

public interface GoBackDirectlyRegisterService {
	
	public void createThrowMsg(String msgConfirm, List<String> msgLst);
	
	
	/**
	 * 
	 * @param companyId
	 * @param agentAtr
	 * @param application
	 * @param goBackDirectly
	 * @param inforGoBackCommonDirectOutput
	 * @param mode
	 * @return
	 */
	public List<ConfirmMsgOutput> checkBeforRegisterNew(String companyId, boolean agentAtr, Application application,  GoBackDirectly goBackDirectly, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput, boolean mode);
	/**
	 * Refactor4 
	 * UKDesign.UniversalK.就業.KAF_申請.KAF009_直行直帰の申請.A:直行直帰の申請（新規）.アルゴリズム.直行直帰するチェック
	 * 直行直帰するチェック
	 * @param goBackDirectly
	 * @return
	 */
	public GoBackDirectAtr goBackDirectCheckNew(GoBackDirectly goBackDirectly);
	
	/**refactor4 
	 * 直行直帰登録
	 * @param goBackDirectly
	 * @param application
	 * @return
	 */
	public ProcessResult register(GoBackDirectly goBackDirectly, Application application, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput); 
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF009_直行直帰の申請.A:直行直帰の申請（新規）.アルゴリズム.直行直帰登録
	 * @param companyId
	 * @param application
	 * @param goBackDirectly
	 * @param inforGoBackCommonDirectOutput
	 * @return
	 */
	public ProcessResult update(String companyId, Application application, GoBackDirectly goBackDirectly, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput); 
	
	
}
