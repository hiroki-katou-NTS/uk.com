package nts.uk.ctx.exio.infra.entity.exo.cdconvert;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetail;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* コード変換詳細
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_CD_CONVERT_DETAIL")
public class OiomtCdConvertDetail extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtCdConvertDetailPk cdConvertDetailPk;
    
    
    /**
    * 出力項目
    */
    @Basic(optional = true)
    @Column(name = "OUTPUT_ITEM")
    public String outputItem;
    
    /**
    * 本システムのコード
    */
    @Basic(optional = false)
    @Column(name = "SYSTEM_CD")
    public String systemCd;
    
    @Override
    protected Object getKey()
    {
        return cdConvertDetailPk;
    }

    public CdConvertDetail toDomain() {
        return new CdConvertDetail(this.cdConvertDetailPk.cid, this.cdConvertDetailPk.convertCd, this.outputItem, this.systemCd, this.cdConvertDetailPk.lineNumber);
    }
    public static OiomtCdConvertDetail toEntity(CdConvertDetail domain) {
        return new OiomtCdConvertDetail(
        		new OiomtCdConvertDetailPk(domain.getConvertCd().v(), domain.getLineNumber() ,domain.getCid()),
        		domain.getOutputItem().isPresent() ? domain.getOutputItem().get() : null,
        		domain.getSystemCd());
    }


}
