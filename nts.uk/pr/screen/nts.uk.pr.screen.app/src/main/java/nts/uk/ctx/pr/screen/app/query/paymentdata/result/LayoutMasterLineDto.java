package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;

@Data
public class LayoutMasterLineDto {

	private String autoLineId;

	private int lineDispayAttribute;

	private int linePosition;

	private List<LayoutMasterDetail> layoutMasterDetails;

}
