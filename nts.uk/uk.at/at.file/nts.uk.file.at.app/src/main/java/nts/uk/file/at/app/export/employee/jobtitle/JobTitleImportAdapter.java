package nts.uk.file.at.app.export.employee.jobtitle;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceCode;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMasterRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class JobTitleImportAdapter {
	@Inject
	private JobTitleInfoRepository jobTitleInfoRepository;

	/** The sequence master repository. */
	@Inject
	private SequenceMasterRepository sequenceMasterRepository;
	
	@Inject
	private AffJobTitleHistoryRepository affJobTitleHisRepo;
	
	@Inject
	private AffJobTitleHistoryItemRepository affJobTitleHisItemRepo;
	
	public Optional<EmployeeJobHistExport> findBySid(String employeeId, GeneralDate baseDate) {
		// Query
		Optional<AffJobTitleHistory> optAffJobTitleHist = this.affJobTitleHisRepo
				.getByEmpIdAndStandardDate(employeeId, baseDate);
	
		if (optAffJobTitleHist.isPresent()) {
	
			DateHistoryItem dateHistoryItem = optAffJobTitleHist.get().getHistoryItems().get(0);
	
			AffJobTitleHistoryItem affJobTitleHistItem = affJobTitleHisItemRepo
					.findByHitoryId(dateHistoryItem.identifier()).get();
	
			// Get information of employee
			String companyId = AppContexts.user().companyId();
	
			List<SimpleJobTitleExport> simpleJobTitleExports = findByIds(companyId,
					Arrays.asList(affJobTitleHistItem.getJobTitleId()), baseDate);
	
			if (!simpleJobTitleExports.isEmpty()) {
				SimpleJobTitleExport simpleJobTitleExport = simpleJobTitleExports.get(0);
				EmployeeJobHistExport jobTitleExport = EmployeeJobHistExport.builder().employeeId(employeeId)
						.jobTitleID(simpleJobTitleExport.getJobTitleId())
						.jobTitleName(simpleJobTitleExport.getJobTitleName()).startDate(dateHistoryItem.start())
						.endDate(dateHistoryItem.end()).build();
				// Return
				return Optional.of(jobTitleExport);
			}
		}
	
		return Optional.empty();
	}
	
	public List<SimpleJobTitleExport> findByIds(String companyId, List<String> jobIds, GeneralDate baseDate) {
		// Query infos
		List<JobTitleInfo> jobTitleInfos = this.jobTitleInfoRepository.findByIds(companyId, jobIds, baseDate);

		List<SequenceMaster> seqMasters = this.sequenceMasterRepository.findByCompanyId(companyId);

		Map<SequenceCode, Integer> seqMasterMap = seqMasters.stream()
				.collect(Collectors.toMap(SequenceMaster::getSequenceCode, SequenceMaster::getOrder));

		// Return
		return jobTitleInfos.stream()
				.map(item -> SimpleJobTitleExport.builder().jobTitleId(item.getJobTitleId())
						.jobTitleCode(item.getJobTitleCode().v()).jobTitleName(item.getJobTitleName().v())
						.disporder(seqMasterMap.get(item.getSequenceCode())).build())
				.collect(Collectors.toList());
	}
}
