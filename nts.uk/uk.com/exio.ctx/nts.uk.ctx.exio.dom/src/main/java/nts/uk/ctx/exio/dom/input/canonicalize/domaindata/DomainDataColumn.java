package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

@Value
public class DomainDataColumn {

	String columnName;
	DataType dataType;

	public static final DomainDataColumn CID = new DomainDataColumn("CID", DataType.STRING);
	public static final DomainDataColumn SID = new DomainDataColumn("SID", DataType.STRING);
	public static final DomainDataColumn YMD = new DomainDataColumn("YMD", DataType.DATE);
}
