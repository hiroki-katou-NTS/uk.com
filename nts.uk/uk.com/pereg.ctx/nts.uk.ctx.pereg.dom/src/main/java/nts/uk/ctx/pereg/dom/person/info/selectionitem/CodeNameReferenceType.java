package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;

@Getter
public class CodeNameReferenceType extends ReferenceTypeState {
	
	private TypeCode typeCode;
	
	@Override
	public String getReferenceCode() {
		return this.typeCode.v();
	}

	private CodeNameReferenceType(String typeCode) {
		super();
		this.referenceType = ReferenceTypes.CODE_NAME;
		this.typeCode = new TypeCode(typeCode);
	}

	public static CodeNameReferenceType createFromJavaType(String typeCode) {
		return new CodeNameReferenceType(typeCode);
	}

}
