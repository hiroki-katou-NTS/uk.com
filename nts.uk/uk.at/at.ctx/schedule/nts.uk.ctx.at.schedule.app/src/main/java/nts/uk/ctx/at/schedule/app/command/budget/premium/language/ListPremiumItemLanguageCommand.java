package nts.uk.ctx.at.schedule.app.command.budget.premium.language;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListPremiumItemLanguageCommand {
	private List<InsertPremiumItemLanguageCommand> listData = new ArrayList<>();

	public ListPremiumItemLanguageCommand(List<InsertPremiumItemLanguageCommand> listData) {
		super();
		this.listData = listData;
	}

}
