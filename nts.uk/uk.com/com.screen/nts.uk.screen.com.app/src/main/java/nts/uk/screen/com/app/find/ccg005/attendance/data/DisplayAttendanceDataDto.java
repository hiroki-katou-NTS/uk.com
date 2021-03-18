package nts.uk.screen.com.app.find.ccg005.attendance.data;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.screen.com.app.find.ccg005.attendance.information.AttendanceInformationDto;
import nts.uk.screen.com.app.find.ccg005.favorite.information.FavoriteSpecifyDto;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.表示初期の在席データ.表示初期の在席データDTO
 */
@Data
@Builder
public class DisplayAttendanceDataDto {
	//お気に入りの指定
	private List<FavoriteSpecifyDto> favoriteSpecifyDto;
	
	//在席情報DTO
	private List<AttendanceInformationDto> attendanceInformationDtos;
	
	//感情状態を利用する
	private Integer emojiUsage;
	
	//担当者か
	private boolean isInCharge;
	
	//申請名
	private List<ApplicationNameDto> applicationNameDtos;
	
	//自分のビジネスネーム
	private String bussinessName;
}
