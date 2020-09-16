package nts.uk.cnv.infra.entity.pattern;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.pattern.CodeToCodePattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_CODE_TO_CODE")
public class ScvmtConversionTypeCodeToCode extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "SOURCE_NO")
	private int sourceNo;

	@Column(name = "SOURCE_COLUMN_NAME")
	private String sourceColumnName;

	@Column(name = "MAPPING_TYPE")
	private String mappingType;

	@Override
	protected Object getKey() {
		return pk;
	}

	public CodeToCodePattern toDomain(ConversionInfo info, Join sourcejoin) {
		return new CodeToCodePattern(
				info,
				sourcejoin,
				this.sourceColumnName
			);
	}

}
