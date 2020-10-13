package nts.uk.query.app.exo;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import java.util.Optional;

@Data
@AllArgsConstructor
public class ExternalOutLogQueryDto {

    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 外部出力処理ID
     */
    private String outputProcessId;

    /**
     * エラー内容
     */
    private Optional<String> errorContent;

    /**
     * エラー対象値
     */
    private Optional<String> errorTargetValue;

    /**
     * エラー日付
     */
    private Optional<GeneralDate> errorDate;

    /**
     * エラー社員
     */
    private Optional<String> errorEmployee;

    /**
     * エラー項目
     */
    private Optional<String> errorItem;

    /**
     * ログ登録日時
     */
    private GeneralDateTime logRegisterDateTime;

    /**
     * ログ連番
     */
    private int logSequenceNumber;

    /**
     * 処理カウント
     */
    private int processCount;

    /**
     * 処理内容
     */
    private String processContent;
}
