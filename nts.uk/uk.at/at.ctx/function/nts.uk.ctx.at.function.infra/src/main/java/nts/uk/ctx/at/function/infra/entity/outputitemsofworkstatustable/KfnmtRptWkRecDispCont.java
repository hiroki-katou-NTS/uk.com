package nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
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

@Entity
@Table(name = "KFNMT_RPT_WK_REC_DISP_CONT")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtRptWkRecDispCont extends UkJpaEntity implements Serializable {
    public static long serialVersionUID = 1L;
    @EmbeddedId
    public KfnmtRptWkRecDispContPk pk;

    //	契約コード
    @Column(name = "CONTRACT_CD")
    public String contractCode;

    //	会社ID
    @Column(name = "CID")
    public String companyId;

    //	演算子->出力項目詳細の選択勤怠項目.演算子
    @Column(name = "OPERATOR")
    public int operator;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static List<KfnmtRptWkRecDispCont>fromDomain(String cid, WorkStatusOutputSettings outputSettings,
                                                        List<OutputItem> outputItemList,
                                                        List<OutputItemDetailAttItem> attendanceItemList){
        val rs = new ArrayList<KfnmtRptWkRecDispCont>();
        for (val item: outputItemList ) {
            rs.addAll(item.getSelectedAttendanceItemList().stream().map(e->new KfnmtRptWkRecDispCont(
                    new KfnmtRptWkRecDispContPk(outputSettings.getSettingId(),item.getRank(),e.getAttendanceItemId()),
                    AppContexts.user().contractCode(),
                    cid,
                    e.getOperator().value
            ) ).collect(Collectors.toList()));
        }
        return rs;
    }
}
