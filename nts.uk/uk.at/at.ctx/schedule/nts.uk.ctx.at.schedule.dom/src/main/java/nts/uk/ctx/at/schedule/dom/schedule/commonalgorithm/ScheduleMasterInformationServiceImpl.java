package nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SClsHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.employment.EmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.employment.ScEmploymentAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.EmployeeJobHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SWkpHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmp;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpAdaptor;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpHis;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpHisAdaptor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * 勤務予定マスタ情報を取得する(lấy các thông tin master 勤務予定マスタ情報)
 * 
 * @author sonnh1
 *
 */
@Stateless
public class ScheduleMasterInformationServiceImpl implements ScheduleMasterInformationService {

	@Inject
	private ScEmploymentAdapter scEmploymentAdapter;

	@Inject
	private ScheduleErrorLogRepository scheduleErrorLogRepository;

	@Inject
	private I18NResourcesForUK internationalization;

	@Inject
	private SyClassificationAdapter syClassificationAdapter;

	@Inject
	private SyJobTitleAdapter syJobTitleAdapter;

	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;

	@Inject
	private BusinessTypeOfEmpHisAdaptor businessTypeOfEmpHisAdaptor;

	@Inject
	private BusinessTypeOfEmpAdaptor businessTypeOfEmpAdaptor;

	@Override
	public Optional<ScheduleMasterInformationDto> getScheduleMasterInformationDto(String employeeId,
			GeneralDate baseDate, String exeId) {
		String companyId = AppContexts.user().companyId();
		Optional<ScheduleMasterInformationDto> result = Optional.of(new ScheduleMasterInformationDto());

		// 雇用コードを取得する
		boolean valueSetEmpCode = this.setEmployeeCode(companyId, exeId, employeeId, baseDate, result);
		if (!valueSetEmpCode) {
			return Optional.empty();
		}
		// 分類コードを取得する
		this.getClassificationCode(companyId, employeeId, baseDate, result);

		// 職位IDを取得する
		boolean valueAcquireJobTitleId = this.acquireJobTitleId(companyId, exeId, employeeId, baseDate, result);
		if (!valueAcquireJobTitleId) {
			return Optional.empty();
		}

		// 職場IDを取得する
		boolean valueAcquireWorkplaceId = this.acquireWorkplaceId(companyId, exeId, employeeId, baseDate, result);
		if (!valueAcquireWorkplaceId) {
			return Optional.empty();
		}

		// 勤務種別コードを取得する
		this.acquireWorkTypeCode(employeeId, baseDate, result);

		return result;
	}

	/**
	 * 雇用コードを取得する
	 * 
	 * @param companyId
	 * @param exeId
	 * @param employeeId
	 * @param baseDate
	 * @param result
	 * @return
	 */
	private boolean setEmployeeCode(String companyId, String exeId, String employeeId, GeneralDate baseDate,
			Optional<ScheduleMasterInformationDto> result) {
		// Imported「所属雇用履歴」から雇用コードを取得する(lấy 雇用コード từ Imported「所属雇用履歴」)
		Optional<EmploymentHistoryImported> employmentHisOptional = this.scEmploymentAdapter.getEmpHistBySid(companyId,
				employeeId, baseDate);
		if (employmentHisOptional.isPresent()) {
			String employmentCode = employmentHisOptional.get().getEmploymentCode();
			result.get().setEmployeeCode(employmentCode);
			return true;
		} else {
			ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(this.getErrorContent("Msg_602", "#Com_Employment"),
					exeId, baseDate, employeeId);
			this.scheduleErrorLogRepository.add(scheduleErrorLog);
			return false;
		}
	}

	/**
	 * 分類コードを取得する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @param result
	 */
	private void getClassificationCode(String companyId, String employeeId, GeneralDate baseDate,
			Optional<ScheduleMasterInformationDto> result) {
		// Imported「所属分類履歴」から分類コードを取得する(lấy 分類コード từ Imported「所属分類履歴」)
		Optional<SClsHistImported> hisExport = this.syClassificationAdapter.findSClsHistBySid(companyId, employeeId,
				baseDate);
		if (hisExport.isPresent()) {
			String classificationCode = hisExport.get().getClassificationCode();
			result.get().setClassificationCode(classificationCode);
		} else {
			result.get().setClassificationCode(null);
		}
	}

	/**
	 * 職位IDを取得する
	 * 
	 * @param companyId
	 * @param exeId
	 * @param employeeId
	 * @param baseDate
	 * @param result
	 * @return
	 */
	private boolean acquireJobTitleId(String companyId, String exeId, String employeeId, GeneralDate baseDate,
			Optional<ScheduleMasterInformationDto> result) {
		// Imported「所属職位履歴」から職位IDを取得する(lấy 職位ID từ Imported「所属職位履歴」)
		Optional<EmployeeJobHistImported> employeeJobHisOptional = this.syJobTitleAdapter.findBySid(employeeId,
				baseDate);
		if (employeeJobHisOptional.isPresent()) {
			String jobId = employeeJobHisOptional.get().getJobTitleID();
			result.get().setJobId(jobId);
			return true;
		} else {
			ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(this.getErrorContent("Msg_602", "#Com_Jobtitle"),
					exeId, baseDate, employeeId);
			this.scheduleErrorLogRepository.add(scheduleErrorLog);
			return false;
		}
	}

	/**
	 * 職場IDを取得する
	 * 
	 * @param companyId
	 * @param exeId
	 * @param employeeId
	 * @param baseDate
	 * @param result
	 * @return
	 */
	private boolean acquireWorkplaceId(String companyId, String exeId, String employeeId, GeneralDate baseDate,
			Optional<ScheduleMasterInformationDto> result) {
		// Imported「所属職場履歴」から職場IDを取得する(lấy職場ID từ Imported「所属職場履歴」)
		Optional<SWkpHistImported> swkpHisOptional = this.syWorkplaceAdapter.findBySid(employeeId, baseDate);
		if (swkpHisOptional.isPresent()) {
			String workPlaceId = swkpHisOptional.get().getWorkplaceId();
			result.get().setWorkplaceId(workPlaceId);
			return true;
		} else {
			ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(this.getErrorContent("Msg_602", "#Com_Workplace"),
					exeId, baseDate, employeeId);
			this.scheduleErrorLogRepository.add(scheduleErrorLog);
			return false;
		}
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
			Optional<ScheduleMasterInformationDto> result) {
		// ドメインモデル「社員の勤務種別の履歴」を取得する
		Optional<BusinessTypeOfEmpHis> businessTypeOfEmpHis = this.businessTypeOfEmpHisAdaptor
				.findByBaseDateAndSid(baseDate, employeeId);
		if (!businessTypeOfEmpHis.isPresent()) {
			result.get().setBusinessTypeCode(null);
			return;
		}
		// ドメインモデル「社員の勤務種別」を取得する
		Optional<BusinessTypeOfEmp> businessTypeOfEmp = this.businessTypeOfEmpAdaptor.getBySidAndHistId(employeeId,
				businessTypeOfEmpHis.get().getHistoryId());
		if (!businessTypeOfEmp.isPresent()) {
			result.get().setBusinessTypeCode(null);
			return;
		}

		result.get().setBusinessTypeCode(businessTypeOfEmp.get().getBusinessTypeCode());
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
