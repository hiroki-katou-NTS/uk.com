package nts.uk.file.com.app.maintenance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceLayoutData extends AggregateRoot {

	private String layoutCd;
	private String layoutName;
	private String categoryName;
	private String itemName;
	private String itemNameC;
	private String itemParentCD;
	private int dataType;
	private String itemCD;
	private int layoutItemType;
	
}

