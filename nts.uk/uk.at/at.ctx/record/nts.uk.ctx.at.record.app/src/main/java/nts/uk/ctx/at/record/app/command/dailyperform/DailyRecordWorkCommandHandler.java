package nts.uk.ctx.at.record.app.command.dailyperform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.AffiliationInforOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.AffiliationInforOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.BusinessTypeOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.BusinessTypeOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.AttendanceLeavingGateOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.AttendanceLeavingGateOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.PCLogInfoOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.PCLogInfoOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendancetime.AttendanceTimeOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendancetime.AttendanceTimeOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.BreakTimeOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.BreakTimeOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute.CalcAttrOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute.CalcAttrOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.editstate.EditStateOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.editstate.EditStateOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.goout.OutingTimeOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.goout.OutingTimeOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.optionalitem.OptionalItemOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.optionalitem.OptionalItemOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.remark.RemarkOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.remark.RemarkOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.shorttimework.ShortTimeOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.shorttimework.ShortTimeOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr.SpecificDateAttrOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr.SpecificDateAttrOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workinfo.WorkInformationOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workinfo.WorkInformationOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.AttendanceTimeByWorkOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.AttendanceTimeByWorkOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeavingOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeavingOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordServiceCenter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.divergencetime.service.DivCheckSharedData;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service.ErAlCheckService;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

@Stateless
public class DailyRecordWorkCommandHandler {

	/** 勤務情報： 日別実績の勤務情報 */
	@Inject
	@AttendanceItemLayout(layout = "A", jpPropertyName = "", index = 1)
	private WorkInformationOfDailyPerformCommandAddHandler workInfoAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "A", jpPropertyName = "", index = 1)
	private WorkInformationOfDailyPerformCommandUpdateHandler workInfoUpdateHandler;

	/** 計算区分： 日別実績の計算区分 */
	@Inject
	@AttendanceItemLayout(layout = "B", jpPropertyName = "", index = 2)
	private CalcAttrOfDailyPerformanceCommandAddHandler calcAttrAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "B", jpPropertyName = "", index = 2)
	private CalcAttrOfDailyPerformanceCommandUpdateHandler calcAttrUpdateHandler;

	/** 所属情報： 日別実績の所属情報 */
	@Inject
	@AttendanceItemLayout(layout = "C", jpPropertyName = "", index = 3)
	private AffiliationInforOfDailyPerformCommandAddHandler affiliationInfoAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "C", jpPropertyName = "", index = 3)
	private AffiliationInforOfDailyPerformCommandUpdateHandler affiliationInfoUpdateHandler;

	/** エラー一覧： 社員の日別実績エラー一覧 */
//	@Inject
//	@AttendanceItemLayout(layout = "D", jpPropertyName = "", index = 4)
//	private EmployeeDailyPerErrorCommandAddHandler errorAddHandler;
//	@Inject
//	@AttendanceItemLayout(layout = "D", jpPropertyName = "", index = 4)
//	private EmployeeDailyPerErrorCommandUpdateHandler errorUpdateHandler;
	
	@Inject
	@AttendanceItemLayout(layout = "D", jpPropertyName = "", index = 4)
	private BusinessTypeOfDailyPerformCommandAddHandler businessTypeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "D", jpPropertyName = "", index = 4)
	private BusinessTypeOfDailyPerformCommandUpdateHandler businessTypeUpdateHandler;

	/** 外出時間帯: 日別実績の外出時間帯 */
	@Inject
	@AttendanceItemLayout(layout = "E", jpPropertyName = "", index = 5)
	private OutingTimeOfDailyPerformanceCommandAddHandler outingTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "E", jpPropertyName = "", index = 5)
	private OutingTimeOfDailyPerformanceCommandUpdateHandler outingTimeUpdateHandler;

	/** 休憩時間帯: 日別実績の休憩時間帯 */
	@Inject
	@AttendanceItemLayout(layout = "F", jpPropertyName = "", index = 6)
	private BreakTimeOfDailyPerformanceCommandAddHandler breakTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "F", jpPropertyName = "", index = 6)
	private BreakTimeOfDailyPerformanceCommandUpdateHandler breakTimeUpdateHandler;

	/** 勤怠時間: 日別実績の勤怠時間 */
	@Inject
	@AttendanceItemLayout(layout = "G", jpPropertyName = "", index = 7)
	private AttendanceTimeOfDailyPerformCommandAddHandler attendanceTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "G", jpPropertyName = "", index = 7)
	private AttendanceTimeOfDailyPerformCommandUpdateHandler attendanceTimeUpdateHandler;

	/** 作業別勤怠時間: 日別実績の作業別勤怠時間 */
	@Inject
	@AttendanceItemLayout(layout = "H", jpPropertyName = "", index = 8)
	private AttendanceTimeByWorkOfDailyCommandAddHandler attendanceTimeByWorkAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "H", jpPropertyName = "", index = 8)
	private AttendanceTimeByWorkOfDailyCommandUpdateHandler attendanceTimeByWorkUpdateHandler;

	/** 出退勤: 日別実績の出退勤 */
	@Inject
	@AttendanceItemLayout(layout = "I", jpPropertyName = "", index = 9)
	private TimeLeavingOfDailyPerformanceCommandAddHandler timeLeavingAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "I", jpPropertyName = "", index = 9)
	private TimeLeavingOfDailyPerformanceCommandUpdateHandler timeLeavingUpdatedHandler;

	/** 短時間勤務時間帯: 日別実績の短時間勤務時間帯 */
	@Inject
	@AttendanceItemLayout(layout = "J", jpPropertyName = "", index = 10)
	private ShortTimeOfDailyCommandAddHandler shortWorkTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "J", jpPropertyName = "", index = 10)
	private ShortTimeOfDailyCommandUpdateHandler shortWorkTimeUpdateHandler;

	/** 特定日区分: 日別実績の特定日区分 */
	@Inject
	@AttendanceItemLayout(layout = "K", jpPropertyName = "", index = 11)
	private SpecificDateAttrOfDailyCommandAddHandler specificDateAttrAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "K", jpPropertyName = "", index = 11)
	private SpecificDateAttrOfDailyCommandUpdateHandler specificDateAttrUpdateHandler;

	/** 入退門: 日別実績の入退門 */
	@Inject
	@AttendanceItemLayout(layout = "L", jpPropertyName = "", index = 12)
	private AttendanceLeavingGateOfDailyCommandAddHandler attendanceLeavingGateAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "L", jpPropertyName = "", index = 12)
	private AttendanceLeavingGateOfDailyCommandUpdateHandler attendanceLeavingGateUpdateHandler;

	/** 任意項目: 日別実績の任意項目 */
	@Inject
	@AttendanceItemLayout(layout = "M", jpPropertyName = "", index = 13)
	private OptionalItemOfDailyPerformCommandAddHandler optionalItemAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "M", jpPropertyName = "", index = 13)
	private OptionalItemOfDailyPerformCommandUpdateHandler optionalItemUpdateHandler;

	/** 編集状態: 日別実績の編集状態 */
	@Inject
	//@AttendanceItemLayout(layout = "N", jpPropertyName = "", index = 14)
	private EditStateOfDailyPerformCommandAddHandler editStateAddHandler;
	@Inject
	//@AttendanceItemLayout(layout = "N", jpPropertyName = "", index = 14)
	private EditStateOfDailyPerformCommandUpdateHandler editStateUpdateHandler;

	/** 臨時出退勤: 日別実績の臨時出退勤 */
	@Inject
	@AttendanceItemLayout(layout = "O", jpPropertyName = "", index = 15)
	private TemporaryTimeOfDailyPerformanceCommandAddHandler temporaryTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "O", jpPropertyName = "", index = 15)
	private TemporaryTimeOfDailyPerformanceCommandUpdateHandler temporaryTimeUpdateHandler;
	
	@Inject
	@AttendanceItemLayout(layout = "P", jpPropertyName = "", index = 16)
	private PCLogInfoOfDailyCommandAddHandler pcLogInfoAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "P", jpPropertyName = "", index = 16)
	private PCLogInfoOfDailyCommandUpdateHandler pcLogInfoUpdateHandler;

	@Inject
	@AttendanceItemLayout(layout = "Q", jpPropertyName = "", index = 17)
	private RemarkOfDailyCommandAddHandler remarksAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "Q", jpPropertyName = "", index = 17)
	private RemarkOfDailyCommandUpdateHandler remarksUpdateHandler;
	
	@Inject
	private CalculateDailyRecordServiceCenter calcService;
	
	@Inject 
	private ErAlCheckService determineErrorAlarmWorkRecordService;
	
	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepository;
	
	@Inject
	private DailyRecordWorkFinder finder;
	
	private final List<String> DOMAIN_CHANGED_BY_CALCULATE = Arrays.asList("G", "M");
	
	private final List<String> DOMAIN_CHANGE_EVENT = Arrays.asList("A", "I");
	
	private final Map<String, List<String>> DOMAIN_CHANGED_BY_EVENT = new HashMap<>();
	{
		DOMAIN_CHANGED_BY_EVENT.put("A", Arrays.asList("I", "F"));
		DOMAIN_CHANGED_BY_EVENT.put("I", Arrays.asList("F"));
	}

	public void handleAdd(DailyRecordWorkCommand command) {
		handler(command, false);
	}
	
	public void handleUpdate(DailyRecordWorkCommand command) {
		handler(command, true);
	}
	
	public void handleAdd(List<DailyRecordWorkCommand> command) {
		handler(command, false);
	}
	
	public void handleUpdate(List<DailyRecordWorkCommand> command) {
		handler(command, true);
	}

	private <T extends DailyWorkCommonCommand> void handler(DailyRecordWorkCommand command, boolean isUpdate) {
		handler(Arrays.asList(command), isUpdate);
	}
	
	@SuppressWarnings({ "unchecked" })
	private <T extends DailyWorkCommonCommand> void handler(List<DailyRecordWorkCommand> commands, boolean isUpdate) {
		registerNotCalcDomain(commands, isUpdate);
		
		Set<String> mapped = new HashSet<>();
		List<EmployeeDailyPerError> errors = calcIfNeed(mapped, commands);
		commands.stream().forEach(command -> {
			mapped.stream().forEach(c -> {
				CommandFacade<T> handler = (CommandFacade<T>) getHandler(c, isUpdate);
				if(handler != null){
					handler.handle((T) command.getCommand(c));
				}
			});
		});
		
		//check and insert error;
//		determineErrorAlarmWorkRecordService.checkAndInsert(command.getEmployeeId(), command.getWorkDate());
		determineErrorAlarmWorkRecordService.createEmployeeDailyPerError(errors);
		
		DivCheckSharedData.clearAll();
	}

	@SuppressWarnings({ "unchecked" })
	private <T extends DailyWorkCommonCommand> void registerNotCalcDomain(List<DailyRecordWorkCommand> commands, boolean isUpdate) {
		commands.stream().forEach(command -> {
			Set<String> mapped = new HashSet<>();
			mapped.addAll(command.itemValues().stream().map(c -> getGroup(c)).distinct().collect(Collectors.toSet()));
			mapped.stream().filter(c -> !DOMAIN_CHANGED_BY_CALCULATE.contains(c)).forEach(c -> {
				CommandFacade<T> handler = (CommandFacade<T>) getHandler(c, isUpdate);
				if(handler != null){
					handler.handle((T) command.getCommand(c));
				}
			});
			//remove data error
			employeeDailyPerErrorRepository.removeParam(command.getEmployeeId(), command.getWorkDate());
			Set<String> updating = new HashSet<>();
			DOMAIN_CHANGE_EVENT.stream().filter(l -> mapped.contains(l)).forEach(l -> {
				updating.addAll(DOMAIN_CHANGED_BY_EVENT.get(l));
			});
			updating.stream().forEach(layout -> {
				ConvertibleAttendanceItem updatedD = finder.getFinder(layout).find(command.getEmployeeId(), command.getWorkDate());
				command.getCommand(layout).setRecords(updatedD);
			});
		});
	}
	
	private List<EmployeeDailyPerError> calcIfNeed(Set<String> group, List<DailyRecordWorkCommand> commands){
		List<IntegrationOfDaily> mapped = commands.stream().map(c -> toDomain(c)).collect(Collectors.toList());
		List<IntegrationOfDaily> calced = calcService.calculate(mapped);
		commands.stream().forEach(c -> {
			calced.stream().filter(d -> d.getAffiliationInfor().getEmployeeId().equals(c.getEmployeeId()) 
					&& d.getAffiliationInfor().getYmd().equals(c.getWorkDate()))
			.findFirst().ifPresent(d -> {
				DOMAIN_CHANGED_BY_CALCULATE.stream().forEach(dcbc -> {
					c.getCommand(dcbc).updateData(getDomain(dcbc, d));
				});
//				c.getAttendanceTime().updateData(d.getAttendanceTimeOfDailyPerformance().orElse(null));
//				c.getOptionalItem().updateData(d.getAnyItemValue().orElse(null));
			});
		});
		group.addAll(DOMAIN_CHANGED_BY_CALCULATE);
		return calced.stream().map(d -> d.getEmployeeError()).flatMap(List::stream).collect(Collectors.toList());
//		}
	}

	private IntegrationOfDaily toDomain(DailyRecordWorkCommand c) {
		return new IntegrationOfDaily(c.getWorkInfo().toDomain(), 
										c.getCalcAttr().toDomain(), 
										c.getAffiliationInfo().toDomain(),
										c.getBusinessType().toDomain(), 
										c.getPcLogInfo().toDomain(), 
										Arrays.asList(c.getErrors().toDomain()), 
										c.getOutingTime().toDomain(), 
										c.getBreakTime().toDomain(), 
										c.getAttendanceTime().toDomain(), 
										c.getAttendanceTimeByWork().toDomain(), 
										c.getTimeLeaving().toDomain(), 
										c.getShortWorkTime().toDomain(), 
										c.getSpecificDateAttr().toDomain(), 
										c.getAttendanceLeavingGate().toDomain(), 
										c.getOptionalItem().toDomain(), 
										c.getEditState().toDomain(), 
										c.getTemporaryTime().toDomain());
	}
	
	private CommandFacade<?> getHandler(String group, boolean isUpdate) {
		CommandFacade<?> handler = null;
		switch (group) {
		case "A":
			handler = isUpdate ? this.workInfoUpdateHandler : this.workInfoAddHandler;
			break;
		case "B":
			handler = isUpdate ? this.calcAttrUpdateHandler : this.calcAttrAddHandler;
			break;
		case "C":
			handler = isUpdate ? this.affiliationInfoUpdateHandler : this.affiliationInfoAddHandler;
			break;
		case "D":
			handler = isUpdate ? this.businessTypeUpdateHandler : this.businessTypeAddHandler;
			break;
		case "E":
			handler = isUpdate ? this.outingTimeUpdateHandler : this.outingTimeAddHandler;
			break;
		case "F":
			handler = isUpdate ? this.breakTimeUpdateHandler : this.breakTimeAddHandler;
			break;
		case "G":
			handler = isUpdate ? this.attendanceTimeUpdateHandler : this.attendanceTimeAddHandler;
			break;
		case "H":
			handler = isUpdate ? this.attendanceTimeByWorkUpdateHandler : this.attendanceTimeByWorkAddHandler;
			break;
		case "I":
			handler = isUpdate ? this.timeLeavingUpdatedHandler : this.timeLeavingAddHandler;
			break;
		case "J":
			handler = isUpdate ? this.shortWorkTimeUpdateHandler : this.shortWorkTimeAddHandler;
			break;
		case "K":
			handler = isUpdate ? this.specificDateAttrUpdateHandler : this.specificDateAttrAddHandler;
			break;
		case "L":
			handler = isUpdate ? this.attendanceLeavingGateUpdateHandler : this.attendanceLeavingGateAddHandler;
			break;
		case "M":
			handler = isUpdate ? this.optionalItemUpdateHandler : this.optionalItemAddHandler;
			break;
		case "N":
			handler = isUpdate ? this.editStateUpdateHandler : this.editStateAddHandler;
			break;
		case "O":
			handler = isUpdate ? this.temporaryTimeUpdateHandler : this.temporaryTimeAddHandler;
			break;
		case "P":
			handler = isUpdate ? this.pcLogInfoUpdateHandler : this.pcLogInfoAddHandler;
			break;
		case "Q":
			handler = isUpdate ? this.remarksUpdateHandler : this.remarksAddHandler;
			break;
		default:
			break;
		}
		return handler;
	}
	
	private Object getDomain(String group, IntegrationOfDaily domain) {
		Object returnD = null;
		switch (group) {
		case "A":
			returnD = domain.getWorkInformation();
			break;
		case "B":
			returnD = domain.getCalAttr();
			break;
		case "C":
			returnD = domain.getAffiliationInfor();
			break;
		case "D":
			returnD = domain.getBusinessType().orElse(null);
			break;
		case "E":
			returnD = domain.getOutingTime().orElse(null);
			break;
		case "F":
			returnD = domain.getBreakTime();
			break;
		case "G":
			returnD = domain.getAttendanceTimeOfDailyPerformance().orElse(null);
			break;
		case "H":
			returnD = domain.getAttendancetimeByWork().orElse(null);
			break;
		case "I":
			returnD = domain.getAttendanceLeave().orElse(null);
			break;
		case "J":
			returnD = domain.getShortTime().orElse(null);
			break;
		case "K":
			returnD = domain.getSpecDateAttr().orElse(null);
			break;
		case "L":
			returnD = domain.getAttendanceLeavingGate().orElse(null);
			break;
		case "M":
			returnD = domain.getAnyItemValue().orElse(null);
			break;
		case "N":
			returnD = domain.getEditState();
			break;
		case "O":
			returnD = domain.getTempTime().orElse(null);
			break;
		case "P":
			returnD = domain.getPcLogOnInfo().orElse(null);
			break;
		case "Q":
//			returnD = isUpdate ? this.remarksUpdateHandler : this.remarksAddHandler;
			break;
		default:
			break;
		}
		return returnD;
	}

	private String getGroup(ItemValue c) {
		return String.valueOf(c.layoutCode().charAt(0));
	}

}
