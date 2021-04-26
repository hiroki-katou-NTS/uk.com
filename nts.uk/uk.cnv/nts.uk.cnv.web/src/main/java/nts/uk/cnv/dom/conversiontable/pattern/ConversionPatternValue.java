package nts.uk.cnv.dom.conversiontable.pattern;

public interface ConversionPatternValue {

	/** main **/
	String getConversionType();
	String getSourceTable();

	/** NONE **/
	String getSourceColumn_none();

	/** CODE_TO_CODE **/
	String getCodeToCodeType();
	String getSourceColumn_codeToCode();

	/** CODE_TO_ID **/
	String getCodeToIdType();
	String getSourceColumn_codeToId();
	String getSourceColumn_codeToId_ccd();

	/** FIXID_VALUE **/
	String getFixedValue();
	boolean isFixedValueIsParam();

	/** FIXID_VALUE_WITH_CONDITION **/
	String getSourceColumn_fixedCalueWithCond();
	String getOperator();
	String getConditionValue();
	String getFixedValueWithCond();
	boolean isFixedValueWithCondIsParam();

	/** PARENT **/
	String getParentTable();
	String getSourceColumn_parent();
	String getJoinPKs();

	/** STRING_CONCAT **/
	String getSourceColumn1();
	String getSourceColumn2();
	String getDelimiter();

	/** TIME_WITH_DAY_ATTR **/
	String getSourceColumn_timeWithDayAttr_dayAttr();
	String getSourceColumn_timeWithDayAttr_time();

	/** DATETIME_MERGE **/
	String getSourceColumn_yyyymmdd();
	String getSourceColumn_yyyy();
	String getSourceColumn_mm();
	String getSourceColumn_yyyymm();
	String getSourceColumn_mmdd();
	String getSourceColumn_dd();
	String getSourceColumn_hh();
	String getSourceColumn_mi();
	String getSourceColumn_hhmi();
	String getSourceColumn_ss();
	String getSourceColumn_minutes();
	String getSourceColumn_yyyymmddhhmi();
	String getSourceColumn_yyyymmddhhmiss();

	/** GUID **/

	/** PASSWORD **/
	String getSourceColumn_password();

	/** FILE_ID **/
	String getSourceColumn_fileId();
}
