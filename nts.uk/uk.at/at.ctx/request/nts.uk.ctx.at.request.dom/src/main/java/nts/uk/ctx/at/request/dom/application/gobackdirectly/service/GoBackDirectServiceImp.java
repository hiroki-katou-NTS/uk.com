package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkGoBackDirectOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkTime;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkType;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayService;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeAndSiftType;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeService;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkTypeWorkTimeSelect;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflect;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflectRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

public class GoBackDirectServiceImp implements GoBackDirectService {

	@Inject
	private HolidayService holidayServiceDomain;

	@Inject
	private AppWorkChangeService appWorkChangeService;

	@Inject
	private OvertimeService overtimeService;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private GoBackReflectRepository goBackDirectServiceImp;
	
	@Inject
	private GoBackDirectlyRepository goBackDirectlyRepository;

	@Override
	public InforGoBackCommonDirectOutput getDataAlgorithm(String companyId, Optional<List<String>> sids,
			Optional<List<GeneralDate>> dates, AppDispInfoStartupOutput appDispInfoStartup) {
		InforGoBackCommonDirectOutput output =  new InforGoBackCommonDirectOutput();
		String sid = null;
		if (sids.isPresent()) {
			if (!sids.get().isEmpty()) {
				sid = sids.get().get(0);
			}
		}
		GeneralDate date = null;
		if (dates.isPresent()) {
			if (!dates.get().isEmpty()) {
				date = dates.get().get(0);
			}
		}
		GeneralDate baseDate = appDispInfoStartup.getAppDispInfoWithDateOutput().getBaseDate();

		AppEmploymentSet appEmployment = null;
		if (appDispInfoStartup.getAppDispInfoWithDateOutput().getOpEmploymentSet().isPresent()) {
			appEmployment = appDispInfoStartup.getAppDispInfoWithDateOutput().getOpEmploymentSet().get();
		}

		List<WorkTimeSetting> lstWts = null;
		if (appDispInfoStartup.getAppDispInfoWithDateOutput().getOpWorkTimeLst().isPresent()) {
			lstWts = appDispInfoStartup.getAppDispInfoWithDateOutput().getOpWorkTimeLst().get();
		}
		InforWorkGoBackDirectOutput inforWorkGoBackDirectOutput = this.getInfoWorkGoBackDirect(companyId, sid, date, baseDate, appEmployment, lstWts);
		
//		ドメインモデル「直行直帰申請の反映」より取得する 
		Optional<GoBackReflect> goBackReflectOp = goBackDirectServiceImp.findByCompany(companyId);
		if (goBackReflectOp.isPresent()) {
			output.setGoBackReflect(goBackReflectOp.get());
		}
		output.setWorkType(inforWorkGoBackDirectOutput.getWorkType());
		output.setWorkTime(inforWorkGoBackDirectOutput.getWorkTime());
		output.setLstWorkType(inforWorkGoBackDirectOutput.getLstWorkType());
		if (appDispInfoStartup.getAppDetailScreenInfo().isPresent()) {
			if (appDispInfoStartup.getAppDetailScreenInfo().get().getOutputMode() == OutputMode.EDITMODE) {
				//新規モード：ドメイン「直行直帰申請」がない。
				output.setGoBackDirectly(Optional.ofNullable(null));
			}
		}
		
		return output;
	}

	@Override
	public InforWorkGoBackDirectOutput getInfoWorkGoBackDirect(String companyId, String employeeId, GeneralDate appDate,
			GeneralDate baseDate, AppEmploymentSet appEmployment, List<WorkTimeSetting> lstWts) {
		InforWorkGoBackDirectOutput output = new InforWorkGoBackDirectOutput();
		// 起動時勤務種類リストを取得する
		List<WorkType> lstWorkType = this.getWorkTypes(companyId, appEmployment);

		// 09_勤務種類就業時間帯の初期選択をセットする
		// WorkTypeAndSiftType workTypeAndWorktimeSelect =
		// overtimeService.getWorkTypeAndSiftTypeByPersonCon(
		// companyId,
		// employeeId,
		// appDate,
		// lstWorkType,
		// siftTypes);
			// siftTypes is differed with lstWts		
		// WorkType wType = workTypeAndWorktimeSelect.getWorkType();
		// output.setWorkType(new InforWorkType(wType.getWorkTypeCode().v(),
		// wType.getName().v()));
		// WorkTimeSetting wTime = workTypeAndWorktimeSelect.getWorkTime();
		// output.setWorkTime(new InforWorkTime(wTime.getWorktimeCode().v(),
		// wTime.getWorkTimeDisplayName().getWorkTimeName().v()));
		
		output.setLstWorkType(lstWorkType);
		return output;

	}

	@Override
	public List<WorkType> getWorkTypes(String companyId, AppEmploymentSet appEmploymentSet) {
		// TODO Auto-generated method stub
		List<WorkType> result = workTypeRepository.findNotDeprecated(companyId);
		// sort 
		result = result.stream().sorted().collect(Collectors.toList());
		// INPUT．雇用別申請承認設定．申請別対象勤務種類をチェックする
		if (CollectionUtil.isEmpty(appEmploymentSet.getTargetWorkTypeByAppLst())) {
			return result;
		}
		// INPUT．雇用別申請承認設定．申請別対象勤務種類．勤務種類リストを取得する
		List<String> workTypeLst = appEmploymentSet.getTargetWorkTypeByAppLst().get(0).getWorkTypeLst();
		// ドメインモデル「勤務種類」を取得
		// filter
		return result.stream().filter(x -> x.getDeprecate() == DeprecateClassification.NotDeprecated
				&& workTypeLst.contains(x.getWorkTypeCode().v())).collect(Collectors.toList());
	}

}
