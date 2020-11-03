package nts.uk.ctx.at.function.app.find.alarm;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.app.find.alarm.extractionrange.SpecifiedMonthDto;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.SpecifiedMonth;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AlarmPatternSettingFinder {

	@Inject
	private AlarmPatternSettingRepository alarmPatternRepo;

	@Inject
	private AlarmCheckConditionByCategoryRepository alarmCategoryRepo;

	public List<AlarmPatternSettingDto> findAllAlarmPattern() {
		String companyId = AppContexts.user().companyId();
		return alarmPatternRepo.findByCompanyId(companyId)
							   .stream()
							   .map(AlarmPatternSettingDto::createFromDomain)
							   .collect(Collectors.toList());
	}

	public List<AlarmCheckConditonCodeDto> findAllAlarmCheckCondition() {
		String companyId = AppContexts.user().companyId();
		List<AlarmCheckConditionByCategory> listCheckCondition = alarmCategoryRepo.findAll(companyId);
		listCheckCondition.sort(Comparator.comparingInt(a -> a.getCategory().value));
		return listCheckCondition.stream()
								 .map(this::convertToCheckConditionCode)
								 .collect(Collectors.toList());
	}

	private AlarmCheckConditonCodeDto convertToCheckConditionCode(AlarmCheckConditionByCategory domain) {
		return new AlarmCheckConditonCodeDto(EnumAdaptor.convertToValueName(domain.getCategory()),
											 domain.getCode().v(),
											 domain.getName().v());
	}

	public List<SpecifiedMonthDto> getSpecifiedMonth() {
		List<SpecifiedMonthDto> monthDtos = new ArrayList<SpecifiedMonthDto>();
		for (SpecifiedMonth r : SpecifiedMonth.values()) {
			monthDtos.add(new SpecifiedMonthDto(r.value, r.nameId));
		}
		return monthDtos;
	}

//	public CheckConditionDto convertToCheckConditionDto(CheckCondition domain) {
//		ExtractionPeriodDailyDto extractionPeriodDailyDto = null;
//		ExtractionPeriodUnitDto extractionUnit = null;
//		List<ExtractionPeriodMonthlyDto> listExtractionMonthly = new ArrayList<ExtractionPeriodMonthlyDto>();
//		ExtractionRangeYearDto extractionYear =null;
//		ExtractionAverMonthDto extractionAverMonth = null;
//
//		if (domain.isDaily() || domain.isManHourCheck()) {
//			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
//			ExtractionPeriodDaily extractionPeriodDaily = (ExtractionPeriodDaily) extractBase;
//			extractionPeriodDailyDto = ExtractionPeriodDailyDto.fromDomain(extractionPeriodDaily);
//
//		} else if (domain.isMonthly() || domain.isMultipleMonth()) {
//			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
//			ExtractionPeriodMonth extractionPeriodMonth = (ExtractionPeriodMonth) extractBase;
//			ExtractionPeriodMonthlyDto extractionPeriodMonthlyDto = ExtractionPeriodMonthlyDto
//					.fromDomain(extractionPeriodMonth);
//			listExtractionMonthly.add(extractionPeriodMonthlyDto);
//
//		} else if (domain.is4W4D()) {
//			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
//			extractionUnit = ExtractionPeriodUnitDto.fromDomain((ExtractionPeriodUnit) extractBase);
//
//		}	else if(domain.isAgrrement()) {
//
//			for(ExtractionRangeBase extractBase : domain.getExtractPeriodList()) {
//
//				if(extractBase instanceof ExtractionPeriodDaily) {
//					ExtractionPeriodDaily extractionPeriodDaily = (ExtractionPeriodDaily) extractBase;
//					extractionPeriodDailyDto = ExtractionPeriodDailyDto.fromDomain(extractionPeriodDaily);
//
//				}else if(extractBase  instanceof ExtractionPeriodMonth) {
//					ExtractionPeriodMonth extractionPeriodMonth = (ExtractionPeriodMonth) extractBase;
//					listExtractionMonthly.add(ExtractionPeriodMonthlyDto.fromDomain(extractionPeriodMonth));
//
//				}else if (extractBase instanceof AYear){
//					extractionYear  = ExtractionRangeYearDto.fromDomain((AYear) extractBase);
//				} else {
//					extractionAverMonth = ExtractionAverMonthDto.fromDomain((AverageMonth) extractBase);
//				}
//			}
//
//		}
//
//		return new CheckConditionDto(domain.getAlarmCategory().value, domain.getCheckConditionList(),
//				extractionPeriodDailyDto, extractionUnit, listExtractionMonthly, extractionYear, extractionAverMonth);
//
//	}

	public List<CodeNameAlarmDto> getCodeNameAlarm() {
		String companyId = AppContexts.user().companyId();
		String roleId = AppContexts.user().roles().forAttendance();
		List<CodeNameAlarmDto> result = alarmPatternRepo.findByCompanyIdAndUser(companyId).stream()
				.filter(a -> !a.isAuthSetting() || a.isAuthSetting() && intersectTwoListRoleId(a.getRoleIds(), roleId))
				.map(a -> new CodeNameAlarmDto(a.getAlarmCode(), a.getAlarmName(), a.getAlarmCode() + " " + a.getAlarmName())).collect(Collectors.toList());
		result.sort((a, b) -> a.getAlarmCode().compareTo(b.getAlarmCode()));
		return result;
	}

	private boolean intersectTwoListRoleId(List<String> listRole1, String roleId) {
		if (CollectionUtil.isEmpty(listRole1)) {
			return false;
		}
		return listRole1.contains(roleId);
	}

}
