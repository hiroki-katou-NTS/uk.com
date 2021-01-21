package nts.uk.file.at.app.export.statement.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.find.statement.outputitemsetting.CardNoStampInfo;
import nts.uk.ctx.at.function.app.find.statement.outputitemsetting.EmployeEngravingInfor;
import nts.uk.ctx.at.function.app.find.statement.outputitemsetting.OutputScreenListOfStampFinder;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.i18n.TextResource;
/**
 * 
 * @author HieuLT
 *
 */
@Stateless
public class OutputListOfStampExportService extends ExportService<ConditionListOfStampQueryDto> {

	@Inject
	private OutputConditionListOfStampGenerator generator;

	@Inject
	private OutputScreenListOfStampFinder finder;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<ConditionListOfStampQueryDto> context) {
		// OutputConditionListOfStampQuery query = context.getQuery();
		ConditionListOfStampQueryDto query1 = context.getQuery();
		GeneralDate startDate = GeneralDate.fromString(query1.startDate, "yyyy/MM/dd");
		GeneralDate endDate = GeneralDate.fromString(query1.endDate, "yyyy/MM/dd");
		String datePeriod = query1.startDate + "～" + query1.endDate;
		DatePeriod newDatePerioD = new DatePeriod(startDate, endDate);
		// Map
		OutputConditionListOfStampQuery query = null;
		//selectedIdProcessSelect == 1 THCardNo : 2 == listEmployee
		if (query1.selectedIdProcessSelect == 1) {
			List<CardNoStampInfo> cardNoStampInfos = finder.createCardNoStampQuery(newDatePerioD);
			if(cardNoStampInfos.isEmpty()){
				throw new BusinessException("Msg_1617");
			}
			query = convertToDataSource(true, cardNoStampInfos, datePeriod);
			this.generator.generate(context.getGeneratorContext(), query, true);
		} else {
			List<EmployeEngravingInfor> employeEngravingInfors = finder.createListOfStampEmpQuery(newDatePerioD, query1.lstEmployee);
			if(employeEngravingInfors.isEmpty()){
				throw new BusinessException("Msg_1617");
			}
			query = convertToDataSource(false, employeEngravingInfors, datePeriod);
			this.generator.generate(context.getGeneratorContext(), query, false);
			
		}
	}

	@SuppressWarnings("unchecked")
	private <T> OutputConditionListOfStampQuery convertToDataSource(boolean isCardNo, List<T> data, String datePeriod) {
		List<EmployeeInfor> employeeList = new ArrayList<>();
		OutputConditionListOfStampQuery query = new OutputConditionListOfStampQuery();
		String companyName = company.getCurrentCompany()
				.orElseThrow(() -> new RuntimeException("Company is not found!!!!")).getCompanyName();
		StampHeader header = new StampHeader();
		header.setCompanyName(companyName);
		header.setDatePeriodHead(datePeriod);
		query.setHeader(header);
		if (isCardNo) {
			List<CardNoStampInfo> cardNoStampInfos = (List<CardNoStampInfo>) data;
			Map<String, List<CardNoStampInfo>> cardNoStampInfoMap = cardNoStampInfos.stream()
					.collect(Collectors.groupingBy(CardNoStampInfo::getCardNo));
			cardNoStampInfoMap.forEach((key, value) -> {
				EmployeeInfor employeeInfor = new EmployeeInfor();
				employeeInfor.setWorkplace(TextResource.localize("KDP011_32") + "　－");
				employeeInfor.setEmployee(TextResource.localize("KDP011_33") + "　－");
				employeeInfor.setCardNo(TextResource.localize("KDP011_34") + "　" + key);
				List<StampList> stampLists = new ArrayList<>();
				value.forEach(i -> {
					StampList stampList = new StampList();
					stampList.setDate(i.getDateAndTime());
					stampList.setTime(i.getDateAndTime());
					stampList.setClassification(i.getAttendanceAtr());
					stampList.setMean(i.getStampMeans());
					stampList.setMethod(i.getAuthcMethod());
					stampList.setInsLocation(i.getInstallPlace());
					stampList.setLocationInfor(i.getLocalInfor());
					stampList.setAddress(i.isAddress());
					stampList.setSupportCard(i.getSupportCard());
					stampList.setWorkingHour(i.getWorkTimeDisplayName());
					stampList.setOvertimeHour(i.getOvertimeHours());
					stampList.setNightTime(i.getLateNightTime());
					stampLists.add(stampList);
				});
				employeeInfor.setStampList(stampLists);
				employeeList.add(employeeInfor);
			});
		} else {
			List<EmployeEngravingInfor> employeEngravingInfors = (List<EmployeEngravingInfor>) data;
			Map<String, List<EmployeEngravingInfor>> employeEngravingInforMap = employeEngravingInfors.stream()
					.collect(Collectors.groupingBy(EmployeEngravingInfor::getEmployeeCode));
			employeEngravingInforMap.forEach((key, value) -> {
				EmployeeInfor employeeInfor = new EmployeeInfor();
				employeeInfor.setWorkplace(TextResource.localize("KDP011_32") + "　" + value.get(0).getWorkplaceCd()
						+ "　" + value.get(0).getWorkplaceName());
				employeeInfor.setEmployee(TextResource.localize("KDP011_33") + "　" + value.get(0).getEmployeeCode()
						+ "　" + value.get(0).getEmployeeName());
				
				List<StampList> stampLists = new ArrayList<>();
				value.forEach(i -> {
					StampList stampList = new StampList();
					stampList.setDate(i.getDateAndTime());
					stampList.setTime(i.getDateAndTime());
					stampList.setClassification(i.getAttendanceAtr());
					stampList.setMean(i.getStampMeans());
					stampList.setMethod(i.getAuthcMethod());
					stampList.setCardNo(TextResource.localize("KDP011_34") + "　" + i.getCardNo());
					stampList.setInsLocation(i.getInstallPlace());
					stampList.setLocationInfor(i.getLocalInfor());
					stampList.setAddress(i.isAddress());
					stampList.setSupportCard(i.getSupportCard());
					stampList.setWorkingHour(i.getWorkTimeDisplayName());
					stampList.setOvertimeHour(i.getOvertimeHours());
					stampList.setNightTime(i.getLateNightTime());
					stampLists.add(stampList);
				});
				employeeInfor.setStampList(stampLists);
				employeeList.add(employeeInfor);
			});
		}
		query.setEmployeeList(employeeList);
		return query;
	}
}
