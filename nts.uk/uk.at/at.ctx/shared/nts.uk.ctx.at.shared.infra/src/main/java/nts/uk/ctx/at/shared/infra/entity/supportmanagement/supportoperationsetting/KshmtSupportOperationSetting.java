package nts.uk.ctx.at.shared.infra.entity.supportmanagement.supportoperationsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "KSHMT_SUPPORT_OPERATION_SETTING")
public class KshmtSupportOperationSetting extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CID")
    private String cid;

    //利用するか
    @Column(name = "IS_AVAILABLE")
    private boolean isAvailable;

    //応援先が応援者を指定できるか
    @Column(name = "CAN_RECIPIENT_CHOOSE_SUPPORTER")
    private boolean canRecipientChooseSupporter;

    //一日の最大応援回数
    @Column(name = "MAX_TIMES_PER_DAY_OF_SUPPORT")
    private Integer maxTimesPerDayOfSupport;

    @Override
    protected Object getKey() {
        return this.cid;
    }


}
