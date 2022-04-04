package nts.uk.ctx.at.record.infra.entity.supportmanagement.supportableemployee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KSHDT_SUPPORTABLE_EMPLOYEE")
public class KshdtSupportTableEmployee extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersion = 1L;
    /**
     * ID: GUI
     */
    @Column(name = "ID")
    @Id
    private String Id;

    @Column(name = "CID")
    public String companyId;
    /**
     * 社員ID
     */
    @Column(name = "SID")
    private String employeeId;
    /**
     * 応援先: 対象組織の単位
     * 対象組織の単位
     * 0: 職場
     * 1: 職場グループ
     */
    @Column(name = "RECIPIENT_TARGET_UNIT")
    private int recipientTargetUnit;

    /**
     * 応援先: 対象組織の単位に応じたID
     * 対象組織の単位に応じたID
     * RECIPIENT_TARGET_UNIT = 0 -> 職場ID
     * RECIPIENT_TARGET_UNIT = 1 -> 職場グループID
     */
    @Column(name = "RECIPIENT_TARGET_ID")
    private String recipientTargetId;
    /**
     * 応援形式
     * 応援形式
     * 0: 終日応援
     * 1: 時間帯応援
     */
    @Column(name = "SUPPORT_TYPE")
    private int supportType;

    /**
     * 期間: 開始日
     * 期間の開始日
     */
    @Column(name = "START_DATE")
    public GeneralDate startDate;

    /**
     * 期間: 終了日
     * 期間の終了日
     */
    @Column(name = "END_DATE")
    public GeneralDate endDate;
    /**
     * 時間帯: 開始時刻
     * 時間帯の開始時刻
     */
    @Column(name = "START_TS")
    public Integer startTs;

    /**
     * 時間帯: 終了時刻
     * 時間帯の終了時刻
     */
    @Column(name = "END_TS")
    public Integer endTs;

    @Override
    protected Object getKey() {
        return Id;
    }

    public static KshdtSupportTableEmployee toEntity(SupportableEmployee domain,String companyId) {
        return new KshdtSupportTableEmployee(
                domain.getId(),
                companyId,
                domain.getEmployeeId().v(),
                domain.getRecipient().getUnit().value,
                domain.getRecipient().getTargetId(),
                domain.getSupportType().getValue(),
                domain.getPeriod().start(),
                domain.getPeriod().end(),
                domain.getTimespan().isPresent() ? domain.getTimespan().get().start() : null,
                domain.getTimespan().isPresent() ? domain.getTimespan().get().end() : null
        );
    }


}
