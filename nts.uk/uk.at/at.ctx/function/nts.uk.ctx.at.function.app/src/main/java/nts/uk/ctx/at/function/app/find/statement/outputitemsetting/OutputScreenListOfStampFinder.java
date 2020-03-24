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
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetListStampEmployeeService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
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
	private GetListStampEmployeeService getListStampEmployeeService;
	
	@Inject
	private EmployeeInformationAdapter employeeInformationAdapter;
	
	@Inject
	private WorkLocationRepository workLocationRepository;

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
	@SuppressWarnings("static-access")
	public List<EmployeEngravingInfor> createListOfStampEmpQuery(DatePeriod datePerriod, List<String> listEmp) {
		//1取得する(@Require, 社員ID, 年月日) :社員の打刻情報
		List<EmployeeStampInfo> listEmployeeStampInfo = new ArrayList<>();
		GetListStampEmployeeService.Require require = new RequireImpl(stampCardRepository, stampRecordRepository,
				stampDakokuRepository);
		Optional<EmployeeStampInfo> employeeStampInfo;
		for (GeneralDate date : datePerriod.datesBetween()) {
			for (String employeeId : listEmp) {
				employeeStampInfo = getListStampEmployeeService.get(require, employeeId, date);
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
		//ListString>		
				
				
					

		return null;
	}
	
	
	public List<CardNoStampInfo> createCardNoStampQuery(DatePeriod datePerriod){
		RetrieveNoStampCardRegisteredService
		//1取得する(@Require, 期間): 打刻情報リスト
		//打刻カード未登録の打刻データを取得する
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
			// TODO Auto-generated method stub
			return stampRecordRepository.get(stampNumbers, stampDate);
		}
		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate stampDateTime) {
			// TODO Auto-generated method stub
			return stampDakokuRepository.get(stampNumbers, stampDateTime);
		}
		
		
	}
	@AllArgsConstructor
	private static class RequireCardNoIml implements RetrieveNoStampCardRegisteredService{
		
	}
	
}
