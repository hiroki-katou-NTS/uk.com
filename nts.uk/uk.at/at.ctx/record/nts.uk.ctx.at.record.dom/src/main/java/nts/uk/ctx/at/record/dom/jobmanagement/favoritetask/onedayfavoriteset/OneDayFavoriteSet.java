package nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskName;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.お気に入り作業.1日お気に入りセット
 * AR: 1日お気に入りセット
 * 
 * @author tutt
 */
@AllArgsConstructor
@Getter
public class OneDayFavoriteSet extends AggregateRoot {
	
	/** 社員ID*/
	private final String sId;
	
	/** お気に入りID*/
	private final String favId;
	
	/** お気に入り作業名称*/
	private FavoriteTaskName taskName;
	
	/** お気に入り内容*/
	private final List<TaskBlockDetailContent> taskBlockDetailContents;
}
