package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 特別休暇付与日数テーブル
 * @author masaaki_jinno
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_GRANT_TBL")
public class KshmtHdspGrantTbl extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KshstGrantDateTblPK pk;
	
	/* 名称 */
	@Column(name = "GRANT_NAME")
	public String grantName;
	
	/* 規定のテーブルとする */
	@Column(name = "IS_SPECIFIED")
	public int isSpecified;
	
	/* テーブル以降の毎年付与日数 */
	@Column(name = "NUMBER_OF_DAYS")
	public Integer numberOfDays;
	
	@Override
	protected Object getKey() {
		return pk;
	}

	/**
	 * コンストラクタ
	 * @param pk
	 * @param grantName
	 * @param isSpecified
	 * @param numberOfDays
	 */
	public KshmtHdspGrantTbl(
			KshstGrantDateTblPK pk, String grantName, int isSpecified, Integer numberOfDays) {
		this.pk = pk;
		this.grantName = grantName;
		this.isSpecified = isSpecified;
		this.numberOfDays = numberOfDays;
	}
}
