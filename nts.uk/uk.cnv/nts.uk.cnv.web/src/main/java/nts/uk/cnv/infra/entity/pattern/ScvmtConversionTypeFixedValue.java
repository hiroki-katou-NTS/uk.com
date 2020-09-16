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
import nts.uk.cnv.dom.pattern.FixedValuePattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_FIXED_VALUE")
public class ScvmtConversionTypeFixedValue extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "IS_PARAMATER")
	private boolean isParameter;

	@Column(name = "FIXED_VALUE")
	private String fixedValue;

	@Override
	protected Object getKey() {
		return pk;
	}

	public FixedValuePattern toDomain(ConversionInfo info) {
		return new FixedValuePattern(
				info,
				this.isParameter,
				this.fixedValue
			);
	}

}
