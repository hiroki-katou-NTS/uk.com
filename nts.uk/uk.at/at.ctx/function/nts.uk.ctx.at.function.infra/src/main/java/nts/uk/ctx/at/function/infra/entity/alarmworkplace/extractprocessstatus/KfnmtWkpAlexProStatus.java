package nts.uk.ctx.at.function.infra.entity.alarmworkplace.extractprocessstatus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.ExtractState;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * entity : アラームリスト抽出処理状況(職場別)
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNMT_WKP_ALEX_PRO_STATUS")
public class KfnmtWkpAlexProStatus extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtWkpAlexProStatusPk pk;

    @Column(name = "CID")
    public String companyID;

    @Column(name = "START_TIME")
    public int startTime;

    @Column(name = "START_DATE")
    public GeneralDate startDate;

    @Column(name = "STATUS")
    public int status;

    @Column(name = "SID")
    public String employeeID;

    @Column(name = "END_DATE")
    public GeneralDate endDate;

    @Column(name = "END_TIME")
    public Integer endTime;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public static AlarmListExtractProcessStatusWorkplace toDomain(KfnmtWkpAlexProStatus entity) {
        return new AlarmListExtractProcessStatusWorkplace(
            entity.pk.extraStatusId,
            entity.companyID,
            entity.startDate,
            entity.startTime,
            entity.employeeID,
            entity.endDate,
            entity.endTime,
            EnumAdaptor.valueOf(entity.status, ExtractState.class)
        );
    }

    public static KfnmtWkpAlexProStatus toEntity(AlarmListExtractProcessStatusWorkplace domain ) {
        KfnmtWkpAlexProStatus entity = new KfnmtWkpAlexProStatus(
            new KfnmtWkpAlexProStatusPk(domain.getId()),
            domain.getCompanyID(),
            domain.getStartTime(),
            domain.getStartDate(),
            domain.getStatus().value,
            domain.getEmployeeID().isPresent() ? domain.getEmployeeID().get() : null,
            domain.getEndDate().isPresent() ? domain.getEndDate().get() : null,
            domain.getEndTime().isPresent() ? domain.getEndTime().get() : null
        );
        entity.setContractCd(AppContexts.user().contractCode());
        return entity;
    }


}
