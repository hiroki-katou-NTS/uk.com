package nts.uk.ctx.exio.infra.entity.input.canonicalize;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(OiottImportingItemPK.class)
@Table(name = "OIOTT_IMPORTING_ITEM")
public class OiottImportingItem implements Serializable{
	
	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String cid;
	
	/*import対象列名*/
	@Id
	@Column(name = "NAME")
	public String name;
}
