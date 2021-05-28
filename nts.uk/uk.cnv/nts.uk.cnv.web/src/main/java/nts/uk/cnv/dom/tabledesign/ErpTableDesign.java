package nts.uk.cnv.dom.tabledesign;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * （こんぶの）Erpテーブル定義
 */
@Getter
@AllArgsConstructor
public class ErpTableDesign {
	String name;
	List<ColumnDesign> columns;
}
