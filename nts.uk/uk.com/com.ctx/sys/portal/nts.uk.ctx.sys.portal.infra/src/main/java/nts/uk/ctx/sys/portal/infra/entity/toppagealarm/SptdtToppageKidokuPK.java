package nts.uk.ctx.sys.portal.infra.entity.toppagealarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@Embeddable
public class SptdtToppageKidokuPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@NonNull
	@Column(name = "CID")
	public String cId;

	@NonNull
	@Column(name = "ALARM_CLS")
	public String alarmCls;

	@NonNull
	@Column(name = "IDEN_KEY")
	public String idenKey;

	@NonNull
	@Column(name = "DISP_SID")
	public String dispSid;

	@NonNull
	@Column(name = "DISP_ATR")
	public String dispAtr;
	
}
