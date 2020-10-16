package nts.uk.ctx.sys.auth.infra.entity.avatar;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.sys.auth.dom.avatar.UserAvatar;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity 個人の顔写真
 */
@Data
@Entity
@Table(name = "BPSDT_PS_AVATAR")
@EqualsAndHashCode(callSuper = true)
public class BpsdtPsAvatar extends UkJpaEntity implements UserAvatar.MementoGetter, UserAvatar.MementoSetter, Serializable {

    private static final long serialVersionUID = 1L;
    // Embedded primary key 個人ID
    @EmbeddedId
    public BpsdtPsAvatarPK bpsdtPsAvatarPK;
    // column 排他バージョン
    @Version
    @Column(name = "EXCLUS_VER")
    private long version;
    // column 契約コード
    @Basic(optional = false)
    @Column(name = "CONTRACT_CD")
    private String contractCd;
    // column ファイルID
    @Basic(optional = false)
    @Column(name = "FILE_ID")
    private String fileId;

    @Override
    public String getPersonalId() {
        if (this.bpsdtPsAvatarPK != null) {
            return this.bpsdtPsAvatarPK.getPersonalId();
        }
        return null;
    }

    @Override
    public void setPersonalId(String personalId) {
        if (this.bpsdtPsAvatarPK == null) {
            this.bpsdtPsAvatarPK = new BpsdtPsAvatarPK();
        }
        this.bpsdtPsAvatarPK.setPersonalId(personalId);
    }

    @Override
    protected Object getKey() {
        return this.bpsdtPsAvatarPK;
    }
}
