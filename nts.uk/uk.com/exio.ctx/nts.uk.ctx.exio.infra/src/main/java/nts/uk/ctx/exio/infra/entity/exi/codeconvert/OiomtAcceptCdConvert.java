package nts.uk.ctx.exio.infra.entity.exi.codeconvert;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 受入コード変換
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_ACCEPT_CD_CONVERT")
public class OiomtAcceptCdConvert extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name = "EXCLUS_VER")
    public Long version;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtAcceptCdConvertPk acceptCdConvertPk;
    
    /**
    * コード変換名称
    */
    @Basic(optional = false)
    @Column(name = "CONVERT_NAME")
    public String convertName;
    
    /**
    * 設定のないコードの受入
    */
    @Basic(optional = false)
    @Column(name = "ACCEPT_WITHOUT_SETTING")
    public int acceptWithoutSetting;
    
    @Override
    protected Object getKey()
    {
        return acceptCdConvertPk;
    }
}
