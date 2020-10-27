package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 特別休暇付与テーブル
 * 
 * @author tanlv
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_GRANT_TBL")
public class KshmtHdspGrantTbl extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshmtHdspGrantTblPK pk;
	
	/* 名称 */
	@Column(name = "GRANT_NAME")
	public String grantName;
	
	/* 規定のテーブルとする */
	@Column(name = "IS_SPECIFIED")
	public int isSpecified;
	
	/* テーブル以降の固定付与をおこなう */
	@Column(name = "FIXED_ASSIGN")
	public int fixedAssign;
	
	/* テーブル以降の毎年付与日数 */
	@Column(name = "NUMBER_OF_DAYS")
	public Integer numberOfDays;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}

	public KshmtHdspGrantTbl(KshmtHdspGrantTblPK pk, String grantName, int isSpecified, int fixedAssign, Integer numberOfDays) {
		this.pk = pk;
		this.grantName = grantName;
		this.isSpecified = isSpecified;
		this.fixedAssign = fixedAssign;
		this.numberOfDays = numberOfDays;
	}
}
