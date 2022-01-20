package nts.uk.ctx.at.function.dom.adapter.mailserver;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@Getter
public class MailServerSetImportDto {
    private boolean mailServerSet;

    /**
     * ドメインモデル「メールサーバ」
     */
    private Optional<MailServerImportDto> mailServerExport;
}
