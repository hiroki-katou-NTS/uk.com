package nts.uk.screen.com.app.find.ccg005.favorite.information;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;

@Data
@Builder
public class FavoriteSpecifyDto implements FavoriteSpecify.MementoSetter {
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
}
