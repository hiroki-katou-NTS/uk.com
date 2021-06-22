package nts.uk.ctx.sys.auth.app.find.grant.rolesetjob;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitle;

/**
 * 
 * @author HungTT
 *
 */

@Data
public class RoleSetGrantedJobTitleDto {

	// private String companyId;

	//TODO 「兼務者にも適用する」を消す ので, 「applyToConcurrentPerson」は削除お願いいたします。
	private boolean applyToConcurrentPerson;

	private List<RoleSetGrantedJobTitleDetailDto> details;

	public RoleSetGrantedJobTitleDto(boolean applyToConcurrentPerson, List<RoleSetGrantedJobTitleDetailDto> details) {
		super();
		// this.companyId = companyId;
		this.applyToConcurrentPerson = applyToConcurrentPerson;
		this.details = details;
	}

	public static RoleSetGrantedJobTitleDto fromDomain(RoleSetGrantedJobTitle domain) {
		return new RoleSetGrantedJobTitleDto(
				//TODO 「兼務者にも適用する」を消す ので, 一旦trueを渡す, このクラスは属性「applyToConcurrentPerson」を削除するとき、値[true]は削除お願いいたします。
				//domain.isApplyToConcurrentPerson()
				true
				, domain.getDetails().stream()
										.map(detail -> RoleSetGrantedJobTitleDetailDto.fromDomain(detail)).collect(Collectors.toList()));
	}

}
