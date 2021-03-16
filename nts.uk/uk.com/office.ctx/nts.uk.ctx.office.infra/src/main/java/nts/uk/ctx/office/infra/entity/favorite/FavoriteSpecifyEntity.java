package nts.uk.ctx.office.infra.entity.favorite;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.TargetSelection;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/*
 * UKDesign.データベース.ER図.オフィス支援.在席照会.お気に入り.OFIMT_FAVORITE
 * 在席照会のお気に入り設定
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = { "listFavoriteSpecifyEntityDetail" })
@Table(name = "OFIMT_FAVORITE")
public class FavoriteSpecifyEntity extends UkJpaEntity
		implements FavoriteSpecify.MementoGetter, FavoriteSpecify.MementoSetter, Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column 排他バージョン
	@Column(name = "EXCLUS_VER")
	private long version;

	// column 契約コード
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	// Embedded primary key 作成者ID and 入力日
	@EmbeddedId
	private FavoriteSpecifyEntityPK pk;

	// column お気に入り名
	@NotNull
	@Column(name = "FAVORITE_NAME")
	private String favoriteName;

	// column 順序
	@NotNull
	@Column(name = "DISP_ORDER")
	private Integer order;

	// column 対象選択
	@NotNull
	@Column(name = "TGT_SELECT")
	private Integer targetSelection;

	@OneToMany(cascade = CascadeType.ALL,targetEntity = FavoriteSpecifyEntityDetail.class, mappedBy = "favoriteSpecifyEntity", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "OFIMT_FAVORITE_DETAIL")
	public List<FavoriteSpecifyEntityDetail> listFavoriteSpecifyEntityDetail;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	@Override
	public void setFavoriteName(String favoriteName) {
		this.favoriteName = favoriteName;

	}

	@Override
	public void setCreatorId(String creatorId) {
		if (this.pk == null) {
			this.pk = new FavoriteSpecifyEntityPK();
		}
		this.pk.setCreatorId(creatorId);
	}

	@Override
	public void setInputDate(GeneralDateTime inputDate) {
		if (this.pk == null) {
			this.pk = new FavoriteSpecifyEntityPK();
		}
		this.pk.setInputDate(inputDate);
	}

	@Override
	public void setWorkplaceId(List<String> workplaceId) {
		this.listFavoriteSpecifyEntityDetail = workplaceId.stream().map(wid -> {
			FavoriteSpecifyEntityDetail entity = new FavoriteSpecifyEntityDetail();
			entity.toEntity(this.pk.getCreatorId(), this.pk.getInputDate(), wid);
			return entity;
		}).collect(Collectors.toList());
	}

	@Override
	public String getCreatorId() {
		return this.pk.getCreatorId();
	}

	@Override
	public GeneralDateTime getInputDate() {
		return this.pk.getInputDate();
	}

	@Override
	public List<String> getWorkplaceId() {
		if(targetSelection == TargetSelection.WORKPLACE.value) {
			return listFavoriteSpecifyEntityDetail.stream()
					.map(item -> {
						return item.getPk().getTargetSelection();
					})
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
}
