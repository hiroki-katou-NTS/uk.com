package nts.uk.ctx.office.app.command.favorite;

import lombok.Data;
import nts.arc.time.GeneralDateTime;

/*
 * お気に入りの指定 Command
 */
@Data
public class FavoriteSpecifyDelCommand {
	// 作��D
	private String creatorId;

	// 入力日
	private GeneralDateTime inputDate;
}
