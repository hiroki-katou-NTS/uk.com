package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriodDto;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.1.承認すべき申請データの取得.承認すべき申請データ有無表示_（3次用）
 * 
 * @author tutt
 *
 */
@Stateless
public class AppDisplayAtrFinder {

	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;

	@Inject
	private ApplicationRepository applicationRepository_New;

	/**
	 * 承認すべき申請データ有無表示_（3次用）
	 * 
	 * @param periodImport
	 * @param employeeId
	 * @param companyId
	 * @return
	 */
	public Boolean getAppDisplayAtr(GetYearProcessAndPeriodDto periodImport, String employeeId, String companyId) {

		List<String> listApplicationID = approvalRootStateRepository.resultKTG002Mobile(
				periodImport.getClosureStartDate(), periodImport.getClosureEndDate(), employeeId, 0, companyId);

		// アルゴリズム「申請IDを使用して申請一覧を取得する」を実行する
		List<Application> listApplication = applicationRepository_New.findByListID(companyId, listApplicationID);

		// 「申請」．申請種類＝Input．申請種類 & 「申請」．実績反映状態<>差し戻し に該当する申請が存在するかチェックする
		List<Application> listApplicationFilter = listApplication.stream()
				.filter(c -> c.getAppReflectedState() != ReflectedState.REMAND).collect(Collectors.toList());

		return !listApplicationFilter.isEmpty();
	}

}
