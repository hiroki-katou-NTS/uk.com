package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ApprovalStatusActualResult;

@Getter
@NoArgsConstructor
public class ApprovalStatusActualResultKDW003Dto {
	@Setter
	private boolean statusNormal;
	
	private ConfirmStatusActualResultKDW003Dto confirmStatusActualResult;

	public static ApprovalStatusActualResultKDW003Dto  fromDomain(ApprovalStatusActualResult domain) {
		return new ApprovalStatusActualResultKDW003Dto(domain.isStatus(), new ConfirmStatusActualResultKDW003Dto(domain.getEmployeeId(), domain.getDate(),
				domain.isStatus(), domain.getPermissionCheck().value, domain.getPermissionRelease().value));
	}
	
	public ApprovalStatusActualResult toDomain() {
		
		ApprovalStatusActualResult domain = new ApprovalStatusActualResult(this.confirmStatusActualResult.getEmployeeId(), this.confirmStatusActualResult.getDate(), 
				this.confirmStatusActualResult.isStatus(), this.statusNormal);
		domain.setPermission(this.confirmStatusActualResult.getPermissionCheck() == 1?true:false, this.confirmStatusActualResult.getPermissionRelease()==1?true:false);
		return domain;
	}

	public ApprovalStatusActualResultKDW003Dto(boolean statusNormal,
			ConfirmStatusActualResultKDW003Dto confirmStatusActualResult) {
		super();
		this.statusNormal = statusNormal;
		this.confirmStatusActualResult = confirmStatusActualResult;
	}
	
	@Override
	public boolean equals(Object other) {
          boolean check = this.confirmStatusActualResult.equals(((ApprovalStatusActualResultKDW003Dto)other).confirmStatusActualResult) && ((ApprovalStatusActualResultKDW003Dto)other).statusNormal == this.statusNormal;
          return check;
	}
}
