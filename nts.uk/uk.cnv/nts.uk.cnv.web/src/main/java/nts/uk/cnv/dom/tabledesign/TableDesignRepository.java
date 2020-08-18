package nts.uk.cnv.dom.tabledesign;

public interface TableDesignRepository {

	void insert(TableDesign tableDesign);
	void update(TableDesign tableDesign);
	void delete(TableDesign tableDesign);
	boolean exists(String tableName);
	void rename(String befor, String after);
}
