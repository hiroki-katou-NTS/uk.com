package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.createperapprovalmonthly;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.createdailyapprover.AppRootInsContentFnImport;
import nts.uk.ctx.at.record.dom.adapter.createdailyapprover.CreateDailyApproverAdapter;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.ErrorMessageRC;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthly;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppInterrupMon;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppInterrupMonRepository;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class CreateperApprovalMonthlyDefault implements CreateperApprovalMonthlyService {
	
	@Inject 
	private AppDataInfoMonthlyRepository appDataInfoMonthlyRepo;
	
	@Inject
	private CreateDailyApproverAdapter createDailyApproverAdapter;

	@Inject
	private ManagedParallelWithContext parallel;
	
	@Inject
	private AppInterrupMonRepository appInterrupMonRepository;
	@Override
	public OutputCreatePerApprovalMon createperApprovalMonthly(String companyId, String executionId, List<String> employeeIDs,
			int processExecType, GeneralDate endDateClosure) {
			AtomicBoolean checkStop = new AtomicBoolean(false);
			/**パラメータ.社員ID（List）の数だけループする*/
			this.parallel.forEach(employeeIDs, employeeID -> {
				if(checkStop.get()) return;
				/**アルゴリズム「指定社員の中間データを作成する」を実行する*/
				AppRootInsContentFnImport appRootInsContentFnImport =  createDailyApproverAdapter.createDailyApprover(employeeID, 2, GeneralDate.today(),endDateClosure);
				if(checkStop.get()) return;
				boolean flagError = appRootInsContentFnImport.getErrorFlag().intValue()==0?false:true;
				String errorMessage = appRootInsContentFnImport.getErrorMsgID();
				//取得したエラーフラグ != エラーなし
				if(flagError) {
					/**ドメインモデル「承認中間データエラーメッセージ情報（月別実績）」を追加する*/
					AppDataInfoMonthly appDataInfoMonthly = new AppDataInfoMonthly(employeeID, executionId, new ErrorMessageRC(TextResource.localize(errorMessage)));
					appDataInfoMonthlyRepo.addAppDataInfoMonthly(appDataInfoMonthly);
				}
				if(checkStop.get()) return;
				//ドメインモデル「承認中間データ中断管理（月別実績）」を取得する
				Optional<AppInterrupMon> appInterrupMon =  appInterrupMonRepository.getAppInterrupMonByID(executionId);
				if(appInterrupMon.isPresent() && appInterrupMon.get().isSuspendedState()) {
					checkStop.set(true);
					return;
				}
			});//end for listEmployee
			if(checkStop.get()) {
				return new OutputCreatePerApprovalMon(false,true);
			}
			/**ドメインモデル「承認中間データエラーメッセージ情報（月別実績）」を取得する*/
			List<AppDataInfoMonthly> listAppDataInfoMonthly = appDataInfoMonthlyRepo.getAppDataInfoMonthlyByExeID(executionId);
			if(!listAppDataInfoMonthly.isEmpty()) {//取得できた場合
//				return true;
				return new OutputCreatePerApprovalMon(true,false);
			}else {
//				return false;
				return new OutputCreatePerApprovalMon(false,false);
			}
			
	}



}
