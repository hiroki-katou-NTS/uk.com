package nts.uk.ctx.pr.proto.app.layout.register.command;

import java.util.List;

import lombok.Getter;

@Getter
public class RegisterLayoutCommand {

	private LayoutCommand layoutCommand;
	private List<LayoutCategoryCommand> categoryCommand;
	private List<LayoutLineCommand> lineCommand;
	private List<LayoutDetailCommand> detailCommand;
	
	private List<Integer> listCategoryAtrDeleted;
	private List<String> listAutoLineIdDeleted;
	private List<String> listItemCodeDeleted;
	
}
