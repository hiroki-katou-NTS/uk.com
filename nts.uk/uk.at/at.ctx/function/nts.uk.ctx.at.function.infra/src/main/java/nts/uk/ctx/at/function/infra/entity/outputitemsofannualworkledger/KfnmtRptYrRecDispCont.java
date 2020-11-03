package nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.DailyOutputItemsAnnualWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
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
 * Entity: 	年間勤務台帳の表示内容
 */
@Entity
@Table(name = "KFNMT_RPT_YR_REC_DISP_CONT")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtRptYrRecDispCont extends UkJpaEntity implements Serializable {

    private static long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtRptYrRecDispContPk pk;
    //	契約コード
    @Column(name = "CONTRACT_CD")
    private String contractCode;

    //	会社ID
    @Column(name = "CID")
    public String companyId;

    //		演算子->出力項目詳細の選択勤怠項目.演算子
    @Column(name = "OPERATOR")
    public int operator;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static List<KfnmtRptYrRecDispCont> fromDomain(AnnualWorkLedgerOutputSetting outputSetting,
                                                           List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList,
                                                           List<OutputItem> outputItemList,
                                                           List<OutputItemDetailSelectionAttendanceItem> attendanceItemList){

        val rs = new ArrayList<KfnmtRptYrRecDispCont>();
        for (OutputItemDetailSelectionAttendanceItem i:attendanceItemList ) {
            rs.addAll(outputItemList.stream().map(e->new KfnmtRptYrRecDispCont(
                    new KfnmtRptYrRecDispContPk(Integer.parseInt(outputSetting.getID()),e.getRank(),i.getAttendanceItemId()),
                    AppContexts.user().contractCode(),
                    AppContexts.user().companyId(),
                    i.getOperator().value
            ) ).collect(Collectors.toList()));
        }
        return rs;


    }
}
