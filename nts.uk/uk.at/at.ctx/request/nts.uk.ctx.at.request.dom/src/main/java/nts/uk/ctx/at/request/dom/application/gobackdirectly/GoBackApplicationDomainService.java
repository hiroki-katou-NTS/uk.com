package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayService;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeService;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkTypeWorkTimeSelect;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;


@Stateless
public class GoBackApplicationDomainService {
//	@Inject
//	private GoBackApplicationRepo goBackApplicationRepo;
	@Inject 
	private HolidayService holidayService;
	@Inject
	private AppWorkChangeService appWorkChangeService;
	@Inject
	private GoBackDirectlyCommonSettingRepository backDirectlyCommonSettingRepository;

//	public GoBackAplication getDomain() {
//		return goBackApplicationRepo.getDomain();
//	}
	/**
	 * アルゴリズム「直行直帰画面初期（新規）」を実行する
	 * @param companyID
	 * @param sIDs
	 * @param appDates
	 * @param appDispInfoStartupOutput
	 * @return
	 */
	public InforGoBackCommonDirectOutput getInfoOutput(String companyId, List<String> sIDs, List<String> appDates,
			AppDispInfoStartupOutput appDispInfoStartupOutput) {
//		InforGoBackCommonDirectOutput output = new InforGoBackCommonDirectOutput();
//		List<GeneralDate> lstDate = new ArrayList<>();
//		if (!CollectionUtil.isEmpty(appDates)) {
//			lstDate.addAll(appDates.stream().map(item -> GeneralDate.fromString(item, "yyyy/MM/dd"))
//					.collect(Collectors.toList()));
//		}
//		String employeeId = CollectionUtil.isEmpty(sIDs) ? "" : sIDs.get(0);
//		GeneralDate appDate = CollectionUtil.isEmpty(lstDate) ? null : lstDate.get(0);
//		GeneralDate baseDate = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate();
////		AppDispInfoStartupOutput appDispInfoStartupOutputTemp = appDispInfoStartupOutput;
//		AppEmploymentSetting appEmployment = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmploymentSet();
//		List<WorkTimeSetting> lstWts = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getWorkTimeLst();
////		直行直帰申請起動時初期データを取得する
//		InforGoBackDirectOutput inforGoBackDirectOutput = this.getInforGoBackDirect(companyId, employeeId, appDate, baseDate, appEmployment, lstWts);
//		
//		output.setLstWorkType(inforGoBackDirectOutput.getLstWorkType());
//		output.setWorkType(inforGoBackDirectOutput.getWorkType());
//		output.setWorkTime(inforGoBackDirectOutput.getWorkTime());
//		// エラー情報をチェックする(Check ErrorInfo)
//		if (appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getErrorFlag() != ErrorFlagImport.NO_ERROR) {
//			// handle error message
//		} else {
//			// ドメインモデル「直行直帰申請共通設定」より取得する
//			// change  old reflect domain ->
//			Optional<GoBackDirectlyCommonSetting> gbdcs = backDirectlyCommonSettingRepository.findByCompanyID(companyId);
//			if (gbdcs.isPresent()) {
//				output.setGobackDirectCommon(gbdcs.get());
//			}
//			output.setAppDispInfoStartup(appDispInfoStartupOutput);
//		}
//
//		//new mode GoBack = null
//		// new Domain GoBackApplication
//		int mode = 0;
//		Optional<GoBackDirectly> gbdOptional = Optional.ofNullable(null);
//		if (mode == 1) {
//			// get domain
//		}
//		output.setGoBackDirectly(gbdOptional);
		
		return null;
	}
	public InforWorkGoBackDirectOutput getInforGoBackDirect(String companyId, String employeeId, GeneralDate appDate, GeneralDate baseDate,
			AppEmploymentSetting appEmployment, List<WorkTimeSetting> lstWts) {
		InforWorkGoBackDirectOutput output = new InforWorkGoBackDirectOutput();
		// 起動時勤務種類リストを取得する
		List<WorkType> lstWorkType = holidayService.getWorkTypeLstStart(companyId, appEmployment);

		// 09_勤務種類就業時間帯の初期選択をセットする
		WorkTypeWorkTimeSelect workTypeAndWorktimeSelect = appWorkChangeService.initWorkTypeWorkTime(companyId,
				employeeId, baseDate, lstWorkType, lstWts);

		// set output
		output.setLstWorkType(lstWorkType);
		WorkType wType = workTypeAndWorktimeSelect.getWorkType();
//		output.setWorkType(new InforWorkType(wType.getWorkTypeCode().v(), wType.getName().v()));
//		WorkTimeSetting wTime = workTypeAndWorktimeSelect.getWorkTime();
//		output.setWorkTime(
//				new InforWorkTime(wTime.getWorktimeCode().v(), wTime.getWorkTimeDisplayName().getWorkTimeName().v()));
		return output;
	}
}
