package nts.uk.file.at.app.export.worktype;

import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeSetRepository;
import nts.uk.ctx.at.request.dom.applicationreflect.AppReflectExeConditionRepository;
import nts.uk.ctx.at.request.dom.applicationreflect.AppReflectExecutionCondition;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.UseDivision;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.*;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.DeadlineCriteria;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.AppLimitSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.ReceptionRestrictionSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.AtWorkAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.Time36AgreeCheckRegister;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.ApplicationStampSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHdWorkAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSetRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSetRepository;
import nts.uk.ctx.at.shared.app.find.workrule.closure.ClosureHistoryFinder;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryFindDto;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.GoBackReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWorkRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp.SubLeaveAppReflectRepository;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuNameExport;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuNameQuery;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuPub;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.ApprovalSettingDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.ApprovalSettingFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.JobAssignSettingDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.JobAssignSettingFinder;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID(value = "PreparationBeforeApply")
public class PreparationBeforeApplyExportImpl implements MasterListData {

    private static final String COLUMN_NO_HEADER = "COLUMN_NO_HEADER_";
    private static final String CHECK = "○";
    private static final String NOT_CHECK = "-";
    private static final int ROW_SIZE_A4 = 4;
    private static final int ROW_SIZE_A5 = 3;
    private static final int ROW_SIZE_A9 = 4;
    private static final int MAIN_COL_SIZE = 6;
    private static final int REASON_COL_SIZE = 5;
    private static final int PRESENT_APP_COL_SIZE = 3;
    private static final int OVERTIME_COL_SIZE = 4;

    @Inject
    private PreparationBeforeApplyRepository preparationBeforeApplyRepository;

    @Inject
    private PreparationBeforeApplyExport preparationBeforeApply;

    @Inject
    private ApprovalFunctionConfigExport approvalFunctionConfigExport;

    @Inject
    private ApprovalFunctionConfigRepository approvalRepository;

    @Inject
    private EmploymentApprovalSettingExport employmentApprovalSettingExport;

    @Inject
    private ClosureHistoryFinder closureHistoryFinder;

    @Inject
    private AppWorkChangeSetRepository appWorkChangeSetRepo;

    @Inject
    private JobAssignSettingFinder jobFinder;

    @Inject
    private ApprovalSettingFinder approvalSettingFinder;

    @Inject
    private StandardMenuPub menuPub;

    @Inject
    private ApplicationSettingRepository appSettingRepo;

    @Inject
    private DisplayReasonRepository displayReasonRepo;

    @Inject
    private OvertimeAppSetRepository overtimeAppSetRepo;

    @Inject
    private AppReflectExeConditionRepository appReflectConditionRepo;

    @Inject
    private GoBackReflectRepository goBackReflectRepo;

    @Inject
    private LateEarlyCancelAppSetRepository lateEarlyCancelRepo;

    @Inject
    private ApplicationStampSettingRepository appStampSettingRepo;

    @Inject
    private ApprovalListDispSetRepository approvalListDispSetRepo;

    @Inject
    private AppTripRequestSetRepository appTripRequestSetRepo;

    @Inject
    private AppEmailSetRepository appEmailSetRepo;

    @Inject
    private HolidayApplicationSettingRepository holidayApplicationSettingRepo;

    @Inject
    private VacationApplicationReflectRepository holidayApplicationReflectRepo;

    @Inject
    private HolidayWorkAppSetRepository holidayWorkAppSetRepo;

    @Inject
    private TimeLeaveAppReflectRepository timeLeaveAppReflectRepo;

    @Inject
    private OptionalItemAppSetRepository optionalItemAppSetRepo;

    @Inject
    private SubstituteHdWorkAppSetRepository substituteHdWorkAppSetRepo;

    @Inject
    private AppReflectOtHdWorkRepository otHdWorkAppReflectRepo;

    @Inject
    private SubLeaveAppReflectRepository substituteLeaveAppReflectRepo;

    @Inject
    private SubstituteWorkAppReflectRepository substituteWorkAppReflectRepo;

    @Inject
    private LateEarlyCancelReflectRepository lateEarlyCancelReflectRepo;

    @Inject
    private StampAppReflectRepository stampAppReflectRepo;

    @Inject
    private AppReasonStandardRepository appReasonStandardRepo;

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0) {
        return getCommonHeaderColumns(MAIN_COL_SIZE);
    }

    private List<MasterHeaderColumn> getCommonHeaderColumns(int colSize) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        for (int i = 0; i < colSize; i++) {
            String columnText;
            if (i == 0) columnText = TextResource.localize("KAF022_454");
            else if (i == colSize - 1) columnText = TextResource.localize("KAF022_455");
            else columnText = "";
            columns.add(
                    new MasterHeaderColumn(
                            COLUMN_NO_HEADER + i,
                            columnText,
                            ColumnTextAlign.LEFT,
                            "",
                            true
                    )
            );
        }
        return columns;
    }

    private List<MasterData> getDataA4(List<AppDeadlineSetting> appDeadlineSetLst) {
        List<MasterData> data = new ArrayList<>();
        List<ClosureHistoryFindDto> listClosures = closureHistoryFinder.findAll();
        for (int t = 0; t < listClosures.size(); t++) {
            ClosureHistoryFindDto closureDto = listClosures.get(t);
            Optional<AppDeadlineSetting> appDeadlineSetting = appDeadlineSetLst.stream().filter(i -> i.getClosureId() == closureDto.getId()).findFirst();
            for (int row = 0; row < ROW_SIZE_A4; row++) {
                Map<String, MasterCellData> rowData = new HashMap<>();
                for (int col = 0; col < MAIN_COL_SIZE; col++) {
                    String value;
                    if (col == 0 && row == 0 && t == 0) value = TextResource.localize("KAF022_456");
                    else if (col == 1 && row == 0) value = closureDto.getId().toString();
                    else if (col == 2) {
                        if (row == 0) value = TextResource.localize("KAF022_457");
                        else if (row == 1) value = TextResource.localize("KAF022_458");
                        else if (row == 2) value = TextResource.localize("KAF022_459");
                        else value = "";
                    } else if (col == 5) {
                        switch (row) {
                            case 0:
                                value = closureDto.getName();
                                break;
                            case 1:
                                value = appDeadlineSetting.isPresent()
                                        ? (appDeadlineSetting.get().getUseAtr() == UseDivision.TO_USE ? CHECK : NOT_CHECK)
                                        : "";
                                break;
                            case 2:
                                value = appDeadlineSetting.isPresent()
                                        ? (appDeadlineSetting.get().getDeadlineCriteria() == DeadlineCriteria.CALENDAR_DAY
                                        ? TextResource.localize("KAF022_321") + TextResource.localize("KAF022_508")
                                        : TextResource.localize("KAF022_322") + TextResource.localize("KAF022_508"))
                                        : "";
                                break;
                            case 3:
                                if (appDeadlineSetting.isPresent()) {
                                    int resourceId = 323 + appDeadlineSetting.get().getDeadline().v();
                                    String resource = "KAF022_" + resourceId;
                                    value = TextResource.localize(resource) + TextResource.localize("KAF022_509");
                                } else value = "";
                                break;
                            default:
                                value = "";
                        }
                    } else value = "";

                    rowData.put(
                            COLUMN_NO_HEADER + col,
                            MasterCellData.builder()
                                    .columnId(COLUMN_NO_HEADER + col)
                                    .value(value)
                                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                    .build());
                }

                data.add(MasterData.builder().rowData(rowData).build());
            }
        }
        return data;
    }

    private List<MasterData> getDataA7(List<ReceptionRestrictionSetting> receptionRestrictionSettings) {
        receptionRestrictionSettings.sort(Comparator.comparing(ReceptionRestrictionSetting::getAppType));
        List<MasterData> data = new ArrayList<>();
        for (int t = 0; t < receptionRestrictionSettings.size(); t++) {
            ReceptionRestrictionSetting setting = receptionRestrictionSettings.get(t);
            int maxRow = setting.getAppType() == ApplicationType.OVER_TIME_APPLICATION ? 7 : 3;
            for (int row = 0; row < maxRow; row++) {
                Map<String, MasterCellData> rowData = new HashMap<>();
                for (int col = 0; col < MAIN_COL_SIZE; col++) {
                    rowData.put(
                            COLUMN_NO_HEADER + col,
                            MasterCellData.builder()
                                    .columnId(COLUMN_NO_HEADER + col)
                                    .value(getValueA7(col, row, t, maxRow - 1, setting))
                                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                    .build());
                }
                data.add(MasterData.builder().rowData(rowData).build());
            }
        }
        return data;
    }

    private String getValueA7(int col, int row, int block, int lastRow, ReceptionRestrictionSetting setting) {
        if (col == 0 && row == 0 && block == 0) return TextResource.localize("KAF022_466");

        if (col == 1 && row == 0) return setting.getAppType().name;

        if (col == 2 && row == 0) return TextResource.localize("KAF022_467");
        if (col == 2 && row == lastRow) return TextResource.localize("KAF022_476");

        if (col == 3 && row == 0) return TextResource.localize("KAF022_468");
        if (lastRow > 2) {
            if (col == 3 && row == 1) return TextResource.localize("KAF022_469");
            if (col == 3 && row == 2) return TextResource.localize("KAF022_470");
            if (col == 3 && row == 3) return TextResource.localize("KAF022_472");

            if (col == 4 && row == 2) return TextResource.localize("KAF022_471");
            if (col == 4 && row == 3) return TextResource.localize("KAF022_473");
            if (col == 4 && row == 4) return TextResource.localize("KAF022_474");
            if (col == 4 && row == 5) return TextResource.localize("KAF022_475");

            if (col == MAIN_COL_SIZE - 1) {
                if (row == 0) return setting.getOtAppBeforeAccepRestric().get().isToUse() ? CHECK : NOT_CHECK;
                if (row == 1)
                    return setting.getOtAppBeforeAccepRestric().get().getMethodCheck() == BeforeAddCheckMethod.CHECK_IN_DAY ? TextResource.localize("KAF022_63") : TextResource.localize("KAF022_66");
                if (row == 2)
                    return setting.getOtAppBeforeAccepRestric().get().getDateBeforehandRestrictions().name + TextResource.localize("KAF022_510");
                if (row == 3)
                    return setting.getOtAppBeforeAccepRestric().get().getOpEarlyOvertime().map(val -> convertToTime(val.v()) + TextResource.localize("KAF022_510")).orElse("");
                if (row == 4)
                    return setting.getOtAppBeforeAccepRestric().get().getOpNormalOvertime().map(val -> convertToTime(val.v()) + TextResource.localize("KAF022_510")).orElse("");
                if (row == 5)
                    return setting.getOtAppBeforeAccepRestric().get().getOpEarlyNormalOvertime().map(val -> convertToTime(val.v()) + TextResource.localize("KAF022_510")).orElse("");
                return setting.getAfterhandRestriction().isAllowFutureDay() ? CHECK : NOT_CHECK;
            }
        } else {
            if (col == 3 && row == 2) return TextResource.localize("KAF022_477");

            if (col == 4 && row == 1) return TextResource.localize("KAF022_470");

            if (col == MAIN_COL_SIZE - 1) {
                if (row == 0) return setting.getBeforehandRestriction().get().isToUse() ? CHECK : NOT_CHECK;
                else if (row == 1)
                    return setting.getBeforehandRestriction().get().getDateBeforehandRestrictions().name + TextResource.localize("KAF022_510");
                else return setting.getAfterhandRestriction().isAllowFutureDay() ? CHECK : NOT_CHECK;
            }
        }

        return "";
    }

    private List<MasterData> getDataA11(AppLimitSetting appLimitSetting) {
        List<MasterData> data = new ArrayList<>();
        for (int row = 0; row < ROW_SIZE_A9; row++) {
            Map<String, MasterCellData> rowData = new HashMap<>();
            for (int col = 0; col < MAIN_COL_SIZE; col++) {
                rowData.put(
                        COLUMN_NO_HEADER + col,
                        MasterCellData.builder()
                                .columnId(COLUMN_NO_HEADER + col)
                                .value(getValueA11(col, row, appLimitSetting))
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build()
                );
            }
            data.add(MasterData.builder().rowData(rowData).build());
        }
        return data;
    }

    private String getValueA11(int col, int row, AppLimitSetting setting) {
        if (col == 0 && row == 0) return TextResource.localize("KAF022_485");
        if (col == 1) {
            if (row == 0) return TextResource.localize("KAF022_488");
            if (row == 1) return TextResource.localize("KAF022_489");
            if (row == 2) return TextResource.localize("KAF022_490");
            else return TextResource.localize("KAF022_491");
        }
        if (col == MAIN_COL_SIZE - 1) {
            if (row == 0)
                return setting.isCanAppAchievementConfirm() ? TextResource.localize("KAF022_437") : TextResource.localize("KAF022_438");
            if (row == 1)
                return setting.isCanAppAchievementMonthConfirm() ? TextResource.localize("KAF022_437") : TextResource.localize("KAF022_438");
            if (row == 2)
                return setting.isCanAppFinishWork() ? TextResource.localize("KAF022_437") : TextResource.localize("KAF022_438");
            else
                return setting.isCanAppAchievementLock() ? TextResource.localize("KAF022_437") : TextResource.localize("KAF022_438");
        }
        return "";
    }

    private List<MasterData> getDataA17(RecordDate recordDateAtr, int approvalByPersonAtr, Boolean includeConcurrentPersonel) {
        List<MasterData> data = new ArrayList<>();
        for (int row = 0; row < ROW_SIZE_A5; row++) {
            Map<String, MasterCellData> rowData = new HashMap<>();
            for (int col = 0; col < MAIN_COL_SIZE; col++) {
                boolean value;
                if (row == 0) value = recordDateAtr == RecordDate.SYSTEM_DATE;
                if (row == 1) value = approvalByPersonAtr == 1;
                else value = includeConcurrentPersonel;
                rowData.put(
                        COLUMN_NO_HEADER + col,
                        MasterCellData.builder()
                                .columnId(COLUMN_NO_HEADER + col)
                                .value(getValueA17(col, row, value))
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
            }
            data.add(MasterData.builder().rowData(rowData).build());
        }
        return data;
    }

    private String getValueA17(int col, int row, boolean value) {
        if (col == 0 && row == 0) return TextResource.localize("KAF022_460");
        if (col == 1 && row == 0) return TextResource.localize("KAF022_461");
        if (col == 1 && row == 1) return TextResource.localize("KAF022_462");
        if (col == 1 && row == 2) return TextResource.localize("KAF022_498");
        if (col == MAIN_COL_SIZE - 1) {
            if (row == 0) return value ? TextResource.localize("KAF022_403") : TextResource.localize("KAF022_404");
            if (row == 1) return value ? TextResource.localize("KAF022_272") : TextResource.localize("KAF022_273");
            else return value ? TextResource.localize("KAF022_389") : TextResource.localize("KAF022_390");
        }
        return "";
    }

    private List<MasterData> getDataA19(NotUseAtr nightOvertimeReflectAtr) {
        List<MasterData> data = new ArrayList<>();
        Map<String, MasterCellData> rowData = new HashMap<>();
        for (int col = 0; col < MAIN_COL_SIZE; col++) {
            String value;
            if (col == 0) value = TextResource.localize("KAF022_740");
            else if (col == 1) value = TextResource.localize("KAF022_487");
            else if (col == MAIN_COL_SIZE - 1)
                value = nightOvertimeReflectAtr == NotUseAtr.USE ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
            else value = "";
            rowData.put(
                    COLUMN_NO_HEADER + col,
                    MasterCellData.builder()
                            .columnId(COLUMN_NO_HEADER + col)
                            .value(value)
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
        }
        data.add(MasterData.builder().rowData(rowData).build());
        return data;
    }

    private List<MasterData> getDataA12(DisplayAtr prePostDisplayAtr) {
        List<MasterData> data = new ArrayList<>();
        Map<String, MasterCellData> rowData = new HashMap<>();
        for (int col = 0; col < MAIN_COL_SIZE; col++) {
            String value;
            if (col == 0) value = TextResource.localize("KAF022_741");
            else if (col == 1) value = TextResource.localize("KAF022_493");
            else if (col == MAIN_COL_SIZE - 1)
                value = prePostDisplayAtr == DisplayAtr.DISPLAY ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            else value = "";
            rowData.put(
                    COLUMN_NO_HEADER + col,
                    MasterCellData.builder()
                            .columnId(COLUMN_NO_HEADER + col)
                            .value(value)
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
        }
        data.add(MasterData.builder().rowData(rowData).build());
        return data;
    }

    private List<MasterData> getDataA22(List<AppTypeSetting> appTypeSettings) {
        appTypeSettings.sort(Comparator.comparing(AppTypeSetting::getAppType));
        List<MasterData> data = new ArrayList<>();
        for (int block = 0; block < appTypeSettings.size(); block++) {
            AppTypeSetting setting = appTypeSettings.get(block);
            for (int row = 0; row < 2; row++) {
                Map<String, MasterCellData> rowData = new HashMap<>();
                for (int col = 0; col < MAIN_COL_SIZE; col++) {
                    rowData.put(
                            COLUMN_NO_HEADER + col,
                            MasterCellData.builder()
                                    .columnId(COLUMN_NO_HEADER + col)
                                    .value(getValueA22(block, row, col, setting))
                                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                    .build());
                }
                data.add(MasterData.builder().rowData(rowData).build());
            }
        }
        return data;
    }

    private String getValueA22(int block, int row, int col, AppTypeSetting setting) {
        if (block == 0 && row == 0 && col == 0) return TextResource.localize("KAF022_464");
        if (row == 0 && col == 1) return setting.getAppType().name;
        if (col == 2 && row == 0) return TextResource.localize("KAF022_483");
        if (col == 2 && row == 1) return TextResource.localize("KAF022_484");
        if (col == 3 && row == 1) return TextResource.localize("KAF022_481");
        if (col == MAIN_COL_SIZE - 1) {
            if (row == 0) {
                if (setting.getDisplayInitialSegment().isPresent()) {
                    if (setting.getDisplayInitialSegment().get() == PrePostInitAtr.PREDICT)
                        return TextResource.localize("KAF022_745");
                    if (setting.getDisplayInitialSegment().get() == PrePostInitAtr.POSTERIOR)
                        return TextResource.localize("KAF022_746");
                    return TextResource.localize("KAF022_747");
                } else {
                    return "";
                }
            } else return setting.getCanClassificationChange().isPresent()
                    ? setting.getCanClassificationChange().get() ? CHECK : NOT_CHECK
                    : "";
        }
        return "";
    }

    private List<MasterData> getDataA20(AppLimitSetting setting) {
        List<MasterData> data = new ArrayList<>();
        for (int row = 0; row < 2; row++) {
            Map<String, MasterCellData> rowData = new HashMap<>();
            for (int col = 0; col < MAIN_COL_SIZE; col++) {
                rowData.put(
                        COLUMN_NO_HEADER + col,
                        MasterCellData.builder()
                                .columnId(COLUMN_NO_HEADER + col)
                                .value(getValueA20(row, col, setting))
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
            }
            data.add(MasterData.builder().rowData(rowData).build());
        }
        return data;
    }

    private String getValueA20(int row, int col, AppLimitSetting setting) {
        if (col == 0 && row == 0) return TextResource.localize("KAF022_743");
        if (col == 1 && row == 0) return TextResource.localize("KAF022_744");
        if (col == 1 && row == 1) return TextResource.localize("KAF022_486");
        if (col == MAIN_COL_SIZE - 1) {
            if (row == 0)
                return setting.isStandardReasonRequired()
                        ? TextResource.localize("KAF022_100")
                        : TextResource.localize("KAF022_101");
            if (row == 1)
                return setting.isRequiredAppReason()
                        ? TextResource.localize("KAF022_100")
                        : TextResource.localize("KAF022_101");
        }
        return "";
    }

    private List<MasterData> getDataA8(List<DisplayReason> displayReasons) {
        displayReasons.sort(Comparator.comparing(DisplayReason::getAppType).thenComparing(DisplayReason::getHolidayAppType));
        List<MasterData> data = new ArrayList<>();
        for (int block = 0; block < displayReasons.size(); block++) {
            DisplayReason setting = displayReasons.get(block);
            for (int row = 0; row < 2; row++) {
                Map<String, MasterCellData> rowData = new HashMap<>();
                for (int col = 0; col < MAIN_COL_SIZE; col++) {
                    rowData.put(
                            COLUMN_NO_HEADER + col,
                            MasterCellData.builder()
                                    .columnId(COLUMN_NO_HEADER + col)
                                    .value(getValueA8(block, row, col, setting))
                                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                    .build());
                }
                data.add(MasterData.builder().rowData(rowData).build());
            }
        }
        return data;
    }

    private String getValueA8(int block, int row, int col, DisplayReason setting) {
        if (block == 0 && row == 0 && col == 0) return TextResource.localize("KAF022_464");
        if (col == 1 && row == 0) {
            if (setting.getAppType() != ApplicationType.ABSENCE_APPLICATION)
                return setting.getAppType().name;
            else if (setting.getHolidayAppType() == 0)
                return setting.getAppType().name;
        }

        if (col == 2) {
            if (row == 0 && setting.getAppType() != ApplicationType.ABSENCE_APPLICATION)
                return TextResource.localize("KAF022_478");
            if (row == 0 && setting.getAppType() == ApplicationType.ABSENCE_APPLICATION)
                return "【" + setting.getOpHolidayAppType().get().name + "】";
            if (row == 1 && setting.getAppType() != ApplicationType.ABSENCE_APPLICATION)
                return TextResource.localize("KAF022_479");
        }

        if (col == 3) {
            if (setting.getAppType() != ApplicationType.ABSENCE_APPLICATION)
                return TextResource.localize("KAF022_100");
            else {
                if (row == 0) return TextResource.localize("KAF022_478");
                else return TextResource.localize("KAF022_479");
            }
        }

        if (col == 4 && setting.getAppType() == ApplicationType.ABSENCE_APPLICATION)
            return TextResource.localize("KAF022_100");

        if (col == MAIN_COL_SIZE - 1) {
            if (row == 0) return setting.getDisplayFixedReason() == DisplayAtr.DISPLAY ? CHECK : NOT_CHECK;
            else return setting.getDisplayAppReason() == DisplayAtr.DISPLAY ? CHECK : NOT_CHECK;
        }

        return "";
    }

    private List<MasterData> getDataA21(AppReflectExecutionCondition setting) {
        List<MasterData> data = new ArrayList<>();
        for (int row = 0; row < 2; row++) {
            Map<String, MasterCellData> rowData = new HashMap<>();
            for (int col = 0; col < MAIN_COL_SIZE; col++) {
                rowData.put(
                        COLUMN_NO_HEADER + col,
                        MasterCellData.builder()
                                .columnId(COLUMN_NO_HEADER + col)
                                .value(getValueA21(row, col, setting))
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
            }
            data.add(MasterData.builder().rowData(rowData).build());
        }
        return data;
    }

    private String getValueA21(int row, int col, AppReflectExecutionCondition setting) {
        if (row == 0 && col == 0) return TextResource.localize("KAF022_77");
        if (col == 1) {
            if (row == 0) return TextResource.localize("KAF022_33");
            else return TextResource.localize("KAF022_34");
        }
        if (col == MAIN_COL_SIZE - 1) {
            if (row == 0) return setting.getEvenIfScheduleConfirmed() == NotUseAtr.USE
                    ? TextResource.localize("KAF022_44")
                    : TextResource.localize("KAF022_396");
            else return setting.getEvenIfWorkRecordConfirmed() == NotUseAtr.USE
                    ? TextResource.localize("KAF022_44")
                    : TextResource.localize("KAF022_396");
        }
        return "";
    }

    private String convertToTime(int param) {
        int m = param / 60;
        int s = param % 60;
        return m > 9 ? String.format("%02d:%02d", m, s) : String.format("%2d:%02d", m, s);
    }

    public List<MasterHeaderColumn> getHeaderColumns(Sheet sheet) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        switch (sheet) {
            case JOB:
                columns.addAll(preparationBeforeApply.getHeaderColumnsJob());
                break;
            case REASON:
                columns.addAll(preparationBeforeApply.getHeaderColumnsReaSon());
                break;
            case OVER_TIME:
                columns.addAll(preparationBeforeApply.getHeaderColumnsOverTime());
                break;
            case HOLIDAY_APP:
                columns.addAll(preparationBeforeApply.getHeaderColumnsCommon());
                break;
            case WORK_CHANGE:
                columns.addAll(preparationBeforeApply.getHeaderColumnsCommon());
                break;
            case COMMON_APP:
                columns.addAll(preparationBeforeApply.getHeaderColumnsCommon());
                break;
            case OVER_TIME2:
                columns.addAll(preparationBeforeApply.getHeaderColumnsOverTime());
                break;
            case PAYOUT_APP:
                columns.addAll(preparationBeforeApply.getHeaderColumnsCommon());
                break;
            case APPROVAL_LIST:
                columns.addAll(preparationBeforeApply.getHeaderColumnsCommon());
                break;
            case REFLECT_APP:
                columns.addAll(preparationBeforeApply.getHeaderColumnsReflectApp());
                break;
            case EMP_APPROVE:
                columns.addAll(employmentApprovalSettingExport.getHeaderColumnsEmpApprove());
                break;
            case APPROVAL_CONFIG:
                columns.addAll(approvalFunctionConfigExport.getHeaderColumnsApproveConfig());
                break;
        }
        return columns;
    }

    private List<MasterData> getData(Sheet sheet, List<Object[]> extraData) {
        String companyId = AppContexts.user().companyId();
        String baseDate = "9999-12-31";
        List<MasterData> datas = new ArrayList<>();
        switch (sheet) {
            case REASON:
                datas.addAll(preparationBeforeApply.getDataReaSon(extraData));
                break;
            case JOB:
                List<Object[]> preparationBefore = preparationBeforeApplyRepository.getJob(companyId, baseDate);
                datas.addAll(preparationBeforeApply.getDataJob(preparationBefore));
                break;
            case OVER_TIME:
                datas.addAll(preparationBeforeApply.getDataOverTime(extraData));
                break;
            case HOLIDAY_APP:
                datas.addAll(preparationBeforeApply.getDataHoliDayApp(extraData));
                break;
            case WORK_CHANGE:
                datas.addAll(preparationBeforeApply.getDataWorkChange(extraData));
                break;
            case COMMON_APP:
                datas.addAll(preparationBeforeApply.getDataCommon(extraData));
                break;
            case OVER_TIME2:
                datas.addAll(preparationBeforeApply.getDataOverTime2(extraData));
                break;
            case PAYOUT_APP:
                datas.addAll(preparationBeforeApply.getDataPayout(extraData));
                break;
            case APPROVAL_LIST:
                datas.addAll(preparationBeforeApply.getDataApprove(extraData));
                break;
            case REFLECT_APP:
                datas.addAll(preparationBeforeApply.getDataReflectApp(extraData));
                break;
            case EMP_APPROVE:
                List<Object[]> empApproval = approvalRepository.getAllEmploymentApprovalSetting(companyId);
                datas.addAll(empApproval.stream().map(e -> employmentApprovalSettingExport.getDataEmploymentApprovalSetting(e)).collect(Collectors.toList()));
                break;
            case APPROVAL_CONFIG:
                List<Object[]> approvalCongif = approvalRepository.getAllApprovalFunctionConfig(companyId, baseDate);
                datas.addAll(approvalCongif.stream().map(i -> approvalFunctionConfigExport.getDataApprovalConfig(i)).collect(Collectors.toList()));
                break;
        }
        return datas;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        List<MasterData> data = new ArrayList<>();

        Optional<ApplicationSetting> applicationSetting = appSettingRepo.findByCompanyId(companyId);
        ApprovalSettingDto approvalSetting = approvalSettingFinder.findApproSet();
        JobAssignSettingDto jobAssignSetting = jobFinder.findApp();
        Optional<AppReflectOtHdWork> appReflectOtHdWork = otHdWorkAppReflectRepo.findByCompanyId(companyId);
        List<DisplayReason> displayReasons = displayReasonRepo.findByCompanyId(companyId);
        Optional<AppReflectExecutionCondition> appReflectExecutionCondition = appReflectConditionRepo.findByCompanyId(companyId);
        if (!applicationSetting.isPresent()
                || !appReflectOtHdWork.isPresent()
                || !appReflectExecutionCondition.isPresent())
            throw new BusinessException("Setting Data Not Found!");

        data.addAll(getDataA4(applicationSetting.get().getAppDeadlineSetLst()));
        data.addAll(getDataA7(applicationSetting.get().getReceptionRestrictionSettings()));
        data.addAll(getDataA11(applicationSetting.get().getAppLimitSetting()));
        data.addAll(getDataA17(applicationSetting.get().getRecordDate(), approvalSetting.getPrinFlg(), jobAssignSetting.getIsConcurrently()));
        data.addAll(getDataA19(appReflectOtHdWork.get().getNightOvertimeReflectAtr()));
        data.addAll(getDataA12(applicationSetting.get().getAppDisplaySetting().getPrePostDisplayAtr()));
        data.addAll(getDataA22(applicationSetting.get().getAppTypeSettings()));
        data.addAll(getDataA20(applicationSetting.get().getAppLimitSetting()));
        data.addAll(getDataA8(displayReasons));
        data.addAll(getDataA21(appReflectExecutionCondition.get()));
        return data;
    }

    @Override
    public List<SheetData> extraSheets(MasterListExportQuery query) {
        List<SheetData> sheetData = new ArrayList<>();
        sheetData.add(SheetData.builder()
                .mainData(this.getReasonMasterData())
                .mainDataColumns(this.getReasonHeaderColumns())
                .sheetName(TextResource.localize("KAF022_511"))
                .mode(this.mainSheetMode())
                .build());
        sheetData.add(SheetData.builder()
                .mainData(this.getRepresentAppMasterData())
                .mainDataColumns(this.getCommonHeaderColumns(PRESENT_APP_COL_SIZE))
                .sheetName(TextResource.localize("KAF022_750"))
                .mode(this.mainSheetMode())
                .build());
        sheetData.add(SheetData.builder()
                .mainData(this.getOvertimeMasterData())
                .mainDataColumns(this.getCommonHeaderColumns(OVERTIME_COL_SIZE))
                .sheetName(TextResource.localize("KAF022_519"))
                .mode(this.mainSheetMode())
                .build());
        return sheetData;
    }

    @Override
    public String mainSheetName() {
        return TextResource.localize("KAF022_453");
    }

    private String getSheetName(Sheet sheet) {
        switch (sheet) {
            case JOB:
                return TextResource.localize("KAF022_515");
            case REASON:
                return TextResource.localize("KAF022_511");
            case OVER_TIME:
                return TextResource.localize("KAF022_519");
            case HOLIDAY_APP:
                return TextResource.localize("KAF022_537");
            case WORK_CHANGE:
                return TextResource.localize("KAF022_564");
            case COMMON_APP:
                return TextResource.localize("KAF022_571");
            case OVER_TIME2:
                return TextResource.localize("KAF022_575");
            case PAYOUT_APP:
                return TextResource.localize("KAF022_581");
            case APPROVAL_LIST:
                return TextResource.localize("KAF022_586");
            case REFLECT_APP:
                return TextResource.localize("KAF022_601");
            case EMP_APPROVE:
                return TextResource.localize("KAF022_627");
            case APPROVAL_CONFIG:
                return TextResource.localize("KAF022_634");
        }
        return "";
    }

    private List<MasterHeaderColumn> getReasonHeaderColumns() {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        for (int i = 0; i < REASON_COL_SIZE; i++) {
            String columnText;
            if (i == 0) columnText = TextResource.localize("KAF022_512");
            else if (i == 1) columnText = "";
            else if (i == 2) columnText = TextResource.localize("KAF022_513");
            else if (i == 3) columnText = TextResource.localize("KAF022_694");
            else columnText = TextResource.localize("KAF022_514");
            columns.add(
                    new MasterHeaderColumn(
                            COLUMN_NO_HEADER + i,
                            columnText,
                            ColumnTextAlign.LEFT,
                            "",
                            true
                    )
            );
        }
        return columns;
    }

    private  List<MasterData> getReasonMasterData() {
        String companyId = AppContexts.user().companyId();
        List<MasterData> data = new ArrayList<>();
        List<AppReasonStandard> reasons = appReasonStandardRepo.findByCompanyId(companyId);
        reasons.sort(Comparator.comparing(AppReasonStandard::getApplicationType));
        for (AppReasonStandard reason : reasons) {
            reason.getReasonTypeItemLst().sort(Comparator.comparing(ReasonTypeItem::getDisplayOrder));
            for (int row = 0; row < reason.getReasonTypeItemLst().size(); row++) {
                ReasonTypeItem item = reason.getReasonTypeItemLst().get(row);
                Map<String, MasterCellData> rowData = new HashMap<>();
                for (int col = 0; col < REASON_COL_SIZE; col++) {
                    String value;
                    if (row == 0 && col == 0) value = reason.getApplicationType().name;
                    else if (col == 1 && row == 0 && reason.getApplicationType() == ApplicationType.ABSENCE_APPLICATION) value = reason.getOpHolidayAppType().get().name;
                    else if (col == 2) value = item.isDefaultValue() ? CHECK : NOT_CHECK;
                    else if (col == 3) value = item.getAppStandardReasonCD().v().toString();
                    else if (col == 4) value = item.getReasonForFixedForm().v();
                    else value = "";
                    rowData.put(
                            COLUMN_NO_HEADER + col,
                            MasterCellData.builder()
                                    .columnId(COLUMN_NO_HEADER + col)
                                    .value(value)
                                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                    .build());
                }
                data.add(MasterData.builder().rowData(rowData).build());
            }
        }
        return data;
    }

    private  List<MasterData> getRepresentAppMasterData() {
        String companyId = AppContexts.user().companyId();

        List<StandardMenuNameQuery> queries = new ArrayList<>();
        queries.add(new StandardMenuNameQuery("KAF005", "A", Optional.of("overworkatr=0")));
        queries.add(new StandardMenuNameQuery("KAF005", "A", Optional.of("overworkatr=1")));
        queries.add(new StandardMenuNameQuery("KAF005", "A", Optional.of("overworkatr=2")));
        queries.add(new StandardMenuNameQuery("KAF006", "A", Optional.empty()));
        queries.add(new StandardMenuNameQuery("KAF007", "A", Optional.empty()));
        queries.add(new StandardMenuNameQuery("KAF008", "A", Optional.empty()));
        queries.add(new StandardMenuNameQuery("KAF009", "A", Optional.empty()));
        queries.add(new StandardMenuNameQuery("KAF010", "A", Optional.empty()));
        queries.add(new StandardMenuNameQuery("KAF012", "A", Optional.empty()));
        queries.add(new StandardMenuNameQuery("KAF004", "A", Optional.empty()));
        queries.add(new StandardMenuNameQuery("KAF002", "A", Optional.empty()));
        queries.add(new StandardMenuNameQuery("KAF002", "C", Optional.empty()));
        queries.add(new StandardMenuNameQuery("KAF011", "A", Optional.empty()));
        queries.add(new StandardMenuNameQuery("KAF020", "A", Optional.empty()));
        List<StandardMenuNameExport> menuList = menuPub.getMenuDisplayName(companyId, queries);

        Optional<ApplicationSetting> applicationSetting = appSettingRepo.findByCompanyId(companyId);
        if (!applicationSetting.isPresent()) throw new BusinessException("Setting Data Not Found!");

        List<AppSetForProxyApp> appSetForProxyApps = applicationSetting.get().getAppSetForProxyApps();

        List<MasterData> data = new ArrayList<>();
        for (int row = 0; row < menuList.size(); row++) {
            StandardMenuNameExport menu = menuList.get(row);
            AppSetForProxyApp setting = appSetForProxyApps.stream().filter(o -> {
                switch (menu.getProgramId()) {
                    case "KAF005":
                        return o.getAppType() == ApplicationType.OVER_TIME_APPLICATION
                                && ((menu.getQueryString().equals("overworkatr=0") && o.getOpOvertimeAppAtr().get() == OvertimeAppAtr.EARLY_OVERTIME)
                                || (menu.getQueryString().equals("overworkatr=1") && o.getOpOvertimeAppAtr().get() == OvertimeAppAtr.NORMAL_OVERTIME)
                                || (menu.getQueryString().equals("overworkatr=2") && o.getOpOvertimeAppAtr().get() == OvertimeAppAtr.EARLY_NORMAL_OVERTIME));
                    case "KAF006":
                        return o.getAppType() == ApplicationType.ABSENCE_APPLICATION;
                    case "KAF007":
                        return o.getAppType() == ApplicationType.WORK_CHANGE_APPLICATION;
                    case "KAF008":
                        return o.getAppType() == ApplicationType.BUSINESS_TRIP_APPLICATION;
                    case "KAF009":
                        return o.getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION;
                    case "KAF010":
                        return o.getAppType() == ApplicationType.HOLIDAY_WORK_APPLICATION;
                    case "KAF012":
                        return o.getAppType() == ApplicationType.ANNUAL_HOLIDAY_APPLICATION;
                    case "KAF004":
                        return o.getAppType() == ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION;
                    case "KAF002":
                        return o.getAppType() == ApplicationType.STAMP_APPLICATION
                                && ((o.getOpStampRequestMode().get() == StampRequestMode.STAMP_ADDITIONAL && menu.getScreenId().equals("A"))
                                || (o.getOpStampRequestMode().get() == StampRequestMode.STAMP_ONLINE_RECORD && menu.getScreenId().equals("C")));
                    case "KAF011":
                        return o.getAppType() == ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
                    case "KAF020":
                        return o.getAppType() == ApplicationType.OPTIONAL_ITEM_APPLICATION;
                    default:
                        return false;
                }
            }).findFirst().orElse(null);
            Map<String, MasterCellData> rowData = new HashMap<>();
            for (int col = 0; col < PRESENT_APP_COL_SIZE; col++) {
                String value;
                if (row == 0 && col == 0) value = TextResource.localize("代行申請で利用できる申請");
                else if (col == 1) value = menu.getDisplayName();
                else if (col == 2) value = setting == null ? NOT_CHECK : CHECK;
                else value = "";
                rowData.put(
                        COLUMN_NO_HEADER + col,
                        MasterCellData.builder()
                                .columnId(COLUMN_NO_HEADER + col)
                                .value(value)
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
            }
            data.add(MasterData.builder().rowData(rowData).build());
        }
        return data;
    }

    private  List<MasterData> getOvertimeMasterData() {
        String companyId = AppContexts.user().companyId();
        List<MasterData> data = new ArrayList<>();
        Optional<OvertimeAppSet> applySetting = overtimeAppSetRepo.findSettingByCompanyId(companyId);
        Optional<AppReflectOtHdWork> reflectSetting = otHdWorkAppReflectRepo.findByCompanyId(companyId);
        if (!applySetting.isPresent() || !reflectSetting.isPresent())
            throw new BusinessException("Setting Data Not Found!");

        OtWorkAppReflect overtimeWorkAppReflect = reflectSetting.get().getOvertimeWorkAppReflect();
        for (int row = 0; row < 21; row++) {
            Map<String, MasterCellData> rowData = new HashMap<>();
            for (int col = 0; col < OVERTIME_COL_SIZE; col++) {
                String value;
                if (col == 0) {
                    if (row == 0) value = TextResource.localize("KAF022_520");
                    else if (row == 1) value = TextResource.localize("KAF022_526");
                    else if (row == 2) value = TextResource.localize("KAF022_531");
                    else if (row == 8) value = TextResource.localize("KAF022_284");
                    else if (row == 14) value = TextResource.localize("KAF022_410");
                    else value = "";
                } else if (col == 1) {
                    if (row == 0) value = TextResource.localize("KAF022_521");
                    else if (row == 1) value = TextResource.localize("KAF022_530");
                    else if (row == 2) value = TextResource.localize("KAF022_532");
                    else if (row == 3) value = TextResource.localize("KAF022_533");
                    else if (row == 4) value = TextResource.localize("KAF022_534");
                    else if (row == 5) value = TextResource.localize("KAF022_535");
                    else if (row == 6) value = TextResource.localize("KAF022_711");
                    else if (row == 7) value = TextResource.localize("KAF022_713");
                    else if (row == 8) value = TextResource.localize("KAF022_641");
                    else if (row == 9) value = TextResource.localize("KAF022_714");
                    else if (row == 10) value = TextResource.localize("KAF022_715");
                    else if (row == 11) value = TextResource.localize("KAF022_305");
                    else if (row == 12) value = TextResource.localize("KAF022_288");
                    else if (row == 13) value = TextResource.localize("KAF022_716");
                    else if (row == 14) value = TextResource.localize("KAF022_622");
                    else if (row == 17) value = TextResource.localize("KAF022_611");
                    else value = "";
                } else if (col == 2) {
                    if (row == 14) value = TextResource.localize("KAF022_717");
                    else if (row == 15) value = TextResource.localize("KAF022_718");
                    else if (row == 16) value = TextResource.localize("KAF022_719");
                    else if (row == 17) value = TextResource.localize("KAF022_720");
                    else if (row == 18) value = TextResource.localize("KAF022_719");
                    else if (row == 19) value = TextResource.localize("KAF022_721");
                    else if (row == 20) value = TextResource.localize("KAF022_722");
//                    else if (row == 21) value = TextResource.localize("KAF022_723");
                    else value = "";
                } else {
                    if (row == 0) value = overtimeWorkAppReflect.getReflectActualWorkAtr() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_420") : TextResource.localize("KAF022_421");
                    else if (row == 1) value = applySetting.get().getOvertimeLeaveAppCommonSet().getExtratimeDisplayAtr() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
                    else if (row == 2) value = applySetting.get().getOvertimeLeaveAppCommonSet().getPreExcessDisplaySetting() == NotUseAtr.NOT_USE
                            ? TextResource.localize("KAF022_173") : TextResource.localize("KAF022_174");
                    else if (row == 3) {
                        if (applySetting.get().getOvertimeLeaveAppCommonSet().getPerformanceExcessAtr() == AppDateContradictionAtr.NOTCHECK)
                            value =  TextResource.localize("KAF022_173");
                        else if (applySetting.get().getOvertimeLeaveAppCommonSet().getPerformanceExcessAtr() == AppDateContradictionAtr.CHECKREGISTER)
                            value =  TextResource.localize("KAF022_174");
                        else value =  TextResource.localize("KAF022_175");
                    } else if (row == 4) value = applySetting.get().getOvertimeLeaveAppCommonSet().getOverrideSet() == OverrideSet.SYSTEM_TIME_PRIORITY
                            ? TextResource.localize("KAF022_709") : TextResource.localize("KAF022_710");
                    else if (row == 5) {
                        if (applySetting.get().getOvertimeLeaveAppCommonSet().getExtratimeExcessAtr() == Time36AgreeCheckRegister.NOT_CHECK)
                            value =  TextResource.localize("KAF022_173");
                        else if (applySetting.get().getOvertimeLeaveAppCommonSet().getExtratimeExcessAtr() == Time36AgreeCheckRegister.CHECK)
                            value =  TextResource.localize("KAF022_175");
                        else value =  TextResource.localize("KAF022_651");
                    } else if (row == 6) value = applySetting.get().getOvertimeLeaveAppCommonSet().getCheckOvertimeInstructionRegister() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
                    else if (row == 7) value = applySetting.get().getOvertimeLeaveAppCommonSet().getCheckDeviationRegister() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
                    else if (row == 8) value = applySetting.get().getApplicationDetailSetting().getTimeCalUse() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
                    else if (row == 9) {
                        if (applySetting.get().getApplicationDetailSetting().getAtworkTimeBeginDisp() == AtWorkAtr.NOTDISPLAY) value = TextResource.localize("KAF022_37");
                        else if (applySetting.get().getApplicationDetailSetting().getAtworkTimeBeginDisp() == AtWorkAtr.DISPLAY) value = TextResource.localize("KAF022_301");
                        else if (applySetting.get().getApplicationDetailSetting().getAtworkTimeBeginDisp() == AtWorkAtr.AT_START_WORK_OFF_PERFORMANCE) value = TextResource.localize("KAF022_302");
                        else value = TextResource.localize("KAF022_303");
                    } else if (row == 10) value = applySetting.get().getApplicationDetailSetting().isDispSystemTimeWhenNoWorkTime()
                            ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
                    else if (row == 11) value = applySetting.get().getApplicationDetailSetting().getTimeInputUse() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
                    else if (row == 12) value = applySetting.get().getApplicationDetailSetting().getRequiredInstruction()
                            ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
                    else if (row == 13) value = applySetting.get().getApplicationDetailSetting().getPreRequireSet() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
                    else if (row == 14) value = overtimeWorkAppReflect.getBefore().getReflectWorkInfoAtr() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
                    else if (row == 15) value = overtimeWorkAppReflect.getBefore().getReflectActualOvertimeHourAtr() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
                    else if (row == 16) value = overtimeWorkAppReflect.getBefore().getBreakLeaveApplication().getBreakReflectAtr() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
                    else if (row == 17) value = overtimeWorkAppReflect.getAfter().getWorkReflect() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
                    else if (row == 18) value = overtimeWorkAppReflect.getAfter().getBreakLeaveApplication().getBreakReflectAtr() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
                    else if (row == 19) value = overtimeWorkAppReflect.getAfter().getOthersReflect().getReflectDivergentReasonAtr() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
                    else value = overtimeWorkAppReflect.getAfter().getOthersReflect().getReflectPaytimeAtr() == NotUseAtr.USE
                            ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
//                    else value = applySetting.get().getApplicationDetailSetting().isDispSystemTimeWhenNoWorkTime()
//                            ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
                }
                rowData.put(
                        COLUMN_NO_HEADER + col,
                        MasterCellData.builder()
                                .columnId(COLUMN_NO_HEADER + col)
                                .value(value)
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
            }
            data.add(MasterData.builder().rowData(rowData).build());
        }
        return data;
    }

}
