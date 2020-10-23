package nts.uk.ctx.sys.portal.app.query.notice;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.portal.dom.notice.adapter.RoleImport;

@Data
@Builder
public class EmployeeNotificationDto {
	
	/**
	 * Map<お知らせメッセージ、作成者> (List)
	 */
	List<MsgNoticesDto> msgNotices;
	
	/**
	 * Map<個人の記念日情報、新記念日Flag> (List)
	 */
	List<AnniversaryNoticesDto> anniversaryNotices;
	
	/**
	 * ロール
	 */
	RoleImport role;
}
