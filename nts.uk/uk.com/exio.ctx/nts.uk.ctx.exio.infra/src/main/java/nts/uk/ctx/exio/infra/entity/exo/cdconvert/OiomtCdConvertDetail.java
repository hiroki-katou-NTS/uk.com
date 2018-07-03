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
    * コード変換コード
    */
    @Basic(optional = false)
    @Column(name = "CONVERT_CD")
    public String convertCd;
    
    /**
    * 出力項目
    */
    @Basic(optional = false)
    @Column(name = "OUTPUT_ITEM")
    public String outputItem;
    
    /**
    * 本システムのコード
    */
    @Basic(optional = false)
    @Column(name = "SYSTEM_CD")
    public String systemCd;
    
    /**
    * 行番号
    */
    @Basic(optional = false)
    @Column(name = "LINE_NUMBER")
    public String lineNumber;
    
    @Override
    protected Object getKey()
    {
        return cdConvertDetailPk;
    }

    public CdConvertDetail toDomain() {
        return new CdConvertDetail(this.convertCd, this.outputItem, this.systemCd, this.lineNumber);
    }
    public static OiomtCdConvertDetail toEntity(CdConvertDetail domain) {
        return new OiomtCdConvertDetail(
        		new OiomtCdConvertDetailPk(domain.getConvertCd(),domain.getOutputItem(),domain.getSystemCd()),
        		domain.getLineNumber());
    }

	public OiomtCdConvertDetail(OiomtCdConvertDetailPk cdConvertDetailPk, String lineNumber) {
		super();
		this.cdConvertDetailPk = cdConvertDetailPk;
		this.lineNumber = lineNumber;
	}

}
