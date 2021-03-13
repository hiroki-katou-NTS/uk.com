package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.schema.prospect.TableProspect;
import nts.uk.cnv.dom.td.schema.prospect.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.Indexes;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

@Getter
public class Snapshot extends TableDesign {
	String snapshotId;
	GeneralDateTime time;
	String eventId;
	boolean empty;

	public Snapshot() {
		super(null, null, null, null, null);

		this.empty = true;
		this.snapshotId = IdentifierUtil.randomUniqueId();
		this.time = GeneralDateTime.min();
		this.eventId = "";
	}

	public Snapshot(
			String snapshotId, String eventId,
			String id, String name, String jpName,
			GeneralDateTime createDate, GeneralDateTime updateDate,
			List<ColumnDesign> columns, List<Indexes> indexes) {

		super(id, name, jpName, columns, indexes);
		this.snapshotId = snapshotId;
		this.eventId = eventId;
		this.empty = false;
	}

	public Snapshot(String snapshotId, String eventId, TableDesign domain) {
		super( domain.getId(),
				domain.getName(),
				domain.getJpName(),
				domain.getColumns(),
				domain.getIndexes());
		this.snapshotId = snapshotId;
		this.time = GeneralDateTime.now();
		this.eventId = eventId;
		this.empty = false;
	}

	/**
	 * プロスペクトを生成する
	 * @param altarations 適用する変更履歴のリスト
	 * @return プロスペクト。テーブルが削除された場合はempty
	 */
	public Optional<TableProspect> createTableProspect(List<Alteration> altarations) {

		TableProspectBuilder builder = this.empty
			? new TableProspectBuilder()
			: new TableProspectBuilder(this);

		altarations.stream().forEach(alt ->{
			alt.apply(builder);
		});

		return builder.build();
	}
}
