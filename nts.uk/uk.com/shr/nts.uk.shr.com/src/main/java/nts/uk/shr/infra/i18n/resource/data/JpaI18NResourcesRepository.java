package nts.uk.shr.infra.i18n.resource.data;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.shr.infra.i18n.resource.I18NResourceType;
import nts.uk.shr.infra.i18n.resource.container.CustomizedI18NResourceContainers;
import nts.uk.shr.infra.i18n.resource.container.I18NResourceContainer;
import nts.uk.shr.infra.i18n.resource.container.I18NResourceItem;
import nts.uk.shr.infra.i18n.resource.container.I18NResourcesRepository;
import nts.uk.shr.infra.i18n.resource.container.MessageResourceItem;
import nts.uk.shr.infra.i18n.resource.container.ProgramResourceItem;

@Stateless
public class JpaI18NResourcesRepository extends JpaRepository implements I18NResourcesRepository {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends I18NResourceItem> I18NResourceContainer<T> loadResourcesDefault(String languageId) {
		
		String query = "SELECT e FROM CismtSystemResource e"
				+ " WHERE e.pk.languageId = :languageId";
		
		val items = this.queryProxy().query(query, CismtSystemResource.class)
				.setParameter("languageId", languageId)
				.getList(e -> {
					val resourceType = I18NResourceType.of(e.resourceType);
					val content = e.content.replace("\\r", "").replace("\\n", System.getProperty("line.separator"));
					switch (resourceType) {
					case MESSAGE:
						return (T)new MessageResourceItem(e.pk.code, content);
					case ITEM_NAME:
						return (T)new ProgramResourceItem(e.pk.programId, e.pk.code, content);
					default:
						// 明らかにバグ（データ設定ミス）だが、エラーにして処理を停止させるほど深刻ではないので、処理を継続させる
						return (T)new MessageResourceItem(e.pk.code, content);
					}
				});
		
		val container = new I18NResourceContainer<T>();
		container.addAll(items);
		
		return container;
	}

	@Override
	public <T extends I18NResourceItem> CustomizedI18NResourceContainers<T> loadResourcesEachCompanies(
			String languageId) {
		// TODO Auto-generated method stub
		return new CustomizedI18NResourceContainers<>();
	}


}
