package nts.uk.ctx.at.request.app.find.application.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProxyApplicationDispName {

    // プログラムID
    private String programID;

    // 遷移先の画面ID
    private String screenID;

    // クエリ文字列
    private String queryString;

    // 表示名称
    private String displayName;
}
