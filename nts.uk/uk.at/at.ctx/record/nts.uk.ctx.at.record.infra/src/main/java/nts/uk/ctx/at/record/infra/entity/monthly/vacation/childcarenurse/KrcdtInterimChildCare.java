package nts.uk.ctx.at.record.infra.entity.monthly.vacation.childcarenurse;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildcareNurseRemNumEachMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：暫定子の看護管理データ
 * @author yuri_tamakoshi
 */
@Entity
@Table(name = "KRCDT_INTERIM_CHILD_CARE")
@NoArgsConstructor
public class KrcdtInterimChildCare extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;


	/**  プライマリキー：残数管理データID */
	@Id
	@Column(name = "REMAIN_MNG_ID")
	public String remainMngId;


	/** 排他バージョン */
	@Version
	@Column(name = "EXCLUS_VER")
	public Long version;

	/** 契約コード */
	@Column(name = "CONTRACT_CD")
	public String ContractCd;

	/** 使用日数 */
	@Column(name = "USED_DAYS")
	public double usedDays;

	/** 使用時間 */
	@Column(name = "USED_TIME")
	public Integer usedTime;

	/** 時間消化休暇区分 */
	@Column(name = "TIME_DIGESTIVE_ATR")
	public boolean timeDigestiveAtr;

	/** 時間休暇種類　*/
	@Column(name = "TIME_HD_TYPE")
	public int timeHdType;

	@Override
	protected Object getKey() {
		// TODO 自動生成されたメソッド・スタブ
		return remainMngId;
	}
}
