package nts.uk.ctx.office.dom.favorite.service;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;

@Data
@Builder
public class FavoriteSpecifyAR implements FavoriteSpecify.MementoGetter, FavoriteSpecify.MementoSetter {
	private String favoriteName;

	private String creatorId;
	
	private GeneralDateTime inputDate;
	
	private Integer targetSelection;
	
	private List<String> workplaceId;
	
	private int order;

	@Override
	public void setFavoriteName(String favoriteName) {
		this.favoriteName = favoriteName;
	}

	@Override
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Override
	public void setInputDate(GeneralDateTime inputDate) {
		this.inputDate = inputDate;
	}

	@Override
	public void setTargetSelection(Integer targetSelection) {
		this.targetSelection = targetSelection;
	}

	@Override
	public void setWorkplaceId(List<String> workplaceId) {
		this.workplaceId = workplaceId;
	}

	@Override
	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public String getFavoriteName() {
		return this.favoriteName;
	}

	@Override
	public String getCreatorId() {
		return this.creatorId;
	}

	@Override
	public GeneralDateTime getInputDate() {
		return this.inputDate;
	}

	@Override
	public Integer getTargetSelection() {
		return this.targetSelection;
	}

	@Override
	public List<String> getWorkplaceId() {
		return this.workplaceId;
	}

	@Override
	public Integer getOrder() {
		return this.order;
	}
}
