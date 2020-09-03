package nts.uk.ctx.sys.portal.infra.entity.logsettings;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
@Setter
public class SrcdtLogSettingPK {
	@Column(name = "CID")
	public String cid;
	
	@Column(name = "SYSTEM")
	public int system;
	
	@Column(name = "MENU_ATR")
	public int menuClassification;
	
	@Column(name = "PROGRAM_ID")
	public String programId;
}
