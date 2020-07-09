package nts.uk.screen.at.app.query.kmp.kmp001.b;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.B：カードNO指定による個人の登録.メニュー別OCD.カードNOから抽出した社員情報を取得する
 * 
 * @author chungnt
 *
 */
@Stateless
public class EmployeeInformationFromCardNo {

	@Inject
	private StampCardRepository stampCardRepo;
	
	@Inject
	private EmployeeInformationRepository employeeInformation;
	
	public EmployeeInformationFromCardNoDto getEmployee(String cardNumber) {
		String contractCd = AppContexts.user().contractCode();
		List<StampCard> stampCards = new ArrayList<>();	
		GeneralDate generalDate = GeneralDate.today();
		
		Optional<StampCard> stampCard = stampCardRepo.getByCardNoAndContractCode(cardNumber, contractCd);
		
		if(!stampCard.isPresent()) {
			throw new RuntimeException("Not found");
		}
		
		stampCards.add(stampCard.get());
		List<String> empIds = stampCards.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());
		
		List<EmployeeInformation> empInfoList = employeeInformation.find(EmployeeInformationQuery.builder()
				.employeeIds(empIds)
				.referenceDate(generalDate)
				.toGetWorkplace(false)
				.toGetDepartment(false)
				.toGetPosition(false)
				.toGetEmployment(false)
				.toGetClassification(false)
				.toGetEmploymentCls(false)
				.build());
		
		if (empInfoList.isEmpty()) {
			throw new RuntimeException("Not found");
		}
		
//		return new EmployeeInformationFromCardNoDto(stampCards.get(0).getStampNumber() == null ? "" : stampCards.get(0).getStampNumber().v(),
//				empInfoList.get(0).getEmployeeCode() == null ? "" : empInfoList.get(0).getEmployeeCode(),
//				empInfoList.get(0).getBusinessName() == null ? "" : empInfoList.get(0).getBusinessName(),
//				empInfoList.get(0).getEmployeeId() == null ? "" : empInfoList.get(0).getEmployeeId());
		
		EmployeeInformationFromCardNoDto employees = new EmployeeInformationFromCardNoDto(
				stampCards.get(0).getStampNumber() == null ? "" : stampCards.get(0).getStampNumber().v(),
				empInfoList.get(0).getEmployeeCode() == null ? "" : empInfoList.get(0).getEmployeeCode(),
				empInfoList.get(0).getBusinessName() == null ? "" : empInfoList.get(0).getBusinessName(),
				empInfoList.get(0).getEmployeeId() == null ? "" : empInfoList.get(0).getEmployeeId());
		
		return employees;
	}
	
	public List<EmployeeInformationFromCardNoDto> getAll() {
		String contractCd = AppContexts.user().contractCode();
		List<StampCard> stampCards = stampCardRepo.getLstStampCardByContractCode(contractCd);
		GeneralDate generalDate = GeneralDate.today();
		
		if (stampCards.isEmpty()) {
			throw new RuntimeException("Not found");
		}
		
		List<String> empIds = stampCards.stream().map(m -> m.getEmployeeId()).distinct().collect(Collectors.toList());

		List<EmployeeInformation> empInfoList = employeeInformation.find(EmployeeInformationQuery.builder()
				.employeeIds(empIds)
				.referenceDate(generalDate)
				.toGetWorkplace(false)
				.toGetDepartment(false)
				.toGetPosition(false)
				.toGetEmployment(false)
				.toGetClassification(false)
				.toGetEmploymentCls(false)
				.build());

		if (empInfoList.isEmpty()) {
			throw new RuntimeException("Not found");
		}
		
		List<EmployeeInformationFromCardNoDto> employees = new ArrayList<>();
		String empCode = "";
		String empName = "";
		String empId = "";
		
		synchronized(empInfoList) {
			for (int i = 0; i <= empInfoList.size() - 2 ; i++) {
				
				if (empInfoList.get(i).getEmployeeCode() != null) {
					empCode = empInfoList.get(i).getEmployeeCode();
				}
				
				if (empInfoList.get(i).getBusinessName() != null) {
					empName = empInfoList.get(i).getBusinessName();
				}
				
				if (empInfoList.get(i).getEmployeeId() != null) {
					empId = empInfoList.get(i).getEmployeeId();
				}
				employees.add(new EmployeeInformationFromCardNoDto(stampCards.get(i).getStampNumber() == null ? "" : stampCards.get(i).getStampNumber().v(),
		//					empInfoList.get(i).getEmployeeCode() == null ? "" : empInfoList.get(i).getEmployeeCode(),
		//					empInfoList.get(i).getBusinessName() == null ? "" : empInfoList.get(i).getBusinessName(),
		//					empInfoList.get(i).getEmployeeId() == null ? "" : empInfoList.get(i).getEmployeeId()));
						empCode,
						empName,
						empId));
			}
		}
		
		return employees;
	}
}
