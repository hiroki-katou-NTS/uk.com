package nts.uk.ctx.office.infra.entity.favorite;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/*
 * UKDesign.データベース.ER図.オフィス支援.在席照会.お気に入り.OFIMT_FAVORITE_DETAIL
 * 在席照会のお気に入り設定詳細
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "OFIMT_FAVORITE_DETAIL")
public class OfimtFavoriteDetail extends ContractUkJpaEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column　会社ID
	@Column(name = "CID")
	private String cid;

	// Embedded primary key 作成者ID and 入力日 and 対象情報ID
	@EmbeddedId
	private OfimtFavoriteDetailPK pk;

	@ManyToOne
	@PrimaryKeyJoinColumns({ 
		@PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
		@PrimaryKeyJoinColumn(name = "INPUT_DATE", referencedColumnName = "INPUT_DATE")
		})
	public OfimtFavorite favoriteSpecifyEntity;

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public void toEntity(String creatorId, GeneralDateTime inputDate, String targetSelection) {
		this.pk = new OfimtFavoriteDetailPK();
		this.pk.setCreatorId(creatorId);
		this.pk.setInputDate(inputDate);
		this.pk.setTargetSelection(targetSelection);
		this.setCid(AppContexts.user().companyId());
	}
}
