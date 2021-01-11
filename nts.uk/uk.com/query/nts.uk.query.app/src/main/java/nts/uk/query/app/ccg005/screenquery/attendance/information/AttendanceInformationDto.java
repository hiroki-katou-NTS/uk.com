package nts.uk.query.app.ccg005.screenquery.attendance.information;

import lombok.Builder;
import lombok.Data;
import nts.uk.query.app.ccg005.query.comment.EmployeeCommentInformationDto;
import nts.uk.query.app.ccg005.screenquery.goout.GoOutEmployeeInformationDto;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.在席情報を取得.在席情報DTO
 */
@Builder
@Data
public class AttendanceInformationDto {
	//申請
	
	
	//社員ID
	private String sid;
	
	//詳細出退勤
	
	
	//個人の顔写真
	
	
	//在席のステータス
	
	
	//社員のコメント情報
	private EmployeeCommentInformationDto commentDto;
	
	//社員の外出情報
	private GoOutEmployeeInformationDto goOutDto;
	
	//社員の感情状態
	private EmployeeEmojiStateDto emojiDto;

}
