package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.WorkLocationAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.StampRecordOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TimePlaceOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TrackRecordAtr;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.ErrorStampInfo;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSetting;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.StampAppReflectRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.MaximumNumberOfSupport;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageRepository;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppCommonDomainServiceImp implements AppCommonDomainService{
	
	@Inject
	private AppStampSettingRepository appStampSetttingRepo;
	
	@Inject
	private TemporaryWorkUseManageRepository temporaryWorkUseManageRepo;
	
	@Inject
	private StampAppReflectRepository appStampReflectRepo;
	
	@Inject
	private NewBeforeRegister registerBefore;
	
	@Inject
	private AppStampRepository appStampRepo;
	
	@Inject
	private AppRecordImageRepository appRecordImageRepo;
	
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	
	@Inject
	private SupportOperationSettingRepository supportOperationSettingRepo;
	
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	
	@Inject
	private WorkLocationAdapter workLocationAdapter;
	
	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Override
	public AppStampOutput getDataCommon(String companyId, Optional<GeneralDate> dates,
			AppDispInfoStartupOutput appDispInfoStartupOutput, Boolean recoderFlag) {
		AppStampOutput appStampOutput = new AppStampOutput();
		
		appStampOutput.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		
//		ドメインモデル「打刻申請設定」を取得する	
		Optional<AppStampSetting> appStampSettingOptional = appStampSetttingRepo.findByAppID(companyId);
		if (appStampSettingOptional.isPresent()) {
			appStampOutput.setAppStampSetting(appStampSettingOptional.get());
		}
		
		if (recoderFlag) {
			return appStampOutput;
		}
		
		
//		ドメインモデル「臨時勤務利用管理」を取得する
		Optional<TemporaryWorkUseManage> temporaryWorkUseManageOptional = temporaryWorkUseManageRepo.findByCid(companyId);
		// in note 
		if (temporaryWorkUseManageOptional.isPresent()) {
			Boolean getValue = BooleanUtils.toBoolean(temporaryWorkUseManageOptional.get().getUseClassification().value);		
			appStampOutput.setUseTemporary(Optional.of(getValue));
		} else {
			appStampOutput.setUseTemporary(Optional.of(false));
		}
		
//		ドメイン「打刻申請の反映」を取得する
		Optional<StampAppReflect> appStampReflect = appStampReflectRepo.findReflectByCompanyId(companyId);
		appStampOutput.setAppStampReflectOptional(appStampReflect);
		
//		打刻申請の設定チェック
		this.checkAppStampSetting(appStampReflect.isPresent() ? appStampReflect.get() : null, appStampOutput.getUseTemporary().isPresent() ? appStampOutput.getUseTemporary().get() : null);
		
//		実績の打刻のチェック
		StampRecordOutput stampRecordOutput = null;
		Optional<String> workTypeCd = Optional.empty();
		Optional<List<ActualContentDisplay>> listActualContentDisplay = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst();
		if (listActualContentDisplay.isPresent()) {
			if (!CollectionUtil.isEmpty(listActualContentDisplay.get())) {
				ActualContentDisplay actualContentDisplay = listActualContentDisplay.get().get(0);
				Optional<AchievementDetail> opAchievementDetail = actualContentDisplay.getOpAchievementDetail();
				if (opAchievementDetail.isPresent()) {
					stampRecordOutput = opAchievementDetail.get().getStampRecordOutput();
					if (opAchievementDetail.get().getTrackRecordAtr().equals(TrackRecordAtr.DAILY_RESULTS)) {
					    workTypeCd = Optional.ofNullable(opAchievementDetail.get().getWorkTypeCD());
					}
				}
			}
		}
		
		List<ErrorStampInfo> errorStampInfos = this.getErrorStampList(stampRecordOutput, workTypeCd);
		appStampOutput.setErrorListOptional(Optional.ofNullable(errorStampInfos));
		
		// ドメイン「応援の運用設定」を取得する
		SupportOperationSetting supportOperationSetting = this.supportOperationSettingRepo.get(companyId);
		appStampOutput.setUseCheering(supportOperationSetting.isUsed());
		appStampOutput.setMaxOfCheer(supportOperationSetting.getMaxNumberOfSupportOfDay().v());
		
		// 応援の運用設定.利用するか　＝　true
		if (supportOperationSetting.isUsed()) {
			List<String> workLocationCDs = stampRecordOutput != null 
					? Stream.of(stampRecordOutput.getWorkingTime()
													 	   , stampRecordOutput.getExtraordinaryTime()
													 	   , stampRecordOutput.getSupportTime())
													.flatMap(Collection::stream)
													.map(t -> t.getOpWorkLocationCD().orElse(null))
													.filter(Objects::nonNull)
													.map(WorkLocationCD::v)
													.collect(Collectors.toList())
					: Collections.emptyList();
			                    					  
			List<WorkplaceId> workplaceIds = stampRecordOutput != null 
					? Stream.of(stampRecordOutput.getWorkingTime()
										 	   		 , stampRecordOutput.getExtraordinaryTime()
										 	   		 , stampRecordOutput.getSupportTime())
													.flatMap(Collection::stream)
													.map(TimePlaceOutput::getWorkplaceId)
													.filter(Objects::nonNull)
													.collect(Collectors.toList())
					: Collections.emptyList(); 
			WkpWorkLocationName wkpWorkLocationName = this.findWkpAndWorkLocationName(
					appStampSettingOptional.isPresent() && appStampSettingOptional.get().getWkpDisAtr().isUse(),
					appStampSettingOptional.isPresent() && appStampSettingOptional.get().getUseLocationSelection().isUse(),
					workplaceIds,
					workLocationCDs,
					dates.orElse(GeneralDate.today()));
			
			appStampOutput.setWorkLocationNames(wkpWorkLocationName.getWorkLocationNames());
			appStampOutput.setWorkplaceNames(wkpWorkLocationName.getWorkplaceNames());
		}

		return appStampOutput;
	}
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF002_打刻申請.B：打刻申請（新規）→A・B画面.打刻申請（新規）共通アルゴリズム.職場名・場所名を取得する.職場名・場所名を取得する
	 *
	 * @param isGetWorkPlaceName 職場名を取得する（boolean）
	 * @param isGetWorkLocationName 場所名を取得する
	 * @param workPlaceIds 職場ID
	 * @param workLocationCds 勤務場所コード
	 * @return the list
	 */
	@Override
	public WkpWorkLocationName findWkpAndWorkLocationName(boolean isGetWorkPlaceName
												 , boolean isGetWorkLocationName
												 , List<WorkplaceId> workPlaceIds
												 , List<String> workLocationCds
												 , GeneralDate baseDate) {
		WkpWorkLocationName result = new WkpWorkLocationName();

		// 職場名を取得するかをチェックする
		// 職場名を取得する＝true　AND　職場ID（List）NOT　Empty
		if (isGetWorkPlaceName && !workPlaceIds.isEmpty()) {
			// 職場名を取得する
			result.setWorkplaceNames(this.workplaceAdapter.findWkpInfo(workPlaceIds, baseDate));
		}

		// INPUT.「場所名を取得する」をチェックする
		// 場所名を取得する＝true　AND　場所コード（List）NOT　Empty
		if (isGetWorkLocationName && !workLocationCds.isEmpty()) {
			// 場所名を取得する
			result.setWorkLocationNames(this.workLocationAdapter.findWorkLocationName(workLocationCds));
		}

		return result;
	}

	@Override
	public void checkAppStampSetting(StampAppReflect appStampReflect, Boolean temporaryWorkUseManage) {
//		「出退勤を反映する＝　しない」
		Boolean isCon1 = BooleanUtils.toBoolean(appStampReflect.getWorkReflectAtr().value);
//		[育児時間帯を反映する＝しない」
		Boolean isCon2 = BooleanUtils.toBoolean(appStampReflect.getChildCareReflectAtr().value);
//		臨時出退勤を反映する＝しない
		Boolean isCon3 = BooleanUtils.toBoolean(appStampReflect.getExtraWorkReflectAtr().value);
//		INPUT.臨時勤務の管理　＝　false
		Boolean isCon4 = temporaryWorkUseManage;
//		介護時間帯を反映する＝しない
		Boolean isCon5 = BooleanUtils.toBoolean(appStampReflect.getCareReflectAtr().value);
//		外出時間帯を反映する＝しない
		Boolean isCon6 = BooleanUtils.toBoolean(appStampReflect.getGoOutReflectAtr().value);
//		休憩時間帯を反映する＝しない
		Boolean isCon7 = BooleanUtils.toBoolean(appStampReflect.getBreakReflectAtr().value);
//		応援開始、終了を反映する=しない
		Boolean isCon8 = BooleanUtils.toBoolean(appStampReflect.getSupportReflectAtr().value);
		
		if (!isCon1 && !isCon2 && (!isCon3 || !isCon4) && !isCon5 && !isCon6 && !isCon7 && !isCon8) {
			throw new BusinessException("Msg_1757");
		}
		
	}

	@Override
	public List<ErrorStampInfo> getErrorStampList(StampRecordOutput stampRecordOutput, Optional<String> workTypeCd) {
//		「打刻エラー情報」＝Empty
		List<ErrorStampInfo> errorStampInfos = new ArrayList<ErrorStampInfo>();
		
		// create dummy data
		
//		stampRecordOutput =  new StampRecordOutput(
//				Collections.emptyList(),
//				Collections.emptyList(),
//				Collections.emptyList(),
//				Collections.emptyList(),
//				Collections.emptyList(),
//				Collections.emptyList(),
//				Collections.emptyList());
		if(stampRecordOutput == null) {
		    return errorStampInfos;
		}
		
		/**
		 * 介護時間帯
		 */
		
		List<TimePlaceOutput> nursing = Collections.emptyList();
		
		if (!CollectionUtil.isEmpty(stampRecordOutput.getNursingTime())) {
			nursing = stampRecordOutput.getNursingTime();
		}
		/**
		 * 休憩時間帯
		 */
		List<TimePlaceOutput> breakTime = Collections.emptyList();
		if(!CollectionUtil.isEmpty(stampRecordOutput.getBreakTime())) {
			breakTime = stampRecordOutput.getBreakTime();
		}
		
		/**
		 * 勤務時間帯
		 */
		List<TimePlaceOutput> workingTime = Collections.emptyList();
		if(!CollectionUtil.isEmpty(stampRecordOutput.getWorkingTime())) {
			workingTime = stampRecordOutput.getWorkingTime();
		}
		
		/**
		 * 育児時間帯
		 */		
		List<TimePlaceOutput> parentingTime = Collections.emptyList();
		if(!CollectionUtil.isEmpty(stampRecordOutput.getParentingTime())) {
			parentingTime = stampRecordOutput.getParentingTime();
		}
		/**
		 * 外出時間帯
		 */
		
		List<TimePlaceOutput> outingTime = Collections.emptyList();
		if(!CollectionUtil.isEmpty(stampRecordOutput.getOutingTime())) {
			outingTime = stampRecordOutput.getOutingTime();
		}
		
		/**
		 * 応援時間帯
		 */
		
		List<TimePlaceOutput> supportTime = Collections.emptyList();
		if(!CollectionUtil.isEmpty(stampRecordOutput.getSupportTime())) {
			supportTime = stampRecordOutput.getSupportTime();
		}

		
		/**
		 * 臨時時間帯
		 */
		
		List<TimePlaceOutput> extraordinaryTime = Collections.emptyList();
		if(!CollectionUtil.isEmpty(stampRecordOutput.getExtraordinaryTime())) {
			extraordinaryTime = stampRecordOutput.getExtraordinaryTime();
		}
		
		this.addErros(errorStampInfos, StampAtrOther.NURSE, nursing, workTypeCd);
		this.addErros(errorStampInfos, StampAtrOther.BREAK, breakTime, workTypeCd);
		this.addErros(errorStampInfos, StampAtrOther.PARENT, parentingTime, workTypeCd);
		this.addErros(errorStampInfos, StampAtrOther.GOOUT_RETURNING, outingTime, workTypeCd);
		this.addErros(errorStampInfos, StampAtrOther.CHEERING, supportTime, workTypeCd);
		this.addErros(errorStampInfos, StampAtrOther.ATTEENDENCE_OR_RETIREMENT, workingTime, workTypeCd);
		this.addErros(errorStampInfos, StampAtrOther.EXTRAORDINARY, extraordinaryTime, workTypeCd);
		
		return errorStampInfos;
	}
	
	public void addErros(List<ErrorStampInfo> errorStampInfos, StampAtrOther stampAtr, List<TimePlaceOutput>  list, Optional<String> workTypeCd) {
		if (!CollectionUtil.isEmpty(list)) {
			list.stream().forEach(item -> {
//				「勤怠時間帯」Listに勤務時間１が存在するのをチェックする
				if(item.getFrameNo().v() == 1 && stampAtr.value == 0 && workTypeCd.isPresent()) {
				        // 「勤務種類」を取得する
				        Optional<WorkType> workTypeOpt = workTypeRepository.findNoAbolishByPK(AppContexts.user().companyId(), workTypeCd.get());
				        if (workTypeOpt.isPresent()) {
				            DailyWork dailyWork = workTypeOpt.get().getDailyWork();
				            if ((dailyWork.getWorkTypeUnit().equals(WorkTypeUnit.OneDay) && checkWorkTypeWork(dailyWork.getOneDay())
				                    || (dailyWork.getWorkTypeUnit().equals(WorkTypeUnit.MonringAndAfternoon) && 
				                            (checkWorkTypeWork(dailyWork.getMorning()) || checkWorkTypeWork(dailyWork.getAfternoon()))))) {
				                if(!item.getOpStartTime().isPresent()) {
    				                // 「打刻エラー情報」に「打刻エラー情報」を追加する
    				                ErrorStampInfo start = new ErrorStampInfo(stampAtr, item.getFrameNo().v(), StartEndClassification.START);
    				                errorStampInfos.add(start);
				                }
				                if(!item.getOpEndTime().isPresent()) {
				                    ErrorStampInfo end = new ErrorStampInfo(stampAtr, item.getFrameNo().v(), StartEndClassification.END);
				                    errorStampInfos.add(end);
				                }
				            }
				        }
				} else {
//				「時刻場所」に時刻開始と時刻終了をチェックする。
				    if(!item.getOpStartTime().isPresent() && item.getOpEndTime().isPresent()) {
				        errorStampInfos.add(new ErrorStampInfo(stampAtr, item.getFrameNo().v(), StartEndClassification.START));
				    }
				    if(item.getOpStartTime().isPresent() && !item.getOpEndTime().isPresent()) {
				        errorStampInfos.add(new ErrorStampInfo(stampAtr, item.getFrameNo().v(), StartEndClassification.END));
				    }
                }
			});
			
		}
	}

	@Override
	public List<ConfirmMsgOutput> checkBeforeRegister(String companyId, Boolean agentAtr,
			Application application, AppStampOutput appStampOutput) {
		List<ConfirmMsgOutput> listConfirmMs = new ArrayList<ConfirmMsgOutput>();
//		「打刻申請（制約）」の事前条件のチェックを実行する
		this.checkRegisterAndUpdate(appStampOutput.getAppStampOptional().orElse(null));
		
//		2-1.新規画面登録前の処理
		registerBefore.processBeforeRegister_New(
				companyId,
				EmploymentRootAtr.APPLICATION,
				agentAtr,
				application,
				null,
				appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpMsgErrorLst().orElse(Collections.emptyList()),
				null,
				appStampOutput.getAppDispInfoStartupOutput(), 
				new ArrayList<String>(), 
				Optional.empty(), 
				Optional.empty(), 
				false);
		
		
		return listConfirmMs;
	}
	
	@Override
	public List<ConfirmMsgOutput> checkBeforeUpdate(String companyId, Boolean agentAtr, Application application,
			AppStampOutput appStampOutput) {
		List<ConfirmMsgOutput> listConfirmMs = new ArrayList<ConfirmMsgOutput>();
		// check 
		this.checkRegisterAndUpdate(appStampOutput.getAppStampOptional().orElse(null));
		
		
//		4-1.詳細画面登録前の処理
		detailBeforeUpdate.processBeforeDetailScreenRegistration(
				companyId,
				application.getEmployeeID(),
				application.getAppDate().getApplicationDate(),
				EmploymentRootAtr.APPLICATION.value,
				application.getAppID(),
				application.getPrePostAtr(),
				application.getVersion(),
				null,
				null,
				appStampOutput.getAppDispInfoStartupOutput(), 
				new ArrayList<String>(), 
				Optional.empty(), 
				false, 
				Optional.empty(), 
				Optional.empty());
		
		return listConfirmMs;
	}
	// msg307
	public void checkTime(List<AppStampStandard> listAppStampStandar) {
		if (CollectionUtil.isEmpty(listAppStampStandar)) return;
		Collections.sort(listAppStampStandar, new Comparator<AppStampStandard>() {

			@Override
			public int compare(AppStampStandard arg0, AppStampStandard arg1) {
				return arg0.getFramNo() > arg1.getFramNo() ? 1 : -1;
			}
			
		});
		AppStampStandard temp = new AppStampStandard();
		listAppStampStandar.stream().forEach(item -> {
			if (temp.getFramNo() != null) {
				if (item.getStartTime() != null && item.getEndTime() != null) {
					if (item.getStartTime() > item.getEndTime()) {
						// throw msg
						
						throw new BusinessException("Msg_307");
					}
					if (temp.getEndTime() != null) {
						if (temp.getEndTime() > item.getStartTime()) {
							throw new BusinessException("Msg_307");
						} 
					} else if (temp.getStartTime() != null) {
						if (temp.getStartTime() > item.getStartTime()) {
							throw new BusinessException("Msg_307");
						} 
					}
					
				}else {
					if (item.getStartTime() != null) {
						
						if (temp.getEndTime() != null) {
							if (item.getStartTime() < temp.getEndTime() ) {
								// throw msg	
								throw new BusinessException("Msg_307");
							}							
						} else if (temp.getStartTime() != null) {
							if (item.getStartTime() < temp.getStartTime() ) {
								// throw msg	
								throw new BusinessException("Msg_307");
							}
						}
					} else if (item.getEndTime() != null) {
						if (temp.getEndTime() != null) {
							if (item.getEndTime() < temp.getEndTime() ) {
								// throw msg	
								throw new BusinessException("Msg_307");
							}							
						} else if (temp.getStartTime() != null) {
							if (item.getEndTime() < temp.getStartTime() ) {
								// throw msg	
								throw new BusinessException("Msg_307");
							}
						}
					}
					
				}
				
				if (item.getStartTime() != null) {
					temp.setStartTime(temp.getEndTime());
					temp.setEndTime(item.getStartTime());
					
				}
				if (item.getEndTime() != null) {
					temp.setStartTime(temp.getEndTime());
					temp.setEndTime(item.getEndTime());
					
				}
				
				
				
			} else {
				temp.setStartTime(item.getStartTime());
				temp.setEndTime(item.getEndTime());
				temp.setFramNo(item.getFramNo());
				temp.setStampAtrOther(item.getStampAtrOther());
				
				if (item.getStartTime() != null && item.getEndTime() != null) {
					if (item.getStartTime() > item.getEndTime()) {
						// throw msg
						
						throw new BusinessException("Msg_307");
					}
				}
			}
		});
		
		
	}
	public void checkRegisterAndUpdate(AppStamp appStamp) {
		if (appStamp == null) return;
//		事前モード：「時間帯：Empty　AND　AND　時刻：Empty」の場合：Msg_308を表示する
		if (appStamp.getPrePostAtr() == PrePostAtr.PREDICT) {
			if (CollectionUtil.isEmpty(appStamp.getListTimeStampApp())  
					&& CollectionUtil.isEmpty(appStamp.getListTimeStampAppOther()))
					{
				throw new BusinessException("Msg_308");
	
			}
		}
//		事後モード：「時間帯：Empty　AND　時間帯の取消：Empty　AND　時刻：Empty　AND　時刻の取消：Empty、」の場合：Msg_308を表示する
		if (appStamp.getPrePostAtr() == PrePostAtr.POSTERIOR) {
			if (CollectionUtil.isEmpty(appStamp.getListTimeStampApp())  
					&& CollectionUtil.isEmpty(appStamp.getListDestinationTimeApp())
					&& CollectionUtil.isEmpty(appStamp.getListTimeStampAppOther())
					&& CollectionUtil.isEmpty(appStamp.getListDestinationTimeZoneApp()))
					{
				throw new BusinessException("Msg_308");
				
				
			}
		}

		if (!CollectionUtil.isEmpty(appStamp.getListTimeStampAppOther())) {
			List<TimeStampAppOther> listTimeStampAppOther = appStamp.getListTimeStampAppOther();
			listTimeStampAppOther.stream().forEach(x -> {
				if (x.getTimeZone().getStartTime() == null || x.getTimeZone().getEndTime() == null ) {
//					育児 または 介護または休憩の時、「 開始時刻　OR　 終了時刻」は入力しない　→　(#Msg_308#)
					throw new BusinessException("Msg_308");
				} 
			});
			
		}
		
		
		// msg 307
		List<AppStampStandard> listAppStampStandar = AppStampStandard.toListStandard(appStamp);
		if (!CollectionUtil.isEmpty(listAppStampStandar)) {
//			出勤／退勤
			List<AppStampStandard> listAttendence = listAppStampStandar.stream().filter(x -> x.getStampAtrOther() == StampAtrOther.ATTEENDENCE_OR_RETIREMENT).collect(Collectors.toList());
//			臨時
			List<AppStampStandard> listExtraordinary = listAppStampStandar.stream().filter(x -> x.getStampAtrOther() == StampAtrOther.EXTRAORDINARY).collect(Collectors.toList());
//			外出／戻り
			List<AppStampStandard> listGoOut = listAppStampStandar.stream().filter(x -> x.getStampAtrOther() == StampAtrOther.GOOUT_RETURNING).collect(Collectors.toList());
//			応援
			List<AppStampStandard> listCheer = listAppStampStandar.stream().filter(x -> x.getStampAtrOther() == StampAtrOther.CHEERING).collect(Collectors.toList());
//			育児
			List<AppStampStandard> listParent = listAppStampStandar.stream().filter(x -> x.getStampAtrOther() == StampAtrOther.PARENT).collect(Collectors.toList());
//			介護
			List<AppStampStandard> listNurse = listAppStampStandar.stream().filter(x -> x.getStampAtrOther() == StampAtrOther.NURSE).collect(Collectors.toList());
//			休憩
			List<AppStampStandard> listBreak = listAppStampStandar.stream().filter(x -> x.getStampAtrOther() == StampAtrOther.BREAK).collect(Collectors.toList());
			
			this.checkTime(listAttendence);
			this.checkTime(listExtraordinary);
			this.checkTime(listGoOut);
			this.checkTime(listCheer);
			this.checkTime(listParent);
			this.checkTime(listNurse);
			this.checkTime(listBreak);
	

		}

		
	}

	@Override
	public AppStampOutput getDataDetailCommon(String companyId, String appId,
			AppDispInfoStartupOutput appDispInfoStartupOutput, Boolean recoderFlag) {
		
		AppStampOutput appStampOutput = new AppStampOutput();
//		ドメイン「打刻申請の反映」を取得する
		Optional<StampAppReflect> appStampReflect = appStampReflectRepo.findReflectByCompanyId(companyId);
		appStampOutput.setAppStampReflectOptional(appStampReflect);
		
//		ドメインモデル「打刻申請設定」を取得する	
		Optional<AppStampSetting> appStampSettingOptional = appStampSetttingRepo.findByAppID(companyId);
		if (appStampSettingOptional.isPresent()) {
			appStampOutput.setAppStampSetting(appStampSettingOptional.get());
		}
		
		if (recoderFlag) {			
//			ドメインモデル「レコーダイメージ申請」を取得する
			Optional<AppRecordImage> appRecordImageOptional = appRecordImageRepo.findByAppID(companyId, appId);
			appStampOutput.setAppRecordImage(appRecordImageOptional);
			
			
		} else {			
//			ドメインモデル「打刻申請」を取得する
			Optional<AppStamp> appStampOptional = appStampRepo.findByAppID(companyId, appId);
			appStampOutput.setAppStampOptional(appStampOptional);
			
			
//			実績の打刻のチェック
			StampRecordOutput stampRecordOutput = null;
			Optional<String> workTypeCd = Optional.empty();
			Optional<List<ActualContentDisplay>> listActualContentDisplay = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst();
			if (listActualContentDisplay.isPresent()) {
				if (!CollectionUtil.isEmpty(listActualContentDisplay.get())) {
					ActualContentDisplay actualContentDisplay = listActualContentDisplay.get().get(0);
					Optional<AchievementDetail> opAchievementDetail = actualContentDisplay.getOpAchievementDetail();
					if (opAchievementDetail.isPresent()) {
	                    stampRecordOutput = opAchievementDetail.get().getStampRecordOutput();
	                    if (opAchievementDetail.get().getTrackRecordAtr().equals(TrackRecordAtr.DAILY_RESULTS)) {
	                        workTypeCd = Optional.ofNullable(opAchievementDetail.get().getWorkTypeCD());
	                    }
	                }
				}
			}
			List<ErrorStampInfo> errorStampInfos = this.getErrorStampList(stampRecordOutput, workTypeCd);
			if (!CollectionUtil.isEmpty(errorStampInfos)) {
				appStampOutput.setErrorListOptional(Optional.of(errorStampInfos));
				
			}
			
			
//			ドメインモデル「臨時勤務利用管理」を取得する
			Optional<TemporaryWorkUseManage> temporaryWorkUseManageOptional = temporaryWorkUseManageRepo.findByCid(companyId);
			
			// in note as A screen
			if (temporaryWorkUseManageOptional.isPresent()) {
				Boolean getValue = BooleanUtils.toBoolean(temporaryWorkUseManageOptional.get().getUseClassification().value);		
				appStampOutput.setUseTemporary(Optional.of(getValue));
			} else {
				appStampOutput.setUseTemporary(Optional.of(false));
			}
			
			// ドメイン「応援の運用設定」を取得する
			SupportOperationSetting supportOperationSetting = this.supportOperationSettingRepo.get(companyId);
			appStampOutput.setUseCheering(supportOperationSetting.isUsed());
			
			// 最大応援回数の設定を取得する
			MaximumNumberOfSupport maxNumberOfSupportOfDay = supportOperationSetting.getMaxNumberOfSupportOfDay();
			appStampOutput.setMaxOfCheer(maxNumberOfSupportOfDay.v());
			
			GeneralDate appDate = GeneralDate.today();
			if (appStampOptional.isPresent()) {
				Optional<Application> optApp = this.appRepo.findByID(appId);
				
				if (optApp.isPresent()) {
					appDate = optApp.get().getAppDate().getApplicationDate();
				}
			}
			
			List<WorkplaceId> workplaceIds = appStampOptional.isPresent()
					? appStampOptional.get().getListTimeStampApp().stream()
							.map(x -> x.getWorkplaceId().orElse(null))
							.filter(Objects::nonNull)
							.collect(Collectors.toList())
					: new ArrayList<WorkplaceId>();
							
			List<String> workLocationCDs = appStampOptional.isPresent()
					? appStampOptional.get().getListTimeStampApp().stream()
							.map(x -> x.getWorkLocationCd().orElse(null))
							.filter(Objects::nonNull)
							.map(WorkLocationCD::v)
							.collect(Collectors.toList())
					: new ArrayList<String>();
			
			WkpWorkLocationName wkpWorkLocationName = this.findWkpAndWorkLocationName(
					appStampSettingOptional.isPresent() && appStampSettingOptional.get().getWkpDisAtr().isUse(),
					appStampSettingOptional.isPresent() && appStampSettingOptional.get().getUseLocationSelection().isUse(),
					workplaceIds,
					workLocationCDs,
					appDate);

			appStampOutput.setWorkLocationNames(wkpWorkLocationName.getWorkLocationNames());
			appStampOutput.setWorkplaceNames(wkpWorkLocationName.getWorkplaceNames());
			
		}
		appStampOutput.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		return appStampOutput;
	}
	
	private static boolean checkWorkTypeWork(WorkTypeClassification workTypeAtr) {
	    if (workTypeAtr.equals(WorkTypeClassification.Attendance) 
	            || workTypeAtr.equals(WorkTypeClassification.Shooting)
	            || workTypeAtr.equals(WorkTypeClassification.ContinuousWork) 
	            || workTypeAtr.equals(WorkTypeClassification.HolidayWork)) {
	        return true;
	    }
	    
	    return false;
	}
	

}
