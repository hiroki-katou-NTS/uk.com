package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 打刻の申請促す設定
 * @author phongtq
 *
 */
@Getter
public class StamPromptApplication  implements DomainAggregate{

	/** 会社ID */
	private final String companyId;
	
	/** 申請促すエラーリスト */
	private List<StampRecordDis> lstStampRecordDis;

	public StamPromptApplication(String companyId, List<StampRecordDis> lstStampRecordDis) {
		this.companyId = companyId;
		this.lstStampRecordDis = lstStampRecordDis;
	}
}
