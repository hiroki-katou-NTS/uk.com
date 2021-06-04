package nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author viet.tx
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KscmtFuncCtrBywkpAlchkcdPk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Column(name = "CID")
    public String cid;

    /**
     * 	確定と同時に実行するアラームチェックコード : スケジュール修正職場別の機能制御 -> 完了方法制御.アラームチェックコードリスト
     */
    @Column(name = "ALCHK_CD")
    public String alchkCd;
}
