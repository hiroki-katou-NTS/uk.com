package nts.uk.ctx.pereg.app.command.layout;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewLayoutCommand {
	private String layoutID;

	private String layoutCode;

	private String layoutName;
	
	private List<ClassificationCommand> itemsClassification;
}
