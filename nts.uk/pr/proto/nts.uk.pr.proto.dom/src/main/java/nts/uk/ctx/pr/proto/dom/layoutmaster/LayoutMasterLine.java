package nts.uk.ctx.pr.proto.dom.layoutmaster;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.proto.dom.layoutmasterdetail.LayoutMasterDetail;

public class LayoutMasterLine extends DomainObject{
	
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
