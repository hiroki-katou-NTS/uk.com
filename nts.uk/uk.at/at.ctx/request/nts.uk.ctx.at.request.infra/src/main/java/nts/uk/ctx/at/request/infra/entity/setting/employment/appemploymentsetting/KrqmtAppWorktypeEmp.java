package nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

//import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStamp;

/**
 * refactor 4 refactor4
 */
@Entity
@Table(name = "KRQMT_APP_WORKTYPE_EMP")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqmtAppWorktypeEmp extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KrqmtAppWorktypeEmpPK pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumns({
            @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
            @PrimaryKeyJoinColumn(name = "EMPLOYMENT_CODE", referencedColumnName = "EMPLOYMENT_CODE"),
            @PrimaryKeyJoinColumn(name = "APP_TYPE", referencedColumnName = "APP_TYPE"),
            @PrimaryKeyJoinColumn(name = "HOLIDAY_OR_PAUSE_TYPE", referencedColumnName = "HOLIDAY_OR_PAUSE_TYPE")
    })
    private KrqmtAppApvEmp krqstAppEmploymentSet;

    public KrqmtAppWorktypeEmp(String companyId, String employmentCode, int appType, int holidayOrPauseType, String workTypeCode) {
        this.pk = new KrqmtAppWorktypeEmpPK(companyId, employmentCode, appType, holidayOrPauseType, workTypeCode);
    }

    @Override
    protected Object getKey() {
        return pk;
    }

    public String getWorkTypeCode() {
        return this.pk.getWorkTypeCode();
    }
}
