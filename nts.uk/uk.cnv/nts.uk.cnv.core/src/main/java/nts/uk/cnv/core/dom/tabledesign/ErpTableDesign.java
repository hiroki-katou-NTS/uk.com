package nts.uk.cnv.core.dom.tabledesign;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * （こんぶの）Erpテーブル定義
 */
@Getter
@AllArgsConstructor
public class ErpTableDesign {
	String tableId;
	String snapshotId;
	String name;
	String jpName;
	List<ColumnDesign> columns;
}
