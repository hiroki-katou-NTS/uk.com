package nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity: 	年間勤務台帳の表示内容
 */
@Entity
@Table(name = "KFNMT_RPT_YR_REC_DISP_CONT")
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
    public String cid;

    //		演算子->出力項目詳細の選択勤怠項目.演算子
    @Column(name = "OPERATOR")
    public int operator;

    @Override
    protected Object getKey() {
        return pk;
    }
}
