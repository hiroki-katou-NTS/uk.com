package nts.uk.ctx.exio.infra.entity.exo.useroutputcnd;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 出力条件詳細: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiomtUserOutCndDetailPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 条件設定コード
    */
    @Basic(optional = false)
    @Column(name = "CND_SET_CD")
    public String cndSetCd;
    
    /**
    * ユーザID
    */
    @Basic(optional = false)
    @Column(name = "USER_ID")
    public String userId;
}
