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
	
	private String categoryCd;
	private String categoryName;
	private String perInfoCtgId;
	private String itemCd;
	private String itemName;
	private int dispoder;
	

	public static NewLayoutExportData createFromJavaType(String categoryCd, String categoryName, String perInfoCtgId, String itemCd, String itemName, int dispoder) {
		return new NewLayoutExportData(categoryCd,categoryName,perInfoCtgId,itemCd,itemName, dispoder);
	}

}
