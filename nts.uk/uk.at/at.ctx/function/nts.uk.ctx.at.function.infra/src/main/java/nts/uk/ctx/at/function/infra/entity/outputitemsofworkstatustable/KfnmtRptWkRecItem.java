package nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "KFNMT_RPT_WK_REC_ITEM")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtRptWkRecItem extends UkJpaEntity implements Serializable {

    @EmbeddedId
    public KfnmtRptWkRecItemPk pk;

    //	契約コード
    @Column(name = "CONTRACT_CD")
    public String contractCode;

    //	会社ID
    @Column(name = "CID")
    public String companyId;

    //	出力名称->出力項目.名称
    @Column(name = "ITEM_NAME")
    public String itemName;

    //	出力印刷対象フラグ	->出力項目.印刷対象フラグ
    @Column(name = "ITEM_IS_PRINTED")
    public boolean itemIsPrintEd;

    //	出力項目詳細の単独計算区分->出力項目.単独計算区分
    @Column(name = "ITEM_TYPE")
    public int itemType;

    //		出力項目詳細の属性->出力項目.属性
    @Column(name = "ITEM_ATTRIBUTE")
    public int itemAtribute;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static List<KfnmtRptWkRecItem> fromDomain(String cid, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList){
       return outputItemList.stream().map(e->new KfnmtRptWkRecItem(
               new KfnmtRptWkRecItemPk(outputSettings.getSettingId(),e.getRank()),
               AppContexts.user().contractCode(),
               cid,
               e.getName().v(),
               e.isPrintTargetFlag(),
               e.getIndependentCalcClassic().value,
               e.getItemDetailAttributes().value
       )).collect(Collectors.toList());
    }
}
