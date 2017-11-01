package nts.uk.ctx.bs.employee.app.find.person.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.bs.employee.app.find.employee.EmployeeDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemCurrentJobPosDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemEmpInfoItemDataDto;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCare;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCareRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTax;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTaxRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsurance;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsuranceRepository;
import nts.uk.ctx.bs.employee.dom.person.ParamForGetPerItem;
import nts.uk.ctx.bs.employee.dom.person.PerInfoCtgDomainService;
import nts.uk.ctx.bs.employee.dom.position.jobposition.JobPositionMain;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
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
	EmployeeRepository employeeRepository;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;
	
	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepository;

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
			CtgItemFixDto ctgItemFixDto = getEmployeeCtgItemFix(perInfoCtg, parentInfoId);
			CtgItemOptionalDto ctgItemOptionalDto = getCtgItemOptionDto(employeeId,
					perInfoCtg.getPersonInfoCategoryId());
			EmpPerCtgInfoDto empPerCtgInfoDto = new EmpPerCtgInfoDto();
			empPerCtgInfoDto = EmpPerCtgInfoDto.createObjectFromDomain(perInfoCtg, lstPerInfoItemDef, ctgItemFixDto,
					ctgItemOptionalDto);
			// if(!(perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO &&
			// perInfoCtg.getCategoryType() == CategoryType.MULTIINFO)){
			//
			// }
			return empPerCtgInfoDto;
		}
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
		List<PersonInfoCategory> lstPerInfoCtg = perInfoCtgRepositoty
				.getPerInfoCtgByParentCdWithOrder(perInfoCtg.getCategoryParentCode().v(), contractCode, true);
		List<PersonInfoItemDefinition> lstPerInfoItemDef = perInfoCtgDomainService
				.getPerInfoItemDefWithAuth(new ParamForGetPerItem(perInfoCtg, parentInfoId,
						roleId == null ? "" : roleId, companyId, contractCode, loginEmpId.equals(employeeId)));
		if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {// employee
			if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {// fixed

			} else {// optional

			}
		} else {// person
			if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {// fixed

			} else {// optional

			}
		}
		return null;
	}

	/**
	 * get category item optional
	 * 
	 * @param sId
	 * @param ctgId
	 * @return CtgItemOptionalDto
	 */
	private CtgItemOptionalDto getCtgItemOptionDto(String sId, String ctgId) {
		CtgItemOptionalDto ctgItemOptionalDto = new CtgItemOptionalDto();
		ctgItemOptionalDto.setEmpInfoCtgData(emInfoCtgDataRepository.getEmpInfoCtgDataBySIdAndCtgId(sId, ctgId)
				.map(x -> new EmpInfoCtgDataDto(x.getRecordId(), x.getPersonInfoCtgId(), x.getEmployeeId())).get());
		ctgItemOptionalDto.setLstEmpInfoItemData(empInfoItemDataRepository
				.getAllInfoItemByRecordId(ctgItemOptionalDto.getEmpInfoCtgData().getRecordId()).stream()
				.map(x -> new ItemEmpInfoItemDataDto(x.getPerInfoDefId(), x.getRecordId(), x.getPerInfoCtgId(),
						x.getItemName(), x.getIsRequired().value, x.getDataState().getDataStateType().value,
						x.getDataState().getStringValue(), x.getDataState().getNumberValue(),
						x.getDataState().getDateValue()))
				.collect(Collectors.toList()));
		return ctgItemOptionalDto;
	}

	/**
	 * get category item fixed
	 * 
	 * @param perInfoCtg
	 * @param parentInfoId
	 * @return CtgItemFixDto
	 */
	private CtgItemFixDto getEmployeeCtgItemFix(PersonInfoCategory perInfoCtg, String parentInfoId) {
		CtgItemFixDto ctgItemFixDto = new CtgItemFixDto();
		if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
			switch (perInfoCtg.getCategoryCode().v()) {
			case "CS00005":
				IncomeTax incomeTax = incomeTaxRepository.getIncomeTaxById(parentInfoId).get();
				ctgItemFixDto = CtgItemFixDto.createIncomeTax(incomeTax.getIncomeTaxID(), incomeTax.getFamilyMemberId(),
						incomeTax.getSid(), incomeTax.getPeriod().start(), incomeTax.getPeriod().end(),
						incomeTax.isSupporter(), incomeTax.getDisabilityType().value,
						incomeTax.getDeductionTargetType().value);
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
				break;
			case "CS00007":
				FamilyCare familyCare = familyCareRepository.getFamilyCareById(parentInfoId).get();
				ctgItemFixDto = CtgItemFixDto.createFamilyCare(familyCare.getFamilyCareId(), familyCare.getFamilyId(),
						familyCare.getSid(), familyCare.getPeriod().start(), familyCare.getPeriod().end(),
						familyCare.getCareClassifi().value);
				break;
			case "CS00013":
				List<SubJobPosition> lstSubJobPos = subJobPosRepository.getSubJobPosByDeptId(parentInfoId);
				ctgItemFixDto = CtgItemFixDto
						.createSetCurJobPos(lstSubJobPos.stream()
								.map(x -> new ItemCurrentJobPosDto(x.getSubJobPosId(), x.getAffiDeptId(),
										x.getJobTitleId(), x.getStartDate(), x.getEndDate()))
								.collect(Collectors.toList()));
				break;
			}

		}
		return ctgItemFixDto;
	}

	private CtgItemFixDto getEmployeeCtgItemFix(EmployeeDto employee, PersonInfoCategory perInfoCtg,
			String parentInfoId) {
		CtgItemFixDto ctgItemFixDto = new CtgItemFixDto();
		if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
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
				break;
			}
		}
		return ctgItemFixDto;
	}

}
