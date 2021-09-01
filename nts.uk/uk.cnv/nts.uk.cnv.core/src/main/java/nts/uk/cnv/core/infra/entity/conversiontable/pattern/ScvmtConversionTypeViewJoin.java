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
import nts.uk.cnv.core.dom.conversiontable.pattern.ViewJoinPattern;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_VIEW")
public class ScvmtConversionTypeViewJoin extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "VIEW_NAME")
	private String viewName;

	@Column(name = "VIEW_COLUMN_NAME")
	private String viewColumnName;

	@Column(name = "JOIN_KEYS")
	private String joinKeys;

	@Column(name = "CREATE_VIEW_SQL")
	private String createViewSql;

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
		List<String> col = Arrays.asList(this.joinKeys.split(","));
		List<OnSentence> on  = col.stream()
			.map(column -> new OnSentence(
					new ColumnName("source_" + viewName, column),
					new ColumnName(Constants.BaseTableAlias, column),
					Optional.empty()
				))
			.collect(Collectors.toList());
		return new ViewJoinPattern(
				info,
				new Join(
					info.getSourceTable(viewName, "view_" + viewName),
					JoinAtr.OuterJoin,
					on),
				viewColumnName,
				createViewSql
			);
	}

	public static ScvmtConversionTypeViewJoin toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof ViewJoinPattern)) {
			return null;
		}

		ViewJoinPattern domain = (ViewJoinPattern) conversionPattern;

		return new ScvmtConversionTypeViewJoin(pk,
				domain.getViewJoin().getTableName().getName(),
				domain.getViewColumn(),
				domain.getJoinKeys(),
				domain.getCreateViewSql(),
				null
			);
	}

}
