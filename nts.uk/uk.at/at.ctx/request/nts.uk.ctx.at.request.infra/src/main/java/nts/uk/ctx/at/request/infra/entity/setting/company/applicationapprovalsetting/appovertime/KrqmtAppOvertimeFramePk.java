package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class KrqmtAppOvertimeFramePk implements Serializable {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "OVERTIME_ATR")
    public int overtimeAtr;

    @Column(name = "FLEX_ATR")
    public int flexAtr;

    @Column(name = "OT_FR_NO")
    public int otFrameNo;
}
