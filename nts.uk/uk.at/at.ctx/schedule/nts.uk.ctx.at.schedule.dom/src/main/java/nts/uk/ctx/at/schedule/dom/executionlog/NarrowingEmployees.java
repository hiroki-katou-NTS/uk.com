package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

//Domain object: 社員の絞り込み条件
@Getter
@AllArgsConstructor
public class NarrowingEmployees extends DomainObject {

    //休職休業者
    private Boolean leaveAbsence;

    //労働条件変更者
    private Boolean personChanged;

    //異動者
    private Boolean transfer;

    //短時間勤務者
    private Boolean shortWorking;
}
