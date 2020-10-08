package nts.uk.ctx.at.request.dom.application.common.adapter.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AtMenuNameQueryImport {

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
    private Optional<String> queryString;

}
