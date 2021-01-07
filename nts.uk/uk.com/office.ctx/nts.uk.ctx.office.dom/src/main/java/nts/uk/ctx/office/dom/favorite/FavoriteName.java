package nts.uk.ctx.office.dom.favorite;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.お気に入り名
 */
@StringMaxLength(20)
public class FavoriteName extends StringPrimitiveValue<FavoriteName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FavoriteName(String rawValue) {
		super(rawValue);
	}

}
