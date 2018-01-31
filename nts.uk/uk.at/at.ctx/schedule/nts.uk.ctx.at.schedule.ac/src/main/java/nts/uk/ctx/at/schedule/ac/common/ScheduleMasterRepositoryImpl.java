package nts.uk.ctx.at.schedule.ac.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm.ScheduleMasterInformationDto;
import nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm.ScheduleMasterInformationRepository;
import nts.uk.ctx.bs.employee.pub.classification.SClsHistExport;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;
import nts.uk.ctx.bs.employee.pub.jobtitle.EmployeeJobHistExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ScheduleMasterRepositoryImpl implements ScheduleMasterInformationRepository {
	@Inject
	SyClassificationPub syClassficationPub;

	@Inject
	private EmploymentAdapter employmentAdapter;
	@Inject
	private SyWorkplacePub syWorkplacePub;

	@Inject
	private SyJobTitlePub syJobTitlePub;
	
	@Inject
	private BusinessTypeEmpOfHistoryRepository bussinessTypeOfHistoryRepo;

	@Inject
	private BusinessTypeOfEmployeeRepository employeeRepo;
	@Override
	public ScheduleMasterInformationDto getScheduleMasterInformationDto(String employeeId, GeneralDate baseDate) {
		ScheduleMasterInformationDto result = new ScheduleMasterInformationDto();
		String companyId = AppContexts.user().companyId();
		// ・Imported「所属雇用履歴」から雇用コードを取得する(lấy 雇用コード từ Imported「所属雇用履歴」)
		Optional<EmploymentHistoryImported> employmentHisOptional = this.employmentAdapter.getEmpHistBySid(companyId,
				employeeId, baseDate);
		if (employmentHisOptional.isPresent()) {
			String employmentCode = employmentHisOptional.get().getEmploymentCode();
			result.setEmployeeCode(employmentCode);
		}
		// Imported「所属分類履歴」から分類コードを取得する(lấy 分類コード từ Imported「所属分類履歴」)
		Optional<SClsHistExport> hisExport = this.syClassficationPub.findSClsHistBySid(companyId, employeeId, baseDate);
		if (hisExport.isPresent()) {
			String classificationCode = hisExport.get().getClassificationCode();
			result.setClassificationCode(classificationCode);
		}
		// Imported「所属職場履歴」から職場IDを取得する(lấy職場ID từ Imported「所属職場履歴」)
		Optional<SWkpHistExport> swkpHisOptional = this.syWorkplacePub.findBySid(employeeId, baseDate);
		if (swkpHisOptional.isPresent()) {
			String workPlaceId = swkpHisOptional.get().getWorkplaceId();
			result.setWorkplaceId(workPlaceId);
		}
		//Imported「所属職位履歴」から職位IDを取得する(lấy 職位ID từ Imported「所属職位履歴」)
		Optional<EmployeeJobHistExport> employeeJobHisOptional = this.syJobTitlePub.findBySid(employeeId, baseDate);
		if (employeeJobHisOptional.isPresent()) {
			String jobId = employeeJobHisOptional.get().getJobTitleID();
			result.setJobId(jobId);
		}
		// ドメインモデル「社員の勤務種別」を取得する(lấy domain 「社員の勤務種別」)
		Optional<BusinessTypeOfEmployeeHistory> employeeHisOptional = this.bussinessTypeOfHistoryRepo.findByBaseDate(baseDate, employeeId);
		if(employeeHisOptional.isPresent()){
			if(employeeHisOptional.get().getHistory().size()==1){
				String historyId = employeeHisOptional.get().getHistory().get(0).identifier();
				Optional<BusinessTypeOfEmployee> employeeOptional = this.employeeRepo.findByHistoryId(historyId);
				if(employeeOptional.isPresent()){
					String businessTypeCode = employeeOptional.get().getBusinessTypeCode().v();
					result.setBusinessTypeCode(businessTypeCode);
				}
			}
		}
		return result;
	}

}
