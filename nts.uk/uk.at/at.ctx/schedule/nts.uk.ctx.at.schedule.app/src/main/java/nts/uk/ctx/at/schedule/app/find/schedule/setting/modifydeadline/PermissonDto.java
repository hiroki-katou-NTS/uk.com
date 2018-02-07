package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import java.util.List;

import lombok.Data;

@Data
public class PermissonDto {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	private List<CommonAuthorDto> commonAuthor;
	
	private List<DateAuthorityDto> dateAuthority;
	
	private List<ModifyDeadlineDto> schemodifyDeadline;
	
	private List<PersAuthorityDto> persAuthority;
	
	private List<PerWorkplaceDto> perWorkplace;
	
	private List<ShiftPermissonDto> shiftPermisson;

	public PermissonDto( String roleId, List<CommonAuthorDto> commonAuthor, List<DateAuthorityDto> dateAuthority,
			List<ModifyDeadlineDto> schemodifyDeadline, List<PersAuthorityDto> persAuthority, List<PerWorkplaceDto> perWorkplace,
			List<ShiftPermissonDto> shiftPermisson) {
		super();
		this.roleId = roleId;
		this.commonAuthor = commonAuthor;
		this.dateAuthority = dateAuthority;
		this.schemodifyDeadline = schemodifyDeadline;
		this.persAuthority = persAuthority;
		this.perWorkplace = perWorkplace;
		this.shiftPermisson = shiftPermisson;
	}
}
