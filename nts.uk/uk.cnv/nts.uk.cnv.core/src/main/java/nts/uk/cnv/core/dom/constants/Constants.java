package nts.uk.cnv.core.dom.constants;

public final class Constants {

    /**基準テーブルエイリアス名 **/
    public static final String BaseTableAlias = "base";

	/** 契約コード **/
    public static final String ContractCodeParamName = "CONTRACT_CD";

    /** 期間指定パラメータ名 **/
    public static final String StartDateParamName = "START_DATE";
    public static final String EndDateParamName = "END_DATE";

    /** CID変換テーブル名（WorkDB） **/
    public static final String CidMappingTableName = "SCVMT_MAPPING_CODE_TO_CID";

    /** 暗号化テーブル名（WorkDB） **/
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

	/** RLS対応のDDL生成のためのpostgreユーザ名 */
	public static final String rlsUserName = "ukuser";

    /**
     * static class
     */
    private Constants() {
    }
}
