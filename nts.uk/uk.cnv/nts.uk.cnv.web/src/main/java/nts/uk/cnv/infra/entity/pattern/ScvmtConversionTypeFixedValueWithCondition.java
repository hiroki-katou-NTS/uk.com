package nts.uk.cnv.infra.entity.pattern;

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
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.dom.conversiontable.pattern.FixedValueWithConditionPattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_FIXED_VALUE_WITH_CONDITION")
public class ScvmtConversionTypeFixedValueWithCondition extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

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

	public static ScvmtConversionTypeFixedValueWithCondition toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof FixedValueWithConditionPattern)) {
			return null;
		}

		FixedValueWithConditionPattern domain = (FixedValueWithConditionPattern) conversionPattern;

		return new ScvmtConversionTypeFixedValueWithCondition(pk,
				domain.getSourceColumn(),
				domain.getRelationalOperator().name(),
				domain.getConditionValue(),
				domain.isParamater(),
				domain.getExpression(),
				null);
	}

}
