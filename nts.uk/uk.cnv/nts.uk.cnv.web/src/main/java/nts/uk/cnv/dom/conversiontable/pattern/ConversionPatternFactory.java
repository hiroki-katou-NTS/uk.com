package nts.uk.cnv.dom.conversiontable.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.OnSentence;
import nts.uk.cnv.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.dom.tabledefinetype.databasetype.DatabaseType;

public class ConversionPatternFactory {

	public static ConversionPattern create(ConversionPatternValue param) {
		ConversionInfo info = new ConversionInfo(DatabaseType.sqlserver, "KINJIROU_ERP", "dbo", "KINJIROU_UK", "dbo", "000000000000");
		Join join = new Join(
				new TableName("KINJIROU_ERP", "dbo", param.getSourceTable(), "base"),
				JoinAtr.Main, new ArrayList<>());

		return create(info, join, param);
	}

	public static ConversionPattern create(ConversionInfo info, Join join, ConversionPatternValue param) {
		ConversionType type = ConversionType.parse(param.getConversionType());

		switch(type) {
			case None:
				return new NotChangePattern(info, join, param.getSourceColumn_none());
			case CodeToId:
				return new CodeToIdPattern(info, join,
						param.getSourceColumn_codeToId(),
						param.getCodeToIdType(),
						param.getSourceColumn_codeToId_ccd());
			case CodeToCode:
				return new CodeToCodePattern(info, join,
						param.getSourceColumn_codeToCode(),
						param.getCodeToCodeType());
			case FixedValue:
				return new FixedValuePattern(info,
						param.isFixedValueIsParam(),
						param.getFixedValue());
			case FixedValueWithCondition:
				return new FixedValueWithConditionPattern(info, join,
						param.getSourceColumn_fixedCalueWithCond(),
						RelationalOperator.parse(param.getOperator()),
						param.getConditionValue(),
						param.isFixedValueWithCondIsParam(),
						param.getFixedValueWithCond());
			case Parent:
				List<OnSentence> onSentences = new ArrayList<>();
				for (String pk : param.getJoinPKs().split(",")) {
					onSentences.add(
						new OnSentence(
							new ColumnName("parent", pk),
							new ColumnName("couverted_source", pk),
							Optional.empty()
						)
					);
				}

				return new ParentJoinPattern(info, join,
						new Join(
								new TableName("KINJIROU_ERP", "dbo", param.getParentTable(), "parent"),
								JoinAtr.InnerJoin,
								onSentences),
						param.getSourceColumn_parent());
			case StringConcat:
				return new StringConcatPattern(info, join,
						param.getSourceColumn1(),
						param.getSourceColumn2(),
						(param.getDelimiter().isEmpty()) ? Optional.empty() : Optional.of(param.getDelimiter()));
			case TimeWithDayAttr:
				return new TimeWithDayAttrPattern(info, join,
						param.getSourceColumn_timeWithDayAttr_time(),
						param.getSourceColumn_timeWithDayAttr_dayAttr());
			case DateTimeMerge:
				return DateTimeMergePattern.builder()
						.yyyymmdd(param.getSourceColumn_yyyymmdd())
						.yyyy(param.getSourceColumn_yyyy())
						.mm(param.getSourceColumn_mm())
						.yyyymm(param.getSourceColumn_yyyymm())
						.mmdd(param.getSourceColumn_mmdd())
						.dd(param.getSourceColumn_dd())
						.hh(param.getSourceColumn_hh())
						.mi(param.getSourceColumn_mi())
						.hhmi(param.getSourceColumn_hhmi())
						.ss(param.getSourceColumn_ss())
						.minutes(param.getSourceColumn_minutes())
						.yyyymmddhhmi(param.getSourceColumn_yyyymmddhhmi())
						.yyyymmddhhmiss(param.getSourceColumn_yyyymmddhhmiss())
						.build();
			case Guid:
				return new GuidPattern(info);
			case Password:
				return new PasswordPattern(info, join, param.getSourceColumn_password());
			case FileId:
				return new FileIdPattern(info, join, param.getSourceColumn_fileId());
		}
		return null;
	}
}
