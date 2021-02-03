package nts.uk.query.app.ccg005.query.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.comment.EmployeeCommentInformation;
import nts.uk.ctx.office.dom.comment.EmployeeCommentInformationRepository;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.コメント.在席照会に表示コメントを取得.在席照会に表示コメントを取得
 */
@Stateless
public class DisplayCommentQuery {

	@Inject
	private EmployeeCommentInformationRepository repo;

	public Map<String, EmployeeCommentInformationDto> getComment(List<String> sids, GeneralDate date) {
		// 1. get(社員IDリスト、年月日): Map<社員ID、社員のコメント情報>
		Map<String, EmployeeCommentInformationDto> returnMap = new HashMap<>();
		Map<String, EmployeeCommentInformation> map = repo.getByListSidAndDate(sids, date);
		List<String> noCommentSids = new ArrayList<>();
		noCommentSids.addAll(sids);
		// remove employee id that have comment
		map.forEach((key, value) -> {
			if (noCommentSids.contains(key)) {
				noCommentSids.remove(key);
			}
			EmployeeCommentInformationDto dto = new EmployeeCommentInformationDto();
			value.setMemento(dto);
			returnMap.put(key, dto);
		});

		// get newest comment
		noCommentSids.forEach(sid -> {
			Optional<EmployeeCommentInformation> newestComment = repo.getTop1BySid(sid);
			EmployeeCommentInformationDto dto = new EmployeeCommentInformationDto();
			newestComment.ifPresent(value -> value.setMemento(dto));
			returnMap.put(sid, dto);
		});
		return returnMap;
	}
}
