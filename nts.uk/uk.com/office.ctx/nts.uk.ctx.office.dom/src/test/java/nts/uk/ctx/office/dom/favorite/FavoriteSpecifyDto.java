package nts.uk.ctx.office.dom.favorite;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
public class FavoriteSpecifyDto implements FavoriteSpecify.MementoGetter, FavoriteSpecify.MementoSetter {
	// お気に入り名
	private String favoriteName;

	// 作��D
	private String creatorId;

	// 入力日
	private GeneralDateTime inputDate;

	// 対象選�
	private Integer targetSelection;

	// 職場ID
	private List<String> workplaceId;

	// 頺
	private Integer order;

	public FavoriteSpecifyDto(String favoriteName, String creatorId, GeneralDateTime inputDate, Integer targetSelection,
			List<String> workplaceId, Integer order) {
		this.favoriteName = favoriteName;
		this.creatorId = creatorId;
		this.inputDate = inputDate;
		this.targetSelection = targetSelection;
		this.workplaceId = workplaceId;
		this.order = order;
	}
	
	public FavoriteSpecifyDto() {}
}
