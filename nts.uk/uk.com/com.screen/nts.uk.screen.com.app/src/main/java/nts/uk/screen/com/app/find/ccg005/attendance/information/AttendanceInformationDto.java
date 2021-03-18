package nts.uk.screen.com.app.find.ccg005.attendance.information;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.screen.com.app.find.ccg005.attendance.information.dto.ApplicationDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.dto.AttendanceDetailDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.dto.EmployeeCommentInformationDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.dto.EmployeeEmojiStateDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.dto.UserAvatarDto;
import nts.uk.screen.com.app.find.ccg005.goout.GoOutEmployeeInformationDto;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.在席情報を取得.在席情報DTO
 */
@Builder
@Data
public class AttendanceInformationDto {
	//申請
	private List<ApplicationDto> applicationDtos;
	
	//社員ID
	private String sid;
	
	//詳細出退勤
	private AttendanceDetailDto attendanceDetailDto;
	
	//個人の顔写真
	private UserAvatarDto avatarDto;
	
	//在席のステータス
	private Integer activityStatusDto;
	
	//社員のコメント情報
	private EmployeeCommentInformationDto commentDto;
	
	//社員の外出情報
	private GoOutEmployeeInformationDto goOutDto;
	
	//社員の感情状態
	private EmployeeEmojiStateDto emojiDto;

}
