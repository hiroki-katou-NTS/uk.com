package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 組織別シフトマスタ
 * @author tutk
 *
 */
public class ShiftMasterOrganization implements DomainAggregate {
	/**
	 * 会社ID
	 */
	@Getter
	private final String companyId;
	
	/**
	 * 対象組織
	 */
	@Getter
	private final TargetOrgIdenInfor targetOrg;
	
	/**
	 * シフトマスタリスト
	 */
	@Getter
	private List<String> listShiftMaterCode = new ArrayList<>();

	
	public ShiftMasterOrganization(String companyId, TargetOrgIdenInfor targetOrg, List<String> listShiftMaterCode) {
		//inv-1	@シフトマスタリスト.size > 0	
		if(listShiftMaterCode.isEmpty()) {
			throw new RuntimeException("シフトマスタリスト.size > 0");
		}
		//inv-2	@シフトマスタリスト の内容が重複しない	
		List<String> listCode =  listShiftMaterCode.stream().distinct().collect(Collectors.toList());
		if(listCode.size() != listShiftMaterCode.size()) {
			throw new RuntimeException("シフトマスタリスト.size > 0");
		}
		
		this.companyId = companyId;
		this.targetOrg = targetOrg;
		this.listShiftMaterCode = listShiftMaterCode;
	}
	
	/**
	 * 変更する
	 * @param listShiftMaterCode
	 */
	public void change(List<String> listShiftMaterCode) {
		//inv-1	@シフトマスタリスト.size > 0	
		if(listShiftMaterCode.isEmpty()) {
			throw new RuntimeException("シフトマスタリスト.size > 0");
		}
		//inv-2	@シフトマスタリスト の内容が重複しない	
		List<String> listCode =  listShiftMaterCode.stream().distinct().collect(Collectors.toList());
		if(listCode.size() != listShiftMaterCode.size()) {
			throw new RuntimeException("シフトマスタリスト.size > 0");
		}
		
		this.listShiftMaterCode = listCode;
	}
	
	/**
	 * 複写する
	 * @param targetOrg
	 */
	public ShiftMasterOrganization copy(TargetOrgIdenInfor targetOrg ) {
		return new ShiftMasterOrganization(this.companyId, targetOrg, this.listShiftMaterCode);
	}
	
		
	

}
