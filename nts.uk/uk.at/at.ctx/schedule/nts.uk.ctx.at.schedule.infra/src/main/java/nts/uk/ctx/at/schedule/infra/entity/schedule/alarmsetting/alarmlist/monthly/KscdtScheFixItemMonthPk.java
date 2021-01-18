package nts.uk.ctx.at.schedule.infra.entity.schedule.alarmsetting.alarmlist.monthly;

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
public class KscdtScheFixItemMonthPk implements Serializable {
    /* 会社ID */
    @Column(name = "CID")
    public String cid;
    /* NO */
    @Column(name = "ITEM_NO")
    public int No;

}
