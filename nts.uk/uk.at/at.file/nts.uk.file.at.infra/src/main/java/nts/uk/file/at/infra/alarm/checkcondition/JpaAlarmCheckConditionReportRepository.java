package nts.uk.file.at.infra.alarm.checkcondition;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.RangeCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeCheckVacation;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeMonCheckItem;
import nts.uk.file.at.app.export.alarm.checkcondition.Agree36ReportData;
import nts.uk.file.at.app.export.alarm.checkcondition.AlarmCheckAnnualHolidayData;
import nts.uk.file.at.app.export.alarm.checkcondition.AlarmCheckCondQuery;
import nts.uk.file.at.app.export.alarm.checkcondition.AlarmCheckConditionReportRepository;
import nts.uk.file.at.app.export.alarm.checkcondition.AlarmCheckConditionUtils;
import nts.uk.file.at.app.export.alarm.checkcondition.DailyReportData;
import nts.uk.file.at.app.export.alarm.checkcondition.MonthReportData;
import nts.uk.file.at.app.export.alarm.checkcondition.MulMonthReportData;
import nts.uk.file.at.app.export.alarm.checkcondition.Schedule4WeekReportData;

@Stateless
public class JpaAlarmCheckConditionReportRepository extends JpaRepository implements AlarmCheckConditionReportRepository{

	@SneakyThrows
	@Override
	public List<Schedule4WeekReportData> getSchedule4WeekConditions(String companyId) {
		List<Schedule4WeekReportData>  result = new ArrayList<>();
		try(PreparedStatement stmt = this.connection().prepareStatement(AlarmCheckCondQuery.SELECT_SCHEDULE_4_WEEK)){
            stmt.setString(1,companyId);
            result = new NtsResultSet(stmt.executeQuery()).getList(x -> toDomainSchedule4Week(x));
        } 
		
		return result;
	}
	
		
	/**
	 * toDomanin calendar company
	 * @param entity
	 * @return
	 */
	private Schedule4WeekReportData toDomainSchedule4Week(NtsResultSet.NtsResultRecord record){
		return Schedule4WeekReportData.createFromJavaType(
				record.getString("CD"),
				record.getString("NAME"),
				record.getString("FILTER_BY_EMP"),
				record.getString("EMP_CDS"),
				record.getString("FILTER_BY_CLS"),
				record.getString("CLS_CDS"),
				record.getString("FILTER_BY_JOB"),
				record.getString("JOB_IDS"),
				record.getString("FILTER_BY_BUSINESSTYPE"),
				record.getString("BUSINESSTYPE_CDS"),
				record.getInt("W4D4_CHECK_COND"));
	}
	
	
	@SneakyThrows
	@Override
	public List<DailyReportData> getDailyConditions(String companyId) {
		List<DailyReportData>  result = new ArrayList<>();
		try(PreparedStatement stmt = this.connection().prepareStatement(AlarmCheckCondQuery.SELECT_DAILY)) {
            stmt.setString(1,companyId);
            result = new NtsResultSet(stmt.executeQuery()).getList(x -> toDomainDaily(x));
        } 
		
		return result;
	}
	
		
	/**
	 * toDomanin calendar company
	 * @param entity
	 * @return
	 */
	private DailyReportData toDomainDaily(NtsResultSet.NtsResultRecord record){
		DailyReportData dailyReportData = new DailyReportData();
		//1: コード
		dailyReportData.setCode(record.getString("CD_1"));
		//2: 名称
		dailyReportData.setName(record.getString("NAME_2"));
		//4: チェック対象範囲 雇用
		dailyReportData.setFilterEmp(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_EMP_4"))));
		//5: チェック対象範囲 雇用対象
		dailyReportData.setEmployees(Optional.ofNullable(record.getString("EMP_CDS_5")));
		//6: チェック対象範囲 分類
		dailyReportData.setFilterClas(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_CLS_6"))));
		//7: チェック対象範囲 分類
		dailyReportData.setClassifications(Optional.ofNullable(record.getString("CLS_CDS_7")));
		//8: チェック対象範囲 職位
		dailyReportData.setFilterJobTitles(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_JOB_8"))));
		//9: チェック対象範囲 職位対象
		dailyReportData.setJobtitles(Optional.ofNullable(record.getString("JOB_IDS_9")));
		//10: チェック対象範囲 勤務種別
		dailyReportData.setFilterWorkType(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_BUSINESSTYPE_10"))));
		//11: チェック対象範囲 勤務種別対象
		dailyReportData.setWorktypeselections(Optional.ofNullable(record.getString("BUSINESSTYPE_CDS_11")));
		//12: 日別実績のエラーアラーム
		dailyReportData.setDailyErrorAlarms(Optional.ofNullable(record.getString("DAILY_ERROR_ALARMS_12")));
		 
		Optional<Integer> userAtr = Optional.ofNullable(record.getInt("USE_ATR"));
		dailyReportData.setUseAtrCond(userAtr);
		if (userAtr.isPresent() && userAtr.get() == 1) {
			dailyReportData.setInsDate(record.getString("INS_DATE"));
			//14: チェック条件 名称
			dailyReportData.setNameCond(Optional.ofNullable(record.getString("NAME_COND_14")));
			//15: チェック条件 チェック項目
			dailyReportData.setCheckItem(Optional.ofNullable(AlarmCheckConditionUtils.getCheckItemStr(Optional.ofNullable(record.getInt("CHECK_ITEM_15")))));
			//16: チェック条件 対象とする勤務種類(TargetServiceType.java)
			dailyReportData.setTargetServiceType(Optional.ofNullable(AlarmCheckConditionUtils.getTargetServiceTypeStr(Optional.ofNullable(record.getInt("WT_COMPARE_ATR_16")))));
			//17: チェック条件 勤務種類
			dailyReportData.setWorktypes(Optional.ofNullable(record.getString("WORKTYPES_17")));
			//18: チェック条件 チェック条件
			dailyReportData.setTargetAttendances(Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_18")));
			
			//--ErrorAlarmConditionType.java(FIXED_VALUE:0, ATTENDANCE_ITEM:1,INPUT_CHECK:2)
			//19(チェック条件 条件(SingleValueCompareType, RangeCompareType))
			Optional<Integer> compareAtrInt = Optional.ofNullable(record.getInt("COMPARE_ATR_19"));
			dailyReportData.setCompareAtrInt(compareAtrInt);
			dailyReportData.setCompareAtr(Optional.ofNullable(AlarmCheckConditionUtils.getCompareAtrStr(compareAtrInt)));
			//--ConditionAtr.java (TIMES:0, TIME_DURATION:1, TIME_WITH_DAY=2, AMOUNT_VALUE=3, DAYS=4)
			dailyReportData.setConditionAtr(Optional.ofNullable(record.getInt("CONDITION_ATR")));
			//20(チェック条件 値１)
			dailyReportData.setStartValue(Optional.ofNullable(record.getInt("START_VALUE_20")));
			//21(チェック条件 値２)
			dailyReportData.setEndValue(Optional.ofNullable(record.getInt("END_VALUE_21")));
			
			//22(チェック条件 複合条件 グループ１(LogicalOperator.java))
			dailyReportData.setConditionOperatorGroup1(Optional.ofNullable(AlarmCheckConditionUtils.getOperatorStr(Optional.ofNullable(record.getInt("CONDITION_OPERATOR_GROUP_1_22")))));
			
			//23(チェック条件 複合条件 計算式)
			dailyReportData.setTargetAttendances1Group1(Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_1_23_1")));
			dailyReportData.setCompareAtr1Group1(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_1_23_2")));
			dailyReportData.setConditionAtr1Group1(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_1_23_3")));
			dailyReportData.setStart1Group1(Optional.ofNullable(record.getInt("START_VALUE_GROUP_1_23_4")));
			dailyReportData.setEnd1Group1(Optional.ofNullable(record.getInt("END_VALUE_GROUP_1_23_5")));
			dailyReportData.setConditionType1Group1(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_1_23_6")));
			dailyReportData.setAttendanceItem1Group1(Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_1_23_7")));
			//24(チェック条件 複合条件 計算式)
			dailyReportData.setTargetAttendances2Group1(Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_1_24_1")));
			dailyReportData.setCompareAtr2Group1(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_1_24_2")));
			dailyReportData.setConditionAtr2Group1(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_1_24_3")));
			dailyReportData.setStart2Group1(Optional.ofNullable(record.getInt("START_VALUE_GROUP_1_24_4")));
			dailyReportData.setEnd2Group1(Optional.ofNullable(record.getInt("END_VALUE_GROUP_1_24_5")));
			dailyReportData.setConditionType2Group1(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_1_24_6")));
			dailyReportData.setAttendanceItem2Group1(Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_1_24_7")));
			//25(チェック条件 複合条件 計算式)
			dailyReportData.setTargetAttendances3Group1(Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_1_25_1")));
			dailyReportData.setCompareAtr3Group1(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_1_25_2")));
			dailyReportData.setConditionAtr3Group1(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_1_25_3")));
			dailyReportData.setStart3Group1(Optional.ofNullable(record.getInt("START_VALUE_GROUP_1_25_4")));
			dailyReportData.setEnd3Group1(Optional.ofNullable(record.getInt("END_VALUE_GROUP_1_25_5")));
			dailyReportData.setConditionType3Group1(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_1_25_6")));
			dailyReportData.setAttendanceItem3Group1(Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_1_25_7")));
			
			//26(チェック条件 複合条件 グループ２)
			Optional<Integer> group2UseAtr = Optional.ofNullable(record.getInt("GROUP2_USE_ATR_26"));
			dailyReportData.setGroup2UseAtr(Optional.ofNullable(AlarmCheckConditionUtils.getUseAtrStr(group2UseAtr)));
			dailyReportData.setGroup2UseAtrInt(group2UseAtr);
			if (group2UseAtr.isPresent() && group2UseAtr.get() == 1) {
				//27(チェック条件 複合条件 グループ2)
				dailyReportData.setConditionOperatorGroup2(Optional.ofNullable(AlarmCheckConditionUtils.getOperatorStr(Optional.ofNullable(record.getInt("CONDITION_OPERATOR_GROUP_2_27")))));
				
				//28(チェック条件 複合条件 計算式)
				dailyReportData.setTargetAttendances1Group2(Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_2_28_1")));
				dailyReportData.setCompareAtr1Group2(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_2_28_2")));
				dailyReportData.setConditionAtr1Group2(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_2_28_3")));
				dailyReportData.setStart1Group2(Optional.ofNullable(record.getInt("START_VALUE_GROUP_2_28_4")));
				dailyReportData.setEnd1Group2(Optional.ofNullable(record.getInt("END_VALUE_GROUP_2_28_5")));
				dailyReportData.setConditionType1Group2(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_2_28_6")));
				dailyReportData.setAttendanceItem1Group2(Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_2_28_7")));
				//29(チェック条件 複合条件 計算式)
				dailyReportData.setTargetAttendances2Group2(Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_2_29_1")));
				dailyReportData.setCompareAtr2Group2(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_2_29_2")));
				dailyReportData.setConditionAtr2Group2(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_2_29_3")));
				dailyReportData.setStart2Group2(Optional.ofNullable(record.getInt("START_VALUE_GROUP_2_29_4")));
				dailyReportData.setEnd2Group2(Optional.ofNullable(record.getInt("END_VALUE_GROUP_2_29_5")));
				dailyReportData.setConditionType2Group2(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_2_29_6")));
				dailyReportData.setAttendanceItem2Group2(Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_2_29_7")));
				//30(チェック条件 複合条件 計算式)
				dailyReportData.setTargetAttendances3Group2(Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_2_30_1")));
				dailyReportData.setCompareAtr3Group2(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_2_30_2")));
				dailyReportData.setConditionAtr3Group2(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_2_30_3")));
				dailyReportData.setStart3Group2(Optional.ofNullable(record.getInt("START_VALUE_GROUP_2_30_4")));
				dailyReportData.setEnd3Group2(Optional.ofNullable(record.getInt("END_VALUE_GROUP_2_30_5")));
				dailyReportData.setConditionType3Group2(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_2_30_6")));
				dailyReportData.setAttendanceItem3Group2(Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_2_30_7")));
				
				//31(--31(チェック条件 複合条件 グループ1とグループ2の条件(LogicalOperator.java)))
				dailyReportData.setOperatorBetweenGroups(Optional.ofNullable(AlarmCheckConditionUtils.getOperatorStr(Optional.ofNullable(record.getInt("OPERATOR_BETWEEN_GROUPS_31")))));
			}
			//32(チェック条件 表示するメッセージ)
			dailyReportData.setMessage(Optional.ofNullable(record.getString("MESSAGE_DISPLAY_32")));
		}
		
		//33(固定チェック条件)
		dailyReportData.setFixedCheckCond(Optional.ofNullable(record.getString("FIXED_CHECK_COND_33")));
		
		return dailyReportData;
	}
	
	
	
	@SneakyThrows
	@Override
	public List<MulMonthReportData> getMulMonthConditions(String companyId) {
		List<MulMonthReportData>  result = new ArrayList<>();
		try(PreparedStatement stmt = this.connection().prepareStatement(AlarmCheckCondQuery.SELECT_MULTIPLE_MONTH)) {
            stmt.setString(1,companyId);
            result = new NtsResultSet(stmt.executeQuery()).getList(x -> toDomainMulMonth(x));
        } 
		
		return result;
	}
	
		
	/**
	 * toDomanin calendar company
	 * @param entity
	 * @return
	 */
	private MulMonthReportData toDomainMulMonth(NtsResultSet.NtsResultRecord record){
		MulMonthReportData mulMonthReportData = new MulMonthReportData();
		//1: コード
		mulMonthReportData.setCode(record.getString("CD_1"));
		//2: 名称
		mulMonthReportData.setName(record.getString("NAME_2"));
		//4: チェック対象範囲 雇用
		mulMonthReportData.setFilterEmp(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_EMP_4"))));
		//5: チェック対象範囲 雇用対象
		mulMonthReportData.setEmployees(Optional.ofNullable(record.getString("EMP_CDS_5")));
		//6: チェック対象範囲 分類
		mulMonthReportData.setFilterClas(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_CLS_6"))));
		//7: チェック対象範囲 分類
		mulMonthReportData.setClassifications(Optional.ofNullable(record.getString("CLS_CDS_7")));
		//8: チェック対象範囲 職位
		mulMonthReportData.setFilterJobTitles(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_JOB_8"))));
		//9: チェック対象範囲 職位対象
		mulMonthReportData.setJobtitles(Optional.ofNullable(record.getString("JOB_IDS_9")));
		//10: チェック対象範囲 勤務種別
		mulMonthReportData.setFilterWorkType(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_BUSINESSTYPE_10"))));
		//11: チェック対象範囲 勤務種別対象
		mulMonthReportData.setWorktypeselections(Optional.ofNullable(record.getString("BUSINESSTYPE_CDS_11")));
		
		 
			//13: チェック条件 名称
		mulMonthReportData.setNameCond(Optional.ofNullable(record.getString("NAME_ALARM_CON_13")));
			//14: チェック条件 チェック項目
		mulMonthReportData.setCheckItem(Optional.ofNullable(AlarmCheckConditionUtils.getCheckItemMulMonthStr(Optional.ofNullable(record.getInt("TYPE_CHECK_ITEM_14")))));
		//15: チェック条件 チェック条件
		mulMonthReportData.setTargetAttendances(Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES")));
			
		//CONDITION
		Optional<Integer> userAtrCond = Optional.ofNullable(record.getInt("IS_USE_FLG_COND"));
		mulMonthReportData.setUseAtrCond(userAtrCond);
		if (userAtrCond.isPresent() && userAtrCond.get() == 1) {
			mulMonthReportData.setConditionAtrCond(Optional.ofNullable(record.getInt("CONDITION_ATR_COND")));
			mulMonthReportData.setConditionTypeCond(Optional.ofNullable(record.getInt("CONDITION_TYPE_COND")));
			Optional<Integer> compareInt = Optional.ofNullable(record.getInt("COMPARE_ATR_COND"));
			mulMonthReportData.setCompareAtrCondInt(compareInt);
			mulMonthReportData.setCompareAtrCond(Optional.ofNullable(AlarmCheckConditionUtils.getCompareAtrStr(compareInt)));
			mulMonthReportData.setStartValueCond(Optional.ofNullable(record.getDouble("START_VALUE_COND")));
			if (compareInt.isPresent() && compareInt.get() >= RangeCompareType.BETWEEN_RANGE_OPEN.value) {
				mulMonthReportData.setEndValueCond(Optional.ofNullable(record.getDouble("END_VALUE_COND")));
			}
		}

		// AVG
		Optional<Integer> userAtrAvg = Optional.ofNullable(record.getInt("IS_USE_FLG_AVG"));
		mulMonthReportData.setUseAtrAvg(userAtrAvg);
		if (userAtrAvg.isPresent() && userAtrAvg.get() == 1) {
			mulMonthReportData.setConditionAtrAvg(Optional.ofNullable(record.getInt("CONDITION_ATR_AVG")));
			mulMonthReportData.setConditionTypeAvg(Optional.ofNullable(record.getInt("CONDITION_TYPE_AVG")));
			Optional<Integer> compareInt = Optional.ofNullable(record.getInt("COMPARE_ATR_AVG"));
			mulMonthReportData.setCompareAtrAvgInt(compareInt);
			mulMonthReportData.setCompareAtrAvg(Optional.ofNullable(AlarmCheckConditionUtils.getCompareAtrStr(compareInt)));
			mulMonthReportData.setStartValueAvg(Optional.ofNullable(record.getDouble("START_VALUE_AVG")));
			if (compareInt.isPresent() && compareInt.get() >= RangeCompareType.BETWEEN_RANGE_OPEN.value) {
				mulMonthReportData.setEndValueAvg(Optional.ofNullable(record.getDouble("END_VALUE_AVG")));
			}
		}

		// CONT
		Optional<Integer> userAtrCont = Optional.ofNullable(record.getInt("IS_USE_FLG_CONT"));
		mulMonthReportData.setUseAtrCont(userAtrCont);
		if (userAtrCont.isPresent() && userAtrCont.get() == 1) {
			mulMonthReportData.setContinueMonth(Optional.ofNullable(record.getInt("CONTINUOUS_MONTH")));
			mulMonthReportData.setConditionAtrCont(Optional.ofNullable(record.getInt("CONDITION_ATR_CONT")));
			mulMonthReportData.setConditionTypeCont(Optional.ofNullable(record.getInt("CONDITION_TYPE_CONT")));
			Optional<Integer> compareInt = Optional.ofNullable(record.getInt("COMPARE_ATR_CONT"));
			mulMonthReportData.setCompareAtrContInt(compareInt);
			mulMonthReportData.setCompareAtrCont(Optional.ofNullable(AlarmCheckConditionUtils.getCompareAtrStr(compareInt)));
			mulMonthReportData.setStartValueCont(Optional.ofNullable(record.getDouble("START_VALUE_CONT")));
			if (compareInt.isPresent() && compareInt.get() >= RangeCompareType.BETWEEN_RANGE_OPEN.value)
				mulMonthReportData.setEndValueCont(Optional.ofNullable(record.getDouble("END_VALUE_CONT")));
		}

		// COSP
		Optional<Integer> userAtrCosp = Optional.ofNullable(record.getInt("IS_USE_FLG_COSP"));
		mulMonthReportData.setUseAtrCosp(userAtrCosp);
		if (userAtrCosp.isPresent() && userAtrCosp.get() == 1) {
			mulMonthReportData.setTimes(Optional.ofNullable(record.getInt("TIMES")));
			mulMonthReportData.setConditionCalculateCosp(Optional.ofNullable(
					AlarmCheckConditionUtils.getCompareAtrStr(Optional.ofNullable(record.getInt("COMPARE_OPERATOR_COSP")))));
			mulMonthReportData.setConditionAtrCosp(Optional.ofNullable(record.getInt("CONDITION_ATR_COSP")));
			mulMonthReportData.setConditionTypeCosp(Optional.ofNullable(record.getInt("CONDITION_TYPE_COSP")));
			Optional<Integer> compareInt = Optional.ofNullable(record.getInt("COMPARE_ATR_COSP"));
			mulMonthReportData.setCompareAtrCospInt(compareInt);
			mulMonthReportData.setCompareAtrCosp(Optional.ofNullable(AlarmCheckConditionUtils.getCompareAtrStr(compareInt)));
			mulMonthReportData.setStartValueCosp(Optional.ofNullable(record.getDouble("START_VALUE_COSP")));
			if (compareInt.isPresent() && compareInt.get() >= RangeCompareType.BETWEEN_RANGE_OPEN.value)
				mulMonthReportData.setEndValueCosp(Optional.ofNullable(record.getDouble("END_VALUE_COSP")));
		}
		// 22(チェック条件 表示するメッセージ)
		mulMonthReportData.setMessage(Optional.ofNullable(record.getString("MESSAGE_DISPLAY_22")));
		mulMonthReportData.setInsDate(record.getString("INS_DATE"));

		return mulMonthReportData;
	}

	@SneakyThrows
	@Override
	public List<MonthReportData> getMonthConditions(String companyId) {
		List<MonthReportData>  result = new ArrayList<>();
		try(PreparedStatement stmt = this.connection().prepareStatement(AlarmCheckCondQuery.SELECT_MONTH)) {
            stmt.setString(1,companyId);
            result = new NtsResultSet(stmt.executeQuery()).getList(x -> toDomainMonth(x));
        } 
		
		return result;
	}
	
		
	/**
	 * toDomanin calendar company
	 * @param entity
	 * @return
	 */
	private MonthReportData toDomainMonth(NtsResultSet.NtsResultRecord record){
		MonthReportData monthReportData = new MonthReportData();
		//1: コード
		monthReportData.setCode(record.getString("CD_1"));
		//2: 名称
		monthReportData.setName(record.getString("NAME_2"));
		//4: チェック対象範囲 雇用
		monthReportData.setFilterEmp(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_EMP_4"))));
		//5: チェック対象範囲 雇用対象
		monthReportData.setEmployees(Optional.ofNullable(record.getString("EMP_CDS_5")));
		//6: チェック対象範囲 分類
		monthReportData.setFilterClas(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_CLS_6"))));
		//7: チェック対象範囲 分類
		monthReportData.setClassifications(Optional.ofNullable(record.getString("CLS_CDS_7")));
		//8: チェック対象範囲 職位
		monthReportData.setFilterJobTitles(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_JOB_8"))));
		//9: チェック対象範囲 職位対象
		monthReportData.setJobtitles(Optional.ofNullable(record.getString("JOB_IDS_9")));
		//10: チェック対象範囲 勤務種別
		monthReportData.setFilterWorkType(AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_BUSINESSTYPE_10"))));
		//11: チェック対象範囲 勤務種別対象
		monthReportData.setWorktypeselections(Optional.ofNullable(record.getString("BUSINESSTYPE_CDS_11")));
		//12: 日別実績のエラーアラーム
		monthReportData.setFixedExtraMonth(Optional.ofNullable(record.getString("FIXED_EXTRA_MON_12")));
		 
		Optional<Integer> userAtr = Optional.ofNullable(record.getInt("USE_ATR"));
		monthReportData.setUseAtrCond(userAtr);
		Optional<Integer> sortBy  = Optional.ofNullable(record.getInt("SORT_BY"));
		monthReportData.setSortBy(sortBy);
		if (userAtr.isPresent() && userAtr.get() == 1) {
			//14: アラームリストのチェック条件 名称
			monthReportData.setNameCond(Optional.ofNullable(record.getString("EXTRA_RESULT_MON_NAME_14")));
			//15: アラームリストのチェック条件 チェック項目)(TypeMonCheckItem.java)
			Optional<Integer> checkItem = Optional.ofNullable(record.getInt("TYPE_CHECK_ITEM_15"));
			monthReportData.setCheckItemInt(checkItem);
			monthReportData.setCheckItem(Optional.ofNullable(AlarmCheckConditionUtils.getCheckItemMonthStr(checkItem)));
			
			if (checkItem.isPresent() && checkItem.get() == TypeMonCheckItem.CERTAIN_DAY_OFF.value) {
				//HOLIDAY_COMPARE_OPERATOR_19, NUMBER_DAY_DIFF_HOLIDAY_1_20, NUMBER_DAY_DIFF_HOLIDAY_2_21
				Optional<Integer> compareOperatorInt = Optional.ofNullable(record.getInt("HOLIDAY_COMPARE_OPERATOR_19"));
				monthReportData.setCompareOperatorHoliday(Optional.ofNullable(AlarmCheckConditionUtils
						.getCompareAtrStr(compareOperatorInt)));
				monthReportData.setCompareOperatorInt(compareOperatorInt);
				monthReportData.setNumdayHoliday1(Optional.ofNullable(record.getDouble("NUMBER_DAY_DIFF_HOLIDAY_1_20")));
				monthReportData.setNumdayHoliday2(Optional.ofNullable(record.getDouble("NUMBER_DAY_DIFF_HOLIDAY_2_21")));
			}
			
			if (checkItem.isPresent() && checkItem.get() == TypeMonCheckItem.CHECK_REMAIN_NUMBER.value) {
				//16: アラームリストのチェック条件 休暇種類(TypeCheckVacation.java)
				Optional<Integer> typeCheckItemInt =  Optional.ofNullable(record.getInt("TYPE_CHECK_VACATION_16"));
				monthReportData.setTypeCheckVacationInt(typeCheckItemInt);
				monthReportData.setTypeCheckVacation(Optional.ofNullable(AlarmCheckConditionUtils.getTypeCheckVacationStr(typeCheckItemInt)));
				//17: アラームリストのチェック条件 休暇項目
				if (typeCheckItemInt.isPresent() 
						&& typeCheckItemInt.get() == TypeCheckVacation.SPECIAL_HOLIDAY.value) {
					monthReportData.setVacationItems(Optional.ofNullable(record.getString("VACATION_ITEMS_17")));
				}
				//VACATION_COMPARE_ATR_19, VACATION_START_VALUE_20, VACATION_END_VALUE_21
				Optional<Integer> vacationCompareAtrInt = Optional.ofNullable(record.getInt("VACATION_COMPARE_ATR_19"));
				monthReportData.setVacationCompareAtr(Optional.ofNullable(AlarmCheckConditionUtils
						.getCompareAtrStr(vacationCompareAtrInt)));
				monthReportData.setVacationCompareAtrInt(vacationCompareAtrInt);
				monthReportData.setVacationStartValue(Optional.ofNullable(record.getDouble("VACATION_START_VALUE_20")));
				monthReportData.setVacationEndValue(Optional.ofNullable(record.getDouble("VACATION_END_VALUE_21")));
			}
			
			if (checkItem.isPresent() && checkItem.get() != TypeMonCheckItem.CERTAIN_DAY_OFF.value
					&& checkItem.get() != TypeMonCheckItem.CHECK_REMAIN_NUMBER.value) {
				// 18: アラームリストのチェック条件 チェック条件
				monthReportData
						.setTargetAttendances(Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_18")));

				// --ErrorAlarmConditionType.java(FIXED_VALUE:0,
				// ATTENDANCE_ITEM:1,INPUT_CHECK:2)
				// 19(アラームリストのチェック条件 条件(SingleValueCompareType, RangeCompareType))
				Optional<Integer> compareAtrInt = Optional.ofNullable(record.getInt("COMPARE_ATR_19"));
				monthReportData.setCompareAtr(Optional.ofNullable(AlarmCheckConditionUtils
						.getCompareAtrStr(compareAtrInt)));
				monthReportData.setCompareAtrInt(compareAtrInt);
				// --ConditionAtr.java (TIMES:0, TIME_DURATION:1,
				// TIME_WITH_DAY=2, AMOUNT_VALUE=3, DAYS=4)
				monthReportData.setConditionAtr(Optional.ofNullable(record.getInt("CONDITION_ATR")));
				// 20(アラームリストのチェック条件 値１)
				monthReportData.setStartValue(Optional.ofNullable(record.getDouble("START_VALUE_20")));
				// 21(アラームリストのチェック条件 値２)
				monthReportData.setEndValue(Optional.ofNullable(record.getDouble("END_VALUE_21")));

				// 22(アラームリストのチェック条件 複合条件 グループ１(LogicalOperator.java))
				monthReportData.setConditionOperatorGroup1(Optional.ofNullable(AlarmCheckConditionUtils
						.getOperatorStr(Optional.ofNullable(record.getInt("CONDITION_OPERATOR_GROUP_1_22")))));

				// 23(アラームリストのチェック条件 複合条件 計算式)
				monthReportData.setTargetAttendances1Group1(
						Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_1_23_1")));
				monthReportData.setCompareAtr1Group1(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_1_23_2")));
				monthReportData
						.setConditionAtr1Group1(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_1_23_3")));
				monthReportData.setStart1Group1(Optional.ofNullable(record.getDouble("START_VALUE_GROUP_1_23_4")));
				monthReportData.setEnd1Group1(Optional.ofNullable(record.getDouble("END_VALUE_GROUP_1_23_5")));
				monthReportData
						.setConditionType1Group1(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_1_23_6")));
				monthReportData
						.setAttendanceItem1Group1(Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_1_23_7")));
				// 24(アラームリストのチェック条件 複合条件 計算式)
				monthReportData.setTargetAttendances2Group1(
						Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_1_24_1")));
				monthReportData.setCompareAtr2Group1(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_1_24_2")));
				monthReportData
						.setConditionAtr2Group1(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_1_24_3")));
				monthReportData.setStart2Group1(Optional.ofNullable(record.getDouble("START_VALUE_GROUP_1_24_4")));
				monthReportData.setEnd2Group1(Optional.ofNullable(record.getDouble("END_VALUE_GROUP_1_24_5")));
				monthReportData
						.setConditionType2Group1(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_1_24_6")));
				monthReportData
						.setAttendanceItem2Group1(Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_1_24_7")));
				// 25(アラームリストのチェック条件 複合条件 計算式)
				monthReportData.setTargetAttendances3Group1(
						Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_1_25_1")));
				monthReportData.setCompareAtr3Group1(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_1_25_2")));
				monthReportData
						.setConditionAtr3Group1(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_1_25_3")));
				monthReportData.setStart3Group1(Optional.ofNullable(record.getDouble("START_VALUE_GROUP_1_25_4")));
				monthReportData.setEnd3Group1(Optional.ofNullable(record.getDouble("END_VALUE_GROUP_1_25_5")));
				monthReportData
						.setConditionType3Group1(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_1_25_6")));
				monthReportData
						.setAttendanceItem3Group1(Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_1_25_7")));

				// 26(アラームリストのチェック条件 複合条件 グループ２)
				Optional<Integer> group2UseAtr = Optional.ofNullable(record.getInt("GROUP2_USE_ATR_26"));
				monthReportData
						.setGroup2UseAtr(Optional.ofNullable(AlarmCheckConditionUtils.getUseAtrStr(group2UseAtr)));
				monthReportData.setGroup2UseAtrInt(group2UseAtr);
				if (group2UseAtr.isPresent() && group2UseAtr.get() == 1) {
					//monthReportData.setInsDate(Optional.ofNullable(record.getString("INS_DATE")));
					//27(アラームリストのチェック条件 複合条件 グループ２)
					monthReportData.setConditionOperatorGroup2(Optional.ofNullable(AlarmCheckConditionUtils
							.getOperatorStr(Optional.ofNullable(record.getInt("CONDITION_OPERATOR_GROUP_2_27")))));

					// 28(アラームリストのチェック条件 複合条件 計算式)
					monthReportData.setTargetAttendances1Group2(
							Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_2_28_1")));
					monthReportData
							.setCompareAtr1Group2(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_2_28_2")));
					monthReportData
							.setConditionAtr1Group2(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_2_28_3")));
					monthReportData.setStart1Group2(Optional.ofNullable(record.getDouble("START_VALUE_GROUP_2_28_4")));
					monthReportData.setEnd1Group2(Optional.ofNullable(record.getDouble("END_VALUE_GROUP_2_28_5")));
					monthReportData
							.setConditionType1Group2(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_2_28_6")));
					monthReportData.setAttendanceItem1Group2(
							Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_2_28_7")));
					// 29(アラームリストのチェック条件 複合条件 計算式)
					monthReportData.setTargetAttendances2Group2(
							Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_2_29_1")));
					monthReportData
							.setCompareAtr2Group2(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_2_29_2")));
					monthReportData
							.setConditionAtr2Group2(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_2_29_3")));
					monthReportData.setStart2Group2(Optional.ofNullable(record.getDouble("START_VALUE_GROUP_2_29_4")));
					monthReportData.setEnd2Group2(Optional.ofNullable(record.getDouble("END_VALUE_GROUP_2_29_5")));
					monthReportData
							.setConditionType2Group2(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_2_29_6")));
					monthReportData.setAttendanceItem2Group2(
							Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_2_29_7")));
					// 30(アラームリストのチェック条件 複合条件 計算式)
					monthReportData.setTargetAttendances3Group2(
							Optional.ofNullable(record.getString("TARGET_ATR_ATTENDANCES_GROUP_2_30_1")));
					monthReportData
							.setCompareAtr3Group2(Optional.ofNullable(record.getInt("COMPARE_ATR_GROUP_2_30_2")));
					monthReportData
							.setConditionAtr3Group2(Optional.ofNullable(record.getInt("CONDITION_ATR_GROUP_2_30_3")));
					monthReportData.setStart3Group2(Optional.ofNullable(record.getDouble("START_VALUE_GROUP_2_30_4")));
					monthReportData.setEnd3Group2(Optional.ofNullable(record.getDouble("END_VALUE_GROUP_2_30_5")));
					monthReportData
							.setConditionType3Group2(Optional.ofNullable(record.getInt("CONDITION_TYPE_GROUP_2_30_6")));
					monthReportData.setAttendanceItem3Group2(
							Optional.ofNullable(record.getInt("ATTENDANCE_ITEM_GROUP_2_30_7")));

					// 31(アラームリストのチェック条件 複合条件 グループ1とグループ2の条件)
					monthReportData.setOperatorBetweenGroups(Optional.ofNullable(AlarmCheckConditionUtils
							.getOperatorStr(Optional.ofNullable(record.getInt("OPERATOR_BETWEEN_GROUPS_31")))));
				}
			}
			//32(アラームリストのチェック条件 表示するメッセージ)
			monthReportData.setMessage(Optional.ofNullable(record.getString("MESSAGE_DISPLAY_32")));
		}
		
		return monthReportData;
	}

	@SneakyThrows
	@Override
	public List<Agree36ReportData> getAgree36Conditions(String companyId) {
		List<Agree36ReportData>  result = new ArrayList<>();
		try(PreparedStatement stmt = this.connection().prepareStatement(AlarmCheckCondQuery.SELECT_AGREE36)) {
            stmt.setString(1,companyId);
            result = new NtsResultSet(stmt.executeQuery()).getList(x -> toDomainAgree36(x));
        } 
		
		return result;
	}
	
	/**
	 * toDomanin calendar company
	 * @param entity
	 * @return
	 */
	private Agree36ReportData toDomainAgree36(NtsResultSet.NtsResultRecord record) {
		return Agree36ReportData.createFromJavaType(
				record.getString("CD_1"),
				record.getString("NAME_2"),
				AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_EMP_4"))),
				record.getString("EMP_CDS_5"),
				AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_CLS_6"))),
				record.getString("CLS_CDS_7"),
				AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_JOB_8"))),
				record.getString("JOB_IDS_9"),
				AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_BUSINESSTYPE_10"))),
				record.getString("BUSINESSTYPE_CDS_11"),
				record.getString("AGREE_COND_ERRS"),
				record.getString("COND_OT_ERRS"));
	}

	@SneakyThrows
	@Override
	public List<AlarmCheckAnnualHolidayData> getAnnualHoliday(String companyId) {
		List<AlarmCheckAnnualHolidayData>  result = new ArrayList<>();
		try(PreparedStatement stmt = this.connection().prepareStatement(AlarmCheckCondQuery.SELECT_ANNUAL_HOLIDAY)) {
            stmt.setString(1,companyId);
            result = new NtsResultSet(stmt.executeQuery()).getList(x -> toDomainAnnualHoliday(x));
        } 
		
		return result;
	}
	
	private AlarmCheckAnnualHolidayData toDomainAnnualHoliday(NtsResultSet.NtsResultRecord record) {
		return AlarmCheckAnnualHolidayData.createFromJavaType(
				record.getString("CD_1"),
				record.getString("NAME_2"),
				AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_EMP_4"))),
				record.getString("EMP_CDS_5"),
				AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_CLS_6"))),
				record.getString("CLS_CDS_7"),
				AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_JOB_8"))),
				record.getString("JOB_IDS_9"),
				AlarmCheckConditionUtils.getFilterStr(Optional.ofNullable(record.getInt("FILTER_BY_BUSINESSTYPE_10"))),
				record.getString("BUSINESSTYPE_CDS_11"),
				record.getString("HDPAID"),
				record.getString("OBL"));
	}
}
