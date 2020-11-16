package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * 	ValueObject: 出力項目詳細の選択勤怠項目
 * 	@author chinh.hm
 */
@AllArgsConstructor
@Getter
@Setter
public class OutputItemDetailAttItem extends ValueObject {
    //	演算子
    private OperatorsCommonToForms operator ;

    // 	勤怠項目ID
    private int attendanceItemId;

    // 	[C-0] 出力項目詳細を作成する
}
