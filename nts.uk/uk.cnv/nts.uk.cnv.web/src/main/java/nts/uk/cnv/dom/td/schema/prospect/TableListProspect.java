package nts.uk.cnv.dom.td.schema.prospect;

import java.util.List;

import lombok.Value;
import nts.uk.cnv.dom.td.schema.TableIdentity;

/**
 * テーブル一覧のプロスペクト
 */
@Value
public class TableListProspect {
	
	/** 最後に適用したorutaのID */
	String lastAlterId;
	
	/** テーブルリスト */
	List<TableIdentity> tables;

}
