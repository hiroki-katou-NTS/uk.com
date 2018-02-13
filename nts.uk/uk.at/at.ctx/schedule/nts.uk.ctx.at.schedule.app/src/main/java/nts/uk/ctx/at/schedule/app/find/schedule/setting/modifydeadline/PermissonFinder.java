package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import java.util.List;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CommonAuthor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CommonAuthorRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.DateAuthority;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.DateAuthorityRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PerWorkplace;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PerWorkplaceRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PersAuthority;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PersAuthorityRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.ScheModifyDeadlineRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.SchemodifyDeadline;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.ShiftPermisson;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.ShiftPermissonRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class PermissonFinder {
	
	@Inject
	private CommonAuthorRepository repositoryCommon;
	
	@Inject
	private DateAuthorityRepository repositoryDate;
	
	@Inject
	private ScheModifyDeadlineRepository repositoryModify;
	
	@Inject
	private PersAuthorityRepository repositoryPers;
	
	@Inject
	private PerWorkplaceRepository repositoryWorkplace;
	
	@Inject
	private ShiftPermissonRepository repositoryShift;
	
	/**
	 * Finder Common Author
	 * @param roleId
	 * @return
	 */
				public List<CommonAuthorDto> findAllCommon(String roleId) {
					String companyId = AppContexts.user().companyId();
					return repositoryCommon.findByCompanyId(companyId, roleId).stream().map(e -> {
						return convertToDbTypeCommon(e);
					}).collect(Collectors.toList());
				}
				private CommonAuthorDto convertToDbTypeCommon(CommonAuthor commonAuthor) {
					CommonAuthorDto commonAuthorDto = new CommonAuthorDto();
					commonAuthorDto.setRoleId(commonAuthor.getRoleId());
					commonAuthorDto.setAvailableCommon(commonAuthor.getAvailableCommon());
					commonAuthorDto.setFunctionNoCommon(commonAuthor.getFunctionNoCommon());
					
				return commonAuthorDto;
				}

	/**
	 * Finder Date Authority
	 * @param roleId
	 * @return
	 */
				public List<DateAuthorityDto> findAllDate(String roleId) {
					String companyId = AppContexts.user().companyId();
					return repositoryDate.findByCompanyId(companyId, roleId).stream().map(e -> {
						return convertToDbTypeDate(e);
					}).collect(Collectors.toList());
				}
			
				private DateAuthorityDto convertToDbTypeDate(DateAuthority commonAuthor) {
					DateAuthorityDto commonAuthorDto = new DateAuthorityDto();
					commonAuthorDto.setRoleId(commonAuthor.getRoleId());
					commonAuthorDto.setAvailableDate(commonAuthor.getAvailableDate());
					commonAuthorDto.setFunctionNoDate(commonAuthor.getFunctionNoDate());
					
				return commonAuthorDto;
				}
	
	/**
	 * Finder Modify Deadline
	 * @param roleId
	 * @return
	 */
				public List<ModifyDeadlineDto> findAllModify(String roleId) {
					String companyId = AppContexts.user().companyId();
					return repositoryModify.findByCompanyId(companyId, roleId).stream().map(e -> {
						return convertToDbTypeModify(e);
					}).collect(Collectors.toList());
				}
				
				private ModifyDeadlineDto convertToDbTypeModify(SchemodifyDeadline deadline) {
					ModifyDeadlineDto deadlineDto = new ModifyDeadlineDto();
					deadlineDto.setRoleId(deadline.getRoleId());
					deadlineDto.setUseCls(deadline.getUseCls().value);
					deadlineDto.setCorrectDeadline(deadline.getCorrectDeadline().v());
					
				return deadlineDto;
				}
	
	/**
	 * Finder Pers Authority
	 * @param roleId
	 * @return
	 */
				public List<PersAuthorityDto> findAllPers(String roleId) {
					String companyId = AppContexts.user().companyId();
					return repositoryPers.findByCompanyId(companyId, roleId).stream().map(e -> {
						return convertToDbTypePers(e);
					}).collect(Collectors.toList());
				}
			
				private PersAuthorityDto convertToDbTypePers(PersAuthority commonAuthor) {
					PersAuthorityDto commonAuthorDto = new PersAuthorityDto();
					commonAuthorDto.setRoleId(commonAuthor.getRoleId());
					commonAuthorDto.setAvailablePers(commonAuthor.getAvailablePers());
					commonAuthorDto.setFunctionNoPers(commonAuthor.getFunctionNoPers());
					
				return commonAuthorDto;
				}
	
	/**
	 * Finder Per Workplace
	 * @param roleId
	 * @return
	 */
				public List<PerWorkplaceDto> findAllWorkplace(String roleId) {
					String companyId = AppContexts.user().companyId();
					return repositoryWorkplace.findByCompanyId(companyId, roleId).stream().map(e -> {
						return convertToDbTypeWorkplace(e);
					}).collect(Collectors.toList());
				}
			
				private PerWorkplaceDto convertToDbTypeWorkplace(PerWorkplace commonAuthor) {
					PerWorkplaceDto commonAuthorDto = new PerWorkplaceDto();
					commonAuthorDto.setRoleId(commonAuthor.getRoleId());
					commonAuthorDto.setAvailableWorkplace(commonAuthor.getAvailableWorkplace());
					commonAuthorDto.setFunctionNoWorkplace(commonAuthor.getFunctionNoWorkplace());
					
				return commonAuthorDto;
				}
				
	/**
	 * Finder Shift Permisson
	 * @param roleId
	 * @return
	 */
				public List<ShiftPermissonDto> findAllShift(String roleId) {
					String companyId = AppContexts.user().companyId();
					return repositoryShift.findByCompanyId(companyId, roleId).stream().map(e -> {
						return convertToDbTypeShift(e);
					}).collect(Collectors.toList());
				}
			
				private ShiftPermissonDto convertToDbTypeShift(ShiftPermisson commonAuthor) {
					ShiftPermissonDto commonAuthorDto = new ShiftPermissonDto();
					commonAuthorDto.setRoleId(commonAuthor.getRoleId());
					commonAuthorDto.setAvailableShift(commonAuthor.getAvailableShift());
					commonAuthorDto.setFunctionNoShift(commonAuthor.getFunctionNoShift());
					
				return commonAuthorDto;
				}
				
	public PermissonDto getAll (String roleId){
		return new PermissonDto( roleId, this.findAllCommon(roleId), this.findAllDate(roleId), this.findAllModify(roleId), 
				this.findAllPers(roleId), this.findAllWorkplace(roleId), this.findAllShift(roleId));
	}			
}
