package nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KscmtFuncCtrUseWktpPk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 	会社ID
     */
    @Column(name = "CID")
    public String cid;

    /**
     * 	使用可能な勤務種類コード : 	スケジュール修正の機能制御.表示可能勤務種類リスト
     */
    @Column(name = "WKTP_CD")
    public String wktpCd;
}
