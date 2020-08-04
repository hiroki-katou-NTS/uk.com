package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name = "KSCDT_SCH_SHORTTIME_TS")
@Getter
public class KscdtSchShortTimeTs extends ContractUkJpaEntity {

	@EmbeddedId
	public KscdtSchShortTimeTsPK pk;

	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	/** 短時間勤務 開始時刻 **/
	@Column(name = "SHORTTIME_TS_START")
	public int shortTimeTsStart;

	@Column(name = "SHORTTIME_TS_END")
	public int shortTimeTsEnd;

	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchBasicInfo kscdtSchBasicInfo;
	
	public static KscdtSchShortTimeTs toEntity(ShortWorkingTimeSheet shortWorkingTimeSheets, String sID, GeneralDate yMD, String cID) {
		KscdtSchShortTimeTsPK pk = new KscdtSchShortTimeTsPK(sID, yMD, shortWorkingTimeSheets.getChildCareAttr().value, shortWorkingTimeSheets.getShortWorkTimeFrameNo().v());
		KscdtSchShortTimeTs kscdtSchShortTimeTs = new KscdtSchShortTimeTs(pk, cID, shortWorkingTimeSheets.getStartTime().v(), shortWorkingTimeSheets.getEndTime().v());
		return kscdtSchShortTimeTs;
	}

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KscdtSchShortTimeTs(KscdtSchShortTimeTsPK pk, String cid, int shortTimeTsStart, int shortTimeTsEnd) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.shortTimeTsStart = shortTimeTsStart;
		this.shortTimeTsEnd = shortTimeTsEnd;
	}
}
