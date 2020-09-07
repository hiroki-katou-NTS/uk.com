package nts.uk.ctx.at.request.app.find.application.businesstrip;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto.ApproveTripRequestParam;
import nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto.DetailScreenInfo;
import nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripMobileDto.StartScreenBDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoOutputDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.DetailScreenDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripWorkTypes;
import nts.uk.ctx.at.request.dom.application.businesstrip.service.BusinessTripService;
import nts.uk.ctx.at.request.dom.application.businesstrip.service.DetailScreenB;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.BusinessTripAppWorkType;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSet;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSetRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class BusinessTripMobileFinder {

    @Inject
    private BusinessTripRepository businessTripRepository;

    @Inject
    private BusinessTripService businessTripService;

    @Inject
    private DetailAppCommonSetService appCommonSetService;

    @Inject
    private CommonAlgorithm commonAlgorithm;

    @Inject
    private AppTripRequestSetRepository appTripRequestSetRepo;

    @Inject
    private WorkTypeRepository wkTypeRepo;

    @Inject
    private AtEmployeeAdapter atEmployeeAdapter;


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
        businessTrip.setDepartureTime(param.getBusinessTripDto().getDepartureTime() == null ? Optional.empty() : Optional.of(param.getBusinessTripDto().getDepartureTime()));
        businessTrip.setReturnTime(param.getBusinessTripDto().getReturnTime() == null ? Optional.empty() : Optional.of(param.getBusinessTripDto().getReturnTime()));
        businessTrip.setInfos(param.getBusinessTripDto().getTripInfos().stream().map(i -> i.toDomain()).collect(Collectors.toList()));

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
        Optional<AppTripRequestSet> tripRequestSet = appTripRequestSetRepo.findById(cid);
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
                    i.getOpAchievementDetail().isPresent() ? mapWorkCds.get(i.getOpAchievementDetail().get().getWorkTypeCD()) : null
            ))
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



}
