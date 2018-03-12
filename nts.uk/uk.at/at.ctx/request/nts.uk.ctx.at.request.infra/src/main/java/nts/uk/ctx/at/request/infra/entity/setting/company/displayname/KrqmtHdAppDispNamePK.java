package nts.uk.ctx.at.request.infra.entity.setting.company.displayname;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqmtHdAppDispNamePK implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 会社ID **/
	@Column(name = "CID")
	public String companyId;
	/** 休暇申請種類 **/
	@Column(name = "HD_APP_TYPE")
	public int hdAppType;
}
