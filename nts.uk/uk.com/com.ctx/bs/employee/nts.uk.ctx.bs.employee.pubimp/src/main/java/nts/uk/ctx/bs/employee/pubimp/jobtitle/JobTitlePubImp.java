/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.jobtitle;
/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitle;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.JobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceCode;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMasterRepository;
import nts.uk.ctx.bs.employee.pub.jobtitle.AffJobTitleBasicExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.AffJobTitleHistoryExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.EmployeeJobHistExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.JobTitleExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SimpleJobTitleExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JobTitlePubImp.
 */
@Stateless
public class JobTitlePubImp implements SyJobTitlePub {

	/** The first item index. */
	private static final int FIRST_ITEM_INDEX = 0;

	/** The job title repository. */
	@Inject
	private JobTitleInfoRepository jobTitleInfoRepository;

	/** The job title repository. */
	@Inject
	private JobTitleRepository jobTitleRepository;

	/** The sequence master repository. */
	@Inject
	private SequenceMasterRepository sequenceMasterRepository;

	@Inject
	private AffJobTitleHistoryRepository affJobTitleHisRepo;

	@Inject
	private AffJobTitleHistoryItemRepository affJobTitleHisItemRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.EmployeePub#findJobTitleBySid(String)
	 */
	@Override
	public List<JobTitleExport> findJobTitleBySid(String employeeId) {
		// Query
		List<AffJobTitleHistory> affJobTitleHistories = this.affJobTitleHisRepo.getAllBySid(employeeId);
		List<AffJobTitleHistoryItem> affJobTitleHistoryItem = this.affJobTitleHisItemRepo
				.getAllBySid(employeeId);

		Map<String, List<Object>> mapMerge = new HashMap<>();
		Map<String, DatePeriod> mapHisIDDate = new HashMap<>();

		/* Change list AffJobTitleHistory to map */
		affJobTitleHistories.stream().forEach((jobEmp) -> {
			jobEmp.getHistoryItems().stream().forEach((dateHistory) -> {
				mapHisIDDate.put(dateHistory.identifier(), dateHistory.span());
			});
		});

		/* Matching history in mapHisIDDate with list affJobTitleHistoryItem */
		affJobTitleHistoryItem.stream().forEach((temp2) -> {
			String hisId = temp2.getHistoryId();
			if (mapHisIDDate.get(hisId) != null) {
				mapMerge.put(hisId, Arrays.asList(new Object[] { mapHisIDDate.get(hisId).start(), temp2.getJobTitleId(),
						mapHisIDDate.get(hisId).end() }));
			}
		});

		String companyId = AppContexts.user().companyId();

		// Return
		List<JobTitleExport> lstJobTitleExport = mapMerge.entrySet().stream().map(e -> {
			JobTitleInfo jobTitleInfo = this.jobTitleInfoRepository
					.find(companyId, e.getValue().get(1).toString(), (GeneralDate) e.getValue().get(0)).get();
			return JobTitleExport.builder().companyId(jobTitleInfo.getCompanyId().v())
					.jobTitleId(jobTitleInfo.getJobTitleId())
					.jobTitleCode(jobTitleInfo.getJobTitleCode().v())
					.jobTitleName(jobTitleInfo.getJobTitleName().v())
					.sequenceCode(jobTitleInfo.getSequenceCode().v())
					.startDate((GeneralDate) e.getValue().get(0))
					.endDate((GeneralDate) e.getValue().get(2))
					.isManager(jobTitleInfo.isManager())
					.build();
		}).collect(Collectors.toList());
		return lstJobTitleExport;
	}

	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.employee.jobtitle.SyJobTitlePub#
	 * findJobTitleByJobTitleId(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<JobTitleExport> findByJobId(String companyId, String jobTitleId, GeneralDate baseDate) {

		// Query
		Optional<JobTitleInfo> optJobInfo = this.jobTitleInfoRepository.find(companyId, jobTitleId, baseDate);

		if(!optJobInfo.isPresent()) {
			return Optional.empty();
		}
		
		JobTitleInfo jobInfo = optJobInfo.get();
				
		Optional<JobTitle> optJobTitle = this.jobTitleRepository.findByHistoryId(companyId, jobInfo.getJobTitleHistoryId());

		if(!optJobTitle.isPresent()) {
			return Optional.empty();
		}
		
		JobTitle jobTitle = optJobTitle.get();
		
		if(CollectionUtil.isEmpty(jobTitle.getJobTitleHistories())) {
			return Optional.empty();
		}
				
		JobTitleHistory jobTitleHistory = jobTitle.getJobTitleHistories().get(FIRST_ITEM_INDEX);

		// Return
		return Optional.of(JobTitleExport.builder().companyId(jobInfo.getCompanyId().v())
				.jobTitleId(jobInfo.getJobTitleId()).jobTitleCode(jobInfo.getJobTitleCode().v())
				.jobTitleName(jobInfo.getJobTitleName().v())
				.sequenceCode(
						jobInfo.getSequenceCode() != null ? jobInfo.getSequenceCode().v() : null)
				.startDate(jobTitleHistory.span().start()).endDate(jobTitleHistory.span().end())
				.isManager(jobInfo.isManager()).build());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub#findByBaseDate(java.
	 * lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<JobTitleExport> findAll(String companyId, GeneralDate baseDate) {
		// Query
		List<JobTitleInfo> jobInfos = this.jobTitleInfoRepository.findAll(companyId, baseDate);

		// Return
		return jobInfos.stream().map(jobInfo -> {
			JobTitle jobTitle = this.jobTitleRepository.findByHistoryId(companyId, jobInfo.getJobTitleHistoryId())
					.get();
			JobTitleHistory jobTitleHistory = jobTitle.getJobTitleHistories().get(FIRST_ITEM_INDEX);
			return JobTitleExport.builder().companyId(jobInfo.getCompanyId().v()).jobTitleId(jobInfo.getJobTitleId())
					.jobTitleCode(jobInfo.getJobTitleCode().v()).jobTitleName(jobInfo.getJobTitleName().v())
					.sequenceCode(jobInfo.getSequenceCode() != null ? jobInfo.getSequenceCode().v() : null)
					.startDate(jobTitleHistory.span().start()).endDate(jobTitleHistory.span().end())
					.isManager(jobInfo.isManager()).build();
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub#findSJobHistBySId(java.lang
	 * .String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<EmployeeJobHistExport> findSJobHistBySId(String employeeId, GeneralDate baseDate) {

		// Query
		Optional<AffJobTitleHistoryItem> optAffJobTitleHistoryItem = affJobTitleHisItemRepo
				.getByEmpIdAndReferDate(employeeId, baseDate);

		// Check exist
		if (!optAffJobTitleHistoryItem.isPresent()) {
			return Optional.empty();
		}

		AffJobTitleHistoryItem affJobTitleHist = optAffJobTitleHistoryItem.get();

		// Query
		Optional<AffJobTitleHistory> optAffJobTitleHistory = affJobTitleHisRepo
				.getListByHidSid(affJobTitleHist.getHistoryId(), employeeId);

		AffJobTitleHistory affJobTitleHistory = optAffJobTitleHistory.get();

		// Query
		Optional<JobTitleInfo> optJobTitleInfo = this.jobTitleInfoRepository.find(affJobTitleHist.getJobTitleId(),
				baseDate);

		// Check exist
		if (!optJobTitleInfo.isPresent()) {
			return Optional.empty();
		}

		JobTitleInfo jobTitleInfo = optJobTitleInfo.get();

		// Return
		return Optional.of(EmployeeJobHistExport.builder().employeeId(affJobTitleHist.getEmployeeId())
				.jobTitleID(jobTitleInfo.getJobTitleId()).jobTitleName(jobTitleInfo.getJobTitleName().v())
				.startDate(affJobTitleHistory.items().get(0).start()).endDate(affJobTitleHistory.items().get(0).end())
				.build());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub#findByIds(java.lang.
	 * String, java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
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

	@Override
	public Optional<AffJobTitleHistoryExport> gerBySidAndBaseDate(String employeeId, GeneralDate baseDate) {
		Optional<AffJobTitleHistory> optAffJobtitle = affJobTitleHisRepo.getByEmpIdAndStandardDate(employeeId,
				baseDate);
		if (!optAffJobtitle.isPresent())
			return Optional.empty();

		AffJobTitleHistory affJob = optAffJobtitle.get();

		if (affJob.getHistoryItems().isEmpty())
			return Optional.empty();

		String hisId = affJob.getHistoryItems().get(0).identifier();

		AffJobTitleHistoryItem optAffJobtitleItem = affJobTitleHisItemRepo.findByHitoryId(hisId).get();

		return Optional.of(new AffJobTitleHistoryExport(affJob.getCompanyId(), affJob.getEmployeeId(), optAffJobtitleItem.getJobTitleId(), affJob.getHistoryItems()));

	}

	@Override
	public Optional<AffJobTitleBasicExport> getBySidAndBaseDate(String sid, GeneralDate baseDate) {
		
		Optional<AffJobTitleHistory> optAffJobtitle = affJobTitleHisRepo.getByEmpIdAndStandardDate(sid,
				baseDate);
		if (!optAffJobtitle.isPresent())
			return Optional.empty();

		AffJobTitleHistory affJob = optAffJobtitle.get();

		if (affJob.getHistoryItems().isEmpty())
			return Optional.empty();

		String hisId = affJob.getHistoryItems().get(0).identifier();

		AffJobTitleHistoryItem optAffJobtitleItem = affJobTitleHisItemRepo.findByHitoryId(hisId).get();
		
		Optional<JobTitleInfo> jobTitleInfoOpt = jobTitleInfoRepository.find(optAffJobtitleItem.getJobTitleId(), baseDate);
		
		if (!jobTitleInfoOpt.isPresent())
			return Optional.empty();
		
		JobTitleInfo jobTitleInfo = jobTitleInfoOpt.get();
		
		return Optional.of(new AffJobTitleBasicExport(jobTitleInfo.getJobTitleId(), jobTitleInfo.getJobTitleCode().v(), jobTitleInfo.getJobTitleName().v()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub#
	 * getJobTitleMapIdBaseDateName(java.lang.String, java.util.List,
	 * java.util.List)
	 */
	@Override
	public Map<Pair<String, GeneralDate>, String> getJobTitleMapIdBaseDateName(String companyId,
			List<String> jobIds, List<GeneralDate> baseDates) {
		// Query infos
		Map<GeneralDate, List<JobTitleInfo>> mapJobTitleInfos = this.jobTitleInfoRepository
				.findByIds(companyId, jobIds, baseDates);

		Map<Pair<String, GeneralDate>, String> mapResult = new HashMap<>();
		mapJobTitleInfos.entrySet().forEach(item -> {
			item.getValue().forEach(jobTitleInfo -> {
				mapResult.put(Pair.of(jobTitleInfo.getJobTitleId(), item.getKey()),
						jobTitleInfo.getJobTitleName().v());
			});
		});

		return mapResult;
	}

}
