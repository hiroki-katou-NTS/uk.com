package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 打刻の申請促す設定
 * @author phongtq
 *
 */
@Getter
@AllArgsConstructor 
public class StampPromptApplication  implements DomainAggregate {

	/** 会社ID */
	private final String companyId;
	
	/** 申請促すエラーリスト */
	private List<StampRecordDis> lstStampRecordDis;
	
	/**
	 * [1] 申請促すエラーリストを取得する	
	 * @return 申請促すエラーリスト
	 */
	public List<ErrorInformationApplication> getErrorListApply() {
		
		List<StampRecordDis> result =  this.lstStampRecordDis
						.stream().filter(t -> t.getUseArt().equals(NotUseAtr.USE)).collect(Collectors.toList());
		
		List<ErrorInformationApplication> list = result.stream().map(x -> x.getErrornformation()).collect(Collectors.toList());
		
		return list;
	}
}
