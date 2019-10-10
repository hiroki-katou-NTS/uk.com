/**
 * 
 */
package nts.uk.ctx.pereg.dom.filemanagement.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.filemanagement.EmpFileManagementRepository;

/**
 *
 */
@Stateless
public class PersonFileManagementService {

	@Inject
	private EmpFileManagementRepository empFileManagementRepo;

	/**
	 * [RQ624]個人IDから個人ファイル管理を取得する
	 */
	public List<PersonFileManagementDto> getPersonalFileManagementFromPID(List<String> lstpid) {

		return new ArrayList<PersonFileManagementDto>();

	}
}
