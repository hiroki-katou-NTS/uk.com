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
public class PpemtItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "PER_INFO_ITEM_DEFINITION_ID")
    public String perInfoItemDefId;
    

}
