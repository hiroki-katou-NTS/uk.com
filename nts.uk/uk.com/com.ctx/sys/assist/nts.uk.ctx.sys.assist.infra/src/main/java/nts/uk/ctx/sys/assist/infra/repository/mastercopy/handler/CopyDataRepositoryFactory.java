package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;


import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import java.util.HashMap;

/**
 * Factory class
 *
 * @author locph
 */
public class CopyDataRepositoryFactory {

    static HashMap<String, DataCopyHandler> mapper = new HashMap<>();

    static {
        mapper.put("PPEMT_PER_INFO_CTG", new PpemtPerInfoCtgDataCopyHandler());
//        mapper.put("PPEMT_PER_INFO_CTG_ORDER", new PersonalInfoDataCopyHandler());                  //Event：個人情報定義の初期値コピー
//        mapper.put("PPEMT_PER_INFO_ITEM_CM", new PersonalInfoDataCopyHandler());                    //Event：個人情報定義の初期値コピー
//        mapper.put("PPEMT_PER_INFO_ITEM", new PersonalInfoDataCopyHandler());                       //Event：個人情報定義の初期値コピー

//        mapper.put("PPEMT_PER_INFO_ITEM_ORDER", new PersonalInfoDataCopyHandler());                 //Event：個人情報定義の初期値コピー
//        mapper.put("PPEMT_DATE_RANGE_ITEM", new PersonalInfoDataCopyHandler());                     //Event：個人情報定義の初期値コピー
//        mapper.put("PPEMT_HISTORY_SELECTION", new PpemtHistorySelectionDataCopyHandler());
//        mapper.put("PPEMT_SELECTION", new CcgmtMyPageSetDataCopyHandler());
//        mapper.put("PPEMT_SEL_ITEM_ORDER", new CcgmtMyPageSetDataCopyHandler());
//        mapper.put("PPEMT_NEW_LAYOUT", new CcgmtMyPageSetDataCopyHandler());
//        mapper.put("PPEMT_LAYOUT_ITEM_CLS", new CcgmtMyPageSetDataCopyHandler());
//        mapper.put("PPEMT_LAYOUT_ITEM_CLS_DF", new CcgmtMyPageSetDataCopyHandler());
//        mapper.put("PPEMT_PINFO_ITEM_GROUP", new CcgmtMyPageSetDataCopyHandler());
//        mapper.put("PPEMT_PINFO_ITEM_DF_GROUP", new CcgmtMyPageSetDataCopyHandler());
        mapper.put("CCGMT_MY_PAGE_SET", new CcgmtMyPageSetDataCopyHandler());

        mapper.put("CCGST_STANDARD_MENU", new CcgstStandardMenuDataCopyHandler());
        mapper.put("CCGMT_TOPPAGE_PART", new CcgmtTopPagePartDataCopyHandler());
        mapper.put("CCGMT_PART_ITEM_SET", new CcgmtPartItemSetDataCopyHandler());
        mapper.put("KSHST_COM_60H_VACATION", new KshstCom60hVacationDataCopyHandler());
        mapper.put("KSVST_COM_SUBST_VACATION", new KsvstComSubstVacationDataCopyHandler());
        mapper.put("KCLMT_COMPENS_LEAVE_COM", new KclmtCompensLeaveComDataCopyHandler());
        mapper.put("KCLMT_ACQUISITION_COM", new KclmtAcquisitionComDataCopyHandler());
        mapper.put("KCTMT_DIGEST_TIME_COM", new KctmtDigestTimeComDataCopyHandler());
        mapper.put("KMFMT_RETENTION_YEARLY", new KmfmtRetentionYearlyDataCopyHandler());
        mapper.put("KALMT_ANNUAL_PAID_LEAVE", new KalmtAnnualPaidLeaveDataCopyHandler());
        mapper.put("KMAMT_MNG_ANNUAL_SET", new KmamtMngAnnualSetDataCopyHandler());
        mapper.put("KTVMT_TIME_ANNUAL_SET", new KtvmtTimeAnnualSetDataCopyHandler());
        mapper.put("KSHST_ATD_ITEM_CONTROL", new KshstControlOfAttendanceItemsDataCopyHandler());
        mapper.put("KSHST_MON_ITEM_CONTROL", new KshstMonItemControlDataCopyHandler());
        mapper.put("KSHST_MON_SER_TYPE_CTR", new KshstMonSerTypeCtrDataCopyHandler());
        mapper.put("KRCMT_DAI_ATTENDANCE_ITEM", new KrcmtDailyAttendanceItemDataCopyHandler());
        mapper.put("KCLMT_CLOSURE", new KclmtClosureDataCopyHandler());
        mapper.put("KSCMT_SCHE_ITEM", new KscmtScheduleItemDataCopyHandler());
        mapper.put("KSCMT_SCHE_ITEM_ORDER", new KscmtScheItemOrderDataCopyHandler());
        mapper.put("KFNST_PLAN_TIME_ITEM ", new KfnstPlanTimeItemDataCopyHandler());
        mapper.put("KCLMT_CLOSURE_HIST", new KclmtClosureHistDataCopyHandler());
        mapper.put("KNLMT_NURSING_LEAVE_SET", new KnlmtNursingLeaveSetDataCopyHandler());
        mapper.put("KSHST_OVERTIME_FRAME", new KshstOvertimeFrameDataCopyHandler());
        mapper.put("KSHST_WORKDAYOFF_FRAME", new KshstWorkdayoffFrameDataCopyHandler());
        mapper.put("KSHST_WORK_MANAGEMENT", new KshstWorkManagementMultipleDataCopyHandler());
        mapper.put("KSHST_TEMP_WK_USE_MANAGE", new KshstTempWkUseManageDataCopyHandler());
        mapper.put("KSHST_MANAGE_ENTRY_EXIT", new KshstManageEntryExitDataCopyHandler());
        mapper.put("KSHST_COM_REG_LABOR_TIME", new KshstComRegLaborTimeDataCopyHandler());
        mapper.put("KSHST_COM_TRANS_LAB_TIME", new KshstComTransLabTimeDataCopyHandler());
        mapper.put("KSHST_HOLIDAY_ADDTION_SET", new KshstHolidayAdditionSetDataCopyHandler());
        mapper.put("KSHST_ADD_SET_MAN_WKHOUR", new KshstAddSetManWKHourDataCopyHandler());
        mapper.put("KSHST_WORK_REGULAR_SET", new KshstWorkRegularSetDataCopyHandler());
        mapper.put("KSHST_WORK_DEF_LABOR_SET", new KshstWorkDepLaborSetDataCopyHandler());
        mapper.put("KSHST_WORK_FLEX_SET", new KshstWorkFlexSetDataCopyHandler());
        mapper.put("KSHST_HOUR_PAY_ADD_SET", new KshstHourPayAddSetDataCopyHandler());
        mapper.put("KSHST_FLEX_WORK_SETTING", new KshstFlexWorkSettingDataCopyHandler());
        mapper.put("KSHST_AGG_LABOR_SET", new KshstAggLaborSetDataCopyHandler());
        mapper.put("KSHST_ZERO_TIME_SET", new KshstZeroTimeSetDataCopyHandler());
        mapper.put("KRCST_CONSTRAINT_TIME_CAL", new KrcstConstralntTimeCalDataCopyHandler());

        mapper.put("KRCST_WK_HOUR_LIMIT_CTRL", new KrcstWkHourLimitCtrlDataCopyHandler());
        mapper.put("KSHST_FLEX_SET", new KshstFlexSetDataCopyHandler());

        mapper.put("KRCMT_STAMP_IMPRINT", new KrcmtStampImprintDataCopyHandler());
        mapper.put("KRCDT_OUTING_MANAGEMENT", new KrcdtTemporaryWorkUseManageDataCopyHandler());
        mapper.put("KRCST_NIGHT_TIMESHEET", new KrcstNightTimeSheetDataCopyHandler());
        mapper.put("KRCST_COM_REG_M_CAL_SET", new KrcstComRegMCalSetDataCopyHandler());
        mapper.put("KRCST_COM_DEFOR_M_CAL_SET", new KrcstComDeforMCalSetDataCopyHandler());
        mapper.put("KRCST_COM_FLEX_M_CAL_SET", new KrcstComFlexMCalSetDataCopyHandler());
        mapper.put("KRCST_VERT_MON_METHOD", new KrcstVertMonMethodDataCopyHandler());
        mapper.put("KRCST_MONSET_LGL_TRNS_SET", new KrcstMonsetLglTrnsSetDataCopyHandler());
        mapper.put("KRCST_MON_ITEM_ROUND", new KrcstMonItemRoundDataCopyHandler());
        mapper.put("KRCST_MON_EXCOUT_ROUND", new KrcstMonExcOutRoundDataCopyHandler());
        mapper.put("KRCST_DVGC_TIME", new KrcstDvgcTimeDataCopyHandler());
        mapper.put("KRCST_DVGC_REASON", new KrcstDvgcReasonDataCopyHandler());

        mapper.put("KMLMT_COST_CALC_SET ", new KmlmtPersonCostCalculationDataCopyHandler());
        mapper.put("KSCMT_WEEKLY_WORK_SET", new KscmtWeeklyWorkSetDataCopyHandler());
        mapper.put("BSYST_TEMP_ABSENCE_FRAME", new BsystTempAbsenceFrameDataCopyHandler());
        mapper.put("KSHST_TOTAL_TIMES", new KshstTotalTimesDataCopyHandler());
        mapper.put("KSHST_TOTAL_CONDITION", new KshstTotalConditionDataCopyHandler());
        mapper.put("KSCST_SCHE_FUNC_CONTROL", new KscstScheFuncControlDataCopyHandler());
        mapper.put("KSCMT_SCHE_DISP_CONTROL", new KscmtScheDispControlDataCopyHandler());
        mapper.put("KSCMT_SCHE_PER_INFO_ATR", new KscmtSchePerInfoAtrDataCopyHandler());
        mapper.put("KSCST_WORKTYPE_DISP_SET", new KscstWorkTypeDispSetDataCopyHandler());
        mapper.put("KSCST_WORKTYPE_DISPLAY", new KscstWorkTypeDisplayDataCopyHandler());

        mapper.put("KRCMT_MON_ATTENDANCE_ITEM", new KrcmtMonAttendanceItemDataCopyHandler());
        mapper.put("KRCST_OPTIONAL_ITEM", new KrcstOptionalItemDataCopyHandler());
//        mapper.put("KRCMT_ERAL_SET", new ErAlWorkRecordCopyHandler());                                  //Event:勤務実績のエラーアラームの初期値を登録する
        mapper.put("BSYMT_EMPLOYEE_CE_SET", new BsymtEmployeeCESettingDataCopyHandler());
        mapper.put("KRQST_APP_DIVERGEN_REASON", new KrqstAppDivergenReasonDataCopyHandler());
        mapper.put("KRQST_APP_REASON", new KrqstAppReasonDataCopyHandler());
        mapper.put("KRQST_URL_EMBEDDED", new KrqstUrlEmbeddedDataCopyHandler());
        mapper.put("KRQMT_MAIL_HD_INSTRUCTION", new KrqmtMailHdInstructionDataCopyHandler());
        mapper.put("KRQMT_MAIL_OT_INSTRUCTION", new KrqmtMailOtInstructionDataCopyHandler());
        mapper.put("KRQMT_APPROVAL_TEMPLATE", new KrqmtApprovalTempDataCopyHandler());
        mapper.put("KRQST_REMAND_MAIL", new KrqstContentOfRemandMailDataCopyHandler());
        mapper.put("KRQMT_HD_APP_DISP_NAME", new KrqmtHdAppDispNameDataCopyHandler());
        mapper.put("KRQMT_APP_DISP_NAME", new KrqmtAppDispNameDataCopyHandler());
        mapper.put("KRQST_APPROVAL_SET", new KrqstApprovalSetDataCopyHandler());
        mapper.put("KRQST_HD_APP_SET", new KrqstHdAppSetDataCopyHandler());
        mapper.put("KRQST_APP_WORK_CHANGE_SET", new KrqstAppWorkChangeSetDataCopyHandler());
        mapper.put("KRQST_STAMP_REQUEST_SET", new KrqstStampRequestSettingDataCopyHandler());
        mapper.put("KRQST_WITHDRAWAL_REQ_SET", new KrqstWithDrawalReqSetDataCopyHandler());
        mapper.put("KRQST_TIME_HD_APP_SET", new KrqstTimeHdAppSetDataCopyHandler());
        mapper.put("KRQST_LATE_EARLY_REQUESET", new KrqstLateEarlyRequestDataCopyHandler());
        mapper.put("KRQST_WITHDRAWAL_APP_SET", new KrqstWithDrawalAppSetDataCopyHandler());
        mapper.put("KRQST_OT_REST_APP_COM_SET", new KrqstOtRestAppComSetDataCopyHandler());
        mapper.put("KRQST_APP_OVERTIME_SET", new KrqstAppOvertimeSetDataCopyHandler());
        mapper.put("KRQST_TRIP_REQUEST_SET", new KrqstTripRequestSetDataCopyHandler());
        mapper.put("KRQST_GO_BACK_DIRECT_SET", new KrqstGoBackDirectSetDataCopyHandler());
        mapper.put("KRQST_COM_APP_CONFIG", new KrqstComAppConfigDataCopyHandler());
        mapper.put("KRQST_COM_APP_CF_DETAIL", new KrqstComAppConfigDetailDataCopyHandler());
        mapper.put("WWFST_JOB_ASSIGN_SET", new WwfstJobAssignSettingDataCopyHandler());
        mapper.put("WWFST_APPROVAL_SETTING", new WwfstApprovalSettingDataCopyHandler());
        mapper.put("KRCST_TOPPAGE_ALARM_SET", new KrcstToppageAlarmSetDatCopyHandler());
        mapper.put("KSHST_DAI_SER_TYPE_CTR", new KshstDailyServiceTypeControlDataCopyHandler());
    }

    public DataCopyHandler getCopyHandler(String tableName) {
        return mapper.get(tableName);
    }
}
