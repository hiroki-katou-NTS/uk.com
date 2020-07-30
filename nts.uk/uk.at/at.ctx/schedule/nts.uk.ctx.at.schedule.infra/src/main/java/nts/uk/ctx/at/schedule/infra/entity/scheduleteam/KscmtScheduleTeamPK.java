package nts.uk.ctx.at.schedule.infra.entity.scheduleteam;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KscmtScheduleTeamPK implements Serializable{
	private static final long serialVersionUID = 5215752859738061438L;
	
	/** 会社ID */
	@Column(name = "CID")
	private String companyId;
	
	/** 職場グループID */
	@Column(name = "WKPGRP_ID")
	private String workplaceGroupId;

	/** スケジュールチームコード **/
	@Column(name = "CD")
	private  String scheduleTeamCd;
}
