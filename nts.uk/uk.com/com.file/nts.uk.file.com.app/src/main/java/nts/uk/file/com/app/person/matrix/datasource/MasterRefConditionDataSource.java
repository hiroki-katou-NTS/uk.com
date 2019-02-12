package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;

/**
 * MasterRefConditionDataSource
 * 
 * @author lanlt
 *
 */
@Getter
public class MasterRefConditionDataSource extends SelectionItemDataSource {

	private String masterType;

	private MasterRefConditionDataSource(String masterType, int dataTypeValue) {
		super(ReferenceTypes.DESIGNATED_MASTER);
		this.masterType = masterType;
		this.dataTypeValue = dataTypeValue;
	}

	public static SelectionItemDataSource createFromJavaType(String masterType, int dataTypeValue) {
		return new MasterRefConditionDataSource(masterType, dataTypeValue);
	}
}
