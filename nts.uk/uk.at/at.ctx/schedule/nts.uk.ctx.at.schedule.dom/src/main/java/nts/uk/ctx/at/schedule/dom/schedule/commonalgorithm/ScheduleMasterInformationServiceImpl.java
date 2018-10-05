package nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification.ExClassificationHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification.ExClassificationHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle.ExJobTitleHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle.ExJobTitleHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkPlaceHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkplaceHistItemImported;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpDto;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * 勤務予定マスタ情報を取得する
 * 
 * lấy các thông tin master 勤務予定マスタ情報
 * 
 * @author sonnh1
 *
 */
@Stateless
public class ScheduleMasterInformationServiceImpl implements ScheduleMasterInformationService {

	@Inject
	private ScheduleErrorLogRepository scheduleErrorLogRepository;

	@Inject
	private I18NResourcesForUK internationalization;

	@Override
	public Optional<ScheduleMasterInformationDto> getScheduleMasterInformationDto(String employeeId,
			GeneralDate baseDate, String exeId, EmployeeGeneralInfoImported empGeneralInfo, List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis) {

		Optional<ScheduleMasterInformationDto> result = Optional.of(new ScheduleMasterInformationDto());

		Map<String, List<ExEmploymentHistItemImported>> mapEmploymentHist = empGeneralInfo.getEmploymentDto().stream()
				.collect(Collectors.toMap(ExEmploymentHistoryImported::getEmployeeId,
						ExEmploymentHistoryImported::getEmploymentItems));

		Map<String, List<ExClassificationHistItemImported>> mapClassificationHist = empGeneralInfo
				.getClassificationDto().stream()
				.collect(Collectors.toMap(ExClassificationHistoryImported::getEmployeeId,
						ExClassificationHistoryImported::getClassificationItems));

		Map<String, List<ExJobTitleHistItemImported>> mapJobTitleHist = empGeneralInfo.getJobTitleDto().stream()
				.collect(Collectors.toMap(ExJobTitleHistoryImported::getEmployeeId,
						ExJobTitleHistoryImported::getJobTitleItems));

		Map<String, List<ExWorkplaceHistItemImported>> mapWorkplaceHist = empGeneralInfo.getWorkplaceDto().stream()
				.collect(Collectors.toMap(ExWorkPlaceHistoryImported::getEmployeeId,
						ExWorkPlaceHistoryImported::getWorkplaceItems));

		// 雇用コードを取得する
		boolean valueSetEmpCode = this.setEmployeeCode(exeId, employeeId, baseDate, result, mapEmploymentHist);
		if (!valueSetEmpCode) {
			return Optional.empty();
		}
		// 分類コードを取得する
		this.getClassificationCode(employeeId, baseDate, result, mapClassificationHist);

		// 職位IDを取得する
		boolean valueAcquireJobTitleId = this.acquireJobTitleId(exeId, employeeId, baseDate, result, mapJobTitleHist);
		if (!valueAcquireJobTitleId) {
			return Optional.empty();
		}

		// 職場IDを取得する
		boolean valueAcquireWorkplaceId = this.acquireWorkplaceId(exeId, employeeId, baseDate, result,
				mapWorkplaceHist);
		if (!valueAcquireWorkplaceId) {
			return Optional.empty();
		}

		// 勤務種別コードを取得する
		this.acquireWorkTypeCode(employeeId, baseDate, result, listBusTypeOfEmpHis);

		return result;
	}

	/**
	 * 雇用コードを取得する
	 * 
	 * @param exeId
	 * @param employeeId
	 * @param baseDate
	 * @param result
	 * @param mapEmploymentHist
	 * @return
	 */
	private boolean setEmployeeCode(String exeId, String employeeId, GeneralDate baseDate,
			Optional<ScheduleMasterInformationDto> result,
			Map<String, List<ExEmploymentHistItemImported>> mapEmploymentHist) {
		// EA No1678
		List<ExEmploymentHistItemImported> listEmpHistItem = mapEmploymentHist.get(employeeId);
		if (listEmpHistItem != null) {
			Optional<ExEmploymentHistItemImported> optEmpHistItem = listEmpHistItem.stream()
					.filter(empHistItem -> empHistItem.getPeriod().contains(baseDate)).findFirst();
			if (optEmpHistItem.isPresent()) {
				result.get().setEmployeeCode(optEmpHistItem.get().getEmploymentCode());
				return true;
			}
		}
		
		ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(this.getErrorContent("Msg_602", "#Com_Employment"),
				exeId, baseDate, employeeId);
		this.scheduleErrorLogRepository.add(scheduleErrorLog);
		return false;
	}

	/**
	 * 分類コードを取得する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @param result
	 * @param mapClassificationHist
	 */
	private void getClassificationCode(String employeeId, GeneralDate baseDate,
			Optional<ScheduleMasterInformationDto> result,
			Map<String, List<ExClassificationHistItemImported>> mapClassificationHist) {
		// EA No1679
		List<ExClassificationHistItemImported> listClassHistItem = mapClassificationHist.get(employeeId);
		if (listClassHistItem != null) {
			Optional<ExClassificationHistItemImported> optClassHistItem = listClassHistItem.stream()
					.filter(classHistItem -> classHistItem.getPeriod().contains(baseDate)).findFirst();
			if (optClassHistItem.isPresent()) {
				result.get().setClassificationCode(optClassHistItem.get().getClassificationCode());
			}
		} else {
			result.get().setClassificationCode(null);
		}
	}

	/**
	 * 職位IDを取得する
	 * 
	 * @param exeId
	 * @param employeeId
	 * @param baseDate
	 * @param result
	 * @param mapJobTitleHist
	 * @return
	 */
	private boolean acquireJobTitleId(String exeId, String employeeId, GeneralDate baseDate,
			Optional<ScheduleMasterInformationDto> result,
			Map<String, List<ExJobTitleHistItemImported>> mapJobTitleHist) {
		// EA No1680
		List<ExJobTitleHistItemImported> listJobTitleHistItem = mapJobTitleHist.get(employeeId);
		if (listJobTitleHistItem != null) {
			Optional<ExJobTitleHistItemImported> optJobTitleHistItem = listJobTitleHistItem.stream()
					.filter(jobTitleHistItem -> jobTitleHistItem.getPeriod().contains(baseDate)).findFirst();
			if (optJobTitleHistItem.isPresent()) {
				result.get().setJobId(optJobTitleHistItem.get().getJobTitleId());
				return true;
			}
		}
		
		ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(this.getErrorContent("Msg_602", "#Com_Jobtitle"),
				exeId, baseDate, employeeId);
		this.scheduleErrorLogRepository.add(scheduleErrorLog);
		
		return false;
	}

	/**
	 * 職場IDを取得する
	 * 
	 * @param exeId
	 * @param employeeId
	 * @param baseDate
	 * @param result
	 * @param mapWorkplaceHist
	 * @return
	 */
	private boolean acquireWorkplaceId(String exeId, String employeeId, GeneralDate baseDate,
			Optional<ScheduleMasterInformationDto> result,
			Map<String, List<ExWorkplaceHistItemImported>> mapWorkplaceHist) {
		// EA No1681
		List<ExWorkplaceHistItemImported> listWorkplaceHistItem = mapWorkplaceHist.get(employeeId);
		if (listWorkplaceHistItem != null) {
			Optional<ExWorkplaceHistItemImported> optWorkplaceHistItem = listWorkplaceHistItem.stream()
					.filter(workplaceHistItem -> workplaceHistItem.getPeriod().contains(baseDate)).findFirst();
			if (optWorkplaceHistItem.isPresent()) {
				result.get().setWorkplaceId(optWorkplaceHistItem.get().getWorkplaceId());
				return true;
			}
		}
		
		ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(this.getErrorContent("Msg_602", "#Com_Workplace"),
				exeId, baseDate, employeeId);
		this.scheduleErrorLogRepository.add(scheduleErrorLog);
		
		return false;

	}

	/**
	 * 
	 * 勤務種別コードを取得する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @param result
	 */

	private void acquireWorkTypeCode(String employeeId, GeneralDate baseDate,
			Optional<ScheduleMasterInformationDto> result, List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis) {
		// ドメインモデル「社員の勤務種別の履歴」を取得する
		// EA No2022
		Optional<BusinessTypeOfEmpDto> businessTypeOfEmpHis = listBusTypeOfEmpHis.stream()
				.filter(x -> (x.getEmployeeId().equals(employeeId) && x.getStartDate().beforeOrEquals(baseDate)
						&& x.getEndDate().afterOrEquals(baseDate)))
				.findFirst();

		if (!businessTypeOfEmpHis.isPresent()) {
			result.get().setBusinessTypeCode(null);
			return;
		}

		result.get().setBusinessTypeCode(businessTypeOfEmpHis.get().getBusinessTypeCd());
	}

	/**
	 * 
	 * @param messageId
	 * @param paramMsg
	 * @return error content
	 */
	private String getErrorContent(String messageId, String paramMsg) {
		return internationalization.localize(messageId, paramMsg).get();
	}
}
