package nts.uk.ctx.sys.portal.infra.repository.toppage.widget;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.WidgetDisplay;
import nts.uk.ctx.sys.portal.infra.entity.toppage.widget.SptstOptionalWidget;
import nts.uk.ctx.sys.portal.infra.entity.toppage.widget.SptstOptionalWidgetPK;
import nts.uk.ctx.sys.portal.infra.entity.toppage.widget.SptstWidgetDisplay;
import nts.uk.ctx.sys.portal.infra.entity.toppage.widget.SptstWidgetDisplayPK;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePart;

@Stateless
public class JpaOptionalWidgetRepository extends JpaRepository implements OptionalWidgetRepository {

	private final String SELECT_BASE = "SELECT e, f" + " FROM SptstOptionalWidget e JOIN CcgmtTopPagePart f"
			+ " ON e.sptstOptionalWidgetPK.topPagePartID = f.ccgmtTopPagePartPK.topPagePartID";
	private final String SELECT_SINGLE = SELECT_BASE + " WHERE m.sptstOptionalWidgetPK.topPagePartID = :topPagePartID";
	private final String SELECT_BY_COMPANY = SELECT_BASE + " WHERE m.sptstOptionalWidgetPK.companyID = :companyID"
			+ " ORDER BY t.ccgmtTopPagePart.code";;
	private final String SELECT_WIDGET = "SELECT w" + " FROM SptstWidgetDisplay w"
			+ " WHERE w.sptstWidgetDisplayPK.companyID = :companyID"
			+ "AND w.sptstWidgetDisplayPK.widgetCode = :widgetCode ";

	@Override
	public List<OptionalWidget> findByCompanyId(String companyID) {
		return this.queryProxy().query(SELECT_BY_COMPANY, Object[].class).setParameter("companyID", companyID)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<OptionalWidget> findByCode(String companyID, String topPagePartID) {
		return this.queryProxy().query(SELECT_SINGLE, Object[].class).setParameter("topPagePartID", topPagePartID)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public List<WidgetDisplay> findAllWidget(String companyID, String widgetCode) {
		return this.queryProxy().query(SELECT_WIDGET, SptstWidgetDisplay.class).setParameter("companyID", companyID)
				.setParameter("widgetCode", widgetCode).getList(c -> toDomainDisplay(c));
	}

	private WidgetDisplay toDomainDisplay(SptstWidgetDisplay entity) {
		WidgetDisplay widget = WidgetDisplay.createFromJavaType(entity.sptstWidgetDisplayPK.companyID,
				entity.sptstWidgetDisplayPK.topPagePartID, entity.sptstWidgetDisplayPK.widgetCode,entity.sptstWidgetDisplayPK.widgetType, entity.useAtr.value);
		return widget;
	}

	private OptionalWidget toDomain(Object[] entity) {
		SptstOptionalWidget widget = (SptstOptionalWidget) entity[0];
		CcgmtTopPagePart topPagePart = (CcgmtTopPagePart) entity[1];
		return OptionalWidget.createFromJavaType(widget.sptstOptionalWidgetPK.companyID,
				widget.sptstOptionalWidgetPK.topPagePartID, topPagePart.code, topPagePart.name,
				topPagePart.topPagePartType, topPagePart.width, topPagePart.height);
	}

	private SptstOptionalWidget toEntity(OptionalWidget domain) {
		return new SptstOptionalWidget(new SptstOptionalWidgetPK(domain.getCompanyID(), domain.getToppagePartID()));
	}

	@Override
	public void add(WidgetDisplay widget) {
		this.commandProxy().insert(toEntityWidget(widget));
	}

	private SptstWidgetDisplay toEntityWidget(WidgetDisplay display) {
		return new SptstWidgetDisplay(new SptstWidgetDisplayPK(display.getCompanyID(), display.getTopPagePartID(),
				display.getWidgetCode().v(),display.getWidgeType().value), display.getUseAtr());

	}

	@Override
	public void update(WidgetDisplay display) {
		SptstWidgetDisplay newEntity = toEntityWidget(display);
		SptstWidgetDisplay entity = this.queryProxy().find(newEntity.sptstWidgetDisplayPK, SptstWidgetDisplay.class)
				.get();
		this.commandProxy().update(entity);
	}

	@Override
	public void removeWidget(String companyID, String widgetCode) {
		this.commandProxy().remove(SptstWidgetDisplay.class, new SptstOptionalWidgetPK(companyID, widgetCode));
		this.getEntityManager().flush();
	}

	@Override
	public void add(OptionalWidget widget) {
		this.commandProxy().insert(toEntity(widget));
	}

	@Override
	public void update(OptionalWidget widget) {
		SptstOptionalWidget newEntity = toEntity(widget);
		SptstOptionalWidget entity = this.queryProxy().find(newEntity.sptstOptionalWidgetPK, SptstOptionalWidget.class)
				.get();
		this.commandProxy().update(entity);
	}

	@Override
	public void remove(String companyID, String topPagePartID) {
		this.commandProxy().remove(SptstOptionalWidget.class, new SptstOptionalWidgetPK(companyID, topPagePartID));
		this.getEntityManager().flush();
	}

}
