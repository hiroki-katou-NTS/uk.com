package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategory;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryGetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategorySetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyData;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataSetMemento;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyCategory;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyData;

@Stateless
public class JpaMasterCopyDataRepository extends JpaRepository implements MasterCopyDataRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository#findByMasterCopyId(java.lang.String)
	 */
	@Override
	public Optional<MasterCopyData> findByMasterCopyId(String masterCopyId) {
		return this.queryProxy().find(masterCopyId, SspmtMastercopyData.class).map(item -> this.toDomain(item));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the master copy data
	 */
	private MasterCopyData toDomain(SspmtMastercopyData entity) {
		MasterCopyDataGetMemento memento = new JpaMasterCopyDataGetMemento(entity);
		return new MasterCopyData(memento);
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the sspmt mastercopy data
	 */
	private SspmtMastercopyData toEntity(MasterCopyData domain) {
		SspmtMastercopyData entity = this.queryProxy().find(domain.getMasterCopyId(), SspmtMastercopyData.class)
				.orElse(new SspmtMastercopyData());

		MasterCopyDataSetMemento memento = new JpaMasterCopyDataSetMemento(entity);
		domain.saveToMemento(memento);

		return entity;
	}

}
