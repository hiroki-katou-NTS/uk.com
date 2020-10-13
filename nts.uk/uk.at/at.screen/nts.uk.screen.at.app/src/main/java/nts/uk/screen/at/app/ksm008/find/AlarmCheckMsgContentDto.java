package nts.uk.screen.at.app.ksm008.find;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlarmCheckMsgContentDto {
    /**
     * 既定のメッセージ
     */
    private String defaultMsg;

    /**
     * 任意のメッセージ
     */
    private String message;
}
