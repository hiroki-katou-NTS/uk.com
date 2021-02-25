package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 休暇申請申請理由表示 PK
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqmtAppHDReasonPk implements Serializable {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "HOLIDAY_APP_TYPE")
    public int holidayApplicationType;
}
