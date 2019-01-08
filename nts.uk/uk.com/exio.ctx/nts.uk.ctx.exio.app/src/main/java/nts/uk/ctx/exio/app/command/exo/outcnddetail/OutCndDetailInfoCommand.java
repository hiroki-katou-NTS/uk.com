package nts.uk.ctx.exio.app.command.exo.outcnddetail;

import lombok.Value;

@Value
public class OutCndDetailInfoCommand {
	OutCndDetailCommand outCndDetail;
	int standardAtr;
	int registerMode;
}
