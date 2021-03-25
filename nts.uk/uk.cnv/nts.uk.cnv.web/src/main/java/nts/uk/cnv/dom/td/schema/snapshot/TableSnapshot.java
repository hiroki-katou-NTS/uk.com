package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspect;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

@Getter
public class TableSnapshot extends TableDesign {
	/** スナップショットID */
	String snapshotId;

	public TableSnapshot(String snapshotId, TableDesign domain) {
		super(domain);
		this.snapshotId = snapshotId;
	}

	public static TableSnapshot empty() {
		return new TableSnapshot(null, TableDesign.empty());
	}

	/**
	 * orutaを適用してプロスペクトを生成する
	 * @param alterations 適用する変更履歴のリスト
	 * @return プロスペクト。テーブルが削除された場合はempty
	 */
	public Optional<TableProspect> apply(List<Alteration> alterations) {

		TableProspectBuilder builder = new TableProspectBuilder(this);

		alterations.forEach(alt -> {
			alt.apply(builder);
		});

		return builder.build();
	}
}
