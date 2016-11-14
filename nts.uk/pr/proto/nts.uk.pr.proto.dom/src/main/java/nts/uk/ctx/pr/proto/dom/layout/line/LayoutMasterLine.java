package nts.uk.ctx.pr.proto.dom.layout.line;

import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;

import java.util.List;

import lombok.Getter;

import nts.arc.layer.dom.DomainObject;

public class LayoutMasterLine extends DomainObject {

	@Getter
	private AutoLineId autoLineId;

	@Getter
	private LineDispAtr lineDispayAttribute;

	@Getter
	private LinePosition linePosition;

	@Getter
	private List<LayoutMasterDetail> layoutMasterDetails;

	public LayoutMasterLine(AutoLineId autoLineId, LineDispAtr lineDispayAttribute, LinePosition linePosition,
			List<LayoutMasterDetail> layoutMasterDetails) {
		super();
		this.autoLineId = autoLineId;
		this.lineDispayAttribute = lineDispayAttribute;
		this.linePosition = linePosition;
		this.layoutMasterDetails = layoutMasterDetails;
	}

}
