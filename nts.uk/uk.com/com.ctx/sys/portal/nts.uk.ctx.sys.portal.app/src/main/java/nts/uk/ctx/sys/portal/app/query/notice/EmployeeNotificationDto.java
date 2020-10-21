package nts.uk.ctx.sys.portal.app.query.notice;

import java.util.Map;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.adapter.AnniversaryNoticeImport;
import nts.uk.ctx.sys.portal.dom.notice.adapter.RoleImport;

@Data
@Builder
public class EmployeeNotificationDto {
	
	/**
	 * Map<お知らせメッセージ、作成者>
	 */
	Map<MessageNotice, String> msgNotices;
	
	/**
	 * Map<個人の記念日情報、新記念日Flag>
	 */
	Map<AnniversaryNoticeImport, Boolean> anniversaryNotices;
	
	/**
	 * ロール
	 */
	RoleImport role;
}
