package nts.uk.cnv.app.command;

import java.util.List;

import lombok.Value;

@Value
public class UpdateCategoryPrioriesSequenceCommand {
	List<String> categories;
}
