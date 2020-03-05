package nts.uk.ctx.at.schedule.infra.repository.shift.management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsCom;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteCmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteCmpCombi;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteCmpCombiDtl;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteCmpCombiDtlPk;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteCmpCombiPk;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteCmpPk;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaShiftPalletComRepository extends JpaRepository implements ShiftPalletsComRepository {

	private static final String SELECT;

	private static final String FIND_BY_PAGE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a.CID, a.PAGE, a.PAGE_NAME, a.USE_ATR, a.NOTE,");
		builderString.append("b.POSITION, b.POSITION_NAME,");
		builderString.append("c.POSITION_ORDER, c.SHIFT_MASTER_CD");
		builderString.append(
				" FROM KSCMT_PALETTE_CMP a LEFT JOIN KSCMT_PALETTE_CMP_COMBI b ON a.CID = b.CID AND a.PAGE = b.PAGE ");
		builderString.append("LEFT JOIN KSCMT_PALETTE_CMP_COMBI_DTL c ON a.CID = c.CID AND a.PAGE = c.PAGE ");
		SELECT = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append("WHERE a.CID = 'companyId' AND a.PAGE = page");
		FIND_BY_PAGE = builderString.toString();

	}

	private static final String FIND_BY_COMPANY = "SELECT a.CID, a.PAGE, a.PAGE_NAME, a.USE_ATR, a.NOTE,"
			+ " b.POSITION, b.POSITION_NAME," + " c.POSITION_ORDER, c.SHIFT_MASTER_CD"
			+ " FROM KSCMT_PALETTE_CMP a JOIN KSCMT_PALETTE_CMP_COMBI b ON a.CID = b.CID AND a.PAGE = b.PAGE"
			+ " JOIN KSCMT_PALETTE_CMP_COMBI_DTL c ON b.CID = c.CID AND b.PAGE = c.PAGE AND b.POSITION = c.POSITION"
			+ " WHERE a.CID = 'companyId'";

	@AllArgsConstructor
	@Getter
	private class FullShiftPallets {
		public String companyId;
		public int page;
		public String pageName;
		public boolean useAtr;
		public String note;
		public int position;
		public String positionName;
		public int positionOrder;
		public String shiftMasterCd;
	}

	@SneakyThrows
	private List<FullShiftPallets> createShiftPallets(ResultSet rs) {
		List<FullShiftPallets> listFullData = new ArrayList<>();
		while (rs.next()) {
			listFullData
					.add(new FullShiftPallets(rs.getString("CID"), Integer.valueOf(rs.getString("PAGE")),
							rs.getString("PAGE_NAME"), Integer.valueOf(rs.getString("USE_ATR")) == 1 ? true : false,
							rs.getString("NOTE"),
							rs.getString("POSITION") != null ? Integer.valueOf(rs.getString("POSITION")) : null,
							rs.getString("POSITION_NAME"), rs.getString("POSITION_ORDER") != null
									? Integer.valueOf(rs.getString("POSITION_ORDER")) : null,
							rs.getString("SHIFT_MASTER_CD")));
		}
		return listFullData;
	}

	private List<KscmtPaletteCmp> toEntity(List<FullShiftPallets> listFullJoin) {
		return listFullJoin.stream().collect(Collectors.groupingBy(FullShiftPallets::getPage)).entrySet().stream()
				.map(x -> {
					FullShiftPallets shiftPallets = x.getValue().get(0);
					KscmtPaletteCmpPk pk = new KscmtPaletteCmpPk(shiftPallets.getCompanyId(), shiftPallets.getPage());
					String pageName = shiftPallets.getPageName();
					boolean useAtr = shiftPallets.isUseAtr();
					String note = shiftPallets.getNote();
					List<KscmtPaletteCmpCombi> cmpCombis = x.getValue().stream()
							.collect(Collectors.groupingBy(FullShiftPallets::getPosition)).entrySet().stream()
							.map(y -> {
								KscmtPaletteCmpCombiPk combiPk = new KscmtPaletteCmpCombiPk(
										y.getValue().get(0).getCompanyId(), y.getValue().get(0).getPage(),
										y.getValue().get(0).getPosition());
								String positionName = y.getValue().get(0).getPositionName();
								List<KscmtPaletteCmpCombiDtl> cmpCombiDtls = y.getValue().stream()
										.collect(Collectors.groupingBy(FullShiftPallets::getPositionOrder)).entrySet()
										.stream().map(z -> {
											KscmtPaletteCmpCombiDtlPk cmpCombiDtlPk = new KscmtPaletteCmpCombiDtlPk(
													z.getValue().get(0).getCompanyId(), z.getValue().get(0).getPage(),
													z.getValue().get(0).getPosition(),
													z.getValue().get(0).getPositionOrder());
											String shiftMasterCd = z.getValue().get(0).getShiftMasterCd();
											return new KscmtPaletteCmpCombiDtl(cmpCombiDtlPk, shiftMasterCd, null);
										}).collect(Collectors.toList());
								return new KscmtPaletteCmpCombi(combiPk, positionName, null, cmpCombiDtls);
							}).collect(Collectors.toList());
					return new KscmtPaletteCmp(pk, pageName, useAtr ? 1 : 0, note, cmpCombis);
				}).collect(Collectors.toList());
	}

	@Override
	public void add(ShiftPalletsCom shiftPalletsCom) {
		commandProxy().insert(KscmtPaletteCmp.fromDomain(shiftPalletsCom));
	}

	@Override
	public void update(ShiftPalletsCom shiftPalletsCom) {

		Optional<KscmtPaletteCmp> getEntity = this.queryProxy().find(
				new KscmtPaletteCmpPk(AppContexts.user().companyId(), shiftPalletsCom.getPage()),
				KscmtPaletteCmp.class);

		if (getEntity.isPresent()) {
			getEntity.get().toEntity(shiftPalletsCom);
//			List<ShiftPalletCombinations> combinations = shiftPalletsCom.getShiftPallet().getCombinations();
//			KscmtPaletteCmp result = getEntity.get();
//			result.pageName = shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v();
//			result.useAtr = shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value; 
//			result.cmpCombis.stream().forEach(e->{
//				combinations.
//			});
			this.getEntityManager().merge(getEntity.get());
		}

	}

	@Override
	public void delete(ShiftPalletsCom shiftPalletsCom) {

		String query = FIND_BY_PAGE;
		query = query.replaceFirst("companyId", shiftPalletsCom.getCompanyId());
		query = query.replaceFirst("page", String.valueOf(shiftPalletsCom.getPage()));
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			KscmtPaletteCmp kscmtPaletteCmp = toEntity(createShiftPallets(rs)).get(0);
			commandProxy().remove(KscmtPaletteCmp.class, kscmtPaletteCmp.pk);
			this.getEntityManager().flush();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}

	@Override
	public Optional<ShiftPalletsCom> findShiftPallet(String companyId, int page) {
		String query = FIND_BY_PAGE;
		query = query.replaceFirst("companyId", companyId);
		query = query.replaceFirst("page", String.valueOf(page));
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			List<ShiftPalletsCom> palletsComs = toEntity(createShiftPallets(rs)).stream().map(x -> x.toDomain())
					.collect(Collectors.toList());

			if (palletsComs.isEmpty())
				return Optional.empty();
			return Optional.of(palletsComs.get(0));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<ShiftPalletsCom> findShiftPallet(String companyId) {
		String query = FIND_BY_COMPANY;
		query = query.replaceFirst("companyId", companyId);
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			List<ShiftPalletsCom> palletsComs = toEntity(createShiftPallets(rs)).stream().map(x -> x.toDomain())
					.collect(Collectors.toList());
			return palletsComs;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean isDuplicateRoleSetCd(String companyId, int page, int position) {
		KscmtPaletteCmpCombiPk pk = new KscmtPaletteCmpCombiPk(companyId, page, position);
		return this.queryProxy().find(pk, KscmtPaletteCmpCombi.class).isPresent();
	}

	@Override
	public void deleteByPage(String companyID, int page) {
		String query = FIND_BY_PAGE;
		query = query.replaceFirst("companyId", companyID);
		query = query.replaceFirst("page", String.valueOf(page));
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			KscmtPaletteCmp kscmtPaletteCmp = toEntity(createShiftPallets(rs)).get(0);
			commandProxy().remove(KscmtPaletteCmp.class, kscmtPaletteCmp.pk);
			this.getEntityManager().flush();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}

}
