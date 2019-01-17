package nts.uk.file.com.app.newlayout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewLayoutExportData extends AggregateRoot {
	
	private int dispoder;
	private String categoryName;
	private String itemName;
	
	

	public static NewLayoutExportData createFromJavaType(int dispoder,String categoryName,String itemName) {
		return new NewLayoutExportData(dispoder, categoryName,itemName);
	}

}
