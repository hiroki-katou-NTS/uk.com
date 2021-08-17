package nts.uk.cnv.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nemunoki.oruta.shr.tabledefinetype.databasetype.DatabaseType;
import nts.arc.error.BusinessException;
import nts.uk.cnv.app.dto.FindConversionTableDto;
import nts.uk.cnv.app.dto.FindConversionTableResult;
import nts.uk.cnv.app.dto.GetCategoryTablesDto;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversionsql.WhereSentence;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.core.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.core.dom.conversiontable.pattern.CodeToCodePattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.CodeToIdPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.ConversionType;
import nts.uk.cnv.core.dom.conversiontable.pattern.DateTimeMergePattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.FileIdPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.FixedValuePattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.FixedValueWithConditionPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.NotChangePattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.ParentJoinPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.PasswordPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.SourceJoinPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.StringConcatPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.TimeWithDayAttrPattern;
import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.dom.conversiontable.ConversionRecordRepository;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;
import nts.uk.cnv.dom.conversiontable.ConversionTableRepository;

@Stateless
public class ConversionTableService {

	@Inject
	ConversionCategoryTableRepository categoryRepo;

	@Inject
	ConversionTableRepository repo;

	@Inject
	ConversionRecordRepository recordRepo;

	@Inject
	ConversionSourcesRepository conversionSourceRepo;

	public FindConversionTableResult find(FindConversionTableDto dto) {
		return mappingData(this.find(ConversionInfo.createDummry(), dto));
	}

	public OneColumnConversion find(ConversionInfo info, FindConversionTableDto dto) {
		ConversionRecord record = recordRepo.getRecord(dto.getCategory(), dto.getTable(), dto.getRecordNo());

		ConversionSource source = conversionSourceRepo.get(record.getSourceId()).get();
		return repo.findColumnConversion(
				info,
				dto.getCategory(),
				dto.getTable(),
				dto.getRecordNo(),
				dto.getUkColumn(),
				info.getJoin(source)).orElse(null);
	}

	public List<GetCategoryTablesDto> getCategoryTables(String category) {
		return categoryRepo.get(category).stream()
					.map(ct -> new GetCategoryTablesDto(
							ct.getTable().getTableId(),
							ct.getTable().getName()))
					.collect(Collectors.toList());
	}

	/**
	 * 一列分の変換コードをテスト出力
	 * @param dto
	 * @return
	 */
	public String test(FindConversionTableDto dto) {
		ConversionInfo info = ConversionInfo.createDummry();

		ConversionRecord record = recordRepo.getRecord(dto.getCategory(), dto.getTable(), dto.getRecordNo());
		ConversionSource source = conversionSourceRepo.get(record.getSourceId()).get();
		Optional<OneColumnConversion> onColumn =
				repo.findColumnConversion(
						info,
						dto.getCategory(),
						dto.getTable(),
						dto.getRecordNo(),
						dto.getUkColumn(),
						info.getJoin(source));

		ConversionTable conversonTable = createConversionTable(
				onColumn, source, info, dto, info.getType().getTagetAlias(), record.isRemoveDuplicate());
		ConversionSQL sql = conversonTable.createConversionSql();

		return sql.build(info.getDatebaseType().spec());
	}

	public String testForUpdate(FindConversionTableDto dto) {
		ConversionInfo info = new ConversionInfo(
				DatabaseType.sqlserver, "", "", "", "", "", "", "", ConversionCodeType.UPDATE);

		ConversionRecord record = recordRepo.getRecord(dto.getCategory(), dto.getTable(), dto.getRecordNo());
		ConversionSource source = conversionSourceRepo.get(record.getSourceId()).get();
		Optional<OneColumnConversion> onColumn =
				repo.findColumnConversion(
						info,
						dto.getCategory(),
						dto.getTable(),
						dto.getRecordNo(),
						dto.getUkColumn(),
						info.getJoin(source));

		ConversionTable conversonTable = createConversionTable(
				onColumn, source, info, dto, info.getType().getTagetAlias(), record.isRemoveDuplicate());
		ConversionSQL sql = conversonTable.createUpdateConversionSql();

		return sql.build(info.getDatebaseType().spec());
	}

	private ConversionTable createConversionTable(
			Optional<OneColumnConversion> onColumn, ConversionSource source, ConversionInfo info, FindConversionTableDto dto, String alias, boolean isRemoveDuplicate) {

		List<WhereSentence> whereList = new ArrayList<>();
		List<OneColumnConversion> conversionMap = new ArrayList<>();

		if(!onColumn.isPresent()) {
			throw new BusinessException("コンバート表が登録されていません");
		}

		conversionMap.add(onColumn.get());

		if(!source.getCondition().isEmpty()) {
			whereList = WhereSentence.parse(source.getCondition());
		}

		return  new ConversionTable(
				info.getDatebaseType().spec(),
				new TableFullName(info.getTargetDatabaseName(), info.getSourceSchema(), dto.getTable(), alias),
				source.getDateColumnName(),
				source.getStartDateColumnName(),
				source.getEndDateColumnName(),
				whereList,
				conversionMap,
				isRemoveDuplicate
			);
	}

	private FindConversionTableResult mappingData(OneColumnConversion domain) {
		if(domain == null) return null;

		FindConversionTableResult result = new FindConversionTableResult();
		result.setConversionType(domain.getConversionType());

		ConversionType type = ConversionType.parse(domain.getConversionType());

		switch (type) {
		case None:
			NotChangePattern none = (NotChangePattern) domain.getPattern();
			result.setSourceColumn_none(none.getSourceColumn());
			return result;
		case CodeToId:
			CodeToIdPattern codeToId = (CodeToIdPattern) domain.getPattern();
			result.setSourceColumn_codeToId(codeToId.getSourceColumnName());
			result.setCodeToIdType(codeToId.getCodeToIdType().name());
			return result;
		case CodeToCode:
			CodeToCodePattern codeToCode = (CodeToCodePattern) domain.getPattern();
			result.setSourceColumn_codeToCode(codeToCode.getSourceColumnName());
			result.setCodeToCodeType(codeToCode.getMappingType());
			return result;
		case FixedValue:
			FixedValuePattern fixedVal = (FixedValuePattern) domain.getPattern();
			result.setFixedValue(fixedVal.getExpression());
			result.setFixedValueIsParam(fixedVal.isParamater());
			return result;
		case FixedValueWithCondition:
			FixedValueWithConditionPattern fixedValWithCond = (FixedValueWithConditionPattern) domain.getPattern();
			result.setFixedValueWithCond(fixedValWithCond.getExpression());
			result.setFixedValueWithCondIsParam(fixedValWithCond.isParamater());
			result.setSourceColumn_fixedCalueWithCond(fixedValWithCond.getSourceColumn());
			result.setOperator(fixedValWithCond.getRelationalOperator().getSign());
			result.setConditionValue(fixedValWithCond.getConditionValue());
			return result;
		case Parent:
			ParentJoinPattern parent = (ParentJoinPattern) domain.getPattern();
			result.setParentTable(parent.getParentTableName());
			result.setSourceColumn_parent(parent.getParentColumn());
			result.setJoinPKs(
						String.join(
							",",
							parent.getMappingJoin().onSentences.stream()
								.map(on -> on.getRight().getName())
								.collect(Collectors.toList())
							)
						);
			return result;
		case StringConcat:
			StringConcatPattern concat = (StringConcatPattern) domain.getPattern();
			result.setSourceColumn1(concat.getColumn1());
			result.setSourceColumn2(concat.getColumn2());
			return result;
		case TimeWithDayAttr:
			TimeWithDayAttrPattern dayAttr = (TimeWithDayAttrPattern) domain.getPattern();
			result.setSourceColumn_timeWithDayAttr_time(dayAttr.getTimeColumn());
			result.setSourceColumn_timeWithDayAttr_dayAttr(dayAttr.getDayAttrColumn());
			return result;
		case DateTimeMerge:
			DateTimeMergePattern dtmerge = (DateTimeMergePattern) domain.getPattern();
			result.setSourceColumn_yyyymmdd(dtmerge.getYyyymmdd());
			result.setSourceColumn_yyyy(dtmerge.getYyyy());
			result.setSourceColumn_mm(dtmerge.getMm());
			result.setSourceColumn_yyyymm(dtmerge.getYyyymm());
			result.setSourceColumn_mmdd(dtmerge.getMmdd());
			result.setSourceColumn_dd(dtmerge.getDd());
			result.setSourceColumn_hh(dtmerge.getHh());
			result.setSourceColumn_mi(dtmerge.getMi());
			result.setSourceColumn_hhmi(dtmerge.getHhmi());
			result.setSourceColumn_ss(dtmerge.getSs());
			result.setSourceColumn_minutes(dtmerge.getMinutes());
			result.setSourceColumn_yyyymmddhhmi(dtmerge.getYyyymmddhhmi());
			result.setSourceColumn_yyyymmddhhmiss(dtmerge.getYyyymmddhhmiss());
			return result;
		case Guid:
			return result;
		case Password:
			PasswordPattern pass = (PasswordPattern) domain.getPattern();
			result.setSourceColumn_password(pass.getSourceColumnName());
			return result;
		case FileId:
			FileIdPattern fileId = (FileIdPattern) domain.getPattern();
			result.setSourceColumn_fileId(fileId.getSourceColumnName());
			return result;
		case SourceJoin:
			SourceJoinPattern sourceJoin = (SourceJoinPattern) domain.getPattern();
			result.setSourceTable(sourceJoin.getSourceJoin().getTableName().getName());
			result.setSourceColumn_sourceJoin(sourceJoin.getSourceColumn());
			result.setJoinSourcePKs(sourceJoin.sourceJoinColumns());
			return result;
		}

		return null;
	}
}
