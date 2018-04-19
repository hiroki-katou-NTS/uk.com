package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository;

@Stateless
public class JpaMasterCopyDataRepository extends JpaRepository implements MasterCopyDataRepository{

}
