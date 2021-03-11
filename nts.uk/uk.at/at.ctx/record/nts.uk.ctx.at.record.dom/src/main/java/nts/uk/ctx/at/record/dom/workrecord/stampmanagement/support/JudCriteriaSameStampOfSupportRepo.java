package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import java.util.List;

public interface JudCriteriaSameStampOfSupportRepo {

	public JudgmentCriteriaSameStampOfSupport get(String cid);
	
	public void update(List<JudgmentCriteriaSameStampOfSupport> domains);
	
	public void insert(List<JudgmentCriteriaSameStampOfSupport> domains);
	
	public void delete(List<JudgmentCriteriaSameStampOfSupport> domains);
}
