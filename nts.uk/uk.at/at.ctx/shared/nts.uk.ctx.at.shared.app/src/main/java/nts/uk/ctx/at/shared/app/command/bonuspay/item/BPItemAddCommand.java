package nts.uk.ctx.at.shared.app.command.bonuspay.item;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimeItemAddCommand;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BPItemAddCommand {
	public List<BPTimeItemAddCommand> bonusPayTimeItemListCommand;

	public List<BPTimeItemAddCommand> bonusPayTimeItemSpecListCommand;
}
