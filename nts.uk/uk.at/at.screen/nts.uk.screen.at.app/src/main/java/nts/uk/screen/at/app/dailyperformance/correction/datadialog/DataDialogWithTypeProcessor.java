package nts.uk.screen.at.app.dailyperformance.correction.datadialog;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.type.TypeLink;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DataDialogWithTypeProcessor {

	@Inject
	private DailyPerformanceScreenRepo repo;

	// 勤務種類
	public CodeNameType getDutyType(String companyId, String workTypeCode, String employmentCode) {
		List<WorkTypeChangedDto> dtos = repo.findWorkTypeChanged(employmentCode, workTypeCode, companyId);
		Set<String> workTypeCodes = dtos.stream().map(x -> x.getTypeCode()).collect(Collectors.toSet());
		List<CodeName> codeNames =  repo.findWorkType(companyId, workTypeCodes);
		return CodeNameType.create(TypeLink.SERVICE_PLACE.value, codeNames);
	}

	// 就業時間帯
	public CodeNameType getWorkHours(String companyId, String workplaceId) {
		List<WorkTimeWorkplaceDto> workTimeDtos = repo.findWorkHours(companyId, workplaceId);
		List<String> shiftCodes = workTimeDtos.isEmpty() ? Collections.emptyList()
				: workTimeDtos.stream().map(x -> x.getWorkTimeID()).collect(Collectors.toList());
		List<CodeName> codeNames = repo.findWorkTimeZone(companyId, shiftCodes);
		return CodeNameType.create(TypeLink.SERVICE_PLACE.value, codeNames);
	}

	// 勤務場所
	public CodeNameType getServicePlace(String companyId) {
		List<CodeName> codeNames = repo.findWorkplaceLocation(companyId);
		return CodeNameType.create(TypeLink.SERVICE_PLACE.value, codeNames);
	}

	// 乖離理由
	public void getReason() {

	}

	// 職場
	public CodeNameType getWorkPlace(String companyId, GeneralDate date) {
		List<CodeName> codeNames = repo.findWorkplace(companyId, date);
		return CodeNameType.create(TypeLink.WORKPLACE.value, codeNames);
	}

	// 分類
	public CodeNameType getClassification(String companyId) {
		List<CodeName> codeNames = repo.findClassification(companyId);
		return CodeNameType.create(TypeLink.CLASSIFICATION.value, codeNames);
	}

	// 職位
	public CodeNameType getPossition(String companyId, GeneralDate date) {
		List<CodeName> codeNames = repo.findJobInfo(companyId, date);
		return CodeNameType.create(TypeLink.POSSITION.value, codeNames);
	}

	// 雇用区分
	public CodeNameType getEmployment(String companyId) {
		List<CodeName> codeNames = repo.findEmployment(companyId);
		return CodeNameType.create(TypeLink.EMPLOYMENT.value, codeNames);
	}

	public CodeName getTypeDialog(int type, ParamDialog param) {
		String companyId = AppContexts.user().companyId();
		Optional<CodeName> codeName;
		switch (type) {
		case 1:
			// KDL002
			codeName = this.getDutyType(companyId, param.getWorkTypeCode(), param.getEmploymentCode()).getCodeNames().stream()
					.filter(x -> x.getCode().equals(param.getSelectCode())).findFirst();
			return codeName.isPresent() ? codeName.get() : null;
		case 2:
			// KDL001
			codeName = this.getWorkHours(companyId, param.getWorkplaceId()).getCodeNames().stream()
					.filter(x -> x.getCode().equals(param.getSelectCode())).findFirst();
			return codeName.isPresent() ? codeName.get() : null;
		case 3:
			// KDL010
			codeName = this.getServicePlace(companyId).getCodeNames().stream()
					.filter(x -> x.getCode().equals(param.getSelectCode())).findFirst();
			return codeName.isPresent() ? codeName.get() : null;
		case 4:
			// KDL032
			codeName = this.getPossition(companyId, param.getDate()).getCodeNames().stream()
					.filter(x -> x.getCode().equals(param.getSelectCode())).findFirst();
			return codeName.isPresent() ? codeName.get() : null;
		case 5:
			// CDL008
			codeName = this.getWorkPlace(companyId, param.getDate()).getCodeNames().stream()
					.filter(x -> x.getCode().equals(param.getSelectCode())).findFirst();
			return codeName.isPresent() ? codeName.get() : null;
		case 6:
			// KCP002
			codeName = this.getClassification(companyId).getCodeNames().stream()
					.filter(x -> x.getCode().equals(param.getSelectCode())).findFirst();
			return codeName.isPresent() ? codeName.get() : null;
		case 7:
			// KCP003
			codeName = this.getPossition(companyId, param.getDate()).getCodeNames().stream()
					.filter(x -> x.getCode().equals(param.getSelectCode())).findFirst();
			return codeName.isPresent() ? codeName.get() : null;
		case 8:
			// KCP001
			codeName = this.getEmployment(companyId).getCodeNames().stream()
					.filter(x -> x.getCode().equals(param.getSelectCode())).findFirst();
			return codeName.isPresent() ? codeName.get() : null;
		default:
			return null;
		}
	}
	
	public List<CodeName> getAllTypeDialog(int type, ParamDialog param) {
		String companyId = AppContexts.user().companyId();
		switch (type) {
		case 1:
			// KDL002
			return this.getDutyType(companyId, param.getWorkTypeCode(), param.getEmploymentCode()).getCodeNames().stream().collect(Collectors.toList());
		case 2:
			// KDL001
			return this.getWorkHours(companyId, param.getWorkplaceId()).getCodeNames().stream().collect(Collectors.toList());
		case 3:
			// KDL010
			return this.getServicePlace(companyId).getCodeNames().stream().collect(Collectors.toList());
		case 4:
			// KDL032
			return this.getPossition(companyId, param.getDate()).getCodeNames().stream().collect(Collectors.toList());
		case 5:
			// CDL008
			return this.getWorkPlace(companyId, param.getDate()).getCodeNames().stream().collect(Collectors.toList());
		case 6:
			// KCP002
			return this.getClassification(companyId).getCodeNames().stream().collect(Collectors.toList());
		case 7:
			// KCP003
			return this.getPossition(companyId, param.getDate()).getCodeNames().stream().collect(Collectors.toList());
		case 8:
			// KCP001
			return this.getEmployment(companyId).getCodeNames().stream().collect(Collectors.toList());
		default:
			return null;
		}
	}
}
