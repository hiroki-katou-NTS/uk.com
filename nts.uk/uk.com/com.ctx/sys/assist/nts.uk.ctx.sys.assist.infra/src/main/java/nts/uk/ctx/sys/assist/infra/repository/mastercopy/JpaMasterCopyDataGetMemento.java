package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.sys.assist.dom.mastercopy.CopyAttribute;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyTargetTableName;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyTargetTableNo;
import nts.uk.ctx.sys.assist.dom.mastercopy.KEY;
import nts.uk.ctx.sys.assist.dom.mastercopy.KeyInformation;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryNo;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.TargetTableInfo;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyCategory;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyData;

/**
 * The Class JpaMasterCopyDataGetMemento.
 */
public class JpaMasterCopyDataGetMemento implements MasterCopyDataGetMemento {

	private SspmtMastercopyCategory categoryEntity;

	/** The entity. */
	private List<SspmtMastercopyData> dataEntities;

	/**
	 * Instantiates a new jpa master copy data get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaMasterCopyDataGetMemento(SspmtMastercopyCategory categoryEntity, List<SspmtMastercopyData> dataEntities) {
		this.categoryEntity = categoryEntity;
		this.dataEntities = dataEntities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento#
	 * getMasterCopyId()
	 */
	@Override
	public MasterCopyCategoryNo getCategoryNo() {
		return new MasterCopyCategoryNo(this.categoryEntity.getCategoryNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento#getTargetTable(
	 * )
	 */
	@Override
	public List<TargetTableInfo> getTargetTable() {
		return this.dataEntities.stream().map(e -> {
			KeyInformation keyInfo = new KeyInformation(new KEY(e.getKey1()), Optional.ofNullable(new KEY(e.getKey2())),
					Optional.ofNullable(new KEY(e.getKey3())), Optional.ofNullable(new KEY(e.getKey4())),
					Optional.ofNullable(new KEY(e.getKey5())));
			return new TargetTableInfo(keyInfo, CopyAttribute.valueOf(e.getCopyAtr().intValue()),
					new CopyTargetTableNo(e.getId().getTableNo()), new CopyTargetTableName(e.getTableName()));
		}).collect(Collectors.toList());
	}

}
