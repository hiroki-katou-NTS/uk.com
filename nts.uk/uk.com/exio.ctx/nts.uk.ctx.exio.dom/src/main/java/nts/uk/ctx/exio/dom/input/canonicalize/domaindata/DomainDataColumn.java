package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

@Value
public class DomainDataColumn {

	int itemNo;
	String columnName;
	DataType dataType;

	public static final DomainDataColumn CID = new DomainDataColumn(9998, "CID", DataType.STRING);
}
