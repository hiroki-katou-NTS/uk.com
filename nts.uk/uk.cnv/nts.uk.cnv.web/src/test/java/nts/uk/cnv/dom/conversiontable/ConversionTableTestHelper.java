package nts.uk.cnv.dom.conversiontable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.OnSentence;
import nts.uk.cnv.dom.conversionsql.TableFullName;
import nts.uk.cnv.dom.conversiontable.pattern.CodeToIdPattern;
import nts.uk.cnv.dom.conversiontable.pattern.FixedValuePattern;
import nts.uk.cnv.dom.conversiontable.pattern.NotChangePattern;
import nts.uk.cnv.dom.conversiontable.pattern.ParentJoinPattern;
import nts.uk.cnv.dom.service.ConversionInfo;

public class ConversionTableTestHelper {

	public static ConversionTable create_emptyDummy() {
		return new ConversionTable(
				new TableFullName(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				new ArrayList<>(),
				new ArrayList<>());
	}

	public static ConversionTable create_companyDummy(ConversionInfo info) {
		return new ConversionTable(
				new TableFullName(info.getTargetDatabaseName(), info.getTargetSchema(), "BCMMT_COMPANY", ""),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				new ArrayList<>(),
				Arrays.asList(
					new OneColumnConversion(
						"CID",
						"CODE_TO_ID",
						new CodeToIdPattern(
							info,
							new Join(
									new TableFullName(info.getSourceDatabaseName(), info.getSourceSchema(), Constants.CidMappingTableName, "ccd_cid"),
									JoinAtr.InnerJoin,
									Arrays.asList(new OnSentence(new ColumnName(Constants.BaseTableAlias, "会社CD"), new ColumnName("ccd_cid", "会社CD"), Optional.empty()))),
							"CID",
							"TO_CID",
							null
						)
					),
					new OneColumnConversion(
							"CCD",
							"PARENT",
							new ParentJoinPattern(
									info,
									new Join(new TableFullName(info.getSourceDatabaseName(), info.getSourceSchema(), "kyotukaisya_m", Constants.BaseTableAlias),
									JoinAtr.Main,
									null),
									new Join(new TableFullName(info.getSourceDatabaseName(), info.getSourceSchema(), Constants.CidMappingTableName, "ccd_cid"),
									JoinAtr.InnerJoin,
									Arrays.asList(new OnSentence(new ColumnName(Constants.BaseTableAlias, "会社CD"), new ColumnName("ccd_cid", "会社CD"), Optional.empty()))),
									"BCMMT_COMPANY",
									"CCD"
							)
					),
					new OneColumnConversion(
							"CONTRACT_CD",
							"FIXED_VALUE",
							new FixedValuePattern(info, true, Constants.ContractCodeParamName)
					),
					new OneColumnConversion(
							"NAME",
							"NONE",
							new NotChangePattern(
									info,
									new Join(new TableFullName(info.getSourceDatabaseName(), info.getSourceSchema(), "kyotukaisya_m", Constants.BaseTableAlias),
										JoinAtr.Main,
										null),
									"kainame"
							)
					)
				)
			);
	}

	public static ConversionTable create_personDummy(ConversionInfo info) {
		return new ConversionTable(
				new TableFullName(info.getTargetDatabaseName(), info.getTargetSchema(), "BPSMT_PERSON", ""),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				new ArrayList<>(),
				Arrays.asList(
					new OneColumnConversion(
						"PERSON_NAME",
						"NONE",
						new NotChangePattern(
								info,
								new Join(new TableFullName(info.getSourceDatabaseName(), info.getSourceSchema(), "jm_kihon", Constants.BaseTableAlias),
									JoinAtr.Main,
									null),
								"社員名"
						)
					)
				)
			);
	}

}
