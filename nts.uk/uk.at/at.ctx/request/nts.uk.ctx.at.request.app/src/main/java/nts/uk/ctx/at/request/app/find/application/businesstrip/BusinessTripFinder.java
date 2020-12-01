package nts.uk.ctx.at.request.app.find.application.businesstrip;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto.ApproveTripRequestParam;
import nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto.CheckPeriodDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto.DetailScreenInfo;
import nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto.StartScreenBDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.*;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.ParamUpdate;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.service.BusinessTripService;
import nts.uk.ctx.at.request.dom.application.businesstrip.service.DetailScreenB;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.*;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.CommonAlgorithmMobile;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.BusinessTripAppWorkType;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSetRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
import org.apache.logging.log4j.util.Strings;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class BusinessTripFinder {

    @Inject
    private CommonAlgorithm commonAlgorithm;

    @Inject
    private NewBeforeRegister processBeforeRegister;

    @Inject
    private WorkTypeRepository wkTypeRepo;

    @Inject
    private BusinessTripService businessTripService;

    @Inject
    private AppTripRequestSetRepository appTripRequestSetRepository;

    @Inject
    private DetailAppCommonSetService appCommonSetService;

    /**
     * 起動する
     * @param paramStart
     * @return
     */
    public DetailStartScreenInfoDto initKAF008(ParamStart paramStart) {
        String cid = AppContexts.user().companyId();
        DetailStartScreenInfoDto result = new DetailStartScreenInfoDto();
        List<GeneralDate> dateList = paramStart.getDateLst().stream().map(i -> GeneralDate.fromString(i, "yyyy/MM/dd")).collect(Collectors.toList());

        AppDispInfoStartupOutput appDispInfoStartupOutput = paramStart.getAppDispInfoStartupOutput().toDomain();
        // アルゴリズム「出張申請画面初期（新規）」を実行する
        BusinessTripInfoOutputDto businessTripInfoOutputDto = this.businessScreenInit_New(cid, paramStart.getApplicantList(), dateList, appDispInfoStartupOutput);
        // 申請対象日リスト全ての日付に対し「表示する実績内容」が存在する
        // Check xem có ngày nào không có content không, nếu có add Error msg 1695 + date
        Optional<BusinessTripActualContentDto> itemNotHaveConent = businessTripInfoOutputDto.getBusinessTripActualContent()
                .stream()
                .filter(i -> i.getOpAchievementDetail() == null).findFirst();
        if (itemNotHaveConent.isPresent()) {
            result.setConfirmMsgOutputs(Arrays.asList(new ConfirmMsgOutput("Msg_1695", Arrays.asList(itemNotHaveConent.get().getDate().toString()))));
            result.setResult(false);
        } else {
            result.setResult(true);
        }
        result.setBusinessTripInfoOutputDto(businessTripInfoOutputDto);
        return result;
    }

    /**
     * アルゴリズム「出張申請画面初期（新規）」を実行する
     *
     * @param cid                      会社ID
     * @param applicantList            申請者リスト
     * @param appDate                  申請対象日リスト
     * @param appDispInfoStartupOutput 申請表示情報
     */
    private BusinessTripInfoOutputDto businessScreenInit_New(String cid, List<String> applicantList, List<GeneralDate> appDate, AppDispInfoStartupOutput appDispInfoStartupOutput) {
        // ドメインモデル「出張申請設定」を取得する
        String sid = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmpHistImport().getEmployeeId();
        Optional<AppTripRequestSet> tripRequestSet = appTripRequestSetRepository.findById(cid);
        Optional<AppEmploymentSet> opEmploymentSet = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpEmploymentSet();
        // 出張申請勤務種類を取得する
        List<WorkType> workDays = businessTripService.getBusinessAppWorkType(
                opEmploymentSet,
                EnumAdaptor.valueOf(BusinessTripAppWorkType.WORK_DAY.value, BusinessTripAppWorkType.class),
                new ArrayList<>(Arrays.asList(WorkTypeClassification.Attendance))
        );
        // アルゴリズム「出張申請勤務種類を取得する」を実行する
        List<WorkType> holidayWorkType = businessTripService.getBusinessAppWorkType(
                opEmploymentSet,
                EnumAdaptor.valueOf(BusinessTripAppWorkType.HOLIDAY.value, BusinessTripAppWorkType.class),
                new ArrayList<>(Arrays.asList(WorkTypeClassification.Holiday, WorkTypeClassification.HolidayWork, WorkTypeClassification.Shooting))
        );

        Optional<List<ActualContentDisplay>> opActualContentDisplayLst = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst();

        // アルゴリズム「出張申請未承認申請を取得」を実行する
        businessTripService.getBusinessTripNotApproved(sid, appDate, opActualContentDisplayLst);

        List<BusinessTripWorkTypes> businessTripWorkTypes = new ArrayList<>();

        if (opActualContentDisplayLst.isPresent() && !opActualContentDisplayLst.get().isEmpty()) {
            List<String> cds = opActualContentDisplayLst.get().stream().filter(i -> i.getOpAchievementDetail().isPresent())
                    .map(i -> i.getOpAchievementDetail().get().getWorkTypeCD())
                    .distinct()
                    .collect(Collectors.toList());
            // ドメインモデル「勤務種類」を取得する
            Map<String, WorkType> mapWorkCds = wkTypeRepo.getPossibleWorkType(cid, cds).stream().collect(Collectors.toMap(i -> i.getWorkTypeCode().v(), i -> i));
            businessTripWorkTypes = opActualContentDisplayLst.get().stream().map(i -> new BusinessTripWorkTypes(
                    i.getDate(),
                    i.getOpAchievementDetail().isPresent() ? mapWorkCds.get(i.getOpAchievementDetail().get().getWorkTypeCD()) : null))
                    .collect(Collectors.toList());
        }
        // 取得した情報をOUTPUT「出張申請の表示情報」にセットしてを返す
        BusinessTripInfoOutput output = new BusinessTripInfoOutput(
                tripRequestSet.isPresent() ? tripRequestSet.get() : null,
                appDispInfoStartupOutput,
                Optional.of(holidayWorkType),
                Optional.of(workDays),
                opActualContentDisplayLst,
                Optional.of(businessTripWorkTypes),
                Optional.empty()
        );
        return BusinessTripInfoOutputDto.convertToDto(output);
    }

    /**
     * アルゴリズム「出張申請登録前エラーチェック」を実行する
     */
    public List<ConfirmMsgOutput> checkBeforeRegister(CheckBeforeRegisterDto param) {
        String sid = AppContexts.user().employeeId();
        String cid = AppContexts.user().companyId();
        List<ConfirmMsgOutput> confirmMsgOutputs = new ArrayList<>();

        ApplicationDto applicationDto = param.getApplication();

        Application application = Application.createFromNew(
                EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
                sid,
                EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
                new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")),
                sid,
                Optional.empty(),
                Optional.empty(),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(applicationDto.getOpAppReason())),
                applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())
                ));

        BusinessTripInfoOutput output = param.getBusinessTripInfoOutput().toDomain();

        Optional<ApplicationDate> appStartDate = application.getOpAppStartDate();
        Optional<ApplicationDate> appEndDate = application.getOpAppEndDate();
        DatePeriod period = new DatePeriod(appStartDate.get().getApplicationDate(), appEndDate.get().getApplicationDate());
        List<GeneralDate> lstDate = period.datesBetween();

        // アルゴリズム「2-1.新規画面登録前の処理」を実行する
        confirmMsgOutputs = processBeforeRegister.processBeforeRegister_New(
                AppContexts.user().companyId(),
                EmploymentRootAtr.APPLICATION,
                true,
                application,
                null,
                output.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpErrorFlag().get(),
                Collections.emptyList(),
                output.getAppDispInfoStartup()
        );

        BusinessTrip businessTrip = param.getBusinessTrip().toDomain(application);

        if (confirmMsgOutputs.isEmpty()) {
            // アルゴリズム「出張申請個別エラーチェック」を実行する
            if (businessTrip.getInfos().isEmpty()) {
                throw new BusinessException("Msg_1703");
            }
            businessTripService.businessTripIndividualCheck(
                    businessTrip.getInfos(),
                    output.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get()
            );
        }
        return confirmMsgOutputs;
    }


    /**
     * アルゴリズム「申請日を変更する処理」を実行する
     *
     * @param businessTripInfoOutputDto 出張申請の表示情報
     * @param applicationDto
     * @return
     */
    public DetailStartScreenInfoDto updateAppDate(BusinessTripInfoOutputDto businessTripInfoOutputDto, ApplicationDto applicationDto) {
        String cid = AppContexts.user().companyId();
        String loginSid = AppContexts.user().employeeId();
        DetailStartScreenInfoDto result = new DetailStartScreenInfoDto();
        BusinessTripInfoOutput tripRequestInfoOutput = businessTripInfoOutputDto.toDomain();
        AppDispInfoStartupOutput appDispInfoStartupOutput = tripRequestInfoOutput.getAppDispInfoStartup();
        GeneralDate appStartDate = GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd");
        GeneralDate appEndDate = GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd");
        DatePeriod dates = new DatePeriod(appStartDate, appEndDate);
        List<GeneralDate> inputDates = dates.datesBetween();

        if ((ChronoUnit.DAYS.between(appStartDate.localDate(), appEndDate.localDate()) + 1)  > 31){
            throw new BusinessException("Msg_277");
        }

        // 申請対象日リスト全ての日付に対し「表示する実績内容」が存在する
        // エラーメッセージとして「#Msg_1695」を返す({0}＝年月日)
        Optional<List<ActualContentDisplay>> opActualContentDisplayLst = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst();

        if (opActualContentDisplayLst.isPresent()) {
            Optional<ActualContentDisplay> dateNotHaveContent = opActualContentDisplayLst.get().stream().filter(i -> !i.getOpAchievementDetail().isPresent() || i.getOpAchievementDetail() == null).findFirst();
            if (dateNotHaveContent.isPresent()) {
                throw new BusinessException("Msg_1695", dateNotHaveContent.get().getDate().toString());
            }
        }

        businessTripService.getBusinessTripNotApproved(loginSid, inputDates, opActualContentDisplayLst);

        List<BusinessTripWorkTypes> businessTripWorkTypes = new ArrayList<>();
        if (!opActualContentDisplayLst.get().isEmpty()) {
            List<String> cds = opActualContentDisplayLst.get().stream().filter(i -> i.getOpAchievementDetail().isPresent())
                    .map(i -> i.getOpAchievementDetail().get().getWorkTypeCD())
                    .distinct()
                    .collect(Collectors.toList());
            // ドメインモデル「勤務種類」を取得する
            Map<String, WorkType> mapWorkCds = wkTypeRepo.getPossibleWorkType(cid, cds).stream().collect(Collectors.toMap(i -> i.getWorkTypeCode().v(), i -> i));
            businessTripWorkTypes = opActualContentDisplayLst.get().stream().map(i -> new BusinessTripWorkTypes(
                    i.getDate(),
                    i.getOpAchievementDetail().isPresent() ? mapWorkCds.get(i.getOpAchievementDetail().get().getWorkTypeCD()) : null
            )).collect(Collectors.toList());
        }

        tripRequestInfoOutput.setActualContentDisplay(opActualContentDisplayLst);
        tripRequestInfoOutput.setWorkTypeBeforeChange(Optional.of(businessTripWorkTypes));
        result.setResult(true);
        result.setBusinessTripInfoOutputDto(BusinessTripInfoOutputDto.convertToDto(tripRequestInfoOutput));

        return result;
    }

    /**
     * アルゴリズム「出張申請入力コードチェック」を実行する
     * @param changeWorkCodeParam
     * @return
     */
    public BusinessTripInfoOutputDto changeWorkTypeCode(ChangeWorkCodeParam changeWorkCodeParam) {
        String cid = AppContexts.user().companyId();

        BusinessTripInfoOutput businessTripInfoOutput = changeWorkCodeParam.getBusinessTripInfoOutputDto().toDomain();
        GeneralDate inputDate = GeneralDate.fromString(changeWorkCodeParam.getDate(), "yyyy/MM/dd");
        String inputCode = changeWorkCodeParam.getTypeCode();
        List<WorkType> workTypesBeforeChange = new ArrayList<>();

        // コードが未入力
        if (Strings.isBlank(changeWorkCodeParam.getTypeCode())) {
            throw new BusinessException("Msg_1329", inputDate.toString());
        }

        Optional<BusinessTripWorkTypes> currentDateWorkType = businessTripInfoOutput.getWorkTypeBeforeChange().get().stream().filter(i -> i.getDate().equals(inputDate)).findFirst();

        if (currentDateWorkType.isPresent()) {
            workTypesBeforeChange = getBusinessTripWorkChangeInfo(businessTripInfoOutput, currentDateWorkType.get().getWorkType());
        }

        // 取得した勤務種類リストの中に、INPUT.勤務種類コードが存在する
        Optional<WorkType> inputWorkType = workTypesBeforeChange.stream().filter(i -> i.getWorkTypeCode().v().equals(inputCode)).findFirst();
        if (inputWorkType.isPresent()) {
            // ドメインモデル「勤務種類」を取得する
            Optional<WorkType> getWorkTypeInfo = wkTypeRepo.findByPK(cid, inputCode);
            if (businessTripInfoOutput.getWorkTypeAfterChange().isPresent()) {
                // 変更後勤務種類にセット
                BusinessTripWorkTypes itemAfterChange = new BusinessTripWorkTypes(inputDate, getWorkTypeInfo.get());
                if (businessTripInfoOutput.getWorkTypeAfterChange().get().contains(itemAfterChange)) {
                    int index = businessTripInfoOutput.getWorkTypeAfterChange().get().indexOf(itemAfterChange);
                    businessTripInfoOutput.getWorkTypeAfterChange().get().set(index, itemAfterChange);
                } else {
                    businessTripInfoOutput.getWorkTypeAfterChange().get().add(itemAfterChange);
                }
            }
        } else {
            // #Msg_457(利用できる勤怠項目がありません。)を表示する
            throw new BusinessException("Msg_457", inputDate.toString());
        }
        return BusinessTripInfoOutputDto.convertToDto(businessTripInfoOutput);
    }

    /**
     * アルゴリズム「出張申請勤務種類分類内容取得」を実行する
     * @param businessTripInfoOutput
     * @param workType
     * @return
     */
    private List<WorkType> getBusinessTripWorkChangeInfo(BusinessTripInfoOutput businessTripInfoOutput, WorkType workType) {
        Boolean businessTripWorkCls = businessTripService.getBusinessTripClsContent(workType);
        if (businessTripWorkCls) {
            // 勤務種類リスト＝出張申請の表示情報.出勤日勤務種類リスト
            return businessTripInfoOutput.getWorkDayCds().get();
        } else {
            // 勤務種類リスト＝出張申請の表示情報.休日勤務種類リスト
            return businessTripInfoOutput.getHolidayCds().get();
        }
    }

    /**
     * 勤務種類コードを入力する
     * @param changeWorkCodeParam
     * @return
     */
    public WorkTypeNameDto changeWorkTimeCode(ChangeWorkCodeParam changeWorkCodeParam) {
        WorkTypeNameDto result = new WorkTypeNameDto();
        String typeCode = changeWorkCodeParam.getTypeCode();
        String timeCode = changeWorkCodeParam.getTimeCode();
        Integer startWorkTime = changeWorkCodeParam.getStartWorkTime();
        Integer endWorkTime = changeWorkCodeParam.getEndWorkTime();


        BusinessTripInfoOutput businessTripInfoOutput = changeWorkCodeParam.getBusinessTripInfoOutputDto().toDomain();
        GeneralDate inputDate = GeneralDate.fromString(changeWorkCodeParam.getDate(), "yyyy/MM/dd");
        businessTripService.checkInputWorkCode(typeCode, timeCode, inputDate, startWorkTime, endWorkTime);
        // アルゴリズム「出張申請就業時間帯チェック」を実行する
        if (Strings.isBlank(timeCode)) {
            return result;
        }
        val workTimeSet = businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst();
        if (workTimeSet.isPresent() && !workTimeSet.get().isEmpty()) {
            Optional<WorkTimeSetting> existWorkTimeSet = workTimeSet.get().stream().filter(i -> i.getWorktimeCode().v().equals(timeCode)).findFirst();
            if (existWorkTimeSet.isPresent()) {
                result.setName(existWorkTimeSet.get().getWorkTimeDisplayName().getWorkTimeName().v());
            } else {
                throw new BusinessException("Msg_1685", inputDate.toString());
            }
        }
        return result;
    }

    /**
     * 出張申請B画面を実行する
     * @param param
     * @return
     */
    public DetailScreenDto getDetailKAF008(ParamUpdate param) {
        // アルゴリズム「詳細画面起動前申請共通設定を取得する」を実行する
        AppDispInfoStartupOutput appDispInfoStartupOutput =
                appCommonSetService.getCommonSetBeforeDetail(param.getCompanyId(), param.getApplicationId());
        // アルゴリズム「出張申請画面初期（更新）」を実行する
        DetailScreenB detailScreen = businessTripService.getDataDetail(param.getCompanyId(), param.getApplicationId(), appDispInfoStartupOutput);
        DetailScreenDto detailScreenDto = DetailScreenDto.fromDomain(detailScreen);
        return detailScreenDto;
    }

    /**
     * アルゴリズム「出張申請勤務変更ダイアログ用情報の取得」を実行する
     * @param param
     * @return
     */
    public boolean getFlagStartKDL003(ParamStartKDL003 param) {
        GeneralDate selectedDate = GeneralDate.fromString(param.getSelectedDate(), "yyyy/MM/dd");
        BusinessTripInfoOutput infoOutput = param.getBusinessTripInfoOutputDto().toDomain();
        WorkType selectedWorkType = infoOutput.getWorkTypeBeforeChange().get().stream().filter(i -> i.getDate().equals(selectedDate)).findFirst().get().getWorkType();
        return businessTripService.getBusinessTripClsContent(selectedWorkType);
    }

    public BusinessTripOutputDto startKAFS08(AppBusinessParam appBusinessParam) {
        boolean mode = appBusinessParam.getMode();
        String cid = AppContexts.user().companyId();
        ApplicationType appType;
        String employeeID = null;
        //Kiểm tra nếu employeeID null thì lấy giá trị lúc login
        if(appBusinessParam.getEmployeeID() != null) {
            employeeID = appBusinessParam.getEmployeeID();
        }
        List<String> applicantlist = new ArrayList<String>();
        List<GeneralDate> dateList = appBusinessParam.getListDates().stream()
                .map(i -> GeneralDate.fromString(i, "yyyy/MM/dd")).collect(Collectors.toList());
        BusinessTripOutputDto result = new BusinessTripOutputDto();
        BusinessTripInfoOutput businessTripInfoOutput = appBusinessParam
                .getBusinessTripInfoOutput() == null ? null : appBusinessParam.getBusinessTripInfoOutput().toDomain();
        BusinessTrip businessTrip = appBusinessParam.getBusinessTrip() == null ? null : appBusinessParam.getBusinessTrip().toDomain(businessTripInfoOutput.getAppDispInfoStartup().getAppDetailScreenInfo().get().getApplication());
        // new mode thì thực hiện thuật toán 申請共通起動処理
        if (mode) {
            AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(cid,
            		ApplicationType.BUSINESS_TRIP_APPLICATION, Arrays.asList(AppContexts.user().employeeId()),dateList, mode, Optional.ofNullable(null),
                    Optional.ofNullable(null));
            BusinessTripInfoOutputDto businessTripInfoOutputDto = this.businessScreenInit_New(cid, applicantlist,
                    dateList, appDispInfoStartupOutput);
            result.setBusinessTripInfoOutput(businessTripInfoOutputDto);
            // INPUT「出張申請の表示情報」と「出張申請」を返す
        } else {
            result.setBusinessTrip(BusinessTripDto.fromDomain(businessTrip));
            result.setBusinessTripInfoOutput(BusinessTripInfoOutputDto.convertToDto(businessTripInfoOutput));
        }
        return result;
    }

    /**
     * 「KAFS08_出張申請（スマホ）」のB画面を起動する
     * @param param StartScreenBDto
     * @return
     */
    public DetailScreenDto startScreenB(StartScreenBDto param) {
        String cid = AppContexts.user().companyId();
        AppDispInfoStartupOutput appDispInfoStartupOutput = param.getAppDispInfoStartup().toDomain();
        DetailScreenB detailScreen = businessTripService.getDataDetail(cid, param.getAppId(), appDispInfoStartupOutput);
        DetailScreenDto detailScreenDto = DetailScreenDto.fromDomain(detailScreen);
        return detailScreenDto;
    }

    /**
     *
     * @param param
     * @return
     */
    public DetailScreenInfo approveTripRequest(ApproveTripRequestParam param) {
        String cid = AppContexts.user().companyId();
        DetailScreenInfo result = new DetailScreenInfo();
        List<GeneralDate> lstDate = param.getListDate().stream().map(i -> GeneralDate.fromString(i,"")).collect(Collectors.toList());
        boolean newMode = param.isNewMode();
        if (newMode) {
            AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(
                    cid,
                    ApplicationType.BUSINESS_TRIP_APPLICATION,
                    param.getEmployeeIds(),
                    lstDate,
                    true,
                    Optional.empty(),
                    Optional.empty()
            );
            BusinessTripInfoOutputDto businessTripInfoOutput = this.businessScreenInit_New(cid, param.getEmployeeIds(), lstDate, appDispInfoStartupOutput);
            result.setBusinessTripInfoOutput(businessTripInfoOutput);
        } else {
            result.setBusinessTripInfoOutput(param.getBusinessTripInfoOutput());
            result.setBusinessTrip(param.getBusinessTrip());
        }
        return result;
    }

    /**
     * アルゴリズム「出張申請個別エラーチェック」を実行する
     */
    public void checkBeforeRegisterMobile(DetailScreenDto param) {
        String sid = AppContexts.user().employeeId();
        String cid = AppContexts.user().companyId();
        List<ConfirmMsgOutput> confirmMsgOutputs = new ArrayList<>();

        BusinessTrip businessTrip = new BusinessTrip();
        businessTrip.setDepartureTime(param.getBusinessTripDto().getDepartureTime() == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(param.getBusinessTripDto().getDepartureTime())));
        businessTrip.setReturnTime(param.getBusinessTripDto().getReturnTime() == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(param.getBusinessTripDto().getReturnTime())));
        businessTrip.setInfos(param.getBusinessTripDto().getTripInfos().stream().map(i -> i.toDomain()).collect(Collectors.toList()));

        BusinessTripInfoOutput businessTripInfoOutput = param.getBusinessTripInfoOutputDto().toDomain();

        if (businessTrip.getInfos().isEmpty()) {
            throw new BusinessException("Msg_1703");
        }

        businessTripService.businessTripIndividualCheck(
                businessTrip.getInfos(),
                businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get());
    }

    /**
     * 次へをクリックして勤務内容を表示する
     * @param param
     */
    public DetailStartScreenInfoDto mobilePeriodCheck(CheckPeriodDto param) {

        String cid = AppContexts.user().companyId();
        String sid = AppContexts.user().employeeId();
        List<ConfirmMsgOutput> confirmMsgOutputs = new ArrayList<>();
        DetailStartScreenInfoDto result = new DetailStartScreenInfoDto();

        if(param.getIsNewMode()) {

            ApplicationDto applicationDto = param.getApplication();
            Application application = Application.createFromNew(
                    EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
                    sid,
                    EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
                    new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")),
                    sid,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                    Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                    applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(applicationDto.getOpAppReason())),
                    applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())
                    ));
            BusinessTripInfoOutput businessTripInfoOutput = param.getBusinessTripInfoOutput().toDomain();
            List<GeneralDate> dates = new DatePeriod(application.getOpAppStartDate().get().getApplicationDate(), application.getOpAppEndDate().get().getApplicationDate()).datesBetween();

            // アルゴリズム「2-1.新規画面登録前の処理」を実行する
            confirmMsgOutputs = processBeforeRegister.processBeforeRegister_New(
                    AppContexts.user().companyId(),
                    EmploymentRootAtr.APPLICATION,
                    true,
                    application,
                    null,
                    businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpErrorFlag().get(),
                    Collections.emptyList(),
                    businessTripInfoOutput.getAppDispInfoStartup()
            );

            // アルゴリズム「申請日を変更する処理」を実行する
            AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.changeAppDateProcess(cid, dates,
                    ApplicationType.BUSINESS_TRIP_APPLICATION, businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput(),
                    businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput(), Optional.empty());

            Optional<List<ActualContentDisplay>> opActualContentDisplayLst = appDispInfoWithDateOutput.getOpActualContentDisplayLst();


            if (opActualContentDisplayLst.isPresent()) {
                // 申請対象日リスト全ての日付に対し「表示する実績内容」が存在する
                val dateNotHaveContent = opActualContentDisplayLst.get().stream()
                        .filter(i -> !i.getOpAchievementDetail().isPresent() || i.getOpAchievementDetail().get() == null)
                        .findAny();
                if (dateNotHaveContent.isPresent()) {
                    // エラーメッセージとして「#Msg_1695」を返す({0}＝年月日)
                    throw new BusinessException("Msg_1695", dateNotHaveContent.get().getDate().toString());
                }

                businessTripService.getBusinessTripNotApproved(sid, dates, opActualContentDisplayLst);

                List<BusinessTripWorkTypes> businessTripWorkTypes = new ArrayList<>();
                if (!opActualContentDisplayLst.get().isEmpty()) {
                    List<String> cds = opActualContentDisplayLst.get().stream().filter(i -> i.getOpAchievementDetail().isPresent())
                            .map(i -> i.getOpAchievementDetail().get().getWorkTypeCD())
                            .distinct()
                            .collect(Collectors.toList());
                    // ドメインモデル「勤務種類」を取得する
                    Map<String, WorkType> mapWorkCds = wkTypeRepo.getPossibleWorkType(cid, cds).stream().collect(Collectors.toMap(i -> i.getWorkTypeCode().v(), i -> i));
                    businessTripWorkTypes = opActualContentDisplayLst.get().stream().map(i -> new BusinessTripWorkTypes(
                            i.getDate(),
                            i.getOpAchievementDetail().isPresent() ? mapWorkCds.get(i.getOpAchievementDetail().get().getWorkTypeCD()) : null
                    )).collect(Collectors.toList());
                }

                businessTripInfoOutput.setActualContentDisplay(appDispInfoWithDateOutput.getOpActualContentDisplayLst());
                businessTripInfoOutput.setWorkTypeBeforeChange(Optional.of(businessTripWorkTypes));

                result.setResult(true);
                result.setConfirmMsgOutputs(confirmMsgOutputs);
                result.setBusinessTripInfoOutputDto(BusinessTripInfoOutputDto.convertToDto(businessTripInfoOutput));
            }
        }
        return result;
    }

}
