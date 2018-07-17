package nts.uk.ctx.exio.infra.entity.exo.cdconvert;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* コード変換詳細: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiomtCdConvertDetailPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * コード変換コード
    */
    @Basic(optional = false)
    @Column(name = "CONVERT_CD")
    public String convertCd;
    
    /**
    * 行番号
    */
    @Basic(optional = false)
    @Column(name = "LINE_NUMBER")
    public String lineNumber;
    
    
	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;
}
