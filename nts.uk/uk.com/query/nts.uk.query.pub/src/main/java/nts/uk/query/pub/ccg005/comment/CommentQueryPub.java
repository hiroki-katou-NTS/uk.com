package nts.uk.query.pub.ccg005.comment;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;

public interface CommentQueryPub {

	/**
	 * 
	 * @param sids
	 * @param date
	 * @return
	 */
	public Map<String, CommentQueryExport> getComment(List<String> sids, GeneralDate date);
}
