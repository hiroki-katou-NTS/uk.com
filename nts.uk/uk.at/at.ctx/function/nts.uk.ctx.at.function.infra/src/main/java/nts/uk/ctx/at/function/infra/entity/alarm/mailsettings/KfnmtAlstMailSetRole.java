package nts.uk.ctx.at.function.infra.entity.alarm.mailsettings;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity: アラームメール送信ロール
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KFNMT_ALARM_MAIL_SETROLE")
public class KfnmtAlstMailSetRole extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 会社ID */
    @Id
    @Column(name = "CID")
    public String companyID;

    /** ロール設定 */
    @Column(name = "ROLE_SET")
    public int roleSetting;

    /** マスタチェック結果を就業担当へ送信 */
    @Column(name = "SEND_MSTCHK_RESULT_CHARGE_EMP")
    public int sendResult;

    @Override
    protected Object getKey() {
        return companyID;
    }
}
