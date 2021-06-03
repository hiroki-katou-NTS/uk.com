package nts.uk.cnv.core.infra.entity.conversiontable.pattern;

import java.io.Serializable;

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
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.FixedValuePattern;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
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

	public FixedValuePattern toDomain(ConversionInfo info, Join join) {
		return new FixedValuePattern(
				info,
				join,
				this.isParameter,
				this.fixedValue
			);
	}

	public static ScvmtConversionTypeFixedValue toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof FixedValuePattern)) {
			return null;
		}

		FixedValuePattern domain = (FixedValuePattern) conversionPattern;

		return new ScvmtConversionTypeFixedValue(pk, domain.isParamater(), domain.getExpression(), null);
	}
}
