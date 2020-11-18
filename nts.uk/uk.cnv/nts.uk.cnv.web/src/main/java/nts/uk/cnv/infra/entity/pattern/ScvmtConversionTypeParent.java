package nts.uk.cnv.infra.entity.pattern;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.OnSentence;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.dom.conversiontable.pattern.ParentJoinPattern;
import nts.uk.cnv.dom.conversiontable.pattern.manager.ParentJoinPatternManager;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_PARENT")
public class ScvmtConversionTypeParent extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "PARENT_TABLE_NAME")
	private String parentName;

	@Column(name = "PARENT_COLUMN_NAME")
	private String parentColumnName;

	@Column(name = "JOIN_PARENT_COLUMNS")
	private String joinParentColumns;

	@OneToOne(optional=true) @PrimaryKeyJoinColumns({
        @PrimaryKeyJoinColumn(name="CATEGORY_NAME", referencedColumnName="CATEGORY_NAME"),
        @PrimaryKeyJoinColumn(name="TARGET_TBL_NAME", referencedColumnName="TARGET_TBL_NAME"),
        @PrimaryKeyJoinColumn(name="RECORD_NO", referencedColumnName="RECORD_NO"),
        @PrimaryKeyJoinColumn(name="TARGET_COLUMN_NAME", referencedColumnName="TARGET_COLUMN_NAME")
    })
	private ScvmtConversionTable conversionTable;

	@Override
	protected Object getKey() {
		return pk;
	}

	public ParentJoinPattern toDomain(ConversionInfo info, Join sourceJoin) {
		List<String> col = Arrays.asList(this.joinParentColumns.split(","));
		List<OnSentence> on  = col.stream()
			.map(column -> new OnSentence(
					new ColumnName("parent_" + parentColumnName, ParentJoinPatternManager.getSourcePkName(col.indexOf(column))),
					new ColumnName(Constants.BaseTableAlias, column),
					Optional.empty()
				))
			.collect(Collectors.toList());
		return new ParentJoinPattern(
				info,
				sourceJoin,
				new Join(
					new TableName("", "", ParentJoinPatternManager.parentMappingTable, "parent_" + parentColumnName),
					JoinAtr.OuterJoin,
					on),
				parentColumnName,
				this.pk.getTargetTableName()
			);
	}

	public static ScvmtConversionTypeParent toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof ParentJoinPattern)) {
			return null;
		}

		ParentJoinPattern domain = (ParentJoinPattern) conversionPattern;

		String joinColumns = String.join(",", domain.getParentJoin().getOnSentences().stream()
				.map(on -> on.getLeft().getName())
				.collect(Collectors.toList()));

		return new ScvmtConversionTypeParent(pk,
				domain.getParentJoin().tableName.getName(),
				domain.getParentColumn(),
				joinColumns,
				null
			);
	}

}
