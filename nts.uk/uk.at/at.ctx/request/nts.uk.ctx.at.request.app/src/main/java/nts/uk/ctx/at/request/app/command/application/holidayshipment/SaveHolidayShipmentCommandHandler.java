package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService_New;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmploymentHistoryImported;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.holidayshipment.ApplicationCombination;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveWorkingHour;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.WorkTime;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentWorkingHour;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.ContractCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.ctx.at.shared.dom.worktype.holidayset.HolidaySetting;
import nts.uk.ctx.at.shared.dom.worktype.holidayset.HolidaySettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class SaveHolidayShipmentCommandHandler extends CommandHandler<SaveHolidayShipmentCommand> {

	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	@Inject
	private WithDrawalReqSetRepository withDrawRepo;
	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;
	@Inject
	private ApplicationRepository_New appRepo;
	@Inject
	private WorkTypeRepository wkTypeRepo;
	@Inject
	private WorkplaceAdapter wpAdapter;
	@Inject
	private EmpSubstVacationRepository empSubrepo;
	@Inject
	private ComSubstVacationRepository comSubrepo;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithmService;
	@Inject
	private HolidaySettingRepository holidayRepo;
	@Inject
	private NewBeforeRegister_New processBeforeRegister;
	@Inject
	private RegisterAtApproveReflectionInfoService_New registerAppReplection;
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	@Inject
	private RecruitmentAppRepository recRepo;
	@Inject
	private CompltLeaveSimMngRepository CompLeaveRepo;
	@Inject
	private ApplicationApprovalService_New appImp;

	String companyID;

	ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;

	String sID;
	GeneralDate absDate;
	GeneralDate recDate;
	String appReason;
	int comType;

	@Override
	protected void handle(CommandHandlerContext<SaveHolidayShipmentCommand> context) {
		companyID = AppContexts.user().companyId();

		SaveHolidayShipmentCommand command = context.getCommand();
		sID = command.getAppCmd().getEnteredPersonSID();
		absDate = command.getAbsCmd().getAppDate();
		recDate = command.getRecCmd().getAppDate();
		comType = command.getComType();
		// アルゴリズム「振休振出申請の新規登録」を実行する
		createNewForHolidayBreakge(command);

	}

	private void createNewForHolidayBreakge(SaveHolidayShipmentCommand command) {
		// アルゴリズム「登録前エラーチェック（新規）」を実行する
		errorCheckBeforeRegister(command);
		if (command.getComType() == ApplicationCombination.RecAndAbs.value) {
			// アルゴリズム「振休申請・振出申請の同時登録」を実行する
			registerBothApp(command);
		} else {
			// アルゴリズム「振出申請の登録」を実行する
			if (isSaveRec()) {
				RegRecApp(command);
			}
			if (isSaveAbs()) {
				RegAbsenceLeaveApp(command);
			}

		}
	}

	private void RegAbsenceLeaveApp(SaveHolidayShipmentCommand command) {
		String wkTypeCD = command.getRecCmd().getWkTypeCD();
		// アルゴリズム「振休消化管理データ更新と消化対象の決定」を実行する
		updateDigestionTarget(command);
		// アルゴリズム「代休消化管理データ更新と消化対象の決定」を実行する
		updateOfSubstitution(command, wkTypeCD);
		// ドメイン「振休申請」を1件登録する
		createNewAbsenceLeaveApp(command);

	}

	private void updateDigestionTarget(SaveHolidayShipmentCommand command) {
		// アルゴリズム「勤務種類別振休消化数の取得」を実行する
		BigDecimal day = getByWorkType(command.getAbsCmd().getWkTypeCD(), WorkTypeClassification.Pause);
		// TODO アルゴリズム「暫定振休管理データの登録と自動相殺」を実行する chưa làm được do không có thông
		// tin của 暫定振休管理データ
		// 暫定振出振休紐付け管理の件数分ループ

	}

	private void RegRecApp(SaveHolidayShipmentCommand command) {

		String wkTypeCD = command.getRecCmd().getWkTypeCD();
		// アルゴリズム「代休消化管理データ更新と消化対象の決定」を実行する
		updateOfSubstitution(command, wkTypeCD);
		// アルゴリズム「振休発生管理データ更新」を実行する
		updateOccurrenceData(companyID, sID, wkTypeCD);
		// 消化対象代休管理を振出申請に追加する
		createNewRecApp(command);

	}

	private void updateOccurrenceData(String companyID, String sID, String wkTypeCD) {
		// アルゴリズム「勤務種類別振休発生数の取得」を実行する
		BigDecimal holidayBrkDownDay = getByWorkType(wkTypeCD, WorkTypeClassification.Shooting);
		// アルゴリズム「勤務種類別法定内外区分の取得」を実行する
		HolidaySetting holidaySet = getHolidaySetByWkType(wkTypeCD);
		// アルゴリズム「振休有効期限の決定」を実行する
		GeneralDate expDate = DemOfexpDate(recDate);
		// アルゴリズム「暫定振出管理データの登録と自動相殺」を実行する
		registerMngData(recDate);

	}

	private void registerMngData(GeneralDate recDate) {
		// アルゴリズム「暫定振出管理データの登録」を実行する
		registerAbsData();
		// 振出有効期限内の未相殺振休を取得する
		getExpDate(recDate);

	}

	private void getExpDate(GeneralDate recDate) {
		// アルゴリズム「暫定未相殺残数を作成する」を実行する
		createProvisionalBalance(recDate);

	}

	private void createProvisionalBalance(GeneralDate recDate2) {
		// chưa có thông tin của domain 暫定振休管理データ

	}

	private void registerBothApp(SaveHolidayShipmentCommand command) {
		AbsenceLeaveAppCommand absAppCmd = command.getAbsCmd();
		RecruitmentAppCommand recAppCmd = command.getRecCmd();
		// アルゴリズム「代休消化管理データ更新と消化対象の決定」を実行する takingout
		updateOfSubstitution(command, absAppCmd.getWkTypeCD());
		// アルゴリズム「代休消化管理データ更新と消化対象の決定」を実行する holiday
		updateOfSubstitution(command, recAppCmd.getWkTypeCD());
		// 振休発生消化管理データを登録
		RegisterDigestionData(command);

		String recAppID = createNewRecApp(command);

		String absAppID = createNewAbsenceLeaveApp(command);
		// ドメイン「振休振出同時申請管理」を1件登録する
		createNewComLeaveSilMng(recAppID, absAppID);

	}

	private void createNewComLeaveSilMng(String recAppID, String absAppID) {
		CompltLeaveSimMng comMng = new CompltLeaveSimMng(recAppID, absAppID, SyncState.SYNCHRONIZING);
		CompLeaveRepo.insert(comMng);

	}

	private String createNewAbsenceLeaveApp(SaveHolidayShipmentCommand command) {
		Application_New absApplication = Application_New.firstCreate(companyID,
				EnumAdaptor.valueOf(command.getAppCmd().getPrePostAtr(), PrePostAtr.class), absDate, appType, sID,
				new AppReason(appReason));
		String absAppID = absApplication.getAppID();
		// アルゴリズム「登録前共通処理（新規）」を実行する
		CmProcessBeforeReg(command, absApplication);
		// ドメイン「振出申請」を1件登録する
		AbsenceLeaveAppCommand absAppCmd = command.getAbsCmd();
		WkTimeCommand wkTime1Cmd = absAppCmd.getWkTime1();
		WkTimeCommand wkTime2Cmd = absAppCmd.getWkTime2();
		AbsenceLeaveWorkingHour workTime1 = new AbsenceLeaveWorkingHour(new WorkTime(wkTime1Cmd.getStartTime()),
				new WorkTime(wkTime1Cmd.getEndTime()));
		AbsenceLeaveWorkingHour workTime2 = new AbsenceLeaveWorkingHour(new WorkTime(wkTime2Cmd.getStartTime()),
				new WorkTime(wkTime2Cmd.getEndTime()));
		AbsenceLeaveApp absApp = new AbsenceLeaveApp(absAppID, absAppCmd.getWkTypeCD(),
				EnumAdaptor.valueOf(absAppCmd.getChangeWorkHoursType(), NotUseAtr.class),
				new WorkTimeCode(absAppCmd.getWkTimeCD()), workTime1, workTime2, Collections.emptyList(),
				Collections.emptyList());
		appImp.insert(absApplication);
		absRepo.insert(absApp);
		// アルゴリズム「新規画面登録時承認反映情報の整理」を実行する
		registerAppReplection.newScreenRegisterAtApproveInfoReflect(sID, absApplication);

		return absAppID;

	}

	private String createNewRecApp(SaveHolidayShipmentCommand command) {
		// アルゴリズム「登録前共通処理（新規）」を実行する
		Application_New recApplication = Application_New.firstCreate(companyID,
				EnumAdaptor.valueOf(command.getAppCmd().getPrePostAtr(), PrePostAtr.class), recDate, appType, sID,
				new AppReason(appReason));
		String recAppID = recApplication.getAppID();

		CmProcessBeforeReg(command, recApplication);
		// ドメイン「振出申請」を1件登録する
		RecruitmentAppCommand recAppCmd = command.getRecCmd();
		WkTimeCommand wkTime1Cmd = recAppCmd.getWkTime1();
		WkTimeCommand wkTime2Cmd = recAppCmd.getWkTime2();
		RecruitmentApp recApp = new RecruitmentApp(recAppID, recAppCmd.getWkTypeCD(),
				new WorkTimeCode(recAppCmd.getWkTimeCD()),
				new RecruitmentWorkingHour(new WorkTime(wkTime1Cmd.getStartTime()),
						EnumAdaptor.valueOf(wkTime1Cmd.getStartType(), NotUseAtr.class),
						new WorkTime(wkTime1Cmd.getEndTime()),
						EnumAdaptor.valueOf(wkTime1Cmd.getStartType(), NotUseAtr.class)),
				new RecruitmentWorkingHour(new WorkTime(wkTime2Cmd.getStartTime()),
						EnumAdaptor.valueOf(wkTime2Cmd.getStartType(), NotUseAtr.class),
						new WorkTime(wkTime2Cmd.getEndTime()),
						EnumAdaptor.valueOf(wkTime2Cmd.getStartType(), NotUseAtr.class)),
				Collections.emptyList());
		appImp.insert(recApplication);
		recRepo.insert(recApp);
		// アルゴリズム「新規画面登録時承認反映情報の整理」を実行する
		registerAppReplection.newScreenRegisterAtApproveInfoReflect(sID, recApplication);
		return recAppID;
	}

	private void CmProcessBeforeReg(SaveHolidayShipmentCommand command, Application_New application) {
		// アルゴリズム「新規画面登録前の処理」を実行する
		processBeforeRegister.processBeforeRegister(application);

	}

	private void RegisterDigestionData(SaveHolidayShipmentCommand command) {
		// アルゴリズム「勤務種類別振休発生数の取得」を実行する rec
		BigDecimal absBrkDownDay = getByWorkType(command.getAbsCmd().getWkTypeCD(), WorkTypeClassification.Shooting);
		// アルゴリズム「勤務種類別振休発生数の取得」を実行する holiday
		BigDecimal recBrkDownDay = getByWorkType(command.getRecCmd().getWkTypeCD(), WorkTypeClassification.Shooting);
		if ((absBrkDownDay.compareTo(recBrkDownDay) == 0)) {
			// アルゴリズム「振休有効期限の決定」を実行する
			GeneralDate expDate = DemOfexpDate(recDate);
			// アルゴリズム「勤務種類別法定内外区分の取得」を実行する
			HolidaySetting holidaySet = getHolidaySetByWkType(command.getAbsCmd().getWkTypeCD());
			// アルゴリズム「暫定振出・暫定振休管理データの同時登録」を実行する
			registerData(command, expDate, holidaySet);

		}

	}

	private void registerData(SaveHolidayShipmentCommand command, GeneralDate expDate, HolidaySetting holidaySet) {
		// アルゴリズム「暫定振出管理データの登録」を実行する
		registerAbsData();

	}

	private void registerAbsData() {
		// TODO 更新用未使用日数を決定する
		// chưa có thông tin của domain 暫定振出管理データ

	}

	private HolidaySetting getHolidaySetByWkType(String wkTypeCD) {
		// ドメインモデル「勤務種類」を取得する
		HolidaySetting result = null;
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCD);
		if (wkTypeOpt.isPresent()) {
			WorkType wkType = wkTypeOpt.get();
			if (wkType.getDailyWork().isHolidayWork()) {
				result = holidayRepo.findBy(companyID).get();

			}

		}
		return result;

	}

	private GeneralDate DemOfexpDate(GeneralDate refDate) {
		// Imported(就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> empImpOpt = wpAdapter.getEmpHistBySid(companyID, sID, refDate);
		GeneralDate expDate = null;
		if (empImpOpt.isPresent()) {
			// アルゴリズム「振休管理設定の取得」を実行する
			EmploymentHistoryImported empImp = empImpOpt.get();
			String emptCD = empImp.getEmploymentCode();
			Optional<EmpSubstVacation> empSubOpt = empSubrepo.findById(companyID, emptCD);
			if (empSubOpt.isPresent()) {
				EmpSubstVacation empSub = empSubOpt.get();
				expDate = getDateByExpirationTime(empSub.getSetting().getExpirationDate());

			} else {
				Optional<ComSubstVacation> comSubOpt = comSubrepo.findById(companyID);
				ComSubstVacation comSub = comSubOpt.get();
				expDate = getDateByExpirationTime(comSub.getSetting().getExpirationDate());

			}
		}
		return expDate;

	}

	private GeneralDate getDateByExpirationTime(ExpirationTime expirationDate) {
		GeneralDate expDate = null;
		if (expirationDate.equals(ExpirationTime.UNLIMITED)) {
			expDate = GeneralDate.max();
		}
		if (expirationDate.equals(ExpirationTime.THIS_MONTH)) {
			expDate = otherCommonAlgorithmService
					.employeePeriodCurrentMonthCalculate(companyID, sID, GeneralDate.today()).getEndDate();
		}

		return expDate;
	}

	private void updateOfSubstitution(SaveHolidayShipmentCommand command, String wkTypeCD) {
		// アルゴリズム「勤務種類別代休消化数の取得」を実行する
		BigDecimal substitutionDay = getByWorkType(wkTypeCD, WorkTypeClassification.SubstituteHoliday);

		// TODO liên quan đến domain 暫定休出管理データ

	}

	private BigDecimal getByWorkType(String wkTypeCD, WorkTypeClassification wkTypeClass) {
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCD);
		BigDecimal result = new BigDecimal(0);
		if (wkTypeOpt.isPresent()) {
			WorkType wkType = wkTypeOpt.get();
			DailyWork dailyWk = wkType.getDailyWork();
			if (dailyWk.getWorkTypeUnit().equals(WorkTypeUnit.OneDay)) {
				if (dailyWk.getOneDay().equals(wkTypeClass)) {
					result = BigDecimal.valueOf(1);
				}

			}
			if (wkType.getDailyWork().getWorkTypeUnit().equals(WorkTypeUnit.MonringAndAfternoon)) {
				if (dailyWk.getMorning().equals(wkTypeClass)) {
					result = result.add(BigDecimal.valueOf(0.5));
				} else {

				}
				if (dailyWk.getAfternoon().equals(wkTypeClass)) {
					result = result.add(BigDecimal.valueOf(0.5));
				} else {

				}

			}

		}
		return result;

	}

	private void errorCheckBeforeRegister(SaveHolidayShipmentCommand command) {
		// アルゴリズム「事前条件チェック」を実行する
		appReason = preconditionCheck(command, companyID, appType);
		// アルゴリズム「振休振出申請設定の取得」を実行する
		Optional<WithDrawalReqSet> withDrawReqSet = withDrawRepo.getWithDrawalReqSet();
		// アルゴリズム「申請前勤務種類の取得」を実行する takingout
		if (isSaveAbs()) {
			getWorkTypeOfApp(sID, absDate);
		}
		// アルゴリズム「申請前勤務種類の取得」を実行する holiday
		if (isSaveRec()) {
			getWorkTypeOfApp(sID, recDate);
		}
		// アルゴリズム「申請日関連チェック」を実行する
		ApplicationDateRelatedCheck(command, withDrawReqSet.get());
		// アルゴリズム「勤務種類矛盾チェック」を実行する
		checkWorkTypeConflict(command, withDrawReqSet.get());
		// アルゴリズム「終日半日矛盾チェック」を実行する
		checkDayConflict(command);
	}

	private void checkDayConflict(SaveHolidayShipmentCommand command) {
		if (command.getComType() == ApplicationCombination.RecAndAbs.value) {
			// アルゴリズム「勤務種類別振休発生数の取得」を実行する takingout
			BigDecimal takingoutBrkDownDay = getByWorkType(command.getAbsCmd().getWkTypeCD(),
					WorkTypeClassification.Shooting);
			// アルゴリズム「勤務種類別振休発生数の取得」を実行する holiday
			BigDecimal holidayBrkDownDay = getByWorkType(command.getRecCmd().getWkTypeCD(),
					WorkTypeClassification.Shooting);
			if (!(BigDecimal.valueOf(0).compareTo(takingoutBrkDownDay) == 0)
					&& !(BigDecimal.valueOf(0).compareTo(holidayBrkDownDay) == 0)) {
				if (!(takingoutBrkDownDay.compareTo(holidayBrkDownDay) == 0)) {
					throw new BusinessException("Msg_698", "");
				}
			}
		}

	}

	private void checkWorkTypeConflict(SaveHolidayShipmentCommand command, WithDrawalReqSet withDrawalReqSet) {
		if (!withDrawalReqSet.getAppliDateContrac().equals(ContractCheck.DONT_CHECK)) {
			// アルゴリズム「振出勤務種類矛盾チェック」を実行する
			workTypeContradictionCheck();
			// アルゴリズム「申請前勤務種類の取得」を実行する
		}

	}

	private void workTypeContradictionCheck() {
		// TODO tài liệu bị trống

	}

	public boolean isSaveRec() {
		if (comType == ApplicationCombination.RecAndAbs.value || comType == ApplicationCombination.Rec.value) {
			return true;
		}
		return false;
	}

	public boolean isSaveAbs() {
		if (comType == ApplicationCombination.RecAndAbs.value || comType == ApplicationCombination.Abs.value) {
			return true;
		}
		return false;
	}

	private void ApplicationDateRelatedCheck(SaveHolidayShipmentCommand command, WithDrawalReqSet reqSet) {
		// アルゴリズム「同日申請存在チェック」を実行する
		dateCheck(command);
		// 申請の組み合わせをチェックする
		if (command.getComType() == ApplicationCombination.RecAndAbs.value) {
			// アルゴリズム「振休先取可否チェック」を実行する
			checkFirstShipment(reqSet.getLettleSuperLeave(), recDate, absDate);
		}
	}

	private void checkFirstShipment(AllowAtr allowAtr, GeneralDate recDate, GeneralDate absDate) {
		if (recDate.equals(absDate)) {
			throw new BusinessException("Msg_696");
		}
		if (recDate.after(absDate) && allowAtr.equals(AllowAtr.NOTALLOW)) {
			throw new BusinessException("Msg_697");

		}

	}

	private void dateCheck(SaveHolidayShipmentCommand command) {
		// アルゴリズム「休暇・振替系申請存在チェック」を実行する
		if (isSaveAbs()) {
			vacationTransferCheck(sID, absDate, command.getAppCmd().getPrePostAtr());
		}
		// アルゴリズム「休暇・振替系申請存在チェック」を実行する
		if (isSaveRec()) {
			vacationTransferCheck(sID, recDate, command.getAppCmd().getPrePostAtr());
		}

	}

	public void vacationTransferCheck(String sID, GeneralDate appDate, int prePostAtr) {
		List<Application_New> apps = appRepo.getApp(sID, appDate, prePostAtr, ApplicationType.ABSENCE_APPLICATION.value)
				.stream()
				.filter(x -> !x.getReflectionInformation().getStateReflection().equals(ReflectedState_New.CANCELED)
						&& !x.getReflectionInformation().getStateReflection().equals(ReflectedState_New.DENIAL))
				.collect(Collectors.toList());
		if (CollectionUtil.isEmpty(apps)) {
			apps = appRepo.getApp(sID, appDate, prePostAtr, ApplicationType.COMPLEMENT_LEAVE_APPLICATION.value).stream()
					.filter(x -> !x.getReflectionInformation().getStateReflection().equals(ReflectedState_New.CANCELED)
							&& !x.getReflectionInformation().getStateReflection().equals(ReflectedState_New.DENIAL))
					.collect(Collectors.toList());
			if (!CollectionUtil.isEmpty(apps)) {
				throw new BusinessException("Msg_700", " ", appDate.toString());
			}
		} else {
			throw new BusinessException("Msg_700", " ", appDate.toString());
		}

	}

	private void getWorkTypeOfApp(String sID, GeneralDate appDate) {
		// アルゴリズム待ち
		// アルゴリズム「勤務実績を取得する」を実行する
		String workTypeCode = "";
		RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(sID, appDate);
		if (recordWorkInfoImport.getWorkTypeCode().equals("")) {
			// アルゴリズム「予定管理利用チェック」を実行する
			UseAtr useAtr = ScheMngUseCheck(sID, appDate);
			if (useAtr != null && useAtr.equals(UseAtr.USE)) {
				// アルゴリズム「勤務予定を取得する」を実行する

			} else {

			}
		} else {
			workTypeCode = recordWorkInfoImport.getWorkTypeCode();

		}

	}

	private UseAtr ScheMngUseCheck(String sID, GeneralDate appDate) {
		// ドメインモデル「個人労働条件」を取得する
		Optional<PersonalLaborCondition> perCond = personalLaborConditionRepository.findById(sID, appDate);
		if (perCond.isPresent()) {

			return perCond.get().getScheduleManagementAtr();
		}
		return null;

	}

	public String preconditionCheck(SaveHolidayShipmentCommand command, String companyID, ApplicationType appType) {
		// アルゴリズム「申請理由の生成と検査」を実行する
		String reason = GenAndInspectionOfAppReason(command, companyID, appType);
		// INPUT.振出申請に申請理由を設定する

		if (isSaveRec()) {
			validateRec(command.getRecCmd());
		}

		if (isSaveAbs()) {
			validateAbs(command.getAbsCmd());
		}

		return reason;

	}

	private void validateAbs(AbsenceLeaveAppCommand cmd) {

		WkTimeCommand wkTime1 = cmd.getWkTime1();
		WkTimeCommand wkTime2 = cmd.getWkTime2();

		// 就業時間帯変更＝するしない区分.しないのとき、以下の項目が設定されていないこと
		// ・就業時間帯
		// ・勤務時間1
		// ・勤務時間2
		if (cmd.getChangeWorkHoursType() == NotUseAtr.NOT_USE.value) {
			wkTime1.setStartTime(null);
			wkTime1.setEndTime(null);
			cmd.setWkTimeCD(null);
			wkTime2.setStartTime(null);
			wkTime2.setEndTime(null);
			cmd.setWkTimeCD(null);

		} else {
			// 開始時刻＜終了時刻 (#Msg_966#)
			checkTime(wkTime1.getStartTime(), wkTime1.getEndTime());
			checkTime(wkTime2.getStartTime(), wkTime2.getEndTime());
			// reason check trước đó ở hàm GenAndInspectionOfAppReason rồi
		}

	}

	private void validateRec(RecruitmentAppCommand cmd) {
		WkTimeCommand wkTime1 = cmd.getWkTime1();
		WkTimeCommand wkTime2 = cmd.getWkTime2();
		// 開始時刻＜終了時刻 (#Msg_966#)
		checkTime(wkTime1.getStartTime(), wkTime1.getEndTime());
		checkTime(wkTime2.getStartTime(), wkTime2.getEndTime());
		// reason check trước đó ở hàm GenAndInspectionOfAppReason rồi

	}

	private void checkTime(Integer startTime, Integer endTime) {
		if (startTime != null && endTime != null) {
			if (startTime > endTime) {
				throw new BusinessException("Msg_966");
			}
		}
	}

	private String GenAndInspectionOfAppReason(SaveHolidayShipmentCommand command, String companyID,
			ApplicationType appType) {
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingRepository
				.getAppTypeDiscreteSettingByAppType(companyID, appType.value).get();
		String appReason = Strings.EMPTY;
		String typicalReason = Strings.EMPTY;
		String displayReason = Strings.EMPTY;
		if (appTypeDiscreteSetting.getTypicalReasonDisplayFlg().equals(AppDisplayAtr.DISPLAY)) {
			typicalReason += command.getAppCmd().getAppReasonText();
		}
		if (appTypeDiscreteSetting.getDisplayReasonFlg().equals(AppDisplayAtr.DISPLAY)) {
			if (Strings.isNotBlank(typicalReason)) {
				displayReason += System.lineSeparator();
			}
			displayReason += command.getAppCmd().getApplicationReason();
		}
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository
				.getApplicationSettingByComID(companyID);
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		if (appTypeDiscreteSetting.getTypicalReasonDisplayFlg().equals(AppDisplayAtr.DISPLAY)
				&& appTypeDiscreteSetting.getDisplayReasonFlg().equals(AppDisplayAtr.DISPLAY)) {
			if (applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)
					&& Strings.isBlank(typicalReason + displayReason)) {
				throw new BusinessException("Msg_115");
			}
		}
		appReason = typicalReason + displayReason;

		return appReason;
	}

}
