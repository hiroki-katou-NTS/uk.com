package nts.uk.ctx.at.request.app.find.application.lateleaveearly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.CancelAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;

@Getter
@AllArgsConstructor
public class LateEarlyCancelAppSetDto {

	private String companyId;

	private int cancelAtr;

//	private int lateAlClearAtr;

	public static LateEarlyCancelAppSetDto fromDomain(LateEarlyCancelAppSet lateEarlyCancelAppSet) {
		return (lateEarlyCancelAppSet == null) ? null
				: new LateEarlyCancelAppSetDto(lateEarlyCancelAppSet.getCompanyID(),
				lateEarlyCancelAppSet.getCancelAtr().value);
	}

	public LateEarlyCancelAppSet toDomain() {
		return new LateEarlyCancelAppSet(this.companyId, EnumAdaptor.valueOf(this.getCancelAtr(), CancelAtr.class));
	}
}
