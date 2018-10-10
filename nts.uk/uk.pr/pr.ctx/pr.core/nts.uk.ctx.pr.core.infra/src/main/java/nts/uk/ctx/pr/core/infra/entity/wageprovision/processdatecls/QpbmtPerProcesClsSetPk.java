package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 個人処理区分設定: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtPerProcesClsSetPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 処理区分NO
    */
    @Basic(optional = false)
    @Column(name = "PROCESS_CATE_NO")
    public String processCateNo;
    
}
