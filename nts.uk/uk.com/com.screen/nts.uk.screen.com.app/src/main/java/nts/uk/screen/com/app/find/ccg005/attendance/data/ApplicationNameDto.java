package nts.uk.screen.com.app.find.ccg005.attendance.data;

import lombok.Builder;
import lombok.Data;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.表示初期の在席データ.申請名
 */
@Builder
@Data
public class ApplicationNameDto {
	// 申請名
	private String appName;
	// 申請種類
	private Integer appType;
	//他の種類
	private Integer otherType;
}
