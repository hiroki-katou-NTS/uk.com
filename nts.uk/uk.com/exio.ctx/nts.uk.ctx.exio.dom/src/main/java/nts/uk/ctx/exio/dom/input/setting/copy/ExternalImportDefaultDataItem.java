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
    SMILE_SOSHIKI(new ExternalImportCode("SL1")),

    /** SMILE人事基本情報 */
    SMILE_JINJI_KIHON(new ExternalImportCode("SL2")),

    /** SMILE職制情報 */
    SMILE_SHOKUSEI(new ExternalImportCode("SL3")),

    /** SMILE休職情報 */
    SMILE_KYUSHOKU(new ExternalImportCode("SL4")),

    ;

    /** コピー元設定コード */
    @Getter
    private final ExternalImportCode sourceDataCode;

    public String storeBaseCsvFile(RequireStoreBaseCsvFile require) {
        val file = this.getClass().getClassLoader().getResourceAsStream(getBaseCsvResourcePath());
        return require.storeFileThenGetFileId(file, getFileName(), "csv");
    }

    private String getFileName() {
        String[] parts = getBaseCsvResourcePath().split("/");
        return parts[parts.length - 1];
    }

    private String getBaseCsvResourcePath() {
        return "input/defaultdata/basecsv/" + this.name() + ".csv";
    }

    public interface RequireStoreBaseCsvFile {
        String storeFileThenGetFileId(InputStream fileInputStream, String fileName, String fileType);
    }

}
