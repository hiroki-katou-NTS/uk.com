/**
 * 
 */
package nts.uk.ctx.hr.shared.infra.repository.laborcontracthistory;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.algorithm.LaborContractHistoryInfoRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaLaborContractHistoryInfoRepository extends JpaRepository implements LaborContractHistoryInfoRepository {



}
