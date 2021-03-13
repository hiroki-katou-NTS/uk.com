package nts.uk.cnv.dom.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nts.uk.cnv.dom.td.tabledefinetype.DataType;
import nts.uk.cnv.dom.td.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.tabledesign.DefineColumnType;

public final class Constants {

    /**基準テーブルエイリアス名 **/
    public static final String BaseTableAlias = "base";

	/** 契約コード **/
    public static final String ContractCodeParamName = "CONTRACT_CD";

    /** CID変換テーブル名 **/
    public static final String CidMappingTableName = "SCVMT_MAPPING_CODE_TO_CID";

    /** 暗号化テーブル名 **/
    public static final String EncryptionTableName = "SCVMT_MAPPING_PASSWORD";
    /** 暗号化テーブルエイリアス名 **/
    public static final String EncryptionTableAlias = "pass";
    /** 暗号化列名 **/
    public static final String EncryptionColumnName = "PASSWORD";

    /** Erp共通会社マスタテーブル名 **/
    public static final String kyotukaisya_m = "kyotukaisya_m";


    /** =========================================== **
        パスワード変換
     ** =========================================== **/
    /** Erp個人ID列名 **/
    public static final String kojin_id = "kojin_id";
    /** UKログインパスワードとして暗号化するErpの対象列名 **/
    public static final String kyotukaisya_pass = "共通パスワード";


    /** =========================================== **
        コード変換
     ** =========================================== **/
    public static final String MAPPING_TABLE_NAME = "SCVMT_MAPPING_CODE_TO_CODE";
    public static final String MAPPING_TYPE_COLUMN_NAME = "MAPPING_TYPE";
    public static final String MAPPING_IN_COLUMN_NAME = "IN_VALUE";
    public static final String MAPPING_OUT_COLUMN_NAME = "OUT_VALUE";

    /** 固定列 **/
	public static final List<ColumnDesign> FixColumns = Collections.unmodifiableList( new ArrayList<ColumnDesign>() {{
		add (new ColumnDesign("1", "INS_DATE", "",
				new DefineColumnType(DataType.DATETIME, 0, 0, true, "", ""), "", 1));
		add (new ColumnDesign("2", "INS_CCD", "",
				new DefineColumnType(DataType.CHAR, 4, 0, true, "", ""), "", 2));
		add (new ColumnDesign("3", "INS_SCD", "",
				new DefineColumnType(DataType.CHAR, 12, 0, true, "", ""), "", 3));
		add (new ColumnDesign("4", "INS_PG", "",
				new DefineColumnType(DataType.CHAR, 14, 0, true, "", ""), "", 4));
		add (new ColumnDesign("5", "UPD_DATE", "",
				new DefineColumnType(DataType.DATETIME, 0, 0, true, "", ""), "", 5));
		add (new ColumnDesign("6", "UPD_CCD", "",
				new DefineColumnType(DataType.CHAR, 4, 0, true, "", ""), "", 6));
		add (new ColumnDesign("7", "UPD_SCD", "",
				new DefineColumnType(DataType.CHAR, 12, 0, true, "", ""), "", 7));
		add (new ColumnDesign("8", "UPD_PG", "",
				new DefineColumnType(DataType.CHAR, 14, 0, true, "", ""), "", 8));
		add (new ColumnDesign("9", "EXCLUS_VER", "",
				new DefineColumnType(DataType.INT, 8, 0, false, "", ""), "0", 9));
	}} );

	/** RLS対応のDDL生成のためのpostgreユーザ名 */
	public static final String rlsUserName = "ukuser";

    /**
     * static class
     */
    private Constants() {
    }
}
