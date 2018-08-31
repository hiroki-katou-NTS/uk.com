package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
@AllArgsConstructor
public class TmpRsvLeaveMngImport {
	/** 年月日 */
	private String ymd;
	/** 作成元区分（予定実績区分） */
	private String creatorAtr;
	/** 使用日数 */
	private Double useDays;
}
