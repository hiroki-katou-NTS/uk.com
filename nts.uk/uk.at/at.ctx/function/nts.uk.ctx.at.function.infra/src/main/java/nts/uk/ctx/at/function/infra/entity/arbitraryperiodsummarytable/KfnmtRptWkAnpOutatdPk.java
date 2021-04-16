package nts.uk.ctx.at.function.infra.entity.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtRptWkAnpOutatdPk  implements Serializable{
    public static final long serialVersionUID = 1L;

    // 	設定ID -> 	任意期間集計表の出力設定.設定ID
    @Column(name = "LAYOUT_ID")
    public String layOutId;

    // 	順位 -> 	印刷する勤怠項目.順位
    @Column(name = "PRINT_POSITION")
    public int printPosition;

}
