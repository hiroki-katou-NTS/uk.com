package nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity: 	年間勤務台帳の項目
 *
 * @author :chinh.hm
 */
@Entity
@Table(name = "KFNMT_RPT_YR_REC_ITEM")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtRptYrRecItem extends UkJpaEntity implements Serializable {
    @EmbeddedId
    public KfnmtRptYrRecItemPk pk;
    //	契約コード
    @Column(name = "CONTRACT_CD")
    public String contractCode;

    //	会社ID
    @Column(name = "CID")
    public String companyId;

    //	出力名称->出力項目.名称
    @Column(name = "ITEM_NAME")
    public String itemName;

    //	印刷対象フラグ		->出力項目.印刷対象フラグ
    @Column(name = "ITEM_IS_PRINTED")
    public boolean itemIsPrintEd;

    //	単独計算区分->出力項目.単独計算区分
    @Column(name = "ITEM_CALCULATOR_TYPE")
    public int itemCalculatorType;

    //	日次月次区分->出力項目.日次月次区分
    @Column(name = "ITEM_ATTENDANCE_TYPE")
    public int itemAttendanceType;

    //	属性->出力項目.属性
    @Column(name = "ITEM_ATTRIBUTE")
    public int itemAttribute;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static List<KfnmtRptYrRecItem> fromDomain(AnnualWorkLedgerOutputSetting outputSetting) {
        val rs = new ArrayList<KfnmtRptYrRecItem>();
        rs.addAll(outputSetting.getOutputItemList().stream().map(e -> new KfnmtRptYrRecItem(
                new KfnmtRptYrRecItemPk((outputSetting.getID()), e.getRank()),
                AppContexts.user().contractCode(),
                AppContexts.user().companyId(),
                e.getName().v(),
                e.isPrintTargetFlag(),
                e.getIndependentCalcClassic().value,
                DailyMonthlyClassification.MONTHLY.value,
                e.getItemDetailAttributes().value
        )).collect(Collectors.toList()));
        rs.addAll(outputSetting.getDailyOutputItemList().stream().map(e -> new KfnmtRptYrRecItem(
                new KfnmtRptYrRecItemPk((outputSetting.getID()), e.getRank()),
                AppContexts.user().contractCode(),
                AppContexts.user().companyId(),
                e.getName().v(),
                e.isPrintTargetFlag(),
                e.getIndependentCalcClassic().value,
                DailyMonthlyClassification.DAILY.value,
                e.getAttribute().value
        )).collect(Collectors.toList()));

        return rs;
    }
}
