package nts.uk.ctx.bs.person.dom.person.info.selectionitem;

import nts.arc.layer.dom.AggregateRoot;

public class ReferenceTypeState extends AggregateRoot {

	protected ReferenceType referenceType;

	public static ReferenceTypeState createMasterReferenceCondition(String masterType) {
		return MasterReferenceCondition.createFromJavaType(masterType);
	}

	public static ReferenceTypeState createCodeNameReferenceType(String typeCode) {
		return CodeNameReferenceType.createFromJavaType(typeCode);
	}

	public static ReferenceTypeState createEnumReferenceCondition(String enumName) {
		return EnumReferenceCondition.createFromJavaType(enumName);
	}
}
