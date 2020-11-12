package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import lombok.Data;

@Data
public class ToppageReloadSettingCommand {
	private Integer reloadInteval;
	private String cId;
}
