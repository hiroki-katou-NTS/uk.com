package nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteDisplayOrder;

/**
 * AR: 1日お気に入り作業の表示順
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.お気に入り作業.1日お気に入りセット.1日お気に入り作業の表示順
 * @author tutt
 *
 */
@Getter
public class OneDayFavoriteTaskDisplayOrder extends AggregateRoot {
	
	/** 社員ID*/
	private final String sId;
	
	/** 表示順*/
	private List<FavoriteDisplayOrder> favoriteDisplayOrders;
	

	/** [C-1] 新規追加*/
	//TODO: Cần QA
	public OneDayFavoriteTaskDisplayOrder(String sId, String favId) {
		super();
		this.sId = sId;
	}
	
	/**
	 * [1] 新しいお気に入りの表示順を追加する
	 * @param favId お気に入りID
	 */
	public void add(String favId) {
		
	}

	public OneDayFavoriteTaskDisplayOrder(String sId, List<FavoriteDisplayOrder> favoriteDisplayOrders) {
		super();
		this.sId = sId;
		this.favoriteDisplayOrders = favoriteDisplayOrders;
	}
	
	
}
