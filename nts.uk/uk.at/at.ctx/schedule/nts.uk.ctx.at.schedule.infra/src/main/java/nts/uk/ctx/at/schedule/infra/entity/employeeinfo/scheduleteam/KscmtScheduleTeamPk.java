package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 
 * @author HieuLt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KscmtScheduleTeamPk implements Serializable {


	private static final long serialVersionUID = 1L;
	/** 会社ID **/
	@NotNull
	@Column(name = "CID")
	public String CID;
	
	/** 職場グループID **/
	@NotNull
	@Column(name = "WKPGRP_ID")
	public String WKPGRPID;
	
	/** コード **/
	@NotNull
	@Column(name = "CD")
	public String scheduleTeamCd;
	

}
