package nts.uk.ctx.at.function.infra.entity.alarmworkplace.extractprocessstatus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtWkpAlexProStatusPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "EXTRA_PRO_STATUS_ID")
    public String extraStatusId;

}
