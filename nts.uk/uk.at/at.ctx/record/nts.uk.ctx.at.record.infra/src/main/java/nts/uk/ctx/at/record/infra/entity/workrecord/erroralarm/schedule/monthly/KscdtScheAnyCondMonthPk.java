package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KscdtScheAnyCondMonthPk implements Serializable {
    /* 会社ID */
    @Column(name = "CID")
    public String cid;

    /* ID */
    @Column(name = "ERAL_CHECK_ID")
    public String checkId;

    /* 並び順 */
    @Column(name = "SORT_BY")
    public int sortBy;

}
