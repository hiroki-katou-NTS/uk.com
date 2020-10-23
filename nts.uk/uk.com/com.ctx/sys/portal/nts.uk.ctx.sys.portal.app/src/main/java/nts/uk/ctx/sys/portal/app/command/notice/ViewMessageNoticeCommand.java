package nts.uk.ctx.sys.portal.app.command.notice;

import java.util.Map;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * Update message notice view command
 * @author DungDV
 *
 */
@Getter
public class ViewMessageNoticeCommand {
	
	/** Map<作成者ID、入力日>(List) */
	private Map<String, GeneralDateTime> msgInfors;
	
	/** 社員ID */
	private String sid;
}
