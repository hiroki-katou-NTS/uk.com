package nts.uk.ctx.sys.assist.app.command.category;

import java.util.List;

import lombok.Data;

@Data
public class CusCategoryCommand {
	
	private int systemType;
	private String keySearch;
	List<String> categoriesIgnore;
	
}
