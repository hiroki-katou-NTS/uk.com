package nts.uk.ctx.exio.infra.entity.input.canonicalize;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OIOTT_IMPORTING_ITEM")
public class OiottImportingItem implements Serializable{
	
	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String cid;
	
	@ManyToOne
	public OiottCanonicalizedDataMeta canonicalizedDataMeta;

	@Column(name = "NAME")
	public String name;
}
