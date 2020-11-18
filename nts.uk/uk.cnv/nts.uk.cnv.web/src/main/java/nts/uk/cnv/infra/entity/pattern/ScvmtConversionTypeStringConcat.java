package nts.uk.cnv.infra.entity.pattern;

import java.io.Serializable;
import java.util.Optional;

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
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.dom.conversiontable.pattern.StringConcatPattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_CONCAT")
public class ScvmtConversionTypeStringConcat extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "SOURCE_COLUMN_NAME1")
	private String sourceColumnName1;

	@Column(name = "SOURCE_COLUMN_NAME2")
	private String sourceColumnName2;

	@Column(name = "DELIMITER")
	private String delimiter;

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

	public StringConcatPattern toDomain(ConversionInfo info, Join sourceJoin) {
		return new StringConcatPattern(
				info,
				sourceJoin,
				sourceColumnName1,
				sourceColumnName2,
				(delimiter == null) ? Optional.empty() : Optional.of(delimiter)
			);
	}

	public static ScvmtConversionTypeStringConcat toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof StringConcatPattern)) {
			return null;
		}

		StringConcatPattern domain = (StringConcatPattern) conversionPattern;

		return new ScvmtConversionTypeStringConcat(
				pk,
				domain.getColumn1(),
				domain.getColumn2(),
				(domain.getDelimiter().isPresent()) ? domain.getDelimiter().get() : null,
				null
			);
	}

}
