package nts.uk.ctx.at.shared.app.command.bonuspay.item;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimeItemUpdateCommand;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BPItemUpdateCommand {
	public List<BPTimeItemUpdateCommand> bonusPayTimeItemListCommand;

	public List<BPTimeItemUpdateCommand> bonusPayTimeItemSpecListCommand;
}
