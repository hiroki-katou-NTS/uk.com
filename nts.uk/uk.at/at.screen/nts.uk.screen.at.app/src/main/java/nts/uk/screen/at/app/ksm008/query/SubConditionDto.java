package nts.uk.screen.at.app.ksm008.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubConditionDto {
    //    サブ条件リスト.サブコード
    private String subCode;
    //    サブ条件リスト.説明
    private String description;
    //    サブ条件リスト.メッセージ.任意のメッセージ
    private String message;

}
