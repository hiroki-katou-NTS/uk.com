package nts.uk.ctx.at.request.dom.application.common.adapter.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AtStandardMenuNameImport {

    /**
     * プログラムID
     */
    private String programId;

    /**
     * 遷移先の画面ID
     */
    private String screenId;

    /**
     * クエリ文字列
     */
    private String queryString;

    /**
     * 表示名称
     */
    private String displayName;

}
