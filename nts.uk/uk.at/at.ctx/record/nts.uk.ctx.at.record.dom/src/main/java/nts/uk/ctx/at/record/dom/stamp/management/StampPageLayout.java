package nts.uk.ctx.at.record.dom.stamp.management;

import java.util.List;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 打刻ページレイアウト
 * @author phongtq
 *
 */
@Value
public class StampPageLayout implements DomainValue{
	
	/** ページNO */
	private final int pageNo;
	
	/** ページ名 */
	private StampPageName  stampPageName;
	
	/** ページコメント */
	private StampPageComment stampPageComment;
	
	/** ボタン配置タイプ */
	private ButtonLayoutType buttonLayoutType;
	
	/** ボタン詳細設定リスト */
	private List<ButtonSettings> lstButtonSet;
}
