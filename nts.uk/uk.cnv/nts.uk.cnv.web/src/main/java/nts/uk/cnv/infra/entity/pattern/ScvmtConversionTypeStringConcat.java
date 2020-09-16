package nts.uk.cnv.infra.entity.pattern;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.pattern.StringConcatPattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_CONCAT")
public class ScvmtConversionTypeStringConcat extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "SOURCE_NO")
	private int sourceNo;

	@Column(name = "SOURCE_COLUMN_NAME1")
	private String sourceColumnName1;

	@Column(name = "SOURCE_COLUMN_NAME2")
	private String sourceColumnName2;

	@Column(name = "DELIMITER")
	private String delimiter;

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

}
