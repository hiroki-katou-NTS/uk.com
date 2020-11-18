package nts.uk.cnv.infra.entity.conversiontable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
import nts.uk.cnv.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.dom.conversiontable.pattern.ConversionType;
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
@Builder
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

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypeNone typeNone;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypeCodeToId typeCodeToId;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypeCodeToCode typeCodeToCode;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypeFixedValue typeFixedValue;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypeFixedValueWithCondition typeFixedValueWithCondition;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypeParent typeParent;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypeStringConcat typeStringConcat;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypeTimeWithDayAttr typeTimeWithDayAttr;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypeDateTimeMerge typeDateTimeMerge;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypeGuid typeGuid;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
	public ScvmtConversionTypePassword typePassword;

	@OneToOne(optional=true, mappedBy="conversionTable", cascade=CascadeType.ALL)
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

	public static ScvmtConversionTable toEntity(String category, String table, int recordNo, OneColumnConversion domain) {
		ScvmtConversionTablePk pk = new ScvmtConversionTablePk(category, table, recordNo, domain.getTargetColumn());
		ConversionPattern conversionPattern = domain.getPattern();

		return ScvmtConversionTable.builder()
			.pk(pk)
			.conversionType(domain.getConversionType())
			.typeNone(ScvmtConversionTypeNone.toEntity(pk, conversionPattern))
			.typeCodeToId(ScvmtConversionTypeCodeToId.toEntity(pk, conversionPattern))
			.typeCodeToCode(ScvmtConversionTypeCodeToCode.toEntity(pk, conversionPattern))
			.typeFixedValue(ScvmtConversionTypeFixedValue.toEntity(pk, conversionPattern))
			.typeFixedValueWithCondition(ScvmtConversionTypeFixedValueWithCondition.toEntity(pk, conversionPattern))
			.typeParent(ScvmtConversionTypeParent.toEntity(pk, conversionPattern))
			.typeStringConcat(ScvmtConversionTypeStringConcat.toEntity(pk, conversionPattern))
			.typeTimeWithDayAttr(ScvmtConversionTypeTimeWithDayAttr.toEntity(pk, conversionPattern))
			.typeDateTimeMerge(ScvmtConversionTypeDateTimeMerge.toEntity(pk, conversionPattern))
			.typeGuid(ScvmtConversionTypeGuid.toEntity(pk, conversionPattern))
			.typePassword(ScvmtConversionTypePassword.toEntity(pk, conversionPattern))
			.typeFileId(ScvmtConversionTypeFileId.toEntity(pk, conversionPattern))
			.build();
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

		if (sourceCondition == null || sourceCondition.isEmpty()) return where;

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
