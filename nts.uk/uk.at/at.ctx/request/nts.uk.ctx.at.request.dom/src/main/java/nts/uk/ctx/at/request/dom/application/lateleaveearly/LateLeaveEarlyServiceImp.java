package nts.uk.ctx.at.request.dom.application.lateleaveearly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 *
 */
@Stateless
public class LateLeaveEarlyServiceImp implements LateLeaveEarlyService {

	@Inject
	private DetailAppCommonSetService detailAppCommonSetImpl;

	@Inject
	private LateEarlyCancelAppSetRepository cancelAppSetRepository;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService#
	 * getLateLeaveEarlyInfo(java.lang.String)
	 */
	@Override
	public ArrivedLateLeaveEarlyInfoOutput getLateLeaveEarlyInfo(String appId) {
		String companyId = AppContexts.user().companyId();


		List<String>sIds = new ArrayList<String>();
		sIds.add(AppContexts.user().employeeId());

		List<Optional<GeneralDate>> appDates = new ArrayList<Optional<GeneralDate>>();
		Optional<GeneralDate> today = Optional.of(GeneralDate.fromString(GeneralDate.today().toString(), "yyyy/MM/dd"));
		appDates.add(today);

		// 起動時の申請表示情報を取得する
//		AppDispInfoStartupOutput_Old appDispInfoStartupOutput = detailAppCommonSetImpl
//				.getCommonSetBeforeDetail(companyId, appId);
		AppDispInfoStartupOutput appDispInfoStartupOutput = new AppDispInfoStartupOutput(null, null);

		// 遅刻早退取消初期（新規）
		ArrivedLateLeaveEarlyInfoOutput displayInfo = this.initCancelLateEarlyApp(companyId, sIds, appDates,
				appDispInfoStartupOutput);

		return displayInfo;
	}

	/**
	 * 遅刻早退取消初期（新規）
	 *
	 * @param companyId
	 * @param employmentLst
	 * @param appDates
	 * @param appDisplayInfo
	 * @return
	 */
	private ArrivedLateLeaveEarlyInfoOutput initCancelLateEarlyApp(
			String companyId,
			List<String> employmentLst,
			List<Optional<GeneralDate>> appDates,
			AppDispInfoStartupOutput appDisplayInfo
			) {
		ArrivedLateLeaveEarlyInfoOutput displayInfo = new ArrivedLateLeaveEarlyInfoOutput();
		Optional<GeneralDate> appDate;

		// ドメインモデル「遅刻早退取消申請設定」を取得する
		LateEarlyCancelAppSet listAppSet = this.cancelAppSetRepository.getByCId(companyId);

		// 遅刻早退実績のチェック処理
		if (!appDisplayInfo.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent()
				|| !displayInfo.getInfo().isPresent()) {

		} else {

		}


		if (appDates.isEmpty()) {
			appDate = Optional.empty();
		} else {
			appDate = appDates.get(0);
		}

		return displayInfo;
	}

	/**
	 * 遅刻早退実績のチェック処理
	 *
	 * @param lateEarlyActualResults
	 * @param appDate
	 * @return
	 */
	private Optional<String> checkLateEarlyResult(Optional<String> lateEarlyActualResults, String appDate) {

		return null;
	}
}
