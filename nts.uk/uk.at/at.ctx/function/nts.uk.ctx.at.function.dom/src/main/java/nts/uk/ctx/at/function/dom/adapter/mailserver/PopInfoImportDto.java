package nts.uk.ctx.at.function.dom.adapter.mailserver;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PopInfoImportDto {
    private String server;

    // サーバ使用
    private Integer useServer;

    // ポート
    private Integer port;
}
