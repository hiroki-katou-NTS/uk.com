package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.ConditionEmployee;

import java.util.Optional;


@Getter
@AllArgsConstructor
@Setter
//再作成条件
public class RecreateCondition {

    //対象者を限定する
    private final Boolean reTargetAtr;

    //確定済みも対象とする
    private final Boolean reOverwriteConfirmed;

    //手修正・申請反映も対象とする
    private final Boolean reOverwriteRevised;

    private final Optional<ConditionEmployee> narrowingEmployees;
}
