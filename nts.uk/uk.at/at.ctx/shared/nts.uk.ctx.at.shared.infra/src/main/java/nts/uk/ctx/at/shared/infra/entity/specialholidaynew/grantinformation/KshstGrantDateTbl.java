package nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantinformation;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 特別休暇付与テーブル
 * 
 * @author tanlv
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_DATE_TBL")
public class KshstGrantDateTbl extends UkJpaEntity implements Serializable {
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
	
	/* テーブル以降の固定付与をおこなう */
	@Column(name = "FIXED_ASSIGN")
	public int fixedAssign;
	
	/* テーブル以降の毎年付与日数 */
	@Column(name = "NUMBER_OF_DAYS")
	public int numberOfDays;
	
	@OneToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
	})
	public KshstGrantRegularNew grantRegular;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="grantDateTbl", orphanRemoval = true)
	public List<KshstElapseYears> elapseYears;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}
}
