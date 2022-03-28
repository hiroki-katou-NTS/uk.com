package nts.uk.ctx.at.record.infra.entity.daily.shortwork;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtDayShorttimePK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SID")
	public String sid;
	
	@Column(name = "YMD")
	public GeneralDate ymd;
	
	@Column(name = "CHILD_CARE_ATR")
	public int childCareAtr;
	
}
