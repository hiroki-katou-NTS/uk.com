package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Stateless
public class WorkChangeUpdateServiceImpl implements IWorkChangeUpdateService {

	@Inject
	private ApplicationRepository appRepository;
	

	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;

	@Inject
	private AppWorkChangeRepository workChangeRepository;


	@Inject
	private OtherCommonAlgorithm otherCommonAlg;

	@Override
	public ProcessResult updateWorkChange(String companyId, Application application, AppWorkChange workChange) {
		// ドメインモデル「勤務変更申請」の更新をする
		appRepository.update(application);

		workChangeRepository.update(workChange);

		// 年月日Listを作成する
		GeneralDate startDateParam = application.getOpAppStartDate().isPresent()
				? application.getOpAppStartDate().get().getApplicationDate()
				: application.getAppDate().getApplicationDate();
		GeneralDate endDateParam = application.getOpAppEndDate().isPresent()
				? application.getOpAppEndDate().get().getApplicationDate()
				: application.getAppDate().getApplicationDate();
		List<GeneralDate> listDate = new ArrayList<>();
		// 申請期間から休日の申請日を取得する
		List<GeneralDate> lstHoliday = otherCommonAlg.lstDateIsHoliday(companyId, application.getEmployeeID(),
				new DatePeriod(startDateParam, endDateParam));
		// 年月日Listを作成する
		for (GeneralDate loopDate = startDateParam; loopDate
				.beforeOrEquals(endDateParam); loopDate = loopDate.addDays(1)) {
			if (lstHoliday != null ) {
				if (!lstHoliday.contains(loopDate)) {
					listDate.add(loopDate);
				}
			} else {
				listDate.add(loopDate);
			}
		}
		// 暫定データの登録
//		interimRemainDataMngRegisterDateChange.registerDateChange(companyId, application.getEmployeeID(), listDate);

		// アルゴリズム「4-2.詳細画面登録後の処理」を実行する
		return detailAfterUpdate.processAfterDetailScreenRegistration(companyId, application.getAppID());
	}

}
