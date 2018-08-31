package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import java.util.List;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleAuthorityDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleCommonDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleDateDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleDescriptionDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleShiftDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleWorkplaceDto;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.DescriptionRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.ScheduleAuthority;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.ScheduleCommon;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.ScheduleDate;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.ScheduleShift;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.ScheduleWorkplace;
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

	@Inject
	private DescriptionRepository descriptionRepository;

	/**
	 * Finder Common Author
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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

	public PermissonDto getAll(String roleId) {
		return new PermissonDto(roleId, this.findAllCommon(roleId), this.findAllDate(roleId),
				this.findAllModify(roleId), this.findAllPers(roleId), this.findAllWorkplace(roleId),
				this.findAllShift(roleId));
	}

	/**
	 * 
	 * @return
	 */
	public List<ScheduleCommonDto> findDesCommon() {
		return descriptionRepository.findByCom().stream().map(e -> {
			return convertToDbTypeCom(e);
		}).collect(Collectors.toList());
	}

	private ScheduleCommonDto convertToDbTypeCom(ScheduleCommon scheduleCommon) {
		ScheduleCommonDto commonAuthorDto = new ScheduleCommonDto();
		commonAuthorDto.setFunctionNoCom(scheduleCommon.getFunctionNoCom());
		commonAuthorDto.setDisplayOrderCom(scheduleCommon.getDisplayOrderCom());
		commonAuthorDto.setDisplayNameCom(scheduleCommon.getDisplayNameCom().v());
		commonAuthorDto.setDescripptionCom(scheduleCommon.getDescripptionCom().v());
		commonAuthorDto.setInitialValueCom(scheduleCommon.getInitialValueCom());
		return commonAuthorDto;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ScheduleAuthorityDto> findDesAuth() {
		return descriptionRepository.findByAut().stream().map(e -> {
			return convertToDbTypeAuth(e);
		}).collect(Collectors.toList());
	}

	private ScheduleAuthorityDto convertToDbTypeAuth(ScheduleAuthority scheduleAuthority) {
		ScheduleAuthorityDto scheduleAuthorityDto = new ScheduleAuthorityDto();
		scheduleAuthorityDto.setFunctionNoAuth(scheduleAuthority.getFunctionNoAuth());
		scheduleAuthorityDto.setDisplayOrderAuth(scheduleAuthority.getDisplayOrderAuth());
		scheduleAuthorityDto.setDisplayNameAuth(scheduleAuthority.getDisplayNameAuth().v());
		scheduleAuthorityDto.setDescripptionAuth(scheduleAuthority.getDescripptionAuth().v());
		scheduleAuthorityDto.setInitialValueAuth(scheduleAuthority.getInitialValueAuth());
		return scheduleAuthorityDto;
	}
	
	/**
	 * 
	 */
	public List<ScheduleDateDto> findDesDate() {
		return descriptionRepository.findByDate().stream().map(e -> {
			return convertToDbTypeDate(e);
		}).collect(Collectors.toList());
	}

	private ScheduleDateDto convertToDbTypeDate(ScheduleDate scheduleDate) {
		ScheduleDateDto scheduleDateDto = new ScheduleDateDto();
		scheduleDateDto.setFunctionNoDate(scheduleDate.getFunctionNoDate());
		scheduleDateDto.setDisplayOrderDate(scheduleDate.getDisplayOrderDate());
		scheduleDateDto.setDisplayNameDate(scheduleDate.getDisplayNameDate().v());
		scheduleDateDto.setDescripptionDate(scheduleDate.getDescripptionDate().v());
		scheduleDateDto.setInitialValueDate(scheduleDate.getInitialValueDate());
		return scheduleDateDto;
	}
	
	/**
	 * 
	 */
	public List<ScheduleShiftDto> findDesShift() {
		return descriptionRepository.findByShift().stream().map(e -> {
			return convertToDbTypeShift(e);
		}).collect(Collectors.toList());
	}

	private ScheduleShiftDto convertToDbTypeShift(ScheduleShift scheduleShift) {
		ScheduleShiftDto scheduleShiftDto = new ScheduleShiftDto();
		scheduleShiftDto.setFunctionNoShift(scheduleShift.getFunctionNoShift());
		scheduleShiftDto.setDisplayOrderShift(scheduleShift.getDisplayOrderShift());
		scheduleShiftDto.setDisplayNameShift(scheduleShift.getDisplayNameShift().v());
		scheduleShiftDto.setDescripptionShift(scheduleShift.getDescripptionShift().v());
		scheduleShiftDto.setInitialValueShift(scheduleShift.getInitialValueShift());
		return scheduleShiftDto;
	}
	
	/**
	 * 
	 */
	public List<ScheduleWorkplaceDto> findDesWork() {
		return descriptionRepository.findByWork().stream().map(e -> {
			return convertToDbTypeWork(e);
		}).collect(Collectors.toList());
	}

	private ScheduleWorkplaceDto convertToDbTypeWork(ScheduleWorkplace scheduleWorkplace) {
		ScheduleWorkplaceDto scheduleWorkplaceDto = new ScheduleWorkplaceDto();
		scheduleWorkplaceDto.setFunctionNoWork(scheduleWorkplace.getFunctionNoWork());
		scheduleWorkplaceDto.setDisplayOrderWork(scheduleWorkplace.getDisplayOrderWork());
		scheduleWorkplaceDto.setDisplayNameWork(scheduleWorkplace.getDisplayNameWork().v());
		scheduleWorkplaceDto.setDescripptionWork(scheduleWorkplace.getDescripptionWork().v());
		scheduleWorkplaceDto.setInitialValueWork(scheduleWorkplace.getInitialValueWork());
		return scheduleWorkplaceDto;
	}
	public ScheduleDescriptionDto getAllDes() {
		return new ScheduleDescriptionDto(this.findDesAuth(), this.findDesCommon(), this.findDesDate(), this.findDesShift(), this.findDesWork());
	}
}
