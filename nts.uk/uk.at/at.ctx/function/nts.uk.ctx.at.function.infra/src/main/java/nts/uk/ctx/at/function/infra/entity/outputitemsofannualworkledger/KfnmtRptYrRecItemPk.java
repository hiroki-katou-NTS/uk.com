package nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author chinh.hm
 */
@Embeddable
@AllArgsConstructor
public class KfnmtRptYrRecItemPk implements Serializable {
    private static final long serialVersionUID = 1L;
    // 	設定ID
    @Column(name = "ID")
    public int iD;

    //  出力順位->出力項目	.順位
    @Column(name = "ITEM_POS")
    public int itemPos;
}
