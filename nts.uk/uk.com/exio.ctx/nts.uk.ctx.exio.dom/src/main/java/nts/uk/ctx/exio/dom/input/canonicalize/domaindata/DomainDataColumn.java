package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

@Value
public class DomainDataColumn {

	int itemNo;
	String columnName;
	DataType dataType;

	public static final DomainDataColumn CID = new DomainDataColumn(9998, "CID", DataType.STRING);
	
	public static final DomainDataColumn getSID(int itemNo) {
		return new DomainDataColumn(itemNo, "SID", DataType.STRING);
	}
	
    public static final DomainDataColumn getHistId(int itemNo) {
    	return new DomainDataColumn(itemNo, "HIST_ID", DataType.STRING);
    }
}
