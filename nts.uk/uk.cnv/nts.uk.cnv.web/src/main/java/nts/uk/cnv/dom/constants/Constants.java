package nts.uk.cnv.dom.constants;

public final class Constants {

    /**基準テーブルエイリアス名 **/
    public static final String BaseTableAlias = "base";
    
	/** 契約コード **/
    public static final String ContractCodeParamName = "ContractCode";
    
    /** CID変換テーブル名 **/
    public static final String CidMappingTableName = "SCVMT_CCD_TO_CID";

    /** 暗号化テーブル名 **/
    public static final String EncryptionTableName = "SCVMT_ENCRYPTION_MAPPING";
    /** 暗号化テーブルエイリアス名 **/
    public static final String EncryptionTableAlias = "pass";
    /** 暗号化列名 **/
    public static final String EncryptionColumnName = "PASSWORD";
    
    /** Erp共通会社マスタテーブル名 **/
    public static final String kyotukaisya_m = "kyotukaisya_m";
    
    /** Erp個人ID列名 **/
    public static final String kojin_id = "kojin_id";
    /** UKログインパスワードとして暗号化するErpの対象列名 **/
    public static final String kyotukaisya_pass = "共通パスワード";
    
    /**
     * static class
     */
    private Constants() {
    }
}
