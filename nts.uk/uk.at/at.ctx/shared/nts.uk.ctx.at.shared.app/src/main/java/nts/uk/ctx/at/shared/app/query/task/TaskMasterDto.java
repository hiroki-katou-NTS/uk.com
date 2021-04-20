package nts.uk.ctx.at.shared.app.query.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class TaskMasterDto {
    // 作業コード
    private String code;

    // 作業名称
    private String taskName;

    // 作業略名
    private String taskAbName;

    // 有効期限
    private GeneralDate expirationStartDate;

    private GeneralDate expirationEndDate;

    // 備考
    private String remark;
}
