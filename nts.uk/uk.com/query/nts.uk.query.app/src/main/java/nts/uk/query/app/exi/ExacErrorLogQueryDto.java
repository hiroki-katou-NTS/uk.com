package nts.uk.query.app.exi;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

import java.util.Optional;

@AllArgsConstructor
@Data
public class ExacErrorLogQueryDto {

    /**
     * ログ連番
     */
    private int logSeqNumber;

    /**
     * 会社ID
     */
    private String cid;

    /**
     * 外部受入処理ID
     */
    private String externalProcessId;

    /**
     * CSVエラー項目名
     */
    private Optional<String> csvErrorItemName;

    /**
     * CSV受入値
     */
    private Optional<String> csvAcceptedValue;

    /**
     * エラー内容
     */
    private Optional<String> errorContents;

    /**
     * レコード番号
     */
    private int recordNumber;

    /**
     * ログ登録日時
     */
    private GeneralDateTime logRegDateTime;

    /**
     * 項目名
     */
    private Optional<String> itemName;

    /**
     * エラー発生区分
     */
    private String errorAtr;
}
