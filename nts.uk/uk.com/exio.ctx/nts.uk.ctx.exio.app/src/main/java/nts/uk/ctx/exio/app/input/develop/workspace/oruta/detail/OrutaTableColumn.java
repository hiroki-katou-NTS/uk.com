package nts.uk.ctx.exio.app.input.develop.workspace.oruta.detail;

import java.util.Arrays;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;

@Value
public class OrutaTableColumn {
	String id;
	String name;
	String jpName;
	String comment;
	int dispOrder;
	OrutaTypeConfig type;
	
	public static String toCsvHeaderXimctWorkspaceItem() {
		
		List<String> values = Arrays.asList(
				"INS_DATE", "INS_CCD", "INS_SCD", "INS_PG",
				"UPD_DATE", "UPD_CCD", "UPD_SCD", "UPD_PG",
				"EXCLUS_VER",
				"NAME",
				"DOMAIN_ID",
				"ITEM_NO",
				"DATA_TYPE",
				"LENGTH",
				"SCALE");
		
		return String.join(",", values);
	}
	
	public String toCsvXimctWorkspaceItem(ImportingDomainId domainId, int itemNo) {
		
		List<String> values = Arrays.asList(
				"", "", "", "",
				"", "", "", "",
				"0",
				quote(name),
				domainId.value + "",
				itemNo + "",
				type.convertDataType().value + "",
				type.getLength() + "",
				type.getScale() + ""
				);
		
		return String.join(",", values);
	}
	
	private static String quote(String value) {
		return "\"" + value + "\"";
	}
}
