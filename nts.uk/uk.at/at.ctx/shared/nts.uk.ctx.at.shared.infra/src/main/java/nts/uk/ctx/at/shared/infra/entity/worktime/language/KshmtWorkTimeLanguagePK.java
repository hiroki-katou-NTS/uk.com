package nts.uk.ctx.at.shared.infra.entity.worktime.language;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkTimeLanguagePK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String companyId;
	/* 勤務種類コード */
	@Column(name = "WORKTIME_CD")
	public String workTimeCode;
	/* 言語ID */
	@Column(name = "LANG_ID")
	public String langId;
}
