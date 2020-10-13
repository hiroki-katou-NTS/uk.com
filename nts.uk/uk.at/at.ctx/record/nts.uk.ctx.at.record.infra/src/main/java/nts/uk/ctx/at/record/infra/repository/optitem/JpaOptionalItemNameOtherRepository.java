package nts.uk.ctx.at.record.infra.repository.optitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstOptionalItemNameOther;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstOptionalItemNameOtherPK;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNameOther;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNameOtherRepository;

@Stateless
public class JpaOptionalItemNameOtherRepository extends JpaRepository implements OptionalItemNameOtherRepository{

	@Override
	public Optional<OptionalItemNameOther> findByKey(String companyId, int no, String langueId) {
		return this.queryProxy()
				.find(new KrcstOptionalItemNameOtherPK(companyId, no, langueId), KrcstOptionalItemNameOther.class)
				.map(x -> x.toDomain());
	}
	
	@Override
	public List<OptionalItemNameOther> findAll(String companyId, String langueId) {
		StringBuilder build = new StringBuilder();
		build.append(" select o from KrcstOptionalItemNameOther o");
		build.append(" where o.krcstOptItemNamePK.cid = :cid");
		build.append(" and o.krcstOptItemNamePK.langId = :langId");
		
		return this.queryProxy().query(build.toString(), KrcstOptionalItemNameOther.class)
				.setParameter("cid", companyId).setParameter("langId", langueId).getList(x -> x.toDomain());
	}

	@Override
	public void add(OptionalItemNameOther item) {
		this.commandProxy().insert(KrcstOptionalItemNameOther.toEntity(item));
	}

	@Override
	public void update(OptionalItemNameOther item) {
		this.commandProxy().update(KrcstOptionalItemNameOther.toEntity(item));
	}

	@Override
	public void remove(String companyId, int no) {
		StringBuilder build = new StringBuilder();
		build.append(" delete from KrcstOptionalItemNameOther o");
		build.append(" where o.krcstOptItemNamePK.cid = :cid");
		build.append(" and o.krcstOptItemNamePK.optionalItemNo = :no");
		
		this.queryProxy().query(build.toString()).setParameter("cid", companyId).setParameter("no", no);
		
	}

}
