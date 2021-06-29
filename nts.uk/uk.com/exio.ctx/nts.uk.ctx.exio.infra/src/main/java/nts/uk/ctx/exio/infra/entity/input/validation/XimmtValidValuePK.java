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
public class XimmtValidValuePK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	private String companyId;
	
	@Column(name = "SETTING_CODE")
	private String settingCode;
	
	@Column(name = "ITEM_NO")
	private int itemNo;
}
