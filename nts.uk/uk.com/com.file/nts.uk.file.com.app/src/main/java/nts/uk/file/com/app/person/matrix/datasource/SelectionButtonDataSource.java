package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
/**
 * SelectionButtonDataSource
 * @author lanlt
 *
 */
public class SelectionButtonDataSource extends SelectionItemDataSource {

	private ReferenceTypes referenceType;

	public SelectionButtonDataSource(ReferenceTypes referenceType) {
		super(referenceType);
		this.dataTypeValue = DataTypeValue.SELECTION_BUTTON.value;
	}
}