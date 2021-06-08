package nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class KfndtPersisAlarmExtPk implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 会社ID */
    @Column(name = "CID")
    public String cid;

    /** 自動実行コード*/
    @Column(name = "PROCESS_ID")
    public String processId;
}
