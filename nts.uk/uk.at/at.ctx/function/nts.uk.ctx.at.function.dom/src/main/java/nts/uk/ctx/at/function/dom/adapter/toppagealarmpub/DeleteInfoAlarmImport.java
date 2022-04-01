package nts.uk.ctx.at.function.dom.adapter.toppagealarmpub;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 削除の情報Param
 *
 */
@Data
@Builder
@AllArgsConstructor
public class DeleteInfoAlarmImport {
    /**
     * アラーム分類
     */
    private int alarmClassification;
    
    /**
     * 社員IDList
     */
    private List<String> sids;
    
    /**
     * 表示社員区分
     */
    private int displayAtr;
    
    /**
     * パターンコード
     */
    private Optional<String> patternCode;

    /**
     * 部下のエラーがない社員IDList：社員ID（List）
     */
    private List<String> subEmpNoErrs;
}
