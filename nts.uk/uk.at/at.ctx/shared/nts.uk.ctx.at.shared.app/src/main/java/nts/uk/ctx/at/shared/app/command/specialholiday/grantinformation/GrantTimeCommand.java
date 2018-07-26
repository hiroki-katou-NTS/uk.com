package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import java.util.List;

import lombok.Value;

@Value
public class GrantTimeCommand {
	/** 固定付与日 */
	private FixGrantDateCommand fixGrantDate;
	
	/** 特別休暇付与テーブル */
	private List<GrantDateTblCommand> grantDateTbl;
}
