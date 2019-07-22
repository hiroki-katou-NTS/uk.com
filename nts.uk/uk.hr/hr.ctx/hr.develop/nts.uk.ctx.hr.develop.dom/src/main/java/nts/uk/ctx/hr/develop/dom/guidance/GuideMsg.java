package nts.uk.ctx.hr.develop.dom.guidance;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**メッセージ内容*/
@AllArgsConstructor
@Getter
public class GuideMsg {

	/**カテゴリCD*/
	private String categoryCode;
	
	/**カテゴリ名*/
	private String categoryName;
	
	/**イベントCD*/
	private String eventCode;
	
	/**イベント名*/
	private String eventName;
	
	/**プログラムID*/
	private String programId;
	
	/**プログラム名*/
	private String programName;
	
	/**画面ID*/
	private String screenId;
	
	/**画面名*/
	private String screenName;
	
	/**画面毎使用設定*/
	private boolean usageFlgByScreen;
	
	/**メッセージ*/
	private String_Any_1000 guideMsg;
	
	/**画面URL*/
	private Optional<String> screenPath;
	
	public static GuideMsg createFromJavaType(String categoryCode, String categoryName, String eventCode, String eventName, String programId, String programName, String screenId, String screenName, boolean usageFlgByScreen, String guideMsg, String screenPath) {
		return new GuideMsg(
				categoryCode, 
				categoryName, 
				eventCode, 
				eventName, 
				programId, 
				programName, 
				screenId, 
				screenName, 
				usageFlgByScreen, 
				new String_Any_1000(guideMsg), 
				Optional.ofNullable(screenPath));
	}
}
