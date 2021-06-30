package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.care.interimdata;

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
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 暫定介護管理データ
 * @author yuri_tamakoshi
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "KSHDT_INTERIM_CARE_DATA")
public class KshdtInterimCareData  extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshdtInterimCareDataPK pk;

	/**残数管理データID	 */
	@Column(name = "REMAIN_MNG_ID")
	public String remainMngID;

	/**	作成元区分 */
	@Column(name = "CREATOR_ATR")
	public int createAtr;

	/** 使用日数 */
	@Column(name = "USED_DAYS")
	public double usedDays;

	/** 使用時間 */
	@Column(name = "USED_TIME")
	public Integer usedTime;

	protected Object getKey() {
		return remainMngID;
	}

	public TempCareManagement toDomain() {
		return TempCareManagement.of(remainMngID, pk.sid, pk.ymd,
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
												pk.timeDigestiveAtr == 1 ? true : false,
												Optional.ofNullable(pk.timeHdType == null || pk.timeHdType==0 ? null : EnumAdaptor.valueOf(pk.timeHdType - 1, AppTimeType.class))));
	}

	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 暫定介護管理データ
	 */
	public void fromDomainForPersist(TempCareManagement domain) {
		this.fromDomainForUpdate(domain);
	}

	/**
	 * ドメインから変換　(for Update)
	 * @param domain 暫定介護管理データ
	 */
	public void fromDomainForUpdate(TempCareManagement domain){

		this.createAtr  = domain.getCreatorAtr().value;
		this.remainMngID = domain.getRemainManaID();
		this.usedDays = domain.getUsedNumber().getUsedDay().v();
		this.usedTime = domain.getUsedNumber().getUsedTimes().map(c -> c.v()).orElse(null);

	}
}
