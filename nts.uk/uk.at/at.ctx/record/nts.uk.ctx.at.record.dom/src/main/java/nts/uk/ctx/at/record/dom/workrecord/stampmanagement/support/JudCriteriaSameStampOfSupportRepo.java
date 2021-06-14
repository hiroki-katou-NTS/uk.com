package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import java.util.List;

// 応援カード編集設定Repository
public interface JudCriteriaSameStampOfSupportRepo {

	// [1] insert(応援カード編集設定)
	public void insert(List<JudgmentCriteriaSameStampOfSupport> domains);

	// [2] update(応援カード編集設定)
	public void update(List<JudgmentCriteriaSameStampOfSupport> domains);
	
	//[2] get
	public JudgmentCriteriaSameStampOfSupport get(String cid);
	
	public void delete(List<JudgmentCriteriaSameStampOfSupport> domains);
}
