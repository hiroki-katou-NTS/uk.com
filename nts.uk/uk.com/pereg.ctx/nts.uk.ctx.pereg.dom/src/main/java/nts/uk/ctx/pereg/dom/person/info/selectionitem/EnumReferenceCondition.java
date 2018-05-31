package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;

@Getter
public class EnumReferenceCondition extends ReferenceTypeState {

	private EnumName enumName;
	
	@Override
	public String getReferenceCode() {
		return this.enumName.v();
	}

	private EnumReferenceCondition(String enumName) {
		super();
		this.referenceType = ReferenceTypes.ENUM;
		this.enumName = new EnumName(enumName);
	}

	public static EnumReferenceCondition createFromJavaType(String enumName) {
		return new EnumReferenceCondition(enumName);
	}

}
