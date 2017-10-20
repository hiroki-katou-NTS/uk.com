package nts.uk.ctx.workflow.dom.approvermanagement.approvalroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.ConcurrentEmployeeImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.JobTitleImport;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.ApproverInfo;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSet;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSetRepository;

/**
 * 3.職位から承認者へ変換する
 * 
 * @author vunv
 *
 */
@Stateless
public class JobtitleToApproverServiceImpl implements JobtitleToApproverService {

	@Inject
	private EmployeeAdapter employeeAdapter;
	@Inject
	private JobtitleSearchSetRepository jobtitleSearchSetRepository;
	@Inject
	private SyJobTitleAdapter syJobTitleAdapter;
	@Inject
	private JobAssignSettingRepository jobAssignSetRepository;
	@Inject
	private WorkplaceApproverAdapter wkApproverAdapter;

	/**
	 * 3.職位から承認者へ変換する
	 * 
	 */
	@Override
	public List<ApproverInfo> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId) {
		// 共通アルゴリズム「申請者の職位の序列は承認者のと比較する」を実行する
		boolean isApper = compareRank(cid, sid, baseDate, jobTitleId);
		if (isApper) {
			String wkpId = this.wkApproverAdapter.getWorkplaceId(cid, sid, baseDate);
			// thực hiện xử lý 「職場に指定する職位の対象者を取得する」
			List<ApproverInfo> approvers = this.getByWkp(cid, wkpId, baseDate, jobTitleId);
			if (!CollectionUtil.isEmpty(approvers)) {
				return approvers;
			}

			// lấy domain 「職位別のサーチ設定」
			Optional<JobtitleSearchSet> job = this.jobtitleSearchSetRepository.finById(cid, jobTitleId);
			if (job.isPresent()) {
				List<String> wkpIds = this.employeeAdapter.findWpkIdsBySid(cid, sid, baseDate);
				wkpIds.remove(0);

				// 上位職場が存在する(not exist wkpId 上位)
				if (CollectionUtil.isEmpty(wkpIds)) {
					return null;
				}

				// 上位職場の先頭から最後ループ
				for (String id : wkpIds) {
					// thực hiện xử lý 「職場に指定する職位の対象者を取得する」
					List<ApproverInfo> approversByWkp = this.getByWkp(cid, id, baseDate, jobTitleId);
					// If exist break and return
					if (!CollectionUtil.isEmpty(approversByWkp)) {
						return approversByWkp;
					}
				}
			}
		}

		return null;
	}

	/**
	 * 4.申請者の職位の序列は承認者のと比較する
	 * 
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param jobTitleId
	 * @return
	 */
	private boolean compareRank(String cid, String sid, GeneralDate baseDate, String jobTitleId) {
		JobTitleImport jobOfEmp = this.syJobTitleAdapter.findJobTitleBySid(sid, baseDate);
		// 承認者の
		JobTitleImport jobOfApprover = this.syJobTitleAdapter.findJobTitleByPositionId(cid, jobTitleId, baseDate);
		if (jobOfEmp != null) {
			// 申請の
			JobTitleImport jobOfRequest = this.syJobTitleAdapter.findJobTitleByPositionId(cid, jobOfEmp.getPositionId(),
					baseDate);
			if (jobOfApprover == null || jobOfRequest == null) {
				return false;
			}
			if (jobOfRequest.getSequenceCode().compareTo(jobOfApprover.getSequenceCode()) < 0) {
				return true;
			}

		}

		return false;
	}

	/**
	 * 6.職場に指定する職位の対象者を取得する
	 * 
	 * @param cid
	 * @param wkpId
	 *            職場ID（申請本人の所属職場）
	 * @param baseDate
	 * @param jobTitleId
	 *            職位ID（承認者）
	 * @return
	 */
	private List<ApproverInfo> getByWkp(String cid, String wkpId, GeneralDate baseDate, String jobTitleId) {
		List<ApproverInfo> approvers = new ArrayList<>();
		// 承認者の
		List<ConcurrentEmployeeImport> employeeList = this.employeeAdapter.getConcurrentEmployee(cid, jobTitleId,
				baseDate);
		JobAssignSetting assignSet = this.jobAssignSetRepository.findById(cid);
		if (assignSet.getIsConcurrently()) {
			// 本務兼務区分が兼務の対象者を除く
			List<ConcurrentEmployeeImport> concurrentList = employeeList.stream().filter(x -> {
				return x.getJobCls() == 1;
			}).collect(Collectors.toList());
			employeeList.removeAll(concurrentList);
		}

		for (ConcurrentEmployeeImport emp : employeeList) {
			String wkpIdOfEmp = this.wkApproverAdapter.getWorkplaceId(cid, emp.getEmployeeId(), baseDate);
			if (wkpId.equals(wkpIdOfEmp)) {
				// truyền tạm approvalAtr = 1
				approvers.add(new ApproverInfo(emp.getJobId(), emp.getEmployeeId(), null, null, null,
						emp.getPersonName(), ApprovalAtr.JOB_TITLE));
			}
		}
		return approvers;
	}
}
