package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscstScheQualifySetPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 資格コード */
	@Column(name = "CID")
	public String companyId;
	
	/** 資格コード */
	@Column(name = "QUALIFY_CD")
	public String qualifyCode;
}
