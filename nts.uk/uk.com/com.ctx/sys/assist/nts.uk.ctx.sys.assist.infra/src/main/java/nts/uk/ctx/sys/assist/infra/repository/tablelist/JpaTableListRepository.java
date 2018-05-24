package nts.uk.ctx.sys.assist.infra.repository.tablelist;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.ctx.sys.assist.infra.entity.tablelist.SspmtTableList;

public class JpaTableListRepository extends JpaRepository implements TableListRepository {

	@Override
	public void add(TableList domain) {
		this.commandProxy().insert(SspmtTableList.toEntity(domain));
	}

}
