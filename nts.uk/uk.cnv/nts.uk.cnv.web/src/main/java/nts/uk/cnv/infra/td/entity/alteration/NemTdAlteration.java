package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;

/**
 * おるた
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALTERATION")
public class NemTdAlteration extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ALTERATION_ID")
	private String alterationId;

	@Column(name = "TABLE_ID")
	private String tableId;

	@Column(name = "FEATURE_ID")
	private String featureId;

	@Column(name = "DATETIME")
	private GeneralDateTime time;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "COMMENT")
	private String comment;

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
				tableId,
				new AlterationMetaData(
					featureId,
					time,
					userName,
					comment),
				contents
			);
	}

	public static NemTdAlteration toEntity(Alteration alt) {
		// TODO 自動生成されたメソッド・スタブ
		return new NemTdAlteration();
	}
}
