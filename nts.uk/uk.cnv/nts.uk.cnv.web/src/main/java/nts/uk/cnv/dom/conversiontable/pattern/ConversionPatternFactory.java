package nts.uk.cnv.dom.conversiontable.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nemunoki.oruta.shr.tabledefinetype.databasetype.DatabaseType;
import nts.uk.cnv.core.dom.constants.Constants;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.OnSentence;
import nts.uk.cnv.core.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.pattern.CodeToCodePattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.CodeToIdPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.ConversionPatternValue;
import nts.uk.cnv.core.dom.conversiontable.pattern.ConversionType;
import nts.uk.cnv.core.dom.conversiontable.pattern.DateTimeMergePattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.FileIdPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.FixedValuePattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.FixedValueWithConditionPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.GuidPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.NotChangePattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.ParentJoinPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.PasswordPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.SourceJoinPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.StringConcatPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.TimeWithDayAttrPattern;

public class ConversionPatternFactory {

	public static ConversionPattern create(ConversionPatternValue param) {
		ConversionInfo info = new ConversionInfo(
				DatabaseType.sqlserver,
				"KINJIROU_ERP", "dbo",
				"KINJIROU_UK", "dbo",
				"UK_CNV", "dbo",
				"000000000000",
				ConversionCodeType.INSERT
			);
		Join join = new Join(
				new TableFullName("KINJIROU_ERP", "dbo", param.getSourceTable(), "base"),
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
				return new FixedValuePattern(info, join,
						param.isFixedValueIsParam(),
						param.getFixedValue());
			case FixedValueWithCondition:
				return new FixedValueWithConditionPattern(info.getDatebaseType().spec(), join,
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

				return new ParentJoinPattern(join,
						new Join(
								new TableFullName("KINJIROU_ERP", "dbo", param.getParentTable(), "parent"),
								JoinAtr.InnerJoin,
								onSentences),
						param.getParentTable(),
						param.getSourceColumn_parent());
			case StringConcat:
				return new StringConcatPattern(info.getDatebaseType().spec(), join,
						param.getSourceColumn1(),
						param.getSourceColumn2(),
						(param.getDelimiter().isEmpty()) ? Optional.empty() : Optional.of(param.getDelimiter()));
			case TimeWithDayAttr:
				return new TimeWithDayAttrPattern(join,
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
				return new GuidPattern(info.getDatebaseType().spec());
			case Password:
				return new PasswordPattern(info, join, param.getSourceColumn_password());
			case FileId:
				return new FileIdPattern(info, join, param.getSourceColumn_fileId(),
						param.getFileType(), param.getSourceColumn_kojinId());
			case SourceJoin:
				List<OnSentence> onSentencesForSourceJoin = new ArrayList<>();
				for (String pk : param.getJoinSourcePKs().split(",")) {
					onSentencesForSourceJoin.add(
						new OnSentence(
							new ColumnName("source_" + param.getJoinSourceTable(), pk),
							new ColumnName(Constants.BaseTableAlias, pk),
							Optional.empty()
						)
					);
				}

				return new SourceJoinPattern(
						info,
						new Join(
								info.getSourceTable(param.getJoinSourceTable(), "source_" + param.getJoinSourceTable()),
								JoinAtr.OuterJoin,
								onSentencesForSourceJoin),
						param.getSourceColumn_sourceJoin());
			default:
				break;
		}
		return null;
	}
}
