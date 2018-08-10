package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import java.util.List;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryNo;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.TargetTableInfo;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyData;

/**
 * The Class JpaMasterCopyDataGetMemento.
 */
public class JpaMasterCopyDataGetMemento implements MasterCopyDataGetMemento {

	/** The entity. */
	private SspmtMastercopyData entity;

	/**
	 * Instantiates a new jpa master copy data get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaMasterCopyDataGetMemento(SspmtMastercopyData entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento#
	 * getMasterCopyId()
	 */
	@Override
	public MasterCopyCategoryNo getCategoryNo() {
		return new MasterCopyCategoryNo(this.entity.getId().getCategoryNo());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento#getTargetTable()
	 */
	@Override
	public List<TargetTableInfo> getTargetTable() {
		// TODO Auto-generated method stub
		return null;
	}

}
