package nts.uk.cnv.core.infra.entity.conversiontable.pattern;

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
import nts.uk.cnv.core.dom.constants.Constants;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.OnSentence;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.SourceJoinPattern;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_SOURCEJOIN")
public class ScvmtConversionTypeSourceJoin extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "SOURCE_TABLE_NAME")
	private String sourceTableName;

	@Column(name = "SOURCE_COLUMN_NAME")
	private String sourceColumnName;

	@Column(name = "JOIN_SOURCE_COLUMNS")
	private String joinSourceColumns;

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

	public ConversionPattern toDomain(ConversionInfo info) {
		List<String> col = Arrays.asList(this.joinSourceColumns.split(","));
		List<OnSentence> on  = col.stream()
			.map(column -> new OnSentence(
					new ColumnName("source_" + sourceTableName, column),
					new ColumnName(Constants.BaseTableAlias, column),
					Optional.empty()
				))
			.collect(Collectors.toList());
		return new SourceJoinPattern(
				info,
				new Join(
					info.getSourceTable(sourceTableName, "source_" + sourceTableName),
					JoinAtr.OuterJoin,
					on),
				sourceColumnName
			);
	}

	public static ScvmtConversionTypeSourceJoin toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof SourceJoinPattern)) {
			return null;
		}

		SourceJoinPattern domain = (SourceJoinPattern) conversionPattern;

		return new ScvmtConversionTypeSourceJoin(pk,
				domain.getSourceJoin().getTableName().getName(),
				domain.getSourceColumn(),
				domain.sourceJoinColumns(),
				null
			);
	}

}
