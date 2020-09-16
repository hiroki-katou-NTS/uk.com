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
import nts.uk.cnv.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.dom.pattern.FixedValueWithConditionPattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_FIXED_VALUE_WITH_CONDITION")
public class ScvmtConversionTypeFixedValueWithCondition extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "SOURCE_NO")
	private int sourceNo;

	@Column(name = "SOURCE_COLUMN_NAME")
	private String sourceColumnName;

	@Column(name = "OPERATOR")
	private String operator;

	@Column(name = "CONDITION_VALUE")
	private String conditionValue;

	@Column(name = "IS_PARAMATER")
	private boolean isParameter;

	@Column(name = "FIXED_VALUE")
	private String fixedValue;

	@Override
	protected Object getKey() {
		return pk;
	}

	public FixedValueWithConditionPattern toDomain(ConversionInfo info, Join sourcejoin) {
		return new FixedValueWithConditionPattern(
				info,
				sourcejoin,
				this.sourceColumnName,
				RelationalOperator.valueOf(this.operator),
				this.conditionValue,
				this.isParameter,
				this.fixedValue
			);
	}

}
