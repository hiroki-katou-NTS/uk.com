package nts.uk.ctx.bs.employee.app.find.person.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.bs.employee.app.find.employee.EmployeeDto;
import nts.uk.ctx.bs.employee.app.find.person.info.PersonDto;
import nts.uk.ctx.bs.employee.app.find.person.info.PersonFinder;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemCurrentJobPosDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemEmpInfoItemDataDto;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCare;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCareRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTax;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTaxRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsurance;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsuranceRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMainRepository;
import nts.uk.ctx.bs.employee.dom.person.ParamForGetPerItem;
import nts.uk.ctx.bs.employee.dom.person.PerInfoCtgDomainService;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

/**
 * get person information category and it's children
 * 
 * @author xuan vinh
 *
 */
@Stateless
public class EmpPerInfoCategoryFinder {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private PerInfoCtgDomainService perInfoCtgDomainService;
	@Inject
	private SubJobPosRepository subJobPosRepository;

	@Inject
	private IncomeTaxRepository incomeTaxRepository;

	@Inject
	private FamilySocialInsuranceRepository familySocialInsuranceRepository;

	@Inject
	private FamilyCareRepository familyCareRepository;

	@Inject
	private EmInfoCtgDataRepository emInfoCtgDataRepository;
	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	@Inject
	private EmployeeRepository employeeRepository;
	
	@Inject
	private PersonFinder personFinder;
	
	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepository;
	
	@Inject
	private JobTitleMainRepository jobTitleMainRepository;

	/**
	 * get person ctg infor and list of item children
	 * 
	 * @param ctgId
	 * @return EmpPerCtgInfoDto
	 */
	public EmpPerCtgInfoDto getCtgAndItemByCtgId(String ctgId) {
		val perCtgInfo = perInfoCtgRepositoty.getPerInfoCategory(ctgId, AppContexts.user().contractCode()).get();
		val lstPerItemDef = pernfoItemDefRep.getPerInfoItemByCtgId(ctgId, AppContexts.user().companyId(),
				AppContexts.user().contractCode());
		return EmpPerCtgInfoDto.createObjectFromDomain(perCtgInfo, lstPerItemDef);
	}

	/**
	 * person ctg infor and list of item children by parent
	 * 
	 * @param employeeId
	 * @param ctgId
	 * @param parentInfoId
	 * @return EmpPerCtgInfoDto
	 */
	public EmpPerCtgInfoDto getCtgAndItemByParent(String employeeId, String ctgId, String parentInfoId) {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		val perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		if (perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO)
			return EmpPerCtgInfoDto.createObjectFromDomain(perInfoCtg);
		else {
			List<PersonInfoItemDefinition> lstPerInfoItemDef = perInfoCtgDomainService
					.getPerItemDef(new ParamForGetPerItem(perInfoCtg, parentInfoId, roleId == null ? "" : roleId,
							companyId, contractCode, loginEmpId.equals(employeeId)));			
			EmpPerCtgInfoDto empPerCtgInfoDto = new EmpPerCtgInfoDto();
			empPerCtgInfoDto = EmpPerCtgInfoDto.createObjectFromDomain(perInfoCtg, lstPerInfoItemDef);
			setEmployeeCtgItem(empPerCtgInfoDto, perInfoCtg, parentInfoId);
			return empPerCtgInfoDto;
		}
	}
	
	/**
	 * get person information category and it's children
	 * @param ctgId
	 * @return
	 */
	public List<PersonInfoCategory> getCtgAndChildren(String ctgId){
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		List<PersonInfoCategory> lstPerInfoCtg = perInfoCtgRepositoty
				.getPerInfoCtgByParentCdWithOrder(perInfoCtg.getCategoryParentCode().v(), contractCode, true);
		lstPerInfoCtg.add(perInfoCtg);
		return lstPerInfoCtg;
	}

	
	/**
	 * hien thi mot thong tin trong danh sach
	 * 
	 * @param employeeId
	 * @param ctgId
	 * @param parentInfoId
	 * @return
	 */
	public EmpPerCtgInfoDto getItemInCtgAndChildrenList(String employeeId, String ctgId, String parentInfoId) {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		EmployeeDto employee = EmployeeDto.fromDomain(employeeRepository.findBySid(companyId, employeeId).get());
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		List<PersonInfoItemDefinition> lstPerInfoItemDef = perInfoCtgDomainService
				.getPerInfoItemDefWithAuth(new ParamForGetPerItem(perInfoCtg, parentInfoId,
						roleId == null ? "" : roleId, companyId, contractCode, loginEmpId.equals(employeeId)));
		EmpPerCtgInfoDto empPerCtgInfoDto = EmpPerCtgInfoDto.createObjectFromDomain(perInfoCtg, lstPerInfoItemDef);
		if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {// employee
			if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {// fixed
				setEmployeeCtgItem(empPerCtgInfoDto, employee, perInfoCtg, parentInfoId);
			} else {// optional
				setCtgItemOptionDto(empPerCtgInfoDto, parentInfoId, true);
			}
		} else {// person
			if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {// fixed

			} else {// optional

			}
		}
		return null;
	}

	/**
	 * set category item optional
	 * 
	 * @param sId
	 * @param ctgId
	 * @return CtgItemOptionalDto
	 */
	private void setCtgItemOptionDto(EmpPerCtgInfoDto empPerCtgInfoDto, String recordId, boolean isSingleList) {
		CtgItemOptionalDto ctgItemOptionalDto = new CtgItemOptionalDto();
		List<ItemEmpInfoItemDataDto> lstCtgItemOptionalDto = empInfoItemDataRepository
				.getAllInfoItemByRecordId(recordId).stream()
				.map(x -> new ItemEmpInfoItemDataDto(x.getPerInfoDefId(), x.getRecordId(), x.getPerInfoCtgId(),
						x.getItemName(), x.getIsRequired().value, x.getDataState().getDataStateType().value,
						x.getDataState().getStringValue(), x.getDataState().getNumberValue(),
						x.getDataState().getDateValue()))
				.collect(Collectors.toList());
		if(isSingleList){
			ctgItemOptionalDto.setLstEmpInfoItemData(lstCtgItemOptionalDto);
		}else{
			lstCtgItemOptionalDto.stream().forEach(x -> ctgItemOptionalDto.addToItemEmpInfoItemDataDto(x));
		}
		empPerCtgInfoDto.setCtgItemOptionalDto(ctgItemOptionalDto);
	}

	/**
	 * set category item
	 * 
	 * @param perInfoCtg
	 * @param parentInfoId
	 */
	private void setEmployeeCtgItem(EmpPerCtgInfoDto empPerCtgInfoDto, PersonInfoCategory perInfoCtg, String parentInfoId) {
		CtgItemFixDto ctgItemFixDto = new CtgItemFixDto();
		CtgItemOptionalDto ctgItemOptionalDto = new CtgItemOptionalDto();		
		switch (perInfoCtg.getCategoryCode().v()) {
		case "CS00005":
			IncomeTax incomeTax = incomeTaxRepository.getIncomeTaxById(parentInfoId).get();
			ctgItemFixDto = CtgItemFixDto.createIncomeTax(incomeTax.getIncomeTaxID(), incomeTax.getFamilyMemberId(),
					incomeTax.getSid(), incomeTax.getPeriod().start(), incomeTax.getPeriod().end(),
					incomeTax.isSupporter(), incomeTax.getDisabilityType().value,
					incomeTax.getDeductionTargetType().value);
			setCtgItemOptionDto(empPerCtgInfoDto, incomeTax.getIncomeTaxID(), true);
			break;
		case "CS00006":
			FamilySocialInsurance familySocialInsurance = familySocialInsuranceRepository
					.getFamilySocialInsById(parentInfoId).get();
			ctgItemFixDto = CtgItemFixDto.createFamilySocialInsurance(familySocialInsurance.getFamilyMemberId(),
					familySocialInsurance.getSid(), familySocialInsurance.getSocailInsuaranceId(),
					familySocialInsurance.getStartDate(), familySocialInsurance.getEndDate(),
					familySocialInsurance.isNursingCare(), familySocialInsurance.isHealthInsuranceDependent(),
					familySocialInsurance.isNationalPensionNo3(),
					familySocialInsurance.getBasicPensionNumber().v());
			setCtgItemOptionDto(empPerCtgInfoDto, familySocialInsurance.getSocailInsuaranceId(), true);
			break;
		case "CS00007":
			FamilyCare familyCare = familyCareRepository.getFamilyCareById(parentInfoId).get();
			ctgItemFixDto = CtgItemFixDto.createFamilyCare(familyCare.getFamilyCareId(), familyCare.getFamilyId(),
					familyCare.getSid(), familyCare.getPeriod().start(), familyCare.getPeriod().end(),
					familyCare.getCareClassifi().value);
			setCtgItemOptionDto(empPerCtgInfoDto, familyCare.getFamilyCareId(), true);
			break;
		case "CS00013":
			List<SubJobPosition> lstSubJobPos = subJobPosRepository.getSubJobPosByDeptId(parentInfoId);
			ctgItemFixDto = CtgItemFixDto
					.createSetCurJobPos(lstSubJobPos.stream()
							.map(x -> new ItemCurrentJobPosDto(x.getSubJobPosId(), x.getAffiDeptId(),
									x.getJobTitleId(), x.getStartDate(), x.getEndDate()))
							.collect(Collectors.toList()));
			lstSubJobPos.stream().forEach(x -> setCtgItemOptionDto(empPerCtgInfoDto, x.getJobTitleId(), false));
			break;
		}
		empPerCtgInfoDto.setCtgItemOptionalDto(ctgItemOptionalDto);		
	}

	/**set category item
	 * 
	 * @param empPerCtgInfoDto
	 * @param employee
	 * @param perInfoCtg
	 * @param parentInfoId
	 */
	private void setEmployeeCtgItem(EmpPerCtgInfoDto empPerCtgInfoDto, EmployeeDto employee, PersonInfoCategory perInfoCtg,
			String parentInfoId) {	
		CtgItemFixDto ctgItemFixDto = new CtgItemFixDto();
		CtgItemOptionalDto ctgItemOptionalDto = new CtgItemOptionalDto();
		switch (perInfoCtg.getCategoryCode().v()) {
			case "CS00002":
				ctgItemFixDto = CtgItemFixDto.createEmployee(employee.getPersonId(), employee.getEmployeeId(),
						employee.getEmployeeCode(), employee.getEmployeeMail(), employee.getRetirementDate(),
						employee.getJoinDate());
				break;
			case "CS00008":
				TemporaryAbsence temporaryAbsence = temporaryAbsenceRepository.getByTempAbsenceId(parentInfoId).get();
				ctgItemFixDto = CtgItemFixDto.createLeaveHoliday(temporaryAbsence.getEmployeeId(), temporaryAbsence.getTempAbsenceId(), 
						temporaryAbsence.getTempAbsenceType().value, temporaryAbsence.getStartDate(), temporaryAbsence.getEndDate(), 
						temporaryAbsence.getTempAbsenceReason(), temporaryAbsence.getFamilyMemberId(), temporaryAbsence.getBirthDate(), temporaryAbsence.getMulPregnancySegment());
				break;
			case "CS00009":
				JobTitleMain jobTitleMain = jobTitleMainRepository.getJobTitleMainById(parentInfoId).get();
				ctgItemFixDto = CtgItemFixDto.createJobTitleMain(jobTitleMain.getSid(), jobTitleMain.getGenericHistoryItem().getHistoryId(), 
						jobTitleMain.getJobTitleId(), jobTitleMain.getGenericHistoryItem().getPeriod().start(), jobTitleMain.getGenericHistoryItem().getPeriod().end());
				break;
			case "CS00010":
				//AssignedWorkplace 
				break;
			case "CS00011":
				//
				break;
		}
		empPerCtgInfoDto.setCtgItemOptionalDto(ctgItemOptionalDto);		
	}
	
	private void setEmployeeCtgItem(EmpPerCtgInfoDto empPerCtgInfoDto, String employeeId, PersonInfoCategory perInfoCtg,
			String parentInfoId){
		CtgItemFixDto ctgItemFixDto = new CtgItemFixDto();
		CtgItemOptionalDto ctgItemOptionalDto = new CtgItemOptionalDto();
		switch (perInfoCtg.getCategoryCode().v()) {
		case "CS00001":
			PersonDto person = personFinder.getPersonByEmpId(employeeId);
			ctgItemFixDto = CtgItemFixDto.createPerson(person.getPersonNameGroup().getBusinessName(), person.getPersonNameGroup().getPersonName(), person.getPersonNameGroup().getBusinessOtherName(),
					person.getPersonNameGroup().getBusinessEnglishName(), person.getPersonNameGroup().getPersonNameKana(), person.getPersonNameGroup().getPersonRomanji(), 
					person.getPersonNameGroup().getTodokedeFullName(), person.getPersonNameGroup().getOldName(), person.getPersonNameGroup().getTodokedeFullName());
			break;
		case "CS00003":
			CurrentAddress currentAddress = 
			break;
		}
	}

}
