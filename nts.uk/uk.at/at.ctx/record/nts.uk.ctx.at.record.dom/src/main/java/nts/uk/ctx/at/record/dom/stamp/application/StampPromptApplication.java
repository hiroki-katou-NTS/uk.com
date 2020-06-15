package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 *    打刻の申請促す設定
 * @author phongtq
 *
 */
@Getter
public class StampPromptApplication  implements DomainAggregate {

	/** 会社ID */
	private final String companyId;
	
	/** 申請促すエラーリスト */
	private List<StampRecordDis> lstStampRecordDis;

	public StampPromptApplication(String companyId, List<StampRecordDis> lstStampRecordDis) {
		this.companyId = companyId;
		this.lstStampRecordDis = lstStampRecordDis;
	}
	
	/*
	 * [1] 申請促すエラーリストを取得する
	 * 
	 * @return:  申請促すエラーリスト
	 */
	
	public List<StampRecordDis> getErrorListApply() {
		// List<StampRecordDis> list = this.lstStampRecordDis.stream().filter(t -> t.getUseArt() ==  );
		return null; // this.lstStampRecordDis.stream().filter(t -> t.getUseArt() ==  );
	}
	
}
