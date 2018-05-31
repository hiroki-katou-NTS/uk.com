package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;

@Getter
public class MasterReferenceCondition extends ReferenceTypeState {

	private MasterType masterType;
	
	@Override
	public String getReferenceCode() {
		return this.masterType.v();
	}

	private MasterReferenceCondition(String masterType) {
		super();
		this.referenceType = ReferenceTypes.DESIGNATED_MASTER;
		this.masterType = new MasterType(masterType);
	}

	public static MasterReferenceCondition createFromJavaType(String masterType) {
		return new MasterReferenceCondition(masterType);
	}

}
