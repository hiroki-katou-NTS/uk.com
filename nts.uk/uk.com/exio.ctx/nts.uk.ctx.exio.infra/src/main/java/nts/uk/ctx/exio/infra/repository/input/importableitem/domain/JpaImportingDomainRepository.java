package nts.uk.ctx.exio.infra.repository.input.importableitem.domain;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomain;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainRepository;
import nts.uk.ctx.exio.infra.entity.input.importableitem.domain.XimctDomain;

@Stateless
@TransactionAttribute
public class JpaImportingDomainRepository extends JpaRepository implements ImportingDomainRepository {

	@Override
	public List<ImportingDomain> findAll() {
		
		String sql = "select * from XIMCT_DOMAIN";
		return this.jdbcProxy().query(sql)
				.getList(rec -> XimctDomain.MAPPER.toEntity(rec).toDomain());
	}
	
	@Override
	public ImportingDomain find(ImportingDomainId domainId) {
		
		String sql = "select * from XIMCT_DOMAIN"
				+ " where DOMAIN_ID = @id";
		
		return this.jdbcProxy().query(sql)
				.paramInt("id", domainId.value)
				.getSingle(rec -> XimctDomain.MAPPER.toEntity(rec))
				.map(e -> e.toDomain())
				.get();
	}

}
