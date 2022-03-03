package nts.uk.ctx.exio.dom.input.setting.copy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

import java.io.InputStream;

/**
 * 外部受入の初期データ項目
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExternalImportDefaultDataItem {

    /** SMILE組織情報 */
    SMILE_SOSHIKI(new ExternalImportCode("SL1"), ""),

    /** SMILE人事基本情報 */
    SMILE_JINJI_KIHON(new ExternalImportCode("SL1"), ""),

    /** SMILE職制情報 */
    SMILE_SHOKUSEI(new ExternalImportCode("SL1"), ""),

    /** SMILE休職情報 */
    SMILE_KYUSHOKU(new ExternalImportCode("SL1"), ""),

    ;

    /** コピー元設定コード */
    @Getter
    private final ExternalImportCode sourceDataCode;

    /** サンプルCSVファイルのリソースのパス */
    private final String sampleCsvResourcePath;

    public String storeBaseCsvFile(RequireStoreBaseCsvFile require) {
        val file = this.getClass().getClassLoader().getResourceAsStream(sampleCsvResourcePath);
        return require.storeFileThenGetFileId(file, getFileName(), "csv");
    }

    private String getFileName() {
        String[] parts = sampleCsvResourcePath.split("/");
        return parts[parts.length - 1];
    }

    public interface RequireStoreBaseCsvFile {
        String storeFileThenGetFileId(InputStream fileInputStream, String fileName, String fileType);
    }

}
