package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;

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
		
		e.addTables = toEntity(
				domain.getContents(AddTable.class),
				(seqNo, d) -> NemTdAltAddTable.toEntity(e, seqNo, d));
		
		return e;
	}

	public static <D, E> List<E> toEntity(
			List<D> domains,
			BiFunction<Integer, D, E> toEntity) {
		
		List<E> entities = new ArrayList<>();
		for (int i = 0; i < domains.size(); i++) {
			val d = domains.get(i);
			entities.add(toEntity.apply(i, d));
		}
		
		return entities;
	}
}
