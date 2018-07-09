package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@Value
@AllArgsConstructor
public class TableListByCategory {
	
	private String categoryId;
	private List<TableList> tables;
	
}
