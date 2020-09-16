package nts.uk.cnv.infra.entity.conversiontable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.conversionsql.WhereSentence;
import nts.uk.cnv.dom.conversiontable.ConversionTable;
import nts.uk.cnv.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.dom.pattern.ConversionPattern;
import nts.uk.cnv.dom.pattern.ConversionType;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeCodeToCode;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeCodeToId;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeDateTimeMerge;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeFileId;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeFixedValue;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeFixedValueWithCondition;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeGuid;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeNone;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeParent;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypePassword;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeStringConcat;
import nts.uk.cnv.infra.entity.pattern.ScvmtConversionTypeTimeWithDayAttr;

/**
 * コンバート表
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TABLE")
public class ScvmtConversionTable extends JpaEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "CONVERSION_TYPE")
	private String conversionType;

//	@ManyToOne
//	@JoinColumns({
//		@JoinColumn(name = "CATEGORY_NAME", referencedColumnName = "CATEGORY_NAME"),
//		@JoinColumn(name = "TARGET_TBL_NAME", referencedColumnName = "TARGET_TBL_NAME"),
//		@JoinColumn(name = "RECORD_NO", referencedColumnName = "RECORD_NO")
//    })
//	public ScvmtConversionRecord record;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeNone_pk", referencedColumnName="pk")
	public ScvmtConversionTypeNone typeNone;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeCodeToId_pk", referencedColumnName="pk")
	public ScvmtConversionTypeCodeToId typeCodeToId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeCodeToCode_pk", referencedColumnName="pk")
	public ScvmtConversionTypeCodeToCode typeCodeToCode;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeFixedValue_pk", referencedColumnName="pk")
	public ScvmtConversionTypeFixedValue typeFixedValue;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeFixedValueWithCondition_pk", referencedColumnName="pk")
	public ScvmtConversionTypeFixedValueWithCondition typeFixedValueWithCondition;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeParent_pk", referencedColumnName="pk")
	public ScvmtConversionTypeParent typeParent;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeStringConcat_pk", referencedColumnName="pk")
	public ScvmtConversionTypeStringConcat typeStringConcat;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeTimeWithDayAttr_pk", referencedColumnName="pk")
	public ScvmtConversionTypeTimeWithDayAttr typeTimeWithDayAttr;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeDateTimeMerge_pk", referencedColumnName="pk")
	public ScvmtConversionTypeDateTimeMerge typeDateTimeMerge;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeGuid_pk", referencedColumnName="pk")
	public ScvmtConversionTypeGuid typeGuid;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typePassword_pk", referencedColumnName="pk")
	public ScvmtConversionTypePassword typePassword;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="typeFileId_pk", referencedColumnName="pk")
	public ScvmtConversionTypeFileId typeFileId;


	@Override
	protected Object getKey() {
		return pk;
	}

	public ConversionTable toDomain(ConversionInfo info, List<OneColumnConversion> columns, String sourceCondition) {
		List<WhereSentence> where = createWhereSentence(info, sourceCondition);

		return new ConversionTable(
					new TableName(info.getTargetDatabaseName(), info.getTargetSchema(), pk.getTargetTableName(), "base"),
					where,
					columns
				);
	}

	public OneColumnConversion toDomain(ConversionInfo info, Join sourceJoin) {
		return new OneColumnConversion(
				this.pk.getTargetColumnName(),
				this.conversionType,
				this.createConversionPattern(info, sourceJoin)
			);
	}

	private ConversionPattern createConversionPattern(ConversionInfo info, Join sourceJoin) {
		ConversionType type = ConversionType.parse(this.conversionType);

		switch(type) {
			case None:
				return typeNone.toDomain(info, sourceJoin);
			case CodeToId:
				return typeCodeToId.toDomain(info, sourceJoin);
			case CodeToCode:
				return typeCodeToCode.toDomain(info, sourceJoin);
			case FixedValue:
				return typeFixedValue.toDomain(info);
			case FixedValueWithCondition:
				return typeFixedValueWithCondition.toDomain(info, sourceJoin);
			case Parent:
				return typeParent.toDomain(info, sourceJoin);
			case StringConcat:
				return typeStringConcat.toDomain(info, sourceJoin);
			case TimeWithDayAttr:
				return typeTimeWithDayAttr.toDomain(info, sourceJoin);
			case DateTimeMerge:
				return typeDateTimeMerge.toDomain(info, sourceJoin);
			case Guid:
				return typeGuid.toDomain(info);
			case Password:
				return typePassword.toDomain(info, sourceJoin);
			case FileId:
				return typeFileId.toDomain(info, sourceJoin);
		}

		throw new RuntimeException("ConversionPatternが不正です");
	}

	private List<WhereSentence> createWhereSentence(ConversionInfo info, String sourceCondition) {

		List<WhereSentence> where = new ArrayList<>();
		String[] conditions = sourceCondition.toUpperCase().split("AND");

		for (String condition : conditions) {
			RelationalOperator operator = null;
			for (RelationalOperator oreratorValue : RelationalOperator.values()) {
				if(condition.contains(oreratorValue.getSign())) {
					operator = oreratorValue;
					break;
				}
			}

			if(operator == null) throw new RuntimeException();

			String[] expressions = condition.split(operator.getSign());

			if(expressions.length < 2) throw new RuntimeException();

			where.add(new WhereSentence(
					new ColumnName(Constants.BaseTableAlias, expressions[0]),
					operator,
					Optional.of(new ColumnExpression(Optional.empty(), expressions[1]))
				));
		}

		return where;
	}

}
