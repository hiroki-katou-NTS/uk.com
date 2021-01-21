package nts.uk.query.pubimp.ccg005.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.query.app.ccg005.query.comment.DisplayCommentQuery;
import nts.uk.query.app.ccg005.query.comment.EmployeeCommentInformationDto;
import nts.uk.query.pub.ccg005.comment.CommentQueryExport;
import nts.uk.query.pub.ccg005.comment.CommentQueryPub;

@Stateless
public class CommentQueryPubImpl implements CommentQueryPub {

	@Inject
	private DisplayCommentQuery query;

	@Override
	public Map<String, CommentQueryExport> getComment(List<String> sids, GeneralDate date) {
		Map<String, EmployeeCommentInformationDto> map = query.getComment(sids, date);
		Map<String, CommentQueryExport> returnMap = new HashMap<>();
		map.forEach((key, value) -> {
			CommentQueryExport val = CommentQueryExport.builder()
					.comment(value.getComment())
					.date(value.getDate())
					.sid(value.getSid()).build();
			returnMap.put(key, val);
		});
		return returnMap;
	}
}
