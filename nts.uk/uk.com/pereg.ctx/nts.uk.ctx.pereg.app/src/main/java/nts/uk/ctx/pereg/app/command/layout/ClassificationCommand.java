package nts.uk.ctx.pereg.app.command.layout;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationCommand {
	private int dispOrder;
	private String personInfoCategoryID;
	private int layoutItemType;

	List<ClassificationItemDfCommand> listItemClsDf;
}
