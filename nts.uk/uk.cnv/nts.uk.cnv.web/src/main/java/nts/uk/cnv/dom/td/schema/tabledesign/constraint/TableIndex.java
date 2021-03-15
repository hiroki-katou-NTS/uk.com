package nts.uk.cnv.dom.td.schema.tabledesign.constraint;

import java.util.List;

import lombok.Value;

/**
 * インデックス定義
 */
@Value
public class TableIndex {

	/** サフィックス */
	String suffix;
	
	/** 列IDリスト */
	List<String> columnIds;
	
	/** クラスタ化 */
	boolean isClustered;
}
