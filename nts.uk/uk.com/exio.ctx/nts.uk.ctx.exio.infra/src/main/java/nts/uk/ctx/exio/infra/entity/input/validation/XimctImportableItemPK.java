package nts.uk.ctx.exio.infra.entity.input.validation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class XimctImportableItemPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "DOMAIN_ID")
	private int domaindId;
	
	@Column(name = "ITEM_NO")
	private int itemNo;
}
