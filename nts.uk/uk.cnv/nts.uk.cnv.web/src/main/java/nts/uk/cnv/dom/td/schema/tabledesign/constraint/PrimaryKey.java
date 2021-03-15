package nts.uk.cnv.dom.td.schema.tabledesign.constraint;

import java.util.Collections;
import java.util.List;

import lombok.Value;

/**
 * 主キー定義
 */
@Value
public class PrimaryKey {

	/** 列IDリスト */
	List<String> columnIds;
	
	/** クラスタ化 */
	boolean isClustered;
	
	public static PrimaryKey empty() {
		return new PrimaryKey(Collections.emptyList(), false);	
	}
}
