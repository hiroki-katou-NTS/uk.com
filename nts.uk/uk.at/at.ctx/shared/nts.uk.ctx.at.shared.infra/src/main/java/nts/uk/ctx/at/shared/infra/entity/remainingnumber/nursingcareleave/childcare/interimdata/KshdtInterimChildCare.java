package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.childcare.interimdata;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 暫定子の看護管理データ
 * @author yuri_tamakoshi
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "KSHDT_INTERIM_CHILD_CARE")
public class KshdtInterimChildCare  extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshdtInterimChildCarePK pk;

	/**残数管理データID 	 */
	@Column(name = "REMAIN_MNG_ID")
	public String remainMngID;

	/**	作成元区分 */
	@Column(name = "CREATOR_ATR")
	public int createAtr;

	/** 使用日数 */
	@Column(name = "USED_DAYS")
	public Double usedDays;

	/** 使用時間 */
	@Column(name = "USED_TIME")
	public Integer usedTime;

	protected Object getKey() {
		return pk;
	}

	public TempChildCareManagement toDomain() {
		return TempChildCareManagement.of(remainMngID, pk.sid, pk.ymd,
					EnumAdaptor.valueOf(createAtr, CreateAtr.class),
					ChildCareNurseUsedNumber.of(
								new DayNumberOfUse(usedDays),
								Optional.ofNullable(usedTime == null ? null : new TimeOfUse(usedTime))),
					createHourlyTime());
	}

	private Optional<DigestionHourlyTimeType> createHourlyTime() {
		if (pk.timeDigestiveAtr == null) {
			return Optional.empty();
		}

		return Optional.of(DigestionHourlyTimeType.of(
												pk.timeDigestiveAtr == 1,
												Optional.ofNullable(EnumAdaptor.valueOf(pk.timeHdType, AppTimeType.class))));
	}


	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 暫定子の看護管理データ
	 */
	public void fromDomainForPersist(TempChildCareManagement domain) {
		this.fromDomainForUpdate(domain);
	}

	/**
	 * ドメインから変換　(for Update)
	 * @param domain 暫定子の看護管理データ
	 */
	public void fromDomainForUpdate(TempChildCareNurseManagement domain){

		this.remainMngID = domain.getRemainManaID();
		this.createAtr  = domain.getCreatorAtr().value;
		this.usedDays = domain.getUsedNumber().getUsedDay().v();
		this.usedTime = domain.getUsedNumber().getUsedTimes().map(c -> c.v()).orElse(null);
		this.pk.timeDigestiveAtr = domain.getAppTimeType().map(c -> c.isHourlyTimeType() ? 1 : 0).orElse(0);
		this.pk.timeHdType = domain.getAppTimeType().flatMap(c -> c.getAppTimeType()).map(c -> c.value).orElse(0);

	}
}