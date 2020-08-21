package nts.uk.cnv.dom.databasetype;

public interface DataTypeDefine {
	// 型
	public String dataType(DataType type, Integer... length);
	public DataType parse(String type, Integer... length) ;
}
