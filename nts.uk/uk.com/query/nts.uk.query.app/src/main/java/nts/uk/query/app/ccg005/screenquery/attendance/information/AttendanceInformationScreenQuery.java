package nts.uk.query.app.ccg005.screenquery.attendance.information;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.personal.avatar.AvatarRepository;
import nts.uk.ctx.bs.person.dom.person.personal.avatar.UserAvatar;
import nts.uk.ctx.health.dom.emoji.employee.EmployeeEmojiState;
import nts.uk.ctx.health.dom.emoji.employee.EmployeeEmojiStateRepository;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformationRepository;
import nts.uk.ctx.office.dom.status.service.AttendanceStatusJudgmentService;
import nts.uk.query.app.ccg005.query.comment.DisplayCommentQuery;
import nts.uk.query.app.ccg005.query.comment.EmployeeCommentInformationDto;
import nts.uk.query.app.ccg005.query.work.information.EmployeeWorkInformationDto;
import nts.uk.query.app.ccg005.query.work.information.WorkInformationQuery;
import nts.uk.query.app.ccg005.screenquery.goout.GoOutEmployeeInformationDto;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.在席情報を取得.在席情報を取得
 */
public class AttendanceInformationScreenQuery {

	private WorkInformationQuery workInforQuery;

	private DisplayCommentQuery commentQuery;

	@Inject
	private GoOutEmployeeInformationRepository goOutRepo;

	@Inject
	private EmployeeEmojiStateRepository emojiRepo;

	@Inject
	private AvatarRepository avatarRepo;

	private List<EmployeeEmojiState> emojiList = Collections.emptyList();
	
	public List<AttendanceInformationDto> getAttendanceInformation(List<String> sids, GeneralDate baseDate,
			boolean emojiUsage) {
		// 1: 出退勤・申請情報を取得(Require, 社員ID, 年月日): List<出退勤・申請情報>
		List<EmployeeWorkInformationDto> workInfor = workInforQuery.getWorkInformationQuery(sids, baseDate);

		// TODO 2: 在席のステータスの判断(Require, 社員ID, 年月日, 日別実績の勤務情報, 日別実績の出退勤, 勤務種類):
		// Optional<ステータス分類>
		// AttendanceStatusJudgmentService

		// TODO 3: 申請情報を取得する(社員IDリスト, 期間, 反映状態リスト): Map<社員ID、List<申請>>

		// 4: コメントを取得する(社員ID, 年月日): Map<社員ID、社員のコメント情報>
		Map<String, EmployeeCommentInformationDto> commentData = commentQuery.getComment(sids, baseDate);

		// TODO 5: get(社員IDリスト、年月日): List<社員の外出情報>
		List<GoOutEmployeeInformation> goOutList = goOutRepo.getByListSidAndDate(sids, baseDate);

		// TODO 6: [感情状態を利用する＝する]: get(社員IDリスト、年月日): List<社員の感情状態>
		if (emojiUsage) {
			emojiList = emojiRepo.getByListSidAndDate(sids, baseDate);
		}
		// TODO 7: get(個人IDリスト): List<個人の顔写真>
		List<UserAvatar> avatarList = avatarRepo.getAvatarByPersonalIds(Collections.emptyList()); // TODO pids

		// TODO 8: create()
		return sids.stream().map(sid -> {
			//commentDto
			EmployeeCommentInformationDto commentDto = commentData.get(sid);
			//goOutDto
			List<GoOutEmployeeInformation> filterListGoOut = goOutList.stream().filter(goOut -> goOut.getSid() == sid).collect(Collectors.toList());
			GoOutEmployeeInformationDto goOutDto = GoOutEmployeeInformationDto.builder().build();
			if(!filterListGoOut.isEmpty()) {
				filterListGoOut.get(0).setMemento(goOutDto);
			}
			//emojiDto
			List<EmployeeEmojiState> filterListEmoji = emojiList.stream().filter(emoji -> emoji.getSid() == sid).collect(Collectors.toList());
			EmployeeEmojiStateDto emojiDto = EmployeeEmojiStateDto.builder().build();
			if(!filterListEmoji.isEmpty()) {
				filterListEmoji.get(0).setMemento(emojiDto);
			}

			return AttendanceInformationDto.builder()
					//
					.sid(sid)
					//
					//.avatar
					//.status
					.commentDto(commentDto)
					.goOutDto(goOutDto)
					.emojiDto(emojiDto)
					.build();
		}).collect(Collectors.toList());
	}
}
