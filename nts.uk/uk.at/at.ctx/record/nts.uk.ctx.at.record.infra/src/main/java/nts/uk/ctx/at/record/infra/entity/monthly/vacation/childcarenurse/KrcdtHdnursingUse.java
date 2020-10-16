package nts.uk.ctx.at.record.infra.entity.monthly.vacation.childcarenurse;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * エンティティ：子の看護介護休暇使用数データ
 *
 * @author yuri_tamakoshi
 */
@Entity
@Table(name = "KRCDT_HDNURSING_USE")
@NoArgsConstructor
public class KrcdtHdnursingUse extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;


	/** プライマリキー */
	@EmbeddedId
	public KrcdtHdnursingUsePK pk;


	/** 排他バージョン */
	@Version
	@Column(name = "EXCLUS_VER")
	public Long version;

	/** 会社ID */
	@Column(name = "CID")
	public String companyID;

	/** 使用日数 */
	@Column(name = "USED_DAYS")
	public Double usedDays;

	/** 使用時間 */
	@Column(name = "USED_MINUTES")
	public Integer usedMinutes;

	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return pk;
	}

	/**
	 * ドメインに変換
	 * @return 子の看護介護休暇使用数
	 */
	public ChildCareNurseUsedNumber toDomain() {
		return ChildCareNurseUsedNumber.of(new DayNumberOfUse(usedDays),
																	Optional.ofNullable(usedMinutes == null ? null : new TimeOfUse(usedMinutes)));
	}

	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 子の看護介護休暇使用数
	 */
	public void fromDomainForPersist(String employeeId, ChildCareNurseUsedNumber domain) {

		this.pk = new KrcdtHdnursingUsePK();
		this.fromDomainForUpdate(employeeId, domain);
	}

	/**
	 * ドメインから変換　(for Update)
	 * @param domain 子の看護介護休暇使用数
	 */
	public void fromDomainForUpdate(String employeeId, ChildCareNurseUsedNumber domain){

		this.usedDays = domain.getUsedDay().v();
		this.usedMinutes = domain.getUsedTimes().map(c -> c.v()).orElse(null);
	}
}
