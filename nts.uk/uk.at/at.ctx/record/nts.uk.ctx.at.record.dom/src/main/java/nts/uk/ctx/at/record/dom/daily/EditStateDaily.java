package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 日別実績の編集状態
 * @author keisuke_hoshina
 *
 */
@Value
public class EditStateDaily {
	private String employeerId;
	private GeneralDate date;
	private String premiumItemId;
	
}
