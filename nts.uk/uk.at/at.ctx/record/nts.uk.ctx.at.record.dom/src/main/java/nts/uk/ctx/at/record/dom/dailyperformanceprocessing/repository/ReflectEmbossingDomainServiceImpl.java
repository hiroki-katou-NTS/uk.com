package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.enterprise.inject.New;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.BreakSwitchClass;
import nts.uk.ctx.at.record.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ProcessTimeOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.StampReflectRangeOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.StampReflectTimezoneOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.TimePrintDestinationOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.TimeZoneOutput;
import nts.uk.ctx.at.record.dom.stamp.ReflectedAtr;
import nts.uk.ctx.at.record.dom.stamp.StampAtr;
import nts.uk.ctx.at.record.dom.stamp.StampItem;
import nts.uk.ctx.at.record.dom.stamp.StampMethod;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.record.dom.workinformation.primitivevalue.WorkTimeCode;
import nts.uk.ctx.at.record.dom.workinformation.primitivevalue.WorkTypeCode;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.common.FontRearSection;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.MultiStampTimePiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class ReflectEmbossingDomainServiceImpl implements ReflectEmbossingDomainService {
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeRepo;
	@Inject
	private StampReflectionManagementRepository stampRepo;
	@Inject
	private WorkInformationRepository workInforRepo;
	@Inject
	private WorkTypeRepository WorkRepo;
	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private ReflectWorkInformationDomainService reflectWorkInformationDomainService;
	@Inject
	private OutingTimeOfDailyPerformanceRepository OutRepo;
	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeRepo;

	private List<OutingTimeOfDailyPerformance> lstOutingTimeOfDailyPerformance = new ArrayList<OutingTimeOfDailyPerformance>();
	private List<TemporaryTimeOfDailyPerformance> lstTemporaryTimeOfDailyPerformance = new ArrayList<TemporaryTimeOfDailyPerformance>();
	private List<StampItem> lstStamp = new ArrayList<StampItem>();

	@Override
	public void reflectStamp(WorkInfoOfDailyPerformance WorkInfo, TimeLeavingOfDailyPerformance timeDailyPer,
			List<StampItem> lstStampItem, StampReflectRangeOutput s, GeneralDate date, String employeeId) {
		if (lstStampItem == null) {
			return;
		}
		lstStampItem.forEach(x -> {

			switch (x.getStampAtr().value) {
			case 0: // 出勤
				String confirmReflectRange = this.confirmReflectRange(x, s);
				if ("range1".equals(confirmReflectRange)) {
					// 出退勤区分 = 出勤
					String attendanceClass = "出勤";
					// 実打刻区分 = 実打刻
					String actualStampClass = "実打刻";
					int worktNo = 1;

					this.reflectActualTimeOrAttendence(WorkInfo, timeDailyPer, date, employeeId, x, attendanceClass,
							actualStampClass, worktNo);
					// 出退勤区分 = 出勤
					attendanceClass = "出勤";
					// 実打刻区分 = 実打刻
					actualStampClass = "打刻";
					worktNo = 1;
					this.reflectActualTimeOrAttendence(WorkInfo, timeDailyPer, date, employeeId, x, attendanceClass,
							actualStampClass, worktNo);

					//

				} else if ("range2".equals(confirmReflectRange)) {
					// 出退勤区分 = 出勤
					String attendanceClass = "出勤";
					// 実打刻区分 = 実打刻
					String actualStampClass = "実打刻";
					int worktNo = 2;

					this.reflectActualTimeOrAttendence(WorkInfo, timeDailyPer, date, employeeId, x, attendanceClass,
							actualStampClass, worktNo);
					// 出退勤区分 = 出勤
					attendanceClass = "出勤";
					// 実打刻区分 = 実打刻
					actualStampClass = "打刻";
					worktNo = 2;
					this.reflectActualTimeOrAttendence(WorkInfo, timeDailyPer, date, employeeId, x, attendanceClass,
							actualStampClass, worktNo);

				} else {

				}
				break;
			case 1: // 退勤
				// in or outrange
				String confirmReflectRangeLeavingTime = this.confirmReflectRangeLeavingTime(x, s);
				if ("range1".equals(confirmReflectRangeLeavingTime)) {
					// 出退勤区分 = 退勤
					String attendanceClass = "退勤";
					// 実打刻区分 = 実打刻
					String actualStampClass = "実打刻";
					int worktNo = 1;
					this.reflectActualTimeOrAttendence(WorkInfo, timeDailyPer, date, employeeId, x, attendanceClass,
							actualStampClass, worktNo);
					// 出退勤区分 = 退勤
					attendanceClass = "退勤";
					// 実打刻区分 = 打刻
					actualStampClass = "打刻";
					worktNo = 1;
					this.reflectActualTimeOrAttendence(WorkInfo, timeDailyPer, date, employeeId, x, attendanceClass,
							actualStampClass, worktNo);

				} else if ("range2".equals(confirmReflectRangeLeavingTime)) {
					// 出退勤区分 = 退勤
					String attendanceClass = "退勤";
					// 実打刻区分 = 実打刻
					String actualStampClass = "実打刻";
					int worktNo = 2;
					this.reflectActualTimeOrAttendence(WorkInfo, timeDailyPer, date, employeeId, x, attendanceClass,
							actualStampClass, worktNo);
					// 出退勤区分 = 退勤
					attendanceClass = "退勤";
					// 実打刻区分 = 打刻
					actualStampClass = "打刻";
					worktNo = 2;
					this.reflectActualTimeOrAttendence(WorkInfo, timeDailyPer, date, employeeId, x, attendanceClass,
							actualStampClass, worktNo);

				} else {

				}

				// todo
				break;
			case 4:
			case 5: // 外出,戻り
				// Thay đổi 打刻の時刻 của ngày đang xử lý thành 時刻 tương ứng với
				// ngày đang xử lý
				// 打刻を反映するか確認する (Xác nhận )
				ProcessTimeOutput processTimeOutput = new ProcessTimeOutput();
				boolean confirmReflectStamp = confirmReflectStamp(s, x, processTimeOutput);
				if (confirmReflectStamp) {
					// stampAtr
					StampAtr stampAtr = x.getStampAtr();
					// 外出
					if (stampAtr.value == 4) {
						// *7 外出打刻を反映する (Phản ánh 外出打刻 (thời diểm check ra
						// ngoài))
						reflectTimeGoOutCheck(date, employeeId, x, processTimeOutput);
						// *7
					}
					// 戻り
					else if (stampAtr.value == 5) {
						// 8* 戻り打刻を反映する (Phản ánh 戻り打刻 (THời điểm check quay
						// về))
						reflectTimeComeBackCheck(date, employeeId, x, processTimeOutput);
						// 8*
					}
				} else {

				}

				// todo
				break;
			case 8:
			case 9:
				// todo 開始 , 終了
				// 9* ドメインモデル「臨時勤務管理」を取得する (Lấy về domain model "臨時勤務管理")
				// chưa sử lý (fixed) sửa dụng
				// 9*
				String check = "used";
				if ("used".equals(check)) {
					// 10* Chuyển thời gian check tay đang xử lý sang thời gian
					// tương ứng với ngày tháng năm đang xử lý
					ProcessTimeOutput processTimeOutput1 = new ProcessTimeOutput();
					AttendanceTime attendanceTime = x.getAttendanceTime();
					processTimeOutput1.setTimeOfDay(new TimeWithDayAttr(attendanceTime.v()));
					// 10*
					// 11* Xác nhận xem có phản ảnh check tay chưa)
					if (s.getTemporary().getStart().v() <= processTimeOutput1.getTimeOfDay().v()
							&& s.getTemporary().getEnd().v() >= processTimeOutput1.getTimeOfDay().v()) {
						// reflect
						if (x.getStampAtr().value == 8) {
							// 開始
							reflectTimeTemporaryStart(date, employeeId, x, processTimeOutput1);

						} else if (x.getStampAtr().value == 9) {
							// 終了
							reflectTimeTemporaryEnd(date, employeeId, x, processTimeOutput1);
						}

					}
					// 11*

				}

				break;
			default:
				break;
			}

		});

	}
	
	// *7 臨時終了打刻を反映する (Phản ánh 打刻 kết thúc tạm thời)
		private void reflectTimeTemporaryEnd(GeneralDate date, String employeeId, StampItem x,
				ProcessTimeOutput processTimeOutput) {
			Optional<TemporaryTimeOfDailyPerformance> temporaryTimeOptional = this.temporaryTimeRepo.findByKey(employeeId,
					date);
			//

			if (temporaryTimeOptional.isPresent()) {
				TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance = temporaryTimeOptional.get();
				List<TimeLeavingWork> timeLeavingWorks = temporaryTimeOfDailyPerformance.getTimeLeavingWorks();
				Collections.sort(timeLeavingWorks, new Comparator<TimeLeavingWork>() {
					public int compare(TimeLeavingWork o1, TimeLeavingWork o2) {
						int t1 = o1.getAttendanceStamp().getStamp().getTimeWithDay().v().intValue();
						int t2 = o2.getAttendanceStamp().getStamp().getTimeWithDay().v().intValue();
						if (t1 == t2)
							return 0;
						return t1 < t2 ? -1 : 1;
					}
				});

				// *7.1 出退勤Listに最大枠数分の枠を用意する (Trong 出退勤List, chuẩn bị phần tử có số
				// lượng phần tử lớn nhất)
				// 臨時勤務管理 chưa có (fixed)
				// 最大使用回数 = 11;
				int Maxcount = 11;
				int timeLeavingSize = timeLeavingWorks.size();
				if (timeLeavingSize < Maxcount) {
					for (int i = 0; i < Maxcount - 11; i++) {
						timeLeavingWorks.add(new TimeLeavingWork(null, null, null));
					}
				}
				// *7.1
				int timeLeavingWorkSize = timeLeavingWorks.size();
				boolean isBreak = false;
				List<TimeLeavingWork> newTimeLeavingWorks =  null;
				for (int i = 0; i < timeLeavingWorkSize; i++) {
					TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(i);
					TimeActualStamp leaveStamp = timeLeavingWork.getLeaveStamp();
					if (leaveStamp != null && leaveStamp.getStamp() !=null) {
						// 8* 打刻時刻と臨時時刻が同一か判定する (Đánh giá xem 打刻時刻 và 臨時時刻 có giống
						// nhau không)
						// 臨時勤務管理 chưa có (fixed) true (đồng nhất thời gian) false
						// (k đồng nhất)
						boolean equal = true;
						// 8*
						if (equal) {
							// 打刻を出退勤．退勤．実打刻に入れる (Set 打刻 vào 退勤．退勤．実打刻)
							timeLeavingWork = putInActualStampOfLeaveWork(x, processTimeOutput, timeLeavingWork);
							newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
							isBreak = true;
							break;
						}

					} else {
						
						if(timeLeavingWork.getAttendanceStamp()==null || timeLeavingWork.getAttendanceStamp().getStamp() == null || (timeLeavingWork.getAttendanceStamp()!=null && timeLeavingWork.getAttendanceStamp().getStamp() != null && timeLeavingWork.getAttendanceStamp().getStamp().getTimeWithDay().v()<=processTimeOutput.getTimeOfDay().v())){
							if(i+1==timeLeavingWorkSize || timeLeavingWorks.get(i+1)==null || timeLeavingWorks.get(i+1).getAttendanceStamp()==null || timeLeavingWorks.get(i+1).getAttendanceStamp().getStamp() == null  || (i+1<timeLeavingWorkSize && timeLeavingWorks.get(i+1)!=null && timeLeavingWorks.get(i+1).getAttendanceStamp()!=null && timeLeavingWorks.get(i+1).getAttendanceStamp().getStamp()!=null && processTimeOutput.getTimeOfDay().v() < timeLeavingWorks.get(i+1).getAttendanceStamp().getStamp().getTimeWithDay().v() )){
								timeLeavingWork = putTimeLeaveForActualAndStamp(x, processTimeOutput, timeLeavingWork);
								newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
								isBreak = true;
								break;
							}
						}
						
					}
				}
				if(!isBreak){
					newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
				}
				if(newTimeLeavingWorks==null|| newTimeLeavingWorks.size()==0){
					this.lstTemporaryTimeOfDailyPerformance.add(new TemporaryTimeOfDailyPerformance(employeeId, temporaryTimeOfDailyPerformance.getWorkTimes(), timeLeavingWorks, date));
				}else{
					this.lstTemporaryTimeOfDailyPerformance.add(new TemporaryTimeOfDailyPerformance(employeeId, temporaryTimeOfDailyPerformance.getWorkTimes(), newTimeLeavingWorks, date));
				}
				
				
			}
		}
		
		// 打刻を出退勤．退勤（実打刻と打刻）に入れる (Set 打刻 vào 出退勤．退勤（実打刻と打刻))
		private TimeLeavingWork putTimeLeaveForActualAndStamp(StampItem x, ProcessTimeOutput processTimeOutput,
				TimeLeavingWork timeLeavingWork) {

			// fixed 丸め設定 (InstantRounding )
			// ,
			// (FontRearSection) 前後区分 = 後 ,
			// (RoundingTimeUnit) 時刻丸め単位 =
			// 1;

			TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
			FontRearSection fontRearSection = FontRearSection.AFTER;
			RoundingTimeUnit roundTimeUnit = RoundingTimeUnit.ONE;
			int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, fontRearSection, roundTimeUnit);
			processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
			// 7.2.1
			/*
			WorkStamp actualStamp = timeLeavingWork.getLeaveStamp().getActualStamp();
			actualStamp.setAfterRoundingTime(processTimeOutput.getTimeAfter());
			actualStamp.setTimeWithDay(processTimeOutput.getTimeOfDay());
			actualStamp.setLocationCode(x.getWorkLocationCd());
			*/
			WorkStamp actualStamp =null;
			switch (x.getStampMethod().value) {
			// タイムレコーダー → タイムレコーダー
			case 0:
				 actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
				break;
			// Web → Web打刻入力
			case 1:
				 actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.WEB_STAMP_INPUT);
				break;
			// ID入力 → タイムレコーダ(ID入力)
			case 2:
				actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
				break;
			// 磁気カード → タイムレコーダ(磁気カード)
			case 3:
				actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
				break;
			// ICカード → タイムレコーダ(ICカード)
			case 4:
				actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
				break;
			// 指紋 → タイムレコーダ(指紋打刻)
			case 5:
				actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
				break;
			// その他 no cover
			default:
				actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
				break;
			}

			// 反映済み区分 ← true stamp
			StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
					x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
					x.getGoOutReason(), x.getDate(), x.getPersonId(), ReflectedAtr.REFLECTED);
			lstStamp.add(stampItem);
			
			return new TimeLeavingWork(timeLeavingWork.getWorkNo(), timeLeavingWork.getAttendanceStamp(), new TimeActualStamp(actualStamp, actualStamp, timeLeavingWork.getLeaveStamp().getNumberOfReflectionStamp() + 1));
			
			
		}
		
		// 打刻を出退勤．退勤．実打刻に入れる (Set 打刻 vào 退勤．退勤．実打刻)
		private TimeLeavingWork putInActualStampOfLeaveWork(StampItem x, ProcessTimeOutput processTimeOutput,
				TimeLeavingWork timeLeavingWork) {
			WorkStamp leaveStamp = timeLeavingWork.getLeaveStamp().getActualStamp();
			if (leaveStamp == null) {
				// (fixed) lam tron
				TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
				FontRearSection fontRearSection = FontRearSection.AFTER;
				RoundingTimeUnit roundTimeUnit = RoundingTimeUnit.ONE;
				int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, fontRearSection, roundTimeUnit);
				processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));

				WorkStamp actualStamp = null;
				/*
				ac.setAfterRoundingTime(processTimeOutput.getTimeAfter());
				ac.setTimeWithDay(processTimeOutput.getTimeOfDay());
				ac.setLocationCode(x.getWorkLocationCd());
				*/

				switch (x.getStampMethod().value) {
				// タイムレコーダー → タイムレコーダー
				case 0:
					actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
					break;
				// Web → Web打刻入力
				case 1:
					actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.WEB_STAMP_INPUT);
					break;
				// ID入力 → タイムレコーダ(ID入力)
				case 2:
					actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
					break;
				// 磁気カード → タイムレコーダ(磁気カード)
				case 3:
					actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
					break;
				// ICカード → タイムレコーダ(ICカード)
				case 4:
					actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
					break;
				// 指紋 → タイムレコーダ(指紋打刻)
				case 5:
					actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
					break;
				// その他 no cover
				default:
					actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
					break;
				}
				
				return new TimeLeavingWork(timeLeavingWork.getWorkNo(), timeLeavingWork.getAttendanceStamp(), new TimeActualStamp(actualStamp, timeLeavingWork.getLeaveStamp().getStamp(), timeLeavingWork.getLeaveStamp().getNumberOfReflectionStamp()));

			} 
			
				// 反映済み区分 = true
				StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
						x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
						x.getGoOutReason(), x.getDate(), x.getPersonId(), ReflectedAtr.REFLECTED);
				lstStamp.add(stampItem);
				
				return new TimeLeavingWork(timeLeavingWork.getWorkNo(), timeLeavingWork.getAttendanceStamp(), new TimeActualStamp(timeLeavingWork.getLeaveStamp().getActualStamp(), timeLeavingWork.getLeaveStamp().getStamp(), timeLeavingWork.getLeaveStamp().getNumberOfReflectionStamp()+1));
			
		}	
		
		
	
	

	// *7 臨時開始打刻を反映する (Phản ánh 打刻 bắt đầu tạm thời)
	private void reflectTimeTemporaryStart(GeneralDate date, String employeeId, StampItem x,
			ProcessTimeOutput processTimeOutput) {
		Optional<TemporaryTimeOfDailyPerformance> temporaryTimeOptional = this.temporaryTimeRepo.findByKey(employeeId,
				date);
		//

		if (temporaryTimeOptional.isPresent()) {
			TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance = temporaryTimeOptional.get();
			List<TimeLeavingWork> timeLeavingWorks = temporaryTimeOfDailyPerformance.getTimeLeavingWorks();
			Collections.sort(timeLeavingWorks, new Comparator<TimeLeavingWork>() {
				public int compare(TimeLeavingWork o1, TimeLeavingWork o2) {
					int t1 = o1.getAttendanceStamp().getStamp().getTimeWithDay().v().intValue();
					int t2 = o2.getAttendanceStamp().getStamp().getTimeWithDay().v().intValue();
					if (t1 == t2)
						return 0;
					return t1 < t2 ? -1 : 1;
				}
			});

			// *7.1 出退勤Listに最大枠数分の枠を用意する (Trong 出退勤List, chuẩn bị phần tử có số
			// lượng phần tử lớn nhất)
			// 臨時勤務管理 chưa có (fixed)
			// 最大使用回数 = 11;
			int Maxcount = 11;
			int timeLeavingSize = timeLeavingWorks.size();
			if (timeLeavingSize < Maxcount) {
				for (int i = 0; i < Maxcount - 11; i++) {
					timeLeavingWorks.add(new TimeLeavingWork(null, null, null));
				}
			}
			// *7.1
			int timeLeavingWorkSize = timeLeavingWorks.size();
			List<TimeLeavingWork> newTimeLeavingWorks =null;
			boolean isBreak = false;
			for (int i = 0; i < timeLeavingWorkSize; i++) {
				TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(i);
				TimeActualStamp attendanceStamp = timeLeavingWork.getAttendanceStamp();
				if (attendanceStamp != null && attendanceStamp.getStamp()!=null) {
					// 8* 打刻時刻と臨時時刻が同一か判定する (Đánh giá xem 打刻時刻 và 臨時時刻 có giống
					// nhau không)
					// 臨時勤務管理 chưa có (fixed) true (đồng nhất thời gian) false
					// (k đồng nhất)
					boolean equal = true;
					// 8*
					if (equal) {
						// tiếp
						// 打刻を出退勤．出勤．実打刻に入れる (Set 打刻 vào 出退勤．出勤．実打刻)
						timeLeavingWork = setStampInActualStampOfTimeLeave(x, processTimeOutput, timeLeavingWork);
						newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
						isBreak = true;
						break;
					}

				} else {
					if (timeLeavingWork.getLeaveStamp()==null||timeLeavingWork.getLeaveStamp().getStamp() == null || (timeLeavingWork.getLeaveStamp()!=null&&timeLeavingWork.getLeaveStamp().getStamp() != null &&processTimeOutput.getTimeOfDay()
							.v() <= timeLeavingWork.getLeaveStamp().getStamp().getTimeWithDay().v())) {
						// 打刻を出退勤．出勤（実打刻と打刻）に入れる (Set 打刻 vào 出退勤．出勤（実打刻と打刻))
						putDataTimeLeaveForActualAndStamp(x, processTimeOutput, timeLeavingWork);
						newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
						isBreak = true;
						break;
					}
				}
			}
			if(!isBreak){
				newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
			}
			if (newTimeLeavingWorks ==null || newTimeLeavingWorks.isEmpty()) {
				this.lstTemporaryTimeOfDailyPerformance.add(new TemporaryTimeOfDailyPerformance(employeeId, temporaryTimeOfDailyPerformance.getWorkTimes(), timeLeavingWorks, date));
			}else{
				this.lstTemporaryTimeOfDailyPerformance.add(new TemporaryTimeOfDailyPerformance(employeeId, temporaryTimeOfDailyPerformance.getWorkTimes(), newTimeLeavingWorks, date));
			}
			
			
		}
	}

	// 打刻を出退勤．出勤（実打刻と打刻）に入れる (Set 打刻 vào 出退勤．出勤（実打刻と打刻))
	private TimeLeavingWork putDataTimeLeaveForActualAndStamp(StampItem x, ProcessTimeOutput processTimeOutput,
			TimeLeavingWork timeLeavingWork) {

		// fixed 丸め設定 (InstantRounding )
		// ,
		// (FontRearSection) 前後区分 = 後 ,
		// (RoundingTimeUnit) 時刻丸め単位 =
		// 1;

		TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
		FontRearSection fontRearSection = FontRearSection.AFTER;
		RoundingTimeUnit roundTimeUnit = RoundingTimeUnit.ONE;
		int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, fontRearSection, roundTimeUnit);
		processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
		// 7.2.1
		WorkStamp actualStamp =null;
		/*
		WorkStamp actualStamp = timeLeavingWork.getAttendanceStamp().getActualStamp();
		actualStamp.setAfterRoundingTime(processTimeOutput.getTimeAfter());
		actualStamp.setTimeWithDay(processTimeOutput.getTimeOfDay());
		actualStamp.setLocationCode(x.getWorkLocationCd());
		*/

		switch (x.getStampMethod().value) {
		// タイムレコーダー → タイムレコーダー
		case 0:
			actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
			break;
		// Web → Web打刻入力
		case 1:
			actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.WEB_STAMP_INPUT);
			break;
		// ID入力 → タイムレコーダ(ID入力)
		case 2:
			actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
			break;
		// 磁気カード → タイムレコーダ(磁気カード)
		case 3:
			actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
			break;
		// ICカード → タイムレコーダ(ICカード)
		case 4:
			actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
			break;
		// 指紋 → タイムレコーダ(指紋打刻)
		case 5:
			actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
			break;
		// その他 no cover
		default:
			actualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
			break;
		}

		// 反映済み区分 ← true stamp
		StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
				x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
				x.getGoOutReason(), x.getDate(), x.getPersonId(), ReflectedAtr.REFLECTED);
		lstStamp.add(stampItem);
		
		return new TimeLeavingWork(timeLeavingWork.getWorkNo(), new TimeActualStamp(actualStamp, actualStamp, timeLeavingWork.getAttendanceStamp().getNumberOfReflectionStamp() + 1), timeLeavingWork.getLeaveStamp());
	}

	// 打刻を出退勤．出勤．実打刻に入れる (Set 打刻 vào 出退勤．出勤．実打刻)
	private TimeLeavingWork setStampInActualStampOfTimeLeave(StampItem x, ProcessTimeOutput processTimeOutput,
			TimeLeavingWork timeLeavingWork) {
		WorkStamp actualStamp = timeLeavingWork.getAttendanceStamp().getActualStamp();
		if (actualStamp == null) {
			// (fixed) lam tron
			TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
			FontRearSection fontRearSection = FontRearSection.AFTER;
			RoundingTimeUnit roundTimeUnit = RoundingTimeUnit.ONE;
			int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, fontRearSection, roundTimeUnit);
			processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
			WorkStamp newActualStamp =null;
			/*
			WorkStamp ac = new WorkStamp();
			ac.setAfterRoundingTime(processTimeOutput.getTimeAfter());
			ac.setTimeWithDay(processTimeOutput.getTimeOfDay());
			ac.setLocationCode(x.getWorkLocationCd());
			*/

			switch (x.getStampMethod().value) {
			// タイムレコーダー → タイムレコーダー
			case 0:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
				break;
			// Web → Web打刻入力
			case 1:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.WEB_STAMP_INPUT);
				break;
			// ID入力 → タイムレコーダ(ID入力)
			case 2:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
				break;
			// 磁気カード → タイムレコーダ(磁気カード)
			case 3:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
				break;
			// ICカード → タイムレコーダ(ICカード)
			case 4:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
				break;
			// 指紋 → タイムレコーダ(指紋打刻)
			case 5:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
				break;
			// その他 no cover
			default:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
				break;
			}
			return new TimeLeavingWork(timeLeavingWork.getWorkNo(), new TimeActualStamp(newActualStamp, timeLeavingWork.getAttendanceStamp().getStamp(), timeLeavingWork.getAttendanceStamp().getNumberOfReflectionStamp()), timeLeavingWork.getLeaveStamp());
		} 
			// 反映済み区分 = true
			StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
					x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
					x.getGoOutReason(), x.getDate(), x.getPersonId(), ReflectedAtr.REFLECTED);
			lstStamp.add(stampItem);
			return new TimeLeavingWork(timeLeavingWork.getWorkNo(), new TimeActualStamp(actualStamp, timeLeavingWork.getAttendanceStamp().getStamp(), timeLeavingWork.getAttendanceStamp().getNumberOfReflectionStamp() + 1), timeLeavingWork.getLeaveStamp());
		
	}

	// *8 THời điểm check quay về
	private void reflectTimeComeBackCheck(GeneralDate date, String employeeId, StampItem x,
			ProcessTimeOutput processTimeOutput) {
		Optional<OutingTimeOfDailyPerformance> outDailyOptional = this.OutRepo.findByEmployeeIdAndDate(employeeId,
				date);
		//

		if (outDailyOptional.isPresent()) {
			OutingTimeOfDailyPerformance outDailyPer = outDailyOptional.get();
			List<OutingTimeSheet> lstOutingTimeSheet = outDailyPer.getOutingTimeSheets();
			Collections.sort(lstOutingTimeSheet, new Comparator<OutingTimeSheet>() {
				public int compare(OutingTimeSheet o1, OutingTimeSheet o2) {
					int t1 = o1.getGoOut().getStamp().getTimeWithDay().v().intValue();
					int t2 = o2.getGoOut().getStamp().getTimeWithDay().v().intValue();
					if (t1 == t2)
						return 0;
					return t1 < t2 ? -1 : 1;
				}
			});

			// *7.1 外出時間帯Listに最大枠数分の枠を用意する (Chuẩn bị )
			// Xác nhận 最大使用回数 (最大使用回数 lấy từ 打刻反映管理 .外出管理 )
			String companyId = AppContexts.user().companyId();
			Optional<StampReflectionManagement> stampOptional = this.stampRepo.findByCid(companyId);
			if (stampOptional.isPresent()) {
				StampReflectionManagement stampReflectionManagement = stampOptional.get();
				// stampReflectionManagement sẽ gọi .外出管理.最大使用回数
				// (outingManager)
				// fixed outingManager =11
				int outingManager = 11;
				// thiếu điều kiện giữa outingManager và
				// lstOutingTimeSheet.size();
				int outingTimeSize = lstOutingTimeSheet.size();
				if (outingTimeSize < outingManager) {
					for (int i = 0; i < outingManager - outingTimeSize; i++) {
						lstOutingTimeSheet.add(new OutingTimeSheet(null, null, null, null, null, null));
					}
				}
				// *7.1
				int lstOutingTimeSheetSize = lstOutingTimeSheet.size();
				List<OutingTimeSheet> newOutingTimeSheets =null;
				boolean isBreak = false;
				for (int i = 0; i < lstOutingTimeSheetSize; i++) {
					OutingTimeSheet o = lstOutingTimeSheet.get(i);
					WorkStamp stamp = o.getComeBack().getStamp();
					if (o.getComeBack() !=null && stamp != null && stamp.getStampSourceInfo().value != 17) {
						if (stamp.getTimeWithDay().v() == processTimeOutput.getTimeOfDay().v()) {
							 o = putInDataComeBack(x, processTimeOutput, o); // ok
							newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
							isBreak = true;
							break;
						}
					} else {
						// 次の枠の時間帯．外出．打刻を確認する
						if ((o.getGoOut()==null || o.getGoOut().getStamp() == null)|| (o.getGoOut()!=null && o.getGoOut().getStamp() != null&& o.getGoOut().getStamp().getTimeWithDay()
								.v() <= processTimeOutput.getTimeOfDay().v()) ) {

							if (i + 1 == lstOutingTimeSheetSize || lstOutingTimeSheet.get(i + 1) ==null|| lstOutingTimeSheet.get(i + 1).getGoOut()==null
									|| lstOutingTimeSheet.get(i + 1).getGoOut().getStamp() == null
									|| (i + 1 < lstOutingTimeSheetSize
											&& lstOutingTimeSheet.get(i + 1) !=null && lstOutingTimeSheet.get(i + 1).getGoOut()!=null && lstOutingTimeSheet.get(i + 1).getGoOut().getStamp() != null && processTimeOutput.getTimeOfDay().v() < lstOutingTimeSheet.get(i + 1).getGoOut()
											.getStamp().getTimeWithDay().v())) {
								o = putDataComeBackForActualAndStamp(x, processTimeOutput, o);
								newOutingTimeSheets =	revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
								isBreak = true;
								break;

							}
						}
						
					}
				}
				if (!isBreak) {
					newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
				}
				if(newOutingTimeSheets==null ||newOutingTimeSheets.isEmpty()){
					this.lstOutingTimeOfDailyPerformance
					.add(new OutingTimeOfDailyPerformance(employeeId, lstOutingTimeSheet, date));
				}else{
					this.lstOutingTimeOfDailyPerformance
					.add(new OutingTimeOfDailyPerformance(employeeId, newOutingTimeSheets, date));
				}
				
			}
		
			
		}
	}

	// *7 外出打刻を反映する (Phản ánh 外出打刻 (thời diểm check ra ngoài))
	private void reflectTimeGoOutCheck(GeneralDate date, String employeeId, StampItem x,
			ProcessTimeOutput processTimeOutput) {
		Optional<OutingTimeOfDailyPerformance> outDailyOptional = this.OutRepo.findByEmployeeIdAndDate(employeeId,
				date);
		//

		if (outDailyOptional.isPresent()) {
			OutingTimeOfDailyPerformance outDailyPer = outDailyOptional.get();
			List<OutingTimeSheet> lstOutingTimeSheet = outDailyPer.getOutingTimeSheets();
			Collections.sort(lstOutingTimeSheet, new Comparator<OutingTimeSheet>() {
				public int compare(OutingTimeSheet o1, OutingTimeSheet o2) {
					int t1 = o1.getGoOut().getStamp().getTimeWithDay().v().intValue();
					int t2 = o2.getGoOut().getStamp().getTimeWithDay().v().intValue();
					if (t1 == t2)
						return 0;
					return t1 < t2 ? -1 : 1;
				}
			});

			// *7.1 外出時間帯Listに最大枠数分の枠を用意する (Chuẩn bị )
			// Xác nhận 最大使用回数 (最大使用回数 lấy từ 打刻反映管理 .外出管理 )
			String companyId = AppContexts.user().companyId();
			Optional<StampReflectionManagement> stampOptional = this.stampRepo.findByCid(companyId);
			if (stampOptional.isPresent()) {
				StampReflectionManagement stampReflectionManagement = stampOptional.get();
				// stampReflectionManagement sẽ gọi .外出管理.最大使用回数
				// (outingManager)
				// fixed outingManager =11
				int outingManager = 11;
				// thiếu điều kiện giữa outingManager và
				// lstOutingTimeSheet.size();
				int outingTimeSize = lstOutingTimeSheet.size();
				if (outingTimeSize < outingManager) {
					for (int i = 0; i < outingManager - outingTimeSize; i++) {
						lstOutingTimeSheet.add(new OutingTimeSheet(null, null, null, null, null, null));
					}
				}
				// *7.1
				int lstOutingTimeSheetSize = lstOutingTimeSheet.size();
				List<OutingTimeSheet> newOutingTimeSheets =null;
				boolean isBreak = false;
				for (int i = 0; i < lstOutingTimeSheetSize; i++) {
					OutingTimeSheet o = lstOutingTimeSheet.get(i);
					WorkStamp stamp = o.getGoOut().getStamp();
					if (o.getGoOut()!=null && stamp != null && stamp.getStampSourceInfo().value != 17) {
						if (stamp.getTimeWithDay().v() == processTimeOutput.getTimeOfDay().v()) {
							// 打刻を時間帯．外出．実打刻に入れる (put vào 打刻を時間帯．外出．実打刻)
							o = putInDataActualStamp(x, processTimeOutput, o);
							// 7.1.2 Xóa những cái trống trong list
							newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
							isBreak = true;
							break;
							// 7.1.2
						}
					} else {
						if (o.getComeBack()==null || o.getComeBack().getStamp() == null) {
							// 7.2* 打刻を時間帯．外出（実打刻と打刻）に入れる
							// (put
							// 打刻を時間帯．外出)
							OutingTimeSheet newOutingTimeSheet = putDataInGoOut(x, processTimeOutput, o);
							lstOutingTimeSheet.remove(o);
							lstOutingTimeSheet.add(newOutingTimeSheet);
							newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
							isBreak = true;
							break;
							// 7.2*
						} else {
							if ( o.getComeBack().getStamp().getTimeWithDay().v() <= processTimeOutput.getTimeOfDay()
									.v()) {
								OutingTimeSheet newOutingTimeSheet = putDataInGoOut(x, processTimeOutput, o);
								lstOutingTimeSheet.remove(o);
								lstOutingTimeSheet.add(newOutingTimeSheet);
								newOutingTimeSheets = 	revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
								isBreak = true;
								break;
							}
						}
					}
				}
				if (!isBreak) {
					newOutingTimeSheets = 	revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
				}
				if(newOutingTimeSheets==null || newOutingTimeSheets.isEmpty()){
					this.lstOutingTimeOfDailyPerformance
					.add(new OutingTimeOfDailyPerformance(employeeId, lstOutingTimeSheet, date));
				}else{
					this.lstOutingTimeOfDailyPerformance
					.add(new OutingTimeOfDailyPerformance(employeeId, newOutingTimeSheets, date));
				}

				
			}
		

		}
	}

	private List<TimeLeavingWork>  revomeEmptyTimeLeaves(List<TimeLeavingWork> timeLeavingWorks) {
		List<TimeLeavingWork> newTimeLeavingWorks = new ArrayList<TimeLeavingWork>();
		int lstOutingTimeSize = timeLeavingWorks.size();
		for (int j = 0; j < lstOutingTimeSize; j++) {
			TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(j);
			if (timeLeavingWork.getAttendanceStamp() == null && timeLeavingWork.getLeaveStamp() == null) {
				timeLeavingWorks.remove(timeLeavingWork);
			}
		}
		Collections.sort(timeLeavingWorks, new Comparator<TimeLeavingWork>() {
			public int compare(TimeLeavingWork o1, TimeLeavingWork o2) {
				int t1 = o1.getAttendanceStamp().getStamp().getTimeWithDay().v().intValue();
				int t2 = o2.getAttendanceStamp().getStamp().getTimeWithDay().v().intValue();
				if (t1 == t2)
					return 0;
				return t1 < t2 ? -1 : 1;
			}
		});
		int timeLeavingWorksSize = timeLeavingWorks.size();
		for (int j = 0; j < timeLeavingWorksSize; j++) {
			TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(j);
			if (j < 3) {
				newTimeLeavingWorks.add(new TimeLeavingWork(new WorkNo(new BigDecimal(j + 1)), timeLeavingWork.getAttendanceStamp(), timeLeavingWork.getLeaveStamp()));
				
			} else {
				newTimeLeavingWorks.add(new TimeLeavingWork(new WorkNo(new BigDecimal(j%3+1)), timeLeavingWork.getAttendanceStamp(), timeLeavingWork.getLeaveStamp()));
			}
		}
		return newTimeLeavingWorks;
	}

	private List<OutingTimeSheet>  revomeEmptyOutingTimeSheets(List<OutingTimeSheet> lstOutingTimeSheet) {
		int lstOutingTimeSize = lstOutingTimeSheet.size();
		List<OutingTimeSheet> newOutingTimeSheets = new ArrayList<OutingTimeSheet>();
		for (int j = 0; j < lstOutingTimeSize; j++) {
			OutingTimeSheet outingTimeSheet = lstOutingTimeSheet.get(j);
			if (outingTimeSheet.getGoOut() == null && outingTimeSheet.getComeBack() == null) {
				lstOutingTimeSheet.remove(outingTimeSheet);
			}
		}
		Collections.sort(lstOutingTimeSheet, new Comparator<OutingTimeSheet>() {
			public int compare(OutingTimeSheet o1, OutingTimeSheet o2) {
				int t1 = o1.getGoOut().getStamp().getTimeWithDay().v().intValue();
				int t2 = o2.getGoOut().getStamp().getTimeWithDay().v().intValue();
				if (t1 == t2)
					return 0;
				return t1 < t2 ? -1 : 1;
			}
		});
		int lstOutingTimeNewSize = lstOutingTimeSheet.size();
		for (int j = 0; j < lstOutingTimeNewSize; j++) {
			OutingTimeSheet outingTimeSheet = lstOutingTimeSheet.get(j);
			if (j < 10) {
				OutingFrameNo outingFrameNo = new OutingFrameNo(new BigDecimal(j + 1));
				newOutingTimeSheets.add(new OutingTimeSheet(outingFrameNo, outingTimeSheet.getGoOut(), outingTimeSheet.getOutingTimeCalculation(), outingTimeSheet.getOutingTime(), outingTimeSheet.getReasonForGoOut(), outingTimeSheet.getComeBack()));
			} else {
				OutingFrameNo outingFrameNo = new OutingFrameNo(new BigDecimal(j %10+1));
				newOutingTimeSheets.add(new OutingTimeSheet(outingFrameNo, outingTimeSheet.getGoOut(), outingTimeSheet.getOutingTimeCalculation(), outingTimeSheet.getOutingTime(), outingTimeSheet.getReasonForGoOut(), outingTimeSheet.getComeBack()));
			}
		}
		return newOutingTimeSheets;
	}

	private OutingTimeSheet putInDataActualStamp(StampItem x, ProcessTimeOutput processTimeOutput, OutingTimeSheet o) {
		
		WorkStamp actualStamp = o.getGoOut().getActualStamp();
		if (actualStamp == null) {
			TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
			FontRearSection fontRearSection = FontRearSection.AFTER;
			RoundingTimeUnit roundTimeUnit = RoundingTimeUnit.ONE;
			int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, fontRearSection, roundTimeUnit);
			processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));

			WorkStamp newActualStamp = null;
			/*
			ac.setAfterRoundingTime(processTimeOutput.getTimeAfter());
			ac.setTimeWithDay(processTimeOutput.getTimeOfDay());
			ac.setLocationCode(x.getWorkLocationCd());
			*/

			switch (x.getStampMethod().value) {
			// タイムレコーダー → タイムレコーダー
			case 0:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
				break;
			// Web → Web打刻入力
			case 1:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.WEB_STAMP_INPUT);
				break;
			// ID入力 → タイムレコーダ(ID入力)
			case 2:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
				break;
			// 磁気カード → タイムレコーダ(磁気カード)
			case 3:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
				break;
			// ICカード → タイムレコーダ(ICカード)
			case 4:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
				break;
			// 指紋 → タイムレコーダ(指紋打刻)
			case 5:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
				break;
			// その他 no cover
			default:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
				break;
			}
			return new OutingTimeSheet(o.getOutingFrameNo(), new TimeActualStamp(actualStamp, o.getGoOut().getStamp(), o.getGoOut().getNumberOfReflectionStamp()), o.getOutingTimeCalculation(), o.getOutingTime(), EnumAdaptor.valueOf(x.getGoOutReason().value, GoingOutReason.class), o.getComeBack());

		} 
			// 反映済み区分 = true
			StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
					x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
					x.getGoOutReason(), x.getDate(), x.getPersonId(), ReflectedAtr.REFLECTED);
			lstStamp.add(stampItem);
		return new OutingTimeSheet(o.getOutingFrameNo(), new TimeActualStamp(o.getGoOut().getActualStamp(), o.getGoOut().getStamp(), o.getGoOut().getNumberOfReflectionStamp() + 1), o.getOutingTimeCalculation(), o.getOutingTime(), EnumAdaptor.valueOf(x.getGoOutReason().value, GoingOutReason.class), o.getComeBack());
		
	}

	// 打刻を時間帯．戻り．実打刻に入れる
	private OutingTimeSheet putInDataComeBack(StampItem x, ProcessTimeOutput processTimeOutput, OutingTimeSheet o) {
		WorkStamp actualStamp = o.getComeBack().getActualStamp();
		if (actualStamp == null) {
			TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
			FontRearSection fontRearSection = FontRearSection.AFTER;
			RoundingTimeUnit roundTimeUnit = RoundingTimeUnit.ONE;
			int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, fontRearSection, roundTimeUnit);
			processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));

			WorkStamp newActualStamp = null;
			/*
			ac.setAfterRoundingTime(processTimeOutput.getTimeAfter());
			ac.setTimeWithDay(processTimeOutput.getTimeOfDay());
			ac.setLocationCode(x.getWorkLocationCd());
			*/

			switch (x.getStampMethod().value) {
			// タイムレコーダー → タイムレコーダー
			case 0:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
				break;
			// Web → Web打刻入力
			case 1:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.WEB_STAMP_INPUT);
				break;
			// ID入力 → タイムレコーダ(ID入力)
			case 2:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
				break;
			// 磁気カード → タイムレコーダ(磁気カード)
			case 3:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
				break;
			// ICカード → タイムレコーダ(ICカード)
			case 4:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
				break;
			// 指紋 → タイムレコーダ(指紋打刻)
			case 5:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
				break;
			// その他 no cover
			default:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
				break;
			}
			
			return new OutingTimeSheet(o.getOutingFrameNo(), o.getGoOut(), o.getOutingTimeCalculation(), o.getOutingTime(), EnumAdaptor.valueOf(x.getGoOutReason().value, GoingOutReason.class), new TimeActualStamp(newActualStamp, o.getComeBack().getStamp(), o.getComeBack().getNumberOfReflectionStamp()));
			

		} 
		
			// 反映済み区分 = true
			StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
					x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
					x.getGoOutReason(), x.getDate(), x.getPersonId(), ReflectedAtr.REFLECTED);
			lstStamp.add(stampItem);
		
		return new OutingTimeSheet(o.getOutingFrameNo(), o.getGoOut(), o.getOutingTimeCalculation(), o.getOutingTime(), EnumAdaptor.valueOf(x.getGoOutReason().value, GoingOutReason.class), new TimeActualStamp(o.getComeBack().getActualStamp(), o.getComeBack().getStamp(), o.getComeBack().getNumberOfReflectionStamp()+1));
	}

	// 打刻を時間帯．戻り（実打刻と打刻）に入れる
	private OutingTimeSheet  putDataComeBackForActualAndStamp(StampItem x, ProcessTimeOutput processTimeOutput, OutingTimeSheet o) {
		// 7.2.1* Làm tròn 打刻時刻 đang xử
		// lý (chưa xử
		// lý)
		// fixed 丸め設定 (InstantRounding )
		// ,
		// (FontRearSection) 前後区分 = 後 ,
		// (RoundingTimeUnit) 時刻丸め単位 =
		// 1;

		TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
		FontRearSection fontRearSection = FontRearSection.AFTER;
		RoundingTimeUnit roundTimeUnit = RoundingTimeUnit.ONE;
		int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, fontRearSection, roundTimeUnit);
		processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
		// 7.2.1
		WorkStamp newActualStamp = null;
		/*
		actualStamp.setAfterRoundingTime(processTimeOutput.getTimeAfter());
		actualStamp.setTimeWithDay(processTimeOutput.getTimeOfDay());
		actualStamp.setLocationCode(x.getWorkLocationCd());
		*/

		switch (x.getStampMethod().value) {
		// タイムレコーダー → タイムレコーダー
		case 0:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
			break;
		// Web → Web打刻入力
		case 1:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.WEB_STAMP_INPUT);
			break;
		// ID入力 → タイムレコーダ(ID入力)
		case 2:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
			break;
		// 磁気カード → タイムレコーダ(磁気カード)
		case 3:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
			break;
		// ICカード → タイムレコーダ(ICカード)
		case 4:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
			break;
		// 指紋 → タイムレコーダ(指紋打刻)
		case 5:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
			break;
		// その他 no cover
		default:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
			break;
		}

		// 反映済み区分 ← true stamp
		StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
				x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
				x.getGoOutReason(), x.getDate(), x.getPersonId(), ReflectedAtr.REFLECTED);
		lstStamp.add(stampItem);
		
		return new OutingTimeSheet(o.getOutingFrameNo(), o.getGoOut(), o.getOutingTimeCalculation(), o.getOutingTime(), EnumAdaptor.valueOf(x.getGoOutReason().value, GoingOutReason.class), new TimeActualStamp(newActualStamp, newActualStamp, o.getComeBack().getNumberOfReflectionStamp() + 1));
	}

	private OutingTimeSheet putDataInGoOut(StampItem x, ProcessTimeOutput processTimeOutput, OutingTimeSheet o) {
		// 7.2.1* Làm tròn 打刻時刻 đang xử
		// lý (chưa xử
		// lý)
		// fixed 丸め設定 (InstantRounding )
		// ,
		// (FontRearSection) 前後区分 = 後 ,
		// (RoundingTimeUnit) 時刻丸め単位 =
		// 1;

		TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
		FontRearSection fontRearSection = FontRearSection.AFTER;
		RoundingTimeUnit roundTimeUnit = RoundingTimeUnit.ONE;
		int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, fontRearSection, roundTimeUnit);
		processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
		// 7.2.1
		WorkStamp newActualStamp = null;
	

		switch (x.getStampMethod().value) {
		// タイムレコーダー → タイムレコーダー
		case 0:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
			break;
		// Web → Web打刻入力
		case 1:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.WEB_STAMP_INPUT);
			break;
		// ID入力 → タイムレコーダ(ID入力)
		case 2:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
			break;
		// 磁気カード → タイムレコーダ(磁気カード)
		case 3:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
			break;
		// ICカード → タイムレコーダ(ICカード)
		case 4:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
			break;
		// 指紋 → タイムレコーダ(指紋打刻)
		case 5:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
			break;
		// その他 no cover
		default:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(), x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER);
			break;
		}

		// 反映済み区分 ← true stamp
		StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
				x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
				x.getGoOutReason(), x.getDate(), x.getPersonId(), ReflectedAtr.REFLECTED);
		lstStamp.add(stampItem);
		
		return new OutingTimeSheet(o.getOutingFrameNo(), new TimeActualStamp(newActualStamp, newActualStamp, o.getGoOut().getNumberOfReflectionStamp() + 1), o.getOutingTimeCalculation(), o.getOutingTime(), EnumAdaptor.valueOf(x.getGoOutReason().value, GoingOutReason.class), o.getComeBack());
		
	}

	private int roudingTimeWithDay(TimeWithDayAttr timeOfDay, FontRearSection fontRearSection,
			RoundingTimeUnit roundTimeUnit) {
		InstantRounding instantRounding = new InstantRounding(fontRearSection, roundTimeUnit);
		int numberMinuteTimeOfDay = timeOfDay.v();
		int timeOfDayMinute = timeOfDay.minute();
		int roundingTimeUnit = instantRounding.getRoundingTimeUnit().value;
		// FontRearSection.BEFOR
		int numberMinuteTimeOfDayRounding = 0;
		int modTimeOfDay = timeOfDayMinute % roundingTimeUnit;
		if (instantRounding.getFontRearSection().value == 0) {

			numberMinuteTimeOfDayRounding = modTimeOfDay == 0 ? numberMinuteTimeOfDay
					: numberMinuteTimeOfDay - modTimeOfDay;
		} else {
			numberMinuteTimeOfDayRounding = modTimeOfDay == 0 ? numberMinuteTimeOfDay
					: numberMinuteTimeOfDay - modTimeOfDay + roundingTimeUnit;
		}
		return numberMinuteTimeOfDayRounding;
	}

	// 打刻を反映するか確認する (Xác nhận ) true reflect false no reflect
	private boolean confirmReflectStamp(StampReflectRangeOutput s, StampItem x, ProcessTimeOutput processTimeOutput) {
		AttendanceTime attendanceTime = x.getAttendanceTime();
		processTimeOutput.setTimeOfDay(new TimeWithDayAttr(attendanceTime.v()));
		// 外出
		TimeZoneOutput goOut = s.getGoOut();
		if (goOut.getStart().v() <= processTimeOutput.getTimeOfDay().v()
				&& goOut.getEnd().v() >= processTimeOutput.getTimeOfDay().v()) {
			return true;
		}
		return false;
	}

	private String confirmReflectRangeLeavingTime(StampItem stampItem, StampReflectRangeOutput s) {
		AttendanceTime attendanceTime = stampItem.getAttendanceTime();
		List<StampReflectTimezoneOutput> lstStampReflectTimezone = s.getLstStampReflectTimezone();
		int n = lstStampReflectTimezone.size();
		for (int i = 0; i < n; i++) {
			// 打刻．勤務時刻 is 打刻.時刻 ?
			StampReflectTimezoneOutput stampReflectTimezone = lstStampReflectTimezone.get(i);
			if (stampReflectTimezone.getStartTime().v() <= attendanceTime.v()
					&& stampReflectTimezone.getEndTime().v() >= attendanceTime.v()
					&& stampReflectTimezone.getClassification().value == 1) {
				if (stampReflectTimezone.getWorkNo().v().intValue() == 1) {
					return "range1";
				}
				if (stampReflectTimezone.getWorkNo().v().intValue() == 2) {
					return "range2";
				}
			}
		}
		return "outrange";
	}

	private void reflectActualTimeOrAttendence(WorkInfoOfDailyPerformance WorkInfo,
			TimeLeavingOfDailyPerformance timeDailyPer, GeneralDate date, String employeeId, StampItem x,
			String attendanceClass, String actualStampClass, int worktNo) {
		TimePrintDestinationOutput timePrintDestinationOutput = new TimePrintDestinationOutput();
		// getReflecDestination Lấy dữ liệu 反映先
		
		// confirm? timeDailyPer có cần phải update k hiện tại actualStamp đang khởi tạo đối tượng mới
		WorkStamp actualStamp = getWorkStamp(timeDailyPer, worktNo, attendanceClass, actualStampClass);
		timePrintDestinationOutput.setLocationCode(actualStamp.getLocationCode());
		timePrintDestinationOutput.setStampSourceInfo(actualStamp.getStampSourceInfo());
		timePrintDestinationOutput.setTimeOfDay(actualStamp.getTimeWithDay());
		// 組み合わせ区分 != 直行 or != 直帰
		if (x.getStampCombinationAtr().value != 6 && x.getStampCombinationAtr().value != 7) {

			// 1* // Phán đoán điều kiện phản ảnh 出退勤 của 通常打刻
			boolean checkReflectNormal = checkReflectNormal(x, timePrintDestinationOutput, date, employeeId);
			// 1*
			if (checkReflectNormal) {
				// 2* check tay ngày nghỉ) worktype thay đổi
				boolean checkHolidayChange = checkHolidayChange(WorkInfo);
				// 2*
				if (checkHolidayChange) {
					// Phản ánh 時刻
					AttendanceTime attendanceTime = x.getAttendanceTime();
					WorkLocationCD workLocationCd = x.getWorkLocationCd();
					StampMethod stampMethod = x.getStampMethod();

					TimePrintDestinationOutput timePrintDestinationCopy = new TimePrintDestinationOutput();
					timePrintDestinationCopy.setTimeOfDay(new TimeWithDayAttr(attendanceTime.v()));
					timePrintDestinationCopy.setLocationCode(workLocationCd);
					switch (stampMethod.value) {
					// タイムレコーダー → タイムレコーダー
					case 0:
						timePrintDestinationCopy.setStampSourceInfo(StampSourceInfo.TIME_RECORDER);
						break;
					// Web → Web打刻入力
					case 1:
						timePrintDestinationCopy.setStampSourceInfo(StampSourceInfo.WEB_STAMP_INPUT);
						break;
					// ID入力 → タイムレコーダ(ID入力)
					case 2:
						timePrintDestinationCopy.setStampSourceInfo(StampSourceInfo.TIME_RECORDER_ID_INPUT);
						break;
					// 磁気カード → タイムレコーダ(磁気カード)
					case 3:
						timePrintDestinationCopy.setStampSourceInfo(StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
						break;
					// ICカード → タイムレコーダ(ICカード)
					case 4:
						timePrintDestinationCopy.setStampSourceInfo(StampSourceInfo.TIME_RECORDER_Ic_CARD);
						break;
					// 指紋 → タイムレコーダ(指紋打刻)
					case 5:
						timePrintDestinationCopy.setStampSourceInfo(StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
						break;
					// その他 no cover
					default:
						timePrintDestinationCopy.setStampSourceInfo(StampSourceInfo.TIME_RECORDER);
						break;
					}

					// Copy tới 勤怠打刻 từ 打刻反映先
					actualStamp = 	new WorkStamp(actualStamp.getAfterRoundingTime(), timePrintDestinationCopy.getTimeOfDay(), timePrintDestinationCopy.getLocationCode(), timePrintDestinationCopy.getStampSourceInfo());
					/*
					actualStamp.setLocationCode(timePrintDestinationCopy.getLocationCode());
					actualStamp.setStampSourceInfo(timePrintDestinationCopy.getStampSourceInfo());
					actualStamp.setTimeWithDay(timePrintDestinationCopy.getTimeOfDay());
					*/
					// lấy workstamp cần confirm ?
					// 5* làm tròn 打刻
					// timePrintDestinationCopy
					if ("打刻".equals(actualStampClass)) {
						// fixed 丸め設定 (InstantRounding ) ,
						// (FontRearSection) 前後区分 = 後 ,
						// (RoundingTimeUnit) 時刻丸め単位 = 1;
						InstantRounding instantRounding = new InstantRounding(FontRearSection.AFTER,
								RoundingTimeUnit.ONE);
						TimeWithDayAttr timeOfDay = actualStamp.getTimeWithDay();
						int numberMinuteTimeOfDay = actualStamp.getTimeWithDay().v();
						int timeOfDayMinute = timeOfDay.minute();
						int roundingTimeUnit = instantRounding.getRoundingTimeUnit().value;
						// FontRearSection.BEFOR
						int numberMinuteTimeOfDayRounding = 0;
						int modTimeOfDay = timeOfDayMinute % roundingTimeUnit;
						if (instantRounding.getFontRearSection().value == 0) {

							numberMinuteTimeOfDayRounding = modTimeOfDay == 0 ? numberMinuteTimeOfDay
									: numberMinuteTimeOfDay - modTimeOfDay;
						} else {
							numberMinuteTimeOfDayRounding = modTimeOfDay == 0 ? numberMinuteTimeOfDay
									: numberMinuteTimeOfDay - modTimeOfDay + roundingTimeUnit;
						}
						//actualStamp.setAfterRoundingTime(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
						actualStamp = 	new WorkStamp(new TimeWithDayAttr(numberMinuteTimeOfDayRounding), timePrintDestinationCopy.getTimeOfDay(), timePrintDestinationCopy.getLocationCode(), timePrintDestinationCopy.getStampSourceInfo());

					} else {
						// return
					}

					// 5*
					// gan 反映済み区分 = true, trường này chưa có trong StampItem
					StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(),
							x.getStampCombinationAtr(), x.getSiftCd(), x.getStampMethod(), x.getStampAtr(),
							x.getWorkLocationCd(), x.getWorkLocationName(), x.getGoOutReason(), x.getDate(),
							x.getPersonId(), ReflectedAtr.REFLECTED);
					lstStamp.add(stampItem);

				}

			}
			// 6* Update số lần phản ánh 打刻
			if ("実打刻".equals(actualStampClass)) {
				TimeActualStamp timeActualStamp = this.getTimeActualStamp(timeDailyPer, worktNo, attendanceClass,
						actualStampClass);
				//timeActualStamp.setNumberOfReflectionStamp(timeActualStamp.getNumberOfReflectionStamp() + 1);
				timeActualStamp = new TimeActualStamp(timeActualStamp.getActualStamp(), timeActualStamp.getStamp(), timeActualStamp.getNumberOfReflectionStamp() + 1);
				// tăng lên 1 nhưng lưu vào đâu, cần confirm ?
			}
			// 6*

		}
	}

	/*
	 * private WorkStamp getWorkStamp(GeneralDate date, String employeeId, int
	 * worktNo, String attendanceClass, String actualStampClass) { //8*
	 * Optional<TimeLeavingOfDailyPerformance> timeOptional =
	 * this.timeRepo.findByKey(employeeId, date);
	 * 
	 * //8* lấy từ a nam if (timeOptional.isPresent()) {
	 * TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance =
	 * timeOptional.get(); List<TimeLeavingWork> lstTimeLeavingWork =
	 * timeLeavingOfDailyPerformance.getTimeLeavingWorks(); int n =
	 * lstTimeLeavingWork.size(); for (int i = 0; i < n; i++) { TimeLeavingWork
	 * timeLeavingWork = lstTimeLeavingWork.get(i); if
	 * (timeLeavingWork.getWorkNo().v().intValue() == worktNo) { // 出勤
	 * TimeActualStamp attendanceStamp = null; if ("出勤".equals(attendanceClass))
	 * { attendanceStamp = timeLeavingWork.getAttendanceStamp(); } else {
	 * attendanceStamp = timeLeavingWork.getLeaveStamp(); } // 実打刻 if
	 * ("実打刻".equals(actualStampClass)) { return
	 * attendanceStamp.getActualStamp(); } return attendanceStamp.getStamp(); }
	 * }
	 * 
	 * } return null; }
	 */
	private WorkStamp getWorkStamp(TimeLeavingOfDailyPerformance timeDailyPer, int worktNo, String attendanceClass,
			String actualStampClass) {

		if (timeDailyPer != null) {
			List<TimeLeavingWork> lstTimeLeavingWork = timeDailyPer.getTimeLeavingWorks();
			int n = lstTimeLeavingWork.size();
			for (int i = 0; i < n; i++) {
				TimeLeavingWork timeLeavingWork = lstTimeLeavingWork.get(i);
				if (timeLeavingWork.getWorkNo().v().intValue() == worktNo) {
					// 出勤
					TimeActualStamp attendanceStamp = null;
					if ("出勤".equals(attendanceClass)) {
						attendanceStamp = timeLeavingWork.getAttendanceStamp();
					} else {
						attendanceStamp = timeLeavingWork.getLeaveStamp();
					}
					// 実打刻
					if ("実打刻".equals(actualStampClass)) {
						return attendanceStamp.getActualStamp();
					}
					return attendanceStamp.getStamp();
				}
			}

		}
		return null;
	}

	private TimeActualStamp getTimeActualStamp(TimeLeavingOfDailyPerformance timeDailyPer, int worktNo,
			String attendanceClass, String actualStampClass) {
		if (timeDailyPer != null) {
			List<TimeLeavingWork> lstTimeLeavingWork = timeDailyPer.getTimeLeavingWorks();
			int n = lstTimeLeavingWork.size();
			for (int i = 0; i < n; i++) {
				TimeLeavingWork timeLeavingWork = lstTimeLeavingWork.get(i);
				if (timeLeavingWork.getWorkNo().v().intValue() == worktNo) {
					// 出勤
					if ("出勤".equals(attendanceClass)) {
						return timeLeavingWork.getAttendanceStamp();
					} else {
						return timeLeavingWork.getLeaveStamp();
					}
				}
			}
		}
		return null;
	}

	// Phán đoán điều kiện phản ảnh 出退勤 của 通常打刻 (true reflect and false no
	// reflect)
	private boolean checkReflectNormal(StampItem stamp, TimePrintDestinationOutput timePrintDestinationOutput,
			GeneralDate date, String employeeId) {
		if (timePrintDestinationOutput == null) {
			return true;
		} else {
			if (timePrintDestinationOutput.getStampSourceInfo().value == 6
					|| timePrintDestinationOutput.getStampSourceInfo().value == 7) {
				// sua bang tay
				// return no reflect
				return false;

			} else if (timePrintDestinationOutput.getStampSourceInfo().value == 1) {
				// k phai sua bang tay
				// return no reflect
				return false;

			} else {
				String companyId = AppContexts.user().companyId();
				// Phán đoán thứ tự uu tiên đơn xin và 打刻
				boolean checkStampPriority = checkStampPriority(timePrintDestinationOutput, companyId);
				if (checkStampPriority) {
					// Phán đoán thứ tự ưu tiên tự động check 打刻
					boolean checkPriorityAutoStamp = checkPriorityAutoStamp(timePrintDestinationOutput, companyId);
					if (checkPriorityAutoStamp) {
						// 3* 前優先後優先を見て反映するか確認する
						boolean confirmReflectPriority = confirmReflectPriority(stamp, timePrintDestinationOutput, date,
								employeeId);
						if (confirmReflectPriority) {
							return true;
						}
						// 3*
						return false;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}

		}
	}

	// 3* 前優先後優先を見て反映するか確認する (true reflect and false no reflect )
	private boolean confirmReflectPriority(StampItem stamp, TimePrintDestinationOutput timePrintDestinationOutput,
			GeneralDate date, String employeeId) {
		Optional<WorkInfoOfDailyPerformance> WorkInfoOptional = this.workInforRepo.find(employeeId, date);
		if (WorkInfoOptional.isPresent()) {
			WorkInformation recordWorkInformation = WorkInfoOptional.get().getRecordWorkInformation();
			WorkTimeCode workTimeCode = recordWorkInformation.getWorkTimeCode();

			// 4* KMK003 get PrioritySetting and (fixed:
			// priorityAtr = 前優先 )
			// 優先設定．打刻区分 ＝ パラメータ「出退勤区分」
			MultiStampTimePiorityAtr priorityAtr = MultiStampTimePiorityAtr.valueOf(0);
			// 4*

			AttendanceTime attendanceTime = stamp.getAttendanceTime();
			TimeWithDayAttr timeDestination = timePrintDestinationOutput.getTimeOfDay();
			if (priorityAtr.value == 0) {
				if (attendanceTime.v() >= timeDestination.v()) {
					return false;
				} else {
					return true;
				}

			} else {
				if (attendanceTime.v() >= timeDestination.v()) {
					return true;
				} else {
					return false;
				}
			}

		}
		return true;
	}

	/*
	 * // 2* check tay ngày nghỉ) worktype thay đổi (true reflect and false no
	 * // reflect) private boolean checkHolidayChange(GeneralDate date, String
	 * employeeId) { Optional<WorkInfoOfDailyPerformance> WorkInfoOptional =
	 * this.workInforRepo.find(employeeId, date); if
	 * (WorkInfoOptional.isPresent()) { WorkInformation recordWorkInformation =
	 * WorkInfoOptional.get().getRecordWorkInformation(); // Xác định phân loại
	 * 1日半日出勤・1日休日 WorkStyle checkWorkDay = this.basicScheduleService
	 * .checkWorkDay(recordWorkInformation.getWorkTypeCode().v()); // 休日系 if
	 * (checkWorkDay.value == 0) { // service 勤務情報を反映する // WorkTimeCode
	 * workTimeCode = // recordWorkInformation.getWorkTimeCode(); //
	 * WorkTypeCode workTypeCode = // recordWorkInformation.getWorkTypeCode();
	 * if (!this.reflectWorkInformationDomainService.changeWorkInformation(
	 * timeDailyPer)) { return false; } } return true; } // chưa xác nhận return
	 * true; }
	 */
	// 2* check tay ngày nghỉ) worktype thay đổi (true reflect and false no
	// reflect)
	private boolean checkHolidayChange(WorkInfoOfDailyPerformance WorkInfo) {
		if (WorkInfo != null) {
			WorkInformation recordWorkInformation = WorkInfo.getRecordWorkInformation();
			// Xác định phân loại 1日半日出勤・1日休日
			WorkStyle checkWorkDay = this.basicScheduleService
					.checkWorkDay(recordWorkInformation.getWorkTypeCode().v());
			// 休日系
			if (checkWorkDay.value == 0) {
				// service 勤務情報を反映する
				// WorkTimeCode workTimeCode =
				// recordWorkInformation.getWorkTimeCode();
				// WorkTypeCode workTypeCode =
				// recordWorkInformation.getWorkTypeCode();
				if (!this.reflectWorkInformationDomainService.changeWorkInformation(WorkInfo)) {
					return false;
				}
			}
			return true;
		}
		// chưa xác nhận
		return true;
	}

	// Phán đoán thứ tự ưu tiên tự động check 打刻
	private boolean checkPriorityAutoStamp(TimePrintDestinationOutput timePrintDestinationOutput, String companyId) {
		// true (reflect) and false (no reflect)
		if (timePrintDestinationOutput.getStampSourceInfo().value == 4
				&& timePrintDestinationOutput.getStampSourceInfo().value == 5) {
			Optional<StampReflectionManagement> stampOptional = this.stampRepo.findByCid(companyId);
			if (stampOptional.isPresent()) {
				StampReflectionManagement stampReflectionManagement = stampOptional.get();
				if (stampReflectionManagement.getAutoStampReflectionClass().value == 0) {
					return false;
				}
				return true;
			}
			// reflect
			// stampOptional is null chua cover
			// trong ea
			return true;

		}
		return true;
	}

	// Phán đoán thứ tự ueu tiên đơn xin và 打刻
	private boolean checkStampPriority(TimePrintDestinationOutput timePrintDestinationOutput, String companyId) {
		// true (reflect) and false (no reflect)
		Optional<StampReflectionManagement> stampOptional = this.stampRepo.findByCid(companyId);
		if (stampOptional.isPresent()) {
			StampReflectionManagement stampReflectionManagement = stampOptional.get();
			// 直行直帰・出張申請の打刻優先 and 直行直帰申請
			if (stampReflectionManagement.getActualStampOfPriorityClass().value == 1
					&& timePrintDestinationOutput.getStampSourceInfo().value == 4) {
				return false;
			}
			return true;
		}
		// stampOptional is null chua cover trong ea
		return true;
	}

	private String confirmReflectRange(StampItem stampItem, StampReflectRangeOutput s) {
		// Chuyển đổi 打刻．時刻
		AttendanceTime attendanceTime = stampItem.getAttendanceTime();
		ProcessTimeOutput processTimeOutput = new ProcessTimeOutput();
		processTimeOutput.setTimeOfDay(new TimeWithDayAttr(attendanceTime.v()));

		// in or outrange
		List<StampReflectTimezoneOutput> lstStampReflectTimezone = s.getLstStampReflectTimezone();
		int n = lstStampReflectTimezone.size();
		for (int i = 0; i < n; i++) {
			StampReflectTimezoneOutput stampReflectTimezone = lstStampReflectTimezone.get(i);
			if (stampReflectTimezone.getStartTime().v() <= processTimeOutput.getTimeOfDay().v()
					&& stampReflectTimezone.getEndTime().v() >= processTimeOutput.getTimeOfDay().v()
					&& stampReflectTimezone.getClassification().value == 0) {
				if (stampReflectTimezone.getWorkNo().v().intValue() == 1) {
					return "range1";
				}
				if (stampReflectTimezone.getWorkNo().v().intValue() == 2) {
					return "range2";
				}
			}
		}
		return "outrange";
	}

}
