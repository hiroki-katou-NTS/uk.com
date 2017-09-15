package nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 基準日時点の対象の職場IDに一致する職場コードと職場名称を取得する
 * @author dudt
 *
 */
public interface WorkplaceApproverAdaptor {
	List<WorkplaceApproverDto> findByWkpId(String companyId, String workplaceId, GeneralDate baseDate);
}
