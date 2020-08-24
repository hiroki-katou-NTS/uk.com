package nts.uk.ctx.at.record.pubimp.workinformation;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.TotalWorkingTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkingTimeSheet;
import nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute;
import nts.uk.ctx.at.record.dom.shorttimework.primitivevalue.ShortWorkTimFrameNo;
import nts.uk.ctx.at.record.dom.stamp.GoOutReason;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.pub.workinformation.CommonTimeSheet;
import nts.uk.ctx.at.record.pub.workinformation.InfoCheckNotRegisterPubExport;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPub;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPubExport;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPubExport_Old;
import nts.uk.ctx.at.record.pub.workinformation.WorkInfoOfDailyPerExport;
import nts.uk.ctx.at.record.pub.workinformation.WrScheduleTimeSheetExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrReasonTimeChangeExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrTimeActualStampExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrTimeLeavingWorkExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrWorkStampExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrWorkTimeInformationExport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class RecordWorkInfoPubImpl implements RecordWorkInfoPub {

	@Inject
	private WorkInformationRepository workInformationRepository;
	
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;
	
	@Inject
	private AttendanceTimeRepository attendanceTimeRepo;

	/**
	 * RequestList5
	 */
	@Override
	public RecordWorkInfoPubExport_Old getRecordWorkInfo(String employeeId, GeneralDate ymd) {
		Optional<WorkInfoOfDailyPerformance> workInfo = this.workInformationRepository.find(employeeId, ymd);
		if(!workInfo.isPresent()) {
			return new RecordWorkInfoPubExport_Old("", "");
		}
		String workTimeCode = workInfo.get().getRecordInfo().getWorkTimeCode() == null 
				? null : workInfo.get().getRecordInfo().getWorkTimeCode().v();
		String workTypeCode = workInfo.get().getRecordInfo().getWorkTypeCode().v();
		RecordWorkInfoPubExport_Old record = new RecordWorkInfoPubExport_Old(
				workTypeCode,
				workTimeCode);
		
		//日別実績の出退勤
		Optional<TimeLeavingOfDailyPerformance> timeLeaving = this.timeLeavingOfDailyPerformanceRepository.findByKey(employeeId, ymd);
		Optional<AttendanceTimeOfDailyPerformance> attenTime = this.attendanceTimeRepo.find(employeeId, ymd);
		
		timeLeaving.ifPresent(tl -> {
			tl.getAttendanceLeavingWork(1).ifPresent(al -> {
				// nampt : check null case
				al.getAttendanceStamp().ifPresent(stamp -> {
					stamp.getStamp().ifPresent(s -> {
						record.setAttendanceStampTimeFirst(s.getTimeWithDay() == null ? null : s.getTimeWithDay().valueAsMinutes());
					});
				});
				al.getLeaveStamp().ifPresent(stamp -> {
					stamp.getStamp().ifPresent(s -> {
						record.setLeaveStampTimeFirst(s.getTimeWithDay() == null ? null : s.getTimeWithDay().valueAsMinutes());
					});
				});
			});
			tl.getAttendanceLeavingWork(2).ifPresent(al -> {
				// nampt : check null case
				al.getAttendanceStamp().ifPresent(stamp -> {
					stamp.getStamp().ifPresent(s -> {
						record.setAttendanceStampTimeSecond(s.getTimeWithDay() == null ? null : s.getTimeWithDay().valueAsMinutes());
					});
				});
				al.getLeaveStamp().ifPresent(stamp -> {
					stamp.getStamp().ifPresent(s -> {
						record.setLeaveStampTimeSecond(s.getTimeWithDay() == null ? null : s.getTimeWithDay().valueAsMinutes());
					});
				});
			});
		});
		
		attenTime.ifPresent(at -> {
			TotalWorkingTime totalWT = at.getActualWorkingTimeOfDaily().getTotalWorkingTime();
			if(totalWT == null){
				return;
			}
			totalWT.getLateTimeNo(1).ifPresent(lt -> {
				Integer time = lt.getLateTime().getTime() == null ? null : lt.getLateTime().getTime().valueAsMinutes();
				record.setLateTime1(time);
			});
			totalWT.getLateTimeNo(2).ifPresent(lt -> {
				Integer time = lt.getLateTime().getTime() == null ? null : lt.getLateTime().getTime().valueAsMinutes();
				record.setLateTime2(time);
			});
			totalWT.getLeaveEarlyTimeNo(1).ifPresent(lt -> {
				Integer time = lt.getLeaveEarlyTime().getTime() == null ? null : lt.getLeaveEarlyTime().getTime().valueAsMinutes();
				record.setLeaveEarlyTime1(time);
			});
			totalWT.getLeaveEarlyTimeNo(2).ifPresent(lt -> {
				Integer time = lt.getLeaveEarlyTime().getTime() == null ? null : lt.getLeaveEarlyTime().getTime().valueAsMinutes();
				record.setLeaveEarlyTime2(time);
			});
			if(totalWT.getShotrTimeOfDaily() != null && 
					totalWT.getShotrTimeOfDaily().getTotalTime() != null
					&& totalWT.getShotrTimeOfDaily().getTotalTime().getTotalTime() != null){
				record.setChildCareTime(totalWT.getShotrTimeOfDaily().getTotalTime().getTotalTime().getTime().valueAsMinutes());
			}
			totalWT.getOutingTimeByReason(GoOutReason.OFFICAL).ifPresent(ot -> {
				if(ot.getRecordTotalTime() != null && ot.getRecordTotalTime().getTotalTime() != null){
					record.setOutingTimePrivate(ot.getRecordTotalTime().getTotalTime().getTime().valueAsMinutes());
				}
			});
			totalWT.getOutingTimeByReason(GoOutReason.SUPPORT).ifPresent(ot -> {
				if(ot.getRecordTotalTime() != null && ot.getRecordTotalTime().getTotalTime() != null){
					record.setOutingTimePrivate(ot.getRecordTotalTime().getTotalTime().getTime().valueAsMinutes());
				}
			});
			
			totalWT.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().ifPresent(ovt -> {
				if(ovt.getFlexTime() != null && ovt.getFlexTime().getFlexTime() != null){
					record.setFlexTime(ovt.getFlexTime().getFlexTime().getCalcTime().valueAsMinutes());
				}
				record.setOvertimes(ovt.getOverTimeWorkFrameTime().stream().map(c -> 
																	new CommonTimeSheet(c.getOverWorkFrameNo().v(), 
																			getCalcTime(c.getOverTimeWork()), 
																			getCalcTime(c.getTransferTime())))
																.collect(Collectors.toList()));
			});
			totalWT.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().ifPresent(holW -> {
				record.setHolidayWorks(holW.getHolidayWorkFrameTime().stream().map(c -> 
																	new CommonTimeSheet(c.getHolidayFrameNo().v(), 
																			getCalcTime(c.getHolidayWorkTime()), 
																			getCalcTime(c.getTransferTime())))
																.collect(Collectors.toList()));
			});
			record.setMidnightTime(totalWT.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime().getCalcTime().v());
		});
		
		return record;
	}

	@Override
	public Optional<InfoCheckNotRegisterPubExport> getInfoCheckNotRegister(String employeeId, GeneralDate ymd) {
		 Optional<InfoCheckNotRegisterPubExport> data = workInformationRepository.find(employeeId, ymd).map(c->convertToExport(c));
		if(data.isPresent()) {
			return data;
		}
		return Optional.empty();
	}
	
	private InfoCheckNotRegisterPubExport convertToExport(WorkInfoOfDailyPerformance domain) {
		return new InfoCheckNotRegisterPubExport(
				domain.getEmployeeId(),
				domain.getRecordInfo().getWorkTimeCode()==null?"":domain.getRecordInfo().getWorkTimeCode().v(),
				domain.getRecordInfo().getWorkTypeCode().v(),
				domain.getYmd());
	}
	
	private Integer getCalcTime(TimeDivergenceWithCalculation calc){
		return calc == null || calc.getCalcTime() == null ? null : calc.getCalcTime().valueAsMinutes();
	}

	private Integer getCalcTime(Finally<TimeDivergenceWithCalculation> calc){
		return !calc.isPresent() || calc == null || calc.get().getCalcTime() == null ? null : calc.get().getCalcTime().valueAsMinutes();
	}

	@Override
	public Optional<String> getWorkTypeCode(String employeeId, GeneralDate ymd) {
		Optional<WorkInfoOfDailyPerformance> optWorkInfo =  workInformationRepository.find(employeeId, ymd);
		if(!optWorkInfo.isPresent()) return Optional.ofNullable(null);		
		return Optional.of(optWorkInfo.get().getRecordInfo().getWorkTypeCode().v());
	}

	@Override
	public List<InfoCheckNotRegisterPubExport> findByPeriodOrderByYmdAndEmps(List<String> employeeIds, DatePeriod datePeriod) {
		List<WorkInfoOfDailyPerformance> data = workInformationRepository.findByPeriodOrderByYmdAndEmps(employeeIds, datePeriod);
		if(data.isEmpty())
			return Collections.emptyList();
		return data.stream().map(c->convertToExportInfor(c)).collect(Collectors.toList());
	}
	
	private InfoCheckNotRegisterPubExport convertToExportInfor(WorkInfoOfDailyPerformance domain) {
		return new InfoCheckNotRegisterPubExport(
				domain.getEmployeeId(),
				domain.getRecordInfo() ==null?null : (domain.getRecordInfo().getWorkTimeCode()==null? null : domain.getRecordInfo().getWorkTimeCode().v()),
				domain.getRecordInfo() ==null?null : (domain.getRecordInfo().getWorkTypeCode()==null? null : domain.getRecordInfo().getWorkTypeCode().v()),
				domain.getYmd());
	}

	@Override
	public List<WorkInfoOfDailyPerExport> findByEmpId(String employeeId) {
		List<WorkInfoOfDailyPerformance> workInfo = this.workInformationRepository.findByEmployeeId(employeeId);
		if(workInfo.isEmpty())
			return Collections.emptyList();
		return workInfo.stream().map(c->convertToWorkInfoOfDailyPerformance(c)).collect(Collectors.toList());
	}
	
	private WorkInfoOfDailyPerExport convertToWorkInfoOfDailyPerformance(WorkInfoOfDailyPerformance domain) {
		return new WorkInfoOfDailyPerExport(
				domain.getEmployeeId(),
				domain.getYmd()
				);
	}

	@Override
	public RecordWorkInfoPubExport getRecordWorkInfoNew(String employeeId, GeneralDate ymd) {
		try {
			
			String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
			File fXmlFile = new File(currentPath + "\\datatest\\staff1.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			String testXml = doc.getDocumentElement().getNodeName();
			Element eElement = (Element) doc.getElementsByTagName("data").item(0);
			
			//社員ID
			employeeId = eElement.getElementsByTagName("employeeId").item(0).getTextContent();
				
			//年月日
			ymd = GeneralDate.fromString(eElement.getElementsByTagName("ymd").item(0).getTextContent(), "yyyy/MM/dd");

			// 勤務種類コード
			String workTypeCode = eElement.getElementsByTagName("workTypeCode").item(0).getTextContent();
			
			// 就業時間帯コード
			String workTimeCode = eElement.getElementsByTagName("workTimeCode").item(0).getTextContent();
			
			RecordWorkInfoPubExport recordWorkInfoPubExport = new RecordWorkInfoPubExport(workTypeCode, workTimeCode);
			recordWorkInfoPubExport.setEmployeeId(employeeId);
			recordWorkInfoPubExport.setYmd(ymd);
			
			//開始時刻1
			Integer attendanceStampTimeFirst = Integer.parseInt(eElement.getElementsByTagName("attendanceStampTimeFirst").item(0).getTextContent());
			recordWorkInfoPubExport.setAttendanceStampTimeFirst(attendanceStampTimeFirst);
			//終了時刻1
			Integer leaveStampTimeFirst = Integer.parseInt(eElement.getElementsByTagName("leaveStampTimeFirst").item(0).getTextContent());
			recordWorkInfoPubExport.setLeaveStampTimeFirst(leaveStampTimeFirst);
			//開始時刻2
			Integer attendanceStampTimeSecond = Integer.parseInt(eElement.getElementsByTagName("attendanceStampTimeSecond").item(0).getTextContent());
			recordWorkInfoPubExport.setAttendanceStampTimeSecond(attendanceStampTimeSecond);
			//終了時刻1
			Integer leaveStampTimeSecond = Integer.parseInt(eElement.getElementsByTagName("leaveStampTimeSecond").item(0).getTextContent());
			recordWorkInfoPubExport.setAttendanceStampTimeSecond(attendanceStampTimeSecond);
			//遅刻時間
			Integer lateTime1 = Integer.parseInt(eElement.getElementsByTagName("lateTime1").item(0).getTextContent());
			recordWorkInfoPubExport.setLateTime1(lateTime1);
			//早退時間
			Integer leaveEarlyTime1 = Integer.parseInt(eElement.getElementsByTagName("leaveEarlyTime1").item(0).getTextContent());
			recordWorkInfoPubExport.setLeaveEarlyTime1(leaveEarlyTime1);
			//遅刻時間2
			Integer lateTime2 = Integer.parseInt(eElement.getElementsByTagName("lateTime2").item(0).getTextContent());
			recordWorkInfoPubExport.setLateTime2(lateTime2);
			//早退時間2
			Integer leaveEarlyTime2 = Integer.parseInt(eElement.getElementsByTagName("leaveEarlyTime2").item(0).getTextContent());
			recordWorkInfoPubExport.setLeaveEarlyTime2(leaveEarlyTime2);
			//育児時間
			Integer childCareTime = Integer.parseInt(eElement.getElementsByTagName("childCareTime").item(0).getTextContent());
			recordWorkInfoPubExport.setChildCareTime(childCareTime);
			//外出時間.私用
			Integer outingTimePrivate = Integer.parseInt(eElement.getElementsByTagName("outingTimePrivate").item(0).getTextContent());
			recordWorkInfoPubExport.setOutingTimePrivate(outingTimePrivate);
			//外出時間.組合
			Integer outingTimeCombine = Integer.parseInt(eElement.getElementsByTagName("outingTimeCombine").item(0).getTextContent());
			recordWorkInfoPubExport.setOutingTimeCombine(outingTimeCombine);
			Integer flexTime = Integer.parseInt(eElement.getElementsByTagName("flexTime").item(0).getTextContent());
			recordWorkInfoPubExport.setFlexTime(flexTime);
			
			//計算残業 and 計算振替残業
			List<CommonTimeSheet> overtimes = new ArrayList<>();
			Element nodeOvertimes = (Element)eElement.getElementsByTagName("overtimes").item(0);
			NodeList nodeCommonTimeSheet1 = nodeOvertimes.getElementsByTagName("CommonTimeSheet");
			for (int itr = 0; itr < nodeCommonTimeSheet1.getLength(); itr++) {
				Node node = nodeCommonTimeSheet1.item(itr);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					Integer no = Integer.parseInt(e.getElementsByTagName("no").item(0).getTextContent());
					Integer time = Integer.parseInt(e.getElementsByTagName("time").item(0).getTextContent());
					Integer tranferTime = Integer.parseInt(e.getElementsByTagName("tranferTime").item(0).getTextContent());
					CommonTimeSheet commonTimeSheet = new CommonTimeSheet(no, time, tranferTime);
					overtimes.add(commonTimeSheet);
				}
			}
			recordWorkInfoPubExport.setOvertimes(overtimes);
			
			//計算休日出勤 and 計算振替
			List<CommonTimeSheet> holidayWorks = new ArrayList<>();
			Element nodeHolidayWorks = (Element)eElement.getElementsByTagName("overtimes").item(0);
			NodeList nodeCommonTimeSheet2 = nodeHolidayWorks.getElementsByTagName("CommonTimeSheet");
			for (int itr = 0; itr < nodeCommonTimeSheet2.getLength(); itr++) {
				Node node = nodeCommonTimeSheet2.item(itr);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					Integer no = Integer.parseInt(e.getElementsByTagName("no").item(0).getTextContent());
					Integer time = Integer.parseInt(e.getElementsByTagName("time").item(0).getTextContent());
					Integer tranferTime = Integer.parseInt(e.getElementsByTagName("tranferTime").item(0).getTextContent());
					CommonTimeSheet commonTimeSheet = new CommonTimeSheet(no, time, tranferTime);
					holidayWorks.add(commonTimeSheet);
				}
			}
			recordWorkInfoPubExport.setHolidayWorks(holidayWorks);
			Integer midnightTime = Integer.parseInt(eElement.getElementsByTagName("midnightTime").item(0).getTextContent());
			recordWorkInfoPubExport.setMidnightTime(midnightTime);
			//勤務予定時間帯 (予定出勤時刻1~2 予定退勤時刻1~2)
			List<WrScheduleTimeSheetExport> listWrScheduleTimeSheetExport = new ArrayList<>();
			
			Element nodelistWrScheduleTimeSheetExport = (Element)eElement.getElementsByTagName("listWrScheduleTimeSheetExport").item(0);
			
			NodeList nodeWrScheduleTimeSheetExport = nodelistWrScheduleTimeSheetExport.getElementsByTagName("WrScheduleTimeSheetExport");
			for (int itr = 0; itr < nodeWrScheduleTimeSheetExport.getLength(); itr++) {
				Node node = nodeWrScheduleTimeSheetExport.item(itr);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					Integer workNo = Integer.parseInt(e.getElementsByTagName("workNo").item(0).getTextContent());
					Integer attendance = Integer.parseInt(e.getElementsByTagName("attendance").item(0).getTextContent());
					Integer leaveWork = Integer.parseInt(e.getElementsByTagName("leaveWork").item(0).getTextContent());
					WrScheduleTimeSheetExport i = new WrScheduleTimeSheetExport(workNo, new TimeWithDayAttr(attendance), new TimeWithDayAttr(leaveWork));
					listWrScheduleTimeSheetExport.add(i);
				}
			}
			recordWorkInfoPubExport.setListWrScheduleTimeSheetExport(listWrScheduleTimeSheetExport);
			//臨時時間帯 
			List<WrTimeLeavingWorkExport> listTimeLeavingWorkExport = new ArrayList<>();
			Element nodelistTimeLeavingWorkExport = (Element)eElement.getElementsByTagName("listTimeLeavingWorkExport").item(0);
			NodeList nodeWrTimeLeavingWorkExport = nodelistTimeLeavingWorkExport.getElementsByTagName("WrTimeLeavingWorkExport");
			
			for (int itr = 0; itr < nodeWrTimeLeavingWorkExport.getLength(); itr++) {
				Node node = nodeWrTimeLeavingWorkExport.item(itr);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					Integer workNo = Integer.parseInt(e.getElementsByTagName("workNo").item(0).getTextContent());

//					WrTimeActualStampExport attendanceStamp = new WrTimeActualStampExport();
					Element eAttendanceStamp = (Element) e.getElementsByTagName("attendanceStamp").item(0);
					WrTimeActualStampExport attendanceStampOp = null;
					if (eAttendanceStamp != null) {						
						Element eWrTimeActualStampExport = (Element) eAttendanceStamp.getElementsByTagName("WrTimeActualStampExport").item(0);
						
//					actualStamp
						Element eActualStamp = (Element) eWrTimeActualStampExport.getElementsByTagName("actualStamp").item(0);
						WrWorkStampExport actualStamp = null;
						if (eActualStamp != null) {
							
							Element eWrWorkStampExport = (Element) eActualStamp.getElementsByTagName("WrWorkStampExport").item(0);	
							Integer afterRoundingTime = Integer.parseInt(eWrWorkStampExport.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
							
							Element eTimeDay = (Element) eWrWorkStampExport.getElementsByTagName("timeDay").item(0);	
							Element eWrWorkTimeInformationExport = (Element) eTimeDay.getElementsByTagName("WrWorkTimeInformationExport").item(0);
							Element eReasonTimeChange = (Element) eWrWorkTimeInformationExport.getElementsByTagName("reasonTimeChange").item(0);
							Element eWrReasonTimeChangeExport = (Element) eReasonTimeChange.getElementsByTagName("WrReasonTimeChangeExport").item(0);
							Integer timeChangeMeans = Integer.parseInt(eWrReasonTimeChangeExport.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
							Integer engravingMethod = Integer.parseInt(eWrReasonTimeChangeExport.getElementsByTagName("engravingMethod").item(0).getTextContent());
							WrReasonTimeChangeExport reasonTimeChange = new WrReasonTimeChangeExport(timeChangeMeans, engravingMethod);
							Integer timeWithDay = Integer.parseInt(eWrWorkTimeInformationExport.getElementsByTagName("timeWithDay").item(0).getTextContent());
//					timeDay
							WrWorkTimeInformationExport timeDay = new WrWorkTimeInformationExport(reasonTimeChange, timeWithDay);
							String locationCode = eWrWorkStampExport.getElementsByTagName("locationCode").item(0).getTextContent();
							// WrWorkStampExport
							actualStamp = new WrWorkStampExport(afterRoundingTime, timeDay, locationCode);
							//Optional
							Optional<WrWorkStampExport> actualStampOp = Optional.of(actualStamp);
						}
						
						
						
//					stamp
						Element eStamp = (Element) eWrTimeActualStampExport.getElementsByTagName("stamp").item(0);
						if (eStamp != null) {
							
							Element eWrWorkStampExport = (Element) eStamp.getElementsByTagName("WrWorkStampExport").item(0);	
							Integer afterRoundingTime = Integer.parseInt(eWrWorkStampExport.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
							
							Element eTimeDay = (Element) eWrWorkStampExport.getElementsByTagName("timeDay").item(0);	
							Element eWrWorkTimeInformationExport = (Element) eTimeDay.getElementsByTagName("WrWorkTimeInformationExport").item(0);
							Element eReasonTimeChange = (Element) eWrWorkTimeInformationExport.getElementsByTagName("reasonTimeChange").item(0);
							Element eWrReasonTimeChangeExport = (Element) eReasonTimeChange.getElementsByTagName("WrReasonTimeChangeExport").item(0);
							Integer timeChangeMeans = Integer.parseInt(eWrReasonTimeChangeExport.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
							Integer engravingMethod = Integer.parseInt(eWrReasonTimeChangeExport.getElementsByTagName("engravingMethod").item(0).getTextContent());
							WrReasonTimeChangeExport reasonTimeChange1 = new WrReasonTimeChangeExport(timeChangeMeans, engravingMethod);
							Integer timeWithDay = Integer.parseInt(eWrWorkTimeInformationExport.getElementsByTagName("timeWithDay").item(0).getTextContent());
//					timeDay
							WrWorkTimeInformationExport timeDay1 = new WrWorkTimeInformationExport(reasonTimeChange1, timeWithDay);
							String locationCode1 = eWrWorkStampExport.getElementsByTagName("locationCode").item(0).getTextContent();
							// WrWorkStampExport
							WrWorkStampExport stamp = new WrWorkStampExport(afterRoundingTime, timeDay1, locationCode1);
							//Optional
							Optional<WrWorkStampExport> stampOp = Optional.of(stamp);
							
							Integer numberOfReflectionStamp = Integer.parseInt(eWrTimeActualStampExport.getElementsByTagName("numberOfReflectionStamp").item(0).getTextContent());
							WrTimeActualStampExport wrTimeActualStampExport = new WrTimeActualStampExport(actualStamp, stamp, numberOfReflectionStamp);
							attendanceStampOp = wrTimeActualStampExport;
						}
					}

					
					
					
					
					
					
					
					
					
					
					
					
					Element leaveStamp = (Element) e.getElementsByTagName("leaveStamp").item(0);
					WrTimeActualStampExport leaveStampOp = null;
					if (leaveStamp != null) {						
						Element eWrTimeActualStampExport = (Element) leaveStamp.getElementsByTagName("WrTimeActualStampExport").item(0);
						
//					actualStamp
						Element eActualStamp = (Element) eWrTimeActualStampExport.getElementsByTagName("actualStamp").item(0);
						WrWorkStampExport actualStamp1 = null;
						if (eActualStamp != null) {
							
							Element eWrWorkStampExport = (Element) eActualStamp.getElementsByTagName("WrWorkStampExport").item(0);	
							Integer afterRoundingTime = Integer.parseInt(eWrWorkStampExport.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
							
							Element eTimeDay = (Element) eWrWorkStampExport.getElementsByTagName("timeDay").item(0);	
							Element eWrWorkTimeInformationExport = (Element) eTimeDay.getElementsByTagName("WrWorkTimeInformationExport").item(0);
							Element eReasonTimeChange = (Element) eWrWorkTimeInformationExport.getElementsByTagName("reasonTimeChange").item(0);
							Element eWrReasonTimeChangeExport = (Element) eReasonTimeChange.getElementsByTagName("WrReasonTimeChangeExport").item(0);
							Integer timeChangeMeans = Integer.parseInt(eWrReasonTimeChangeExport.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
							Integer engravingMethod = Integer.parseInt(eWrReasonTimeChangeExport.getElementsByTagName("engravingMethod").item(0).getTextContent());
							WrReasonTimeChangeExport reasonTimeChange2 = new WrReasonTimeChangeExport(timeChangeMeans, engravingMethod);
							Integer timeWithDay = Integer.parseInt(eWrWorkTimeInformationExport.getElementsByTagName("timeWithDay").item(0).getTextContent());
//					timeDay
							WrWorkTimeInformationExport timeDay2 = new WrWorkTimeInformationExport(reasonTimeChange2, timeWithDay);
							String locationCode2 = eWrWorkStampExport.getElementsByTagName("locationCode").item(0).getTextContent();
							// WrWorkStampExport
							actualStamp1 = new WrWorkStampExport(afterRoundingTime, timeDay2, locationCode2);
							//Optional
							Optional<WrWorkStampExport> actualStampOp = Optional.of(actualStamp1);
						}
						
						
						
						
//					stamp
						Element eStamp = (Element) eWrTimeActualStampExport.getElementsByTagName("stamp").item(0);
						if (eStamp != null) {
							
							Element eWrWorkStampExport = (Element) eStamp.getElementsByTagName("WrWorkStampExport").item(0);	
							Integer afterRoundingTime = Integer.parseInt(eWrWorkStampExport.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
							
							Element eTimeDay = (Element) eWrWorkStampExport.getElementsByTagName("timeDay").item(0);	
							Element eWrWorkTimeInformationExport = (Element) eTimeDay.getElementsByTagName("WrWorkTimeInformationExport").item(0);
							Element eReasonTimeChange = (Element) eWrWorkTimeInformationExport.getElementsByTagName("reasonTimeChange").item(0);
							Element eWrReasonTimeChangeExport = (Element) eReasonTimeChange.getElementsByTagName("WrReasonTimeChangeExport").item(0);
							Integer timeChangeMeans = Integer.parseInt(eWrReasonTimeChangeExport.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
							Integer engravingMethod = Integer.parseInt(eWrReasonTimeChangeExport.getElementsByTagName("engravingMethod").item(0).getTextContent());
							WrReasonTimeChangeExport reasonTimeChange3 = new WrReasonTimeChangeExport(timeChangeMeans, engravingMethod);
							Integer timeWithDay = Integer.parseInt(eWrWorkTimeInformationExport.getElementsByTagName("timeWithDay").item(0).getTextContent());
//					timeDay
							WrWorkTimeInformationExport timeDay3 = new WrWorkTimeInformationExport(reasonTimeChange3, timeWithDay);
							String locationCode3 = eWrWorkStampExport.getElementsByTagName("locationCode").item(0).getTextContent();
							// WrWorkStampExport
							WrWorkStampExport stamp1 = new WrWorkStampExport(afterRoundingTime, timeDay3, locationCode3);
							//Optional
							Optional<WrWorkStampExport> stampOp = Optional.of(stamp1);
							
							Integer numberOfReflectionStamp = Integer.parseInt(eWrTimeActualStampExport.getElementsByTagName("numberOfReflectionStamp").item(0).getTextContent());
							WrTimeActualStampExport wrTimeActualStampExport1 = new WrTimeActualStampExport(actualStamp1, stamp1, numberOfReflectionStamp);
							leaveStampOp = wrTimeActualStampExport1;
							
						}
					}

					
					WrTimeLeavingWorkExport wrTimeLeavingWorkExport = new WrTimeLeavingWorkExport(workNo, attendanceStampOp, leaveStampOp);
					
					listTimeLeavingWorkExport.add(wrTimeLeavingWorkExport);
				}
			}
			recordWorkInfoPubExport.setListTimeLeavingWorkExport(listTimeLeavingWorkExport);
//			nodeOvertimes.item(0).getElementsByTagName()
			//外出時間帯
			List<OutingTimeSheet> listOutingTimeSheet = new ArrayList<>();
			Element elistOutingTimeSheet = (Element) eElement.getElementsByTagName("listOutingTimeSheet").item(0);
			NodeList nodeOutingTimeSheet = elistOutingTimeSheet.getElementsByTagName("OutingTimeSheet");
			
			for (int itr = 0; itr < nodeOutingTimeSheet.getLength(); itr++) {
				Node node = nodeOutingTimeSheet.item(itr);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					
					Integer outingFrameNo = Integer.parseInt(e.getElementsByTagName("outingFrameNo").item(0).getTextContent());
					
					Element egoOut = (Element) eElement.getElementsByTagName("goOut").item(0);
					
					Element eTimeActualStamp = (Element) egoOut.getElementsByTagName("TimeActualStamp").item(0);
					Optional<TimeActualStamp> goOutOp;
					
					if (eTimeActualStamp == null) {
						goOutOp = Optional.empty();
					} else {
						TimeActualStamp timeActualStamp = new TimeActualStamp();
						
						Element eActualStamp = (Element) eTimeActualStamp.getElementsByTagName("actualStamp").item(0);
						goOutOp = Optional.empty();
						Optional<WorkStamp> actualStamp = Optional.empty();
						if (eActualStamp != null) {
							
							Element eWorkStamp = (Element) eActualStamp.getElementsByTagName("WorkStamp").item(0);
							
							Integer AfterRoundingTime = Integer.parseInt(eWorkStamp.getElementsByTagName("AfterRoundingTime").item(0).getTextContent());
							Integer timeWithDay = Integer.parseInt(eWorkStamp.getElementsByTagName("timeWithDay").item(0).getTextContent());
							String locationCode = eWorkStamp.getElementsByTagName("locationCode").item(0).getTextContent();
							Integer stampSourceInfo = Integer.parseInt(eWorkStamp.getElementsByTagName("stampSourceInfo").item(0).getTextContent());
							
							WorkStamp workStamp = new WorkStamp(
									new TimeWithDayAttr(AfterRoundingTime),
									new TimeWithDayAttr(timeWithDay),
									new WorkLocationCD(locationCode),
									EnumAdaptor.valueOf(stampSourceInfo, StampSourceInfo.class));
							actualStamp = Optional.ofNullable(workStamp);
							
						}
						
						Element eStamp = (Element) eTimeActualStamp.getElementsByTagName("stamp").item(0);
						Optional<WorkStamp> stamp = Optional.empty();
						if (eStamp != null) {
							Element eWorkStamp = (Element) eStamp.getElementsByTagName("WorkStamp").item(0);
							
							Integer AfterRoundingTime = Integer.parseInt(eWorkStamp.getElementsByTagName("AfterRoundingTime").item(0).getTextContent());
							Integer timeWithDay = Integer.parseInt(eWorkStamp.getElementsByTagName("timeWithDay").item(0).getTextContent());
							String locationCode = eWorkStamp.getElementsByTagName("locationCode").item(0).getTextContent();
							Integer stampSourceInfo = Integer.parseInt(eWorkStamp.getElementsByTagName("stampSourceInfo").item(0).getTextContent());
							
							WorkStamp workStamp1 = new WorkStamp(
									new TimeWithDayAttr(AfterRoundingTime),
									new TimeWithDayAttr(timeWithDay),
									new WorkLocationCD(locationCode),
									EnumAdaptor.valueOf(stampSourceInfo, StampSourceInfo.class));
							stamp = Optional.ofNullable(workStamp1);
							
						}
						Integer numberOfReflectionStamp = Integer.parseInt(eTimeActualStamp.getElementsByTagName("numberOfReflectionStamp").item(0).getTextContent());
						timeActualStamp.setPropertyTimeActualStamp(actualStamp, stamp, numberOfReflectionStamp);
						goOutOp = Optional.of(timeActualStamp);
						
					}
					
					AttendanceTime outingTimeCalculation = new AttendanceTime(Integer.parseInt(e.getElementsByTagName("outingTimeCalculation").item(0).getTextContent()));
					
					AttendanceTime outingTime = new AttendanceTime(Integer.parseInt(e.getElementsByTagName("outingTime").item(0).getTextContent()));

					Integer reasonForGoOut = Integer.parseInt(e.getElementsByTagName("reasonForGoOut").item(0).getTextContent());

					
					Element ecomeBack = (Element) eElement.getElementsByTagName("comeBack").item(0);
					
					Element eTimeActualStamp1 = (Element) ecomeBack.getElementsByTagName("TimeActualStamp").item(0);
					Optional<TimeActualStamp> comeBackOp = Optional.empty();
					
					if (eTimeActualStamp1 == null) {
						comeBackOp = Optional.empty();
					} else {
						TimeActualStamp timeActualStamp = new TimeActualStamp();
						
						Element eActualStamp = (Element) eTimeActualStamp.getElementsByTagName("actualStamp").item(0);
						Optional<WorkStamp> actualStamp = Optional.empty();
						if (eActualStamp != null) {
							Element eWorkStamp = (Element) eActualStamp.getElementsByTagName("WorkStamp").item(0);
							
							Integer AfterRoundingTime = Integer.parseInt(eWorkStamp.getElementsByTagName("AfterRoundingTime").item(0).getTextContent());
							Integer timeWithDay = Integer.parseInt(eWorkStamp.getElementsByTagName("timeWithDay").item(0).getTextContent());
							String locationCode = eWorkStamp.getElementsByTagName("locationCode").item(0).getTextContent();
							Integer stampSourceInfo = Integer.parseInt(eWorkStamp.getElementsByTagName("stampSourceInfo").item(0).getTextContent());
							
							WorkStamp workStamp = new WorkStamp(
									new TimeWithDayAttr(AfterRoundingTime),
									new TimeWithDayAttr(timeWithDay),
									new WorkLocationCD(locationCode),
									EnumAdaptor.valueOf(stampSourceInfo, StampSourceInfo.class));
							actualStamp = Optional.ofNullable(workStamp);
							
						}
						
						
						
						Element eStamp = (Element) eTimeActualStamp.getElementsByTagName("stamp").item(0);
						Optional<WorkStamp> stamp = Optional.empty();
						if (eStamp != null) {
							Element eWorkStamp = (Element) eStamp.getElementsByTagName("WorkStamp").item(0);
							
							Integer AfterRoundingTime = Integer.parseInt(eWorkStamp.getElementsByTagName("AfterRoundingTime").item(0).getTextContent());
							Integer timeWithDay = Integer.parseInt(eWorkStamp.getElementsByTagName("timeWithDay").item(0).getTextContent());
							String locationCode = eWorkStamp.getElementsByTagName("locationCode").item(0).getTextContent();
							Integer stampSourceInfo = Integer.parseInt(eWorkStamp.getElementsByTagName("stampSourceInfo").item(0).getTextContent());
							
							WorkStamp workStamp1 = new WorkStamp(
									new TimeWithDayAttr(AfterRoundingTime),
									new TimeWithDayAttr(timeWithDay),
									new WorkLocationCD(locationCode),
									EnumAdaptor.valueOf(stampSourceInfo, StampSourceInfo.class));
							stamp = Optional.ofNullable(workStamp1);
							
						}
						Integer numberOfReflectionStamp = Integer.parseInt(eTimeActualStamp.getElementsByTagName("numberOfReflectionStamp").item(0).getTextContent());
						timeActualStamp.setPropertyTimeActualStamp(actualStamp, stamp, numberOfReflectionStamp);
						comeBackOp = Optional.of(timeActualStamp);
						
					}
					OutingTimeSheet outingTimeSheet = new OutingTimeSheet(
							new OutingFrameNo(outingFrameNo),
							goOutOp,
							outingTimeCalculation,
							outingTime,
							EnumAdaptor.valueOf(reasonForGoOut, GoingOutReason.class),
							comeBackOp);
					listOutingTimeSheet.add(outingTimeSheet);
					
				}
				
			}
			recordWorkInfoPubExport.setListOutingTimeSheet(listOutingTimeSheet);
			//短時間勤務時間帯
			List<ShortWorkingTimeSheet> listShortWorkingTimeSheet  = new ArrayList<>();
			
			Element elistShortWorkingTimeSheet = (Element) eElement.getElementsByTagName("listShortWorkingTimeSheet").item(0);
			NodeList nodeShortWorkingTimeSheet = elistShortWorkingTimeSheet.getElementsByTagName("ShortWorkingTimeSheet");
			for (int itr = 0; itr < nodeShortWorkingTimeSheet.getLength(); itr++) {
				Node node = nodeShortWorkingTimeSheet.item(itr);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					Integer shortWorkTimeFrameNo = Integer.parseInt(e.getElementsByTagName("shortWorkTimeFrameNo").item(0).getTextContent());
					Integer childCareAttr = Integer.parseInt(e.getElementsByTagName("childCareAttr").item(0).getTextContent());
					Integer startTime = Integer.parseInt(e.getElementsByTagName("startTime").item(0).getTextContent());
					Integer endTime = Integer.parseInt(e.getElementsByTagName("endTime").item(0).getTextContent());
					Integer deductionTime = Integer.parseInt(e.getElementsByTagName("deductionTime").item(0).getTextContent());
					Integer shortTime = Integer.parseInt(e.getElementsByTagName("shortTime").item(0).getTextContent());
					ShortWorkingTimeSheet shortWorkingTimeSheet = new ShortWorkingTimeSheet(
							new ShortWorkTimFrameNo(shortWorkTimeFrameNo),
							EnumAdaptor.valueOf(childCareAttr, ChildCareAttribute.class),
							new TimeWithDayAttr(startTime),
							new TimeWithDayAttr(endTime),
							new AttendanceTime(deductionTime),
							new AttendanceTime(shortTime));
					listShortWorkingTimeSheet.add(shortWorkingTimeSheet);

				}
			}
			recordWorkInfoPubExport.setListShortWorkingTimeSheet(listShortWorkingTimeSheet);
			
			//休憩時間帯
			List<BreakTimeSheet> listBreakTimeSheet = new ArrayList<>(); 
			
			Element elistBreakTimeSheet = (Element) eElement.getElementsByTagName("listBreakTimeSheet").item(0);
			NodeList nodeBreakTimeSheet = elistBreakTimeSheet.getElementsByTagName("BreakTimeSheet");
			for (int itr = 0; itr < nodeBreakTimeSheet.getLength(); itr++) {
				Node node = nodeBreakTimeSheet.item(itr);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					Integer breakFrameNo = Integer.parseInt(e.getElementsByTagName("breakFrameNo").item(0).getTextContent());
					Integer startTime = Integer.parseInt(e.getElementsByTagName("startTime").item(0).getTextContent());
					Integer endTime = Integer.parseInt(e.getElementsByTagName("endTime").item(0).getTextContent());
					Integer breakTime = Integer.parseInt(e.getElementsByTagName("breakTime").item(0).getTextContent());
					BreakTimeSheet b = new BreakTimeSheet(
							new BreakFrameNo(breakFrameNo),
							new TimeWithDayAttr(startTime),
							new TimeWithDayAttr(endTime));
					
					listBreakTimeSheet.add(b);

				}
			}
			recordWorkInfoPubExport.setListBreakTimeSheet(listBreakTimeSheet);
			//残業深夜時間
			Optional<TimeDivergenceWithCalculation> excessOverTimeWorkMidNightTime = Optional.empty();
			NodeList nodeExcessOverTimeWorkMidNightTime = eElement.getElementsByTagName("excessOverTimeWorkMidNightTime");
			Element eExcessOverTimeWorkMidNightTime = (Element) nodeExcessOverTimeWorkMidNightTime.item(0);
			Integer time = Integer.parseInt(eExcessOverTimeWorkMidNightTime.getElementsByTagName("time").item(0).getTextContent());
			Integer calcTime = Integer.parseInt(eExcessOverTimeWorkMidNightTime.getElementsByTagName("calcTime").item(0).getTextContent());
//			Integer divergenceTime = Integer.parseInt(eExcessOverTimeWorkMidNightTime.getElementsByTagName("divergenceTime").item(0).getTextContent());
			TimeDivergenceWithCalculation timeDivergenceWithCalculation = TimeDivergenceWithCalculation.createTimeWithCalculation(
					new AttendanceTime(time),
					new AttendanceTime(calcTime));
			if (eExcessOverTimeWorkMidNightTime != null) {
				excessOverTimeWorkMidNightTime = Optional.of(timeDivergenceWithCalculation);
			}
			recordWorkInfoPubExport.setExcessOverTimeWorkMidNightTime(excessOverTimeWorkMidNightTime);
			
			//法内休出深夜時間
			Optional<TimeDivergenceWithCalculation> insideTheLaw = Optional.empty();
			
			NodeList nodeInsideTheLaw = eElement.getElementsByTagName("insideTheLaw");
			Element eInsideTheLaw = (Element) nodeInsideTheLaw.item(0);
			Integer time1 = Integer.parseInt(eInsideTheLaw.getElementsByTagName("time").item(0).getTextContent());
			Integer calcTime1 = Integer.parseInt(eInsideTheLaw.getElementsByTagName("calcTime").item(0).getTextContent());
//			Integer divergenceTime = Integer.parseInt(eExcessOverTimeWorkMidNightTime.getElementsByTagName("divergenceTime").item(0).getTextContent());
			TimeDivergenceWithCalculation timeDivergenceWithCalculation1 = TimeDivergenceWithCalculation.createTimeWithCalculation(
					new AttendanceTime(time1),
					new AttendanceTime(calcTime1));
			if (eInsideTheLaw != null) {
				insideTheLaw = Optional.of(timeDivergenceWithCalculation1);
			}
			recordWorkInfoPubExport.setInsideTheLaw(insideTheLaw);
			//法外休出深夜時間
			Optional<TimeDivergenceWithCalculation> outrageous = Optional.empty();
			
			
			NodeList nodeOutrageous = eElement.getElementsByTagName("outrageous");
			Element eOutrageous = (Element) nodeOutrageous.item(0);
			Integer time2 = Integer.parseInt(eOutrageous.getElementsByTagName("time").item(0).getTextContent());
			Integer calcTime2 = Integer.parseInt(eOutrageous.getElementsByTagName("calcTime").item(0).getTextContent());
//			Integer divergenceTime = Integer.parseInt(eExcessOverTimeWorkMidNightTime.getElementsByTagName("divergenceTime").item(0).getTextContent());
			TimeDivergenceWithCalculation timeDivergenceWithCalculation2 = TimeDivergenceWithCalculation.createTimeWithCalculation(
					new AttendanceTime(time2),
					new AttendanceTime(calcTime2));
			if (eOutrageous != null) {
				outrageous = Optional.of(timeDivergenceWithCalculation2);
			}
			recordWorkInfoPubExport.setOutrageous(outrageous);
			//祝日休出深夜時間
			Optional<TimeDivergenceWithCalculation> publicHoliday = Optional.empty();
			
			NodeList nodePublicHoliday = eElement.getElementsByTagName("publicHoliday");
			Element ePublicHoliday = (Element) nodePublicHoliday.item(0);
			Integer time3 = Integer.parseInt(ePublicHoliday.getElementsByTagName("time").item(0).getTextContent());
			Integer calcTime3 = Integer.parseInt(ePublicHoliday.getElementsByTagName("calcTime").item(0).getTextContent());
//			Integer divergenceTime = Integer.parseInt(eExcessOverTimeWorkMidNightTime.getElementsByTagName("divergenceTime").item(0).getTextContent());
			TimeDivergenceWithCalculation timeDivergenceWithCalculation3 = TimeDivergenceWithCalculation.createTimeWithCalculation(
					new AttendanceTime(time3),
					new AttendanceTime(calcTime3));
			if (ePublicHoliday != null) {
				publicHoliday = Optional.of(timeDivergenceWithCalculation3);
			}
			recordWorkInfoPubExport.setPublicHoliday(publicHoliday);
			
			return recordWorkInfoPubExport;
		}catch (Exception e) {
			return null;
		}
	}
	
}
