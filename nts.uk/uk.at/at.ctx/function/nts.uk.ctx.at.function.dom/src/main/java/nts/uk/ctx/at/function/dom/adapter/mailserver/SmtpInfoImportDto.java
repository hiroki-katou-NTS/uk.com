package nts.uk.ctx.at.function.dom.adapter.mailserver;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SmtpInfoImportDto {
    private String server;

    private Integer port;
}
