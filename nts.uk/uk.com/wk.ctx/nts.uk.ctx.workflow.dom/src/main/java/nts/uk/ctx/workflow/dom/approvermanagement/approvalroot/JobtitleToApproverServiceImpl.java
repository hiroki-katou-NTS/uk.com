package nts.uk.ctx.workflow.dom.approvermanagement.approvalroot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.ConcurrentEmployeeImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.JobTitleImport;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSetRepository;
import nts.uk.ctx.workflow.dom.service.output.ApproverInfo;

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
		boolean isApper = checkIfApproverIsGreaterThanRequester(cid, sid, baseDate, jobTitleId);
		if (isApper) {
			WorkplaceImport wkpInfor = this.wkApproverAdapter.findBySid(sid, baseDate);
			// thực hiện xử lý 「職場に指定する職位の対象者を取得する」
			List<ApproverInfo> approvers = this.getByWkp(cid, wkpInfor.getWkpId(), baseDate, jobTitleId);
			if (!CollectionUtil.isEmpty(approvers)) {
				return approvers;
			}

			// lấy domain 「職位別のサーチ設定」
			boolean needsSearch = this.jobtitleSearchSetRepository.finById(cid, jobTitleId)
						.map(s -> s.needsSearch())
							.orElse(false);
			if (needsSearch) {
				//List<String> wkpIds = this.employeeAdapter.findWpkIdsBySid(cid, sid, baseDate);
				List<String> wkpIds = this.employeeAdapter.getWorkplaceIdAndUpper(cid, baseDate, wkpInfor.getWkpId());
				wkpIds.remove(0);
				
				// 上位職場が存在する(not exist wkpId 上位)
				if (CollectionUtil.isEmpty(wkpIds)) {
					Collections.emptyList();
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

		return Collections.emptyList();
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
	private boolean checkIfApproverIsGreaterThanRequester(String cid, String sid, GeneralDate baseDate, String jobTitleId) {
		JobTitleImport jobOfEmp = this.syJobTitleAdapter.findJobTitleBySid(sid, baseDate);
		// 承認者の
		JobTitleImport jobOfApprover = this.syJobTitleAdapter.findJobTitleByPositionId(cid, jobTitleId, baseDate);		
		if (jobOfEmp != null) {
			// 申請の
			JobTitleImport jobOfRequest = this.syJobTitleAdapter.findJobTitleByPositionId(cid, jobOfEmp.getPositionId(), baseDate);
			if (jobOfApprover == null || jobOfRequest == null) {
				return false;
			}
			if (jobOfApprover.isGreaterThan(jobOfRequest)) {
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
		
		// 承認者の
		List<ConcurrentEmployeeImport> employeeList = this.employeeAdapter.getConcurrentEmployee(cid, jobTitleId,
				baseDate);
		Optional<JobAssignSetting> assignSet = this.jobAssignSetRepository.findById();
		if (assignSet.get().getIsConcurrently()) {
			// 本務兼務区分が兼務の対象者を除く
			employeeList.removeIf(x -> x.isConcurrent());
		}
		
		List<ApproverInfo> approvers = new ArrayList<>();
		for (ConcurrentEmployeeImport emp : employeeList) {
			WorkplaceImport wkpIdOfEmp = this.wkApproverAdapter.findBySid(emp.getEmployeeId(), baseDate);
			if (wkpId.equals(wkpIdOfEmp.getWkpId())) {
				// truyền tạm approvalAtr = 1
				approvers.add(ApproverInfo.create(emp));
			}
		}
		return approvers;
	}
}
