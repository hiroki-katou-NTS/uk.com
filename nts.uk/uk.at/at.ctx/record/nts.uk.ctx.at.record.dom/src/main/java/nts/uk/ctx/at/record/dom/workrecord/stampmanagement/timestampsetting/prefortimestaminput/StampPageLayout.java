package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 打刻ページレイアウト
 * @author phongtq
 *
 */
@Getter
public class StampPageLayout implements DomainValue{
	
	/** ページNO */
	private final PageNo pageNo;
	
	/** ページ名 */
	private StampPageName  stampPageName;
	
	/** ページコメント */
	private StampPageComment stampPageComment;
	
	/** ボタン配置タイプ */
	private ButtonLayoutType buttonLayoutType;
	
	/** ボタン詳細設定リスト */
	private List<ButtonSettings> lstButtonSet;

	public StampPageLayout(PageNo pageNo, StampPageName stampPageName, StampPageComment stampPageComment,
			ButtonLayoutType buttonLayoutType, List<ButtonSettings> lstButtonSet) {
		
		if(lstButtonSet.size() <= 0){
			throw new BusinessException("Msg_1627");
		}
		
		this.pageNo = pageNo;
		this.stampPageName = stampPageName;
		this.stampPageComment = stampPageComment;
		this.buttonLayoutType = buttonLayoutType;
		this.lstButtonSet = lstButtonSet;
	}
	
}
