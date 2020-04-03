package nts.uk.query.app.employee.ccg029;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.bs.employee.pub.employee.EmpInfo614;
import nts.uk.ctx.bs.employee.pub.employee.EmpInfo614Param;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.pereg.dom.filemanagement.services.PersonFileManagementDto;
import nts.uk.ctx.pereg.dom.filemanagement.services.PersonFileManagementService;
import nts.uk.query.model.employee.EmployeeAuthAdapter;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class Ccg029Employeefinder {

	@Inject
	private SyEmployeePub syEmployeePub;

	/** The emp auth adapter. */
	@Inject
	private EmployeeAuthAdapter empAuthAdapter;
	
	@Inject
	private EmployeeInformationRepository employeeInformationRepo;
	
	@Inject 
	private PersonFileManagementService personFileManagementService;
	/**
	 * 社員のキーワード検索
	 */
	public List<Ccg029EmployeeInforDto> employeeKeywordSearch(Ccg029QueryParam input) {
		if(input.keyword == null || input.keyword.equals("")) {
			throw new BusinessException("Msg_1571");
		}else {
			String roleId = AppContexts.user().roles().forPersonnel();
			String cId = AppContexts.user().companyId();
			return this.searchEmpByKey(roleId, cId, input);
		}
	}

	/**
	 * 社員コード、氏名、カナ氏名の社員キーワード検索
	 */
	private List<Ccg029EmployeeInforDto> searchEmpByKey(String roleId, String cId, Ccg029QueryParam input) {
		EmpInfo614Param param614 = new EmpInfo614Param(input.baseDate, cId, input.keyword, input.includePreEmployee, input.includeRetirement, input.includeAbsence, input.includeClosed, input.includeTransferEmployee, input.includeAcceptanceTransferEmployee);
		//[RQ614]社員CD、ビジネスネーム、カナから検索キーワードをもとに社員IDを取得する
		List<EmpInfo614> listEmpBeforeFillter = syEmployeePub.findEmpByKeyWordsListSid(param614);
		if(listEmpBeforeFillter.isEmpty()) {
			return new ArrayList<>();
		}
		
		int roleType = 8;
		if(input.getSystemType() == 0 || input.getSystemType() == 2 || input.getSystemType() == 3) {
			roleType = 8;
		}else if(input.getSystemType() == 1){
			roleType = 3;
		}
		List<String> sID = listEmpBeforeFillter.stream().map(c->c.getEmployeeId()).collect(Collectors.toList());
		
		//アルゴリズム [社員リストを参照範囲で絞り込む] を実行する
		List<String> narrowedSids = empAuthAdapter.narrowEmpListByReferenceRange(sID, roleType, input.getBaseDate());
		
		EmployeeInformationQuery employeeInformationQuery = EmployeeInformationQuery.builder()
				.employeeIds(narrowedSids)
				.referenceDate(input.getBaseDate())
				.toGetWorkplace(true)
				.toGetDepartment(false)
				.toGetPosition(input.getPosition)
				.toGetEmployment(input.getEmployment)
				.toGetClassification(false)
				.toGetEmploymentCls(false).build();
		//<<Public>> 社員の情報を取得する
		List<EmployeeInformation> employeeInformation = employeeInformationRepo.find(employeeInformationQuery); 
		Map<String, EmployeeInformation> employeeInformationMap = employeeInformation.stream().collect(Collectors.toMap(EmployeeInformation::getEmployeeId, c->c));
		
		List<EmpInfo614> listEmpAfterFillter = listEmpBeforeFillter.stream().filter(c -> narrowedSids.contains(c.getEmployeeId())).collect(Collectors.toList());
		
		List<Ccg029EmployeeInforDto> result = new ArrayList<>(); 
		if(input.getPersonalFileManagement) {
			List<String> pIDs = listEmpAfterFillter.stream().map(c->c.getPersonalId()).collect(Collectors.toList());
			//[RQ624]個人IDから個人ファイル管理を取得する
			List<PersonFileManagementDto> personFileManagements = personFileManagementService.getPersonalFileManagementFromPID(pIDs);
			Map<String, PersonFileManagementDto> personFileManagementsMap = personFileManagements.stream().collect(Collectors.toMap(PersonFileManagementDto::getPId, c->c));
			for (EmpInfo614 empFillter : listEmpAfterFillter) {
				EmployeeInformation infor = employeeInformationMap.get(empFillter.getEmployeeId());
				Ccg029EmployeeInforDto emp = new Ccg029EmployeeInforDto(
						empFillter.getPersonalId()
						,empFillter.getEmployeeId()
						,empFillter.getEmployeeCode()
						,infor.getBusinessName()
						,infor.getBusinessNameKana());
				
				if(infor.getWorkplace().isPresent()) {
					emp.setWorkplace(infor.getWorkplace().get());
				}
				if(infor.getDepartment().isPresent()) {
					emp.setDepartment(infor.getDepartment().get());
				}
				if(input.getGetEmployment() && infor.getEmployment().isPresent()) {
					emp.setEmployment(infor.getEmployment().get());
				}
				if(input.getGetPosition() && infor.getPosition().isPresent()) {
					emp.setPosition(infor.getPosition().get());
				}
				emp.setPersonalFileManagement(personFileManagementsMap.getOrDefault(empFillter.getPersonalId(), new PersonFileManagementDto(null, Optional.empty(), Optional.empty(), new ArrayList<>())));
				result.add(emp);
			}
		}else {
			for (EmpInfo614 empFillter : listEmpAfterFillter) {
				EmployeeInformation infor = employeeInformationMap.get(empFillter.getEmployeeId());
				Ccg029EmployeeInforDto emp = new Ccg029EmployeeInforDto(
						empFillter.getPersonalId()
						,empFillter.getEmployeeId()
						,empFillter.getEmployeeCode()
						,infor.getBusinessName()
						,infor.getBusinessNameKana());
				
				if(infor.getWorkplace().isPresent()) {
					emp.setWorkplace(infor.getWorkplace().get());
				}
				if(input.getEmployment && infor.getEmployment().isPresent()) {
					emp.setEmployment(infor.getEmployment().get());
				}
				if(input.getGetPosition() && infor.getPosition().isPresent()) {
					emp.setPosition(infor.getPosition().get());
				}
				result.add(emp);
			}
		}
		
		return result;
	}
	
}
