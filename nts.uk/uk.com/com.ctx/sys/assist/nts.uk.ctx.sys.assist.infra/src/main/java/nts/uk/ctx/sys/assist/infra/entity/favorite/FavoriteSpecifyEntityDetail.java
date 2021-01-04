package nts.uk.ctx.sys.assist.infra.entity.favorite;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/*
 * UKDesign.データベース.ER図.オフィス支援.在席照会.お気に入り.OFIMT_FAVORITE_DETAIL
 * 在席照会のお気に入り設定詳細
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OFIMT_FAVORITE_DETAIL")
public class FavoriteSpecifyEntityDetail extends UkJpaEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column 排他バージョン
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;

	// column 契約コード
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	// Embedded primary key 作成者ID and 入力日 and 対象情報ID
	@EmbeddedId
	private FavoriteSpecifyEntityDetailPK pk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ 
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "INPUT_DATE", referencedColumnName = "INPUT_DATE", insertable = false, updatable = false)
		})
	public FavoriteSpecifyEntity favoriteSpecifyEntity;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}
