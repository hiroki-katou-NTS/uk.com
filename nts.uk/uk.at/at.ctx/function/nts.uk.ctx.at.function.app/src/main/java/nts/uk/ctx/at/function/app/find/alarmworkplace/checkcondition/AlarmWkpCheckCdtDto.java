package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmWkpCheckCdtDto {

    // カテゴリ
    private int category;

    // アラームチェック条件コード
    private String code;

    // 会社ID
    private String cID;

    // 名称
    private String name;

}
