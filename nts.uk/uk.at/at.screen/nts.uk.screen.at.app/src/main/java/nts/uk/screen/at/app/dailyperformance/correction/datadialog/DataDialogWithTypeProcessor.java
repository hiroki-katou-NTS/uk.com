package nts.uk.screen.at.app.dailyperformance.correction.datadialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
		List<CodeName> codeNames = repo.findWorkType(companyId, workTypeCodes);
		return CodeNameType.create(TypeLink.DUTY.value, codeNames);
	}

	// 勤務種類
	public CodeNameType getDutyTypeAll(String companyId) {
		List<CodeName> codeNames = repo.findWorkType(companyId, new HashSet<>());
		return CodeNameType.create(TypeLink.DUTY.value, codeNames);
	}

	// 就業時間帯
	public CodeNameType getWorkHours(String companyId, String workplaceId) {
		List<WorkTimeWorkplaceDto> workTimeDtos = repo.findWorkHours(companyId, workplaceId);
		List<String> shiftCodes = workTimeDtos.isEmpty() ? Collections.emptyList()
				: workTimeDtos.stream().map(x -> x.getWorkTimeID()).collect(Collectors.toList());
		List<CodeName> codeNames = repo.findWorkTimeZone(companyId, shiftCodes);
		return CodeNameType.create(TypeLink.WORK_HOURS.value, codeNames);
	}

	// 就業時間帯
	public CodeNameType getWorkHoursAll(String companyId) {
		List<CodeName> codeNames = repo.findWorkTimeZone(companyId, new ArrayList<>());
		return CodeNameType.create(TypeLink.WORK_HOURS.value, codeNames);
	}

	// 勤務場所
	public CodeNameType getServicePlace(String companyId) {
		List<CodeName> codeNames = repo.findWorkplaceLocation(companyId);
		return CodeNameType.create(TypeLink.SERVICE_PLACE.value, codeNames);
	}

	// 乖離理由
	public CodeNameType getReason(String companyId) {
		List<CodeName> codeNames = repo.findReason(companyId);
		return CodeNameType.create(TypeLink.REASON.value, codeNames);
	}

	// 職場--
	public CodeNameType getWorkPlace(String companyId, GeneralDate date) {
		List<CodeName> codeNames = repo.findWorkplace(companyId, date);
		return CodeNameType.create(TypeLink.WORKPLACE.value, codeNames);
	}

	// 分類
	public CodeNameType getClassification(String companyId) {
		List<CodeName> codeNames = repo.findClassification(companyId);
		return CodeNameType.create(TypeLink.CLASSIFICATION.value, codeNames);
	}

	// 職位--
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
			codeName = this.getDutyType(companyId, param.getWorkTypeCode(), param.getEmploymentCode()).getCodeNames()
					.stream().filter(x -> x.getCode().equals(param.getSelectCode())).findFirst();
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
			codeName = this.getReason(companyId).getCodeNames().stream()
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
			return this.getDutyType(companyId, param.getWorkTypeCode(), param.getEmploymentCode()).getCodeNames()
					.stream().collect(Collectors.toList());
		case 2:
			// KDL001
			return this.getWorkHours(companyId, param.getWorkplaceId()).getCodeNames().stream()
					.collect(Collectors.toList());
		case 3:
			// KDL010
			return this.getServicePlace(companyId).getCodeNames().stream().collect(Collectors.toList());
		case 4:
			// KDL032
			return this.getReason(companyId).getCodeNames().stream().collect(Collectors.toList());
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

	public Map<Integer, Map<String, CodeName>> getAllCodeName(List<Integer> types, String companyId) {
		Map<Integer, Map<String, CodeName>> result = new HashMap<Integer, Map<String, CodeName>>();
		for (int i = 0; i < types.size(); i++) {
			int type = types.get(i);
			List<CodeName> codeNames = new ArrayList<>();
			switch (type) {
			case 1:
				// KDL002
				codeNames = this.getDutyTypeAll(companyId).getCodeNames().stream().collect(Collectors.toList());
				Map<String, CodeName> map1 = new HashMap<>();
				codeNames.forEach(x ->{
					map1.put(x.getCode(),x);
				});
				result.put(type, map1);
				break;
			case 2:
				// KDL001
				codeNames = this.getWorkHoursAll(companyId).getCodeNames().stream().collect(Collectors.toList());
				Map<String, CodeName> map2 = new HashMap<>();
				codeNames.forEach(x ->{
					map2.put(x.getCode(),x);
				});
				result.put(type, map2);
				break;
			case 3:
				// KDL010
				codeNames = this.getServicePlace(companyId).getCodeNames().stream().collect(Collectors.toList());
				Map<String, CodeName> map3 = new HashMap<>();
				codeNames.forEach(x ->{
					map3.put(x.getCode(),x);
				});
				result.put(type, map3);
				break;
			case 4:
				// KDL032
				codeNames = this.getReason(companyId).getCodeNames().stream().collect(Collectors.toList());
				Map<String, CodeName> map4 = new HashMap<>();
				codeNames.forEach(x ->{
					map4.put(x.getCode(),x);
				});
				result.put(type, map4);
				break;
			case 5:
				// CDL008
				codeNames = new ArrayList<>();
				break;
			case 6:
				// KCP002
				codeNames = this.getClassification(companyId).getCodeNames().stream().collect(Collectors.toList());
				Map<String, CodeName> map6 = new HashMap<>();
				codeNames.forEach(x ->{
					map6.put(x.getCode(),x);
				});
				result.put(type, map6);
			case 7:
				// KCP003
				codeNames = new ArrayList<>();
				break;
			case 8:
				// KCP001
				codeNames = this.getEmployment(companyId).getCodeNames().stream().collect(Collectors.toList());
				Map<String, CodeName> map8 = new HashMap<>();
				codeNames.forEach(x ->{
					map8.put(x.getCode(),x);
				});
				result.put(type, map8);
				break;
			default:
				break;
			}
		};

		return result;
	}
	
	public Optional<CodeName> getCodeNameWithId(int type, GeneralDate date, String id) {
		String companyId = AppContexts.user().companyId();
		if(type == TypeLink.POSSITION.value){
			return repo.findJobInfoId(companyId, date, id);
		}else if(type == TypeLink.WORKPLACE.value){
			return repo.findWorkplaceId(companyId, date, id);
		}
		
		return null;
	}
}
