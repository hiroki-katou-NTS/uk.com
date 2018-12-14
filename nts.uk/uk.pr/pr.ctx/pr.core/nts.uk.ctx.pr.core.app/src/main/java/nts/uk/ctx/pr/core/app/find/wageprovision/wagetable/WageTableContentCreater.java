package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.app.find.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClsInforDto;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationImport;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.SysClassificationAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.DepartmentImport;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.SysDepartmentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.SysEmploymentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class WageTableContentCreater {

	@Inject
	private WageTableRepository wageTableRepo;

	@Inject
	private SysEmploymentAdapter employmentAdapter;

	@Inject
	private SysDepartmentAdapter departmentAdapter;

	@Inject
	private SysClassificationAdapter clsAdapter;

	@Inject
	private SyJobTitleAdapter jobAdapter;

	@Inject
	private SalaryClassificationInformationRepository salaryRepo;

	@Inject
	private QualificationInformationRepository qualificationRepo;

	public WageTableContentDto createOneDimensionWageTable(ElementRangeSettingDto params) {
		WageTableContentDto dto = new WageTableContentDto();
		dto.setHistoryID(params.getHistoryID());
		List<ElementItemDto> payments = new ArrayList<>();
		if (params.getFirstElementRange() == null) {
			// master item
			String companyId = AppContexts.user().companyId();
			Optional<WageTable> optWageTable = wageTableRepo.getWageTableById(companyId, params.getWageTableCode());
			if (optWageTable.isPresent()) {
				payments = getMasterElementItems(optWageTable.get().getElementInformation().getOneDimensionalElement()
						.getFixedElement().get().value, companyId);
			}
		} else {
			// numeric item
			payments = getNumericRange(params.getFirstElementRange());
		}
		dto.setList1dElements(payments);
		return dto;
	}

	public WageTableContentDto createTwoDimensionWageTable(ElementRangeSettingDto params) {
		WageTableContentDto dto = this.createOneDimensionWageTable(params);
		List<TwoDmsElementItemDto> payments = dto.getList1dElements().stream().map(i -> new TwoDmsElementItemDto(i))
				.collect(Collectors.toList());
		String companyId = AppContexts.user().companyId();
		Optional<WageTable> optWageTable = wageTableRepo.getWageTableById(companyId, params.getWageTableCode());
		List<ElementItemDto> list2nd = params.getSecondElementRange() == null ? getMasterElementItems(
				optWageTable.get().getElementInformation().getTwoDimensionalElement().get().getFixedElement().get().value,
				companyId) : getNumericRange(params.getSecondElementRange());
		for (TwoDmsElementItemDto t : payments) {
			t.setListSecondDms(list2nd);
		}
		dto.setList2dElements(payments);
		dto.setList1dElements(null);
		return dto;
	}

	public WageTableContentDto createThreeDimensionWageTable(ElementRangeSettingDto params) {
		WageTableContentDto dto = this.createTwoDimensionWageTable(params);
		List<ThreeDmsElementItemDto> payments = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		Optional<WageTable> optWageTable = wageTableRepo.getWageTableById(companyId, params.getWageTableCode());
		if (params.getThirdElementRange() == null) {
			// master item
			if (optWageTable.isPresent()) {
				payments = getMasterElementItems(optWageTable.get().getElementInformation().getThreeDimensionalElement().get()
						.getFixedElement().get().value, companyId).stream()
								.map(i -> new ThreeDmsElementItemDto(new TwoDmsElementItemDto(i)))
								.collect(Collectors.toList());
			}
		} else {
			// numeric item
			payments = getNumericRange(params.getThirdElementRange()).stream()
					.map(i -> new ThreeDmsElementItemDto(new TwoDmsElementItemDto(i))).collect(Collectors.toList());
		}
		List<TwoDmsElementItemDto> listTwoDms = dto.getList2dElements();
		for (ThreeDmsElementItemDto t : payments) {
			t.setListFirstDms(listTwoDms);
		}
		dto.setList3dElements(payments);
		dto.setList2dElements(null);
		return dto;
	}

	private List<ElementItemDto> getNumericRange(ElementRangeDto rangeDto) {
		List<ElementItemDto> result = new ArrayList<>();
		if (rangeDto.getStepIncrement() == null) { // screen F: enum 精皆勤レベル
			for (int i = 1; i <= 5; i ++) {
				result.add(new ElementItemDto(null, null, i, i, i, null));
			}
		} else {
			int frameNum = 1;
			while (rangeDto.getRangeLowerLimit() + rangeDto.getStepIncrement() <= rangeDto.getRangeUpperLimit()) {
				result.add(new ElementItemDto(null, null, frameNum, rangeDto.getRangeLowerLimit(),
						rangeDto.getRangeLowerLimit() + rangeDto.getStepIncrement() - 1, null));
				rangeDto.setRangeLowerLimit(rangeDto.getRangeLowerLimit() + rangeDto.getStepIncrement());
				frameNum++;
			}
			result.add(new ElementItemDto(null, null, frameNum, rangeDto.getRangeLowerLimit(),
					rangeDto.getRangeUpperLimit(), null));
		}
		return result;
	}

	private List<ElementItemDto> getMasterElementItems(String master, String companyId) {
		Map<String, String> mapMaster = getMasterItems(master, companyId);
		List<ElementItemDto> result = mapMaster.entrySet().stream()
				.map(i -> new ElementItemDto(i.getKey(), i.getValue(), null, null, null, null))
				.collect(Collectors.toList());
		Comparator<ElementItemDto> comparator = Comparator
				.comparing(ElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
				.thenComparing(ElementItemDto::getFrameNumber, Comparator.nullsLast(Comparator.naturalOrder()));
		Collections.sort(result, comparator);
		return result;
	}

	public Map<String, String> getMasterItems(String master, String companyId) {
		Map<String, String> result = new HashMap<>();
		Optional<ElementType> optElemType = Arrays.stream(ElementType.values())
				.filter(item -> item.value.equals(master)).findFirst();
		if (optElemType.isPresent()) {
			switch (optElemType.get()) {
			case EMPLOYMENT:
				val listEmp = employmentAdapter.findAll(companyId);
				result = listEmp.stream().collect(Collectors.toMap(EmpCdNameImport::getCode, EmpCdNameImport::getName));
				break;
			case DEPARTMENT:
				val listDep = departmentAdapter.getDepartmentByCompanyIdAndBaseDate(companyId, GeneralDate.today());
				result = listDep.stream().collect(
						Collectors.toMap(DepartmentImport::getDepartmentCode, DepartmentImport::getDepartmentName));
				break;
			case CLASSIFICATION:
				val listCls = clsAdapter.getClassificationByCompanyId(companyId);
				result = listCls.stream().collect(Collectors.toMap(ClassificationImport::getClassificationCode,
						ClassificationImport::getClassificationName));
				break;
			case JOB_TITLE:
				val listJob = jobAdapter.findAll(companyId, GeneralDate.today());
				result = listJob.stream()
						.collect(Collectors.toMap(JobTitle::getJobTitleCode, JobTitle::getJobTitleName));
				break;
			case SALARY_CLASSIFICATION:
				val listSal = salaryRepo.getAllSalaryClassificationInformation(companyId);
				result = listSal.stream().map(i -> SalaryClsInforDto.fromDomain(i))
						.collect(Collectors.toMap(SalaryClsInforDto::getSalaryClassificationCode,
								SalaryClsInforDto::getSalaryClassificationName));
				break;
			case QUALIFICATION:
				val listQual = qualificationRepo.getQualificationGroupSettingByCompanyID();
				result = listQual.stream().map(i -> QualificationInformationDto.fromDomainToDto(i))
						.collect(Collectors.toMap(QualificationInformationDto::getQualificationCode,
								QualificationInformationDto::getQualificationName));
				break;
			default:
				break;
			}
		}
		if (result.isEmpty())
			throw new BusinessException("Msg_37");
		return result;
	}

}
