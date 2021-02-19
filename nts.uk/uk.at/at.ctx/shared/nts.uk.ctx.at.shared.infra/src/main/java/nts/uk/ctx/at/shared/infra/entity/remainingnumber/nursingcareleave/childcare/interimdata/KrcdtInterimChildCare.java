package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.childcare.interimdata;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 暫定子の看護管理データ
 * @author yuri_tamakoshi
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "KRCDT_INTERIM_CHILD_CARE")
public class KrcdtInterimChildCare  extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**残数管理データID 	 */
	@Id
	@Column(name = "REMAIN_MNG_ID")
	public String remainMngID;

	/**社員ID	 */
	@Column(name = "SID")
	public String sID;

	/**	対象日 */
	@Column(name = "YMD")
	public GeneralDate ymd;

	/**	作成元区分 */
	@Column(name = "CREATOR_ATR")
	public int createAtr;

	/**	残数分類 */
	@Column(name = "REMAIN_ATR")
	public int remainAtr;

	/** 使用日数 */
	@Column(name = "USED_DAYS")
	public Double usedDays;

	/** 使用時間 */
	@Column(name = "USED_TIME")
	public Integer usedTime;

	/** 時間消化休暇かどうか */
	@Column(name = "TIME_DIGESTIVE_ATR")
	public Integer timeDigestiveAtr;

	/**時間休暇種類 */
	@Column(name = "TIME_HD_TYPE")
	public Integer timeHdType;

	protected Object getKey() {
		return remainMngID;
	}

	public TempChildCareManagement toDomain() {
		return TempChildCareManagement.of(remainMngID, sID, ymd,
					EnumAdaptor.valueOf(createAtr, CreateAtr.class),
					EnumAdaptor.valueOf(remainAtr, RemainAtr.class),
					ChildCareNurseUsedNumber.of(
								new DayNumberOfUse(usedDays),
								Optional.ofNullable(usedTime == null ? null : new TimeOfUse(usedTime))),
					createHourlyTime());
	}

	private Optional<DigestionHourlyTimeType> createHourlyTime() {
		if (timeDigestiveAtr == null) {
			return Optional.empty();
		}

		return Optional.of(DigestionHourlyTimeType.of(
												timeDigestiveAtr == 1 ? true : false,
												Optional.ofNullable(timeHdType == null ? null : EnumAdaptor.valueOf(timeHdType, AppTimeType.class))));
	}


	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 暫定子の看護管理データ
	 */
	public void fromDomainForPersist(TempChildCareManagement domain) {
		this.remainMngID = domain.getRemainManaID();
		this.fromDomainForUpdate(domain);
	}

	/**
	 * ドメインから変換　(for Update)
	 * @param domain 暫定子の看護管理データ
	 */
	public void fromDomainForUpdate(TempChildCareNurseManagement domain){

		this.sID = domain.getSID();
		this.ymd = domain.getYmd();
		this.createAtr  = domain.getCreatorAtr().value;
		this.remainAtr = domain.getRemainAtr().value;
		this.usedDays = domain.getUsedNumber().getUsedDay().v();
		this.usedTime = domain.getUsedNumber().getUsedTimes().map(c -> c.v()).orElse(null);
		this.timeDigestiveAtr = domain.getAppTimeType().map(c -> c.isHourlyTimeType() ? 1 : 0).orElse(null);
		this.timeHdType = domain.getAppTimeType().flatMap(c -> c.getAppTimeType()).map(c -> c.value).orElse(null);

	}
}