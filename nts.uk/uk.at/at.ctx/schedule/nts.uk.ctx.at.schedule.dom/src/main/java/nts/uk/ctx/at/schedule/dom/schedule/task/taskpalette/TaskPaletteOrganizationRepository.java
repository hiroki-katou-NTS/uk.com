package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface TaskPaletteOrganizationRepository {
	
	/**
	 * insert
	 * @param domain 組織の作業パレット
	 */
	public void insert(TaskPaletteOrganization domain);
	
	/**
	 * update
	 * @param domain 組織の作業パレット
	 */
	public void update(TaskPaletteOrganization domain);
	
	/**
	 * delete
	 * @param targetOrg 対象組織
	 * @param page ページ
	 */
	public void delete(TargetOrgIdenInfor targetOrg, int page);
	
	/**
	 * 取得する
	 * @param targetOrg 対象組織
	 * @param page ページ
	 * @return
	 */
	public Optional<TaskPaletteOrganization> getByPage(TargetOrgIdenInfor targetOrg, int page);
	
	/**
	 * 作業パレットをすべて取得する
	 * @param targetOrg 対象組織
	 * @return
	 */
	public Optional<TaskPaletteOrganization> getAll(TargetOrgIdenInfor targetOrg);
	

}
