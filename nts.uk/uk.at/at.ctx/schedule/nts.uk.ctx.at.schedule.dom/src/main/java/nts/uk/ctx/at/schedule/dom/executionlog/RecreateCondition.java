package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;


@Getter
@AllArgsConstructor

//再作成条件
public class RecreateCondition {

    //対象者を限定する
    private final Boolean limitTarget;

    //確定済みも対象とする
    private final Boolean confirmed;

    //手修正・申請反映も対象とする
    private final Boolean handCorrection;

    private final Optional<NarrowingEmployees> narrowingEmployees;
}
