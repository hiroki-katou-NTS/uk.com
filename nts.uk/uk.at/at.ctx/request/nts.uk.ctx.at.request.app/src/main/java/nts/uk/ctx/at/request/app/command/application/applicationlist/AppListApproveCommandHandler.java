package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.command.application.common.ApproveAppHandler;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ApprovalDevice;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplyActionContent;
import nts.uk.ctx.at.request.dom.application.applist.service.ApprovalListService;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.WorkMotionData;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApproveProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧承認登録ver4.申請一覧承認登録ver4
 * @author Doan Duy Hung
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AppListApproveCommandHandler extends CommandHandlerWithResult<AppListApproveCommand, AppListApproveResult>{
	
	
	@Inject
	private ApproveAppHandler approveAppHandler;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private ManagedParallelWithContext parallel;
	
	@Inject
	private ApprovalListService approvalListService;
	
	@Inject
	private ApprovalListDispSetRepository approvalListDispSetRepository;
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧承認登録ver4.申請一覧承認登録ver4
	 */
	@Override
	protected AppListApproveResult handle(CommandHandlerContext<AppListApproveCommand> context) {
		String companyID = AppContexts.user().companyId();
		AppListApproveResult result = new AppListApproveResult(new HashMap<String, String>(), new HashMap<String, String>());
		AppListApproveCommand command = context.getCommand();
		List<ListOfApplicationCmd> listOfApplicationCmds = command.getListOfApplicationCmds();
//		List<ListOfAppTypes> listOfAppTypes =  command.getListOfAppTypes().stream().map(x -> x.toDomain()).collect(Collectors.toList());
		// ドメインモデル「承認一覧表示設定」を取得する (Lấy domain Approval List display Setting)
		ApprovalListDisplaySetting approvalListDisplaySetting = approvalListDispSetRepository.findByCID(companyID).get();
		// 承認一覧表示設定より作業動作データをセット (set dữ liệu chạy công việc từ 承認一覧表示設定)
		WorkMotionData workMotionData = new WorkMotionData();
		if(approvalListDisplaySetting.getAdvanceExcessMessDisAtr()==DisplayAtr.DISPLAY) {
			workMotionData.setPreAppConfirm(ApplyActionContent.CONFIRM);
		} else {
			workMotionData.setPreAppConfirm(ApplyActionContent.NOT_CONFIRM);
		}
		if(approvalListDisplaySetting.getActualExcessMessDisAtr()==DisplayAtr.DISPLAY) {
			workMotionData.setPreAppConfirm(ApplyActionContent.CONFIRM);
		} else {
			workMotionData.setPreAppConfirm(ApplyActionContent.NOT_CONFIRM);
		}
		// 表示されている申請一覧リストの1行目より最終行まで繰返し (Vong lap tu don day tien den don cuoi cung dang hien thi tren man hinh)
		this.parallel.forEach(listOfApplicationCmds, listOfApplicationCmd -> {
			// 対象の申請が未承認の申請の場合
			// xử lý trên UI
			// INPUT「一括承認」＝True
			// xử lý trên UI
			// デバイス＝スマホ
			if(command.getDevice()==ApprovalDevice.PC.value) {
				// アルゴリズム「申請一覧承認登録チェックver4」を実行する　-　15
				boolean error = approvalListService.checkErrorComfirm(
						approvalListDisplaySetting, 
						workMotionData, 
						EnumAdaptor.valueOf(listOfApplicationCmd.getAppType(), ApplicationType.class));
				if(error) {
					result.getSuccessMap().put(listOfApplicationCmd.getAppID(), "");
				} else {
					result.getSuccessMap().put(listOfApplicationCmd.getAppID(), "");
				}
				return;
			}
			// アルゴリズム「申請一覧の承認登録」を実行する
			Pair<Boolean, String> pair = this.approveSingleApp(companyID, listOfApplicationCmd, Collections.emptyList());
			if(pair.getLeft()) {
				result.getSuccessMap().put(listOfApplicationCmd.getAppID(), pair.getRight());
			} else {
				result.getFailMap().put(listOfApplicationCmd.getAppID(), pair.getRight());
			}
		});
		return result;
	}
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧の承認登録.申請一覧の承認登録
	 * @param companyID
	 * @param listOfApplicationCmd
	 * @return
	 */
	public Pair<Boolean, String> approveSingleApp(String companyID, ListOfApplicationCmd listOfApplicationCmd, List<ListOfAppTypes> listOfAppTypes) {
		try {
			Application application = listOfApplicationCmd.toDomain().getApplication();
			// ドメインモデル「申請設定」を取得し申請表示情報として作成する
			AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(
					companyID, 
					application.getAppType(), 
					Collections.emptyList(), 
					new DatePeriod(application.getOpAppStartDate().get().getApplicationDate(), application.getOpAppEndDate().get().getApplicationDate()).datesBetween(), 
					true,
					Optional.empty(),
					Optional.empty());
			// アルゴリズム「承認する」を実行する
			ApproveProcessResult approveProcessResult = approveAppHandler.approve(companyID, application.getAppID(), application, appDispInfoStartupOutput, 
					"", listOfAppTypes);
			if(approveProcessResult.isProcessDone()) {
				return Pair.of(true, "");
			} else {
				return Pair.of(false, "");
			}
		} catch (Exception e) {
			return Pair.of(false, e.getMessage());
		}
	}
	
	public AppListApproveResult approverAfterConfirm(List<ListOfApplicationCmd> listOfApplicationCmds, List<ListOfAppTypes> listOfAppTypes) {
		String companyID = AppContexts.user().companyId();
		AppListApproveResult result = new AppListApproveResult(new HashMap<String, String>(), new HashMap<String, String>());
		this.parallel.forEach(listOfApplicationCmds, listOfApplicationCmd -> {
			Pair<Boolean, String> pair = this.approveSingleApp(companyID, listOfApplicationCmd, listOfAppTypes);
			if(pair.getLeft()) {
				result.getSuccessMap().put(listOfApplicationCmd.getAppID(), pair.getRight());
			} else {
				result.getFailMap().put(listOfApplicationCmd.getAppID(), pair.getRight());
			}
		});
		return result;
	}

}
