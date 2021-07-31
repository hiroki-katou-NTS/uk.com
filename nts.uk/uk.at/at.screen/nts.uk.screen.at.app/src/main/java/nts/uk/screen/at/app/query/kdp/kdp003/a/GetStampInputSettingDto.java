package nts.uk.screen.at.app.query.kdp.kdp003.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;

/**
 * 打刻入力(共有)でお知らせメッセージを取得する
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@Data
public class GetStampInputSettingDto {

	// Map<お知らせメッセージ>(List)
	private List<MessageNoticeDto> messageNotices;
	
	private StopBySystemDto stopBySystem;
	
	private StopByCompanyDto stopByCompany;

	// 会社単位の利用停止の設定

	// テナント単位の利用停止の設定
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class StopBySystemDto {

	private String contractCd;

	private int systemStatusType;

	private int stopMode;

	private String stopMessage;

	private String usageStopMessage;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class StopByCompanyDto {
	private int systemStatus;

	private String stopMessage;

	private int stopMode;

	private String usageStopMessage;
}
