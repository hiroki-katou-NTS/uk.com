package nts.uk.ctx.sys.portal.infra.entity.logsettings;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SrcdtLogSettingPK {
	
	@NonNull
	@Column(name = "CID")
	public String cid;
	
	@NonNull
	@Column(name = "SYSTEM")
	public Integer system;
	
	@NonNull
	@Column(name = "PROGRAM_CODE")
	public String programCd;
	
	@NonNull
	@Column(name = "MENU_ATR")
	public Integer menuClassification;
}
