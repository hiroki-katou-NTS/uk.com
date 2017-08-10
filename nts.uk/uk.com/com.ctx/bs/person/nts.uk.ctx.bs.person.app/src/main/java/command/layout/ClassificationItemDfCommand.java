package command.layout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationItemDfCommand {
	private String layoutID;
	private int layoutDispOrder;
	private int dispOrder;
	private String personInfoItemDefinitionID;
}
