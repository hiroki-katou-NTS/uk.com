package nts.uk.ctx.at.function.app.find.alarm.workplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FixedExtractionCdtOutput {

    // ID
    private String id;

    // NO
    private int no;

    // 区分
    private int classification;

    // 名称
    private String name;

    // 表示メッセージ
    private String message;

}
