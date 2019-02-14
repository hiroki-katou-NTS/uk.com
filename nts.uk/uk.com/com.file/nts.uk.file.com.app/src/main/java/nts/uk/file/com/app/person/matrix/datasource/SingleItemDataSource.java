package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;

/**
 * SingleItemDataSource
 * 
 * @author lanlt
 *
 */
@Getter
public class SingleItemDataSource extends ItemTypeStateDataSource {

	protected DataTypeStateDataSource dataTypeState;

	private SingleItemDataSource(DataTypeStateDataSource dataTypeState) {
		super();
//		this.itemType = ItemType.SINGLE_ITEM.value;
		this.dataTypeState = dataTypeState;
	}

	public static SingleItemDataSource createFromJavaType(DataTypeStateDataSource dataTypeState) {
		return new SingleItemDataSource(dataTypeState);
	}
}
