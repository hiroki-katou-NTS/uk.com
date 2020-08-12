package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;

import java.util.Optional;

/**
 * The Class Specify Creation
 */
// Domain object: 作成方法の指定

@Getter
@AllArgsConstructor
public class SpecifyCreation extends DomainObject {

    /** The make. */
    // 	作成方法
    private final CreationMethod creationMethod;

    /** The copy start date. */
    // 	コピー開始日
    private final Optional<GeneralDate> copyStartDate;

    /** The reference master. */
    // マスタ参照先
    private final Optional<ReferenceMaster> referenceMaster;

    /** The monthly pattern code. */
    // 月間パターンコード
    private final Optional<MonthlyPatternCode> monthlyPatternCode;

}
