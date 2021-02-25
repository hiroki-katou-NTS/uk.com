package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

public interface OtherEmTimezoneLateEarlySetRepository {

	public boolean getStampExaclyByKey(String cid, String worktimeCd, int workFormAtr, int worktimeSetMethod,
			int lateEarlyAtr);
}
