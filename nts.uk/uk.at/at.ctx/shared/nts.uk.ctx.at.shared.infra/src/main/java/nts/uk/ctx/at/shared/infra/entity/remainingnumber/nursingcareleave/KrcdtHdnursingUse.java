package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * エンティティ：子の看護介護休暇使用数データ
 *
 * @author yuri_tamakoshi
 */
@Entity
@Table(name = "KRCDT_HDNURSING_USE")
@NoArgsConstructor
public class KrcdtHdnursingUse extends ContractUkJpaEntity implements Serializable {

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
		ChildCareNurseUsedNumber childCareNurseUsedNumber = ChildCareNurseUsedNumber.of(
				new DayNumberOfUse(usedDays),
				Optional.ofNullable(usedMinutes == null ? null : new TimeOfUse(usedMinutes)));

		if (this.pk.nursingType.equals(NursingCategory.ChildNursing.value)) {
			return new ChildCareUsedNumberData(this.pk.employeeId, childCareNurseUsedNumber);
		} else if (this.pk.nursingType.equals(NursingCategory.Nursing.value)) {
			return new CareUsedNumberData(this.pk.employeeId, childCareNurseUsedNumber);
		}

		return new ChildCareNurseUsedNumber();
	}

	/**
	 * ドメインから変換
	 * @param domain 子の看護介護休暇使用数
	 */
	public void fromDomain(String cid, ChildCareNurseUsedNumber domain) {
		this.companyID = cid;
		this.usedDays = domain.getUsedDay().v();
		this.usedMinutes = domain.getUsedTimes().map(mapper->mapper.v()).orElse(0);
	}

}
