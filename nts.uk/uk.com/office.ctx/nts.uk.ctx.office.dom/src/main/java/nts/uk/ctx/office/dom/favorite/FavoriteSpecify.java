package nts.uk.ctx.office.dom.favorite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforImport;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.お気に入りの指定
 */
@Getter
public class FavoriteSpecify extends AggregateRoot {

	// お気に入り名
	private FavoriteName favoriteName;

	// 作成者ID
	private String creatorId;

	// 入力日
	private GeneralDateTime inputDate;

	// 対象選択
	private TargetSelection targetSelection;

	// 職場ID
	private List<String> workplaceId;

	// 順序
	private Integer order;

	public FavoriteSpecify() {}

	/**
	 * [1] 対象惱名を渡
	 * 
	 * @param require
	 * @return List<String>
	 */
	public List<String> passingTargetInfoName(Require require) {
		if (this.targetSelection == TargetSelection.AFFILIATION_WORKPLACE) {
			return Collections.emptyList();
		} else {
			List<String> list = new ArrayList<>();
			require.getWrkspDispName(this.workplaceId, GeneralDate.today())
				.forEach((key, value) -> list.add(value.getWorkplaceDisplayName()));
			return list;
		}
	}

	public static FavoriteSpecify createFromMemento(MementoGetter memento) {
		FavoriteSpecify domain = new FavoriteSpecify();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.favoriteName = new FavoriteName(memento.getFavoriteName());
		this.creatorId = memento.getCreatorId();
		this.inputDate = memento.getInputDate();
		this.targetSelection = EnumAdaptor.valueOf(memento.getTargetSelection(), TargetSelection.class);
		this.workplaceId = memento.getWorkplaceId();
		this.order = memento.getOrder();
	}

	public void setMemento(MementoSetter memento) {
		memento.setFavoriteName(this.favoriteName.v());
		memento.setCreatorId(this.creatorId);
		memento.setInputDate(this.inputDate);
		memento.setTargetSelection(this.targetSelection.value);
		memento.setWorkplaceId(this.workplaceId);
		memento.setOrder(this.order);
	}

	public interface MementoSetter {
		void setFavoriteName(String favoriteName);

		void setCreatorId(String creatorId);

		void setInputDate(GeneralDateTime inputDate);

		void setTargetSelection(Integer targetSelection);

		void setWorkplaceId(List<String> workplaceId);

		void setOrder(Integer order);
	}

	public interface MementoGetter {
		String getFavoriteName();

		String getCreatorId();

		GeneralDateTime getInputDate();

		Integer getTargetSelection();

		List<String> getWorkplaceId();

		Integer getOrder();
	}

	public static interface Require {
		/**
		 * [R-1] 職場表示名を取得す�
		 * 
		 * @param wrkspIds 職場IDリス�
		 * @param date     基準日
		 * @return  Map<String, WorkplaceInforImport>
		 * 
		 *         職場惱を取得するAdapter.職場惱を取得す�職場IDリスト、基準日)
		 */
		public Map<String, WorkplaceInforImport> getWrkspDispName(List<String> wrkspIds, GeneralDate date);
	}
}
