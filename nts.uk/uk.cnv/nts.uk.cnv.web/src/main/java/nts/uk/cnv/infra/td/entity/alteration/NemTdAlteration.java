package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.dom.td.alteration.content.RemoveTable;
import nts.uk.cnv.dom.td.alteration.content.column.AddColumn;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnComment;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnJpName;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnName;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnType;
import nts.uk.cnv.dom.td.alteration.content.column.RemoveColumn;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltAddColumn;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnComment;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnJpName;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnName;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnType;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltDeleteColumn;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltAddTable;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltChangeTableJpName;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltChangeTableName;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltDeleteTable;

/**
 * おるた
 * @author ai_muto
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALTERATION")
public class NemTdAlteration extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ALTERATION_ID")
	public String alterationId;

	@Column(name = "TABLE_ID")
	public String tableId;

	@Column(name = "FEATURE_ID")
	public String featureId;

	@Column(name = "DATETIME")
	public GeneralDateTime time;

	@Column(name = "USER_NAME")
	public String userName;

	@Column(name = "COMMENT")
	public String comment;

	@OneToMany(targetEntity=NemTdAltAddTable.class, mappedBy="alteration", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAltAddTable> addTables;

	@OneToMany(targetEntity=NemTdAltChangeTableName.class, mappedBy="alteration", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAltChangeTableName> changeTableNames;

	@OneToMany(targetEntity=NemTdAltChangeTableJpName.class, mappedBy="alteration", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAltChangeTableJpName> changeTableJpNames;

	@OneToMany(targetEntity=NemTdAltAddColumn.class, mappedBy="alteration", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAltAddColumn> addColumns;

	@OneToMany(targetEntity=NemTdAltChangeColumnName.class, mappedBy="alteration", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAltChangeColumnName> changeColumnNames;

	@OneToMany(targetEntity=NemTdAltChangeColumnJpName.class, mappedBy="alteration", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAltChangeColumnJpName> changeColumnJpNames;

	@OneToMany(targetEntity=NemTdAltChangeColumnType.class, mappedBy="alteration", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAltChangeColumnType> changeColumnType;

	@OneToMany(targetEntity=NemTdAltChangeColumnComment.class, mappedBy="alteration", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAltChangeColumnComment> changeColumnComment;

	@OneToMany(targetEntity=NemTdAltDeleteColumn.class, mappedBy="alteration", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAltDeleteColumn> deleteColumn;

	@OneToMany(targetEntity=NemTdAltDeleteTable.class, mappedBy="alteration", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAltDeleteTable> deleteTable;

	@Override
	protected Object getKey() {
		return alterationId;
	}

	public Alteration toDomain() {
		List<AlterationContent> contents = new ArrayList<>();
		this.addTables.stream().forEach(content -> contents.add(content.toDomain(tableId)));
		this.changeTableNames.stream().forEach(content -> contents.add(content.toDomain()));
		this.changeTableJpNames.stream().forEach(content -> contents.add(content.toDomain()));
		this.addColumns.stream().forEach(content -> contents.add(content.toDomain()));
		this.changeColumnNames.stream().forEach(content -> contents.add(content.toDomain()));
		this.changeColumnJpNames.stream().forEach(content -> contents.add(content.toDomain()));
		this.changeColumnType.stream().forEach(content -> contents.add(content.toDomain()));
		this.changeColumnComment.stream().forEach(content -> contents.add(content.toDomain()));
		this.deleteColumn.stream().forEach(content -> contents.add(content.toDomain()));
		this.deleteTable.stream().forEach(content -> contents.add(content.toDomain()));

		return new Alteration(
				alterationId,
				featureId,
				time,
				tableId,
				new AlterationMetaData(
					userName,
					comment),
				contents
			);
	}

	public static NemTdAlteration toEntity(Alteration domain) {
		
		val e = new NemTdAlteration();
		
		e.alterationId = domain.getAlterId();
		e.tableId = domain.getTableId();
		e.featureId = domain.getFeatureId();
		e.time = domain.getCreatedAt();
		e.userName = domain.getMetaData().getUserName();
		e.comment = domain.getMetaData().getComment();
		
		e.addTables = new ArrayList<>();
		e.changeTableNames = new ArrayList<>();
		e.changeTableJpNames = new ArrayList<>();
		e.addColumns = new ArrayList<>();
		e.changeColumnNames = new ArrayList<>();
		e.changeColumnJpNames = new ArrayList<>();
		e.changeColumnType = new ArrayList<>();
		e.changeColumnComment = new ArrayList<>();
		e.deleteColumn = new ArrayList<>();
		e.deleteTable = new ArrayList<>();
		
		for (int i = 0; i < domain.getContents().size(); i++) {
			
			val content = domain.getContents().get(i);
			val pk = new NemTdAltContentPk(e.alterationId, i);
			
			new Match(content)
				.when(AddTable.class, e.addTables, d -> NemTdAltAddTable.toEntity(pk, d))
				.when(ChangeTableName.class, e.changeTableNames, d -> NemTdAltChangeTableName.toEntity(pk, d))
				.when(ChangeTableJpName.class, e.changeTableJpNames, d -> NemTdAltChangeTableJpName.toEntity(pk, d))
				.when(AddColumn.class, e.addColumns, d -> NemTdAltAddColumn.toEntity(pk, d))
				.when(ChangeColumnName.class, e.changeColumnNames, d -> NemTdAltChangeColumnName.toEntity(pk, d))
				.when(ChangeColumnJpName.class, e.changeColumnJpNames, d -> NemTdAltChangeColumnJpName.toEntity(pk, d))
				.when(ChangeColumnType.class, e.changeColumnType, d -> NemTdAltChangeColumnType.toEntity(pk, d))
				.when(ChangeColumnComment.class, e.changeColumnComment, d -> NemTdAltChangeColumnComment.toEntity(pk, d))
				.when(RemoveColumn.class, e.deleteColumn, d -> NemTdAltDeleteColumn.toEntity(pk, d))
				.when(RemoveTable.class, e.deleteTable, d -> NemTdAltDeleteTable.toEntity(pk))
				.go(() -> { throw new RuntimeException("未対応：" + content.getClass()); });
		}
		
		return e;
	}
	
	@RequiredArgsConstructor
	private static class Match {
		private final AlterationContent content;
		private final List<Process<?, ?>> processes = new ArrayList<>();
		
		<D, E> Match when(Class<D> entityClass, List<E> container, Function<D, E> toEntity) {
			processes.add(new Process<>(entityClass, container, toEntity));
			return this;
		}
		
		void go(Runnable elseAction) {
			for (val p : processes) {
				if (p.go(content)) {
					return;
				}
			}
			
			elseAction.run();
		}
		
		@Value
		private static class Process<D, E> {
			Class<D> entityClass;
			List<E> container;
			Function<D, E> toEntity;
			
			@SuppressWarnings("unchecked")
			boolean go(AlterationContent content) {
				if (entityClass.isInstance(content)) {
					container.add(toEntity.apply((D) content));
					return true;
				}
				
				return false;
			}
		}
	}
}
