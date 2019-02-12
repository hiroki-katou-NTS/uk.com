package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class SelectionRadioDataSource extends SelectionItemDataSource {

	private ReferenceTypes referenceType;

	public SelectionRadioDataSource(ReferenceTypes referenceType) {
		super(referenceType);
		this.dataTypeValue = DataTypeValue.SELECTION_RADIO.value;
	}

}
