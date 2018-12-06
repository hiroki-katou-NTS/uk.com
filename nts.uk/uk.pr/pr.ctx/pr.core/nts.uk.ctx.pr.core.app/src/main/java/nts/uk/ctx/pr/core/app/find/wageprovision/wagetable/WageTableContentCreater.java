package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.SysClassificationAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.SysDepartmentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.SysEmploymentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformationRepository;
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
		WageTableContentDto dto = new WageTableContentDto();
		dto.setHistoryID(params.getHistoryID());
		List<TwoDmsElementItemDto> payments = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		Optional<WageTable> optWageTable = wageTableRepo.getWageTableById(companyId, params.getWageTableCode());
		if (params.getFirstElementRange() == null) {
			// master item
			if (optWageTable.isPresent()) {
				payments = getMasterElementItems(optWageTable.get().getElementInformation().getOneDimensionalElement()
						.getFixedElement().get().value, companyId).stream().map(i -> new TwoDmsElementItemDto(i))
								.collect(Collectors.toList());
			}
		} else {
			// numeric item
			payments = getNumericRange(params.getFirstElementRange()).stream().map(i -> new TwoDmsElementItemDto(i))
					.collect(Collectors.toList());
		}
		List<ElementItemDto> list2nd = params.getSecondElementRange() == null ? getMasterElementItems(
				optWageTable.get().getElementInformation().getOneDimensionalElement().getFixedElement().get().value,
				companyId) : getNumericRange(params.getSecondElementRange());
		for (TwoDmsElementItemDto t : payments) {
			t.setListSecondDms(list2nd);
		}
		dto.setList2dElements(payments);
		return dto;
	}

	public WageTableContentDto createThreeDimensionWageTable(ElementRangeSettingDto params) {
		// WageTableContentDto dto = new WageTableContentDto();
		// dto.setHistoryID(params.getHistoryID());
		// List<ElementsCombinationPaymentAmountDto> payments = new
		// ArrayList<>();
		// if (params.getFirstElementRange() == null) {
		// // master item
		// payments.addAll(getMasterElements());
		// } else {
		// // numeric item
		// payments.addAll(getNumericRange(params.getFirstElementRange()));
		// }
		// if (params.getSecondElementRange() == null) {
		// // master item
		// payments.addAll(getMasterElements());
		// } else {
		// // numeric item
		// payments.addAll(getNumericRange(params.getSecondElementRange()));
		// }
		// if (params.getThirdElementRange() == null) {
		// // master item
		// payments.addAll(getMasterElements());
		// } else {
		// // numeric item
		// payments.addAll(getNumericRange(params.getThirdElementRange()));
		// }
		// dto.setPayments(payments);
		return null;
	}

	private List<ElementItemDto> getNumericRange(ElementRangeDto rangeDto) {
		List<ElementItemDto> result = new ArrayList<>();
		int frameNum = 1;
		while (rangeDto.getRangeLowerLimit() + rangeDto.getStepIncrement() < rangeDto.getRangeUpperLimit()) {
			result.add(new ElementItemDto(null, null, frameNum, rangeDto.getRangeLowerLimit(),
					rangeDto.getRangeLowerLimit() + rangeDto.getStepIncrement() - 1, null));
			rangeDto.setRangeLowerLimit(rangeDto.getRangeLowerLimit() + rangeDto.getStepIncrement());
			frameNum++;
		}
		result.add(new ElementItemDto(null, null, frameNum, rangeDto.getRangeLowerLimit(),
				rangeDto.getRangeUpperLimit(), null));
		return result;
	}

	private List<ElementItemDto> getMasterElementItems(String master, String companyId) {
		Optional<ElementType> optElemType = Arrays.stream(ElementType.values())
				.filter(item -> item.value.equals(master)).findFirst();
		if (optElemType.isPresent()) {
			switch (optElemType.get()) {
			case EMPLOYMENT:
				val listEmp = employmentAdapter.findAll(companyId);
				return listEmp.stream().map(e -> new ElementItemDto(e.getCode(), e.getName(), null, null, null, null))
						.collect(Collectors.toList());
			case DEPARTMENT:
				val listDep = departmentAdapter.getDepartmentByCompanyIdAndBaseDate(companyId, GeneralDate.today());
				return listDep.stream().map(
						d -> new ElementItemDto(d.getDepartmentCode(), d.getDepartmentName(), null, null, null, null))
						.collect(Collectors.toList());
			case CLASSIFICATION:
				val listCls = clsAdapter.getClassificationByCompanyId(companyId);
				return listCls.stream().map(c -> new ElementItemDto(c.getClassificationCode(),
						c.getClassificationName(), null, null, null, null)).collect(Collectors.toList());
			case JOB_TITLE:
				val listJob = jobAdapter.findAll(companyId, GeneralDate.today());
				return listJob.stream()
						.map(j -> new ElementItemDto(j.getJobTitleCode(), j.getJobTitleName(), null, null, null, null))
						.collect(Collectors.toList());
			case SALARY_CLASSIFICATION:
				val listSal = salaryRepo.getAllSalaryClassificationInformation(companyId);
				return listSal.stream()
						.map(s -> new ElementItemDto(s.getSalaryClassificationCode().v(),
								s.getSalaryClassificationName().v(), null, null, null, null))
						.collect(Collectors.toList());
			case QUALIFICATION:
				val listQual = qualificationRepo.getQualificationGroupSettingByCompanyID();
				return listQual.stream().map(s -> new ElementItemDto(s.getQualificationCode().v(),
						s.getQualificationName().v(), null, null, null, null)).collect(Collectors.toList());
			default:
				return Collections.emptyList();
			}
		} else {
			return Collections.emptyList();
		}
	}

}
