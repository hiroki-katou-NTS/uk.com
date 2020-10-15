package nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
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
@AllArgsConstructor
public class DisplayContentsOfWorkStatus extends UkJpaEntity implements Serializable {
    public static long serialVersionUID = 1L;
    @EmbeddedId
    public DisplayContentsOfWorkStatusPk pk;

    //	契約コード
    @Column(name = "CONTRACT_CD")
    private String contractCode;

    //	会社ID
    @Column(name = "CID")
    public String cid;

    //	演算子->出力項目詳細の選択勤怠項目.演算子
    @Column(name = "OPERATOR")
    public int operator;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static List<DisplayContentsOfWorkStatus>fromDomain(String cid, WorkStatusOutputSettings outputSettings,
                                                              List<OutputItem> outputItemList,
                                                              List<OutputItemDetailSelectionAttendanceItem> attendanceItemList){
        val rs = new ArrayList<DisplayContentsOfWorkStatus>();
        for (OutputItemDetailSelectionAttendanceItem i:attendanceItemList ) {
            rs.addAll(outputItemList.stream().map(e->new DisplayContentsOfWorkStatus(
                    new DisplayContentsOfWorkStatusPk(outputSettings.getSettingId(),e.getRank(),i.getAttendanceItemId()),
                    AppContexts.user().contractCode(),
                    cid,
                    i.getOperator().value
            ) ).collect(Collectors.toList()));
        }
        return rs;
    }
}
