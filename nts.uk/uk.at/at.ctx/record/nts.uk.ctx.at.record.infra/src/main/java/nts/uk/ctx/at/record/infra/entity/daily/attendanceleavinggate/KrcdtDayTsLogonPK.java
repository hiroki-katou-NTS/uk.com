package nts.uk.ctx.at.record.infra.entity.daily.attendanceleavinggate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The primary key class for the KRCDT_DAY_TS_LOGON database table.
 * 
 */
@Embeddable
public class KrcdtDayTsLogonPK implements Serializable{

	/***/
	private static final long serialVersionUID = 1L;

	@Column(name = "SID")
	public String sid;

	@Column(name = "YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate ymd;

	@Column(name = "PC_LOG_NO")
	public int pcLogNo;

	public KrcdtDayTsLogonPK() {
	}

	public KrcdtDayTsLogonPK(String sid, GeneralDate ymd, int pcLogNo) {
		super();
		this.sid = sid;
		this.ymd = ymd;
		this.pcLogNo = pcLogNo;
	}

}