package nts.uk.ctx.at.function.infra.entity.alarm.workplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "KFNMT_WKP_ALSTCHK_CONCAT")
public class KfnmtWkpAlstchkConcat extends UkJpaEntity implements Serializable {

    @Column(name="CONTRACT_CD")
    public String contractCd;

    @Id
    @Column(name="WP_ERROR_ALARM_CHKID")
    public String id;

    @Column(name="CID")
    public String cid;

    @Column(name="CATEGORY")
    public int category;

    @Column(name="ALARMCHK_CONDITN_CD")
    public String code;

    @Column(name="ROLL_ID")
    public String rollID;

    @Column(name="ALARMCHK_CONDTN_NAME")
    public String name;

    @Override
    protected Object getKey() {
        return this.id;
    }
}
