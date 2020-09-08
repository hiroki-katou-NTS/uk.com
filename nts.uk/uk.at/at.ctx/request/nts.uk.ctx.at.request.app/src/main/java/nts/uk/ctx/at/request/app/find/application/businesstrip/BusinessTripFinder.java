package nts.uk.ctx.at.request.app.find.application.businesstrip;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.*;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.ParamUpdate;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.service.BusinessTripService;
import nts.uk.ctx.at.request.dom.application.businesstrip.service.DetailScreenB;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.*;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.BusinessTripAppWorkType;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSetRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSet;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.*;
import nts.uk.shr.com.context.AppContexts;
import org.apache.logging.log4j.util.Strings;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class BusinessTripFinder {

    @Inject
    private CommonAlgorithm commonAlgorithm;

    @Inject
    private NewBeforeRegister processBeforeRegister;

    @Inject
    private WorkTypeRepository workTypeRepository;

    @Inject
    private WorkTypeRepository wkTypeRepo;

    @Inject
    private BusinessTripService businessTripService;

    @Inject
    private AppTripRequestSetRepository appTripRequestSetRepository;

    @Inject
    private AtEmployeeAdapter atEmployeeAdapter;

    @Inject
    private DetailAppCommonSetService appCommonSetService;

    public DetailStartScreenInfoDto initKAF008(ParamStart paramStart) {
        String cid = AppContexts.user().companyId();
        DetailStartScreenInfoDto result = new DetailStartScreenInfoDto();
        List<GeneralDate> dateList = paramStart.getDateLst().stream().map(i -> GeneralDate.fromString(i, "yyyy/MM/dd")).collect(Collectors.toList());

        AppDispInfoStartupOutput appDispInfoStartupOutput = paramStart.getAppDispInfoStartupOutput().toDomain();
        // アルゴリズム「出張申請画面初期（新規）」を実行する
        BusinessTripInfoOutputDto businessTripInfoOutputDto = this.businessScreenInit_New(cid, paramStart.getApplicantList(), dateList, appDispInfoStartupOutput);
        // 申請対象日リスト全ての日付に対し「表示する実績内容」が存在する
        // Check xem có ngày nào không có content không, nếu có add Error msg 1695 + date
        List<ConfirmMsgOutput> listErrors = this.checkDateWithContent(businessTripInfoOutputDto.getBusinessTripActualContent());
        if (!listErrors.isEmpty()) {
            result.setBusinessTripInfoOutputDto(businessTripInfoOutputDto);
            result.setResult(false);
            result.setConfirmMsgOutputs(listErrors);
            return result;
        }
        result.setResult(true);
        result.setBusinessTripInfoOutputDto(businessTripInfoOutputDto);
        return result;

        //Dummy data
//        DetailStartScreenInfoDto dummy = new DetailStartScreenInfoDto();
//        dummy.setResult(true);
//        dummy.setConfirmMsgOutputs(Collections.emptyList());
//        BusinessTripInfoOutputDto dummyContent = new BusinessTripInfoOutputDto();
//        List<BusinessTripActualContentDto> dummyActualContent = new ArrayList<>();
//        AchievementDetailDto dummyDetail = new AchievementDetailDto();
//        dummyDetail.setWorkTypeCD("001");
//        dummyDetail.setWorkTimeCD("001");
//        dummyDetail.setOpWorkTypeName("勤務種類");
//        dummyDetail.setOpWorkTimeName("就業時間帯");
//        dummyDetail.setOpWorkTime(480);
//        dummyDetail.setOpLeaveTime(1020);
//        GeneralDate baseDateDummy = GeneralDate.today();
//        for(int i = 0 ; i < 12; i ++) {
//            BusinessTripActualContentDto eachDetail = new BusinessTripActualContentDto();
//            eachDetail.setDate(baseDateDummy.addDays(i).toString());
//            eachDetail.setOpAchievementDetail(dummyDetail);
//            dummyActualContent.add(eachDetail);
//        }
//        dummyContent.setBusinessTripActualContent(dummyActualContent);
//        dummy.setBusinessTripInfoOutputDto(dummyContent);
//        dummy.setConfirmMsgOutputs(Collections.emptyList());
//        return dummy;
    }

    private List<ConfirmMsgOutput> checkDateWithContent(List<BusinessTripActualContentDto> contents) {
        List<ConfirmMsgOutput> result = new ArrayList<>();
        if (!contents.isEmpty()) {
            List<BusinessTripActualContentDto> itemNotHaveConent = contents.stream().filter(i -> i.getOpAchievementDetail() == null).collect(Collectors.toList());
            if (!itemNotHaveConent.isEmpty()) {
                ConfirmMsgOutput msg = new ConfirmMsgOutput();
                msg.setMsgID("Msg_1695");
                msg.setParamLst(itemNotHaveConent.stream().map(i -> i.getDate().toString()).collect(Collectors.toList()));
                result.add(msg);
            }
        }
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
                opEmploymentSet.get(),
                EnumAdaptor.valueOf(BusinessTripAppWorkType.WORK_DAY.value, BusinessTripAppWorkType.class),
                new ArrayList<>(Arrays.asList(WorkTypeClassification.Attendance))
        );
        // アルゴリズム「出張申請勤務種類を取得する」を実行する
        List<WorkType> holidayWorkType = businessTripService.getBusinessAppWorkType(
                opEmploymentSet.get(),
                EnumAdaptor.valueOf(BusinessTripAppWorkType.HOLIDAY.value, BusinessTripAppWorkType.class),
                new ArrayList<>(Arrays.asList(WorkTypeClassification.Holiday, WorkTypeClassification.HolidayWork, WorkTypeClassification.Shooting))
        );
        // アルゴリズム「出張申請未承認申請を取得」を実行する
        List<ActualContentDisplay> actualContentDisplays = businessTripService.getBusinessTripNotApproved(sid, appDate, appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst());

        // Actual content get từ AppDispInfoStartupOutput đang default là optional empty
         Optional<List<ActualContentDisplay>> appActualContent = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst();

        List<BusinessTripWorkTypes> businessTripWorkTypes = new ArrayList<>();

//        if (appActualContent.isPresent()) {
        if (!actualContentDisplays.isEmpty()) {
            List<String> cds = actualContentDisplays.stream().filter(i -> i.getOpAchievementDetail().isPresent())
                    .map(i -> i.getOpAchievementDetail().get().getWorkTypeCD())
                    .distinct()
                    .collect(Collectors.toList());
            // ドメインモデル「勤務種類」を取得する
            Map<String, WorkType> mapWorkCds = wkTypeRepo.getPossibleWorkType(cid, cds).stream().collect(Collectors.toMap(i -> i.getWorkTypeCode().v(), i -> i));
            businessTripWorkTypes = actualContentDisplays.stream().map(i -> new BusinessTripWorkTypes(
                    i.getDate(),
                    i.getOpAchievementDetail().isPresent() ? mapWorkCds.get(i.getOpAchievementDetail().get().getWorkTypeCD()) : null))
                    .collect(Collectors.toList());
        }
        // 取得した情報をOUTPUT「出張申請の表示情報」にセットしてを返す
        BusinessTripInfoOutput output = new BusinessTripInfoOutput(
                tripRequestSet.isPresent() ? tripRequestSet.get() : null,
                appDispInfoStartupOutput,
                Optional.of(workDays),
                Optional.of(holidayWorkType),
                Optional.of(actualContentDisplays),
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
                Optional.of(new AppReason(applicationDto.getOpAppReason())),
                Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())
                ));

        BusinessTripInfoOutput output = param.getBusinessTripInfoOutput().toDomain();

        Optional<ApplicationDate> appStartDate = application.getOpAppStartDate();
        Optional<ApplicationDate> appEndDate = application.getOpAppEndDate();
        DatePeriod period = new DatePeriod(appStartDate.get().getApplicationDate(), appEndDate.get().getApplicationDate());
        List<GeneralDate> lstDate = period.datesBetween();

        // アルゴリズム「2-1.新規画面登録前の処理」を実行する
//        confirmMsgOutputs = processBeforeRegister.processBeforeRegister_New(
//                AppContexts.user().companyId(),
//                EmploymentRootAtr.APPLICATION,
//                true,
//                application,
//                null,
//                output.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpErrorFlag().get(),
//                Collections.emptyList(),
//                output.getAppDispInfoStartup()
//        );

        BusinessTrip businessTrip = param.getBusinessTrip().toDomain(application);

        if (confirmMsgOutputs.isEmpty()) {
            // アルゴリズム「出張申請個別エラーチェック」を実行する
            if (businessTrip.getInfos().isEmpty()) {
                throw new BusinessException("Msg_1703");
            }
            // loop 年月日　in　期間
            businessTrip.getInfos().stream().forEach(i -> {
                String wkTypeCd = i.getWorkInformation().getWorkTypeCode().v();
                String wkTimeCd = i.getWorkInformation().getWorkTimeCode() == null ? null : i.getWorkInformation().getWorkTimeCode().v();
                // アルゴリズム「出張申請就業時間帯チェック」を実行する
                businessTripService.checkInputWorkCode(wkTypeCd, wkTimeCd, i.getDate());

                List<EmployeeInfoImport> employeeInfoImports = atEmployeeAdapter.getByListSID(Arrays.asList(sid));
                // 申請の矛盾チェック
//                this.commonAlgorithm.appConflictCheck(
//                        cid,
//                        employeeInfoImports.get(0),
//                        lstDate,
//                        new ArrayList<>(Arrays.asList(i.getWorkInformation().getWorkTypeCode().v())),
//                        output.getActualContentDisplay().get()
//                );
            });
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
        Application application = Application.createFromNew(
                EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
                applicationDto.getEmployeeID() == null ? loginSid : applicationDto.getEmployeeID(),
                EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
                new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")),
                loginSid,
                Optional.empty(),
                Optional.empty(),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                Optional.of(new AppReason(applicationDto.getOpAppReason())),
                Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())
                ));
        DatePeriod dates = new DatePeriod(application.getOpAppStartDate().get().getApplicationDate(), application.getOpAppEndDate().get().getApplicationDate());
        List<GeneralDate> inputDates = dates.datesBetween();

        // 申請対象日リスト全ての日付に対し「表示する実績内容」が存在する
        // エラーメッセージとして「#Msg_1695」を返す({0}＝年月日)
//        if (!inputDates.isEmpty()) {
//            inputDates.stream().forEach(i -> {
//                if (appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent()) {
//                    Optional<ActualContentDisplay> opContent = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get()
//                            .stream().filter(x -> x.getDate() == i)
//                            .findAny();
//                    if (!opContent.isPresent()) {
//                        throw new BusinessException("Msg_1695", i.toString());
//                    }
//                 } else {
//                    throw new BusinessException("Msg_1695", i.toString());
//                }
//            });
//        }

//        List<ActualContentDisplay> appActualContents = businessTripService.getBusinessTripNotApproved(
//                loginSid, inputDates, appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst()
//        );

        List<ActualContentDisplay> appActualContents = Collections.emptyList();

        List<BusinessTripWorkTypes> businessTripWorkTypes = new ArrayList<>();
        if (!appActualContents.isEmpty()) {
            List<String> cds = appActualContents.stream().filter(i -> i.getOpAchievementDetail().isPresent())
                    .map(i -> i.getOpAchievementDetail().get().getWorkTypeCD())
                    .distinct()
                    .collect(Collectors.toList());
            // ドメインモデル「勤務種類」を取得する
            Map<String, WorkType> mapWorkCds = wkTypeRepo.getPossibleWorkType(cid, cds).stream().collect(Collectors.toMap(i -> i.getWorkTypeCode().v(), i -> i));
            businessTripWorkTypes = appActualContents.stream().map(i -> new BusinessTripWorkTypes(
                    i.getDate(),
                    i.getOpAchievementDetail().isPresent() ? mapWorkCds.get(i.getOpAchievementDetail().get().getWorkTypeCD()) : null
            )).collect(Collectors.toList());
        }

        tripRequestInfoOutput.setActualContentDisplay(Optional.of(appActualContents));
        tripRequestInfoOutput.setWorkTypeBeforeChange(Optional.of(businessTripWorkTypes));
        result.setResult(true);
        result.setBusinessTripInfoOutputDto(BusinessTripInfoOutputDto.convertToDto(tripRequestInfoOutput));

//        return result;
        //Dummy Data
        return this.dummyDataForTest(inputDates, businessTripInfoOutputDto);
    }

    public DetailStartScreenInfoDto dummyDataForTest(List<GeneralDate> inputDates, BusinessTripInfoOutputDto businessTripInfoOutputDto) {
        String cid = AppContexts.user().companyId();
        DetailStartScreenInfoDto result = new DetailStartScreenInfoDto();

        BusinessTripInfoOutput tripRequestInfoOutput = businessTripInfoOutputDto.toDomain();

        List<BusinessTripActualContentDto> dummyActualContent = new ArrayList<>();
        AchievementDetailDto dummyDetail = new AchievementDetailDto();
        dummyDetail.setWorkTypeCD("001");
        dummyDetail.setWorkTimeCD("001");
        dummyDetail.setOpWorkTypeName("勤務種類");
        dummyDetail.setOpWorkTimeName("就業時間帯");
        dummyDetail.setOpWorkTime(480);
        dummyDetail.setOpLeaveTime(1020);
        inputDates.stream().forEach(i -> {
            BusinessTripActualContentDto eachDetail = new BusinessTripActualContentDto();
            eachDetail.setDate(i.toString());
            eachDetail.setOpAchievementDetail(dummyDetail);
            dummyActualContent.add(eachDetail);
        });

        List<BusinessTripWorkTypes> businessTripWorkTypes = new ArrayList<>();

        List<String> cds = dummyActualContent.stream()
                .map(i -> i.getOpAchievementDetail().getWorkTypeCD())
                .distinct()
                .collect(Collectors.toList());

        Map<String, WorkType> mapWorkCds = wkTypeRepo.getPossibleWorkType(cid, cds).stream().collect(Collectors.toMap(i -> i.getWorkTypeCode().v(), i -> i));

        businessTripWorkTypes = dummyActualContent.stream().map(i -> new BusinessTripWorkTypes(
                GeneralDate.fromString(i.getDate(), "yyyy/MM/dd"),
                i.getOpAchievementDetail().getWorkTypeCD() != null ? mapWorkCds.get(i.getOpAchievementDetail().getWorkTypeCD()) : null
        )).collect(Collectors.toList());

        tripRequestInfoOutput.setActualContentDisplay(Optional.of(Collections.emptyList()));
        tripRequestInfoOutput.setWorkTypeBeforeChange(Optional.of(businessTripWorkTypes));
        result.setResult(true);
        result.setBusinessTripInfoOutputDto(BusinessTripInfoOutputDto.convertToDto(tripRequestInfoOutput));
        // Rewrite dummy content
        result.getBusinessTripInfoOutputDto().setBusinessTripActualContent(dummyActualContent);

        return result;
    }

    public BusinessTripInfoOutputDto changeWorkTypeCode(ChangeWorkCodeParam changeWorkCodeParam) {
        String cid = AppContexts.user().companyId();
        if (Strings.isBlank(changeWorkCodeParam.getTypeCode())) {
            throw new BusinessException("Msg_1329");
        }
        BusinessTripInfoOutput businessTripInfoOutput = changeWorkCodeParam.getBusinessTripInfoOutputDto().toDomain();
        GeneralDate inputDate = GeneralDate.fromString(changeWorkCodeParam.getDate(), "yyyy/MM/dd");
        String inputCode = changeWorkCodeParam.getTypeCode();
        List<WorkType> workTypesBeforeChange = new ArrayList<>();
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
                BusinessTripWorkTypes itemAfterChange = new BusinessTripWorkTypes(inputDate, getWorkTypeInfo.get());
                if (businessTripInfoOutput.getWorkTypeAfterChange().get().contains(itemAfterChange)) {
                    int index = businessTripInfoOutput.getWorkTypeAfterChange().get().indexOf(itemAfterChange);
                    businessTripInfoOutput.getWorkTypeAfterChange().get().set(index, itemAfterChange);
                } else {
                    businessTripInfoOutput.getWorkTypeAfterChange().get().add(itemAfterChange);
                }
            }
        } else {
            throw new BusinessException("Msg_457");
        }
        return BusinessTripInfoOutputDto.convertToDto(businessTripInfoOutput);
    }

    /**
     *
     * @param businessTripInfoOutput
     * @param workType
     * @return
     */
    private List<WorkType> getBusinessTripWorkChangeInfo(BusinessTripInfoOutput businessTripInfoOutput, WorkType workType) {
        Boolean businessTripWorkCls = getBusinessTripClsContent(workType);
        if (businessTripWorkCls) {
            // 勤務種類リスト＝出張申請の表示情報.出勤日勤務種類リスト
            return businessTripInfoOutput.getWorkDayCds().get();
        } else {
            // 勤務種類リスト＝出張申請の表示情報.休日勤務種類リスト
            return businessTripInfoOutput.getHolidayCds().get();
        }
    }

    /**
     * アルゴリズム「出張申請勤務種類分類内容取得」を実行する
     * @param workType
     * @return
     */
    private boolean getBusinessTripClsContent(WorkType workType) {
        Boolean result = true;
        val workCls = workType.getDailyWork().getWorkTypeUnit();
        List<WorkTypeClassification> workDays = Arrays.asList(
                WorkTypeClassification.Attendance, // 出勤
                WorkTypeClassification.AnnualHoliday, // 年休
                WorkTypeClassification.YearlyReserved, // 積立年休
                WorkTypeClassification.SpecialHoliday, // 特別年休
                WorkTypeClassification.Absence, // 欠勤
                WorkTypeClassification.SubstituteHoliday, // 代休
                WorkTypeClassification.Pause, // 振休
                WorkTypeClassification.TimeDigestVacation // 時間消化休暇
        );
        List<WorkTypeClassification> holidays = Arrays.asList(
                WorkTypeClassification.Holiday, // 休日
                WorkTypeClassification.HolidayWork, // 休日出勤
                WorkTypeClassification.Shooting // 振出
        );
        switch (workCls) {
            case OneDay:
                WorkTypeClassification workTypeClassification = workType.getDailyWork().getOneDay();
                if (workDays.contains(workTypeClassification)) {
                    result = true;
                }
                if (holidays.contains(workTypeClassification)) {
                    result = false;
                }
                break;
            case MonringAndAfternoon:
                WorkTypeClassification afternoonWork = workType.getDailyWork().getAfternoon();
                WorkTypeClassification morningWork = workType.getDailyWork().getMorning();
                if (workDays.contains(morningWork) && workDays.contains(afternoonWork)) {
                    result = false;
                }
                if (holidays.contains(morningWork) && holidays.contains(afternoonWork)) {
                    result = true;
                }
                break;
        }
        return result;
    }

    public WorkTypeNameDto changeWorkTimeCode(ChangeWorkCodeParam changeWorkCodeParam) {
        WorkTypeNameDto result = new WorkTypeNameDto();
        String typeCode = changeWorkCodeParam.getTypeCode();
        String timeCode = changeWorkCodeParam.getTimeCode();
        BusinessTripInfoOutput businessTripInfoOutput = changeWorkCodeParam.getBusinessTripInfoOutputDto().toDomain();
        GeneralDate inputDate = GeneralDate.fromString(changeWorkCodeParam.getDate(), "yyyy/MM/dd");
        businessTripService.checkInputWorkCode(typeCode, timeCode, inputDate);
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
                throw new BusinessException("Msg_1685");
            }
        }
        return result;
    }

    public DetailScreenDto getDetailKAF008(ParamUpdate param) {
        //		14-1.詳細画面起動前申請共通設定を取得する
        AppDispInfoStartupOutput appDispInfoStartupOutput =
                appCommonSetService.getCommonSetBeforeDetail(param.getCompanyId(), param.getApplicationId());
        DetailScreenB detailScreen = businessTripService.getDataDetail(param.getCompanyId(), param.getApplicationId(), appDispInfoStartupOutput);
        DetailScreenDto detailScreenDto = DetailScreenDto.fromDomain(detailScreen);
        return detailScreenDto;
    }

    public boolean getFlagStartKDL003(ParamStartKDL003 param) {
        GeneralDate selectedDate = GeneralDate.fromString(param.getSelectedDate(), "yyyy/MM/dd");
        BusinessTripInfoOutput infoOutput = param.getBusinessTripInfoOutputDto().toDomain();
        WorkType selectedWorkType = infoOutput.getWorkTypeBeforeChange().get().stream().filter(i -> i.getDate().equals(selectedDate)).findFirst().get().getWorkType();
        return this.getBusinessTripClsContent(selectedWorkType);
    }


}
