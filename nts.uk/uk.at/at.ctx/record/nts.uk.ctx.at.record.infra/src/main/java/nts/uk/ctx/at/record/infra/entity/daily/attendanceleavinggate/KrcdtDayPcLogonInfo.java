package nts.uk.ctx.at.record.infra.entity.daily.attendanceleavinggate;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.LogOnInfo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnNo;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The persistent class for the KRCDT_DAY_PC_LOGON_INFO database table.
 * 
 */
@Entity
@Table(name = "KRCDT_DAY_PC_LOGON_INFO")
// @NamedQuery(name="KrcdtDayPcLogonInfo.findAll", query="SELECT k FROM
// KrcdtDayPcLogonInfo k")
public class KrcdtDayPcLogonInfo extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDayPcLogonInfoPK id;

	@Column(name = "LOGOFF_TIME1")
	public Integer logoffTime1;

	@Column(name = "LOGOFF_TIME2")
	public Integer logoffTime2;

	@Column(name = "LOGON_TIME1")
	public Integer logonTime1;

	@Column(name = "LOGON_TIME2")
	public Integer logonTime2;

	public KrcdtDayPcLogonInfo() {
	}

	public KrcdtDayPcLogonInfo(KrcdtDayPcLogonInfoPK id) {
		super();
		this.id = id;
	}

	@Override
	protected Object getKey() {
		return this.id;
	}

	public static KrcdtDayPcLogonInfo from(PCLogOnInfoOfDaily domain) {
		KrcdtDayPcLogonInfo entity = new KrcdtDayPcLogonInfo(
				new KrcdtDayPcLogonInfoPK(domain.getEmployeeId(), domain.getYmd()));
		entity.mergeData(domain);
		return entity;
	}

	public void mergeData(PCLogOnInfoOfDaily domain) {
		getPCLogNo(1, domain).ifPresent(pcl -> {
			pcl.getLogOn().ifPresent(lo -> {
				this.logonTime1 = lo.valueAsMinutes();
			});
			pcl.getLogOff().ifPresent(lo -> {
				this.logoffTime1 = lo.valueAsMinutes();
			});

		});
		getPCLogNo(2, domain).ifPresent(pcl -> {
			pcl.getLogOn().ifPresent(lo -> {
				this.logonTime2 = lo.valueAsMinutes();
			});
			pcl.getLogOff().ifPresent(lo -> {
				this.logoffTime2 = lo.valueAsMinutes();
			});
		});
	}

	private static Optional<LogOnInfo> getPCLogNo(int no, PCLogOnInfoOfDaily domain) {
		return domain.getLogOnInfo().stream().filter(c -> c.getWorkNo().v() == no).findFirst();
	}

	public PCLogOnInfoOfDaily toDomain() {
		return new PCLogOnInfoOfDaily(this.id.sid, this.id.ymd,
				Stream.of(getNo1(), getNo2()).collect(Collectors.toList()));
	}

	private LogOnInfo getNo1() {
		return new LogOnInfo(new PCLogOnNo(1), toTimeWithDay(logonTime1), toTimeWithDay(logoffTime1));
	}

	private LogOnInfo getNo2() {
		return new LogOnInfo(new PCLogOnNo(2), toTimeWithDay(logonTime2), toTimeWithDay(logoffTime2));
	}

	private TimeWithDayAttr toTimeWithDay(Integer time) {
		return time == null ? null : new TimeWithDayAttr(time);
	}
}