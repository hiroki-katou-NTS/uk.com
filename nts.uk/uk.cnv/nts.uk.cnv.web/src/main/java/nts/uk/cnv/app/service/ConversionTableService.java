package nts.uk.cnv.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.cnv.app.dto.FindConversionTableDto;
import nts.uk.cnv.app.dto.FindConversionTableResult;
import nts.uk.cnv.app.dto.GetCategoryTablesDto;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.conversionsql.WhereSentence;
import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.dom.conversiontable.ConversionRecordRepository;
import nts.uk.cnv.dom.conversiontable.ConversionSource;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;
import nts.uk.cnv.dom.conversiontable.ConversionTable;
import nts.uk.cnv.dom.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.dom.conversiontable.pattern.CodeToCodePattern;
import nts.uk.cnv.dom.conversiontable.pattern.CodeToIdPattern;
import nts.uk.cnv.dom.conversiontable.pattern.ConversionType;
import nts.uk.cnv.dom.conversiontable.pattern.DateTimeMergePattern;
import nts.uk.cnv.dom.conversiontable.pattern.FileIdPattern;
import nts.uk.cnv.dom.conversiontable.pattern.FixedValuePattern;
import nts.uk.cnv.dom.conversiontable.pattern.FixedValueWithConditionPattern;
import nts.uk.cnv.dom.conversiontable.pattern.NotChangePattern;
import nts.uk.cnv.dom.conversiontable.pattern.ParentJoinPattern;
import nts.uk.cnv.dom.conversiontable.pattern.PasswordPattern;
import nts.uk.cnv.dom.conversiontable.pattern.StringConcatPattern;
import nts.uk.cnv.dom.conversiontable.pattern.TimeWithDayAttrPattern;
import nts.uk.cnv.dom.service.ConversionInfo;

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
		ConversionInfo info = new ConversionInfo();
		return mappingData(this.find(info, dto));
	}

	public OneColumnConversion find(ConversionInfo info, FindConversionTableDto dto) {
		ConversionRecord record = recordRepo.getRecord(dto.getCategory(), dto.getTable(), dto.getRecordNo());

		ConversionSource source = conversionSourceRepo.get(record.getSourceId());
		return repo.findColumnConversion(info, dto.getCategory(), dto.getTable(), dto.getRecordNo(), dto.getUkColumn(), source.getJoin(info)).orElse(null);
	}

	public GetCategoryTablesDto getCategoryTables(String category) {
		return new GetCategoryTablesDto(
				categoryRepo.get(category).stream()
					.map(ct -> ct.getTablename())
					.collect(Collectors.toList())
			);
	}

	/**
	 * 一列分の変換コードをテスト出力
	 * @param dto
	 * @return
	 */
	public String test(FindConversionTableDto dto) {
		ConversionInfo info = ConversionInfo.createDummry();
		ConversionRecord record = recordRepo.getRecord(dto.getCategory(), dto.getTable(), dto.getRecordNo());

		List<WhereSentence> whereList = new ArrayList<>();
		List<OneColumnConversion> conversionMap = new ArrayList<>();

		ConversionSource source = conversionSourceRepo.get(record.getSourceId());
		Optional<OneColumnConversion> onColumn = repo.findColumnConversion(info, dto.getCategory(), dto.getTable(), dto.getRecordNo(), dto.getUkColumn(), source.getJoin(info));

		if(!onColumn.isPresent()) {
			throw new BusinessException("コンバート表が登録されていません");
		}

		conversionMap.add(onColumn.get());

		if(!source.getCondition().isEmpty()) {
			whereList = WhereSentence.parse(source.getCondition());
		}

		ConversionTable conversonTable = new ConversionTable(
				new TableName(info.getTargetDatabaseName(), info.getSourceSchema(), dto.getTable(), ""),
				whereList,
				conversionMap
			);

		ConversionSQL sql = conversonTable.createConversionSql();

		return sql.build(info);
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
			result.setParentTable(parent.getParentJoin().getTableName().getName());
			result.setSourceColumn_parent(parent.getParentColumn());
			result.setJoinPKs(
						String.join(
							",",
							parent.getParentJoin().onSentences.stream()
								.map(on -> on.getLeft().getName())
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
		}

		return null;
	}
}
