package nts.uk.ctx.at.record.infra.entity.daily.attendanceleavinggate;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.LogOnInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnNo;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The persistent class for the KRCDT_DAY_TS_LOGON database table.
 * 
 */
@Entity
@Table(name = "KRCDT_DAY_TS_LOGON")
// @NamedQuery(name="KrcdtDayTsLogon.findAll", query="SELECT k FROM
// KrcdtDayTsLogon k")
public class KrcdtDayTsLogon extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDayTsLogonPK id;

	@Column(name = "LOGOFF_TIME")
	public Integer logoffTime;

	@Column(name = "LOGON_TIME")
	public Integer logonTime;

	public KrcdtDayTsLogon() {
	}

	public KrcdtDayTsLogon(KrcdtDayTsLogonPK id) {
		super();
		this.id = id;
	}

	@Override
	protected Object getKey() {
		return this.id;
	}

	public static List<KrcdtDayTsLogon> from(PCLogOnInfoOfDaily domain) {
		return domain.getTimeZone().getLogOnInfo().stream().map(c -> 
	 							from(domain.getEmployeeId(), domain.getYmd(), c)
		).collect(Collectors.toList());
	}
	
	public static KrcdtDayTsLogon from(String eId, GeneralDate ymd, LogOnInfo domain) {
		KrcdtDayTsLogon entity = new KrcdtDayTsLogon(new KrcdtDayTsLogonPK(eId, ymd, domain.getWorkNo().v()));
		entity.setData(domain);
		return entity;
	}
	
	public LogOnInfo toDomain() {
		return new LogOnInfo(new PCLogOnNo(id.pcLogNo), toTimeWithDay(logoffTime), toTimeWithDay(logonTime));
	}
	
	public void setData(LogOnInfo c) {
		this.logonTime = c.getLogOn().isPresent() ? c.getLogOn().get().valueAsMinutes() : null;
		this.logoffTime = c.getLogOff().isPresent() ? c.getLogOff().get().valueAsMinutes() : null;
//		c.getLogOn().ifPresent(lo -> {
//			this.logonTime = lo.valueAsMinutes();
//		});
//		c.getLogOff().ifPresent(lo -> {
//			this.logoffTime = lo.valueAsMinutes();
//		});
	}

	private TimeWithDayAttr toTimeWithDay(Integer time) {
		return time == null ? null : new TimeWithDayAttr(time);
	}
}
