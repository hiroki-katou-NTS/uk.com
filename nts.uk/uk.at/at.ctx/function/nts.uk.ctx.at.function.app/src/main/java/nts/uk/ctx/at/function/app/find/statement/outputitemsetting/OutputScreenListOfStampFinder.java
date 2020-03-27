package nts.uk.ctx.at.function.app.find.statement.outputitemsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.record.dom.stamp.WorkLocationCd;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
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
		GetListStampEmployeeService.Require require = new RequireImpl(stampCardRepository, stampRecordRepository,
				stampDakokuRepository);
		Optional<EmployeeStampInfo> employeeStampInfo;
		for (GeneralDate date : datePerriod.datesBetween()) {
			for (String employeeId : listEmp) {
				employeeStampInfo = GetListStampEmployeeService.get(require, employeeId, date);
				if (employeeStampInfo.isPresent()) {
					listEmployeeStampInfo.add(employeeStampInfo.get());
				}
			}
		}
		List<String> newListEmp1 = listEmployeeStampInfo.stream().map(c->c.getEmployeeId()).collect(Collectors.toList());
		//2 <call> List＜社員の打刻情報＞.社員ID :List<社員情報>
		// <<Public>> 社員の情報を取得する
					List<EmployeeInformationImport> listEmpInfo = employeeInformationAdapter
							.getEmployeeInfo(new EmployeeInformationQueryDtoImport(newListEmp1, datePerriod.end(), false, false, true,
									false, false, false));
					
		List<String> listWorkLocationCode = listEmpInfo.stream().map(c ->c.getWorkplace().getWorkplaceCode()).distinct().collect(Collectors.toList());		
					
		//3 get* List<社員の打刻情報>．勤務場所コード  : List< 勤務場所>	
		List<WorkLocation> listWorkLocation = workLocationRepository.findByListEmp(listWorkLocationCode);
		//4get* List<社員の打刻情報>.就業時間帯コード : List< 就業時間帯>
		//
		List<RefectActualResult> listRefectActualResult = listEmployeeStampInfo.stream()
						.flatMap(c -> {	       
							List<RefectActualResult> stampInfos = c.getListStampInfoDisp().stream()
									.map(t -> t.getStamp())
									.filter( s -> s.isPresent())
									.map( r -> r.get().getRefActualResults())
									.collect(Collectors.toList());
							return stampInfos.stream();
						})
						.collect(Collectors.toList());	
		List<WorkTimeCode> listWorkTime = listRefectActualResult.stream()
				.map(c-> c.getWorkTimeCode())
				.filter(t -> t.isPresent())
				.distinct()
				.map( g -> g.get())
				.collect(Collectors.toList());
		List<String> listWorkTimeCode = listWorkTime.stream().map( c -> c.v()).collect(Collectors.toList());
		List<WorkTimeSetting> listWorkTimeSetting = workTimeSettingRepository.getListWorkTimeSetByListCode(AppContexts.user().companyId(), listWorkTimeCode);		
		return null;
	}
	
	
	public List<CardNoStampInfo> createCardNoStampQuery(DatePeriod datePerriod){
		//RetrieveNoStampCardRegisteredService
		//1取得する(@Require, 期間): 打刻情報リスト
		//打刻カード未登録の打刻データを取得する
		RetrieveNoStampCardRegisteredService.Require requireCardNo = new RequireCardNoIml(stampRecordRepository, stampDakokuRepository);
		List<StampInfoDisp> listStampInfoDisp = RetrieveNoStampCardRegisteredService.get(requireCardNo, datePerriod);
		List<RefectActualResult> listRefectActual = listStampInfoDisp.stream().map(c -> c.getStamp())
																		      .filter(t -> t.isPresent())
																		      .distinct()
																		      .map( g -> g.get().getRefActualResults())
																	      .collect(Collectors.toList());
		//勤務場所コードリスト = 打刻情報リスト:map $.打刻場所distinct	
		List<WorkLocationCD> listWorkLocationCd =  listRefectActual.stream().
														map(c -> c.getWorkLocationCD())
														.filter(t ->t.isPresent())
														.map( g ->g.get())
														.collect(Collectors.toList());
		List<String> lstWorkLocationCd = listWorkLocationCd.stream().map( c -> c.v()).collect(Collectors.toList()); 
		//2 打刻情報リスト: List< 勤務場所>
		List<WorkLocation> listWorkLocation = workLocationRepository.findByListEmp(lstWorkLocationCd);
		//就業時間帯コードリスト＝打刻情報リスト：map $.就業時間帯コード distinct
		
		List<WorkTimeCode> listWorkTime = listRefectActual.stream()
				.map(c-> c.getWorkTimeCode())
				.filter(t -> t.isPresent())
				.distinct()
				.map( g -> g.get())
				.collect(Collectors.toList());
		List<String> listWorkTimeCode = listWorkTime.stream().map( c -> c.v()).collect(Collectors.toList());
		//3get 打刻．就業時間帯コード List<就業時間帯>
		List<WorkTimeSetting> listWorkTimeSetting = workTimeSettingRepository.getListWorkTimeSetByListCode(AppContexts.user().companyId(), listWorkTimeCode);	
		
		
		return null;
		
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
			// TODO Auto-generated method stub
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
