package nts.uk.ctx.sys.assist.infra.entity.storage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* データ保存の対象社員: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SspdtSaveTargetPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * データ保存処理ID
    */
    @Basic(optional = false)
    @Column(name = "STORE_PROCESSING_ID")
    public String storeProcessingId;
    
    /**
    * 社員ID
    */
    @Basic(optional = false)
    @Column(name = "SID")
    public String Sid;
    
}
