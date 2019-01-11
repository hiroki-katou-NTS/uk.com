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
	private String itemName;
	private String categoryName;

	public static MaintenanceLayoutData createFromJavaType(String layoutCd, String layoutName,
			String categoryName, String itemName) {
		return new MaintenanceLayoutData(layoutCd, layoutName,categoryName,itemName);
	}

}

