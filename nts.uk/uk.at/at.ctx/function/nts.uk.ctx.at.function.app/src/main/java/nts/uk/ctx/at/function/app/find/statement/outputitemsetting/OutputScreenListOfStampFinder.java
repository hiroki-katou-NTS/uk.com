package nts.uk.ctx.at.function.app.find.statement.outputitemsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationName;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetListStampEmployeeService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.RetrieveNoStampCardRegisteredService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampInfoDisp;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author HieuLT
 *
 */
@Stateless
public class OutputScreenListOfStampFinder {

	@Inject
	private CompanyAdapter company;
	
	@Inject
	private ClosureService closureService;
	
	@Inject
	private StampCardRepository stampCardRepository;
	
	@Inject
	private StampRecordRepository stampRecordRepository;
	
	@Inject
	private  StampDakokuRepository  stampDakokuRepository;
	
	@Inject
	private EmployeeInformationAdapter employeeInformationAdapter;
	
	@Inject
	private WorkLocationRepository workLocationRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	// 起動する(khởi động)
	public OutputScreenListOfStampDto initScreen() {
			String employeeID = AppContexts.user().employeeId();
			GeneralDate ymd = GeneralDate.today();
			OutputScreenListOfStampDto result = new OutputScreenListOfStampDto();
		// [RQ622]会社IDから会社情報を取得する
		CompanyInfor companyInfo = company.getCurrentCompany().orElseGet(() -> {
			throw new RuntimeException("System Error: Company Info");
		});
		// 社員に対応する締め期間を取得する(Lấy closurePeriod ứng với employee)
		DatePeriod period = closureService.findClosurePeriod(employeeID, ymd);
		result.setCompanyCode(companyInfo.getCompanyCode());
		result.setCompanyName(companyInfo.getCompanyName());
		result.setStartDate(period.start());
		result.setEndDate(period.end());
		
		return result;
	}
	
	////社員別打刻一覧表を作成す Create a list of stamps for each employee
	public List<EmployeEngravingInfor> createListOfStampEmpQuery(DatePeriod datePerriod, List<String> listEmp) {
		List<EmployeEngravingInfor> result = new ArrayList<>();
		
		//1取得する(@Require, 社員ID, 年月日) :社員の打刻情報
		List<EmployeeStampInfo> listEmployeeStampInfo = new ArrayList<>();
		List<String> listWorkLocationCode = new  ArrayList<>();
		
		GetListStampEmployeeService.Require require = new RequireImpl(stampCardRepository, stampRecordRepository, stampDakokuRepository);
		for (GeneralDate date : datePerriod.datesBetween()) {
			for (String employeeId : listEmp) {
				Optional<EmployeeStampInfo> optEmployeeStampInfo = GetListStampEmployeeService.get(require, employeeId, date);
				if (optEmployeeStampInfo.isPresent()) {
					val employeeStampInfo = optEmployeeStampInfo.get();
					listEmployeeStampInfo.add(employeeStampInfo);
					
					// Get distinct WorkLocationCD
					val listStempInfoDisp = employeeStampInfo.getListStampInfoDisp();
					for (val stampInfoDisp : listStempInfoDisp) {
						val optStamp = stampInfoDisp.getStamp();
						if (optStamp.isPresent()) {
							val workLocationCD = optStamp.get().getRefActualResults().getWorkLocationCD();
							if (workLocationCD.isPresent())
								listWorkLocationCode.add(workLocationCD.get().v());
						}
					}
				}
			}
		}
		List<String> listEmployeeId = listEmployeeStampInfo.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		//2 <call> List＜社員の打刻情報＞.社員ID :List<社員情報>
		// <<Public>> 社員の情報を取得する
		List<EmployeeInformationImport> listEmpInfo = employeeInformationAdapter.getEmployeeInfo(new EmployeeInformationQueryDtoImport(listEmployeeId, datePerriod.end(), true, false, true, false, false, false));
		
		//3 get* List<社員の打刻情報>．勤務場所コード  : List< 勤務場所>	
		List<WorkLocation> listWorkLocation = workLocationRepository.findByCodes(AppContexts.user().companyId(), listWorkLocationCode);
		
		//4 get* List<社員の打刻情報>.就業時間帯コード : List< 就業時間帯>
		List<RefectActualResult> listRefectActualResult = listEmployeeStampInfo.stream()
						.flatMap(c -> {
							List<RefectActualResult> stampInfos = c.getListStampInfoDisp().stream()
									.map(t -> t.getStamp()).filter( s -> s.isPresent())
									.map( r -> r.get().getRefActualResults()).collect(Collectors.toList());
							return stampInfos.stream();
						})
						.collect(Collectors.toList());
		List<WorkTimeCode> listWorkTime = listRefectActualResult.stream()
				.map(c -> c.getWorkTimeCode()).filter(t -> t.isPresent())
				.distinct().map(g -> g.get()).collect(Collectors.toList());
		List<String> listWorkTimeCode = listWorkTime.stream().map(c -> c.v()).collect(Collectors.toList());
		List<WorkTimeSetting> listWorkTimeSetting = workTimeSettingRepository.getListWorkTimeSetByListCode(AppContexts.user().companyId(), listWorkTimeCode);
		
		for (val item : listEmployeeStampInfo) {
			EmployeEngravingInfor employeEngravingInfor = new EmployeEngravingInfor();
			EmployeeInformationImport empInfo = listEmpInfo.stream().filter(c -> c.getEmployeeId().equals(item.getEmployeeId())).findFirst().orElse(null);
			
			// StampInfoDisp
			for (val stampInfoDisp : item.getListStampInfoDisp()) {
				val workLocationCode = stampInfoDisp.getStamp().get().getRefActualResults().getWorkLocationCD().get();
				val optWorkLocation = listWorkLocation.stream().filter(c -> c.getWorkLocationCD().v().equals(workLocationCode.v())).findFirst();
				val workLocationName = (optWorkLocation.isPresent()) ? optWorkLocation.get().getWorkLocationName().v() : "";
				
				// Local Infor
				val localInfor = (optWorkLocation.isPresent()) ? optWorkLocation.get().getLatitude().v() + " " + optWorkLocation.get().getLongitude().v() : "";
				
				// Support Card
				val optSupportCard = stampInfoDisp.getStamp().get().getRefActualResults().getCardNumberSupport();
				
				// WorkTime Name
				val optWorkTimeCode = stampInfoDisp.getStamp().get().getRefActualResults().getWorkTimeCode();
				val optWorkTimeSetting = listWorkTimeSetting.stream().filter(c -> optWorkTimeCode.isPresent() && c.getWorktimeCode().v().equals(optWorkTimeCode.get().v())).findFirst();
				val workTimeName = (optWorkTimeSetting.isPresent()) ? optWorkTimeSetting.get().getWorkTimeDisplayName().getWorkTimeName().v() : "";
				
				// Overtime Hour & Late Night Time
				val optOvertimeDeclaration = stampInfoDisp.getStamp().get().getRefActualResults().getOvertimeDeclaration();			
				val overtimeHours = (optOvertimeDeclaration.isPresent()) ? optOvertimeDeclaration.get().getOverTime().v() : 0;
				val lateNightTime = (optOvertimeDeclaration.isPresent()) ? optOvertimeDeclaration.get().getOverLateNightTime().v() : 0;
				
				// Set data
				employeEngravingInfor.setWorkplaceCd((empInfo != null) ? empInfo.getWorkplace().getWorkplaceCode() : "");
				employeEngravingInfor.setWorkplaceName((empInfo != null) ? empInfo.getWorkplace().getWorkplaceName() : "");
				employeEngravingInfor.setEmployeeCode((empInfo != null) ? empInfo.getEmployeeCode() : "");
				employeEngravingInfor.setEmployeeName((empInfo != null) ? empInfo.getBusinessName() : "");
				employeEngravingInfor.setDateAndTime(stampInfoDisp.getStampDatetime().toString());
				employeEngravingInfor.setAttendanceAtr(stampInfoDisp.getStampAtr());
				employeEngravingInfor.setStampMeans(stampInfoDisp.getStamp().get().getRelieve().getStampMeans().name);
				employeEngravingInfor.setAuthcMethod(stampInfoDisp.getStamp().get().getRelieve().getAuthcMethod().name);
				employeEngravingInfor.setInstallPlace(workLocationName);
				employeEngravingInfor.setLocalInfor(localInfor);
				employeEngravingInfor.setCardNo(stampInfoDisp.getStampNumber().v());
				employeEngravingInfor.setSupportCard(optSupportCard.orElse(""));
				employeEngravingInfor.setWorkTimeDisplayName(workTimeName);
				employeEngravingInfor.setOvertimeHours(getTimeString(overtimeHours));
				employeEngravingInfor.setLateNightTime(getTimeString(lateNightTime));
				
				result.add(employeEngravingInfor);
			}
		}
			
		return result;                                                                                                                                                                                                                                                                     
	}
	
	public List<CardNoStampInfo> createCardNoStampQuery(DatePeriod datePerriod){
		//RetrieveNoStampCardRegisteredService
		//1取得する(@Require, 期間): 打刻情報リスト
		//打刻カード未登録の打刻データを取得する
		RetrieveNoStampCardRegisteredService.Require requireCardNo = new RequireCardNoIml(stampRecordRepository, stampDakokuRepository);
		List<StampInfoDisp> listStampInfoDisp = RetrieveNoStampCardRegisteredService.get(requireCardNo, datePerriod);
		List<RefectActualResult> listRefectActual = listStampInfoDisp.stream().map(c -> c.getStamp())
																	 .filter(t -> t.isPresent()).distinct()
																	 .map( g -> g.get().getRefActualResults()).collect(Collectors.toList());
		//勤務場所コードリスト = 打刻情報リスト:map $.打刻場所distinct	
		List<String> listWorkLocationCd =  listRefectActual.stream().map(c -> c.getWorkLocationCD())
														.filter(t ->t.isPresent())
														.map( g ->g.get().v()).collect(Collectors.toList());
		
		//2 打刻情報リスト: List< 勤務場所>
		List<WorkLocation> listWorkLocation = workLocationRepository.findByCodes(AppContexts.user().companyId() ,listWorkLocationCd);
		
		//就業時間帯コードリスト＝打刻情報リスト：map $.就業時間帯コード distinct
		List<WorkTimeCode> listWorkTime = listRefectActual.stream().map(c-> c.getWorkTimeCode())
														  .filter(t -> t.isPresent()).distinct().map( g -> g.get()).collect(Collectors.toList());
		List<String> listWorkTimeCode = listWorkTime.stream().map( c -> c.v()).collect(Collectors.toList());
		
		//3get 打刻．就業時間帯コード List<就業時間帯>
		List<WorkTimeSetting> listWorkTimeSetting = workTimeSettingRepository.getListWorkTimeSetByListCode(AppContexts.user().companyId(), listWorkTimeCode);	
		
		// 打刻情報リスト
		List<CardNoStampInfo> cardNoStampInfos = new ArrayList<>();
		for (StampInfoDisp stampInfoDisp : listStampInfoDisp) {
			CardNoStampInfo cardNoStampInfo = new CardNoStampInfo();
			cardNoStampInfo.setCardNo(stampInfoDisp.getStampNumber().toString());
			
			// Chuẩn bị data
			String stampMeans = "";
			String authcMethod = "";
		
			String localInfor = "";
			String supportCard = "";
			String workTimeDisplayName = "";
			Integer overtimeHours = 0;
			Integer lateNightTime = 0;
			String workLocationName = "";
			if (stampInfoDisp.getStamp().isPresent()) {
			
				stampMeans = stampInfoDisp.getStamp().get().getRelieve().getStampMeans().name;
				authcMethod = stampInfoDisp.getStamp().get().getRelieve().getAuthcMethod().name;
				
				val refActualResults = stampInfoDisp.getStamp().get().getRefActualResults();
				val workLocationCD = refActualResults.getWorkLocationCD();
				
				if (workLocationCD.isPresent()) {
					val optWorkLocation = listWorkLocation.stream().filter(c ->c.getWorkLocationCD().v().equals(workLocationCD.get().v())).findFirst();
					workLocationName = (optWorkLocation.isPresent()) ? optWorkLocation.get().getWorkLocationName().v() : "";
				}
				
				val locationInfo = stampInfoDisp.getStamp().get().getLocationInfor();
				if (locationInfo.isPresent()) {
					val positionInfo = locationInfo.get().getPositionInfor();
					localInfor = positionInfo.getLatitude() + " " + positionInfo.getLongitude();
				}
				
				supportCard = refActualResults.getCardNumberSupport().get();
				
				val workTimeCode = refActualResults.getWorkTimeCode();
				if (workTimeCode.isPresent()) {
					workTimeDisplayName = listWorkTimeSetting.stream().filter(c -> c.getWorktimeCode().v().equals(workTimeCode.get().v()))
															 .map(c -> c.getWorkTimeDisplayName().getWorkTimeName().v()).findFirst().get();
				}
				
				val overtimeDeclaration = refActualResults.getOvertimeDeclaration();
				if (overtimeDeclaration.isPresent()) {
					overtimeHours = overtimeDeclaration.get().getOverTime().v();
					lateNightTime = overtimeDeclaration.get().getOverLateNightTime().v();
				}
			}
			
			// Set data
			cardNoStampInfo.setCardNo(stampInfoDisp.getStampNumber().toString());
			cardNoStampInfo.setDateAndTime(stampInfoDisp.getStampDatetime().toString());
			cardNoStampInfo.setAttendanceAtr(stampInfoDisp.getStampAtr());
			cardNoStampInfo.setStampMeans(stampMeans);
			cardNoStampInfo.setAuthcMethod(authcMethod);
			cardNoStampInfo.setInstallPlace(workLocationName);
			cardNoStampInfo.setLocalInfor(localInfor);
			cardNoStampInfo.setSupportCard(supportCard);
			cardNoStampInfo.setWorkTimeDisplayName(workTimeDisplayName);
			cardNoStampInfo.setOvertimeHours(getTimeString(overtimeHours));
			cardNoStampInfo.setLateNightTime(getTimeString(lateNightTime));
			cardNoStampInfos.add(cardNoStampInfo);
		}
				
		return cardNoStampInfos;
	}
	
	public String getTimeString(Integer t) {
		int hours = t / 60; 
		int minutes = t % 60;
		return String.format("%02d:%02d", hours, minutes);
	}
	
	
	@AllArgsConstructor
	private static class RequireImpl implements GetListStampEmployeeService.Require{
		@Inject
		private StampCardRepository stampCardRepository;
		@Inject
		private StampRecordRepository stampRecordRepository;
		@Inject
		private  StampDakokuRepository  stampDakokuRepository;
		@Override
		public List<StampCard> getListStampCard(String sid) {
			return stampCardRepository.getListStampCard(sid);
		}
		@Override
		public List<StampRecord> getStampRecord(List<StampNumber> stampNumbers, GeneralDate stampDate) {
			return stampRecordRepository.get(stampNumbers, stampDate);
		}
		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate stampDateTime) {
			return stampDakokuRepository.get(stampNumbers, stampDateTime);
		}
		
		
	}
	@AllArgsConstructor
	private static class RequireCardNoIml implements RetrieveNoStampCardRegisteredService.Require{
		
		@Inject
		private StampRecordRepository stampRecordRepo;
		@Inject
		private  StampDakokuRepository  stampDakokuRepo;
		@Override
		public List<StampRecord> getStempRcNotResgistNumber(DatePeriod period) {	
			
			return stampRecordRepo.getStempRcNotResgistNumber(period);
			
		}

		@Override
		public List<Stamp> getStempRcNotResgistNumberStamp(DatePeriod period) {	
			return stampDakokuRepo.getStempRcNotResgistNumber(period);
		}	
	}
	
}
