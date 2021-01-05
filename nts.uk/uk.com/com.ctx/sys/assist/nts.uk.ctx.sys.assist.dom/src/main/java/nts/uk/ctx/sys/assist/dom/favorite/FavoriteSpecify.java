package nts.uk.ctx.sys.assist.dom.favorite;

import java.util.List;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.お気に入りの指定
 */
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
	private int order;

	private FavoriteSpecify() {
	}
	
	//TODO [1] 対象情報名を渡す
//	public List<String> passingTargetInfoName( Require require) {
//		if(this.targetSelection == TargetSelection.AFFILIATION_WORKPLACE) {
//			return empty;
//		}
//		if(this.targetSelection == TargetSelection.???) {
//			return require.getBussinessName(this.listEmployee);
//		}
//		if(this.targetSelection == TargetSelection.WORKPLACE) {
//			return require.getWorkplaceDisplayName(this.workplaceId);;
//		}
//	}
	
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

		void setOrder(int order);
	}

	public interface MementoGetter {
		String getFavoriteName();

		String getCreatorId();

		GeneralDateTime getInputDate();

		Integer getTargetSelection();

		List<String> getWorkplaceId();

		Integer getOrder();
	}
}
