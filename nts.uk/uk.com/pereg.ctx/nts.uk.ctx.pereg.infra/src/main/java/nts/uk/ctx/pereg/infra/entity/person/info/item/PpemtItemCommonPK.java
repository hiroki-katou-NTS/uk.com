package nts.uk.ctx.pereg.infra.entity.person.info.item;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PpemtItemCommonPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @Column(name = "CONTRACT_CD")
    public String contractCd;
	
    @Basic(optional = false)
    @Column(name = "CATEGORY_CD")
    public String categoryCd;
    
    @Basic(optional = false)
    @Column(name = "ITEM_CD")
    public String itemCd;

}
