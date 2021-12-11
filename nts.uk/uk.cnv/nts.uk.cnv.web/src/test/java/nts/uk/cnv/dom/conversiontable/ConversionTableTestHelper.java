package nts.uk.cnv.dom.conversiontable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import nemunoki.oruta.shr.tabledefinetype.databasetype.SqlServerSpec;
import nts.uk.cnv.core.dom.constants.Constants;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.OnSentence;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.core.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.core.dom.conversiontable.pattern.CodeToIdPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.FixedValuePattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.NotChangePattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.ParentJoinPattern;

public class ConversionTableTestHelper {

	public static ConversionTable create_emptyDummy() {
		return new ConversionTable(
				new SqlServerSpec(),
				new TableFullName(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				new ArrayList<>(),
				new ArrayList<>(),
				true);
	}

	public static ConversionTable create_companyDummy(ConversionInfo info) {
		return new ConversionTable(
				new SqlServerSpec(),
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
							new FixedValuePattern(
									info,
									new Join(new TableFullName(info.getSourceDatabaseName(), info.getSourceSchema(), "kyotukaisya_m", Constants.BaseTableAlias),
											JoinAtr.Main,
											null),
									true,
									Constants.ContractCodeParamName)
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
				),
				true
			);
	}

	public static ConversionTable create_personDummy(ConversionInfo info) {
		return new ConversionTable(
				new SqlServerSpec(),
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
				),
				true
			);
	}

}
