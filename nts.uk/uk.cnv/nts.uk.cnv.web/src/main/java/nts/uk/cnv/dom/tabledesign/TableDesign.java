package nts.uk.cnv.dom.tabledesign;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;

@AllArgsConstructor
@Getter
public class TableDesign {
	private String name;
	private String id;
	private GeneralDateTime createDate;
	private GeneralDateTime updateDate;
	
	private List<ColumnDesign> columns;
	private List<Indexes> indexes;
}
