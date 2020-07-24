package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.DataWork;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkGoBackDirectOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkTime;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.TargetWorkTypeByApp;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflect;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflectRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
@Stateless
public class GoBackDirectServiceImp implements GoBackDirectService {

	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private GoBackReflectRepository goBackDirectServiceImp;
	

	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private GoBackDirectlyRepository goBackDirectlyRepository;
	
	@Override
	public InforGoBackCommonDirectOutput getDataAlgorithm(String companyId, Optional<List<GeneralDate>> dates ,
			Optional<String> sids, AppDispInfoStartupOutput appDispInfoStartup) {
		InforGoBackCommonDirectOutput output =  new InforGoBackCommonDirectOutput();
		String sid = null;
		if (sids.isPresent()) {
			sid = sids.get();
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
				// get Object
				output.setGoBackDirectly(goBackDirectlyRepository.find(companyId, appDispInfoStartup.getAppDetailScreenInfo().get().getApplication().getAppID()));
			}
		}
		output.setAppDispInfoStartup(appDispInfoStartup);
		return output;
	}

	@Override
	public InforWorkGoBackDirectOutput getInfoWorkGoBackDirect(String companyId, String employeeId, GeneralDate appDate,
			GeneralDate baseDate, AppEmploymentSet appEmployment, List<WorkTimeSetting> lstWts) {
		InforWorkGoBackDirectOutput output = new InforWorkGoBackDirectOutput();
		// 起動時勤務種類リストを取得する
		List<WorkType> lstWorkType = this.getWorkTypes(companyId, appEmployment);

		// 09_勤務種類就業時間帯の初期選択をセットする
//		 WorkTypeAndSiftType workTypeAndWorktimeSelect =
//		 overtimeService.getWorkTypeAndSiftTypeByPersonCon(
//		 companyId,
//		 employeeId,
//		 appDate,
//		 lstWorkType,
//		 null);
			// siftTypes is differed with lstWts		
		// WorkType wType = workTypeAndWorktimeSelect.getWorkType();
		// output.setWorkType(new InforWorkType(wType.getWorkTypeCode().v(),
		// wType.getName().v()));
		// WorkTimeSetting wTime = workTypeAndWorktimeSelect.getWorkTime();
		// output.setWorkTime(new InforWorkTime(wTime.getWorktimeCode().v(),
		// wTime.getWorkTimeDisplayName().getWorkTimeName().v()));
		InforWorkType inforWorkType = new InforWorkType("001", "abc");
		InforWorkTime inforWorkTime = new InforWorkTime("001", "def");
		output.setWorkType(inforWorkType);
		output.setWorkTime(inforWorkTime);
		output.setLstWorkType(lstWorkType);
		return output;

	}

	@Override
	public List<WorkType> getWorkTypes(String companyId, AppEmploymentSet appEmploymentSet) {
		
		List<WorkType> result = workTypeRepository.findNotDeprecated(companyId);
		// sort 
//		result = result.stream().sorted().collect(Collectors.toList());
		
		if (appEmploymentSet == null) {
			return result;
		}
		// INPUT．雇用別申請承認設定．申請別対象勤務種類をチェックする
		if (CollectionUtil.isEmpty(appEmploymentSet.getTargetWorkTypeByAppLst())) {
			return result;
		}
		// INPUT．雇用別申請承認設定．申請別対象勤務種類．勤務種類リストを取得する
		Optional<TargetWorkTypeByApp> tarOp = appEmploymentSet.getTargetWorkTypeByAppLst().stream().filter(y -> y.getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION).findFirst();
		List<String> workTypeLst = Collections.emptyList();
		if (tarOp.isPresent()) {
			workTypeLst = tarOp.get().getWorkTypeLst();
		}
		final List<String> listWorkType = workTypeLst;
		// ドメインモデル「勤務種類」を取得
		// filter
		if (CollectionUtil.isEmpty(workTypeLst)) {
			return Collections.emptyList();
		}else {
			result =  result.stream().filter(x -> x.getDeprecate() == DeprecateClassification.NotDeprecated && listWorkType.contains(x.getWorkTypeCode().v())
					 ).collect(Collectors.toList());
			
			return result;
					
		}
	}

	@Override
	public InforGoBackCommonDirectOutput getDateChangeAlgorithm(String companyId, List<GeneralDate> dates,
			List<String> sids, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput) {
		// 申請日を変更する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.changeAppDateProcess(companyId, dates,
				ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput(),
				inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput());
		inforGoBackCommonDirectOutput.getAppDispInfoStartup().setAppDispInfoWithDateOutput(appDispInfoWithDateOutput);
		InforWorkGoBackDirectOutput inforWorkGoBackDirectOutput = this.getInfoWorkGoBackDirect(companyId, sids.get(0), dates.get(0),
				inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getBaseDate(),
				inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpEmploymentSet().isPresent()
						? inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpEmploymentSet().get()
						: null,
						inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst().isPresent()
						? inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst().get()
						: null);
		
		inforGoBackCommonDirectOutput.setWorkType(inforWorkGoBackDirectOutput.getWorkType());
		inforGoBackCommonDirectOutput.setWorkTime(inforWorkGoBackDirectOutput.getWorkTime());
		inforGoBackCommonDirectOutput.setLstWorkType(inforWorkGoBackDirectOutput.getLstWorkType());
		if (inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDetailScreenInfo().isPresent()) {
			if (inforGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDetailScreenInfo().get().getOutputMode() == OutputMode.EDITMODE) {
				//新規モード：ドメイン「直行直帰申請」がない。
				inforGoBackCommonDirectOutput.setGoBackDirectly(Optional.ofNullable(null));
			}
		}
		return inforGoBackCommonDirectOutput;
	}

	@Override
	public InforGoBackCommonDirectOutput getDataDetailAlgorithm(String companyId, String appId, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		InforGoBackCommonDirectOutput output = new InforGoBackCommonDirectOutput();
		// AppDispInfoStartupOutput appDispInfoStartupOutput = null;
		// appCommonSetService.getCommonSetBeforeDetail(companyId, appId);
		// appDispInfoStartupOutput
		// ドメインモデル「直行直帰申請の反映」より取得する
		Optional<GoBackReflect> goBackReflect = goBackDirectServiceImp.findByCompany(companyId);
		if (goBackReflect.isPresent()) {
			output.setGoBackReflect(goBackReflect.get());
		}
		
//		ドメインモデル「直行直帰申請」を取得する
		Optional<GoBackDirectly> goBackDirectly = goBackDirectlyRepository.find(companyId, appId);
		output.setGoBackDirectly(goBackDirectly);
//		起動時勤務種類リストを取得する
		List<WorkType> lstWorkType = this.getWorkTypes(companyId, appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpEmploymentSet().isPresent() ? appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpEmploymentSet().get() : null);
		output.setLstWorkType(lstWorkType);
		
		if (goBackDirectly.isPresent()) {
			Optional<DataWork> dataWork = goBackDirectly.get().getDataWork();
			if (dataWork.isPresent()) {
//				直行直帰申請起動時の表示情報．勤務種類初期選択=取得したドメインモデル「直行直帰申請」．勤務情報．勤務種類 
				output.setWorkType(new InforWorkType(dataWork.get().getWorkType().getWorkType(), dataWork.get().getWorkType().getNameWorkType()));
//				直行直帰申請起動時の表示情報．就業時間帯初期選択=取得したドメインモデル「直行直帰申請」．勤務情報．就業時間帯
				if (dataWork.get().getWorkTime().isPresent()) {
					InforWorkTime inforWorkTime = dataWork.get().getWorkTime().get();
					output.setWorkTime(new InforWorkTime(inforWorkTime.getWorkTime(), inforWorkTime.getNameWorkTime()));
				}
			}
		}
		
		return output;
	}

}
