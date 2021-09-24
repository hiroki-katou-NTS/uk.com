package nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @name UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.お気に入り作業.お気に入り作業項目.お気に入り作業項目
 * @author tutt
 *
 */
@Getter
public class FavoriteTaskItem extends AggregateRoot {
	
	/** 社員ID*/
	private final String employeeId;
	
	/** お気に入りID: GUID*/
	private final String favoriteId;
	
	/** 名称*/
	private FavoriteTaskName taskName;
	
	/** お気に入り内容*/
	private final List<TaskContent> favoriteContents;

	/**
	 * [C-1] 新規追加
	 * @param employeeId 社員ID
	 * @param favoriteId お気に入りID
	 * @param taskName 名称
	 * @param favoriteContents お気に入り内容
	 */
	public FavoriteTaskItem(String employeeId, String favoriteId, FavoriteTaskName taskName,
			List<TaskContent> favoriteContents) {
		super();
		this.employeeId = employeeId;
		this.favoriteId = favoriteId;
		this.taskName = taskName;
		this.favoriteContents = favoriteContents;
	}
}
