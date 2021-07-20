package nts.uk.ctx.at.function.infra.entity.alarm.mailsettings;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRole;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.IndividualWkpClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity: アラームメール送信ロール
 */
@Entity
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

    @OneToMany(mappedBy = "mailSetRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(name = "KFNMT_ALST_SELECT_ROLE")
    public List<KfnmtAlstSelectRole> alarmSelectRoles;

    public KfnmtAlstMailSetRole(String companyID, int roleSetting, int sendResult) {
        this.companyID = companyID;
        this.roleSetting = roleSetting;
        this.sendResult = sendResult;
    }

    public static KfnmtAlstMailSetRole of(AlarmMailSendingRole domain) {
        return new KfnmtAlstMailSetRole(
                AppContexts.user().companyId(),
                BooleanUtils.toInteger(domain.isRoleSetting()),
                BooleanUtils.toInteger(domain.isSendResult())
        );
    }

    public AlarmMailSendingRole toDomain() {
        return new AlarmMailSendingRole(
                IndividualWkpClassification.of(1), //TODO
                this.alarmSelectRoles.stream().map(r -> r.getPk().roleId).collect(Collectors.toList()),
                BooleanUtils.toBoolean(this.sendResult == 1),
                BooleanUtils.toBoolean(this.roleSetting == 1)
        );
    }
}
