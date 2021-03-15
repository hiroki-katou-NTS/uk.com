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
	
	/**
	 * 永続化されたスナップショットが存在しない状態でも、「テーブル作成」のorutaを流し込む必要があるため、
	 * それを受け入れる「空のTableSnapshot」が必要。
	 * この場合、このemptyフィールドがtrueになり、snapshotIdはnullとなる。
	 */
	boolean empty;

	public TableSnapshot(String snapshotId, TableDesign domain) {
		super(domain);
		this.snapshotId = snapshotId;
		this.empty = false;
	}
	
	public static TableSnapshot empty() {
		return new TableSnapshot(null, TableDesign.empty());
	}

	/**
	 * orutaを適用してプロスペクトを生成する
	 * @param altarations 適用する変更履歴のリスト
	 * @return プロスペクト。テーブルが削除された場合はempty
	 */
	public Optional<TableProspect> apply(List<Alteration> altarations) {

		TableProspectBuilder builder = this.empty
			? new TableProspectBuilder()
			: new TableProspectBuilder(this);

		altarations.stream().forEach(alt ->{
			alt.apply(builder);
		});

		return builder.build();
	}
}
