package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name = "KSCDT_SCH_SHORTTIME_TS")
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

	@Override
	protected Object getKey() {
		return this.pk;
	}
}
