package nts.uk.cnv.dom.td.schema.prospect.list;

import static java.util.stream.Collectors.*;

import java.util.Comparator;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nts.uk.cnv.dom.td.schema.TableIdentity;

/**
 * テーブル一覧のプロスペクト
 */
@Getter
@EqualsAndHashCode
@ToString
public class TableListProspect {
	
	/** 最後に適用したorutaのID */
	private final String lastAlterId;
	
	/** テーブルリスト */
	private final List<TableIdentity> tables;

	public TableListProspect(String lastAlterId, List<TableIdentity> tables) {
		this.lastAlterId = lastAlterId;
		this.tables = tables.stream()
				.sorted(Comparator.comparing(t -> t.getName()))
				.collect(toList());
	}

}
