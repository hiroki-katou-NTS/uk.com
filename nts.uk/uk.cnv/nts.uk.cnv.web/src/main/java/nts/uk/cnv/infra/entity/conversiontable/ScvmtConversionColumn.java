package nts.uk.cnv.infra.entity.conversiontable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.dom.pattern.ConversionPattern;

/**
 * コンバート列定義
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_COLUMN")
public class ScvmtConversionColumn extends JpaEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public ScvmtConversionColumnPk pk;

	@Column(name = "CONVERSION_TYPE")
	private String conversionType;

	@Override
	protected Object getKey() {
		return pk;
	}

	public OneColumnConversion toDomain(ConversionPattern pattern) {
		return new OneColumnConversion(
					this.pk.getTargetColumnName(),
					this.conversionType,
					pattern
				);
	}
}
