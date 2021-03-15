package nts.uk.cnv.dom.td.schema.tabledesign.constraint;

import java.util.List;

import lombok.Value;

/**
 * ユニーク制約定義
 */
@Value
public class UniqueConstraint {

	/** サフィックス */
	String suffix;
	
	/** 列IDリスト */
	List<String> columnIds;
	
	/** クラスタ化 */
	boolean isClustered;
}
