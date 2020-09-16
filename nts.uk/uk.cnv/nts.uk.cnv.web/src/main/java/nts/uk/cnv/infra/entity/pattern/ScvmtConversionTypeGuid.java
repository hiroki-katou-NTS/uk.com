package nts.uk.cnv.infra.entity.pattern;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.pattern.GuidPattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_GUID")
public class ScvmtConversionTypeGuid extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Override
	protected Object getKey() {
		return pk;
	}

	public GuidPattern toDomain(ConversionInfo info) {
		return new GuidPattern(info);
	}

}
